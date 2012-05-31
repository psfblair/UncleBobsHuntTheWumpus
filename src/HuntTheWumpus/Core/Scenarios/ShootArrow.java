package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class ShootArrow extends Scenario {
  private String direction;
  private boolean arrowWasShot = false;

  public ShootArrow(Game game, Output presenter, String direction) {
    super(game, presenter);
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
