package io.vertx.kotlin.redis

import io.vertx.redis.RedisOptions
import io.vertx.core.net.JdkSSLEngineOptions
import io.vertx.core.net.JksOptions
import io.vertx.core.net.OpenSSLEngineOptions
import io.vertx.core.net.PemKeyCertOptions
import io.vertx.core.net.PemTrustOptions
import io.vertx.core.net.PfxOptions
import io.vertx.core.net.ProxyOptions

/**
 * A function providing a DSL for building [io.vertx.redis.RedisOptions] objects.
 *
 * This object controls the connection setting to the Redis Server. There is no need to specify most of the settings
 * since it has built the following sensible defaults:
 * <p>
 * * `encoding`: `UTF-8`
 * * `host`: `localhost`
 * * `port`: 6379
 * * `tcpKeepAlive`: true
 * * `tcpNoDelay`: true
 * * `binary`: false
 * <p>
 * However there are two extra properties that have no defaults since they are optional:
 * <p>
 * * `auth`
 * * `select`
 * <p>
 * The usage of this two extra properties is to setup required authentication and optionally the selection of the active
 * database at connection time. If you define this extra properties on every connection to Redis server this client
 * will perform the authentication handshake and database selection, however if you don't do this and call [io.vertx.redis.RedisClient]
 * yourself in case of connection failure the client will not be able to perform the correct authentication handshake.
 *
 * @param address  Set the eventbus address prefix for `PUB/SUB`. * @param address address prefix.
 * @param auth  Set the password for authentication at connection time.
 * @param binary  Set the user defined character encoding, e.g.: `iso-8859-1`. * @param binary use binary messages
 * @param connectTimeout 
 * @param crlPaths 
 * @param crlValues 
 * @param enabledCipherSuites 
 * @param enabledSecureTransportProtocols 
 * @param encoding  Set the user defined character encoding, e.g.: `iso-8859-1`. * @param encoding the user character encoding
 * @param host  Set the host name where the Redis server is listening. * @param host host name
 * @param hostnameVerificationAlgorithm 
 * @param idleTimeout 
 * @param jdkSslEngineOptions 
 * @param keyStoreOptions 
 * @param localAddress 
 * @param logActivity 
 * @param masterName  Set name of Redis master (used with Sentinel).
 * @param metricsName 
 * @param openSslEngineOptions 
 * @param pemKeyCertOptions 
 * @param pemTrustOptions 
 * @param pfxKeyCertOptions 
 * @param pfxTrustOptions 
 * @param port  Set the tcp port where the Redis server is listening.
 * @param proxyOptions 
 * @param receiveBufferSize 
 * @param reconnectAttempts 
 * @param reconnectInterval 
 * @param reuseAddress 
 * @param select  Set the database to select at connection time. * @param select database id
 * @param sendBufferSize 
 * @param sentinels  Set the list of Sentinels.
 * @param soLinger 
 * @param ssl 
 * @param tcpKeepAlive 
 * @param tcpNoDelay 
 * @param trafficClass 
 * @param trustAll 
 * @param trustStoreOptions 
 * @param useAlpn 
 * @param usePooledBuffers 
 *
 * <p/>
 * NOTE: This function has been automatically generated from the [io.vertx.redis.RedisOptions original] using Vert.x codegen.
 */
