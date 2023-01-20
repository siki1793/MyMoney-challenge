package com.saikrishna.mymoney;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.saikrishna.mymoney.client.Client;
import com.saikrishna.mymoney.client.ClientFactory;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.interaction.CommandFactory;

public class MyMoneyApplication {
  public static void main(String[] args) {
    MyMoneyCommandHandler myMoneyCommandHandler = new MyMoneyCommandHandler();
    CommandFactory commandFactory = CommandFactory.init(myMoneyCommandHandler);
    try {
      Client client = ClientFactory.buildClient(args, commandFactory);
      client.handleInput();
    } catch (FileNotFoundException ex) {
      System.out.println("Sorry! the supplied input file was not found!");
    } catch (IOException ex) {
      System.out.println("Something went wrong. Please try again!");
    }
  }
}