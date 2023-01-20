package com.saikrishna.mymoney.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.utils.MessageConstants;
import com.saikrishna.mymoney.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Investment {
  protected List<Fund> assetFund;
  protected int totalFundsAmount;

  public Investment(List<Integer> funds) throws InvalidParameterException {
    if (Utils.isCollectionEmpty(funds)) {
      throw new InvalidParameterException(MessageConstants.INVALID_FUNDS);
    }
    totalFundsAmount = funds.stream().mapToInt(Integer::intValue).sum();
    assetFund = Utils.transformToFunds(funds);
  }

  public LinkedList<Integer> getFundsValue() {
    return new LinkedList<>(
        assetFund.stream().map(Fund::getAssertAmount).collect(Collectors.toList()));
  }
}
