package HuntTheWumpus;

import HuntTheWumpus.Presentation.ResponseModel;
import HuntTheWumpus.fixtures.MockConsole;
import junit.framework.TestCase;

public class GameTest extends TestCase {
  private Game g;
  private MockConsole mc;

  protected void setUp() throws Exception {
    g = new Game();
  }

  public void testGoEast() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.move(GameMap.EAST);
    assertEquals(2, g.gameMap.playerCavern(g));
  }

  public void testGoEastTwice() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.addPath(2, 3, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.move(GameMap.EAST);
    g.move(GameMap.EAST);
    assertEquals(3, g.gameMap.playerCavern(g));
  }

  public void testSmellWumpus() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putWumpusInCavern(2);
    assertTrue(g.gameMap.playerIsInCavernNextToWumpus());
  }

  public void testCantSmellWumpus() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.addPath(2, 3, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putWumpusInCavern(3);
    assertFalse(g.gameMap.playerIsInCavernNextToWumpus());
  }

  public void testPickUpArrows() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.setQuiver(0);
    g.gameMap.putArrowInCavern(2);
    g.move(GameMap.EAST);
    assertEquals(1, g.getQuiver());
    assertEquals(0, g.gameMap.arrowsInCavern(2));
  }

  public void testShootArrow() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.addPath(2, 3, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.setQuiver(1);
    assertTrue(g.shoot(GameMap.EAST));
    assertEquals(0, g.getQuiver());
    assertEquals(1, g.gameMap.arrowsInCavern(3));
    assertEquals(0, g.gameMap.arrowsInCavern(2));
    assertFalse(g.gameTerminated());
  }

  public void testShootArrowWhenQuiverEmpty() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.setQuiver(0);
    assertFalse(g.shoot(GameMap.EAST));
    assertEquals(0, g.getQuiver());
    assertEquals(0, g.gameMap.arrowsInCavern(2));
  }

  public void testPlayerDiesIfShootsAtWall() throws Exception {
    g.initializeResponseModel();
    g.gameMap.putPlayerInCavern(1);
    g.setQuiver(1);
    g.shoot(GameMap.EAST);
    assertTrue(g.gameTerminated());
  }

  public void testFallInPit() throws Exception {
    g.initializeResponseModel();
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putPitInCavern(2);
    g.move(GameMap.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(responseModel.isGameTerminated());
    assertEquals(GameOverReasons.FELL_IN_PIT, responseModel.getGameTerminationReason());
  }

  public void testHearPit() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.addPath(2, 3, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putPitInCavern(3);
    g.move(GameMap.EAST);
    assertTrue(g.gameMap.playerIsInCavernNextToPit());
    g.move(GameMap.WEST);
    assertFalse(g.gameMap.playerIsInCavernNextToPit());
  }

  public void testKillWumpusAtDistance() throws Exception {
    g.initializeResponseModel();
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.addPath(2, 3, GameMap.EAST);
    g.gameMap.putWumpusInCavern(3);
    g.gameMap.putPlayerInCavern(1);
    g.setQuiver(1);
    g.shoot(GameMap.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReasons.WUMPUS_HIT_BY_ARROW, responseModel.getGameTerminationReason());
  }

  public void testKillWumpusUpClose() throws Exception {
    g.initializeResponseModel();
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putWumpusInCavern(2);
    g.gameMap.putPlayerInCavern(1);
    g.setQuiver(1);
    g.shoot(GameMap.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReasons.WUMPUS_HIT_BY_ARROW, responseModel.getGameTerminationReason());
  }

  public void testRandomWumpusMovement() throws Exception {
    boolean moved = false;
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putWumpusInCavern(1);
    for (int i = 0; i < 100; i++) {
      g.moveWumpus();
      if (g.gameMap.getWumpusCavern() == 2) {
        moved = true;
        break;
      }
    }
    assertTrue(moved);
  }

  public void testHearBats() throws Exception {
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putBatsInCavern(2);
    assertTrue(g.gameMap.playerIsInCavernNextToBats());
  }

  public void testBatsCarryYouAway() throws Exception {
    g.initializeResponseModel();
    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putBatsInCavern(2);
    g.move(GameMap.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(responseModel.isTransportedByBats());
    assertEquals(1, g.gameMap.playerCavern(g));
  }
}
