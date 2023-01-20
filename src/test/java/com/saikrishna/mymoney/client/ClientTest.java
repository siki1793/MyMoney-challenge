package com.saikrishna.mymoney.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringReader;

import com.saikrishna.mymoney.exception.CommandNotFoundException;
import com.saikrishna.mymoney.exception.InvalidParameterException;
import com.saikrishna.mymoney.exception.InvalidTransactionException;
import com.saikrishna.mymoney.exception.MyMoneyException;
import com.saikrishna.mymoney.handler.MyMoneyCommandHandler;
import com.saikrishna.mymoney.interaction.CommandFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ClientTest {
  private static CommandFactory commandFactory;

  @BeforeAll
  public static void setup() {
    commandFactory = mock(CommandFactory.class);
  }

  @Test
  public void handleInput_shouldHandleInput() {
    Client client = new CLIClient(new BufferedReader(new StringReader("exit")), commandFactory);
    assertDoesNotThrow(() -> client.handleInput());
  }

  @Test
  public void handleInput_shouldThrowError_CommandNotFoundException()
      throws InvalidParameterException, MyMoneyException, InvalidTransactionException,
      CommandNotFoundException, FileNotFoundException {
    doThrow(CommandNotFoundException.class).when(commandFactory).executeCommand(any(), any());
    Client client = new CLIClient(new BufferedReader(new StringReader("random")), commandFactory);
    assertDoesNotThrow(() -> client.handleInput());
  }

  @Test
  public void handleInput_shouldThrowError_MyMoneyException()
      throws InvalidParameterException, MyMoneyException, InvalidTransactionException,
      CommandNotFoundException, FileNotFoundException {
    doThrow(MyMoneyException.class).when(commandFactory).executeCommand(any(), any());
    Client client = new CLIClient(new BufferedReader(new StringReader("random")), commandFactory);
    assertDoesNotThrow(() -> client.handleInput());
  }

  @Test
  public void handleInput_shouldThrowError_InvalidTransactionException()
      throws InvalidParameterException, MyMoneyException, InvalidTransactionException,
      CommandNotFoundException, FileNotFoundException {
    doThrow(InvalidTransactionException.class).when(commandFactory).executeCommand(any(), any());
    Client client = new CLIClient(new BufferedReader(new StringReader("random")), commandFactory);
    assertDoesNotThrow(() -> client.handleInput());
  }
}
