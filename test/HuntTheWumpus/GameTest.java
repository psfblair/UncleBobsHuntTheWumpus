package HuntTheWumpus;

import HuntTheWumpus.Presentation.ResponseModel;
import HuntTheWumpus.fixtures.MockConsole;
import junit.framework.TestCase;

public class GameTest extends TestCase {
  private Game g;
  private Player p;
  private Wumpus w;
  private MockConsole mc;

  protected void setUp() throws Exception {
    g = new Game();
  }

  public void testGoEast() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.move(GameCaverns.EAST);
    assertEquals(2, g.gameCaverns.playerCavern(g));
  }

  public void testGoEastTwice() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.addPath(2, 3, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.move(GameCaverns.EAST);
    g.move(GameCaverns.EAST);
    assertEquals(3, g.gameCaverns.playerCavern(g));
  }

  public void testSmellWumpus() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.gameCaverns.putWumpusInCavern(2);
    assertTrue(g.gameCaverns.playerIsInCavernNextToWumpus());
  }

  public void testCantSmellWumpus() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.addPath(2, 3, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.gameCaverns.putWumpusInCavern(3);
    assertFalse(g.gameCaverns.playerIsInCavernNextToWumpus());
  }

  public void testPickUpArrows() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.setQuiver(0);
    g.gameCaverns.putArrowInCavern(2);
    g.move(GameCaverns.EAST);
    assertEquals(1, g.getQuiver());
    assertEquals(0, g.gameCaverns.arrowsInCavern(2));
  }

  public void testShootArrow() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.addPath(2, 3, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.setQuiver(1);
    assertTrue(g.shoot(GameCaverns.EAST));
    assertEquals(0, g.getQuiver());
    assertEquals(1, g.gameCaverns.arrowsInCavern(3));
    assertEquals(0, g.gameCaverns.arrowsInCavern(2));
    assertFalse(g.gameTerminated());
  }

  public void testShootArrowWhenQuiverEmpty() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.setQuiver(0);
    assertFalse(g.shoot(GameCaverns.EAST));
    assertEquals(0, g.getQuiver());
    assertEquals(0, g.gameCaverns.arrowsInCavern(2));
  }

  public void testPlayerDiesIfShootsAtWall() throws Exception {
    g.initializeResponseModel();
    g.gameCaverns.putPlayerInCavern(1);
    g.setQuiver(1);
    g.shoot(GameCaverns.EAST);
    assertTrue(g.gameTerminated());
  }

  public void testFallInPit() throws Exception {
    g.initializeResponseModel();
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.gameCaverns.putPitInCavern(2);
    g.move(GameCaverns.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(responseModel.isGameTerminated());
    assertEquals(GameOverReasons.FELL_IN_PIT, responseModel.getGameTerminationReason());
  }

  public void testHearPit() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.addPath(2, 3, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.gameCaverns.putPitInCavern(3);
    g.move(GameCaverns.EAST);
    assertTrue(g.gameCaverns.playerIsInCavernNextToPit());
    g.move(GameCaverns.WEST);
    assertFalse(g.gameCaverns.playerIsInCavernNextToPit());
  }

  public void testKillWumpusAtDistance() throws Exception {
    g.initializeResponseModel();
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.addPath(2, 3, GameCaverns.EAST);
    g.gameCaverns.putWumpusInCavern(3);
    g.gameCaverns.putPlayerInCavern(1);
    g.setQuiver(1);
    g.shoot(GameCaverns.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReasons.WUMPUS_HIT_BY_ARROW, responseModel.getGameTerminationReason());
  }

  public void testKillWumpusUpClose() throws Exception {
    g.initializeResponseModel();
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putWumpusInCavern(2);
    g.gameCaverns.putPlayerInCavern(1);
    g.setQuiver(1);
    g.shoot(GameCaverns.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReasons.WUMPUS_HIT_BY_ARROW, responseModel.getGameTerminationReason());
  }

  public void testRandomWumpusMovement() throws Exception {
    boolean moved = false;
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putWumpusInCavern(1);
    for (int i = 0; i < 100; i++) {
      g.moveWumpus();
      if (g.gameCaverns.getWumpusCavern() == 2) {
        moved = true;
        break;
      }
    }
    assertTrue(moved);
  }

  public void testHearBats() throws Exception {
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.gameCaverns.putBatsInCavern(2);
    assertTrue(g.gameCaverns.playerIsInCavernNextToBats());
  }

  public void testBatsCarryYouAway() throws Exception {
    g.initializeResponseModel();
    g.gameCaverns.addPath(1, 2, GameCaverns.EAST);
    g.gameCaverns.putPlayerInCavern(1);
    g.gameCaverns.putBatsInCavern(2);
    g.move(GameCaverns.EAST);
    ResponseModel responseModel = g.finalizeResponseModel();
    assertTrue(responseModel.isTransportedByBats());
    assertEquals(1, g.gameCaverns.playerCavern(g));
  }
}
