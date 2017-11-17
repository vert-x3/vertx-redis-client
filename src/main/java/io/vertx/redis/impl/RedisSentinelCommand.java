package io.vertx.redis.impl;

/**
 * Redis Sentinel Commands
 */
public enum RedisSentinelCommand implements AbstractCommand {

  CKQUORUM("SENTINEL ckquorum"),
  FAILOVER("SENTINEL failover"),
  FLUSHCONFIG("SENTINEL flushconfig"),
  GET_MASTER_ADDR_BY_NAME("SENTINEL get-master-addr-by-name"),
  INFO("SENTINEL info"),
  MASTER("SENTINEL master"),
  MASTERS("SENTINEL masters"),
  PSUBSCRIBE("PSUBSCRIBE"),
  PUNSUBSCRIBE("PUNSUBSCRIBE"),
  QUIT("QUIT"),
  RESET("SENTINEL reset"),
  SLAVES("SENTINEL slaves"),
  SENTINELS("SENTINEL sentinels"),
  SUBSCRIBE("SUBSCRIBE"),
  UNSUBSCRIBE("UNSUBSCRIBE");

  private final String[] tokens;

  RedisSentinelCommand(String command) {
    this.tokens = command.split(" ");
  }

  public String[] getTokens() {
    return tokens;
  }
}


