package HuntTheWumpus;

public class Player {
  public int playerCavern = -1;
  private final GameCaverns caverns;
  private final Wumpus wumpus;

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

  boolean playerIsInWumpusCavern() {
    return wumpus.isWumpusCavern(playerCavern);
  }

  public boolean playerIsInCavernNextToWumpus() {
    return wumpus.isAdjacentTo(playerCavern);
  }

  boolean playerIsInCavernWithPit() {
    return caverns.isCavernWithPit(playerCavern);
  }

  public boolean playerIsInCavernNextToPit() {
    return caverns.isCavernNextToPit(playerCavern);
  }

  boolean playerIsInCavernWithBats() {
    return caverns.isCavernWithBats(playerCavern);
  }

  public boolean playerIsInCavernNextToBats() {
    return caverns.isCavernNextToBats(playerCavern);
  }

  boolean playerIsInCavernWithArrow() {
    return caverns.isInCavernWithArrow(playerCavern);
  }

  // TODO - want to add to player's quiver too here, not just remove from room
  void pickUpArrow() {
    caverns.removeArrowFrom(playerCavern);
  }
}
