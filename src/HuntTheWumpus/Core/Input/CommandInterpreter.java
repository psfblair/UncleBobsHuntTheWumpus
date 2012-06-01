package HuntTheWumpus.Core.Input;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Scenarios.Scenario;

public interface CommandInterpreter {
  Scenario getCommand(String commandString, Game game, Output presenter);
}
