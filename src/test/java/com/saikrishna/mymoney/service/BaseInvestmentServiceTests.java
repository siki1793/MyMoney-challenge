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

public class BaseInvestmentServiceTests {
  private static BaseInvestmentService baseInvestmentService;
  @BeforeAll
  public static void setup() {
    baseInvestmentService = new BaseInvestmentService();
  }

  @Test
  public void testProcessTransactions() {
    Portfolio portfolio = Portfolio.builder().build();
    List<Integer> funds = Arrays.asList(1,2,3);
    assertDoesNotThrow(() -> baseInvestmentService.processTransaction(portfolio, Month.JANUARY, funds));
    Map.Entry<Month, LinkedList<Investment>> lastTransactions = portfolio.getPortfolioTransactionMonths().lastEntry();
    assertEquals(Month.JANUARY, lastTransactions.getKey());
    assertEquals(funds, lastTransactions.getValue().getLast().getFundsValue());
  }
}
