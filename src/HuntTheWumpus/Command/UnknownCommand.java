package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Presentation.Presentation;

public class UnknownCommand extends Command {
  private String command;

  public UnknownCommand(Game game, Presentation presenter, String command) {
    super(game, presenter);
    this.command = command;
  }

  public void Invoke() {
    presenter.printUnknownCommand(getCommandString());
    super.Invoke();
  }

  public String getCommandString() {
    return command;
  }
}
