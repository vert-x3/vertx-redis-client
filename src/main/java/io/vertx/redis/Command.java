package io.vertx.redis;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Command {

  private String command;
  private Args args;
  private boolean readOnly;

  public Command() {}

  public Command(JsonObject json) {

  }

  public JsonObject toJson() {
    return null;
  }

  public String getCommand() {
    return command;
  }

  public Command setCommand(String command) {
    this.command = command;
    return this;
  }

  public Args getArgs() {
    return args;
  }

  public Command setArgs(Args args) {
    this.args = args;
    return this;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public Command setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    return this;
  }
}
