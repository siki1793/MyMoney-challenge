package com.saikrishna.mymoney.interaction;

import com.saikrishna.mymoney.exception.CommandNotFoundException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.interaction.commands.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandFactoryTests {
  @Test
  public void testInit_shouldInitializeAllCommands() {
    MyMoneyCommandHandler myMoneyCommandHandler = new MyMoneyCommandHandler();
    CommandFactory commandFactory = CommandFactory.init(myMoneyCommandHandler);
    assertTrue(commandFactory.getCommands().keySet().contains(Command.ALLOCATE));
    assertTrue(commandFactory.getCommands().keySet().contains(Command.SIP));
    assertTrue(commandFactory.getCommands().keySet().contains(Command.CHANGE));
    assertTrue(commandFactory.getCommands().keySet().contains(Command.BALANCE));
    assertTrue(commandFactory.getCommands().keySet().contains(Command.REBALANCE));
  }
  @Test
  public void testExecuteInvalidCommand_shouldThrowError() {
    MyMoneyCommandHandler myMoneyCommandHandler = new MyMoneyCommandHandler();
    CommandFactory commandFactory = CommandFactory.init(myMoneyCommandHandler);
    String[] params = {"random"};
    assertThrows(CommandNotFoundException.class, () -> commandFactory.executeCommand("random", params));
  }

  @Test
  public void testExecuteValidCommand_shouldThrowError() {
    MyMoneyCommandHandler myMoneyCommandHandler = new MyMoneyCommandHandler();
    CommandFactory commandFactory = CommandFactory.init(myMoneyCommandHandler);
    String[] params = {"JANUARY"};
    assertThrows(MyMoneyException.class, () -> commandFactory.executeCommand("BALANCE", params));
  }
}
