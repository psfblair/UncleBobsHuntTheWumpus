package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class UnknownCommand extends Scenario {
  private String command;

  public UnknownCommand(Game game, Output presenter, String command) {
    super(game, presenter);
    this.command = command;
  }

  @Override
  public ResponseModel prepareResponseModel() {
    responseModel.setUnknownCommand(command);
    return super.prepareResponseModel();
  }
}
