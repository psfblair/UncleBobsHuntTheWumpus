package HuntTheWumpus;

import HuntTheWumpus.Commands.*;

import java.util.*;

public class Game {
  public static final String EAST = "e";
  public static final String WEST = "w";
  public static final String NORTH = "n";
  public static final String SOUTH = "s";
  List<Path> paths = new ArrayList<Path>();
  int playerCavern = -1;
  private int wumpusCavern = -1;
  private int quiver = 0;
  private ArrayList<Integer> arrows = new ArrayList<Integer>();
  private boolean gameTerminated = false;
  private ArrayList<Integer> pits = new ArrayList<Integer>();
  private boolean wumpusFrozen = false;
  private ArrayList<Integer> bats = new ArrayList<Integer>();
  private boolean batTransport = false;
  private Game.ResponseModel responseModel;

  public void invoke(MovePlayer theCommand, PresentationBoundary presenter) {
    initializeResponseModel();
    String direction = theCommand.getDirection();
    if (move(direction) == false)
      presenter.printCannotMove(direction);
    output(presenter);
  }

  public void invoke(ShootArrow theCommand, PresentationBoundary presenter) {
    initializeResponseModel();
    if (shoot(theCommand.getDirection()) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
    output(presenter);
  }

  public void invoke(Rest theCommand, PresentationBoundary presenter) {
    initializeResponseModel();
    rest();
    output(presenter);
  }

  public void invoke(UnknownCommand theCommand, PresentationBoundary presenter) {
    initializeResponseModel();
    presenter.printUnknownCommand(theCommand.getCommandString());
    output(presenter);
  }

  public void initializeResponseModel() {
    responseModel = new ResponseModel();
    responseModel.setArrowsInQuiverBeforeTurn(this.quiver);
  }

  private void output(PresentationBoundary presenter) {
    ResponseModel responseModel = finalizeResponseModel();
    presenter.printEndOfTurnMessages(responseModel);
  }

// ************************ INITIALIZATION CODE ****************************** //

  public void addPath(int start, int end, String direction) throws Exception {
    direction = direction.toLowerCase();
    addSinglePath(start, end, direction);
    addSinglePath(end, start, oppositeDirection(direction));
  }

  public void clearMap() {
    paths.clear();
  }

  private String oppositeDirection(String direction) throws Exception {
    if (direction.equals(EAST)) return WEST;
    else if (direction.equals(WEST)) return EAST;
    else if (direction.equals(NORTH)) return SOUTH;
    else if (direction.equals(SOUTH)) return NORTH;
    else
      throw new Exception("No such direction: " + direction);
  }

  private void addSinglePath(int start, int end, String direction) {
    Path p = new Path(start, end, direction);
    paths.add(p);
  }

  public void putPlayerInCavern(int cavern) {
    playerCavern = cavern;
  }

  public void putPitInCavern(int cavern) {
    pits.add(cavern);
  }

  public void putBatsInCavern(int cavern) {
    bats.add(cavern);
  }

  public void putWumpusInCavern(int where) {
    wumpusCavern = where;
  }
// ************************ END INITIALIZATION CODE ****************************** //


  public void rest() {
    moveWumpus();
  }

  public boolean move(String direction) {
    int destination = adjacentTo(direction, playerCavern);
    if (destination != 0) {
      playerCavern = destination;
      checkWumpusEatsPlayer();
      checkForPit();
      checkForBats();
      pickUpArrow();
      moveWumpus();
      return true;
    }
    return false;
  }

  // EATEN BY WUMPUS SCENARIO
  private void checkWumpusEatsPlayer() {
    if (playerCavern == wumpusCavern) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(ReasonsGameOver.EATEN_BY_WUMPUS);
    }
  }
  // EATEN BY WUMPUS SCENARIO

