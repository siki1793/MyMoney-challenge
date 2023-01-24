package com.saikrishna.mymoney.interaction.commands;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class AllocateCommandTests {
  private static MyMoneyCommandHandler myMoneyCommandHandler;
  private static AllocateSIPCommand allocateCommand;

  @BeforeAll
  public static void setup() {
    myMoneyCommandHandler = mock(MyMoneyCommandHandler.class);
    allocateCommand = new AllocateSIPCommand(myMoneyCommandHandler, Command.ALLOCATE);
  }

  @Test
  public void testExecuteWithNoArg_shouldThrowError() {
    String[] params = {};
    assertThrows(InvalidParameterException.class, () -> allocateCommand.execute(params));
  }

  @Test
  public void testExecuteWithMoreArgs_shouldThrowError() {
    String[] params = {"1", "2", "3", "4"};
    assertThrows(InvalidParameterException.class, () -> allocateCommand.execute(params));
  }

  @Test
  public void testExecuteWithInvalidArgs_shouldThrowError() {
    String[] params = {"1", "2", "random"};
    assertThrows(InvalidParameterException.class, () -> allocateCommand.execute(params));
  }

  @Test
  public void testExecuteWithValidArgs_shouldNotThrowError()
      throws InvalidParameterException, MyMoneyException {
    String[] params = {"1", "2", "3"};
    doNothing().when(myMoneyCommandHandler).allocateFunds(anyList());
    assertDoesNotThrow(() -> allocateCommand.execute(params));
  }
}
