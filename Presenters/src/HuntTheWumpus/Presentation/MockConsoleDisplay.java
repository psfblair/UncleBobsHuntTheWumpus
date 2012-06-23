package HuntTheWumpus.Presentation;

import java.util.ArrayList;
import java.util.List;

public class MockConsoleDisplay implements TextDisplay {
  private List<String> messages = new ArrayList<String>();
  private boolean checked = false;

  public void print(String message) {
    if (checked) {
      messages.clear();
      checked = false;
    }
    messages.add(message);
  }

  public boolean check(String s) {
    checked = true;
    return messages.contains(s);
  }
}
