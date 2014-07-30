/*
 * Copyright (c) 2011-2013 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.sockjs.impl;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VoidHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.impl.WebSocketMatcher;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.routematcher.RouteMatcher;
import io.vertx.ext.sockjs.BridgeOptions;
import io.vertx.ext.sockjs.EventBusBridgeHook;
import io.vertx.ext.sockjs.SockJSServer;
import io.vertx.ext.sockjs.SockJSServerOptions;
import io.vertx.ext.sockjs.SockJSSocket;
import io.vertx.ext.sockjs.Transport;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static io.vertx.core.buffer.Buffer.*;

/**
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SockJSServerImpl implements SockJSServer, Handler<HttpServerRequest> {

  private static final Logger log = LoggerFactory.getLogger(SockJSServerImpl.class);

  private final Vertx vertx;
  private RouteMatcher rm = RouteMatcher.newRouteMatcher();
  private WebSocketMatcher wsMatcher = new WebSocketMatcher();
  private final LocalMap<String, Session> sessions;
  private EventBusBridgeHook hook;
  private long timerID;

  SockJSServerImpl(Vertx vertx, HttpServer httpServer) {
    this.vertx = vertx;
    this.sessions = vertx.sharedData().getLocalMap("_vertx.sockjssessions");
    // Any previous request and websocket handlers will become default handlers
    // if nothing else matches
    rm.noMatch(httpServer.requestHandler());
    wsMatcher.noMatch(new Handler<WebSocketMatcher.Match>() {
      Handler<ServerWebSocket> wsHandler = httpServer.websocketHandler();
      public void handle(WebSocketMatcher.Match match) {
        if (wsHandler != null) {
          wsHandler.handle(match.ws);
        }
      }
    });
    httpServer.requestHandler(this);
    httpServer.websocketHandler(wsMatcher);
    // Sanity check - a common mistake users make is to set the http request handler AFTER they have created this
    // which overwrites this one.
    timerID = vertx.setPeriodic(5000, new Handler<Long>() {
      @Override
      public void handle(Long timerID) {
        if (httpServer.requestHandler() == null) {
          // Implies server is closed - cancel timer id
          vertx.cancelTimer(timerID);
        } else if (httpServer.requestHandler() != SockJSServerImpl.this) {
          log.warn("You have overwritten the Http server request handler AFTER the SockJSServer has been created " +
                   "which will stop the SockJSServer from functioning. Make sure you set http request handler BEFORE " +
                   "you create the SockJSServer");
        }
      }
    });
  }

  @Override
  public void handle(HttpServerRequest req) {
    if (log.isTraceEnabled()) {
      log.trace("Got request in sockjs server: " + req.uri());
    }
    rm.accept(req);
  }

  public void close() {
    vertx.cancelTimer(timerID);
  }

  public SockJSServerImpl setHook(EventBusBridgeHook hook) {
	  this.hook = hook;
    return this;
  }

  public SockJSServerImpl installApp(SockJSServerOptions options,
                                     Handler<SockJSSocket> sockHandler) {

    String prefix = options.getPrefix();

    if (prefix == null || prefix.equals("") || prefix.endsWith("/")) {
      throw new IllegalArgumentException("Invalid prefix: " + prefix);
    }

    // Base handler for app

    rm.getWithRegEx(prefix + "\\/?", new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        if (log.isTraceEnabled()) log.trace("Returning welcome response");
        req.response().headers().set("Content-Type", "text/plain; charset=UTF-8");
        req.response().writeStringAndEnd("Welcome to SockJS!\n");
      }
    });

    // Iframe handlers
    String iframeHTML = IFRAME_TEMPLATE.replace("{{ sockjs_url }}", options.getLibraryURL());
    Handler<HttpServerRequest> iframeHandler = createIFrameHandler(iframeHTML);

    // Request exactly for iframe.html
    rm.getWithRegEx(prefix + "\\/iframe\\.html", iframeHandler);

    // Versioned
    rm.getWithRegEx(prefix + "\\/iframe-[^\\/]*\\.html", iframeHandler);

    // Chunking test
    rm.postWithRegEx(prefix + "\\/chunking_test", createChunkingTestHandler());
    rm.optionsWithRegEx(prefix + "\\/chunking_test", BaseTransport.createCORSOptionsHandler(options, "OPTIONS, POST"));

    // Info
    rm.getWithRegEx(prefix + "\\/info", BaseTransport.createInfoHandler(options));
    rm.optionsWithRegEx(prefix + "\\/info", BaseTransport.createCORSOptionsHandler(options, "OPTIONS, GET"));

    // Transports

    Set<String> enabledTransports = new HashSet<>();
    enabledTransports.add(Transport.EVENT_SOURCE.toString());
    enabledTransports.add(Transport.HTML_FILE.toString());
    enabledTransports.add(Transport.JSON_P.toString());
    enabledTransports.add(Transport.WEBSOCKET.toString());
    enabledTransports.add(Transport.XHR.toString());
    Set<String> disabledTransports = options.getDisabledTransports();
    if (disabledTransports == null) {
      disabledTransports = new HashSet<>();
    }
    enabledTransports.removeAll(disabledTransports);

    if (enabledTransports.contains(Transport.XHR.toString())) {
      new XhrTransport(vertx, rm, prefix, sessions, options, sockHandler);
    }
    if (enabledTransports.contains(Transport.EVENT_SOURCE.toString())) {
      new EventSourceTransport(vertx, rm, prefix, sessions, options, sockHandler);
    }
    if (enabledTransports.contains(Transport.HTML_FILE.toString())) {
      new HtmlFileTransport(vertx, rm, prefix, sessions, options, sockHandler);
    }
    if (enabledTransports.contains(Transport.JSON_P.toString())) {
      new JsonPTransport(vertx, rm, prefix, sessions, options, sockHandler);
    }
    if (enabledTransports.contains(Transport.WEBSOCKET.toString())) {
      new WebSocketTransport(vertx, wsMatcher, rm, prefix, sessions, options, sockHandler);
      new RawWebSocketTransport(vertx, wsMatcher, rm, prefix, sockHandler);
    }
    // Catch all for any other requests on this app

    rm.getWithRegEx(prefix + "\\/.+", new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        if (log.isTraceEnabled()) log.trace("Request: " + req.uri() + " does not match, returning 404");
        req.response().setStatusCode(404);
        req.response().end();
      }
    });
    return this;
  }

  public SockJSServerImpl bridge(SockJSServerOptions serverOptions, BridgeOptions bridgeOptions) {
	  EventBusBridge busBridge = new EventBusBridge(vertx, bridgeOptions);
    if (hook != null) {
      busBridge.setHook(hook);
    }
    installApp(serverOptions, busBridge);
    return this;
  }


  private Handler<HttpServerRequest> createChunkingTestHandler() {
    return new Handler<HttpServerRequest>() {

      class TimeoutInfo {
        long timeout;
        Buffer buff;

        TimeoutInfo(long timeout, Buffer buff) {
          this.timeout = timeout;
          this.buff = buff;
        }
      }

      private void setTimeout(List<TimeoutInfo> timeouts, long delay, Buffer buff) {
        timeouts.add(new TimeoutInfo(delay, buff));
      }

      private void runTimeouts(List<TimeoutInfo> timeouts, HttpServerResponse response) {
        Iterator<TimeoutInfo> iter = timeouts.iterator();
        nextTimeout(timeouts, iter, response);
      }

      private void nextTimeout(List<TimeoutInfo> timeouts, Iterator<TimeoutInfo> iter, HttpServerResponse response) {
        if (iter.hasNext()) {
          TimeoutInfo timeout = iter.next();
          vertx.setTimer(timeout.timeout, new Handler<Long>() {
            public void handle(Long id) {
              response.writeBuffer(timeout.buff);
              nextTimeout(timeouts, iter, response);
            }
          });
        } else {
          timeouts.clear();
        }
      }

      public void handle(HttpServerRequest req) {
        req.response().headers().set("Content-Type", "application/javascript; charset=UTF-8");

        BaseTransport.setCORS(req);
        req.response().setChunked(true);

        Buffer h = buffer(2);
        h.appendString("h\n");

        Buffer hs = buffer(2050);
        for (int i = 0; i < 2048; i++) {
          hs.appendByte((byte) ' ');
        }
        hs.appendString("h\n");

        List<TimeoutInfo> timeouts = new ArrayList<>();

        setTimeout(timeouts, 0, h);
        setTimeout(timeouts, 1, hs);
        setTimeout(timeouts, 5, h);
        setTimeout(timeouts, 25, h);
        setTimeout(timeouts, 125, h);
        setTimeout(timeouts, 625, h);
        setTimeout(timeouts, 3125, h);

        runTimeouts(timeouts, req.response());

      }
    };
  }

  private Handler<HttpServerRequest> createIFrameHandler(String iframeHTML) {
    String etag = getMD5String(iframeHTML);
    return new Handler<HttpServerRequest>() {
      public void handle(HttpServerRequest req) {
        try {
          if (log.isTraceEnabled()) log.trace("In Iframe handler");
          if (etag != null && etag.equals(req.headers().get("if-none-match"))) {
            req.response().setStatusCode(304);
            req.response().end();
          } else {
            req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
            req.response().headers().set("Cache-Control", "public,max-age=31536000");
            long oneYear = 365 * 24 * 60 * 60 * 1000;
            String expires = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date(System.currentTimeMillis() + oneYear));
            req.response().headers().set("Expires", expires);
            req.response().headers().set("ETag", etag);
            req.response().writeStringAndEnd(iframeHTML);
          }
        } catch (Exception e) {
          log.error("Failed to server iframe", e);
        }
      }
    };
  }

  private static String getMD5String(String str) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(str.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
          sb.append(Integer.toHexString(b + 127));
        }
        return sb.toString();
    }
    catch (Exception e) {
        log.error("Failed to generate MD5 for iframe, If-None-Match headers will be ignored");
        return null;
    }
  }


  private static String IFRAME_TEMPLATE =
      "<!DOCTYPE html>\n" +
      "<html>\n" +
      "<head>\n" +
      "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
      "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
      "  <script>\n" +
      "    document.domain = document.domain;\n" +
      "    _sockjs_onload = function(){SockJS.bootstrap_iframe();};\n" +
      "  </script>\n" +
      "  <script src=\"{{ sockjs_url }}\"></script>\n" +
      "</head>\n" +
      "<body>\n" +
      "  <h2>Don't panic!</h2>\n" +
      "  <p>This is a SockJS hidden iframe. It's used for cross domain magic.</p>\n" +
      "</body>\n" +
      "</html>";

  // For debug only
//  public static void main(String[] args) throws Exception {
//    Vertx vertx = Vertx.newVertx();
//    HttpServer httpServer = vertx.createHttpServer(new HttpServerOptions().setPort(8081));
//    SockJSServerImpl sjsServer = (SockJSServerImpl)vertx.createSockJSServer(httpServer);
//    sjsServer.installTestApplications();
//    httpServer.listen();
//    Thread.sleep(Long.MAX_VALUE);
//  }

  /*
  These applications are required by the SockJS protocol and QUnit tests
   */
  public void installTestApplications() {
    installApp(SockJSServerOptions.options().setPrefix("/echo").setMaxBytesStreaming(4096),
               sock -> sock.dataHandler(sock::writeBuffer));
    installApp(SockJSServerOptions.options().setPrefix("/close").setMaxBytesStreaming(4096),
               sock -> sock.close());
    installApp(SockJSServerOptions.options().setPrefix("/disabled_websocket_echo").setMaxBytesStreaming(4096).addDisabledTransport("WEBSOCKET"),
      sock -> sock.dataHandler(sock::writeBuffer));
    installApp(SockJSServerOptions.options().setPrefix("/ticker").setMaxBytesStreaming(4096),
      sock -> {
        long timerID = vertx.setPeriodic(1000, tid -> sock.writeBuffer(buffer("tick!")));
        sock.endHandler(v -> vertx.cancelTimer(timerID));
      });
    installApp(SockJSServerOptions.options().setPrefix("/amplify").setMaxBytesStreaming(4096),
      sock -> {
        sock.dataHandler(data -> {
          String str = data.toString();
          int n = Integer.valueOf(str);
          if (n < 0 || n > 19) {
            n = 1;
          }
          int num = (int) Math.pow(2, n);
          Buffer buff = buffer(num);
          for (int i = 0; i < num; i++) {
            buff.appendByte((byte) 'x');
          }
          sock.writeBuffer(buff);
        });
      });
    installApp(SockJSServerOptions.options().setPrefix("/broadcast").setMaxBytesStreaming(4096),
      new Handler<SockJSSocket>() {
        Set<String> connections = new HashSet<>();
        public void handle(SockJSSocket sock) {
          connections.add(sock.writeHandlerID());
          sock.dataHandler(new Handler<Buffer>() {
            public void handle(Buffer buffer) {
              for (String actorID : connections) {
                vertx.eventBus().publish(actorID, buffer);
              }
            }
          });
          sock.endHandler(new VoidHandler() {
            public void handle() {
              connections.remove(sock.writeHandlerID());
            }
          });
        }
      });

    installApp(SockJSServerOptions.options().setPrefix("/cookie_needed_echo").setMaxBytesStreaming(4096).setInsertJSESSIONID(true),
      sock -> sock.dataHandler(sock::writeBuffer));
  }

}

