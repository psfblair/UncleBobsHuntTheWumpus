package HuntTheWumpus.Commands;

import HuntTheWumpus.CommandInterpreter.CommandInterpreter;
import HuntTheWumpus.Console;
import HuntTheWumpus.Game;
import HuntTheWumpus.GamePresenter;
import HuntTheWumpus.PresentationBoundary;

public class GameController {

  private final Game game = new Game();
  private PresentationBoundary presenter;
  private CommandInterpreter interpreter;

  public GameController(Console console, CommandInterpreter interpreter) {
    this.interpreter = interpreter;
    presenter = new GamePresenter(console);
  }

  public void execute(String commandString) {
    Command command = interpreter.getCommand(commandString);
    command.Dispatch(this);
  }

  void invoke(MovePlayer theCommand) {
    game.invoke(theCommand, presenter);
  }

  void invoke(ShootArrow theCommand) {
    game.invoke(theCommand, presenter);
  }

  void invoke(Rest theCommand) {
    game.invoke(theCommand, presenter);
  }

  void invoke(UnknownCommand theCommand) {
    game.invoke(theCommand, presenter);
  }

  // TODO - shouldn't need this.
  public Game getGame() {
    return game;
  }

}

