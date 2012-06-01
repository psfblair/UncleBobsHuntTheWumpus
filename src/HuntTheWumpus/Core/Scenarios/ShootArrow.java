package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class ShootArrow extends Scenario {
  private Direction direction;
  private boolean arrowWasShot = false;

  public ShootArrow(Game game, Output output, Direction direction) {
    super(game, output);
    this.direction = direction;
  }

  @Override
  public void invoke() {
    arrowWasShot = game.shoot(direction);
    super.invoke();
  }

  @Override
  public ResponseModel prepareResponseModel() {
    responseModel.setArrowWasShot(arrowWasShot);
    responseModel.setTriedShootingWithNoArrows(!arrowWasShot);
    return super.prepareResponseModel();
  }

}
