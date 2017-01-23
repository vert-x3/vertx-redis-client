package io.vertx.kotlin.redis

import io.vertx.redis.RedisOptions

fun RedisOptions(
    address: String? = null,
  auth: String? = null,
  binary: Boolean? = null,
  encoding: String? = null,
  host: String? = null,
  port: Int? = null,
  select: Int? = null,
  tcpKeepAlive: Boolean? = null,
  tcpNoDelay: Boolean? = null): RedisOptions = io.vertx.redis.RedisOptions().apply {

  if (address != null) {
    this.address = address
  }

  if (auth != null) {
    this.auth = auth
  }

  if (binary != null) {
    this.isBinary = binary
  }

  if (encoding != null) {
    this.encoding = encoding
  }

  if (host != null) {
    this.host = host
  }

  if (port != null) {
    this.port = port
  }

  if (select != null) {
    this.select = select
  }

  if (tcpKeepAlive != null) {
    this.isTcpKeepAlive = tcpKeepAlive
  }

  if (tcpNoDelay != null) {
    this.isTcpNoDelay = tcpNoDelay
  }

}

