package HuntTheWumpus.Core;

import HuntTheWumpus.Core.Actors.GameCaverns;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class GameCavernsTest extends TestCase {
  private GameCaverns caverns;

  protected void setUp() throws Exception {
    caverns = new GameCaverns();
  }

  public void testAvailableDirectionsWithNoPlaceToGo() {
    Set<String> expected = (new HashSet<String>());
    Set<String> availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }

  public void testSouthIsAvailable() throws Exception {
    caverns.addPath(1, 2, Direction.SOUTH);
    Set<Direction> expected = new HashSet<Direction>();
    expected.add(Direction.SOUTH);
    Set<String> availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }

  public void testNorthAndSouthAvailable() throws Exception {
    caverns.addPath(1, 2, Direction.SOUTH);
    caverns.addPath(1, 3, Direction.NORTH);

    Set<Direction> expected = new HashSet<Direction>();
    expected.add(Direction.SOUTH);
    expected.add(Direction.NORTH);

    Set<String>  availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }

  public void testFourDirections() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    caverns.addPath(1, 3, Direction.WEST);
    caverns.addPath(1, 4, Direction.SOUTH);
    caverns.addPath(1, 5, Direction.NORTH);

    Set<Direction> expected = new HashSet<Direction>();
    expected.add(Direction.SOUTH);
    expected.add(Direction.EAST);
    expected.add(Direction.WEST);
    expected.add(Direction.NORTH);

    Set<String> availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }
}
