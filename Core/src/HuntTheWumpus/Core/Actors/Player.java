package HuntTheWumpus.Core.Actors;

public class Player {
  public int playerCavern = -1;
  private final GameCaverns caverns;
  private final Wumpus wumpus;
  private int quiver = 0;

  public Player(GameCaverns caverns, Wumpus wumpus) {
    this.caverns = caverns;
    this.wumpus = wumpus;
  }

  public int cavern() {
    return playerCavern;
  }

  public void startInCavern(int cavern) {
    playerCavern = cavern;
  }

  public void moveTo(int destination) {
    playerCavern = destination;
  }

  public boolean isMyCavern(int nextCavern) {
    return nextCavern == playerCavern;
  }

  public void moveToRandomCavern() {
    playerCavern = caverns.getRandomPathStart();
  }

  public boolean isInWumpusCavern() {
    return wumpus.isMyCavern(playerCavern);
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

  public boolean isInCavernWithArrow() {
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

  public void pickUpArrow() {
    caverns.removeArrowFrom(playerCavern);
    quiver++;
  }
}
