package io.vertx.tests.redis.client;

final class Pair<A, B> {
  final A a;
  final B b;

  Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }
}
