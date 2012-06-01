package HuntTheWumpus.Presentation;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.GameOverReason;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

import java.util.Set;

public class TextPresenter implements Output {
  private Console console;

  public TextPresenter(Console console) {
    this.console = console;
  }

  public void outputResponse(ResponseModel responseModel) {
    printShotArrow(responseModel);
    printCannotMove(responseModel);

    if (responseModel.isGameTerminated()) {
      printCauseOfTermination(responseModel);
      console.print("Game over.");
      return;
    }

    printUnknownCommand(responseModel);
    printTransportMessage(responseModel);
    printArrowsFound(responseModel);
    printQuiverStatus(responseModel);
    printWumpusOdor(responseModel);
    printPitSounds(responseModel);
    printBatSounds(responseModel);
    printAvailableDirections(responseModel);
  }

  public void printUnknownCommand(ResponseModel responseModel) {
    String command = responseModel.unknownCommand();
    if (command != null) {
      console.print("I don't know how to " + command + ".");
    }
  }
  public void printShotArrow(ResponseModel responseModel) {
    if (responseModel.arrowWasShot()) {
      console.print("The arrow flies away in silence.");
    } else if (responseModel.triedShootingWithNoArrows()) {
      console.print("You don't have any arrows.");
    }
  }

  public void printCannotMove(ResponseModel responseModel) {
    if (responseModel.cannotMoveInRequestedDirection()) {
      Direction direction = responseModel.requestedDirection();
      console.print("You can't go " + TextPresenter.directionName(direction) + " from here.");
    }
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
    if (responseModel.getGameTerminationReason() == GameOverReason.KILLED_BY_ARROW_BOUNCE)
      console.print("The arrow bounced off the wall and killed you.");
    else if (responseModel.getGameTerminationReason() == GameOverReason.FELL_IN_PIT)
      console.print("You fall into a pit and die.");
    else if (responseModel.getGameTerminationReason() == GameOverReason.WUMPUS_HIT_BY_ARROW)
      console.print("You have killed the Wumpus.");
    else if (responseModel.getGameTerminationReason() == GameOverReason.EATEN_BY_WUMPUS)
      console.print("The ravenous snarling Wumpus gobbles you down.");
    else if (responseModel.getGameTerminationReason() == GameOverReason.HIT_BY_OWN_ARROW)
      console.print("You were hit by your own arrow.");
  }

  public void printAvailableDirections(ResponseModel responseModel) {
    AvailableDirectionsMessages availableDirections = new AvailableDirectionsMessages(responseModel.getAvailableDirections());
    console.print(availableDirections.toString());
  }

  static String directionName(Direction direction) {
    if (direction.equals(Direction.NORTH))
      return "north";
    else if (direction.equals(Direction.SOUTH))
      return "south";
    else if (direction.equals(Direction.EAST))
      return "east";
    else if (direction.equals(Direction.WEST))
      return "west";
    else
      return "tilt";
  }

  static class AvailableDirectionsMessages {
    private Set<String> directions;
    private int nDirections;
    private StringBuffer available;
    private int directionsPlaced;

    AvailableDirectionsMessages(Set<String> directions) {
      this.directions = directions;
    }

    public String toString() {
      if (directions.isEmpty())
        return "There are no exits!";
      else {
        return assembleDirections();
      }
    }

    private String assembleDirections() {
      available = new StringBuffer();
      nDirections = directions.size();
      directionsPlaced = 0;
      for (Direction dir : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
        if (directions.contains(dir)) {
          placeDirection(dir);
        }
      }
      return "You can go " + available.toString() + " from here.";
    }

    private void placeDirection(Direction dir) {
      directionsPlaced++;
      if (isLastOfMany())
        available.append(" and ");
      else if (notFirst())
        available.append(", ");
      available.append(directionName(dir));
    }

    private boolean notFirst() {return directionsPlaced > 1;}

    private boolean isLastOfMany() {return nDirections > 1 && directionsPlaced == nDirections;}

  }
}
