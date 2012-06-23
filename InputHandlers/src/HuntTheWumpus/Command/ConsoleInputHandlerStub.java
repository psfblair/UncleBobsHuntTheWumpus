package HuntTheWumpus.Command;

public class ConsoleInputHandlerStub implements TextInputHandler {

  private String lineToReturn;

  public void setLineToReturn(String line)
  {
    lineToReturn = line;
  }
  public String readLine() throws Exception {
    return lineToReturn;
  }
}
