package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;

public class MovePlayer extends Scenario {
  private String direction;

  public MovePlayer(Game game, Output presenter, String direction) {
    super(game, presenter);
    this.direction = direction;
  }

  public void Invoke() {
    responseModel.setRequestedDirection(direction);
    if (game.move(direction) == false) {
      responseModel.setCannotMoveInRequestedDirection(true);
    }
    super.Invoke();
  }
}
