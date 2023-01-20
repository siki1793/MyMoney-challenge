package com.saikrishna.mymoney.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.domain.Investment;
import com.saikrishna.mymoney.domain.Portfolio;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SystematicInvestmentServiceTests {
  private static SystematicInvestmentService systematicInvestmentService;
  private static InitialInvestmentService initialInvestmentService;

  @BeforeAll
  public static void setup() {
    systematicInvestmentService = new SystematicInvestmentService();
    initialInvestmentService = new InitialInvestmentService();
  }

  @Test
  public void testSetSIPTransaction_shouldNotThrowError() {
    Portfolio portfolio = Portfolio.builder().build();
    List<Integer> funds = Arrays.asList(1, 2, 3);
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, funds));
    assertNotNull(portfolio.getSystematicInvestment());
    assertEquals(funds, portfolio.getSystematicInvestment().getFundsValue());
  }

  @Test
  public void testProcessSIPTransaction_shouldThrowError() {
    Portfolio portfolio = Portfolio.builder().build();
    List<Integer> funds = Arrays.asList(1, 2, 3);
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, funds));
    assertThrows(InvalidTransactionException.class, () -> systematicInvestmentService.processSIPTransaction(portfolio, Month.FEBRUARY));
  }

  @Test
  public void testProcessSIPTransaction_shouldNotThrowError() {
    List<Integer> funds = Arrays.asList(1, 2, 3);
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, funds));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, funds));
    assertDoesNotThrow(() -> systematicInvestmentService.processSIPTransaction(portfolio, Month.FEBRUARY));
    Map.Entry<Month, LinkedList<Investment>> lastTransactions = portfolio.getPortfolioTransactionMonths().lastEntry();
    assertEquals(Month.FEBRUARY, lastTransactions.getKey());
  }

  @Test
  public void testProcessSIPTransaction_shouldNotThrowError_firstMonth() {
    List<Integer> funds = Arrays.asList(1, 2, 3);
    Portfolio portfolio = Portfolio.builder().build();
    assertDoesNotThrow(() -> initialInvestmentService.processInitialTransaction(portfolio, funds));
    assertDoesNotThrow(() -> systematicInvestmentService.setSIPTransaction(portfolio, funds));
    assertDoesNotThrow(() -> systematicInvestmentService.processSIPTransaction(portfolio, Month.JANUARY));
    Map.Entry<Month, LinkedList<Investment>> lastTransactions = portfolio.getPortfolioTransactionMonths().lastEntry();
    assertEquals(Month.JANUARY, lastTransactions.getKey());
  }
}
