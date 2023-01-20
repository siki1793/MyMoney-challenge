package com.saikrishna.mymoney.domain;

import java.util.LinkedList;
import java.util.TreeMap;

import com.saikrishna.mymoney.constants.Month;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Portfolio {
  private InitialInvestment initialInvestment;
  private SystematicInvestment systematicInvestment;
  @Builder.Default
  private Month lastTransactionMonth = Month.JANUARY;
  @Builder.Default
  private TreeMap<Month, LinkedList<Investment>> portfolioTransactionMonths = new TreeMap<>();
}
