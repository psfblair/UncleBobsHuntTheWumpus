package HuntTheWumpus.Command;

public class MovePlayer implements Command {
  private String direction;

  public MovePlayer(String direction) {
    this.direction = direction;
  }

  public void Dispatch(GameController controller) {
    controller.invoke(this);
  }

  public String getDirection() {
    return direction;
  }
}
