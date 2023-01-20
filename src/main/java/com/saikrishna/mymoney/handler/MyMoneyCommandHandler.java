package com.saikrishna.mymoney.handler;

import java.util.List;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.exception.RebalanceOutboundException;
import com.saikrishna.mymoney.service.ChangeInvestmentService;
import com.saikrishna.mymoney.service.InitialInvestmentService;
import com.saikrishna.mymoney.service.InvestmentBalanceService;
import com.saikrishna.mymoney.service.SystematicInvestmentService;
import com.saikrishna.mymoney.utils.MessageConstants;

import static com.saikrishna.mymoney.utils.MessageConstants.PORTFOLIO_ALREADY_CREATED;

public class MyMoneyCommandHandler {

  private final InitialInvestmentService initialInvestmentService;
  private final SystematicInvestmentService systematicInvestmentService;
  private final ChangeInvestmentService changeInvestmentService;
  private final InvestmentBalanceService investmentBalanceService;
  private Portfolio portfolio;

  public MyMoneyCommandHandler() {
    initialInvestmentService = InitialInvestmentService.getInstance();
    systematicInvestmentService = SystematicInvestmentService.getInstance();
    changeInvestmentService = ChangeInvestmentService.getInstance(systematicInvestmentService);
    investmentBalanceService = InvestmentBalanceService.getInstance();
  }

  /**
   * Method Description: this method will allocate initial investment in equity, debt and gold funds for the portfolio and assign investment allocation percentage
   * @param initialFunds
   * @throws MyMoneyException
   * @throws InvalidParameterException
   */
  public void allocateFunds(List<Integer> initialFunds)
      throws MyMoneyException, InvalidParameterException {
    if (isPortfolioCreated()) {
      throw new MyMoneyException(PORTFOLIO_ALREADY_CREATED);
    }
    createPortfolio();
    initialInvestmentService.processInitialTransaction(portfolio, initialFunds);
  }

  private void createPortfolio() {
    portfolio = Portfolio.builder().build();
  }

  /**
   * Method Description: this method set the Monthly SIP payments in equity, debt and gold funds
   * @param sipFunds
   * @throws MyMoneyException
   * @throws InvalidParameterException
   */
  public void setSIP(List<Integer> sipFunds) throws MyMoneyException, InvalidParameterException {
    if (!isPortfolioCreated()) {
      throw new MyMoneyException(MessageConstants.PORTFOLIO_NOT_CREATED);
    }
    systematicInvestmentService.setSIPTransaction(portfolio, sipFunds);
  }

  /**
   * Method Description: this method will calculate Monthly change rate (loss or growth) for each type of fund after SIP is calculated.
   * @param changePercentage
   * @param month
   * @throws MyMoneyException
   * @throws InvalidTransactionException
   * @throws InvalidParameterException
   */
  public void changePercentageByMonth(List<Double> changePercentage, Month month)
      throws MyMoneyException, InvalidTransactionException, InvalidParameterException {
    if (!isPortfolioCreated()) {
      throw new MyMoneyException(MessageConstants.PORTFOLIO_NOT_CREATED);
    }
    changeInvestmentService.processMonthlyChangeTransaction(portfolio, changePercentage, month);
  }

  /**
   * Method Description: this method fetch balance amount for equity, debt and gold funds with a certain month.
   * @param month
   * @throws MyMoneyException
   */
  public void balance(Month month) throws MyMoneyException {
    if (!isPortfolioCreated()) {
      throw new MyMoneyException(MessageConstants.PORTFOLIO_NOT_CREATED);
    }
    Investment investment = investmentBalanceService.getBalanceByMonth(portfolio, month);
    printTransaction(investment.getFundsValue());
  }

  /**
   * Method Description: this method rebalanced amount for the month transaction with initial investment allocation percentage
   * @throws MyMoneyException
   * @throws InvalidParameterException
   */
  public void rebalance() throws MyMoneyException, InvalidParameterException {
    if (!isPortfolioCreated()) {
      throw new MyMoneyException(MessageConstants.PORTFOLIO_NOT_CREATED);
    }
    try {
      investmentBalanceService.rebalance(portfolio);
      getLastTransaction(portfolio);
    } catch (RebalanceOutboundException e) {
      System.out.println("CANNOT_REBALANCE");
    }
  }

  /**
   * Method Description: this method will print last transacting month.
   * @param portfolio
   */
  private void getLastTransaction(Portfolio portfolio) {
    List<Integer> transaction = portfolio.getPortfolioTransactionMonths()
        .get(portfolio.getLastTransactionMonth()).getLast().getFundsValue();
    printTransaction(transaction);
  }

  /**
   * Method Description: this method will console out for a transaction.
   * @param transaction
   */
  private void printTransaction(List<Integer> transaction) {
    int i = 0;
    StringBuilder stringBuilder = new StringBuilder();
    for (Integer val : transaction) {
      stringBuilder.append(val).append((i == transaction.size() - 1 ? "" : " "));
      i++;
    }
    System.out.println(stringBuilder.toString());
  }

  private boolean isPortfolioCreated() {
    return portfolio != null;
  }

  public void resetPortfolio() {
    portfolio = null;
  }
}
