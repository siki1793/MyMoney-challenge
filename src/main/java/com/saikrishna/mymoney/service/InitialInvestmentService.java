package com.saikrishna.mymoney.service;

import java.util.List;

import com.saikrishna.mymoney.domain.InitialInvestment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.InvalidParameterException;

import static com.saikrishna.mymoney.constants.Constants.INITIAL_MONTH;

public class InitialInvestmentService extends BaseInvestmentService {

  private static InitialInvestmentService initialInvestmentService;

  public static InitialInvestmentService getInstance() {
    if (initialInvestmentService == null) {
      synchronized (InitialInvestmentService.class) {
        if (initialInvestmentService == null) {
          initialInvestmentService = new InitialInvestmentService();
        }
      }
    }
    return initialInvestmentService;
  }

  public void processInitialTransaction(Portfolio portfolio, List<Integer> initialFunds)
      throws InvalidParameterException {
    processTransaction(portfolio, INITIAL_MONTH, initialFunds);
    portfolio.setInitialInvestment(new InitialInvestment(initialFunds));
  }
}
