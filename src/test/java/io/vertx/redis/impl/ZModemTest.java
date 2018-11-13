package io.vertx.redis.impl;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ZModemTest {

  @Test
  public void testGenerate() {
    assertEquals(12325, ZModem.generate("foobar"));
    assertEquals(9132, ZModem.generate("abcdefghijklmnopqrstuvwxyz"));
    assertEquals(15532, ZModem.generate("gsdfhan$%^&*(sdgsdnhshcs"));
    assertEquals(12325, ZModem.generate("abc{foobar}"));
    assertEquals(12325, ZModem.generate("{foobar}"));
    assertEquals(12325, ZModem.generate("h8a9sd{foobar}}{asd}}"));
    assertEquals(16235, ZModem.generate("{foobar"));
    assertEquals(4435, ZModem.generate("foobar{}"));
    assertEquals(16235, ZModem.generate("{{foobar}"));
    assertEquals(13690, ZModem.generate("éêe"));
    assertEquals(3872, ZModem.generate("àâa"));
    assertEquals(14191, ZModem.generate("漢字"));
    assertEquals(16196, ZModem.generate("汉字"));
    assertEquals(4350, ZModem.generate("호텔"));
    assertEquals(9284, ZModem.generate("\uD83D\uDC80"));
    assertEquals(11620, ZModem.generate("\uD800\uDC00"));
  }

  @Test
  public void testGenerateMulti() {
    assertEquals(9132, ZModem.generateMulti(Arrays.asList(
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz",
      "abcdefghijklmnopqrstuvwxyz"
    )));
  }
}
