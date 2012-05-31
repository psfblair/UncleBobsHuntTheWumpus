package HuntTheWumpus.Presentation;

import HuntTheWumpus.GameOverReasons;

import java.util.Set;

public class ResponseModel {
  private int quiver = 0;
  private int arrowsInQuiverBeforeTurn = 0;
  private boolean gameTerminated = false;
  private boolean batTransport = false;
  private boolean canSmellWumpus;
  private boolean canHearPit;
  private boolean canHearBats;
  private Set availableDirections;
  private GameOverReasons gameTerminationReason;

  public int getQuiver() {
    return quiver;
  }

  public void setQuiver(int quiver) {
    this.quiver = quiver;
  }

  public int getArrowsInQuiverBeforeTurn() {
    return arrowsInQuiverBeforeTurn;
  }

  public void setArrowsInQuiverBeforeTurn(int arrowsInQuiverBeforeTurn) {
    this.arrowsInQuiverBeforeTurn = arrowsInQuiverBeforeTurn;
  }

  public boolean isGameTerminated() {
    return gameTerminated;
  }

  public void setGameTerminated(boolean gameTerminated) {
    this.gameTerminated = gameTerminated;
  }

  public boolean isTransportedByBats() {
    return batTransport;
  }

  public void setBatTransport(boolean batTransport) {
    this.batTransport = batTransport;
  }

  public void setCanSmellWumpus(boolean canSmellWumpus) {
    this.canSmellWumpus = canSmellWumpus;
  }

  public boolean canSmellWumpus() {
    return canSmellWumpus;
  }

  public void setCanHearPit(boolean canHearPit) {
    this.canHearPit = canHearPit;
  }

  public boolean canHearPit() {
    return canHearPit;
  }

  public void setCanHearBats(boolean canHearBats) {
    this.canHearBats = canHearBats;
  }

  public boolean canHearBats() {
    return canHearBats;
  }

  public void setAvailableDirections(Set availableDirections) {
    this.availableDirections = availableDirections;
  }

  public Set getAvailableDirections() {
    return availableDirections;
  }

  public void setReasonGameTerminated(GameOverReasons gameTerminationReason) {
    this.gameTerminationReason = gameTerminationReason;
  }

  public GameOverReasons getGameTerminationReason() {
    return gameTerminationReason;
  }
}
