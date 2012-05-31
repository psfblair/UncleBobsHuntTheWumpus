package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Actors.GameCaverns;
import HuntTheWumpus.Core.Actors.Player;
import HuntTheWumpus.Presentation.Presentation;
import HuntTheWumpus.Presentation.ResponseModel;

import java.util.Set;

public abstract class Scenario {
  private ResponseModel responseModel;
  protected Player player;
  protected Game game;
  protected Presentation presenter;
  private GameCaverns caverns;
  private int arrowsInQuiverBeforeTurn;

  protected Scenario(Game game, Presentation presenter) {
    this.game = game;
    this.presenter = presenter;
    this.player = game.getPlayer();
    this.caverns = game.getGameCaverns();

    this.game.resetBatTransport();
    this.arrowsInQuiverBeforeTurn = player.getQuiver();
    initializeResponseModel();
  }

  public void Invoke() {
    output();
  }

  private void initializeResponseModel() {
    responseModel = new ResponseModel();
  }

  protected void output() {
    ResponseModel responseModel = prepareResponseModel();
    presenter.printEndOfTurnMessages(responseModel);
  }

  // TODO - Remove? This is public b/c of tests
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
