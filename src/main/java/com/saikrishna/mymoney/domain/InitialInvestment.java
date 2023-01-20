package com.saikrishna.mymoney.domain;

import java.util.LinkedList;
import java.util.List;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public class InitialInvestment extends Investment {
  private List<Double> initialAllocationPercent = new LinkedList<>();

  public InitialInvestment(List<Integer> fundAllocation) throws InvalidParameterException {
    super(fundAllocation);
    for (Integer amount : fundAllocation) {
      initialAllocationPercent.add((double)amount / totalFundsAmount);
    }
  }
}
