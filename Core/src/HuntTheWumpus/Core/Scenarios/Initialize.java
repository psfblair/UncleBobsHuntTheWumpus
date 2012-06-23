package HuntTheWumpus.Core.Scenarios;

import HuntTheWumpus.Core.Constants.Direction;
import HuntTheWumpus.Core.Game;
import HuntTheWumpus.Core.Output.Output;

import java.util.Set;

public class Initialize extends Scenario {
  private InitializationParameters parameters;
  private final int playerCavern;
  private final int numberOfArrows;
  private final int wumpusCavern;

  public Initialize(Game game, Output output, InitializationParameters parameters) {
    super(game, output);
    this.parameters = parameters;
    this.playerCavern = parameters.playerCavern;
    this.numberOfArrows = parameters.numberOfArrows;
    this.wumpusCavern = parameters.wumpusCavern;
  }

  @Override
  public void invoke() {
    initializePaths();
    initializePits();
    initializeBats();
    player.putPlayerInCavern(playerCavern);
    player.setQuiver(numberOfArrows);
    wumpus.startInCavern(wumpusCavern);
    super.invoke();
  }

  private void initializePaths() {
    for(PathInitializer pathInitializer : parameters.pathInitializers) {
      caverns.addPath(pathInitializer.start, pathInitializer.end, pathInitializer.direction);
    }

  }

  private void initializePits() {
    for(Integer pitLocation : parameters.pits) {
      caverns.putPitInCavern(pitLocation);
    }
  }

  private void initializeBats() {
    for(Integer batLocation : parameters.bats) {
      caverns.putPitInCavern(batLocation);
    }
  }

  public static class InitializationParameters {
    private Set<PathInitializer> pathInitializers;
    private Set<Integer> pits;
    private Set<Integer> bats;
    private final int playerCavern;
    private final int wumpusCavern;
    private final int numberOfArrows;

    public InitializationParameters(Set<PathInitializer> pathInitializers, int playerCavern, int wumpusCavern, int numberOfArrows) {
      this.pathInitializers = pathInitializers;
      this.playerCavern = playerCavern;
      this.wumpusCavern = wumpusCavern;
      this.numberOfArrows = numberOfArrows;
    }

    public void setPits(Set<Integer> pits) {
      this.pits = pits;
    }

    public void setBats(Set<Integer> bats) {
      this.bats = bats;
    }
  }

  public static PathInitializer pathFor(int start, int end, Direction direction) {
    return new PathInitializer(start, end, direction);
  }

  public static class PathInitializer {
    public final int start;
    public final int end;
    public final Direction direction;

    public PathInitializer(int start, int end, Direction direction) {
      this.start = start;
      this.end = end;
      this.direction = direction;
    }
  }
}
