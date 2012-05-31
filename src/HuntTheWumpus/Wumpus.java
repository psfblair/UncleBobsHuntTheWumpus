package HuntTheWumpus;

public class Wumpus {
  public int wumpusCavern = -1;
  private GameCaverns gameCaverns;

  public Wumpus(GameCaverns gameCaverns) {
    this.gameCaverns = gameCaverns;
  }

  public int getWumpusCavern() {
    return wumpusCavern;
  }

  public void putWumpusInCavern(int where) {
    wumpusCavern = where;
  }

  boolean isWumpusCavern(int cavern) {
    return cavern == wumpusCavern;
  }

  public boolean isAdjacentTo(int cavern) {
    return gameCaverns.areAdjacent(cavern, wumpusCavern);
  }

  void moveWumpusTo(int selectedMove) {
    wumpusCavern = selectedMove;
  }

}
