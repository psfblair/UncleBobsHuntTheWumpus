package HuntTheWumpus.Core;

public class Player {
  public int playerCavern = -1;
  private final GameCaverns caverns;
  private final Wumpus wumpus;
  private int quiver = 0;

  public Player(GameCaverns caverns, Wumpus wumpus) {
    this.caverns = caverns;
    this.wumpus = wumpus;
  }

  public int getPlayerCavern() { //Also used in Game start
    return playerCavern;
  }

  public void putPlayerInCavern(int cavern) {
    playerCavern = cavern;
  }

  boolean isPlayerCavern(int nextCavern) {
    return nextCavern == playerCavern;
  }

  void putPlayerInRandomCavern() {
    playerCavern = caverns.getRandomPathStart();
  }

  boolean isInWumpusCavern() {
    return wumpus.isWumpusCavern(playerCavern);
  }

  public boolean isInCavernNextToWumpus() {
    return wumpus.isAdjacentTo(playerCavern);
  }

  public boolean isInCavernWithPit() {
    return caverns.isCavernWithPit(playerCavern);
  }

  public boolean isInCavernNextToPit() {
    return caverns.isCavernNextToPit(playerCavern);
  }

  public boolean isInCavernWithBats() {
    return caverns.isCavernWithBats(playerCavern);
  }

  public boolean isInCavernNextToBats() {
    return caverns.isCavernNextToBats(playerCavern);
  }

  public boolean playerIsInCavernWithArrow() {
    return caverns.isInCavernWithArrow(playerCavern);
  }

  public int getQuiver() {
    return quiver;
  }

  public void setQuiver(int quiver) {
    this.quiver = quiver;
  }

  public void shootArrow() {
    quiver--;
  }

  void pickUpArrow() {
    caverns.removeArrowFrom(playerCavern);
    quiver++;
  }
}
