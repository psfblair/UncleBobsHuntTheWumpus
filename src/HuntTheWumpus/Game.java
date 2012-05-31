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
  private boolean killedByArrowBounce = false;
  private ArrayList<Integer> pits = new ArrayList<Integer>();
  private boolean fellInPit = false;
  private boolean wumpusHitByArrow = false;
  private boolean wumpusFrozen = false;
  private boolean eatenByWumpus = false;
  private boolean hitByOwnArrow = false;
  private ArrayList<Integer> bats = new ArrayList<Integer>();
  private boolean batTransport = false;
  private Game.ResponseModel responseModel;

  public void invoke(MovePlayer theCommand, PresentationBoundary presenter) {
    this.responseModel = new ResponseModel();
    int arrowsInQuiverBeforeTurn = getQuiver();
    String direction = theCommand.getDirection();
    if (move(direction) == false)
      presenter.printCannotMove(direction);
    ResponseModel responseModel = createResponseModel(arrowsInQuiverBeforeTurn);
    presenter.printEndOfTurnMessages(responseModel);
  }

  public void invoke(ShootArrow theCommand, PresentationBoundary presenter) {
    int arrowsInQuiverBeforeTurn = getQuiver();
    if (shoot(theCommand.getDirection()) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
    ResponseModel responseModel = createResponseModel(arrowsInQuiverBeforeTurn);
    presenter.printEndOfTurnMessages(responseModel);
  }

  public void invoke(Rest theCommand, PresentationBoundary presenter) {
    rest();
    ResponseModel responseModel = createResponseModel(getQuiver());
    presenter.printEndOfTurnMessages(responseModel);
  }

  public void invoke(UnknownCommand theCommand, PresentationBoundary presenter) {
    presenter.printUnknownCommand(theCommand.getCommandString());
    ResponseModel responseModel = createResponseModel(getQuiver());
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
      eatenByWumpus = true;
    }
  }
  // EATEN BY WUMPUS SCENARIO

  // PIT SCENARIO
  private void checkForPit() {
    if (pits.contains(playerCavern)) {
      gameTerminated = true;
      fellInPit = true;
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

  // GAME OVER REASONS
  public boolean gameTerminated() {
    return gameTerminated;
  }

  public boolean fellInPit() {
    return fellInPit;
  }

  public boolean wumpusHitByArrow() {
    return wumpusHitByArrow;
  }

  public boolean eatenByWumpus() {
    return eatenByWumpus;
  }

  public boolean hitByOwnArrow(){
    return hitByOwnArrow;
  }

  public boolean wasKilledByArrowBounce() {
    return killedByArrowBounce;
  }
  // END GAME OVER REASONS


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

  public void reset() {
    gameTerminated = false;
    wumpusHitByArrow = false;
    fellInPit = false;
    killedByArrowBounce = false;
    eatenByWumpus = false;
    hitByOwnArrow = false;
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
    if (quiver > 0) {
      quiver--;
      if (adjacentTo(direction, playerCavern) == 0) {
        gameTerminated = true;
        killedByArrowBounce = true;
      } else {
        int endCavern = shootAsFarAsPossible(direction, playerCavern);
        putArrowInCavern(endCavern);
        moveWumpus();
      }
      return true;
    } else
      return false;
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
        wumpusHitByArrow = true;
        gameTerminated = true;
        return nextCavern;
      } else if (nextCavern == playerCavern) {
        gameTerminated = true;
        hitByOwnArrow = true;
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

  public ResponseModel createResponseModel(int arrowsInQuiverBeforeTurn) {
    ResponseModel model = new ResponseModel();
    model.setGameTerminated(gameTerminated);
    model.setFellInPit(fellInPit);
    model.setEatenByWumpus(eatenByWumpus);
    model.setWumpusHitByArrow(wumpusHitByArrow);
    model.setHitByOwnArrow(hitByOwnArrow);
    model.setKilledByArrowBounce(killedByArrowBounce);

    model.setQuiver(quiver);
    model.setArrowsInQuiverBeforeTurn(arrowsInQuiverBeforeTurn);

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
    private boolean killedByArrowBounce = false;
    private boolean fellInPit = false;
    private boolean wumpusHitByArrow = false;
    private boolean eatenByWumpus = false;
    private boolean hitByOwnArrow = false;
    private boolean batTransport = false;
    private boolean canSmellWumpus;
    private boolean canHearPit;
    private boolean canHearBats;
    private String availableDirections;

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

    public boolean isKilledByArrowBounce() {
      return killedByArrowBounce;
    }

    public void setKilledByArrowBounce(boolean killedByArrowBounce) {
      this.killedByArrowBounce = killedByArrowBounce;
    }

    public boolean isFallenIntoPit() {
      return fellInPit;
    }

    public void setFellInPit(boolean fellInPit) {
      this.fellInPit = fellInPit;
    }

    public boolean isWumpusHitByArrow() {
      return wumpusHitByArrow;
    }

    public void setWumpusHitByArrow(boolean wumpusHitByArrow) {
      this.wumpusHitByArrow = wumpusHitByArrow;
    }

    public boolean isEatenByWumpus() {
      return eatenByWumpus;
    }

    public void setEatenByWumpus(boolean eatenByWumpus) {
      this.eatenByWumpus = eatenByWumpus;
    }

    public boolean isHitByOwnArrow() {
      return hitByOwnArrow;
    }

    public void setHitByOwnArrow(boolean hitByOwnArrow) {
      this.hitByOwnArrow = hitByOwnArrow;
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
  }

}
