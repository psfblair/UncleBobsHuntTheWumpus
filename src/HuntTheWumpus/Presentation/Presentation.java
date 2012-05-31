package HuntTheWumpus.Presentation;

public interface Presentation {

  void outputResponse(ResponseModel responseModel);

  void printUnknownCommand(String command);

  void printShotArrow();

  void printNoArrows();

  void printCannotMove(String direction);
}
