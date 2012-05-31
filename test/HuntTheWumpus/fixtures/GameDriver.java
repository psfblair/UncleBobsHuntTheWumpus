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
    g.putPlayerInCavern(cavern);
  }
  public boolean putInCavern(String what, int where) {
    if (what.equals("player")) {
      g.putPlayerInCavern(where);
      return true;
    }
    else if (what.equals("wumpus")) {
      g.putWumpusInCavern(where);
      return true;
    }
    else if (what.equals("arrow")) {
      g.putArrowInCavern(where);
      return true;
    }
    else if (what.equals("pit")) {
      g.putPitInCavern(where);
      return true;
    }
    else if (what.equals("bats")) {
      g.putBatsInCavern(where);
      return true;
    }
    return false;
  }
  public void enterCommand(String command) {
    gameController.execute(command);
  }

  public boolean cavernHas(int cavern, String what) {
    if (what.equals("player")) {
      return g.playerCavern() == cavern;
    }
    return false;
  }

  public boolean messageWasPrinted(String message) {
    return mc.check(message);
  }

  public void clearMap() {
    g.clearMap();
  }

  public void freezeWumpus(boolean freeze) {
    g.freezeWumpus();
  }

  public void setQuiverTo(int arrows) {
    g.setQuiver(arrows);
  }

  public int arrowsInCavern(int cavern) {
    return g.arrowsInCavern(cavern);
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
