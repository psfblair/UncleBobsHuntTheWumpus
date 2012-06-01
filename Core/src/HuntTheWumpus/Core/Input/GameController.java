package HuntTheWumpus.Core.Input;

import HuntTheWumpus.Core.Constants.Scenarios;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Scenarios.*;

public class GameController {

  private final Game game = new Game();
  private Output output;

  public GameController(Output output) {
    this.output = output;
  }

  public void execute(RequestModel requestModel) {
    Scenario scenario;
    if(requestModel.requestedScenarioIs(Scenarios.REST)) {
      scenario = new Rest(game, output);
    } else if (requestModel.requestedScenarioIs(Scenarios.MOVE)) {
      scenario = new MovePlayer(game, output, requestModel.getDirection());
    } else if (requestModel.requestedScenarioIs(Scenarios.SHOOT)) {
      scenario = new ShootArrow(game, output, requestModel.getDirection());
    } else {
      scenario = new UnknownCommand(game, output, requestModel.getUnknownCommand());
    }
    scenario.invoke();
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

