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
    presenter = new GamePresenter(console, game);
  }

  public void execute(String commandString) {
    Command command = interpreter.getCommand(commandString);
    command.Dispatch(this);
  }

  void invoke(UnknownCommand theCommand) {
    int quiver = game.getQuiver();
    presenter.printUnknownCommand(theCommand.getCommandString());
    presenter.printEndOfTurnMessages(quiver);
  }

  void invoke(Rest theCommand) {
    int quiver = game.getQuiver();
    game.rest();
    presenter.printEndOfTurnMessages(quiver);
  }

  void invoke(MovePlayer theCommand) {
    int quiver = game.getQuiver();
    String direction = theCommand.getDirection();
    if (game.move(direction) == false)
      presenter.printCannotMove(direction);
    presenter.printEndOfTurnMessages(quiver);
  }

  void invoke(ShootArrow theCommand) {
    int quiver = game.getQuiver();
    if (game.shoot(theCommand.getDirection()) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
    presenter.printEndOfTurnMessages(quiver);
  }

  public Game getGame() {
    return game;
  }

}

