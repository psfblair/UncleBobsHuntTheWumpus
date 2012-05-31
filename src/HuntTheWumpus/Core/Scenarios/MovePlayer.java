package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Presentation.Presentation;

public class MovePlayer extends Scenario {
  private String direction;

  public MovePlayer(Game game, Presentation presenter, String direction) {
    super(game, presenter);
    this.direction = direction;
  }

  public void Invoke() {
    String direction = getDirection();
    if (game.move(direction) == false)
      presenter.printCannotMove(direction);
    super.Invoke();
  }

  public String getDirection() {
    return direction;
  }
}
