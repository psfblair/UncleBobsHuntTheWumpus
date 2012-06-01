package HuntTheWumpus.fixtures;

import static HuntTheWumpus.fixtures.GameDriver.game;
import static HuntTheWumpus.fixtures.GameDriver.gameController;

public class CheckRandomWumpusMovement {
  private int carvernCounts[] = new int[6];
  private int cavern;

  public CheckRandomWumpusMovement() {
    int wumpusCavern = 2;
    for (int i = 0; i < 1000; i++) {
      game.getWumpus().putWumpusInCavern(wumpusCavern);
      gameController.execute("R");
      carvernCounts[game.getWumpus().getWumpusCavern()]++;
    }
  }

  public int count() {
    return carvernCounts[cavern];
  }

  public void setCavern(int cavern) {
    this.cavern = cavern;
  }
}
