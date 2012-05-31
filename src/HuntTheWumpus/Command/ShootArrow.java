package HuntTheWumpus.Command;

public class ShootArrow implements Command {
  private String direction;

  public ShootArrow(String direction) {
    this.direction = direction;
  }

  public void Dispatch(GameController controller) {
    controller.invoke(this);
  }

  public String getDirection() {
    return direction;
  }
}
