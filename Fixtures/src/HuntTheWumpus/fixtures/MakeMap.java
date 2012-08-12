package HuntTheWumpus.fixtures;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Core.Constants.Direction;

public class MakeMap {
  private int start;
  private int end;
  private Direction direction;

  public void execute() throws Exception {
    GameDriver.game.getGameCaverns().addPath(start, end, direction);
  }

  public void setStart(int start) {
    this.start = start;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public void setDirection(String direction) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    EnglishCommandInterpreter interpreter = new EnglishCommandInterpreter();
    this.direction = interpreter.directionFromName(direction.toLowerCase());;
  }
}
