package com.saikrishna.mymoney.interaction.commands;

import java.util.List;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.utils.Utils;

public class SIPCommand implements CommandHandler {
  private final MyMoneyCommandHandler commandHandler;

  public SIPCommand(MyMoneyCommandHandler commandHandler) {
    this.commandHandler = commandHandler;
  }

  @Override
  public void execute(String[] params) throws InvalidParameterException, MyMoneyException {
    if (params.length != 3) {
      throw new InvalidParameterException("Expected three parameter <SIP>");
    }
    List<Integer> sipFunds = Utils.convertToIntegerList(params);
    this.commandHandler.setSIP(sipFunds);
  }
}
