package HuntTheWumpus;

import HuntTheWumpus.Command.*;
import HuntTheWumpus.Presentation.Presentation;
import HuntTheWumpus.Presentation.ResponseModel;

import java.util.*;

public class Game {
  private int quiver = 0;
  private boolean gameTerminated = false;
  private boolean wumpusFrozen = false;
  private boolean batTransport = false;
  private ResponseModel responseModel;
  public final GameMap gameMap = new GameMap();

  public void invoke(MovePlayer theCommand, Presentation presenter) {
    initializeResponseModel();
    String direction = theCommand.getDirection();
    if (move(direction) == false)
      presenter.printCannotMove(direction);
    output(presenter);
  }

  public void invoke(ShootArrow theCommand, Presentation presenter) {
    initializeResponseModel();
    if (shoot(theCommand.getDirection()) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
    output(presenter);
  }

  public void invoke(Rest theCommand, Presentation presenter) {
    initializeResponseModel();
    rest();
    output(presenter);
  }

  public void invoke(UnknownCommand theCommand, Presentation presenter) {
    initializeResponseModel();
    presenter.printUnknownCommand(theCommand.getCommandString());
    output(presenter);
  }

  public void initializeResponseModel() {
    responseModel = new ResponseModel();
    responseModel.setArrowsInQuiverBeforeTurn(this.quiver);
  }

  private void output(Presentation presenter) {
    ResponseModel responseModel = finalizeResponseModel();
    presenter.printEndOfTurnMessages(responseModel);
  }

  public void rest() {
    moveWumpus();
  }

  public boolean move(String direction) {
    int destination = gameMap.adjacentTo(direction, gameMap.playerCavern);
    if (destination != 0) {
      gameMap.playerCavern = destination;
      checkWumpusEatsPlayer();
      checkForPit();
      checkForBats();
      pickUpArrow();
      moveWumpus();
      return true;
    }
    return false;
  }

  private void checkWumpusEatsPlayer() {
    if (gameMap.playerIsInWumpusCavern()) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(GameOverReasons.EATEN_BY_WUMPUS);
    }
  }

  private void checkForPit() {
    if (gameMap.playerIsInCavernWithPit()) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(GameOverReasons.FELL_IN_PIT);
    }
  }

  private void checkForBats() {
    while (gameMap.playerIsInCavernWithBats()) {
      transportPlayer();
      batTransport = true;
    }
  }

  private void transportPlayer() {
    gameMap.putPlayerInRandomCavern(this);
  }

  private void pickUpArrow() {
    if (gameMap.arrowIsInCavernWithPlayer()) {
      gameMap.roomNoLongerContainsArrow();
      quiver++;
    }
  }

  public boolean gameTerminated() { // Used by runner as well as tests
    return gameTerminated;
  }

  // FOR TESTING

  public GameMap getGameMap() {
    return gameMap;
  }

  public void setQuiver(int arrows) { //Also used in Game start
    quiver = arrows;
  }

  public void freezeWumpus() {
    wumpusFrozen = true;
  }

  public ResponseModel getResponseModel() {
    return responseModel;
  }

  public void reset() {
    gameTerminated = false;
    batTransport = false;
  }

  public int getQuiver() {
    return quiver;
  }
  // END FOR TESTING
  
  // SHOOTING SCENARIO //
  public boolean shoot(String direction) {
    if (quiver <= 0)
      return false;
    quiver--;
    if (gameMap.adjacentTo(direction, gameMap.playerCavern) == 0) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(GameOverReasons.KILLED_BY_ARROW_BOUNCE);
      return true;
    }

    int endCavern = shootAsFarAsPossible(direction, gameMap.playerCavern);
    if (!gameTerminated) {
      gameMap.putArrowInCavern(endCavern);
      moveWumpus();
    }
    return true;
  }

  private int shootAsFarAsPossible(String direction, int cavern) {
    int nextCavern = gameMap.adjacentTo(direction, cavern);
    if (nextCavern == 0)
      return cavern;
    else {
      if (nextCavern == gameMap.wumpusCavern) {
        responseModel.setReasonGameTerminated(GameOverReasons.WUMPUS_HIT_BY_ARROW);
        gameTerminated = true;
        return nextCavern;
      } else if (nextCavern == gameMap.playerCavern) {
        gameTerminated = true;
        responseModel.setReasonGameTerminated(GameOverReasons.HIT_BY_OWN_ARROW);
        return nextCavern;
      }
      return shootAsFarAsPossible(direction, nextCavern);
    }
  }
  // END SHOOTING SCENARIO //

  public void moveWumpus() {
    if (wumpusFrozen)
      return;
    List<Integer> moves = new ArrayList<Integer>();
    addPossibleMove(GameMap.EAST, moves);
    addPossibleMove(GameMap.WEST, moves);
    addPossibleMove(GameMap.NORTH, moves);
    addPossibleMove(GameMap.SOUTH, moves);
    moves.add(0); // rest;

    int selection = (int) (Math.random() * moves.size());
    int selectedMove = moves.get(selection);
    if (selectedMove != 0) {
      gameMap.wumpusCavern = selectedMove;
      checkWumpusEatsPlayer();
    }
  }

  private void addPossibleMove(String dir, List<Integer> moves) {
    int possibleMove;
    possibleMove = gameMap.adjacentTo(dir, gameMap.wumpusCavern);
    if (possibleMove != 0)
      moves.add(possibleMove);
  }

  public ResponseModel finalizeResponseModel() {
    ResponseModel model = this.responseModel;

    model.setGameTerminated(gameTerminated);
    model.setQuiver(quiver);

    model.setBatTransport(batTransport);
    batTransport = false;

    model.setAvailableDirections(gameMap.getAvailableDirections(this));

    model.setCanSmellWumpus(gameMap.playerCanSmellWumpus());
    model.setCanHearPit(gameMap.playerCanHearPit());
    model.setCanHearBats(gameMap.playerCanHearBats());
    return model;
  }


}
