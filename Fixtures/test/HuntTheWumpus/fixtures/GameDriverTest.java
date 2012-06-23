package HuntTheWumpus.fixtures;

import junit.framework.TestCase;

public class GameDriverTest extends TestCase {

  public void testOutputGetsPrintedToMockDisplay() throws Exception {
    GameDriver gameDriver = new GameDriver();
    gameDriver.enterCommand("R");
    GameDriver.display.check("Game over.");
  }

}
