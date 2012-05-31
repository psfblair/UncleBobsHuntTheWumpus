package HuntTheWumpus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMap {
  public static final String EAST = "e";
  public static final String WEST = "w";
  public static final String NORTH = "n";
  public static final String SOUTH = "s";

  public List<Path> paths = new ArrayList<Path>();
  public int playerCavern = -1;
  public int wumpusCavern = -1;
  private ArrayList<Integer> arrows = new ArrayList<Integer>();
  public ArrayList<Integer> pits = new ArrayList<Integer>();
  public ArrayList<Integer> bats = new ArrayList<Integer>();

  public GameMap() {
  }

  public void addPath(int start, int end, String direction) throws Exception {
    direction = direction.toLowerCase();
    addSinglePath(start, end, direction);
    addSinglePath(end, start, oppositeDirection(direction));
  }

  public void clearMap() {
    paths.clear();
  }

  public String oppositeDirection(String direction) throws Exception {
    if (direction.equals(EAST)) return WEST;
    else if (direction.equals(WEST)) return EAST;
    else if (direction.equals(NORTH)) return SOUTH;
    else if (direction.equals(SOUTH)) return NORTH;
    else
      throw new Exception("No such direction: " + direction);
  }

  public void addSinglePath(int start, int end, String direction) {
    Path p = new Path(start, end, direction);
    paths.add(p);
  }

  public void putPlayerInCavern(int cavern) {
    playerCavern = cavern;
  }

  public void putWumpusInCavern(int where) {
    wumpusCavern = where;
  }

  public void putPitInCavern(int cavern) {
    pits.add(cavern);
  }

  public void putBatsInCavern(int cavern) {
    bats.add(cavern);
  }

  public int playerCavern(Game game) { //Also used in Game start
    return playerCavern;
  }

  public int getWumpusCavern() {
    return wumpusCavern;
  }

  Set getAvailableDirections(Game game) {
    Set directions = new HashSet();

    for (Path p : paths) {
      if (p.start == playerCavern) {
        directions.add(p.direction);
      }
    }
    return directions;
  }

  boolean playerIsInWumpusCavern() {
    return playerCavern == wumpusCavern;
  }

  boolean playerIsInCavernWithPit() {
    return pits.contains(playerCavern);
  }

  boolean playerIsInCavernWithBats() {
    return bats.contains(playerCavern);
  }

  boolean arrowIsInCavernWithPlayer() {
    return arrowInCavern(playerCavern);
  }

  void roomNoLongerContainsArrow() {
    removeArrowFrom(playerCavern);
  }

  boolean arrowInCavern(int cavern) {
    for (int c : arrows)
      if (c == cavern) return true;
    return false;
  }

  public int arrowsInCavern(int cavern) {
    int count = 0;
    for (int i : arrows) {
      if (i == cavern)
        count++;
    }
    return count;
  }

  public void putArrowInCavern(int cavern) {
    arrows.add(cavern);
  }

  void removeArrowFrom(int cavern) {
    for (int i = 0; i < arrows.size(); i++) {
      int c = arrows.get(i);
      if (c == cavern) {
        arrows.remove(i);
      }
    }
  }

  int adjacentTo(String direction, int cavern) {
    for (Path p : paths) {
      if (p.start == cavern && p.direction.equals(direction))
        return p.end;
    }
    return 0;
  }

  public boolean playerCanSmellWumpus() {
    return areAdjacent(playerCavern, wumpusCavern);
  }

  public boolean playerCanHearPit() {
    for (int pit : pits)
      if (areAdjacent(playerCavern, pit))
        return true;
    return false;
  }

  public boolean playerCanHearBats() {
    for (int batCave : bats)
      if (areAdjacent(batCave, playerCavern))
        return true;
    return false;
  }

  boolean areAdjacent(int c1, int c2) {
    for (Path p : paths) {
      if (p.start == c1 && p.end == c2)
        return true;
    }
    return false;
  }

  void putPlayerInRandomCavern(Game game) {
    Path selectedPath = paths.get((int) (Math.random() * paths.size()));
    playerCavern = selectedPath.start;
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
}