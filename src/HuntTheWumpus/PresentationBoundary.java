package HuntTheWumpus;

public interface PresentationBoundary {

  void printUnknownCommand(String command);

  void printEndOfTurnMessages(Game.ResponseModel responseModel);

  void printShotArrow();

  void printNoArrows();

  void printCannotMove(String direction);
}
