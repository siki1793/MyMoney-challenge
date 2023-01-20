package com.saikrishna.mymoney.interaction.commands;

public enum Command {
  ALLOCATE,
  SIP,
  CHANGE,
  BALANCE,
  REBALANCE;

  public static Command getValue(String value) {
    try {
      return Command.valueOf(value);
    } catch (Exception ex) {
      return null;
    }
  }
}
