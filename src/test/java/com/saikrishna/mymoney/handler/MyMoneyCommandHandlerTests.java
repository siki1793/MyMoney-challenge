package com.saikrishna.mymoney.handler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyMoneyCommandHandlerTests {
  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static PrintStream sysOut;
  private static MyMoneyCommandHandler myMoneyCommandHandler;

  @BeforeAll
  public static void setup() {
    myMoneyCommandHandler = new MyMoneyCommandHandler();
    sysOut = System.out;
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testEmptyAllocateFunds_shouldThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertThrows(InvalidParameterException.class, () -> myMoneyCommandHandler.allocateFunds(new ArrayList<>()));
  }

  @Test
  public void testValidAllocateFunds_shouldNotThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertDoesNotThrow(() -> myMoneyCommandHandler.allocateFunds(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void testAllocateFunds_PortfolioAlreadyCreated_shouldThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertDoesNotThrow(() -> myMoneyCommandHandler.allocateFunds(Arrays.asList(1, 2, 3)));
    assertThrows(MyMoneyException.class, () -> myMoneyCommandHandler.allocateFunds(Arrays.asList(5, 6, 3)));
  }

  @Test
  public void testSIPFunds_PortfolioNotCreated_shouldThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertThrows(MyMoneyException.class, () -> myMoneyCommandHandler.setSIP(Arrays.asList(5, 6, 3)));
  }

  @Test
  public void testSIPFunds_PortfolioCreated_shouldNotThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertDoesNotThrow(() -> myMoneyCommandHandler.allocateFunds(Arrays.asList(1, 2, 3)));
    assertDoesNotThrow(() -> myMoneyCommandHandler.setSIP(Arrays.asList(5, 6, 3)));
  }

  @Test
  public void testChangeFunds_PortfolioNotCreated_shouldThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertThrows(MyMoneyException.class, () -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(0.2, 0.3, 0.5), Month.JANUARY));
  }

  @Test
  public void testChangeFunds_PortfolioCreated_SIPNotSet_shouldNotThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertDoesNotThrow(() -> myMoneyCommandHandler.allocateFunds(Arrays.asList(1, 2, 3)));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(0.2, 0.3, 0.5), Month.JANUARY));
  }

  @Test
  public void testBalance_PortfolioNotCreated_shouldThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertThrows(MyMoneyException.class, () -> myMoneyCommandHandler.balance(Month.JANUARY));
  }

  @Test
  public void testBalance_PortfolioCreated_shouldNotThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    createAPortfolioWithTransactions();
    assertDoesNotThrow(() -> myMoneyCommandHandler.balance(Month.MARCH));
    assertTrue(outContent.toString().trim().endsWith("10593 7897 2272"));
    assertDoesNotThrow(() -> myMoneyCommandHandler.balance(Month.APRIL));
    assertTrue(outContent.toString().trim().endsWith("13600 8630 2966"));
    assertDoesNotThrow(() -> myMoneyCommandHandler.balance(Month.JUNE));
    assertTrue(outContent.toString().trim().endsWith("21590 13664 4112"));
  }

  @Test
  public void testRebalance_PortfolioNotCreated_shouldThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    assertThrows(MyMoneyException.class, () -> myMoneyCommandHandler.rebalance());
  }

  @Test
  public void testRebalance_PortfolioCreated_shouldNotThrowError() {
    myMoneyCommandHandler.resetPortfolio();
    createAPortfolioWithTransactions();
    assertDoesNotThrow(() -> myMoneyCommandHandler.rebalance());
    assertTrue(outContent.toString().trim().endsWith("23619 11809 3936"));
  }

  @Test
  public void testRebalance_PortfolioCreated_shouldThrowError_greaterThat6Month() {
    myMoneyCommandHandler.resetPortfolio();
    createAPortfolioWithTransactions();
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(10.0, 8.0, -5.0), Month.JULY));
    assertDoesNotThrow(() -> myMoneyCommandHandler.rebalance());
    assertTrue(outContent.toString().trim().endsWith("CANNOT_REBALANCE"));
  }

  private void createAPortfolioWithTransactions() {
    assertDoesNotThrow(() -> myMoneyCommandHandler.allocateFunds(Arrays.asList(6000, 3000, 1000)));
    assertDoesNotThrow(() -> myMoneyCommandHandler.setSIP(Arrays.asList(2000, 1000, 500)));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(4.0, 10.0, 2.0), Month.JANUARY));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(-10.0, 40.0, 0.00), Month.FEBRUARY));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(12.5, 12.5, 12.5), Month.MARCH));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(8.0, -3.0, 7.0), Month.APRIL));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(13.0, 21.0, 10.5), Month.MAY));
    assertDoesNotThrow(() -> myMoneyCommandHandler.changePercentageByMonth(Arrays.asList(10.0, 8.0, -5.0), Month.JUNE));
  }
}
