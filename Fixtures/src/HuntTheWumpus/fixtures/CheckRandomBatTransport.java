package HuntTheWumpus.fixtures;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;
import HuntTheWumpus.Core.Scenarios.MovePlayer;

public class CheckRandomBatTransport {
  private int cavern;
  private int counts[] = new int[6];
  private Output output = new Output() {
    public void outputResponse(ResponseModel responseModel) {}
  };

  public CheckRandomBatTransport() {
    GameDriver.game.getGameCaverns().putBatsInCavern(2);

    for (int i=0; i<1000; i++) {
      GameDriver.game.getPlayer().putPlayerInCavern(1);
      MovePlayer movePlayer = new MovePlayer(GameDriver.game, output, Direction.EAST);
      movePlayer.invoke();
      //Also used in Game start
      counts[GameDriver.game.getPlayer().getPlayerCavern()]++;
    }
  }

  public int count() {
    return counts[cavern];
  }

  public void setCavern(int cavern) {
    this.cavern = cavern;
  }
}
