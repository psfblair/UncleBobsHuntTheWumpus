package HuntTheWumpus;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;

import java.io.*;

public class Runner {
  public static void main(String[] args) throws Exception {
    GameController p = new GameController(new HuntTheWumpus.Presentation.Console() {
      public void print(String message) {
        System.out.println(message);
      }
    }, new EnglishCommandInterpreter());
    Game g = p.getGame();

    g.gameMap.addPath(1, 2, GameMap.EAST);
    g.gameMap.addPath(2, 3, GameMap.EAST);
    g.gameMap.addPath(3, 4, GameMap.SOUTH);
    g.gameMap.addPath(4, 5, GameMap.SOUTH);
    g.gameMap.addPath(5, 6, GameMap.SOUTH);
    g.gameMap.addPath(5, 7, GameMap.EAST);
    g.gameMap.addPath(7, 8, GameMap.EAST);
    g.gameMap.addPath(8, 9, GameMap.NORTH);
    g.gameMap.addPath(9, 10, GameMap.EAST);
    g.gameMap.addPath(10, 11, GameMap.EAST);
    g.gameMap.addPath(11, 12, GameMap.NORTH);
    g.gameMap.addPath(12, 13, GameMap.NORTH);
    g.gameMap.addPath(8, 14, GameMap.SOUTH);
    g.gameMap.addPath(14, 15, GameMap.SOUTH);
    g.gameMap.addPath(14, 16, GameMap.EAST);
    g.gameMap.addPath(16, 17, GameMap.EAST);
    g.gameMap.addPath(17, 18, GameMap.NORTH);
    g.gameMap.addPath(18, 11, GameMap.NORTH);
    g.gameMap.addPath(3, 19, GameMap.NORTH);
    g.gameMap.addPath(19, 20, GameMap.NORTH);
    g.gameMap.addPath(20, 21, GameMap.EAST);
    g.gameMap.addPath(21, 22, GameMap.EAST);
    g.gameMap.addPath(22, 23, GameMap.EAST);
    g.gameMap.addPath(23, 24, GameMap.EAST);
    g.gameMap.addPath(24, 13, GameMap.SOUTH);
    g.gameMap.addPath(1, 25, GameMap.SOUTH);
    g.gameMap.addPath(25, 26, GameMap.SOUTH);
    g.gameMap.addPath(26, 15, GameMap.EAST);
    g.gameMap.addPath(15, 27, GameMap.EAST);
    g.gameMap.addPath(27, 16, GameMap.NORTH);
    g.gameMap.addPath(15, 21, GameMap.SOUTH);
    g.gameMap.addPath(25, 20, GameMap.EAST);
    g.gameMap.addPath(8, 18, GameMap.EAST);
    g.gameMap.addPath(21, 9, GameMap.SOUTH);

    g.gameMap.putPlayerInCavern(1);
    g.gameMap.putWumpusInCavern(15);
    g.gameMap.putPitInCavern(22);
    g.gameMap.putPitInCavern(17);
    g.gameMap.putBatsInCavern(8);
    g.setQuiver(5);

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    p.execute("r");
    while (g.gameTerminated() == false) {
      String command = br.readLine();
      p.execute(command);
    }
  }
}
