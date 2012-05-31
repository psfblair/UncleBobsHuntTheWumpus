package HuntTheWumpus.Command;

public class Rest implements Command {

  public void Dispatch(GameController controller) {
    controller.invoke(this);
  }
}
