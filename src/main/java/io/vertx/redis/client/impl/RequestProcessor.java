package io.vertx.redis.client.impl;

import io.vertx.redis.client.Command;

public class RequestProcessor {

  static void process(Command cmd, Object... args) {
    System.out.println("Processing " + cmd);

    if (cmd.isMovable()) {
      // in this case we need to exec COMMAND GETKEYS command plus args and the reply is an array with the keys
      throw new RuntimeException("Command has movable keys, nothing can be said");
    }

    if (cmd.isMultiKey()) {
      System.out.println("multi key command");
      // args exclude the command which is an arg in the commands response
      int start = cmd.getFirstKey() - 1;
      int end = cmd.getLastKey();
      if (end > 0) {
        end--;
      }
      if (end < 0) {
        end = args.length + (end + 1);
      }
      int step = cmd.getInterval();
      System.out.println("Keys:");
      for (int i = start; i < end; i += step) {
        System.out.println(args[i]);
      }
    } else {
      if (cmd.isKeyless()) {
        System.out.println("Keyless");
      } else {
        System.out.println("Keys:");
        int start = cmd.getFirstKey() - 1;
        System.out.println(args[start]);
      }
    }
  }

  public static void main(String[] args) {
    process(Command.COMMAND);
    process(Command.GET, "key", "value");
    process(Command.MGET, "key1", "key2", "key3");
    process(Command.MSET, "key1", "value1", "key2", "value2", "key3", "value3");
    process(Command.BLPOP, "list1", "list2", "list3", "0");
//    process(Command.SORT, "mylist", "BY", "weight_*->fieldname", "GET", "object_*->fieldname");
  }
}
