package HuntTheWumpus;

public class GamePresenter implements PresentationBoundary {
  private Console console;
  private Game game;

  public GamePresenter(Console console, Game game) {
    this.console = console;
    this.game = game;
  }

  static String directionName(String direction) {
    if (direction.equals(Game.NORTH))
      return "north";
    else if (direction.equals(Game.SOUTH))
      return "south";
    else if (direction.equals(Game.EAST))
      return "east";
    else if (direction.equals(Game.WEST))
      return "west";
    else
      return "tilt";
  }

  public void printUnknownCommand(String command) {
    console.print("I don't know how to " + command + ".");
  }

  public void printEndOfTurnMessages(int arrowsInQuiver) {
    if (game.gameTerminated()) {
      printCauseOfTermination();
      console.print("Game over.");
    } else {
      printTransportMessage();
      printArrowsFound(arrowsInQuiver);
      printQuiverStatus();
      printWumpusOdor();
      printPitSounds();
      printBatSounds();
      printAvailableDirections();
    }
  }

  public void printShotArrow() {
    console.print("The arrow flies away in silence.");
  }

  public void printNoArrows() {
    console.print("You don't have any arrows.");
  }

  public void printCannotMove(String direction) {
    console.print("You can't go " + GamePresenter.directionName(direction) + " from here.");
  }

  //Only really needed package level for testing
  public void printAvailableDirections() {
    console.print(getAvailableDirections());
  }

  private void printTransportMessage() {
    if (game.batTransport()) {
      console.print("A swarm of angry bats has carried off.");
      game.resetBatTransport();
    }
  }

  private void printBatSounds() {
    if (game.canHearBats())
      console.print("You hear chirping.");
  }

  private void printPitSounds() {
    if (game.canHearPit())
      console.print("You hear wind.");
  }

  private void printWumpusOdor() {
    if (game.canSmellWumpus()) {
      console.print("You smell the Wumpus.");
    }
  }

  private void printQuiverStatus() {
    if (game.getQuiver() == 0)
      console.print("You have no arrows.");
    else if (game.getQuiver() == 1)
      console.print("You have 1 arrow.");
    else
      console.print("You have " + game.getQuiver() + " arrows.");
  }

  private void printArrowsFound(int arrowsInQuiver) {
    if (game.getQuiver() > arrowsInQuiver)
      console.print("You found an arrow.");
  }

  private void printCauseOfTermination() {
    if (game.wasKilledByArrowBounce())
      console.print("The arrow bounced off the wall and killed you.");
    else if (game.fellInPit())
      console.print("You fall into a pit and die.");
    else if (game.wumpusHitByArrow())
      console.print("You have killed the Wumpus.");
    else if (game.eatenByWumpus())
      console.print("The ravenous snarling Wumpus gobbles you down.");
    else if (game.hitByOwnArrow())
      console.print("You were hit by your own arrow.");
  }

  private String getAvailableDirections() {
    AvailableDirections directions = new AvailableDirections();

    for (Game.Path p : game.paths) {
      if (p.start == game.playerCavern) {
        directions.addDirection(p.direction);
      }
    }
    return directions.toString();
  }
}
