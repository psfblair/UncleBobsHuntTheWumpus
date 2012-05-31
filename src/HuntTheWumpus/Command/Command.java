package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.GameCaverns;
import HuntTheWumpus.Core.Player;
import HuntTheWumpus.Presentation.Presentation;
import HuntTheWumpus.Presentation.ResponseModel;

import java.util.Set;

public abstract class Command {
  private ResponseModel responseModel;
  protected Player player;
  protected Game game;
  protected Presentation presenter;
  private GameCaverns caverns;

  protected Command(Game game, Presentation presenter) {
    this.game = game;
    this.presenter = presenter;
    this.player = game.getPlayer();
    this.caverns = game.getGameCaverns();
    initializeResponseModel();
  }

  public void Invoke() {
    output();
  }

  private void initializeResponseModel() {
    responseModel = new ResponseModel();
    responseModel.setArrowsInQuiverBeforeTurn(player.getQuiver());
  }

  protected void output() {
    ResponseModel responseModel = prepareResponseModel();
    presenter.printEndOfTurnMessages(responseModel);
  }

  // TODO - Remove? This is public b/c of tests
  public ResponseModel prepareResponseModel() {
    responseModel.setGameTerminated(game.gameTerminated());
    responseModel.setReasonGameTerminated(game.gameTerminationReason());
    responseModel.setQuiver(player.getQuiver());

    responseModel.setBatTransport(game.isTransportedByBats());
    game.resetBatTransport();

    Set availableDirections = caverns.getAvailableDirectionsFrom(player.getPlayerCavern());
    responseModel.setAvailableDirections(availableDirections);

    responseModel.setCanSmellWumpus(player.isInCavernNextToWumpus());
    responseModel.setCanHearPit(player.isInCavernNextToPit());
    responseModel.setCanHearBats(player.isInCavernNextToBats());
    return responseModel;
  }

}
