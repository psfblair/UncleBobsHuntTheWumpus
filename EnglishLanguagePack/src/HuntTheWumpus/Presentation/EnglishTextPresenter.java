package HuntTheWumpus.Presentation;

import HuntTheWumpus.Core.Constants.Direction;

public class EnglishTextPresenter extends TextPresenter {

  static {
    TextPresenter.textDisplayClassName = Console.class.getCanonicalName();
  }

  public EnglishTextPresenter() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    super();
  }

  protected String unknownCommandResponse(String command) {
    return "I don't know how to " + command + ".";
  }

  protected String noArrowsResponse() {
    return "You don't have any arrows.";
  }

  protected String arrowShotResponse() {
    return "The arrow flies away in silence.";
  }

  protected String cannotMoveResponse(Direction direction) {
    return "You can't go " + directionName(direction) + " from here.";
  }

  protected String batTransportResponse() {
    return "A swarm of angry bats has carried you off.";
  }

  protected String batSoundsResponse() {
    return "You hear chirping.";
  }

  protected String pitSoundsResponse() {
    return "You hear wind.";
  }

  protected String wumpusOdorResponse() {
    return "You smell the Wumpus.";
  }

  protected String quiverStatusNoArrowsResponse() {
    return "You have no arrows.";
  }

  protected String quiverStatusOneArrowResponse() {
    return "You have 1 arrow.";
  }

  protected String quiverStatusCountResponse(int quiverCount) {
    return "You have " + quiverCount + " arrows.";
  }

  protected String foundArrowResponse() {
    return "You found an arrow.";
  }

  protected String killedByArrowBounceResponse() {
    return "The arrow bounced off the wall and killed you.";
  }

  protected String fellInPitResponse() {
    return "You fall into a pit and die.";
  }

  protected String killedWumpusResponse() {
    return "You have killed the Wumpus.";
  }

  protected String killedByWumpusResponse() {
    return "The ravenous snarling Wumpus gobbles you down.";
  }

  protected String hitByOwnArrowResponse() {
    return "You were hit by your own arrow.";
  }

  protected String gameOverResponse() {
    return "Game over.";
  }

  protected String northDirectionName() {
    return "north";
  }

  protected String southDirectionName() {
    return "south";
  }

  protected String eastDirectionName() {
    return "east";
  }

  protected String westDirectionName() {
    return "west";
  }

  protected String unknownDirectionName() {
    return "tilt";
  }

  protected String noExitsResponse() {
    return "There are no exits!";
  }

  protected String availableDirectionsResponse(StringBuffer directions) {
    return "You can go " + directions + " from here.";
  }

  protected String and() {
    return " and ";
  }

  protected String comma() {
    return ", ";
  }
}
