package HuntTheWumpus.fixtures;

public class CheckRandomWumpusMovement {
  private int carvernCounts[] = new int[6];
  private int cavern;

  public CheckRandomWumpusMovement() throws Exception {
    int wumpusCavern = 2;
    for (int i = 0; i < 1000; i++) {
      GameDriver.game.getWumpus().startInCavern(wumpusCavern);
      GameDriver.inputHandler.setLineToReturn("R");
      GameDriver.gameController.execute(GameDriver.commandInterpreter.getRequest());
      carvernCounts[GameDriver.game.getWumpus().getWumpusCavern()]++;
    }
  }

  public int count() {
    return carvernCounts[cavern];
  }

  public void setCavern(int cavern) {
    this.cavern = cavern;
  }
}
