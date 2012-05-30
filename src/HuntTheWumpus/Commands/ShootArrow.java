package HuntTheWumpus.Commands;

public class ShootArrow implements Command {
  private String direction;

  public ShootArrow(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return direction;
  }
}
