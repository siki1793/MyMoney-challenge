package com.saikrishna.mymoney.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;

public class ChangeInvestmentService extends BaseInvestmentService {

  private static ChangeInvestmentService changeInvestmentService;
  private final SystematicInvestmentService systematicInvestmentService;

  public ChangeInvestmentService(SystematicInvestmentService systematicInvestmentService) {
    this.systematicInvestmentService = systematicInvestmentService;
  }

  public static ChangeInvestmentService getInstance(
      SystematicInvestmentService systematicInvestmentService) {
    if (changeInvestmentService == null) {
      synchronized (ChangeInvestmentService.class) {
        if (changeInvestmentService == null) {
          changeInvestmentService = new ChangeInvestmentService(systematicInvestmentService);
        }
      }
    }
    return changeInvestmentService;
  }

  public void processMonthlyChangeTransaction(Portfolio portfolio, List<Double> changePercentage,
      Month month)
      throws InvalidTransactionException, InvalidParameterException {
    if (checkIfNotFirstPayment(portfolio, month)) {
      systematicInvestmentService.processSIPTransaction(portfolio, month);
    }
    List<Double> percent = changePercentage.stream().map(x -> x / 100).collect(Collectors.toList());
    List<Integer> lastTransaction = portfolio.getPortfolioTransactionMonths().get(month).getLast()
        .getFundsValue();
    List<Integer> computedTransaction = IntStream.range(0, percent.size())
        .mapToObj(i -> (int)((1 + percent.get(i)) * lastTransaction.get(i)))
        .collect(Collectors.toList());
    processTransaction(portfolio, month, computedTransaction);
  }

  private boolean checkIfNotFirstPayment(Portfolio portfolio, Month month) {
    return portfolio.getPortfolioTransactionMonths().size() != 1 ||
        portfolio.getPortfolioTransactionMonths().lastEntry()
            .getValue().size() != 1;
  }

}
