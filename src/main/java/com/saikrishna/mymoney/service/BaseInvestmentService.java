package com.saikrishna.mymoney.service;

import java.util.LinkedList;
import java.util.List;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.InvalidParameterException;

public class BaseInvestmentService {
  public void processTransaction(Portfolio portfolio, Month month, List<Integer> initialFunds)
      throws InvalidParameterException {
    synchronized (portfolio) {
      LinkedList<Investment> monthTransactions = portfolio.getPortfolioTransactionMonths()
          .getOrDefault(month, new LinkedList<>());
      monthTransactions.add(new Investment(initialFunds));
      portfolio.getPortfolioTransactionMonths().put(month, monthTransactions);
      portfolio.setLastTransactionMonth(month);
    }
  }
}
