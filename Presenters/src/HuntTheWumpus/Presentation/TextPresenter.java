package HuntTheWumpus.Presentation;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.GameOverReason;
import HuntTheWumpus.Core.Output.ResponseModel;

import java.util.Set;

public abstract class TextPresenter extends Presenter {

  // Poor man's registration. Receive instance class name created by static initializers on the implementation class.
  public static String textDisplayClassName;

  TextDisplay textDisplay;

  public TextPresenter() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    textDisplay = (TextDisplay) Class.forName(textDisplayClassName).newInstance();
  }

  public void setDisplayForTesting(TextDisplay display)
  {
    textDisplay = display;
  }

  protected abstract String unknownCommandResponse(String command);
  protected abstract String arrowShotResponse();
  protected abstract String noArrowsResponse();
  protected abstract String cannotMoveResponse(Direction direction);
  protected abstract String batTransportResponse();
  protected abstract String batSoundsResponse();
  protected abstract String pitSoundsResponse();
  protected abstract String wumpusOdorResponse();
  protected abstract String quiverStatusNoArrowsResponse();
  protected abstract String quiverStatusOneArrowResponse();
  protected abstract String quiverStatusCountResponse(int quiverCount);
  protected abstract String foundArrowResponse();
  protected abstract String killedByArrowBounceResponse();
  protected abstract String fellInPitResponse();
  protected abstract String killedWumpusResponse();
  protected abstract String killedByWumpusResponse();
  protected abstract String hitByOwnArrowResponse();
  protected abstract String gameOverResponse();
  protected abstract String northDirectionName();
  protected abstract String southDirectionName();
  protected abstract String eastDirectionName();
  protected abstract String westDirectionName();
  protected abstract String unknownDirectionName();

  protected abstract String availableDirectionsResponse(StringBuffer available);
  protected abstract String noExitsResponse();
  protected abstract String and();
  protected abstract String comma();


  public void printUnknownCommand(ResponseModel responseModel) {
    String command = responseModel.unknownCommand();
    if (command != null) {
      textDisplay.print(unknownCommandResponse(command));
    }
  }

  public void printCannotMove(ResponseModel responseModel) {
    if (responseModel.cannotMoveInRequestedDirection()) {
      Direction direction = responseModel.requestedDirection();
      this.textDisplay.print(cannotMoveResponse(direction));
    }
  }

  public void printShotArrow(ResponseModel responseModel) {
    if (responseModel.arrowWasShot()) {
      this.textDisplay.print(arrowShotResponse());
    } else if (responseModel.triedShootingWithNoArrows()) {
      this.textDisplay.print(noArrowsResponse());
    }
  }

  protected void printTransportMessage(ResponseModel responseModel) {
    if (responseModel.isTransportedByBats())
      this.textDisplay.print(batTransportResponse());
  }

  protected void printBatSounds(ResponseModel responseModel) {
    if (responseModel.canHearBats())
      this.textDisplay.print(batSoundsResponse());
  }

  protected void printPitSounds(ResponseModel responseModel) {
    if (responseModel.canHearPit())
      this.textDisplay.print(pitSoundsResponse());
  }

  protected void printWumpusOdor(ResponseModel responseModel) {
    if (responseModel.canSmellWumpus()) {
      this.textDisplay.print(wumpusOdorResponse());
    }
  }

  protected void printQuiverStatus(ResponseModel responseModel) {
    int quiver = responseModel.getQuiver();
    if (quiver == 0)
      this.textDisplay.print(quiverStatusNoArrowsResponse());
    else if (quiver == 1)
      this.textDisplay.print(quiverStatusOneArrowResponse());
    else
      this.textDisplay.print(quiverStatusCountResponse(quiver));
  }

  protected void printArrowsFound(ResponseModel responseModel) {
    if (responseModel.getQuiver() > responseModel.getArrowsInQuiverBeforeTurn())
      this.textDisplay.print(foundArrowResponse());
  }

  protected void printCauseOfTermination(ResponseModel responseModel) {
    if (responseModel.getGameTerminationReason() == GameOverReason.KILLED_BY_ARROW_BOUNCE)
      this.textDisplay.print(killedByArrowBounceResponse());
    else if (responseModel.getGameTerminationReason() == GameOverReason.FELL_IN_PIT)
      this.textDisplay.print(fellInPitResponse());
    else if (responseModel.getGameTerminationReason() == GameOverReason.WUMPUS_HIT_BY_ARROW)
      this.textDisplay.print(killedWumpusResponse());
    else if (responseModel.getGameTerminationReason() == GameOverReason.EATEN_BY_WUMPUS)
      this.textDisplay.print(killedByWumpusResponse());
    else if (responseModel.getGameTerminationReason() == GameOverReason.HIT_BY_OWN_ARROW)
      this.textDisplay.print(hitByOwnArrowResponse());
  }

  protected void printGameOver() {
    this.textDisplay.print(gameOverResponse());
  }

  public void printAvailableDirections(ResponseModel responseModel) {
    AvailableDirectionsMessages availableDirections = new AvailableDirectionsMessages(responseModel.getAvailableDirections());
    this.textDisplay.print(availableDirections.toString());
  }

  protected String directionName(Direction direction) {
    if (direction.equals(Direction.NORTH))
      return northDirectionName();
    else if (direction.equals(Direction.SOUTH))
      return southDirectionName();
    else if (direction.equals(Direction.EAST))
      return eastDirectionName();
    else if (direction.equals(Direction.WEST))
      return westDirectionName();
    else
      return unknownDirectionName();
  }

  class AvailableDirectionsMessages {
    private Set<String> directions;
    private int nDirections;
    private StringBuffer available;
    private int directionsPlaced;

    AvailableDirectionsMessages(Set<String> directions) {
      this.directions = directions;
    }

    public String toString() {
      if (directions.isEmpty())
        return noExitsResponse();
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
      return availableDirectionsResponse(available);
    }

    private void placeDirection(Direction dir) {
      directionsPlaced++;
      if (isLastOfMany())
        available.append(and());
      else if (notFirst())
        available.append(comma());
      available.append(directionName(dir));
    }

    private boolean notFirst() {return directionsPlaced > 1;}

    private boolean isLastOfMany() {return nDirections > 1 && directionsPlaced == nDirections;}
  }
}
