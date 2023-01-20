package com.saikrishna.mymoney.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.saikrishna.mymoney.constants.AssetType;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DomainTests {

  @Test
  public void testInitialInvestment_assertObject() throws InvalidParameterException {
    List<Integer> investmentArray = Arrays.asList(5000, 2000, 3000);
    InitialInvestment initialInvestment = new InitialInvestment(investmentArray);
    assertEquals(Arrays.asList(0.5, 0.2, 0.3), initialInvestment.getInitialAllocationPercent());
    assertFalse(initialInvestment.getFundsValue().isEmpty());
    assertEquals(3, initialInvestment.getAssetFund().size());
    assertEquals(AssetType.EQUITY, initialInvestment.getAssetFund().get(0).getAssetType());
    assertEquals(AssetType.DEBT, initialInvestment.getAssetFund().get(1).getAssetType());
    assertEquals(AssetType.GOLD, initialInvestment.getAssetFund().get(2).getAssetType());
    assertEquals(investmentArray.get(0), initialInvestment.getAssetFund().get(0).getAssertAmount());
    assertEquals(investmentArray.get(1), initialInvestment.getAssetFund().get(1).getAssertAmount());
    assertEquals(investmentArray.get(2), initialInvestment.getAssetFund().get(2).getAssertAmount());
  }

  @Test
  public void testSIPInvestment_assertObject() throws InvalidParameterException {
    List<Integer> investmentArray = Arrays.asList(100, 200, 300);
    SystematicInvestment systematicInvestment = new SystematicInvestment(investmentArray);
    assertFalse(systematicInvestment.getFundsValue().isEmpty());
    assertEquals(3, systematicInvestment.getAssetFund().size());
    assertEquals(AssetType.EQUITY, systematicInvestment.getAssetFund().get(0).getAssetType());
    assertEquals(AssetType.DEBT, systematicInvestment.getAssetFund().get(1).getAssetType());
    assertEquals(AssetType.GOLD, systematicInvestment.getAssetFund().get(2).getAssetType());
    assertEquals(investmentArray.get(0),
        systematicInvestment.getAssetFund().get(0).getAssertAmount());
    assertEquals(investmentArray.get(1),
        systematicInvestment.getAssetFund().get(1).getAssertAmount());
    assertEquals(investmentArray.get(2),
        systematicInvestment.getAssetFund().get(2).getAssertAmount());
  }

  @Test
  public void testInvestment_assertObject() throws InvalidParameterException {
    List<Integer> investmentArray = Arrays.asList(10, 50, 30);
    Investment investment = new Investment(investmentArray);
    assertFalse(investment.getFundsValue().isEmpty());
    assertEquals(3, investment.getAssetFund().size());
    assertEquals(AssetType.EQUITY, investment.getAssetFund().get(0).getAssetType());
    assertEquals(AssetType.DEBT, investment.getAssetFund().get(1).getAssetType());
    assertEquals(AssetType.GOLD, investment.getAssetFund().get(2).getAssetType());
    assertEquals(investmentArray.get(0), investment.getAssetFund().get(0).getAssertAmount());
    assertEquals(investmentArray.get(1), investment.getAssetFund().get(1).getAssertAmount());
    assertEquals(investmentArray.get(2), investment.getAssetFund().get(2).getAssertAmount());
  }

  @Test
  public void testInvestment_assertObject_exception() {
    List<Integer> investmentArray = Collections.emptyList();
    assertThrows(InvalidParameterException.class, () -> new Investment(investmentArray));
  }

  @Test
  public void testPortfolio_assertObject() {
    Portfolio portfolio = Portfolio.builder().build();
    assertTrue(portfolio.getPortfolioTransactionMonths().isEmpty());
    assertNull(portfolio.getInitialInvestment());
    assertNull(portfolio.getSystematicInvestment());
  }

  @Test
  public void testFund_assertObject_forCoverage() {
    Fund fund = Fund.builder()
        .assertAmount(1)
        .assetType(AssetType.EQUITY)
        .build();
    assertEquals(1, fund.getAssertAmount());
    assertEquals(AssetType.EQUITY, fund.getAssetType());
  }

  @Test
  public void testInvestment_assertObject_forCoverage() throws InvalidParameterException {
    Investment dummy = new Investment();
    Investment investment = Investment.builder()
        .assetFund(Collections.singletonList(new Fund(1, AssetType.EQUITY)))
        .totalFundsAmount(1)
        .build();
    assertEquals(1, investment.getAssetFund().get(0).getAssertAmount());
    assertEquals(AssetType.EQUITY, investment.getAssetFund().get(0).getAssetType());
    assertEquals(1, investment.getTotalFundsAmount());
  }
}
