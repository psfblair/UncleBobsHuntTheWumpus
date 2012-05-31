package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class MovePlayer extends Scenario {
  private String direction;
  private boolean cannotMoveInRequestedDirection = false;

  public MovePlayer(Game game, Output presenter, String direction) {
    super(game, presenter);
    this.direction = direction;
  }

  @Override
  public void invoke() {
    if (game.move(direction) == false) {
      cannotMoveInRequestedDirection = true;
    }
    super.invoke();
  }

  @Override
  public ResponseModel prepareResponseModel() {
    responseModel.setRequestedDirection(direction);
    responseModel.setCannotMoveInRequestedDirection(cannotMoveInRequestedDirection);
    return super.prepareResponseModel();
  }
}
