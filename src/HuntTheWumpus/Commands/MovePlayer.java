package HuntTheWumpus.Commands;

public class MovePlayer implements Command {
  private String direction;

  public MovePlayer(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return direction;
  }
}
