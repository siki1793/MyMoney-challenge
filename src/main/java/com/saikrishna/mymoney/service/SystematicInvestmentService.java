package com.saikrishna.mymoney.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.domain.SystematicInvestment;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.utils.MessageConstants;

public class SystematicInvestmentService extends BaseInvestmentService {

  private static SystematicInvestmentService systematicInvestmentService;

  public static SystematicInvestmentService getInstance() {
    if (systematicInvestmentService == null) {
      synchronized (SystematicInvestmentService.class) {
        if (systematicInvestmentService == null) {
          systematicInvestmentService = new SystematicInvestmentService();
        }
      }
    }
    return systematicInvestmentService;
  }

  public void setSIPTransaction(Portfolio portfolio, List<Integer> sipFunds)
      throws InvalidParameterException {
    portfolio.setSystematicInvestment(new SystematicInvestment(sipFunds));
  }

  public void processSIPTransaction(Portfolio portfolio, Month month)
      throws InvalidTransactionException, InvalidParameterException {
    validateTransaction(portfolio, month);
    List<Integer> sipAmount = portfolio.getSystematicInvestment().getFundsValue();
    List<Integer> lastTransaction = getLastTransaction(portfolio, month);
    List<Integer> newSipCalculated = IntStream.range(0, lastTransaction.size())
        .mapToObj(i -> (lastTransaction.get(i) + sipAmount.get(i))).collect(Collectors.toList());
    processTransaction(portfolio, month, newSipCalculated);
  }

  private List<Integer> getLastTransaction(Portfolio portfolio, Month month) {
    LinkedList<Investment> transactions = portfolio.getPortfolioTransactionMonths()
        .get(Month.getPreviousMonth(month));
    if (transactions == null) {
      return portfolio.getInitialInvestment().getFundsValue();
    }
    return transactions.getLast().getFundsValue();
  }

  private void validateTransaction(Portfolio portfolio, Month month)
      throws InvalidTransactionException {
    if (portfolio.getPortfolioTransactionMonths().size() == 0) {
      throw new InvalidTransactionException(MessageConstants.PORTFOLIO_INITIAL_INVESTMENT_NOT_SET);
    }
  }
}
