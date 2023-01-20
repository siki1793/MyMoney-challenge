package com.saikrishna.mymoney.interaction.commands;

import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ChangeCommandTests {
  private static MyMoneyCommandHandler myMoneyCommandHandler;
  private static ChangeCommand changeCommand;

  @BeforeAll
  public static void setup() {
    myMoneyCommandHandler = mock(MyMoneyCommandHandler.class);
    changeCommand = new ChangeCommand(myMoneyCommandHandler);
  }

  @Test
  public void testExecuteWithNoArg_shouldThrowError() {
    String[] params = {};
    assertThrows(InvalidParameterException.class, () -> changeCommand.execute(params));
  }

  @Test
  public void testExecuteWithMoreArgs_shouldThrowError() {
    String[] params = {"1", "2", "3"};
    assertThrows(InvalidParameterException.class, () -> changeCommand.execute(params));
  }

  @Test
  public void testExecuteWithInvalidArgs_shouldThrowError() {
    String[] params = {"1", "2", "3", "random"};
    assertThrows(InvalidParameterException.class, () -> changeCommand.execute(params));
  }

  @Test
  public void testExecuteWithValidArgs_shouldNotThrowError()
      throws InvalidParameterException, MyMoneyException, InvalidTransactionException {
    String[] params = {"1", "2", "3", Month.JANUARY.name()};
    doNothing().when(myMoneyCommandHandler).changePercentageByMonth(anyList(), any(Month.class));
    assertDoesNotThrow(() -> changeCommand.execute(params));
  }
}
