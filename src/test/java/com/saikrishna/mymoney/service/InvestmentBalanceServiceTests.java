package com.saikrishna.mymoney.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.RebalanceOutboundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvestmentBalanceServiceTests {
  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static PrintStream sysOut;
  private static ChangeInvestmentService changeInvestmentService;
  private static SystematicInvestmentService systematicInvestmentService;
  private static InitialInvestmentService initialInvestmentService;
  private static InvestmentBalanceService investmentBalanceService;

  @BeforeAll
  public static void setup() {
    initialInvestmentService = new InitialInvestmentService();
    systematicInvestmentService = new SystematicInvestmentService();
    changeInvestmentService = new ChangeInvestmentService(systematicInvestmentService);
    investmentBalanceService = new InvestmentBalanceService();
    sysOut = System.out;
    System.setOut(new PrintStream(outContent));
  }


  @Test
  public void testBalance_shouldNotThrowError() {
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, Arrays.asList(6000, 3000, 1000)));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, Arrays.asList(2000, 1000, 500)));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(4.00, 10.00, 2.00), Month.JANUARY));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(-10.00, 40.00, 0.00), Month.FEBRUARY));
    Investment investment = investmentBalanceService.getBalanceByMonth(portfolio,Month.JANUARY);
    assertEquals(Arrays.asList(6240, 3300, 1020), investment.getFundsValue());
  }

  @Test
  public void testRebalance_shouldThrowError_lessThan6Months() {
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, Arrays.asList(6000, 3000, 1000)));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, Arrays.asList(2000, 1000, 500)));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(4.00, 10.00, 2.00), Month.JANUARY));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(-10.00, 40.00, 0.00), Month.FEBRUARY));
    assertThrows(RebalanceOutboundException.class, () -> investmentBalanceService.rebalance(portfolio));
  }
  @Test
  public void testRebalance_shouldThrowError_greaterThan6Months() {
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, Arrays.asList(6000, 3000, 1000)));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, Arrays.asList(2000, 1000, 500)));
    changeInvestmentTransactions(portfolio);
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(10.0, 8.0, -5.0), Month.JULY));
    assertThrows(RebalanceOutboundException.class, () -> investmentBalanceService.rebalance(portfolio));
  }

  @Test
  public void testRebalance_shouldNotThrowError() {
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, Arrays.asList(6000, 3000, 1000)));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, Arrays.asList(2000, 1000, 500)));
    changeInvestmentTransactions(portfolio);
    assertDoesNotThrow(() -> investmentBalanceService.rebalance(portfolio));
    Investment investment = portfolio.getPortfolioTransactionMonths()
        .get(portfolio.getLastTransactionMonth()).getLast();
    List<Integer> transaction = investment.getFundsValue();
    assertEquals(39364, investment.getTotalFundsAmount());
    assertEquals(Arrays.asList(23619, 11809, 3936), transaction);
  }


  private void changeInvestmentTransactions(Portfolio portfolio) {
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(4.0, 10.0, 2.0), Month.JANUARY));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(-10.0, 40.0, 0.00), Month.FEBRUARY));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(12.5, 12.5, 12.5), Month.MARCH));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(8.0, -3.0, 7.0), Month.APRIL));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(13.0, 21.0, 10.5), Month.MAY));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(10.0, 8.0, -5.0), Month.JUNE));
  }
}
