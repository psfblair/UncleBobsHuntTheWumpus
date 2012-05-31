package HuntTheWumpus.Presentation;

import HuntTheWumpus.Game;
import HuntTheWumpus.GameOverReasons;

public class GamePresenter implements Presentation {
  private Console console;

  public GamePresenter(Console console) {
    this.console = console;
  }

  public void printUnknownCommand(String command) {
    console.print("I don't know how to " + command + ".");
  }

  public void printEndOfTurnMessages(ResponseModel responseModel) {
    if (responseModel.isGameTerminated()) {
      printCauseOfTermination(responseModel);
      console.print("Game over.");
    } else {
      printTransportMessage(responseModel);
      printArrowsFound(responseModel);
      printQuiverStatus(responseModel);
      printWumpusOdor(responseModel);
      printPitSounds(responseModel);
      printBatSounds(responseModel);
      printAvailableDirections(responseModel);
    }
  }

  public void printShotArrow() {
    console.print("The arrow flies away in silence.");
  }

  public void printNoArrows() {
    console.print("You don't have any arrows.");
  }

  public void printCannotMove(String direction) {
    console.print("You can't go " + GamePresenter.directionName(direction) + " from here.");
  }

  private void printTransportMessage(ResponseModel responseModel) {
    if (responseModel.isTransportedByBats())
      console.print("A swarm of angry bats has carried you off.");
  }

  private void printBatSounds(ResponseModel responseModel) {
    if (responseModel.canHearBats())
      console.print("You hear chirping.");
  }

  private void printPitSounds(ResponseModel responseModel) {
    if (responseModel.canHearPit())
      console.print("You hear wind.");
  }

  private void printWumpusOdor(ResponseModel responseModel) {
    if (responseModel.canSmellWumpus()) {
      console.print("You smell the Wumpus.");
    }
  }

  private void printQuiverStatus(ResponseModel responseModel) {
    int quiver = responseModel.getQuiver();
    if (quiver == 0)
      console.print("You have no arrows.");
    else if (quiver == 1)
      console.print("You have 1 arrow.");
    else
      console.print("You have " + quiver + " arrows.");
  }

  private void printArrowsFound(ResponseModel responseModel) {
    if (responseModel.getQuiver() > responseModel.getArrowsInQuiverBeforeTurn())
      console.print("You found an arrow.");
  }

  private void printCauseOfTermination(ResponseModel responseModel) {
    if (responseModel.getGameTerminationReason() == GameOverReasons.KILLED_BY_ARROW_BOUNCE)
      console.print("The arrow bounced off the wall and killed you.");
    else if (responseModel.getGameTerminationReason() == GameOverReasons.FELL_IN_PIT)
      console.print("You fall into a pit and die.");
    else if (responseModel.getGameTerminationReason() == GameOverReasons.WUMPUS_HIT_BY_ARROW)
      console.print("You have killed the Wumpus.");
    else if (responseModel.getGameTerminationReason() == GameOverReasons.EATEN_BY_WUMPUS)
      console.print("The ravenous snarling Wumpus gobbles you down.");
    else if (responseModel.getGameTerminationReason() == GameOverReasons.HIT_BY_OWN_ARROW)
      console.print("You were hit by your own arrow.");
  }

  public void printAvailableDirections(ResponseModel responseModel) {
    AvailableDirections availableDirections = new AvailableDirections(responseModel.getAvailableDirections());
    console.print(availableDirections.toString());
  }

  static String directionName(String direction) {
    if (direction.equals(Game.NORTH))
      return "north";
    else if (direction.equals(Game.SOUTH))
      return "south";
    else if (direction.equals(Game.EAST))
      return "east";
    else if (direction.equals(Game.WEST))
      return "west";
    else
      return "tilt";
  }
}
