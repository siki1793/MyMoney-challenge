package com.saikrishna.mymoney.interaction.commands;

import com.saikrishna.mymoney.constants.Constants;
import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;

public class BalanceCommand implements CommandHandler {
  private final MyMoneyCommandHandler commandHandler;

  public BalanceCommand(MyMoneyCommandHandler commandHandler) {
    this.commandHandler = commandHandler;
  }

  @Override
  public void execute(String[] params) throws InvalidParameterException, MyMoneyException {
    if (params.length != Constants.ONE_PARAMS) {
      throw new InvalidParameterException("Expected one parameter <Month>");
    }
    Month month = Month.getMonth(params[0]);
    if (month == null) {
      throw new InvalidParameterException("Expected one parameter <Month>");
    }
    this.commandHandler.balance(month);
  }
}
