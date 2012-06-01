package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.Scenarios;
import HuntTheWumpus.Core.Input.GameController;
import HuntTheWumpus.Core.Input.RequestModel;

import java.util.HashMap;
import java.util.Map;

public abstract class TextCommandInterpreter {

  private GameController controller;

  protected TextCommandInterpreter(GameController controller) {
    this.controller = controller;
  }

  public void execute(String commandString) {
    RequestModel requestModel = getRequestModel(commandString);
    controller.execute(requestModel);
  }

  protected Map<TextCommands, String> commandTranslations = new HashMap();

  public RequestModel getRequestModel(String commandString) {
    RequestModel requestModel = new RequestModel();
    String[] tokens = tokenizeInput(commandString);

    if (isRestCommand(tokens)) {
      requestModel.setScenario(Scenarios.REST);
    } else if (isShootCommand(tokens)) {
      createShootRequest(tokens[1], requestModel);
    }
    else if (isSingleWordShootCommand(tokens)) {
      createShootRequest(tokens[0].substring(1), requestModel);
    }
    else if (isGoCommand(tokens)) {
      createMoveRequest(tokens[1], requestModel);
    }
    else if (isImplicitGoCommand(tokens)) {
      createMoveRequest(tokens[0], requestModel);
    } else {
      requestModel.setScenario(Scenarios.UNKNOWN);
    }

    if (requestModel.getScenario().equals(Scenarios.UNKNOWN)) {
      requestModel.setUnknownCommand(commandString);
    }
    return requestModel;
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

  private void createShootRequest(String token, RequestModel requestModel) {
    Direction direction = directionFromName(token);
    if (direction != null) {
      requestModel.setScenario(Scenarios.SHOOT);
      requestModel.setDirection(direction);
    } else {
      requestModel.setScenario(Scenarios.UNKNOWN);
    }
    return;
  }

  private boolean isGoCommand(String[] tokens) {
    return tokens.length == 2 && tokens[0].equals(verboseGo());
  }

  private boolean isImplicitGoCommand(String[] tokens) {
    return tokens.length == 1 && directionFromName(tokens[0]) != null;
  }

  private void createMoveRequest(String token, RequestModel requestModel) {
    Direction direction = directionFromName(token);
    if (direction != null) {
      requestModel.setScenario(Scenarios.MOVE);
      requestModel.setDirection(direction);
    } else {
      requestModel.setScenario(Scenarios.UNKNOWN);
    }
    return;
  }

  public Direction directionFromName(String name) {
    if (name.equals(east()) || name.equals(verboseEast())) return Direction.EAST;
    else if (name.equals(west()) || name.equals(verboseWest())) return Direction.WEST;
    else if (name.equals(north()) || name.equals(verboseNorth())) return Direction.NORTH;
    else if (name.equals(south()) || name.equals(verboseSouth())) return Direction.SOUTH;
    else return null;
  }

  public String verboseRest() { return commandTranslations.get(TextCommands.VERBOSE_REST); }
  public String rest() { return commandTranslations.get(TextCommands.REST); }
  public String verboseShoot(){ return commandTranslations.get(TextCommands.VERBOSE_SHOOT); }
  public String shoot(){ return commandTranslations.get(TextCommands.SHOOT); }
  public char shootChar() { return shoot().toCharArray()[0]; }
  public String verboseGo(){ return commandTranslations.get(TextCommands.VERBOSE_GO); }
  public String verboseEast(){ return commandTranslations.get(TextCommands.VERBOSE_EAST); }
  public String east(){ return commandTranslations.get(TextCommands.EAST); }
  public String verboseWest(){ return commandTranslations.get(TextCommands.VERBOSE_WEST); }
  public String west(){ return commandTranslations.get(TextCommands.WEST); }
  public String verboseNorth(){ return commandTranslations.get(TextCommands.VERBOSE_NORTH); }
  public String north(){ return commandTranslations.get(TextCommands.NORTH); }
  public String verboseSouth(){ return commandTranslations.get(TextCommands.VERBOSE_SOUTH); }
  public String south(){ return commandTranslations.get(TextCommands.SOUTH); }
}
