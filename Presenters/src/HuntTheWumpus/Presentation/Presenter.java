package HuntTheWumpus.Presentation;

import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public abstract class Presenter implements Output {
  public void outputResponse(ResponseModel responseModel) {
    printShotArrow(responseModel);
    printCannotMove(responseModel);

    if (responseModel.isGameTerminated()) {
      printCauseOfTermination(responseModel);
      printGameOver();
      return;
    }

    printUnknownCommand(responseModel);
    printTransportMessage(responseModel);
    printArrowsFound(responseModel);
    printQuiverStatus(responseModel);
    printWumpusOdor(responseModel);
    printPitSounds(responseModel);
    printBatSounds(responseModel);
    printAvailableDirections(responseModel);
  }

  protected abstract void printShotArrow(ResponseModel responseModel);
  protected abstract void printCannotMove(ResponseModel responseModel);
  protected abstract void printCauseOfTermination(ResponseModel responseModel);
  protected abstract void printGameOver();
  protected abstract void printUnknownCommand(ResponseModel responseModel);
  protected abstract void printTransportMessage(ResponseModel responseModel);
  protected abstract void printArrowsFound(ResponseModel responseModel);
  protected abstract void printQuiverStatus(ResponseModel responseModel);
  protected abstract void printWumpusOdor(ResponseModel responseModel);
  protected abstract void printPitSounds(ResponseModel responseModel);
  protected abstract void printBatSounds(ResponseModel responseModel);
  protected abstract void printAvailableDirections(ResponseModel responseModel);
}
