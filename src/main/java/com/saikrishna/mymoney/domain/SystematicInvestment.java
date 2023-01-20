package com.saikrishna.mymoney.domain;

import java.util.List;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystematicInvestment extends Investment {
  public SystematicInvestment(List<Integer> funds) throws InvalidParameterException {
    super(funds);
  }
}
