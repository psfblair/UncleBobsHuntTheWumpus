package HuntTheWumpus.Core;

import HuntTheWumpus.Command.EnglishCommandInterpreter;
import HuntTheWumpus.Command.GameController;
import HuntTheWumpus.Core.Actors.GameCaverns;

import java.io.*;

public class Runner {
  public static void main(String[] args) throws Exception {
    GameController p = new GameController(new HuntTheWumpus.Presentation.Console() {
      public void print(String message) {
        System.out.println(message);
      }
    }, new EnglishCommandInterpreter());
    Game g = p.getGame();

    g.gameCaverns.addPath(1, 2, Direction.EAST);
    g.gameCaverns.addPath(2, 3, Direction.EAST);
    g.gameCaverns.addPath(3, 4, Direction.SOUTH);
    g.gameCaverns.addPath(4, 5, Direction.SOUTH);
    g.gameCaverns.addPath(5, 6, Direction.SOUTH);
    g.gameCaverns.addPath(5, 7, Direction.EAST);
    g.gameCaverns.addPath(7, 8, Direction.EAST);
    g.gameCaverns.addPath(8, 9, Direction.NORTH);
    g.gameCaverns.addPath(9, 10, Direction.EAST);
    g.gameCaverns.addPath(10, 11, Direction.EAST);
    g.gameCaverns.addPath(11, 12, Direction.NORTH);
    g.gameCaverns.addPath(12, 13, Direction.NORTH);
    g.gameCaverns.addPath(8, 14, Direction.SOUTH);
    g.gameCaverns.addPath(14, 15, Direction.SOUTH);
    g.gameCaverns.addPath(14, 16, Direction.EAST);
    g.gameCaverns.addPath(16, 17, Direction.EAST);
    g.gameCaverns.addPath(17, 18, Direction.NORTH);
    g.gameCaverns.addPath(18, 11, Direction.NORTH);
    g.gameCaverns.addPath(3, 19, Direction.NORTH);
    g.gameCaverns.addPath(19, 20, Direction.NORTH);
    g.gameCaverns.addPath(20, 21, Direction.EAST);
    g.gameCaverns.addPath(21, 22, Direction.EAST);
    g.gameCaverns.addPath(22, 23, Direction.EAST);
    g.gameCaverns.addPath(23, 24, Direction.EAST);
    g.gameCaverns.addPath(24, 13, Direction.SOUTH);
    g.gameCaverns.addPath(1, 25, Direction.SOUTH);
    g.gameCaverns.addPath(25, 26, Direction.SOUTH);
    g.gameCaverns.addPath(26, 15, Direction.EAST);
    g.gameCaverns.addPath(15, 27, Direction.EAST);
    g.gameCaverns.addPath(27, 16, Direction.NORTH);
    g.gameCaverns.addPath(15, 21, Direction.SOUTH);
    g.gameCaverns.addPath(25, 20, Direction.EAST);
    g.gameCaverns.addPath(8, 18, Direction.EAST);
    g.gameCaverns.addPath(21, 9, Direction.SOUTH);

    g.gameCaverns.putPitInCavern(22);
    g.gameCaverns.putPitInCavern(17);
    g.gameCaverns.putBatsInCavern(8);

    g.getPlayer().putPlayerInCavern(1);
    g.getPlayer().setQuiver(5);
    g.getWumpus().putWumpusInCavern(15);

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    p.execute("r");
    while (g.gameTerminated() == false) {
      String command = br.readLine();
      p.execute(command);
    }
  }
}
