package HuntTheWumpus;

import HuntTheWumpus.Command.*;
import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Input.GameController;
import HuntTheWumpus.Core.Input.RequestModel;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Scenarios.Initialize;
import HuntTheWumpus.Presentation.Console;
import HuntTheWumpus.Presentation.TextDisplay;
import HuntTheWumpus.Presentation.TextPresenter;

import java.util.HashSet;
import java.util.Set;

public class Runner {
  public static void main(String[] args) throws Exception {
    Output output = createOutputHandler();
    GameController controller = new GameController(output);

    CommandInterpreter commandInterpreter = createCommandInterpreter();

    Initialize.InitializationParameters initializationParameters = createInitializationParameters();
    controller.execute(initializationParameters);


    while (! controller.isGameTerminated()) {
      RequestModel request = commandInterpreter.getRequest();
      controller.execute(request);
    }
  }

  private static CommandInterpreter createCommandInterpreter() {
    return new EnglishCommandInterpreter();
  }

  private static TextPresenter createOutputHandler() {
    TextDisplay console = new Console();
    return new EnglishTextPresenter(console);
  }

  private static Initialize.InitializationParameters createInitializationParameters() {
    Set<Initialize.PathInitializer> paths = new HashSet<Initialize.PathInitializer>();
    paths.add(Initialize.pathFor(1, 2, Direction.EAST));
    paths.add(Initialize.pathFor(2, 3, Direction.EAST));
    paths.add(Initialize.pathFor(3, 4, Direction.SOUTH));
    paths.add(Initialize.pathFor(4, 5, Direction.SOUTH));
    paths.add(Initialize.pathFor(5, 6, Direction.SOUTH));
    paths.add(Initialize.pathFor(5, 7, Direction.EAST));
    paths.add(Initialize.pathFor(7, 8, Direction.EAST));
    paths.add(Initialize.pathFor(8, 9, Direction.NORTH));
    paths.add(Initialize.pathFor(9, 10, Direction.EAST));
    paths.add(Initialize.pathFor(10, 11, Direction.EAST));
    paths.add(Initialize.pathFor(11, 12, Direction.NORTH));
    paths.add(Initialize.pathFor(12, 13, Direction.NORTH));
    paths.add(Initialize.pathFor(8, 14, Direction.SOUTH));
    paths.add(Initialize.pathFor(14, 15, Direction.SOUTH));
    paths.add(Initialize.pathFor(14, 16, Direction.EAST));
    paths.add(Initialize.pathFor(16, 17, Direction.EAST));
    paths.add(Initialize.pathFor(17, 18, Direction.NORTH));
    paths.add(Initialize.pathFor(18, 11, Direction.NORTH));
    paths.add(Initialize.pathFor(3, 19, Direction.NORTH));
    paths.add(Initialize.pathFor(19, 20, Direction.NORTH));
    paths.add(Initialize.pathFor(20, 21, Direction.EAST));
    paths.add(Initialize.pathFor(21, 22, Direction.EAST));
    paths.add(Initialize.pathFor(22, 23, Direction.EAST));
    paths.add(Initialize.pathFor(23, 24, Direction.EAST));
    paths.add(Initialize.pathFor(24, 13, Direction.SOUTH));
    paths.add(Initialize.pathFor(1, 25, Direction.SOUTH));
    paths.add(Initialize.pathFor(25, 26, Direction.SOUTH));
    paths.add(Initialize.pathFor(26, 15, Direction.EAST));
    paths.add(Initialize.pathFor(15, 27, Direction.EAST));
    paths.add(Initialize.pathFor(27, 16, Direction.NORTH));
    paths.add(Initialize.pathFor(15, 21, Direction.SOUTH));
    paths.add(Initialize.pathFor(25, 20, Direction.EAST));
    paths.add(Initialize.pathFor(8, 18, Direction.EAST));
    paths.add(Initialize.pathFor(21, 9, Direction.SOUTH));

    int playerCavern = 1;
    int wumpusCavern = 15;
    int numberOfArrows = 5;

    Initialize.InitializationParameters initializationParameters =
        new Initialize.InitializationParameters(paths, playerCavern, wumpusCavern, numberOfArrows);

    Set<Integer> pits = new HashSet<Integer>();
    pits.add(22);
    pits.add(17);
    initializationParameters.setPits(pits);

    Set<Integer> bats = new HashSet<Integer>();
    bats.add(8);
    initializationParameters.setBats(bats);
    return initializationParameters;
  }

}
