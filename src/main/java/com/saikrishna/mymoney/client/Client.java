package com.saikrishna.mymoney.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import com.saikrishna.mymoney.exception.CommandNotFoundException;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.interaction.CommandFactory;

public class Client {
  private final BufferedReader inputReader;
  private final CommandFactory commandFactory;

  public Client(BufferedReader inputReader, CommandFactory commandFactory) {
    this.inputReader = inputReader;
    this.commandFactory = commandFactory;
  }

  public void handleInput() throws IOException {
    try {
      String inputLine = null;
      while ((inputLine = this.inputReader.readLine()) != null) {
        inputLine = inputLine.trim();
        if ("exit".equals(inputLine)) {break;}
        processInputLine(inputLine);
      }
    } finally {
      inputReader.close();
    }
  }

  /**
   * Method Description: commands and parameters will be passed to commandFactory.executeCommand()
   * Example:
   *  ALLOCATE 8000 6000 3500
   *  BALANCE MARCH
   * @param inputLine
   */
  private void processInputLine(String inputLine) {
    String[] inputChunks = inputLine.split(" ");
    String command = inputChunks[0];
    String[] params = Arrays.copyOfRange(inputChunks, 1, inputChunks.length);
    try {
      commandFactory.executeCommand(command, params);
    } catch (CommandNotFoundException | InvalidParameterException ex) {
      System.out.println("Error: " + ex.getMessage());
    } catch (MyMoneyException e) {
      System.out.println("Error: " + e.getMessage());
    } catch (InvalidTransactionException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
