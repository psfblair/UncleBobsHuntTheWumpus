package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Presentation.Presentation;

public class ShootArrow extends Command {
  private String direction;

  public ShootArrow(Game game, Presentation presenter, String direction) {
    super(game, presenter);
    this.direction = direction;
  }

  public void Invoke() {
    if (game.shoot(getDirection()) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
    super.Invoke();
  }

  public String getDirection() {
    return direction;
  }
}
