package com.saikrishna.mymoney.interaction;

import java.util.HashMap;
import java.util.Map;

import com.saikrishna.mymoney.exception.CommandNotFoundException;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.interaction.commands.BalanceCommand;
import com.saikrishna.mymoney.interaction.commands.ChangeCommand;
import com.saikrishna.mymoney.interaction.commands.Command;
import com.saikrishna.mymoney.interaction.commands.CommandHandler;
import com.saikrishna.mymoney.interaction.commands.RebalanceCommand;
import com.saikrishna.mymoney.interaction.commands.AllocateSIPCommand;

public class CommandFactory {

  private final Map<Command, CommandHandler> commands;

  private CommandFactory() {
    commands = new HashMap<>();
  }

  /**
   * Method Description: initialize Command to commandFactory for the following
   * ALLOCATE, SIP, CHANGE, BALANCE, REBALANCE
   * basic validation on params will be validated in Command execute
   * @param myMoneyCommandHandler
   * @return
   */
  public static CommandFactory init(MyMoneyCommandHandler myMoneyCommandHandler) {
    final CommandFactory commandFactory = new CommandFactory();
    commandFactory.addCommand(Command.ALLOCATE, new AllocateSIPCommand(myMoneyCommandHandler, Command.ALLOCATE));
    commandFactory.addCommand(Command.SIP, new AllocateSIPCommand(myMoneyCommandHandler, Command.SIP));
    commandFactory.addCommand(Command.CHANGE, new ChangeCommand(myMoneyCommandHandler));
    commandFactory.addCommand(Command.BALANCE, new BalanceCommand(myMoneyCommandHandler));
    commandFactory.addCommand(Command.REBALANCE, new RebalanceCommand(myMoneyCommandHandler));
    return commandFactory;
  }

  public void addCommand(Command name, CommandHandler command) {
    commands.put(name, command);
  }

  public void executeCommand(String name, String[] params)
      throws CommandNotFoundException, InvalidParameterException, MyMoneyException,
      InvalidTransactionException {
    Command command = Command.getValue(name);
    if (command != null && commands.containsKey(command)) {
      commands.get(command).execute(params);
    } else {
      throw new CommandNotFoundException(name);
    }
  }

  public Map<Command, CommandHandler> getCommands() {
    return commands;
  }
}
