package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;

public class Rest extends Scenario {

  public Rest(Game game, Output output) {
    super(game, output);
  }

  @Override
  public void invoke() {
    wumpusMoves();
    super.invoke();
  }
}
