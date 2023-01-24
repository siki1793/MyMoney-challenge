package com.saikrishna.mymoney.interaction.commands;

import java.util.List;

import com.saikrishna.mymoney.constants.Constants;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.utils.Utils;

public class AllocateSIPCommand implements CommandHandler {
  private final MyMoneyCommandHandler commandHandler;
  private final Command commandType;

  public AllocateSIPCommand(MyMoneyCommandHandler commandHandler, Command commandType) {
    this.commandHandler = commandHandler;
    this.commandType = commandType;
  }

  @Override
  public void execute(String[] params) throws InvalidParameterException, MyMoneyException {
    if (params.length != Constants.THREE_PARAMS) {
      throw new InvalidParameterException("Expected three parameter <funds>");
    }
    List<Integer> funds = Utils.convertToIntegerList(params);
    if (Command.SIP == commandType) {
      this.commandHandler.setSIP(funds);
    } else {
      this.commandHandler.allocateFunds(funds);
    }
  }
}
