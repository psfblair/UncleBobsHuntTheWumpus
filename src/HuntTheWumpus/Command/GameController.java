package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Scenarios.Command;
import HuntTheWumpus.Presentation.Console;
import HuntTheWumpus.Presentation.GamePresenter;
import HuntTheWumpus.Presentation.Presentation;

public class GameController {

  private final Game game = new Game();
  private Presentation presenter;
  private CommandInterpreter interpreter;

  public GameController(Console console, CommandInterpreter interpreter) {
    this.interpreter = interpreter;
    presenter = new GamePresenter(console);
  }

  public void execute(String commandString) {
    Command command = interpreter.getCommand(commandString, game, presenter);
    command.Invoke();
  }

  // TODO - shouldn't need this.
  public Game getGame() {
    return game;
  }

  public Presentation getPresenter() {
    return presenter;
  }
}

