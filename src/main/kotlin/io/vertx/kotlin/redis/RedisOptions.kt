package io.vertx.kotlin.redis

import io.vertx.redis.RedisOptions

/**
 * A function providing a DSL for building [io.vertx.redis.RedisOptions] objects.
 *
 * This object controls the connection setting to the Redis Server. There is no need to specify most of the settings
 * since it has built the following sensible defaults:
 *
 * * `encoding`: `UTF-8`
 * * `host`: `localhost`
 * * `port`: 6379
 * * `tcpKeepAlive`: true
 * * `tcpNoDelay`: true
 * * `binary`: false
 *
 * However there are two extra properties that have no defaults since they are optional:
 *
 * * `auth`
 * * `select`
 *
 * The usage of this two extra properties is to setup required authentication and optionally the selection of the active
 * database at connection time. If you define this extra properties on every connection to Redis server this client
 * will perform the authentication handshake and database selection, however if you don't do this and call [io.vertx.redis.RedisClient]
 * yourself in case of connection failure the client will not be able to perform the correct authentication handshake.
 *
 * @param address  Set the eventbus address prefix for `PUB/SUB`.
 * @param auth  Set the password for authentication at connection time.
 * @param binary  Set the user defined character encoding, e.g.: `iso-8859-1`.
 * @param encoding  Set the user defined character encoding, e.g.: `iso-8859-1`.
 * @param host  Set the host name where the Redis server is listening.
 * @param port  Set the tcp port where the Redis server is listening.
 * @param select  Set the database to select at connection time.
 * @param tcpKeepAlive  Set the TCP KeepAlive option
 * @param tcpNoDelay  Set the TCP NoDelay at the socket level.
 *
 * <p/>
 * NOTE: This function has been automatically generated from the [io.vertx.redis.RedisOptions original] using Vert.x codegen.
 */
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
    this.setAddress(address)
  }
  if (auth != null) {
    this.setAuth(auth)
  }
  if (binary != null) {
    this.setBinary(binary)
  }
  if (encoding != null) {
    this.setEncoding(encoding)
  }
  if (host != null) {
    this.setHost(host)
  }
  if (port != null) {
    this.setPort(port)
  }
  if (select != null) {
    this.setSelect(select)
  }
  if (tcpKeepAlive != null) {
    this.setTcpKeepAlive(tcpKeepAlive)
  }
  if (tcpNoDelay != null) {
    this.setTcpNoDelay(tcpNoDelay)
  }
}

