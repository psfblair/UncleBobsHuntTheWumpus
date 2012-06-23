package HuntTheWumpus.fixtures;

import HuntTheWumpus.Command.CommandInterpreter;
import HuntTheWumpus.Command.ConsoleInputHandlerStub;
import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Input.GameController;
import HuntTheWumpus.Presentation.MockConsoleDisplay;
import HuntTheWumpus.Presentation.TextPresenter;

public class GameDriver {
  public static MockConsoleDisplay display;
  public static Game game;
  public static CommandInterpreter commandInterpreter;
  public static ConsoleInputHandlerStub inputHandler;
  public static GameController gameController;

  public GameDriver() {
    display = new MockConsoleDisplay();
    gameController = new GameController(new TextPresenter(display));
    inputHandler = new ConsoleInputHandlerStub();
    commandInterpreter = new EnglishCommandInterpreter(inputHandler);
    game = gameController.getGame();
  }

  public void putPlayerInCavern(int cavern) {
    game.getPlayer().startInCavern(cavern);
  }
  public boolean putInCavern(String what, int where) {
    if (what.equals("player")) {
      game.getPlayer().startInCavern(where);
      return true;
    }
    else if (what.equals("wumpus")) {
      game.getWumpus().startInCavern(where);
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

  public void enterCommand(String command) throws Exception {
    inputHandler.setLineToReturn(command);
    gameController.execute(commandInterpreter.getRequest());
  }

  public boolean cavernHas(int cavern, String what) {
    if (what.equals("player")) {
      //Also used in Game start
      return game.getPlayer().cavern() == cavern;
    }
    return false;
  }

  public boolean messageWasPrinted(String message) {
    return display.check(message);
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
