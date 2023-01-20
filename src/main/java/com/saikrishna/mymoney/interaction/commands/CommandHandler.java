package com.saikrishna.mymoney.interaction.commands;

import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;

public interface CommandHandler {
  void execute(String[] params)
      throws InvalidParameterException, MyMoneyException, InvalidTransactionException;
}
