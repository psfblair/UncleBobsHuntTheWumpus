package HuntTheWumpus.Core.Input;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.Scenarios;

public class RequestModel {
  private Scenarios scenario;
  private Direction direction;
  private String unknownCommand;

  public void setScenario(Scenarios scenario) {
    this.scenario = scenario;
  }

  public boolean requestedScenarioIs(Scenarios scenario) {
    return this.scenario.equals(scenario);
  }

  public Direction getDirection() {
    return direction;
  }

  public Scenarios getScenario() {
    return scenario;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public void setUnknownCommand(String commandString) {
    unknownCommand = commandString;
  }

  public String getUnknownCommand() {
    return unknownCommand;
  }
}
