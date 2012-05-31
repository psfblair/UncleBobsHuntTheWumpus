package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Scenarios.Scenario;
import HuntTheWumpus.Presentation.Console;
import HuntTheWumpus.Presentation.TextPresenter;
import HuntTheWumpus.Core.Output.Output;

public class GameController {

  private final Game game = new Game();
  private Output presenter;
  private CommandInterpreter interpreter;

  public GameController(Console console, CommandInterpreter interpreter) {
    this.interpreter = interpreter;
    presenter = new TextPresenter(console);
  }

  public void execute(String commandString) {
    Scenario command = interpreter.getCommand(commandString, game, presenter);
    command.invoke();
  }

  // TODO - shouldn't need this.
  public Game getGame() {
    return game;
  }

  public Output getPresenter() {
    return presenter;
  }
}

