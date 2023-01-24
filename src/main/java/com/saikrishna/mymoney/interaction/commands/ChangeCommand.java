package com.saikrishna.mymoney.interaction.commands;

import java.util.List;

import com.saikrishna.mymoney.constants.Constants;
import com.saikrishna.mymoney.constants.Month;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.utils.Utils;

public class ChangeCommand implements CommandHandler {
  private final MyMoneyCommandHandler commandHandler;

  public ChangeCommand(MyMoneyCommandHandler commandHandler) {
    this.commandHandler = commandHandler;
  }

  @Override
  public void execute(String[] params)
      throws InvalidParameterException, MyMoneyException, InvalidTransactionException {
    if (params.length != Constants.FOUR_PARAMS) {
      throw new InvalidParameterException("Expected four parameter <Change>");
    }
    Month month = Month.getMonth(params[params.length-1]);
    if (month == null) {
      throw new InvalidParameterException("Expected one parameter <Month>");
    }
    List<Double> changePercentage = Utils.convertToDoubleList(params, params.length-1);
    this.commandHandler.changePercentageByMonth(changePercentage, month);
  }
}
