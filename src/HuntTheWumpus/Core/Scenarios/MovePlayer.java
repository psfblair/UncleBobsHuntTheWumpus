package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Direction;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class MovePlayer extends Scenario {
  private Direction direction;
  private boolean cannotMoveInRequestedDirection = false;

  public MovePlayer(Game game, Output output, Direction direction) {
    super(game, output);
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
