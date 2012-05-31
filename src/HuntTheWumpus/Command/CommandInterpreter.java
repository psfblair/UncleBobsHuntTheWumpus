package HuntTheWumpus.Command;

import HuntTheWumpus.Core.*;
import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.Core.Scenarios.*;
import HuntTheWumpus.Presentation.Output;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandInterpreter {
  enum Commands {
    VERBOSE_REST,
    REST,
    VERBOSE_SHOOT,
    SHOOT,
    VERBOSE_GO,
    VERBOSE_EAST,
    EAST,
    VERBOSE_WEST,
    WEST,
    VERBOSE_NORTH,
    NORTH,
    VERBOSE_SOUTH,
    SOUTH
  }
  
  protected Map<Commands, String> commandTranslations = new HashMap();

  public Scenario getCommand(String commandString, Game game, Output presenter) {
    Scenario command = new UnknownCommand(game, presenter, commandString);
    String[] tokens = tokenizeInput(commandString);

    if (isRestCommand(tokens))
      command = new Rest(game, presenter);
    else if (isShootCommand(tokens)) {
      command = createShootCommand(command, tokens[1], game, presenter);
    }
    else if (isSingleWordShootCommand(tokens)) {
      command = createShootCommand(command, tokens[0].substring(1), game, presenter);
    }
    else if (isGoCommand(tokens)) {
      command = createGoCommand(command, tokens[1], game, presenter);
    }
    else if (isImplicitGoCommand(tokens)) {
      command = createGoCommand(command, tokens[0], game, presenter);
    }

    return command;
  }

  private String[] tokenizeInput(String command) {
    return command.toLowerCase().split(" ");
  }

  private boolean isRestCommand(String[] tokens) {
    return tokens[0].equals(rest()) || tokens[0].equals(verboseRest());
  }

  private boolean isShootCommand(String[] tokens) {
    return tokens.length == 2 &&
        (tokens[0].equals(verboseShoot()) || tokens[0].equals(shoot()));
  }

  private boolean isSingleWordShootCommand(String[] tokens) {
    return tokens[0].charAt(0) == shootChar() && directionFromName(tokens[0].substring(1)) != null;
  }

  private Scenario createShootCommand(Scenario command, String token, Game game, Output presenter) {
    String direction = directionFromName(token);
    if (direction != null)
      command = new ShootArrow(game, presenter, direction);
    return command;
  }

  private boolean isGoCommand(String[] tokens) {
    return tokens.length == 2 && tokens[0].equals(verboseGo());
  }

  private boolean isImplicitGoCommand(String[] tokens) {
    return tokens.length == 1 && directionFromName(tokens[0]) != null;
  }

  private Scenario createGoCommand(Scenario command, String token, Game game, Output presenter) {
    String direction = directionFromName(token);
    if (direction != null)
      command = new MovePlayer(game, presenter,direction);
    return command;
  }

  private String directionFromName(String name) {
    if (name.equals(east()) || name.equals(verboseEast())) return GameCaverns.EAST;
    else if (name.equals(west()) || name.equals(verboseWest())) return GameCaverns.WEST;
    else if (name.equals(north()) || name.equals(verboseNorth())) return GameCaverns.NORTH;
    else if (name.equals(south()) || name.equals(verboseSouth())) return GameCaverns.SOUTH;
    else return null;
  }

  public String verboseRest() { return commandTranslations.get(Commands.VERBOSE_REST); }
  public String rest() { return commandTranslations.get(Commands.REST); }
  public String verboseShoot(){ return commandTranslations.get(Commands.VERBOSE_SHOOT); }
  public String shoot(){ return commandTranslations.get(Commands.SHOOT); }
  public char shootChar() { return shoot().toCharArray()[0]; }
  public String verboseGo(){ return commandTranslations.get(Commands.VERBOSE_GO); }
  public String verboseEast(){ return commandTranslations.get(Commands.VERBOSE_EAST); }
  public String east(){ return commandTranslations.get(Commands.EAST); }
  public String verboseWest(){ return commandTranslations.get(Commands.VERBOSE_WEST); }
  public String west(){ return commandTranslations.get(Commands.WEST); }
  public String verboseNorth(){ return commandTranslations.get(Commands.VERBOSE_NORTH); }
  public String north(){ return commandTranslations.get(Commands.NORTH); }
  public String verboseSouth(){ return commandTranslations.get(Commands.VERBOSE_SOUTH); }
  public String south(){ return commandTranslations.get(Commands.SOUTH); }
}
