package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Actors.Wumpus;
import HuntTheWumpus.Core.Constants.GameOverReason;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.Core.Actors.Player;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

import java.util.Set;

public abstract class Scenario {
  protected ResponseModel responseModel;
  protected Player player;
  protected Game game;
  protected Output output;
  protected GameCaverns caverns;
  private int arrowsInQuiverBeforeTurn;
  protected GameOverReason gameTerminationReason;
  protected boolean batTransport;
  protected Wumpus wumpus;

  protected Scenario(Game game, Output output) {
    this.game = game;
    this.output = output;
    this.player = game.getPlayer();
    this.caverns = game.getGameCaverns();
    this.wumpus = game.getWumpus();

    this.arrowsInQuiverBeforeTurn = player.getQuiver();
    initializeResponseModel();
  }

  public void invoke() {
    output();
  }

  private void initializeResponseModel() {
    responseModel = new ResponseModel();
  }

  protected void output() {
    ResponseModel responseModel = prepareResponseModel();
    output.outputResponse(responseModel);
  }

  public ResponseModel prepareResponseModel() {
    responseModel.setGameTerminated(game.isGameTerminated());
    responseModel.setReasonGameTerminated(gameTerminationReason);
    responseModel.setArrowsInQuiverBeforeTurn(arrowsInQuiverBeforeTurn);
    responseModel.setQuiver(player.getQuiver());
    responseModel.setBatTransport(batTransport);

    Set availableDirections = caverns.getAvailableDirectionsFrom(player.cavern());
    responseModel.setAvailableDirections(availableDirections);

    responseModel.setCanSmellWumpus(player.isInCavernNextToWumpus());
    responseModel.setCanHearPit(player.isInCavernNextToPit());
    responseModel.setCanHearBats(player.isInCavernNextToBats());
    return responseModel;
  }


  protected void terminateGame(GameOverReason reason) {
    gameTerminationReason = reason;
    game.setGameTerminated(true);
  }

  public GameOverReason gameTerminationReason() {
    return gameTerminationReason;
  }

  public void transportPlayer() {
    player.moveToRandomCavern();
  }

  public void wumpusMoves() {
    wumpus.move();
    checkIfWumpusEatsPlayer();
  }

  protected void checkIfWumpusEatsPlayer() {
    if (player.isInWumpusCavern()) {
      terminateGame(GameOverReason.EATEN_BY_WUMPUS);
    }
  }
}