  // PIT SCENARIO
  private void checkForPit() {
    if (pits.contains(playerCavern)) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(ReasonsGameOver.FELL_IN_PIT);
    }
  }
  // PIT SCENARIO

  // BATS SCENARIO
  private void checkForBats() {
    while (bats.contains(playerCavern)) {
      transportPlayer();
      batTransport = true;
    }
  }

  private void transportPlayer() {
    Path selectedPath = paths.get((int)(Math.random() * paths.size()));
    playerCavern = selectedPath.start;
  }
  // BATS SCENARIO


  // PICK UP ARROW SCENARIO
  private void pickUpArrow() {
    if (arrowInCavern(playerCavern)) {
      removeArrowFrom(playerCavern);
      quiver++;
    }
  }

  private boolean arrowInCavern(int cavern) {
    for (int c : arrows)
      if (c == cavern) return true;
    return false;
  }

  private void removeArrowFrom(int cavern) {
    for (int i = 0; i < arrows.size(); i++) {
      int c = arrows.get(i);
      if (c == cavern) {
        arrows.remove(i);
      }
    }
  }
  // PICK UP ARROW SCENARIO

  public boolean gameTerminated() {
    return gameTerminated;
  }

  // FOR TESTING
  public int playerCavern() { //Also used in Game start
    return playerCavern;
  }

  public void setQuiver(int arrows) { //Also used in Game start
    quiver = arrows;
  }

  public int getWumpusCavern() {
    return wumpusCavern;
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
  // END FOR TESTING

  // ******************** DETECTION SCENARIOS ********************* //
  public boolean canSmellWumpus() {
    return areAdjacent(playerCavern, wumpusCavern);
  }

  public boolean canHearPit() {
    for (int pit : pits)
      if (areAdjacent(playerCavern, pit))
        return true;
    return false;
  }

  private boolean areAdjacent(int c1, int c2) {
    for (Path p : paths) {
      if (p.start == c1 && p.end == c2)
        return true;
    }
    return false;
  }

  public boolean canHearBats() {
    for (int batCave : bats)
      if (areAdjacent(batCave, playerCavern))
        return true;
    return false;
  }

  // ******************** END DETECTION SCENARIOS ********************* //

  public int getQuiver() {
    return quiver;
  }

  public int arrowsInCavern(int cavern) {
    int count = 0;
    for (int i : arrows) {
      if (i == cavern)
        count++;
    }
    return count;
  }

  // SHOOTING SCENARIO //
  public boolean shoot(String direction) {
    if (quiver <=  0)
      return false;
    quiver--;
    if (adjacentTo(direction, playerCavern) == 0) {
      gameTerminated = true;
      responseModel.setReasonGameTerminated(ReasonsGameOver.KILLED_BY_ARROW_BOUNCE);
      return true;
    }

    int endCavern = shootAsFarAsPossible(direction, playerCavern);
    if (! gameTerminated()) {
      putArrowInCavern(endCavern);
      moveWumpus();
    }
    return true;
  }

  private int adjacentTo(String direction, int cavern) {
    for (Path p : paths) {
      if (p.start == cavern && p.direction.equals(direction))
        return p.end;
    }
    return 0;
  }

  public void putArrowInCavern(int cavern) {
    arrows.add(cavern);
  }

  private int shootAsFarAsPossible(String direction, int cavern) {
    int nextCavern = adjacentTo(direction, cavern);
    if (nextCavern == 0)
      return cavern;
    else {
      if (nextCavern == wumpusCavern) {
        responseModel.setReasonGameTerminated(ReasonsGameOver.WUMPUS_HIT_BY_ARROW);
        gameTerminated = true;
        return nextCavern;
      } else if (nextCavern == playerCavern) {
        gameTerminated = true;
        responseModel.setReasonGameTerminated(ReasonsGameOver.HIT_BY_OWN_ARROW);
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
    addPossibleMove(EAST, moves);
    addPossibleMove(WEST, moves);
    addPossibleMove(NORTH, moves);
    addPossibleMove(SOUTH, moves);
    moves.add(0); // rest;

    int selection = (int) (Math.random() * moves.size());
    int selectedMove = moves.get(selection);
    if (selectedMove != 0) {
      wumpusCavern = selectedMove;
      checkWumpusEatsPlayer();
    }
  }

  private void addPossibleMove(String dir, List<Integer> moves) {
    int possibleMove;
    possibleMove = adjacentTo(dir, wumpusCavern);
    if (possibleMove != 0)
      moves.add(possibleMove);
  }

  private String getAvailableDirections() {
    AvailableDirections directions = new AvailableDirections();

    for (Game.Path p : paths) {
      if (p.start == playerCavern()) {
        directions.addDirection(p.direction);
      }
    }
    return directions.toString();
  }

  class Path {
    public int start;
    public int end;
    public String direction;

    public Path(int start, int end, String direction) {
      this.start = start;
      this.end = end;
      this.direction = direction;
    }
  }

  enum ReasonsGameOver {
    FELL_IN_PIT,
    EATEN_BY_WUMPUS,
    WUMPUS_HIT_BY_ARROW,
    HIT_BY_OWN_ARROW,
    KILLED_BY_ARROW_BOUNCE
  }

  public ResponseModel finalizeResponseModel() {
    ResponseModel model = this.responseModel;

    model.setGameTerminated(gameTerminated);
    model.setQuiver(quiver);

    model.setBatTransport(batTransport);
    batTransport = false;

    model.setAvailableDirections(getAvailableDirections());

    model.setCanSmellWumpus(canSmellWumpus());
    model.setCanHearPit(canHearPit());
    model.setCanHearBats(canHearBats());
    return model;
  }


  class ResponseModel {
    private int quiver = 0;
    private int arrowsInQuiverBeforeTurn = 0;
    private boolean gameTerminated = false;
    private boolean batTransport = false;
    private boolean canSmellWumpus;
    private boolean canHearPit;
    private boolean canHearBats;
    private String availableDirections;
    private ReasonsGameOver gameTerminationReason;

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

    public void setAvailableDirections(String availableDirections) {
      this.availableDirections = availableDirections;
    }

    public String getAvailableDirections() {
      return availableDirections;
    }

    public void setReasonGameTerminated(ReasonsGameOver gameTerminationReason) {
      this.gameTerminationReason = gameTerminationReason;
    }

    public ReasonsGameOver getGameTerminationReason() {
      return gameTerminationReason;
    }
  }

}
