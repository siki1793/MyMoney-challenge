package com.saikrishna.mymoney.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.RebalanceOutboundException;
import com.saikrishna.mymoney.utils.MessageConstants;

public class InvestmentBalanceService extends BaseInvestmentService {

  private static InvestmentBalanceService investmentBalanceService;

  public static InvestmentBalanceService getInstance() {
    if (investmentBalanceService == null) {
      synchronized (InvestmentBalanceService.class) {
        if (investmentBalanceService == null) {
          investmentBalanceService = new InvestmentBalanceService();
        }
      }
    }
    return investmentBalanceService;
  }

  public Investment getBalanceByMonth(Portfolio portfolio, Month month) {
    Investment investment = portfolio.getPortfolioTransactionMonths().get(month).getLast();
    return investment;
  }

  public void rebalance(Portfolio portfolio)
      throws RebalanceOutboundException, InvalidParameterException {
    if (!validateRebalanceOnTransactions(portfolio)) {
      throw new RebalanceOutboundException(MessageConstants.CANNOT_REBALANCE);
    }
    Map.Entry<Month, LinkedList<Investment>> lastMayEntry
        = portfolio.getPortfolioTransactionMonths().lastEntry();
    LinkedList<Integer> lastTransaction = lastMayEntry.getValue().getLast().getFundsValue();
    int sum = lastTransaction.stream().mapToInt(Integer::intValue).sum();
    List<Integer> rebalancedTransaction = portfolio.getInitialInvestment()
        .getInitialAllocationPercent().stream().map(x -> (int)Math.floor(x * sum)).collect(
            Collectors.toList());
    Month lastPaidMonth = lastMayEntry.getKey();
    processTransaction(portfolio, lastPaidMonth, rebalancedTransaction);
  }

  private boolean validateRebalanceOnTransactions(Portfolio portfolio) {
    return portfolio.getLastTransactionMonth() == Month.JUNE ||
        portfolio.getLastTransactionMonth() == Month.DECEMBER;
  }
}
