package HuntTheWumpus.Core;

import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.Core.Actors.Player;
import HuntTheWumpus.Core.Actors.Wumpus;
import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.GameOverReason;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;
import HuntTheWumpus.Core.Scenarios.MovePlayer;
import HuntTheWumpus.Core.Scenarios.ShootArrow;
import HuntTheWumpus.Core.Scenarios.UnknownCommand;
import junit.framework.TestCase;

public class GameTest extends TestCase {
  private Game game;
  private Output output;
  private GameCaverns caverns;
  private Player player;
  private Wumpus wumpus;

  protected void setUp() throws Exception {
    game = new Game();
    caverns = game.getGameCaverns();
    player = game.getPlayer();
    wumpus = game.getWumpus();
    output = new Output() {
      public void outputResponse(ResponseModel responseModel) {
      }
    };
  }

  public void testGoEast() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);

    move(Direction.EAST);
    assertEquals(2, player.getPlayerCavern());
  }

  public void testGoEastTwice() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    caverns.addPath(2, 3, Direction.EAST);
    player.putPlayerInCavern(1);

    move(Direction.EAST);
    move(Direction.EAST);
    assertEquals(3, player.getPlayerCavern());
  }

  public void testSmellWumpus() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);
    wumpus.putWumpusInCavern(2);
    assertTrue(player.isInCavernNextToWumpus());
  }

  public void testCantSmellWumpus() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    caverns.addPath(2, 3, Direction.EAST);
    player.putPlayerInCavern(1);
    wumpus.putWumpusInCavern(3);
    assertFalse(player.isInCavernNextToWumpus());
  }

  public void testPickUpArrows() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);
    player.setQuiver(0);
    caverns.putArrowInCavern(2);

    move(Direction.EAST);
    assertEquals(1, player.getQuiver());
    assertEquals(0, caverns.arrowsInCavern(2));
  }

  public void testShootArrow() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    caverns.addPath(2, 3, Direction.EAST);
    player.putPlayerInCavern(1);
    player.setQuiver(1);

    ShootArrow shootScenario = shoot(Direction.EAST);
    assertTrue(shootScenario.arrowWasShot());
    assertEquals(0, player.getQuiver());
    assertEquals(1, caverns.arrowsInCavern(3));
    assertEquals(0, caverns.arrowsInCavern(2));
    assertFalse(game.isGameTerminated());
  }

  public void testShootArrowWhenQuiverEmpty() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);
    player.setQuiver(0);

    ShootArrow shootScenario = shoot(Direction.EAST);
    assertFalse(shootScenario.arrowWasShot());
    assertEquals(0, player.getQuiver());
    assertEquals(0, caverns.arrowsInCavern(2));
    assertFalse(game.isGameTerminated());
  }

  public void testPlayerDiesIfShootsAtWall() throws Exception {
    player.putPlayerInCavern(1);
    player.setQuiver(1);

    ShootArrow shootScenario = shoot(Direction.EAST);
    assertTrue(game.isGameTerminated());
    assertEquals(GameOverReason.KILLED_BY_ARROW_BOUNCE, shootScenario.gameTerminationReason());
  }

  public void testFallInPit() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);
    caverns.putPitInCavern(2);

    MovePlayer move = move(Direction.EAST);
    assertTrue(game.isGameTerminated());
    assertEquals(GameOverReason.FELL_IN_PIT, move.gameTerminationReason());
  }

  public void testHearPit() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    caverns.addPath(2, 3, Direction.EAST);
    player.putPlayerInCavern(1);
    caverns.putPitInCavern(3);

    move(Direction.EAST);
    assertTrue(player.isInCavernNextToPit());
    move(Direction.WEST);
    assertFalse(player.isInCavernNextToPit());
  }

  public void testKillWumpusAtDistance() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    caverns.addPath(2, 3, Direction.EAST);
    wumpus.putWumpusInCavern(3);
    player.putPlayerInCavern(1);
    player.setQuiver(1);

    ShootArrow shootScenario = shoot(Direction.EAST);
    assertTrue(game.isGameTerminated());
    assertEquals(GameOverReason.WUMPUS_HIT_BY_ARROW, shootScenario.gameTerminationReason());
  }

  public void testKillWumpusUpClose() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    wumpus.putWumpusInCavern(2);
    player.putPlayerInCavern(1);
    player.setQuiver(1);

    ShootArrow shootScenario = shoot(Direction.EAST);
    assertTrue(game.isGameTerminated());
    assertEquals(GameOverReason.WUMPUS_HIT_BY_ARROW, shootScenario.gameTerminationReason());
  }

  public void testRandomWumpusMovement() throws Exception {
    boolean moved = false;
    caverns.addPath(1, 2, Direction.EAST);
    wumpus.putWumpusInCavern(1);

    UnknownCommand commandToTestWumpusMotion = new UnknownCommand(game, output, "foo");
    for (int i = 0; i < 100; i++) {
      commandToTestWumpusMotion.wumpusMoves();
      if (wumpus.getWumpusCavern() == 2) {
        moved = true;
        break;
      }
    }
    assertTrue(moved);
  }

  public void testHearBats() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);
    caverns.putBatsInCavern(2);
    assertTrue(player.isInCavernNextToBats());
  }

  public void testBatsCarryYouAway() throws Exception {
    caverns.addPath(1, 2, Direction.EAST);
    player.putPlayerInCavern(1);
    caverns.putBatsInCavern(2);

    MovePlayer move = move(Direction.EAST);
    assertTrue(move.isTransportedByBats());
    assertEquals(1, player.getPlayerCavern());
  }
  
  private MovePlayer move(Direction direction) {
    MovePlayer move = new MovePlayer(game, output, direction);
    move.invoke();
    return move;
  }

  private ShootArrow shoot(Direction direction) {
    ShootArrow shoot = new ShootArrow(game, output, direction);
    shoot.invoke();
    return shoot;
  }
}
