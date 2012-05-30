package HuntTheWumpus;

import HuntTheWumpus.CommandInterpreter.CommandInterpreter;
import HuntTheWumpus.Commands.Command;
import HuntTheWumpus.Commands.MovePlayer;
import HuntTheWumpus.Commands.Rest;
import HuntTheWumpus.Commands.ShootArrow;

public class GameController {

  private final Game game = new Game();;
  private PresentationBoundary presenter;
  private CommandInterpreter interpreter;

  public GameController(Console console, CommandInterpreter interpreter) {
    this.interpreter = interpreter;
    presenter = new GamePresenter(console, game);
  }

  public boolean execute(String commandString) {
    boolean valid = true;
    int quiver = game.getQuiver();
    Command theCommand = interpreter.getCommand(commandString);
    if (isShootCommand(theCommand))
      shootArrow( ((ShootArrow) theCommand).getDirection()  );
    else if (isGoCommand(theCommand))
      movePlayer( ((MovePlayer) theCommand).getDirection()  );
    else if (isRestCommand(theCommand)) {
      game.rest();
    } else {
      presenter.printUnknownCommand(commandString);
      valid = false;
    }

    presenter.printEndOfTurnMessages(quiver);
    return valid;
  }

  private boolean isShootCommand(Command command) {
    return command instanceof ShootArrow;
  }

  private boolean isGoCommand(Command command) {
    return command instanceof MovePlayer;
  }
  private boolean isRestCommand(Command command) {
    return command instanceof Rest;
  }

  private void shootArrow(String direction) {
    if (game.shoot(direction) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
  }

  private void movePlayer(String direction) {
    if (game.move(direction) == false)
      presenter.printCannotMove(direction);
  }

  public Game getGame() {
    return game;
  }

}

