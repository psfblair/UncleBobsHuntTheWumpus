package HuntTheWumpus.Core.Actors;

import HuntTheWumpus.Core.Constants.Direction;

import java.util.ArrayList;
import java.util.List;

public class Wumpus {
  public int wumpusCavern = -1;
  private boolean wumpusFrozen = false;

  private GameCaverns gameCaverns;

  public Wumpus(GameCaverns gameCaverns) {
    this.gameCaverns = gameCaverns;
  }

  public int getWumpusCavern() {
    return wumpusCavern;
  }

  public void startInCavern(int where) {
    wumpusCavern = where;
  }

  boolean isWumpusCavern(int cavern) {
    return cavern == wumpusCavern;
  }

  public boolean isAdjacentTo(int cavern) {
    return gameCaverns.areAdjacent(cavern, wumpusCavern);
  }

  void moveWumpusTo(int selectedMove) {
    wumpusCavern = selectedMove;
  }

  public void freeze() {
    wumpusFrozen = true;
  }

  public void move() {
    if (wumpusFrozen)
      return;
    List<Integer> moves = new ArrayList<Integer>();
    addPossibleMove(Direction.EAST, moves);
    addPossibleMove(Direction.WEST, moves);
    addPossibleMove(Direction.NORTH, moves);
    addPossibleMove(Direction.SOUTH, moves);
    moves.add(0); // rest;

    int selection = (int) (Math.random() * moves.size());
    int selectedMove = moves.get(selection);
    if (selectedMove != 0) {
      moveWumpusTo(selectedMove);
    }
  }

  private void addPossibleMove(Direction direction, List<Integer> moves) {
    int possibleMove;
    possibleMove = gameCaverns.adjacentTo(direction, wumpusCavern);
    if (possibleMove != 0)
      moves.add(possibleMove);
  }
}
