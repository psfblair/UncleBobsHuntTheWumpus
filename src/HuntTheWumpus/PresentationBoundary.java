package HuntTheWumpus;

public interface PresentationBoundary {
  void printUnknownCommand(String command);

  void printEndOfTurnMessages(int arrowsInQuiver);

  void printShotArrow();

  void printNoArrows();

  void printCannotMove(String direction);

  //Only really needed package level for testing
  void printAvailableDirections();
}
