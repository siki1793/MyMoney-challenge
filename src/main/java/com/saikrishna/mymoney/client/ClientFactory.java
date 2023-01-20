package com.saikrishna.mymoney.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.saikrishna.mymoney.interaction.CommandFactory;

public class ClientFactory {
  /**
   * Method Description: Gives Client based on args passed if args.length==0 then CLIClient else FileClient
   * CLIClient - Takes input from console
   * FileClient - Takes input from file
   * @param args
   * @param commandFactory
   * @return
   * @throws FileNotFoundException
   */
  public static Client buildClient(String[] args, CommandFactory commandFactory)
      throws FileNotFoundException {
    if (args.length == 0) {
      return new CLIClient(new BufferedReader(new InputStreamReader(System.in)), commandFactory);
    } else {
      return new FileClient(new BufferedReader(new FileReader(args[0])), commandFactory);
    }
  }
}
