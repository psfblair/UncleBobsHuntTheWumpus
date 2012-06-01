package HuntTheWumpus.fixtures;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;
import HuntTheWumpus.Core.Scenarios.MovePlayer;

import static HuntTheWumpus.fixtures.GameDriver.game;

public class CheckRandomBatTransport {
  private int cavern;
  private int counts[] = new int[6];
  private Output output = new Output() {
    public void outputResponse(ResponseModel responseModel) {}
  };

  public CheckRandomBatTransport() {
    game.getGameCaverns().putBatsInCavern(2);

    for (int i=0; i<1000; i++) {
      game.getPlayer().putPlayerInCavern(1);
      MovePlayer movePlayer = new MovePlayer(game, output, Direction.EAST);
      movePlayer.invoke();
      //Also used in Game start
      counts[game.getPlayer().getPlayerCavern()]++;
    }
  }

  public int count() {
    return counts[cavern];
  }

  public void setCavern(int cavern) {
    this.cavern = cavern;
  }
}
