package HuntTheWumpus.Core;

import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.Core.Actors.Player;
import HuntTheWumpus.Core.Actors.Wumpus;
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
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.move(Direction.EAST);
    assertEquals(2, g.getPlayer().getPlayerCavern());
  }

  public void testGoEastTwice() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.gameCaverns.addPath(2, 3, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.move(Direction.EAST);
    g.move(Direction.EAST);
    assertEquals(3, g.getPlayer().getPlayerCavern());
  }

  public void testSmellWumpus() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.getWumpus().putWumpusInCavern(2);
    assertTrue(g.getPlayer().isInCavernNextToWumpus());
  }

  public void testCantSmellWumpus() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.gameCaverns.addPath(2, 3, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.getWumpus().putWumpusInCavern(3);
    assertFalse(g.getPlayer().isInCavernNextToWumpus());
  }

  public void testPickUpArrows() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(0);
    g.gameCaverns.putArrowInCavern(2);
    g.move(Direction.EAST);
    assertEquals(1, g.getPlayer().getQuiver());
    assertEquals(0, g.gameCaverns.arrowsInCavern(2));
  }

  public void testShootArrow() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.gameCaverns.addPath(2, 3, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(1);
    assertTrue(g.shoot(Direction.EAST));
    assertEquals(0, g.getPlayer().getQuiver());
    assertEquals(1, g.gameCaverns.arrowsInCavern(3));
    assertEquals(0, g.gameCaverns.arrowsInCavern(2));
    assertFalse(g.gameTerminated());
  }

  public void testShootArrowWhenQuiverEmpty() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(0);
    assertFalse(g.shoot(Direction.EAST));
    assertEquals(0, g.getPlayer().getQuiver());
    assertEquals(0, g.gameCaverns.arrowsInCavern(2));
  }

  public void testPlayerDiesIfShootsAtWall() throws Exception {
    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(1);
    g.shoot(Direction.EAST);
    assertTrue(g.gameTerminated());
  }

  public void testFallInPit() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.gameCaverns.putPitInCavern(2);
    g.move(Direction.EAST);
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReason.FELL_IN_PIT, g.gameTerminationReason());
  }

  public void testHearPit() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.gameCaverns.addPath(2, 3, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.gameCaverns.putPitInCavern(3);
    g.move(Direction.EAST);
    assertTrue(g.getPlayer().isInCavernNextToPit());
    g.move(Direction.WEST);
    assertFalse(g.getPlayer().isInCavernNextToPit());
  }

  public void testKillWumpusAtDistance() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.gameCaverns.addPath(2, 3, Direction.EAST);
    g.getWumpus().putWumpusInCavern(3);
    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(1);
    g.shoot(Direction.EAST);
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReason.WUMPUS_HIT_BY_ARROW, g.gameTerminationReason());
  }

  public void testKillWumpusUpClose() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getWumpus().putWumpusInCavern(2);
    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(1);
    g.shoot(Direction.EAST);
    assertTrue(g.gameTerminated());
    assertEquals(GameOverReason.WUMPUS_HIT_BY_ARROW, g.gameTerminationReason());
  }

  public void testRandomWumpusMovement() throws Exception {
    boolean moved = false;
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getWumpus().putWumpusInCavern(1);
    for (int i = 0; i < 100; i++) {
      g.wumpusMoves();
      if (g.getWumpus().getWumpusCavern() == 2) {
        moved = true;
        break;
      }
    }
    assertTrue(moved);
  }

  public void testHearBats() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.gameCaverns.putBatsInCavern(2);
    assertTrue(g.getPlayer().isInCavernNextToBats());
  }

  public void testBatsCarryYouAway() throws Exception {
    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.getPlayer().putPlayerInCavern(1);
    g.gameCaverns.putBatsInCavern(2);
    g.move(Direction.EAST);
    assertTrue(g.isTransportedByBats());
    //Also used in Game start
    assertEquals(1, g.getPlayer().getPlayerCavern());
  }
}
