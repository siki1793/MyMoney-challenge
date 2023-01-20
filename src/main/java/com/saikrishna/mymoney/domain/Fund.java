package com.saikrishna.mymoney.domain;

import com.saikrishna.mymoney.constants.AssetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class Fund {
  private Integer assertAmount;
  private AssetType assetType;
}
