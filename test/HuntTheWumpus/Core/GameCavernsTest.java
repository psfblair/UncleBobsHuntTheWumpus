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
    caverns.addPath(1, 2, GameCaverns.SOUTH);
    Set<String> expected = (new HashSet<String>());
    expected.add(GameCaverns.SOUTH);
    Set<String> availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }

  public void testNorthAndSouthAvailable() throws Exception {
    caverns.addPath(1, 2, GameCaverns.SOUTH);
    caverns.addPath(1, 3, GameCaverns.NORTH);

    Set<String> expected = (new HashSet<String>());
    expected.add(GameCaverns.SOUTH);
    expected.add(GameCaverns.NORTH);

    Set<String>  availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }

  public void testFourDirections() throws Exception {
    caverns.addPath(1, 2, GameCaverns.EAST);
    caverns.addPath(1, 3, GameCaverns.WEST);
    caverns.addPath(1, 4, GameCaverns.SOUTH);
    caverns.addPath(1, 5, GameCaverns.NORTH);

    Set<String> expected = (new HashSet<String>());
    expected.add(GameCaverns.SOUTH);
    expected.add(GameCaverns.EAST);
    expected.add(GameCaverns.WEST);
    expected.add(GameCaverns.NORTH);

    Set<String> availableDirections = caverns.getAvailableDirectionsFrom(1);
    assertEquals(expected, availableDirections);
  }
}
