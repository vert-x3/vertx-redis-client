package io.vertx.tests.redis.internal;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.ResponseType;
import io.vertx.redis.client.impl.types.BulkType;
import io.vertx.redis.client.impl.types.MultiType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiTypeTest {
  String strKey1 = "test-key-1";
  String strValue1 = "test-value-1";

  String strKey2 = "test-key-2";
  String strValue2 = "test-value-2";

  Buffer binKey1 = Buffer.buffer(new byte[]{0x10, 0x20, 0x30, 0x40, 0x50, 0x60});
  String binValue1 = "bin-value-1";

  Buffer binKey2 = Buffer.buffer(new byte[]{-0x10, -0x20, -0x30, -0x40, -0x50, -0x60});
  String binValue2 = "bin-value-2";

  MultiType multi;

  @BeforeEach
  void setup() {
    // multi isMap=true with two string keys and two binary keys
    multi = MultiType.create(4, true);
    multi.add(BulkType.create(Buffer.buffer(strKey1), false));
    multi.add(BulkType.create(Buffer.buffer(strValue1), false));
    multi.add(BulkType.create(binKey1, false));
    multi.add(BulkType.create(Buffer.buffer(binValue1), false));
    multi.add(BulkType.create(binKey2, false));
    multi.add(BulkType.create(Buffer.buffer(binValue2), false));
    multi.add(BulkType.create(Buffer.buffer(strKey2), false));
    multi.add(BulkType.create(Buffer.buffer(strValue2), false));
  }

  @Test
  void map_GetByStringKey() {
    // when: get value from map multi by string key
    var strKeyToGet = new String(strKey1); // ensure lookup by content, not by ref
    var retrieved = multi.get(strKeyToGet);

    // then: associated bulk value is retrieved correctly
    assertNotNull(retrieved);
    assertEquals(ResponseType.BULK, retrieved.type());
    assertEquals(strValue1, retrieved.toString());
  }

  @Test
  void map_GetByStringKey_NotFound() {
    // when: get value from map multi by a string key that is not present
    var nonExistingStrKey = "non-existing-key";
    var retrieved = multi.get(nonExistingStrKey);

    // then: null is returned
    assertNull(retrieved);
  }

  @Test
  void map_GetByBinaryKey() {
    // when: get value from map multi by binary key
    var binKeyToGet = binKey1.copy(); // ensure lookup by content, not by ref
    var retrieved = multi.get(binKeyToGet);

    // then: associated bulk value is retrieved correctly
    assertNotNull(retrieved);
    assertEquals(ResponseType.BULK, retrieved.type());
    assertEquals(binValue1, retrieved.toString());
  }

  @Test
  void map_GetByBinaryKey_NotFound() {
    // when: get value from map multi by a binary key that is not present
    var nonExistingBinKey = Buffer.buffer(new byte[]{0x01, 0x02, 0x03});
    var retrieved = multi.get(nonExistingBinKey);

    // then: null is returned
    assertNull(retrieved);
  }

  @Test
  void map_ContainsStrKey() {
    // when: check if map multi contains a string key
    var strKeyToCheck = new String(strKey2); // ensure lookup by content, not by ref
    var contains = multi.containsKey(strKeyToCheck);

    // then: key is found
    assertTrue(contains);
  }

  @Test
  void map_ContainsStrKey_NotFound() {
    // when: check if map multi contains a string key that is not present
    var nonExistingStrKey = "another-non-existing-key";
    var contains = multi.containsKey(nonExistingStrKey);

    // then: key is not found
    assertFalse(contains);
  }

  @Test
  void map_ContainsBinKey() {
    // when: check if map multi contains a binary key
    var binKeyToCheck = binKey2.copy(); // ensure lookup by content, not by ref
    var contains = multi.containsKey(binKeyToCheck);

    // then: key is found
    assertTrue(contains);
  }

  @Test
  void map_ContainsBinKey_NotFound() {
    // when: check if map multi contains a binary key that is not present
    var nonExistingBinKey = Buffer.buffer(new byte[]{-0x01, -0x02, -0x03});
    var contains = multi.containsKey(nonExistingBinKey);

    // then: key is not found
    assertFalse(contains);
  }

  @Test
  void map_GetKeys() {
    // when: get all str keys from the map multi
    var keys = multi.getKeys();

    // then: all four keys are returned
    assertEquals(4, keys.size());

    // and: binary keys are converted to UTF-8 strings
    assertTrue(keys.contains(strKey1));
    assertTrue(keys.contains(strKey2));
    assertTrue(keys.contains(binKey1.toString(UTF_8)));
    // note: (negative bytes become replacement char => �, i.e. U+FFFD)
    assertTrue(keys.contains(binKey2.toString(UTF_8)));
  }

  @Test
  void map_GetBinaryKeys() {
    // when: get all binary keys from the map multi
    var keys = multi.getBinaryKeys();

    // then: all four keys are returned
    assertEquals(4, keys.size());

    // and: string keys are converted to binary using UTF-8 encoding
    assertTrue(keys.contains(Buffer.buffer(strKey1)));
    assertTrue(keys.contains(Buffer.buffer(strKey2)));
    assertTrue(keys.contains(binKey1));
    assertTrue(keys.contains(binKey2));
  }

  @Test
  void map_ToStringEncodesKeysAsBase64_WhenBinaryKeysAreUsed() {
    // given: any method that generates binary keys representation was invoked
    // on the map multi
    multi.containsKey(binKey1);

    // when: toString is called on the map multi
    var str = multi.toString();

    // then: all keys are represented as base64 strings with the same format
    // as used in JSON format for binary values (URL-safe without padding)
    assertTrue(str.contains(binKey1.toJson()));
    assertTrue(str.contains(binKey2.toJson()));
    assertTrue(str.contains(Buffer.buffer(strKey1).toJson()));
    assertTrue(str.contains(Buffer.buffer(strKey2).toJson()));
  }

  @Test
  void map_ToStringEncodesKeysAsStrings_WhenOnlyStringKeysAreUsed() {
    // given: no method that generates binary keys representation was invoked
    // on a map multi

    // when: toString is called on the map multi
    var str = multi.toString();

    // then: all keys are represented as normal strings
    assertTrue(str.contains(strKey1));
    assertTrue(str.contains(strKey2));
    assertTrue(str.contains(binKey1.toString(UTF_8)));
    // note: illegal UTF-8 bytes in binary keys are converted to replacement char
    assertTrue(str.contains(binKey2.toString(UTF_8)));
  }

}
