package HuntTheWumpus.Command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleInputHandler implements TextInputHandler {

  private BufferedReader bufferedReader;

  public ConsoleInputHandler() {
    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public String readLine() throws Exception {
    return bufferedReader.readLine();
  }
}
