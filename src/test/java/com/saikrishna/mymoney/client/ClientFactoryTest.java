package com.saikrishna.mymoney.client;

import java.io.FileNotFoundException;

import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.interaction.CommandFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientFactoryTest {
  private static CommandFactory commandFactory;
  @BeforeAll
  public static void setup() {
    MyMoneyCommandHandler myMoneyCommandHandler = new MyMoneyCommandHandler();
    commandFactory = CommandFactory.init(myMoneyCommandHandler);
  }

  @Test
  public void testBuildClient_without_args_for_CLIClient() throws FileNotFoundException {
    String[] args = {};
    Client client = ClientFactory.buildClient(args, commandFactory);
    assertTrue(client instanceof CLIClient);
  }

  @Test
  public void testBuildClient_with_args_for_FileClient() throws FileNotFoundException {
    String[] args = {"input1.txt"};
    Client client = ClientFactory.buildClient(args, commandFactory);
    assertTrue(client instanceof FileClient);
  }

  @Test
  public void testBuildClient_with_args_for_FileClient_invalidFile() {
    String[] args = {"invalid-input1.txt"};
    assertThrows(FileNotFoundException.class, () -> ClientFactory.buildClient(args, commandFactory));
  }
}
