package HuntTheWumpus.Presentation;

public interface Presentation {

  void printUnknownCommand(String command);

  void printEndOfTurnMessages(ResponseModel responseModel);

  void printShotArrow();

  void printNoArrows();

  void printCannotMove(String direction);
}
