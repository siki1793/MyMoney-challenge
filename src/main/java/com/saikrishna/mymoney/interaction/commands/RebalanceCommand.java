package com.saikrishna.mymoney.interaction.commands;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;

public class RebalanceCommand implements CommandHandler {
  private final MyMoneyCommandHandler commandHandler;

  public RebalanceCommand(MyMoneyCommandHandler commandHandler) {
    this.commandHandler = commandHandler;
  }

  @Override
  public void execute(String[] params) throws MyMoneyException, InvalidParameterException {
    this.commandHandler.rebalance();
  }
}
