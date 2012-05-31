package HuntTheWumpus.Core.Scenarios;

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
  private GameCaverns caverns;
  private int arrowsInQuiverBeforeTurn;

  protected Scenario(Game game, Output output) {
    this.game = game;
    this.output = output;
    this.player = game.getPlayer();
    this.caverns = game.getGameCaverns();

    this.game.resetBatTransport();
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
    responseModel.setGameTerminated(game.gameTerminated());
    responseModel.setReasonGameTerminated(game.gameTerminationReason());
    responseModel.setArrowsInQuiverBeforeTurn(arrowsInQuiverBeforeTurn);
    responseModel.setQuiver(player.getQuiver());
    responseModel.setBatTransport(game.isTransportedByBats());

    Set availableDirections = caverns.getAvailableDirectionsFrom(player.getPlayerCavern());
    responseModel.setAvailableDirections(availableDirections);

    responseModel.setCanSmellWumpus(player.isInCavernNextToWumpus());
    responseModel.setCanHearPit(player.isInCavernNextToPit());
    responseModel.setCanHearBats(player.isInCavernNextToBats());
    return responseModel;
  }

}
