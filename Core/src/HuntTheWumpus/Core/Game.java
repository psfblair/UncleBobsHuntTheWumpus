package HuntTheWumpus.Core;

import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.Core.Actors.Player;
import HuntTheWumpus.Core.Actors.Wumpus;

public class Game {
  public final GameCaverns gameCaverns = new GameCaverns();
  public final Wumpus wumpus = new Wumpus(gameCaverns);
  public final Player player = new Player(gameCaverns, wumpus);

  private boolean gameTerminated;

  public boolean isGameTerminated() {
    return gameTerminated;
  }

  public void setGameTerminated(boolean gameTerminated) {
    this.gameTerminated = gameTerminated;
  }

  public GameCaverns getGameCaverns() {
    return gameCaverns;
  }

  public Player getPlayer() {
    return player;
  }

  public Wumpus getWumpus() {
    return wumpus;
  }

  public void reset() {
    gameTerminated = false;
  }
}
