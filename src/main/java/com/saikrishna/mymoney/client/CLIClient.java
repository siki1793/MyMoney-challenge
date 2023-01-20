package com.saikrishna.mymoney.client;

import java.io.BufferedReader;
import java.io.PrintStream;

import com.saikrishna.mymoney.interaction.CommandFactory;

public class CLIClient extends Client {
  public CLIClient(BufferedReader inputReader, CommandFactory commandFactory) {
    super(inputReader, commandFactory);
  }
}
