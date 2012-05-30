package HuntTheWumpus.Commands;

public class UnknownCommand implements Command {
  private String command;

  public UnknownCommand(String command) {
    this.command = command;
  }

  public void Dispatch(GameController controller) {
    controller.invoke(this);
  }

  public String getCommandString() {
    return command;
  }
}
