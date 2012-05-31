package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;

public class Rest extends Scenario {

  public Rest(Game game, Output presenter) {
    super(game, presenter);
  }

  public void Invoke() {
    game.rest();
    super.Invoke();
  }
}
