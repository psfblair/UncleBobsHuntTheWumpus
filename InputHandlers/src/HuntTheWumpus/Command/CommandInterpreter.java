package HuntTheWumpus.Command;

import HuntTheWumpus.Core.Input.RequestModel;

public interface CommandInterpreter {
  RequestModel getRequest() throws Exception;
}
