package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Presentation.Output;

public class ShootArrow extends Scenario {
  private String direction;

  public ShootArrow(Game game, Output presenter, String direction) {
    super(game, presenter);
    this.direction = direction;
  }

  public void Invoke() {
    if (game.shoot(getDirection()) == false)
      responseModel.setTriedShootingWithNoArrows(true);
    else
      responseModel.setArrowWasShot(true);
    super.Invoke();
  }

  public String getDirection() {
    return direction;
  }
}
