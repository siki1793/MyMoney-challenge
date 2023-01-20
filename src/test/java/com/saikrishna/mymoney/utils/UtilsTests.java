package com.saikrishna.mymoney.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.saikrishna.mymoney.constants.AssetType;
import com.saikrishna.mymoney.domain.Fund;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTests {
  @Test
  public void convertToIntegerList_shouldCheckStringArray() throws InvalidParameterException {
    assertThrows(InvalidParameterException.class, () -> Utils.convertToIntegerList(new String[] {"random"}));
    assertDoesNotThrow(() -> Utils.convertToIntegerList(new String[] {"1"}));
    assertEquals(Collections.singletonList(1), Utils.convertToIntegerList(new String[] {"1"}));
  }

  @Test
  public void convertToDoubleList_shouldCheckStringArray() throws InvalidParameterException {
    assertThrows(InvalidParameterException.class, () -> Utils.convertToDoubleList(new String[] {"random"}, 1));
    assertDoesNotThrow(() -> Utils.convertToDoubleList(new String[] {"0.5", "10.1"}, 1));
    assertEquals(Collections.singletonList(0.5), Utils.convertToDoubleList(new String[] {"0.5", "10.1"}, 1));
  }

  @Test
  public void transformToFunds_shouldCheckStringArray() throws InvalidParameterException {
    assertDoesNotThrow(() -> Utils.transformToFunds(Arrays.asList(10, 20, 30)));
    List<Fund> funds = Utils.transformToFunds(Arrays.asList(10, 20, 30));
    assertEquals(10, funds.get(0).getAssertAmount());
    assertEquals(AssetType.EQUITY, funds.get(0).getAssetType());
  }

  @Test
  public void isCollectionEmpty_shouldCheckCollection() {
    assertDoesNotThrow(() -> Utils.isCollectionEmpty(null));
    assertTrue(Utils.isCollectionEmpty(null));
    assertTrue(Utils.isCollectionEmpty(new ArrayList<>()));
    assertFalse(Utils.isCollectionEmpty(Arrays.asList(1)));
  }
}
