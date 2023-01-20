package com.saikrishna.mymoney.interaction.commands;

import java.util.List;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.utils.Utils;

public class AllocateCommand implements CommandHandler {

  private final MyMoneyCommandHandler commandHandler;

  public AllocateCommand(MyMoneyCommandHandler commandHandler) {
    this.commandHandler = commandHandler;
  }

  @Override
  public void execute(String[] params) throws InvalidParameterException, MyMoneyException {
    if (params.length != 3) {
      throw new InvalidParameterException("Expected three parameter <allocate>");
    }
    List<Integer> initialFunds = Utils.convertToIntegerList(params);
    this.commandHandler.allocateFunds(initialFunds);
  }
}
