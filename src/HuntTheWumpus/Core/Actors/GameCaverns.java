package HuntTheWumpus.Core.Actors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameCaverns {
  public static final String EAST = "e";
  public static final String WEST = "w";
  public static final String NORTH = "n";
  public static final String SOUTH = "s";

  public List<Path> paths = new ArrayList<Path>();
  private ArrayList<Integer> arrows = new ArrayList<Integer>();
  public ArrayList<Integer> pits = new ArrayList<Integer>();
  public ArrayList<Integer> bats = new ArrayList<Integer>();

  public GameCaverns() {
  }

  public void clearMap() {
    paths.clear();
  }

  public void addPath(int start, int end, String direction) throws Exception {
    direction = direction.toLowerCase();
    addSinglePath(start, end, direction);
    addSinglePath(end, start, oppositeDirection(direction));
  }

  public void addSinglePath(int start, int end, String direction) {
    Path p = new Path(start, end, direction);
    paths.add(p);
  }

  public String oppositeDirection(String direction) throws Exception {
    if (direction.equals(EAST)) return WEST;
    else if (direction.equals(WEST)) return EAST;
    else if (direction.equals(NORTH)) return SOUTH;
    else if (direction.equals(SOUTH)) return NORTH;
    else
      throw new Exception("No such direction: " + direction);
  }

  public int getRandomPathStart() {
    Path selectedPath =  paths.get((int) (Math.random() * paths.size()));
    return selectedPath.start;
  }

  public Set getAvailableDirectionsFrom(double cavern) {
    Set directions = new HashSet();

    for (Path p : paths) {
      if (p.start == cavern) {
        directions.add(p.direction);
      }
    }
    return directions;
  }

  public boolean thereIsAWallInDirectionFromCavern(String direction, int cavern) {
    return adjacentTo(direction, cavern) == 0;
  }

  public int adjacentTo(String direction, int cavern) {
    for (Path p : paths) {
      if (p.start == cavern && p.direction.equals(direction))
        return p.end;
    }
    return 0;
  }

  boolean areAdjacent(int c1, int c2) {
    for (Path p : paths) {
      if (p.start == c1 && p.end == c2)
        return true;
    }
    return false;
  }

  public void putPitInCavern(int cavern) {
    pits.add(cavern);
  }

  boolean isCavernWithPit(int cavern) {
    return pits.contains(cavern);
  }

  boolean isCavernNextToPit(int cavern) {
    return isCavernNextTo(cavern, pits);
  }

  public void putBatsInCavern(int cavern) {
    bats.add(cavern);
  }

  public boolean isCavernWithBats(int cavern) {
    return bats.contains(cavern);
  }

  boolean isCavernNextToBats(int cavern) {
    return isCavernNextTo(cavern, bats);
  }

  private boolean isCavernNextTo(int cavern, ArrayList<Integer> hazards) {
    for (int hazard : hazards)
      if (areAdjacent(hazard, cavern))
        return true;
    return false;
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

  public boolean isInCavernWithArrow(int cavern) {
    return arrowInCavern(cavern);
  }

  void removeArrowFrom(int cavern) {
    for (int i = 0; i < arrows.size(); i++) {
      int c = arrows.get(i);
      if (c == cavern) {
        arrows.remove(i);
      }
    }
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