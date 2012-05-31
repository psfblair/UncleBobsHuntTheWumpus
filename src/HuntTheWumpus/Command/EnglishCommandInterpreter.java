package HuntTheWumpus.Command;

public class EnglishCommandInterpreter extends CommandInterpreter {

  public EnglishCommandInterpreter() {
    commandTranslations.put(Commands.SHOOT, "s");
    commandTranslations.put(Commands.VERBOSE_REST, "rest");
    commandTranslations.put(Commands.REST, "r");
    commandTranslations.put(Commands.VERBOSE_SHOOT, "shoot");
    commandTranslations.put(Commands.VERBOSE_GO, "go");
    commandTranslations.put(Commands.VERBOSE_EAST, "east");
    commandTranslations.put(Commands.EAST, "e");
    commandTranslations.put(Commands.VERBOSE_WEST, "west");
    commandTranslations.put(Commands.WEST, "w");
    commandTranslations.put(Commands.VERBOSE_NORTH, "north");
    commandTranslations.put(Commands.NORTH, "n");
    commandTranslations.put(Commands.VERBOSE_SOUTH, "south");
    commandTranslations.put(Commands.SOUTH, "s");
  }
}
