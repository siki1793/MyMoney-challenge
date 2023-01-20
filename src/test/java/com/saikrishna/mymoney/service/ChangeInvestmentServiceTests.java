package com.saikrishna.mymoney.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeInvestmentServiceTests {
  private static ChangeInvestmentService changeInvestmentService;
  private static SystematicInvestmentService systematicInvestmentService;
  private static InitialInvestmentService initialInvestmentService;

  @BeforeAll
  public static void setup() {
    initialInvestmentService = new InitialInvestmentService();
    systematicInvestmentService = new SystematicInvestmentService();
    changeInvestmentService = new ChangeInvestmentService(systematicInvestmentService);
  }

  @Test
  public void testProcessTransactions_firstMonth() {
    Portfolio portfolio = Portfolio.builder().build();
    List<Integer> funds = Arrays.asList(10, 20, 30);
    List<Double> changePercentage = Arrays.asList(50D, 20D, 30D);
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, funds));
    assertDoesNotThrow(
        () -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, changePercentage,
            Month.JANUARY));
    Map.Entry<Month, LinkedList<Investment>> lastTransactions
        = portfolio.getPortfolioTransactionMonths().lastEntry();
    assertEquals(Month.JANUARY, lastTransactions.getKey());
    assertEquals(Arrays.asList(15, 24, 39), lastTransactions.getValue().getLast().getFundsValue());
  }

  @Test
  public void testProcessTransactions_sipExecutedMonth() {
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, Arrays.asList(6000, 3000, 1000)));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, Arrays.asList(2000, 1000, 500)));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(4.00, 10.00, 2.00), Month.JANUARY));
    assertDoesNotThrow(() -> changeInvestmentService.processMonthlyChangeTransaction(portfolio, Arrays.asList(-10.00, 40.00, 0.00), Month.FEBRUARY));
    Map.Entry<Month, LinkedList<Investment>> lastTransactions = portfolio.getPortfolioTransactionMonths().lastEntry();
    assertEquals(Month.FEBRUARY, lastTransactions.getKey());
    assertEquals(Arrays.asList(7416, 6020, 1520), lastTransactions.getValue().getLast().getFundsValue());
  }
}
