package HuntTheWumpus.fixtures;

import HuntTheWumpus.Core.Direction;

import static HuntTheWumpus.fixtures.GameDriver.g;

public class CheckRandomBatTransport {
  private int cavern;
  private int counts[] = new int[6];

  public CheckRandomBatTransport() {
    g.getGameCaverns().putBatsInCavern(2);

    for (int i=0; i<1000; i++) {
      g.getPlayer().putPlayerInCavern(1);
      g.move(Direction.EAST);
      //Also used in Game start
      counts[g.getPlayer().getPlayerCavern()]++;
    }
  }

  public int count() {
    return counts[cavern];
  }

  public void setCavern(int cavern) {
    this.cavern = cavern;
  }
}