fun RedisOptions(
  address: String? = null,
  auth: String? = null,
  binary: Boolean? = null,
  connectTimeout: Int? = null,
  crlPaths: Iterable<String>? = null,
  crlValues: Iterable<io.vertx.core.buffer.Buffer>? = null,
  enabledCipherSuites: Iterable<String>? = null,
  enabledSecureTransportProtocols: Iterable<String>? = null,
  encoding: String? = null,
  host: String? = null,
  hostnameVerificationAlgorithm: String? = null,
  idleTimeout: Int? = null,
  jdkSslEngineOptions: io.vertx.core.net.JdkSSLEngineOptions? = null,
  keyStoreOptions: io.vertx.core.net.JksOptions? = null,
  localAddress: String? = null,
  logActivity: Boolean? = null,
  masterName: String? = null,
  metricsName: String? = null,
  openSslEngineOptions: io.vertx.core.net.OpenSSLEngineOptions? = null,
  pemKeyCertOptions: io.vertx.core.net.PemKeyCertOptions? = null,
  pemTrustOptions: io.vertx.core.net.PemTrustOptions? = null,
  pfxKeyCertOptions: io.vertx.core.net.PfxOptions? = null,
  pfxTrustOptions: io.vertx.core.net.PfxOptions? = null,
  port: Int? = null,
  proxyOptions: io.vertx.core.net.ProxyOptions? = null,
  receiveBufferSize: Int? = null,
  reconnectAttempts: Int? = null,
  reconnectInterval: Long? = null,
  reuseAddress: Boolean? = null,
  select: Int? = null,
  sendBufferSize: Int? = null,
  sentinels: Iterable<String>? = null,
  soLinger: Int? = null,
  ssl: Boolean? = null,
  tcpKeepAlive: Boolean? = null,
  tcpNoDelay: Boolean? = null,
  trafficClass: Int? = null,
  trustAll: Boolean? = null,
  trustStoreOptions: io.vertx.core.net.JksOptions? = null,
  useAlpn: Boolean? = null,
  usePooledBuffers: Boolean? = null): RedisOptions = io.vertx.redis.RedisOptions().apply {

  if (address != null) {
    this.setAddress(address)
  }
  if (auth != null) {
    this.setAuth(auth)
  }
  if (binary != null) {
    this.setBinary(binary)
  }
  if (connectTimeout != null) {
    this.setConnectTimeout(connectTimeout)
  }
  if (crlPaths != null) {
    for (item in crlPaths) {
      this.addCrlPath(item)
    }
  }
  if (crlValues != null) {
    for (item in crlValues) {
      this.addCrlValue(item)
    }
  }
  if (enabledCipherSuites != null) {
    for (item in enabledCipherSuites) {
      this.addEnabledCipherSuite(item)
    }
  }
  if (enabledSecureTransportProtocols != null) {
    for (item in enabledSecureTransportProtocols) {
      this.addEnabledSecureTransportProtocol(item)
    }
  }
  if (encoding != null) {
    this.setEncoding(encoding)
  }
  if (host != null) {
    this.setHost(host)
  }
  if (hostnameVerificationAlgorithm != null) {
    this.setHostnameVerificationAlgorithm(hostnameVerificationAlgorithm)
  }
  if (idleTimeout != null) {
    this.setIdleTimeout(idleTimeout)
  }
  if (jdkSslEngineOptions != null) {
    this.setJdkSslEngineOptions(jdkSslEngineOptions)
  }
  if (keyStoreOptions != null) {
    this.setKeyStoreOptions(keyStoreOptions)
  }
  if (localAddress != null) {
    this.setLocalAddress(localAddress)
  }
  if (logActivity != null) {
    this.setLogActivity(logActivity)
  }
  if (masterName != null) {
    this.setMasterName(masterName)
  }
  if (metricsName != null) {
    this.setMetricsName(metricsName)
  }
  if (openSslEngineOptions != null) {
    this.setOpenSslEngineOptions(openSslEngineOptions)
  }
  if (pemKeyCertOptions != null) {
    this.setPemKeyCertOptions(pemKeyCertOptions)
  }
  if (pemTrustOptions != null) {
    this.setPemTrustOptions(pemTrustOptions)
  }
  if (pfxKeyCertOptions != null) {
    this.setPfxKeyCertOptions(pfxKeyCertOptions)
  }
  if (pfxTrustOptions != null) {
    this.setPfxTrustOptions(pfxTrustOptions)
  }
  if (port != null) {
    this.setPort(port)
  }
  if (proxyOptions != null) {
    this.setProxyOptions(proxyOptions)
  }
  if (receiveBufferSize != null) {
    this.setReceiveBufferSize(receiveBufferSize)
  }
  if (reconnectAttempts != null) {
    this.setReconnectAttempts(reconnectAttempts)
  }
  if (reconnectInterval != null) {
    this.setReconnectInterval(reconnectInterval)
  }
  if (reuseAddress != null) {
    this.setReuseAddress(reuseAddress)
  }
  if (select != null) {
    this.setSelect(select)
  }
  if (sendBufferSize != null) {
    this.setSendBufferSize(sendBufferSize)
  }
  if (sentinels != null) {
    this.setSentinels(sentinels.toList())
  }
  if (soLinger != null) {
    this.setSoLinger(soLinger)
  }
  if (ssl != null) {
    this.setSsl(ssl)
  }
  if (tcpKeepAlive != null) {
    this.setTcpKeepAlive(tcpKeepAlive)
  }
  if (tcpNoDelay != null) {
    this.setTcpNoDelay(tcpNoDelay)
  }
  if (trafficClass != null) {
    this.setTrafficClass(trafficClass)
  }
  if (trustAll != null) {
    this.setTrustAll(trustAll)
  }
  if (trustStoreOptions != null) {
    this.setTrustStoreOptions(trustStoreOptions)
  }
  if (useAlpn != null) {
    this.setUseAlpn(useAlpn)
  }
  if (usePooledBuffers != null) {
    this.setUsePooledBuffers(usePooledBuffers)
  }
}

