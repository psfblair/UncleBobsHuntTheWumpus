package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.GameOverReason;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class MovePlayer extends Scenario {
  private Direction direction;
  private boolean cannotMoveInRequestedDirection = false;

  public MovePlayer(Game game, Output output, Direction direction) {
    super(game, output);
    this.direction = direction;
  }

  @Override
  public void invoke() {
    int destination = caverns.adjacentTo(direction, player.getPlayerCavern());
    if (destination != 0) {
      player.putPlayerInCavern(destination);
      checkIfWumpusEatsPlayer();
      checkIfPlayerFallsIntoPit();
      checkIfPlayerIsTransportedByBats();
      pickUpArrow();
      wumpusMoves();
    } else {
      cannotMoveInRequestedDirection();
    }
    super.invoke();
  }

  @Override
  public ResponseModel prepareResponseModel() {
    responseModel.setRequestedDirection(direction);
    responseModel.setCannotMoveInRequestedDirection(cannotMoveInRequestedDirection);
    return super.prepareResponseModel();
  }

  private void checkIfPlayerFallsIntoPit() {
    if (player.isInCavernWithPit()) {
      terminateGame(GameOverReason.FELL_IN_PIT);
    }
  }

  private void checkIfPlayerIsTransportedByBats() {
    while (player.isInCavernWithBats()) {
      transportPlayer();
      batTransport = true;
    }
  }

  private void pickUpArrow() {
    if (player.playerIsInCavernWithArrow()) {
      player.pickUpArrow();
    }
  }

  private void cannotMoveInRequestedDirection() {
    cannotMoveInRequestedDirection = true;
  }

  // For Testing
  public boolean isTransportedByBats() {
    return batTransport;
  }
}
