package HuntTheWumpus;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;
import HuntTheWumpus.Presentation.GamePresenter;
import HuntTheWumpus.Presentation.Presentation;
import junit.framework.TestCase;
import static HuntTheWumpus.Game.*;
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
    game.putPlayerInCavern(1);
    controller.execute(EAST);
    assertTrue(mc.check("You can't go east from here."));
    controller.execute(WEST);
    assertTrue(mc.check("You can't go west from here."));
    controller.execute(SOUTH);
    assertTrue(mc.check("You can't go south from here."));
    controller.execute(NORTH);
    assertTrue(mc.check("You can't go north from here."));
  }

  public void testAvailableDirectionsWithNoPlaceToGo() {
    game.initializeResponseModel();
    game.putPlayerInCavern(1);
    Set<String> expected = (new HashSet<String>());
    Set<String> availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }

  public void testSouthIsAvailable() throws Exception {
    game.initializeResponseModel();
    game.addPath(1, 2, SOUTH);
    game.putPlayerInCavern(1);
    Set<String> expected = (new HashSet<String>());
    expected.add(SOUTH);
    Set<String> availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }

  public void testNortAndSouthAvailable() throws Exception {
    game.initializeResponseModel();
    game.addPath(1, 2, SOUTH);
    game.addPath(1, 3, NORTH);
    game.putPlayerInCavern(1);

    Set<String> expected = (new HashSet<String>());
    expected.add(SOUTH);
    expected.add(NORTH);

    Set<String>  availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }

  public void testFourDirections() throws Exception {
    game.initializeResponseModel();
    game.addPath(1, 2, EAST);
    game.addPath(1, 3, WEST);
    game.addPath(1, 4, SOUTH);
    game.addPath(1, 5, NORTH);
    game.putPlayerInCavern(1);

    Set<String> expected = (new HashSet<String>());
    expected.add(SOUTH);
    expected.add(EAST);
    expected.add(WEST);
    expected.add(NORTH);

    Set<String> availableDirections = game.finalizeResponseModel().getAvailableDirections();
    assertEquals(expected, availableDirections);
  }
}
