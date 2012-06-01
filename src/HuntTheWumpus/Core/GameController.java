package HuntTheWumpus.Core;

import HuntTheWumpus.Command.CommandInterpreter;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Scenarios.Initialize;
import HuntTheWumpus.Core.Scenarios.Scenario;
import HuntTheWumpus.Core.Output.Output;

public class GameController {

  private final Game game = new Game();
  private Output output;
  private CommandInterpreter interpreter;

  public GameController(CommandInterpreter interpreter, Output output) {
    this.interpreter = interpreter;
    this.output = output;
  }

  public void execute(String commandString) {
    Scenario command = interpreter.getCommand(commandString, game, output);
    command.invoke();
  }

  public void execute(Initialize.InitializationParameters initializationParameters) {
    Initialize initializeCommand = new Initialize(game, output, initializationParameters);
    initializeCommand.invoke();
  }

  public boolean isGameTerminated() {
    return game.isGameTerminated();
  }

  // For testing
  public Game getGame() {
    return game;
  }
}

