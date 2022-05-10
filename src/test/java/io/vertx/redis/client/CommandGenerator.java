package io.vertx.redis.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import java.util.*;

import static io.vertx.redis.client.Command.COMMAND;
import static io.vertx.redis.client.Request.cmd;

public class CommandGenerator extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new CommandGenerator());
  }

  @Override
  public void start() {
    Redis client = Redis.createClient(vertx);

    client.send(cmd(COMMAND))
      .onFailure(err -> {
        err.printStackTrace();
        System.exit(1);
      })
      .onSuccess(res -> {

        List<String> commands = new ArrayList<>();

        res.forEach(cmd -> {
          String beginSearch = null;
          String findKeys = null;
          Boolean ro = null;
          boolean getkeys = false;

          for (Response flag : cmd.get(2)) {
            if ("readonly".equalsIgnoreCase(flag.toString())) {
              ro = true;
            }
            if ("write".equalsIgnoreCase(flag.toString())) {
              ro = false;
            }
          }

          if (cmd.get(8).size() > 0) {
            if (cmd.get(8).size() > 1) {
              getkeys = true;
            } else {
              Response hint = cmd.get(8).get(0);
              if (hint.size() > 0) {
                if (hint.size() > 0) {
                  if (hint.containsKey("flags")) {
                    for (Response flag : hint.get("flags")) {
                      if ("RO".equalsIgnoreCase(flag.toString())) {
                        ro = true;
                        break;
                      }
                      if ("RW".equalsIgnoreCase(flag.toString()) || "OW".equalsIgnoreCase(flag.toString()) || "RM".equalsIgnoreCase(flag.toString())) {
                        ro = false;
                        break;
                      }
                    }
                  }
                  if (hint.containsKey("begin_search")) {
                    String type = hint.get("begin_search").get("type").toString();
                    Response spec = hint.get("begin_search").get("spec");
                    switch (type) {
                      case "index":
                        beginSearch = "new BeginSearchIndex(" + spec.get("index").toInteger() + ")";
                        break;
                      case "keyword":
                        beginSearch = "new BeginSearchKeyword(\"" + spec.get("keyword").toString() + "\", " + spec.get("startfrom").toInteger() + ")";
                        break;
                      case "unknown":
                        System.err.println(cmd);
                        break;
                    }
                  }
                  if (hint.containsKey("find_keys")) {
                    String type = hint.get("find_keys").get("type").toString();
                    Response spec = hint.get("find_keys").get("spec");
                    switch (type) {
                      case "range":
                        findKeys = "new FindKeysRange(" + spec.get("lastkey").toInteger() + ", " + spec.get("keystep").toInteger() + ", " + spec.get("limit").toInteger() + ")";
                        break;
                      case "keynum":
                        findKeys = "new FindKeysKeynum(" + spec.get("keynumidx").toInteger() + ", " + spec.get("firstkey").toInteger() + ", " + spec.get("keystep").toInteger() + ")";
                        break;
                      case "unknown":
                        System.err.println(cmd);
                        break;
                    }
                  }
                }
              }
            }
          }

          boolean pubSub = false;

          for (Response flag : cmd.get(2)) {
            if ("pubsub".equals(flag.toString())) {
              // we exclude PUBSUB / PUBLISH from the flag
              if ("pubsub".equalsIgnoreCase(cmd.get(0).toString()) || "publish".equalsIgnoreCase(cmd.get(0).toString())) {
                continue;
              }
              pubSub = true;
              break;
            }
          }

          commands.add(
            generateCommand(
              cmd.get(0).toString(),
              cmd.get(1).toInteger(),
              beginSearch,
              findKeys,
              getkeys,
              ro,
              pubSub
            ));
        });

        commands.sort(Comparator.naturalOrder());
        for (String cmd : commands) {
          System.out.println(cmd);
        }
        vertx.close();
      });
  }

  private String generateCommand(String name, int arity, String beginSearch, String findKeys, boolean getkeys, Boolean ro, boolean pubSub) {
    return
      "Command " + name.replace('-', '_').toUpperCase() + " = Command.create(\"" + name + "\", " + arity + ", " + beginSearch + ", " + findKeys + ", " + getkeys + ", " + ro + ", " + pubSub + ");";
  }
}
