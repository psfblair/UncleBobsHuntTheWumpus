package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Presentation.Presentation;

public class Rest extends Command {

  public Rest(Game game, Presentation presenter) {
    super(game, presenter);
  }

  public void Invoke() {
    game.rest();
    super.Invoke();
  }
}
