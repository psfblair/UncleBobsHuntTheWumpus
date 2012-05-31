package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output;

public class UnknownCommand extends Scenario {
  private String command;

  public UnknownCommand(Game game, Output presenter, String command) {
    super(game, presenter);
    this.command = command;
  }

  public void Invoke() {
    responseModel.setUnknownCommand(getCommandString());
    super.Invoke();
  }

  public String getCommandString() {
    return command;
  }
}
