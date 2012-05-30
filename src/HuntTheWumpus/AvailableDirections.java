package HuntTheWumpus;

import java.util.HashSet;
import java.util.Set;

class AvailableDirections {
  private Set<String> directions = new HashSet<String>();
  private int nDirections;
  private StringBuffer available;
  private int directionsPlaced;

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
    for (String dir : new String[]{Game.NORTH, Game.SOUTH, Game.EAST, Game.WEST}) {
      if (directions.contains(dir)) {
        placeDirection(dir);
      }
    }
    return "You can go " + available.toString() + " from here.";
  }

  private void placeDirection(String dir) {
    directionsPlaced++;
    if (isLastOfMany())
      available.append(" and ");
    else if (notFirst())
      available.append(", ");
    available.append(GamePresenter.directionName(dir));
  }

  private boolean notFirst() {return directionsPlaced > 1;}

  private boolean isLastOfMany() {return nDirections > 1 && directionsPlaced == nDirections;}

  public void addDirection(String direction) {
    directions.add(direction);
  }
}

