package HuntTheWumpus.fixtures;

import HuntTheWumpus.*;
import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;

public class GameDriver {
  public static GameController gameController;
  private MockConsole mc;
  public static Game g;

  public GameDriver() {
    gameController = new GameController(mc = new MockConsole(), new EnglishCommandInterpreter());
    g = gameController.getGame();
  }


  public void putPlayerInCavern(int cavern) {
    g.getGameCaverns().putPlayerInCavern(cavern);
  }
  public boolean putInCavern(String what, int where) {
    if (what.equals("player")) {
      g.getGameCaverns().putPlayerInCavern(where);
      return true;
    }
    else if (what.equals("wumpus")) {
      g.gameCaverns.putWumpusInCavern(where);
      return true;
    }
    else if (what.equals("arrow")) {
      g.gameCaverns.putArrowInCavern(where);
      return true;
    }
    else if (what.equals("pit")) {
      g.getGameCaverns().putPitInCavern(where);
      return true;
    }
    else if (what.equals("bats")) {
      g.getGameCaverns().putBatsInCavern(where);
      return true;
    }
    return false;
  }
  public void enterCommand(String command) {
    gameController.execute(command);
  }

  public boolean cavernHas(int cavern, String what) {
    if (what.equals("player")) {
      return g.gameCaverns.playerCavern(g) == cavern;
    }
    return false;
  }

  public boolean messageWasPrinted(String message) {
    return mc.check(message);
  }

  public void clearMap() {
    g.getGameCaverns().clearMap();
  }

  public void freezeWumpus(boolean freeze) {
    g.freezeWumpus();
  }

  public void setQuiverTo(int arrows) {
    g.setQuiver(arrows);
  }

  public int arrowsInCavern(int cavern) {
    return g.gameCaverns.arrowsInCavern(cavern);
  }

  public int arrowsInQuiver() {
    return g.getQuiver();
  }

  public boolean gameTerminated() {
    return g.gameTerminated();
  }

  public void newGame() {
    g.reset();
  }
}
