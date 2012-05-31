package HuntTheWumpus;

import HuntTheWumpus.Command.*;
import HuntTheWumpus.Presentation.Presentation;
import HuntTheWumpus.Presentation.ResponseModel;

import java.util.*;

public class Game {
  private boolean gameTerminated = false;

  private boolean batTransport = false;
  private ResponseModel responseModel;
  public final GameCaverns gameCaverns = new GameCaverns();
  public final Wumpus wumpus = new Wumpus(gameCaverns);
  public final Player player = new Player(gameCaverns, wumpus);

  /* TODO remove - just for refactoring */
  public Game() {
    gameCaverns.setPlayer(player);
    gameCaverns.setWumpus(wumpus);
  }

  /* TODO Move to Wumpus */
  private boolean wumpusFrozen = false;

  /* TODO Move to Player */
  private int quiver = 0;

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
    int destination = gameCaverns.adjacentTo(direction, player.getPlayerCavern());
    if (destination != 0) {
      player.putPlayerInCavern(destination);
      checkIfWumpusEatsPlayer();
      checkIfPlayerFallsIntoPit();
      checkIfPlayerIsTransportedByBats();
      pickUpArrow();
      moveWumpus();
      return true;
    }
    return false;
  }

  private void checkIfWumpusEatsPlayer() {
    if (player.isInWumpusCavern()) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(GameOverReasons.EATEN_BY_WUMPUS);
    }
  }

  private void checkIfPlayerFallsIntoPit() {
    if (player.isInCavernWithPit()) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(GameOverReasons.FELL_IN_PIT);
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
    if (gameCaverns.arrowIsInCavernWithPlayer()) {
      gameCaverns.roomNoLongerContainsArrow();
      quiver++;
    }
  }

  public boolean gameTerminated() { // Used by runner as well as tests
    return gameTerminated;
  }

  // FOR TESTING

  public GameCaverns getGameCaverns() {
    return gameCaverns;
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

  public boolean shoot(String direction) {
    if (quiver <= 0)
      return false;
    quiver--;
    if (gameCaverns.thereIsAWallInDirection(direction)) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(GameOverReasons.KILLED_BY_ARROW_BOUNCE);
      return true;
    }

    int endCavern = shootAsFarAsPossible(direction, player.getPlayerCavern());
    if (!gameTerminated) {
      gameCaverns.putArrowInCavern(endCavern);
      moveWumpus();
    }
    return true;
  }

  private int shootAsFarAsPossible(String direction, int cavern) {
    int nextCavern = gameCaverns.adjacentTo(direction, cavern);
    if (nextCavern == 0)
      return cavern;
    else {
      if (gameCaverns.isWumpusCavern(nextCavern)) {
        responseModel.setReasonGameTerminated(GameOverReasons.WUMPUS_HIT_BY_ARROW);
        gameTerminated = true;
        return nextCavern;
      } else if (gameCaverns.isPlayerCavern(nextCavern)) {
        gameTerminated = true;
        responseModel.setReasonGameTerminated(GameOverReasons.HIT_BY_OWN_ARROW);
        return nextCavern;
      }
      return shootAsFarAsPossible(direction, nextCavern);
    }
  }


  public void moveWumpus() {
    if (wumpusFrozen)
      return;
    List<Integer> moves = new ArrayList<Integer>();
    addPossibleMove(GameCaverns.EAST, moves);
    addPossibleMove(GameCaverns.WEST, moves);
    addPossibleMove(GameCaverns.NORTH, moves);
    addPossibleMove(GameCaverns.SOUTH, moves);
    moves.add(0); // rest;

    int selection = (int) (Math.random() * moves.size());
    int selectedMove = moves.get(selection);
    if (selectedMove != 0) {
      gameCaverns.moveWumpusTo(selectedMove);
      checkIfWumpusEatsPlayer();
    }
  }

  private void addPossibleMove(String direction, List<Integer> moves) {
    int possibleMove;
    possibleMove = gameCaverns.adjacentTo(direction, gameCaverns.getWumpusCavern());
    if (possibleMove != 0)
      moves.add(possibleMove);
  }

  public ResponseModel finalizeResponseModel() {
    responseModel.setGameTerminated(gameTerminated);
    responseModel.setQuiver(quiver);

    responseModel.setBatTransport(batTransport);
    batTransport = false;

    responseModel.setAvailableDirections(gameCaverns.getAvailableDirections());

    responseModel.setCanSmellWumpus(gameCaverns.playerIsInCavernNextToWumpus());
    responseModel.setCanHearPit(gameCaverns.playerIsInCavernNextToPit());
    responseModel.setCanHearBats(gameCaverns.playerIsInCavernNextToBats());
    return responseModel;
  }


}
