package HuntTheWumpus.Core.Input;

public interface CommandInterpreter {
  RequestModel getRequest() throws Exception;
}
