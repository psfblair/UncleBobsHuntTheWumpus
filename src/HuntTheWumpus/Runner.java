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

    g.gameMap.addPath(1, 2, GameCaverns.EAST);
    g.gameMap.addPath(2, 3, GameCaverns.EAST);
    g.gameMap.addPath(3, 4, GameCaverns.SOUTH);
    g.gameMap.addPath(4, 5, GameCaverns.SOUTH);
    g.gameMap.addPath(5, 6, GameCaverns.SOUTH);
    g.gameMap.addPath(5, 7, GameCaverns.EAST);
    g.gameMap.addPath(7, 8, GameCaverns.EAST);
    g.gameMap.addPath(8, 9, GameCaverns.NORTH);
    g.gameMap.addPath(9, 10, GameCaverns.EAST);
    g.gameMap.addPath(10, 11, GameCaverns.EAST);
    g.gameMap.addPath(11, 12, GameCaverns.NORTH);
    g.gameMap.addPath(12, 13, GameCaverns.NORTH);
    g.gameMap.addPath(8, 14, GameCaverns.SOUTH);
    g.gameMap.addPath(14, 15, GameCaverns.SOUTH);
    g.gameMap.addPath(14, 16, GameCaverns.EAST);
    g.gameMap.addPath(16, 17, GameCaverns.EAST);
    g.gameMap.addPath(17, 18, GameCaverns.NORTH);
    g.gameMap.addPath(18, 11, GameCaverns.NORTH);
    g.gameMap.addPath(3, 19, GameCaverns.NORTH);
    g.gameMap.addPath(19, 20, GameCaverns.NORTH);
    g.gameMap.addPath(20, 21, GameCaverns.EAST);
    g.gameMap.addPath(21, 22, GameCaverns.EAST);
    g.gameMap.addPath(22, 23, GameCaverns.EAST);
    g.gameMap.addPath(23, 24, GameCaverns.EAST);
    g.gameMap.addPath(24, 13, GameCaverns.SOUTH);
    g.gameMap.addPath(1, 25, GameCaverns.SOUTH);
    g.gameMap.addPath(25, 26, GameCaverns.SOUTH);
    g.gameMap.addPath(26, 15, GameCaverns.EAST);
    g.gameMap.addPath(15, 27, GameCaverns.EAST);
    g.gameMap.addPath(27, 16, GameCaverns.NORTH);
    g.gameMap.addPath(15, 21, GameCaverns.SOUTH);
    g.gameMap.addPath(25, 20, GameCaverns.EAST);
    g.gameMap.addPath(8, 18, GameCaverns.EAST);
    g.gameMap.addPath(21, 9, GameCaverns.SOUTH);

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
