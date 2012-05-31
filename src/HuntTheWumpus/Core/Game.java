package HuntTheWumpus.Core;

public class Game {
  public final GameCaverns gameCaverns = new GameCaverns();
  public final Wumpus wumpus = new Wumpus(gameCaverns);
  public final Player player = new Player(gameCaverns, wumpus);
  private GameOverReasons gameTerminationReason = null;
  private boolean batTransport = false;

  public void rest() {
    wumpusMoves();
  }

  public void wumpusMoves() {
    wumpus.move();
    checkIfWumpusEatsPlayer();
  }

  public boolean move(String direction) {
    int destination = gameCaverns.adjacentTo(direction, player.getPlayerCavern());
    if (destination != 0) {
      player.putPlayerInCavern(destination);
      checkIfWumpusEatsPlayer();
      checkIfPlayerFallsIntoPit();
      checkIfPlayerIsTransportedByBats();
      pickUpArrow();
      wumpusMoves();
      return true;
    }
    return false;
  }

  private void checkIfWumpusEatsPlayer() {
    if (player.isInWumpusCavern()) {
      gameTerminationReason = GameOverReasons.EATEN_BY_WUMPUS;
    }
  }

  private void checkIfPlayerFallsIntoPit() {
    if (player.isInCavernWithPit()) {
      gameTerminationReason = GameOverReasons.FELL_IN_PIT;
    }
  }

  private void checkIfPlayerIsTransportedByBats() {
    while (player.isInCavernWithBats()) {
      transportPlayer();
      batTransport = true;
    }
  }

  private void transportPlayer() {
    player.putPlayerInRandomCavern();
  }

  private void pickUpArrow() {
    if (player.playerIsInCavernWithArrow()) {
      player.pickUpArrow();

    }
  }

  public boolean gameTerminated() {
    return gameTerminationReason != null;
  }

  public GameOverReasons gameTerminationReason() {
    return gameTerminationReason;
  }

  public boolean isTransportedByBats() {
    return batTransport;
  }

  public void resetBatTransport() {
    batTransport = false;
  }

  public boolean shoot(String direction) {
    if (player.getQuiver() <= 0)
      return false;
    player.shootArrow();
    if (gameCaverns.thereIsAWallInDirectionFromCavern(direction, player.getPlayerCavern())) {
      gameTerminationReason = GameOverReasons.KILLED_BY_ARROW_BOUNCE;
      return true;
    }

    int endCavern = shootAsFarAsPossible(direction, player.getPlayerCavern());
    if (!gameTerminated()) {
      gameCaverns.putArrowInCavern(endCavern);
      wumpusMoves();
    }
    return true;
  }

  private int shootAsFarAsPossible(String direction, int cavern) {
    int nextCavern = gameCaverns.adjacentTo(direction, cavern);
    if (nextCavern == 0)
      return cavern;
    else {
      if (nextCavern == wumpus.getWumpusCavern()) {
        gameTerminationReason = GameOverReasons.WUMPUS_HIT_BY_ARROW;
        return nextCavern;
      } else if (player.isPlayerCavern(nextCavern)) {
        gameTerminationReason = GameOverReasons.HIT_BY_OWN_ARROW;
        return nextCavern;
      }
      return shootAsFarAsPossible(direction, nextCavern);
    }
  }

  // FOR TESTING
  public GameCaverns getGameCaverns() {
    return gameCaverns;
  }

  public Player getPlayer() {
    return player;
  }

  public Wumpus getWumpus() {
    return wumpus;
  }

  public void reset() {
    gameTerminationReason = null;
    batTransport = false;
  }
  // END FOR TESTING

}
