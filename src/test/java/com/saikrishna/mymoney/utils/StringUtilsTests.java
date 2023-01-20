package com.saikrishna.mymoney.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTests {
  @Test
  public void isInteger_shouldCheckString() {
    assertEquals(StringUtils.isInteger("1"), true);
    assertEquals(StringUtils.isInteger("0.5"), false);
    assertEquals(StringUtils.isInteger("random"), false);
  }

  @Test
  public void isDouble_shouldCheckString() {
    assertEquals(StringUtils.isDouble("10.5"), true);
    assertEquals(StringUtils.isDouble("random"), false);
  }
}
