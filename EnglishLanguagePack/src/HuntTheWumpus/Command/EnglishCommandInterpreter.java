package HuntTheWumpus.Command;

public class EnglishCommandInterpreter extends TextCommandInterpreter {

  static {
    TextCommandInterpreter.textInputHandlerClassName = ConsoleInputHandler.class.getCanonicalName();
  }

  public EnglishCommandInterpreter() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    super();
    commandTranslations.put(TextCommands.SHOOT, "s");
    commandTranslations.put(TextCommands.VERBOSE_REST, "rest");
    commandTranslations.put(TextCommands.REST, "r");
    commandTranslations.put(TextCommands.VERBOSE_SHOOT, "shoot");
    commandTranslations.put(TextCommands.VERBOSE_GO, "go");
    commandTranslations.put(TextCommands.VERBOSE_EAST, "east");
    commandTranslations.put(TextCommands.EAST, "e");
    commandTranslations.put(TextCommands.VERBOSE_WEST, "west");
    commandTranslations.put(TextCommands.WEST, "w");
    commandTranslations.put(TextCommands.VERBOSE_NORTH, "north");
    commandTranslations.put(TextCommands.NORTH, "n");
    commandTranslations.put(TextCommands.VERBOSE_SOUTH, "south");
    commandTranslations.put(TextCommands.SOUTH, "s");
  }
}
