package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Constants.GameOverReason;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;
import HuntTheWumpus.Core.Output.ResponseModel;

public class ShootArrow extends Scenario {
  private Direction direction;
  private boolean arrowWasShot = false;

  public ShootArrow(Game game, Output output, Direction direction) {
    super(game, output);
    this.direction = direction;
  }

  @Override
  public void invoke() {
    if (player.getQuiver() > 0) {
      player.shootArrow();
      arrowWasShot = true;
      determineResultOfShot();
    } else {
      arrowWasShot = false;
    }
    super.invoke();
  }

  private void determineResultOfShot() {
    if (arrowHitsWall()) {
      terminateGame(GameOverReason.KILLED_BY_ARROW_BOUNCE);
      return;
    }
    int endCavern = shootAsFarAsPossible(direction, player.cavern());
    if (!game.isGameTerminated()) {
      caverns.putArrowInCavern(endCavern);
      wumpusMoves();
    }
  }

  private boolean arrowHitsWall() {
    return caverns.thereIsAWallInDirectionFromCavern(direction, player.cavern());
  }

  private int shootAsFarAsPossible(Direction direction, int cavern) {
    int nextCavern = caverns.adjacentTo(direction, cavern);
    if (nextCavern == 0)
      return cavern;
    else {
      if (nextCavern == wumpus.cavern()) {
        terminateGame(GameOverReason.WUMPUS_HIT_BY_ARROW);
        return nextCavern;
      } else if (player.isMyCavern(nextCavern)) {
        terminateGame(GameOverReason.HIT_BY_OWN_ARROW);
        return nextCavern;
      }
      return shootAsFarAsPossible(direction, nextCavern);
    }
  }

  @Override
  public ResponseModel prepareResponseModel() {
    responseModel.setArrowWasShot(arrowWasShot());
    responseModel.setTriedShootingWithNoArrows(! arrowWasShot());
    return super.prepareResponseModel();
  }

  public boolean arrowWasShot() {
    return arrowWasShot;
  }

}
