package com.saikrishna.mymoney.constants;

import java.util.Arrays;
import java.util.List;

public enum AssetType {
  EQUITY,
  DEBT,
  GOLD;

  public static List<AssetType> getAssetList() {
    return Arrays.asList(AssetType.values());
  }
}
