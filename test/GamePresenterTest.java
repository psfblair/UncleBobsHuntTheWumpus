import HuntTheWumpus.GameCaverns;
import HuntTheWumpus.Presentation.GamePresenter;
import HuntTheWumpus.Presentation.ResponseModel;
import HuntTheWumpus.fixtures.MockConsole;
import junit.framework.TestCase;

import java.util.HashSet;

public class GamePresenterTest extends TestCase {
  private GamePresenter presenter;
  private MockConsole mc;

  protected void setUp() throws Exception {
    mc = new MockConsole();
    presenter = new GamePresenter(mc);
  }
  public void testNoAvailableDirections(){
    ResponseModel responseModel = new ResponseModel();
    responseModel.setAvailableDirections(new HashSet<String>());
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("There are no exits!"));
  }

  public void testPrintSouthIsAvailable() throws Exception {
    ResponseModel responseModel = new ResponseModel();
    HashSet<String> availableDirections1 = new HashSet<String>();
    availableDirections1.add(GameCaverns.SOUTH);
    responseModel.setAvailableDirections(availableDirections1);
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("You can go south from here."));
  }

  public void testPrintNorthAndSouthAvailable() throws Exception {
    ResponseModel responseModel = new ResponseModel();
    HashSet<String> availableDirections1 = new HashSet<String>();
    availableDirections1.add(GameCaverns.SOUTH);
    availableDirections1.add(GameCaverns.NORTH);
    responseModel.setAvailableDirections(availableDirections1);
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("You can go north and south from here."));
  }

  public void testPrintFourDirectionsAvailable() throws Exception {
    ResponseModel responseModel = new ResponseModel();
    HashSet<String> availableDirections1 = new HashSet<String>();
    availableDirections1.add(GameCaverns.SOUTH);
    availableDirections1.add(GameCaverns.WEST);
    availableDirections1.add(GameCaverns.NORTH);
    availableDirections1.add(GameCaverns.EAST);
    responseModel.setAvailableDirections(availableDirections1);
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("You can go north, south, east and west from here."));
  }

}
