package HuntTheWumpus;

public class GameController {

  private Console console;
  private final Game game = new Game();
    private GamePresenter presenter;

    public GameController() {
    this(null);
  }

  public GameController(Console console) {
    this.console = console;
    presenter = new GamePresenter(console, game);
  }

  public boolean execute(String command) {
    boolean valid = true;
    int arrowsInQuiver = game.getQuiver();
    String[] tokens = command.toLowerCase().split(" ");
    if (isShootCommand(tokens))
      shootArrow(directionFromName(tokens[1]));
    else if (isSingleWordShootCommand(tokens))
      shootArrow(directionFromName(tokens[0].substring(1)));
    else if (isGoCommand(tokens))
      movePlayer(directionFromName(tokens[1]));
    else if (isRestCommand(tokens))
      game.rest();
    else if (isImplicitGoCommand(tokens)) {
      String direction = directionFromName(tokens[0]);
      if (direction != null)
        movePlayer(direction);
    } else {
        presenter.printUnknownCommand(command);
        valid = false;
    }

    presenter.printEndOfTurnMessages(arrowsInQuiver);
    return valid;
  }

    private boolean isSingleWordShootCommand(String[] tokens) {
    return tokens[0].charAt(0) == 's' &&
           directionFromName(tokens[0].substring(1)) != null;
  }

  private boolean isImplicitGoCommand(String[] tokens) {
    return tokens.length == 1 &&
           directionFromName(tokens[0]) != null;
  }

  private boolean isRestCommand(String[] tokens) {
    return tokens[0].equals("r") ||
           tokens[0].equals("rest");
  }

  private boolean isGoCommand(String[] tokens) {
    return tokens.length == 2 &&
           tokens[0].equals("go");
  }

  private boolean isShootCommand(String[] tokens) {
    return tokens.length == 2 &&
           (tokens[0].equals("shoot") ||
            tokens[0].equals("s"));
  }

  private void shootArrow(String direction) {
    if (game.shoot(direction) == false)
      presenter.printNoArrows();
    else
      presenter.printShotArrow();
  }


  private void movePlayer(String direction) {
    if (game.move(direction) == false)
      presenter.printCannotMove(direction);
  }

  private String directionFromName(String name) {
    if (name.equals("e") || name.equals("east")) return Game.EAST;
    else if (name.equals("w") || name.equals("west")) return Game.WEST;
    else if (name.equals("n") || name.equals("north")) return Game.NORTH;
    else if (name.equals("s") || name.equals("south")) return Game.SOUTH;
    else return null;
  }

    public Game getGame() {
    return game;
  }

} // public class GamePresenter

