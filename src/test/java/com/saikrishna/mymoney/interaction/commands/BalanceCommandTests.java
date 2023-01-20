package com.saikrishna.mymoney.interaction.commands;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BalanceCommandTests {
  private static MyMoneyCommandHandler myMoneyCommandHandler;
  private static BalanceCommand balanceCommand;

  private static RebalanceCommand rebalanceCommand;

  @BeforeAll
  public static void setup() {
    myMoneyCommandHandler = mock(MyMoneyCommandHandler.class);
    balanceCommand = new BalanceCommand(myMoneyCommandHandler);
    rebalanceCommand = new RebalanceCommand(myMoneyCommandHandler);
  }

  @Test
  public void testExecuteWithNoArg_shouldThrowError() {
    String[] params = {};
    assertThrows(InvalidParameterException.class, () -> balanceCommand.execute(params));
  }

  @Test
  public void testExecuteWithMoreArgs_shouldThrowError() {
    String[] params = {Month.MARCH.name(), Month.DECEMBER.name()};
    assertThrows(InvalidParameterException.class, () -> balanceCommand.execute(params));
  }

  @Test
  public void testExecuteWithInvalidArgs_shouldThrowError() {
    String[] params = {"random"};
    assertThrows(InvalidParameterException.class, () -> balanceCommand.execute(params));
  }

  @Test
  public void testExecuteWithValidArgs_shouldNotThrowError() throws MyMoneyException {
    String[] params = {Month.JANUARY.name()};
    doNothing().when(myMoneyCommandHandler).balance(any(Month.class));
    assertDoesNotThrow(() -> balanceCommand.execute(params));
  }

  @Test
  public void testExecuteRebalance_shouldNotThrowError()
      throws MyMoneyException, InvalidParameterException {
    String[] params = {};
    doNothing().when(myMoneyCommandHandler).rebalance();
    assertDoesNotThrow(() -> rebalanceCommand.execute(params));
  }
}
