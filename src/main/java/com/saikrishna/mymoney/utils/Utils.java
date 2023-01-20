package com.saikrishna.mymoney.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.saikrishna.mymoney.constants.AssetType;
import com.saikrishna.mymoney.domain.Fund;
import com.saikrishna.mymoney.exception.InvalidParameterException;

public class Utils {
  public static List<Integer> convertToIntegerList(String[] values)
      throws InvalidParameterException {
    List<Integer> intValues = new ArrayList<>();
    for (String value : values) {
      if (!StringUtils.isInteger(value)) {
        throw new InvalidParameterException("value must be an integer");
      }
      intValues.add(Integer.parseInt(value));
    }
    return intValues;
  }

  public static List<Double> convertToDoubleList(String[] values, int stopPointer)
      throws InvalidParameterException {
    List<Double> doubleValues = new ArrayList<>();
    int i = 0;
    for (String value : values) {
      if (i == stopPointer) {break;}
      value = value.replace("%", "");
      if (!StringUtils.isDouble(value)) {
        throw new InvalidParameterException("value must be an double");
      }
      doubleValues.add(Double.parseDouble(value));
      i++;
    }
    return doubleValues;
  }

  public static List<Fund> transformToFunds(List<Integer> funds) {
    List<Fund> assetFund = new ArrayList<>();
    for (int i = 0; i < AssetType.getAssetList().size(); i++) {
      assetFund.add(new Fund(funds.get(i), AssetType.getAssetList().get(i)));
    }
    return assetFund;
  }

  public static boolean isCollectionEmpty(Collection<Integer> funds) {
    return funds == null || funds.isEmpty();
  }
}
