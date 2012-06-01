package HuntTheWumpus.fixtures;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;
import HuntTheWumpus.Core.Game;

public class GameDriver {
  public static GameController gameController;
  private MockConsole mc;
  public static Game game;

  public GameDriver() {
    gameController = new GameController(mc = new MockConsole(), new EnglishCommandInterpreter());
    game = gameController.getGame();
  }


  public void putPlayerInCavern(int cavern) {
    game.getPlayer().putPlayerInCavern(cavern);
  }
  public boolean putInCavern(String what, int where) {
    if (what.equals("player")) {
      game.getPlayer().putPlayerInCavern(where);
      return true;
    }
    else if (what.equals("wumpus")) {
      game.getWumpus().putWumpusInCavern(where);
      return true;
    }
    else if (what.equals("arrow")) {
      game.gameCaverns.putArrowInCavern(where);
      return true;
    }
    else if (what.equals("pit")) {
      game.getGameCaverns().putPitInCavern(where);
      return true;
    }
    else if (what.equals("bats")) {
      game.getGameCaverns().putBatsInCavern(where);
      return true;
    }
    return false;
  }
  public void enterCommand(String command) {
    gameController.execute(command);
  }

  public boolean cavernHas(int cavern, String what) {
    if (what.equals("player")) {
      //Also used in Game start
      return game.getPlayer().getPlayerCavern() == cavern;
    }
    return false;
  }

  public boolean messageWasPrinted(String message) {
    return mc.check(message);
  }

  public void clearMap() {
    game.getGameCaverns().clearMap();
  }

  public void freezeWumpus(boolean freeze) {
    game.getWumpus().freeze();
  }

  public void setQuiverTo(int arrows) {
    game.getPlayer().setQuiver(arrows);
  }

  public int arrowsInCavern(int cavern) {
    return game.getGameCaverns().arrowsInCavern(cavern);
  }

  public int arrowsInQuiver() {
    return game.getPlayer().getQuiver();
  }

  public boolean gameTerminated() {
    return game.isGameTerminated();
  }

  public void newGame() {
    game.reset();
  }
}
