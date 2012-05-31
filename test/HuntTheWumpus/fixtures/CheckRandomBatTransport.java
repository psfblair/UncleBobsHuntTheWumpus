package HuntTheWumpus.fixtures;

import HuntTheWumpus.GameMap;

import static HuntTheWumpus.fixtures.GameDriver.g;

public class CheckRandomBatTransport {
  private int cavern;
  private int counts[] = new int[6];

  public CheckRandomBatTransport() {
    g.getGameMap().putBatsInCavern(2);

    for (int i=0; i<1000; i++) {
      g.getGameMap().putPlayerInCavern(1);
      g.move(GameMap.EAST);
      counts[g.gameMap.playerCavern(g)]++;
    }
  }

  public int count() {
    return counts[cavern];
  }

  public void setCavern(int cavern) {
    this.cavern = cavern;
  }
}
