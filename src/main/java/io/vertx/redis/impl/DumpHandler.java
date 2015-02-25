package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.redis.RedisEncoding;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public class DumpHandler implements Handler<AsyncResult<String>> {

  private Handler<AsyncResult<String>> delegate;

  public DumpHandler(Handler<AsyncResult<String>> handler) {
    this.delegate = handler;
  }

  @Override
  public void handle(AsyncResult<String> event) {
    delegate.handle(new DelegatingAsyncResult(event));
  }

  private static class DelegatingAsyncResult implements AsyncResult<String> {

    private AsyncResult<String> delegate;

    private DelegatingAsyncResult(AsyncResult<String> event) {
      this.delegate = event;
    }

    @Override
    public String result() {
      String result = delegate.result();
      if (result == null) {
        return null;
      }
      return RedisEncoding.encode(result);
    }

    @Override
    public Throwable cause() {
      return delegate.cause();
    }

    @Override
    public boolean succeeded() {
      return delegate.succeeded();
    }

    @Override
    public boolean failed() {
      return delegate.failed();
    }
  }
}
