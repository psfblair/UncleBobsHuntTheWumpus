package HuntTheWumpus;

import HuntTheWumpus.CommandInterpreter.EnglishCommandInterpreter;
import junit.framework.TestCase;
import static HuntTheWumpus.Game.*;
import HuntTheWumpus.fixtures.MockConsole;

public class GameControllerTest extends TestCase {
  private GameController controller;
  private MockConsole mc;
  private Game game;
  private PresentationBoundary presenter;

  protected void setUp() throws Exception {
    mc = new MockConsole();
    controller = new GameController(mc, new EnglishCommandInterpreter());
    game = controller.getGame();
    presenter = new GamePresenter(mc, game);
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
    game.putPlayerInCavern(1);
    presenter.printAvailableDirections();
    assertTrue(mc.check("There are no exits!"));
  }

  public void testSouthIsAvailable() throws Exception {
    game.addPath(1, 2, SOUTH);
    game.putPlayerInCavern(1);
    presenter.printAvailableDirections();
    assertTrue(mc.check("You can go south from here."));
  }

  public void testNortAndSouthAvailable() throws Exception {
    game.addPath(1, 2, SOUTH);
    game.addPath(1, 3, NORTH);
    game.putPlayerInCavern(1);
    presenter.printAvailableDirections();
    assertTrue(mc.check("You can go north and south from here."));
  }

  public void testFourDirections() throws Exception {
    game.addPath(1, 2, EAST);
    game.addPath(1, 3, WEST);
    game.addPath(1, 4, SOUTH);
    game.addPath(1, 5, NORTH);
    game.putPlayerInCavern(1);
    presenter.printAvailableDirections();
    assertTrue(mc.check("You can go north, south, east and west from here."));
  }
}
