package io.vertx.redis.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<String> commandsMap = new ArrayList<>();

        res.forEach(cmd -> {
          String commandName = cmd.get(0).toString();

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

          String keyLocator = null;

          if (cmd.get(8).size() > 0) {

            for (Response hint : cmd.get(8)) {
              String beginSearch = null;
              String findKeys = null;
              Boolean flagRO = null;

              if (hint.size() > 0) {
                if (hint.containsKey("flags")) {
                  for (Response flag : hint.get("flags")) {
                    if ("RO".equalsIgnoreCase(flag.toString())) {
                      flagRO = true;
                      break;
                    }
                    if ("RW".equalsIgnoreCase(flag.toString()) || "OW".equalsIgnoreCase(flag.toString()) || "RM".equalsIgnoreCase(flag.toString())) {
                      flagRO = false;
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
                      getkeys = true;
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
                      getkeys = true;
                      System.err.println(cmd);
                      break;
                  }
                }
              }

              if (beginSearch != null && findKeys != null) {
                if (keyLocator == null) {
                  keyLocator = "new KeyLocator(" + flagRO + ", " + beginSearch + ", " + findKeys + ")";
                } else {
                  keyLocator += ", new KeyLocator(" + flagRO + ", " + beginSearch + ", " + findKeys + ")";
                }
              }
            }
          }

          boolean pubSub = false;

          for (Response flag : cmd.get(2)) {
            if ("pubsub".equals(flag.toString())) {
              // we exclude PUBSUB / PUBLISH from the flag
              if ("pubsub".equalsIgnoreCase(commandName) || "publish".equalsIgnoreCase(commandName)) {
                continue;
              }
              pubSub = true;
              break;
            }
          }

          commands.add(
            generateCommand(
              commandName,
              cmd.get(1).toInteger(),
              ro,
              pubSub,
              getkeys,
              keyLocator
            ));

          commandsMap.add(generateCommandMap(commandName));
        });

        commands.sort(Comparator.naturalOrder());
        for (String cmd : commands) {
          System.out.println(cmd);
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------");
        System.out.println();

        commandsMap.sort(Comparator.naturalOrder());
        for (String cmd : commandsMap) {
          System.out.println(cmd);
        }

        vertx.close();
      });
  }

  private String generateCommand(String name, int arity, Boolean ro, boolean pubSub, boolean getKeys, String keyLocator) {
    return
      "Command " + toIdentifier(name) + " = new CommandImpl(\"" + name + "\", " + arity + ", " + ro + ", " + pubSub + ", " + getKeys + (keyLocator == null ? "" : ", " + keyLocator) + ");";
  }

  private String generateCommandMap(String name) {
    return "KNOWN_COMMANDS.put(\"" + name.toLowerCase() + "\", Command." + toIdentifier(name) + ");";
  }

  private String toIdentifier(String name) {
    return name.replace('.', '_').replace('-', '_').toUpperCase();
  }
}
