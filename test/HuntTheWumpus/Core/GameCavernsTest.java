package HuntTheWumpus.Core;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;
import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.fixtures.MockConsole;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class GameCavernsTest extends TestCase {
  private GameController controller;
  private MockConsole mc;
  private Game game;
  private GameCaverns caverns;

  protected void setUp() throws Exception {
    mc = new MockConsole();
    controller = new GameController(mc, new EnglishCommandInterpreter());
    game = controller.getGame();
    caverns = new GameCaverns();
  }

  // TODO - This is presentation layer test - move
  public void testDirectionErrorMessages() throws Exception {
    game.getPlayer().putPlayerInCavern(1);
    controller.execute(GameCaverns.EAST);
    assertTrue(mc.check("You can't go east from here."));
    controller.execute(GameCaverns.WEST);
    assertTrue(mc.check("You can't go west from here."));
    controller.execute(GameCaverns.SOUTH);
    assertTrue(mc.check("You can't go south from here."));
    controller.execute(GameCaverns.NORTH);
    assertTrue(mc.check("You can't go north from here."));
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
