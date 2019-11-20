package io.vertx.redis.client.impl;

import io.vertx.redis.client.Command;
import io.vertx.redis.client.Request;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class KeyExtractorTest {

  @Test
  public void geoRadius() {
    String key = "Sicily";
    Request req = Request.cmd(Command.GEORADIUS).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusWithStore() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final Request req = Request.cmd(Command.GEORADIUS).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST")
      .arg(KeyExtractor.STORE_UPPER_CASE).arg(storeKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusWithStoreDist() {
    final String key = "Sicily";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUS).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST")
      .arg(KeyExtractor.STOREDIST_UPPER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusWithStoreAndStoreDist() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUS).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST")
      .arg(KeyExtractor.STORE_LOWER_CASE).arg(storeKey)
      .arg(KeyExtractor.STOREDIST_LOWER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(3, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[2], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusRo() {
    String key = "Sicily";
    Request req = Request.cmd(Command.GEORADIUS_RO).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusWithStoreRo() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final Request req = Request.cmd(Command.GEORADIUS_RO).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST")
      .arg(KeyExtractor.STORE_UPPER_CASE).arg(storeKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusWithStoreDistRo() {
    final String key = "Sicily";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUS_RO).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST")
      .arg(KeyExtractor.STOREDIST_UPPER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusWithStoreAndStoreDistRo() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUS_RO).arg(key).arg(15).arg(37).arg(200).arg("km").arg("WITHDIST")
      .arg(KeyExtractor.STORE_LOWER_CASE).arg(storeKey)
      .arg(KeyExtractor.STOREDIST_LOWER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(3, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[2], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMember() {
    String key = "Sicily";
    Request req = Request.cmd(Command.GEORADIUSBYMEMBER).arg(key).arg("Agrigento").arg(100).arg("km");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberWithStore() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final Request req = Request.cmd(Command.GEORADIUSBYMEMBER).arg(key).arg("Agrigento").arg(100).arg("km")
      .arg(KeyExtractor.STORE_UPPER_CASE).arg(storeKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberWithStoreDist() {
    final String key = "Sicily";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUSBYMEMBER).arg(key).arg("Agrigento").arg(100).arg("km")
      .arg(KeyExtractor.STOREDIST_UPPER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberWithStoreAndStoreDist() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUSBYMEMBER).arg(key).arg("Agrigento").arg(100).arg("km")
      .arg(KeyExtractor.STORE_LOWER_CASE).arg(storeKey)
      .arg(KeyExtractor.STOREDIST_LOWER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(3, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[2], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberRo() {
    String key = "Sicily";
    Request req = Request.cmd(Command.GEORADIUSBYMEMBER_RO).arg(key).arg("Agrigento").arg(100).arg("km");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberWithStoreRo() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final Request req = Request.cmd(Command.GEORADIUSBYMEMBER_RO).arg(key).arg("Agrigento").arg(100).arg("km")
      .arg(KeyExtractor.STORE_UPPER_CASE).arg(storeKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberWithStoreDistRo() {
    final String key = "Sicily";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUSBYMEMBER_RO).arg(key).arg("Agrigento").arg(100).arg("km")
      .arg(KeyExtractor.STOREDIST_UPPER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void geoRadiusByMemberWithStoreAndStoreDistRo() {
    final String key = "Sicily";
    final String storeKey = "sicily_store_key";
    final String storeDistKey = "sicily_storedist_key";
    final Request req = Request.cmd(Command.GEORADIUSBYMEMBER_RO).arg(key).arg("Agrigento").arg(100).arg("km")
      .arg(KeyExtractor.STORE_LOWER_CASE).arg(storeKey)
      .arg(KeyExtractor.STOREDIST_LOWER_CASE).arg(storeDistKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(3, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
    assertEquals(storeDistKey, new String(keys[2], StandardCharsets.UTF_8));
  }

  @Test
  public void sort() {
    final String key = "sort-key";
    final Request req = Request.cmd(Command.SORT).arg(key);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void sortWithStoreLowerCase() {
    final String key = "sort-key";
    final String storeKey = "store-key";
    final Request req = Request.cmd(Command.SORT).arg(key).arg(KeyExtractor.STORE_LOWER_CASE).arg(storeKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void sortWithStoreUpperCase() {
    final String key = "some-key";
    final String storeKey = "store-key";
    final Request req = Request.cmd(Command.SORT).arg(key).arg(KeyExtractor.STORE_UPPER_CASE).arg(storeKey);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(storeKey, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void migrateSingleKey() {
    final String key = "some-key";
    final Request req = Request.cmd(Command.MIGRATE).arg("127.0.0.1").arg(6379).arg(key);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(key, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void migrateSingleKeyKeysList() {
    final String[] migrationKeys = {"first-key"};
    final Request req = Request.cmd(Command.MIGRATE).arg("127.0.0.1").arg(6379).arg(0).arg(5000)
      .arg(KeyExtractor.KEYS_LOWER_CASE)
      .arg(migrationKeys[0]);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(migrationKeys.length, keys.length);
    for (int i = 0; i < migrationKeys.length; i++) {
      String migrationKey = migrationKeys[i];
      assertEquals(migrationKey, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void migrateTwoKeys() {
    final String[] migrationKeys = {"first-key", "second-key"};
    final Request req = Request.cmd(Command.MIGRATE).arg("127.0.0.1").arg(6379).arg(0).arg(5000)
      .arg(KeyExtractor.KEYS_LOWER_CASE)
      .arg(migrationKeys[0]).arg(migrationKeys[1]);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(migrationKeys.length, keys.length);
    for (int i = 0; i < migrationKeys.length; i++) {
      String migrationKey = migrationKeys[i];
      assertEquals(migrationKey, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void migrateThreeKeys() {
    final String[] migrationKeys = {"first-key", "second-key", "third-key"};
    final Request req = Request.cmd(Command.MIGRATE).arg("127.0.0.1").arg(6379).arg(0).arg(5000)
      .arg(KeyExtractor.KEYS_UPPER_CASE)
      .arg(migrationKeys[0]).arg(migrationKeys[1]).arg(migrationKeys[2]);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(migrationKeys.length, keys.length);
    for (int i = 0; i < migrationKeys.length; i++) {
      String migrationKey = migrationKeys[i];
      assertEquals(migrationKey, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void xreadSingleKey() {
    final String streamName = "mystream";
    // Valid ID
    Request req = Request.cmd(Command.XREAD).arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE).arg(streamName)
      .arg("0-0");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(streamName, new String(keys[0], StandardCharsets.UTF_8));

    // Without sequence
    req = Request.cmd(Command.XREAD).arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_LOWER_CASE).arg(streamName)
      .arg("0");

    keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(streamName, new String(keys[0], StandardCharsets.UTF_8));

    // Special $ ID
    req = Request.cmd(Command.XREAD).arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE).arg(streamName)
      .arg("$");

    keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(streamName, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void xreadTwoKeys() {
    final String[] streamNames = {"my-stream", "other-stream"};
    final Request req = Request.cmd(Command.XREAD).arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE)
      .arg(streamNames[0])
      .arg(streamNames[1])
      .arg("0-0")
      .arg("$");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(streamNames.length, keys.length);
    for (int i = 0; i < streamNames.length; i++) {
      String streamName = streamNames[i];
      assertEquals(streamName, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void xreadThreeKeys() {
    final String[] streamNames = {"my-stream", "other-stream", "further_stream"};
    final Request req = Request.cmd(Command.XREAD).arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE)
      .arg(streamNames[0])
      .arg(streamNames[1])
      .arg(streamNames[2])
      .arg("0-0")
      .arg("$")
      .arg("0");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(streamNames.length, keys.length);
    for (int i = 0; i < streamNames.length; i++) {
      String streamName = streamNames[i];
      assertEquals(streamName, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void xreadGroupSingleKey() {
    final String streamName = "mystream";
    // Valid ID
    Request req = Request.cmd(Command.XREADGROUP).arg("GROUP").arg("group-name").arg("consumer-name")
      .arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE).arg(streamName)
      .arg("0-0");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(streamName, new String(keys[0], StandardCharsets.UTF_8));

    // Without sequence
    req = Request.cmd(Command.XREADGROUP).arg("GROUP").arg("group-name").arg("consumer-name")
      .arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_LOWER_CASE).arg(streamName)
      .arg("0");

    keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(streamName, new String(keys[0], StandardCharsets.UTF_8));

    // Special $ ID
    req = Request.cmd(Command.XREADGROUP).arg("GROUP").arg("group-name").arg("consumer-name")
      .arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE).arg(streamName)
      .arg("$");

    keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(1, keys.length);
    assertEquals(streamName, new String(keys[0], StandardCharsets.UTF_8));
  }

  @Test
  public void xreadGroupTwoKeys() {
    final String[] streamNames = {"my-stream", "other-stream"};
    final Request req = Request.cmd(Command.XREADGROUP).arg("GROUP").arg("group-name").arg("consumer-name")
      .arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE)
      .arg(streamNames[0])
      .arg(streamNames[1])
      .arg("0-0")
      .arg("$");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(streamNames.length, keys.length);
    for (int i = 0; i < streamNames.length; i++) {
      String streamName = streamNames[i];
      assertEquals(streamName, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void xreadGroupThreeKeys() {
    final String[] streamNames = {"my-stream", "other-stream", "further_stream"};
    final Request req = Request.cmd(Command.XREADGROUP).arg("GROUP").arg("group-name").arg("consumer-name")
      .arg("COUNT").arg(2).arg(KeyExtractor.STREAMS_UPPER_CASE)
      .arg(streamNames[0])
      .arg(streamNames[1])
      .arg(streamNames[2])
      .arg("0-0")
      .arg("$")
      .arg("0");

    byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(streamNames.length, keys.length);
    for (int i = 0; i < streamNames.length; i++) {
      String streamName = streamNames[i];
      assertEquals(streamName, new String(keys[i], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void zInterstoreSingleKey() {
    final String destination = "destination";
    final String key = "some-key";
    final Request req = Request.cmd(Command.ZINTERSTORE).arg(destination).arg(1).arg(key).arg("WEIGHTS").arg(1);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(destination, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(key, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void zInterstoreTwoKeys() {
    final String destination = "destination";
    final String[] keys = {"some-key", "other-key"};
    final Request req = Request.cmd(Command.ZINTERSTORE).arg(destination).arg(keys.length)
      .arg(keys[0])
      .arg(keys[1])
      .arg("WEIGHTS").arg(1).arg(2).arg("AGGREGATE").arg("SUM");

    final byte[][] movableKeys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(keys.length + 1, movableKeys.length);
    assertEquals(destination, new String(movableKeys[0], StandardCharsets.UTF_8));
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      assertEquals(key, new String(movableKeys[i + 1], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void zInterstoreThreeKeys() {
    final String destination = "destination";
    final String[] keys = {"some-key", "other-key", "further-key"};
    final Request req = Request.cmd(Command.ZINTERSTORE).arg(destination).arg(keys.length)
      .arg(keys[0])
      .arg(keys[1])
      .arg(keys[2])
      .arg("WEIGHTS").arg(1).arg(2).arg("AGGREGATE").arg("SUM");

    final byte[][] movableKeys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(keys.length + 1, movableKeys.length);
    assertEquals(destination, new String(movableKeys[0], StandardCharsets.UTF_8));
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      assertEquals(key, new String(movableKeys[i +1], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void zUnionstoreSingleKey() {
    final String destination = "destination";
    final String key = "some-key";
    final Request req = Request.cmd(Command.ZUNIONSTORE).arg(destination).arg(1).arg(key).arg("WEIGHTS").arg(1);

    final byte[][] keys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(2, keys.length);
    assertEquals(destination, new String(keys[0], StandardCharsets.UTF_8));
    assertEquals(key, new String(keys[1], StandardCharsets.UTF_8));
  }

  @Test
  public void zUnionstoreTwoKeys() {
    final String destination = "destination";
    final String[] keys = {"some-key", "other-key"};
    final Request req = Request.cmd(Command.ZUNIONSTORE).arg(destination).arg(keys.length)
      .arg(keys[0])
      .arg(keys[1])
      .arg("WEIGHTS").arg(1).arg(2).arg("AGGREGATE").arg("SUM");

    final byte[][] movableKeys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(keys.length + 1, movableKeys.length);
    assertEquals(destination, new String(movableKeys[0], StandardCharsets.UTF_8));
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      assertEquals(key, new String(movableKeys[i + 1], StandardCharsets.UTF_8));
    }
  }

  @Test
  public void zUnionstoreThreeKeys() {
    final String destination = "destination";
    final String[] keys = {"some-key", "other-key", "further-key"};
    final Request req = Request.cmd(Command.ZUNIONSTORE).arg(destination).arg(keys.length)
      .arg(keys[0])
      .arg(keys[1])
      .arg(keys[2])
      .arg("WEIGHTS").arg(1).arg(2).arg("AGGREGATE").arg("SUM");

    final byte[][] movableKeys = KeyExtractor.extractMovableKeys((RequestImpl) req);
    assertEquals(keys.length + 1, movableKeys.length);
    assertEquals(destination, new String(movableKeys[0], StandardCharsets.UTF_8));
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      assertEquals(key, new String(movableKeys[i +1], StandardCharsets.UTF_8));
    }
  }
}
