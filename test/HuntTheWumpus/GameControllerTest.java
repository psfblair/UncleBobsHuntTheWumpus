package HuntTheWumpus;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;
import HuntTheWumpus.Presentation.GamePresenter;
import HuntTheWumpus.Presentation.Presentation;
import junit.framework.TestCase;
import HuntTheWumpus.fixtures.MockConsole;

import java.util.HashSet;
import java.util.Set;

public class GameControllerTest extends TestCase {
  private GameController controller;
  private MockConsole mc;
  private Game game;
  private Presentation presenter;

  protected void setUp() throws Exception {
    mc = new MockConsole();
    controller = new GameController(mc, new EnglishCommandInterpreter());
    game = controller.getGame();
    presenter = new GamePresenter(mc);
  }

  public void testDirectionErrorMessages() throws Exception {
    game.gameMap.putPlayerInCavern(1);
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
    game.initializeResponseModel();
    game.gameMap.putPlayerInCavern(1);
    Set<String> expected = (new HashSet<String>());
    Set<String> availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }

  public void testSouthIsAvailable() throws Exception {
    game.initializeResponseModel();
    game.gameMap.addPath(1, 2, GameCaverns.SOUTH);
    game.gameMap.putPlayerInCavern(1);
    Set<String> expected = (new HashSet<String>());
    expected.add(GameCaverns.SOUTH);
    Set<String> availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }

  public void testNorthAndSouthAvailable() throws Exception {
    game.initializeResponseModel();
    game.gameMap.addPath(1, 2, GameCaverns.SOUTH);
    game.gameMap.addPath(1, 3, GameCaverns.NORTH);
    game.gameMap.putPlayerInCavern(1);

    Set<String> expected = (new HashSet<String>());
    expected.add(GameCaverns.SOUTH);
    expected.add(GameCaverns.NORTH);

    Set<String>  availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }

  public void testFourDirections() throws Exception {
    game.initializeResponseModel();
    game.gameMap.addPath(1, 2, GameCaverns.EAST);
    game.gameMap.addPath(1, 3, GameCaverns.WEST);
    game.gameMap.addPath(1, 4, GameCaverns.SOUTH);
    game.gameMap.addPath(1, 5, GameCaverns.NORTH);
    game.gameMap.putPlayerInCavern(1);

    Set<String> expected = (new HashSet<String>());
    expected.add(GameCaverns.SOUTH);
    expected.add(GameCaverns.EAST);
    expected.add(GameCaverns.WEST);
    expected.add(GameCaverns.NORTH);

    Set<String> availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }
}
