package HuntTheWumpus.Presentation;

import HuntTheWumpus.Core.Direction;
import HuntTheWumpus.Core.Output.ResponseModel;
import HuntTheWumpus.fixtures.MockConsole;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class TextPresenterTest extends TestCase {
  private TextPresenter presenter;
  private MockConsole mc;

  protected void setUp() throws Exception {
    mc = new MockConsole();
    presenter = new TextPresenter(mc);
  }
  public void testNoAvailableDirections(){
    ResponseModel responseModel = new ResponseModel();
    responseModel.setAvailableDirections(new HashSet<String>());
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("There are no exits!"));
  }

  public void testPrintSouthIsAvailable() throws Exception {
    ResponseModel responseModel = new ResponseModel();
    Set<Direction> availableDirections = new HashSet<Direction>();
    availableDirections.add(Direction.SOUTH);
    responseModel.setAvailableDirections(availableDirections);
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("You can go south from here."));
  }

  public void testPrintNorthAndSouthAvailable() throws Exception {
    ResponseModel responseModel = new ResponseModel();
    Set<Direction> availableDirections = new HashSet<Direction>();
    availableDirections.add(Direction.SOUTH);
    availableDirections.add(Direction.NORTH);
    responseModel.setAvailableDirections(availableDirections);
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("You can go north and south from here."));
  }

  public void testPrintFourDirectionsAvailable() throws Exception {
    ResponseModel responseModel = new ResponseModel();
    Set<Direction> availableDirections = new HashSet<Direction>();
    availableDirections.add(Direction.SOUTH);
    availableDirections.add(Direction.WEST);
    availableDirections.add(Direction.NORTH);
    availableDirections.add(Direction.EAST);
    responseModel.setAvailableDirections(availableDirections);
    presenter.printAvailableDirections(responseModel);
    assertTrue(mc.check("You can go north, south, east and west from here."));
  }

  public void testPrintCannotGoEast() {
    ResponseModel responseModel = new ResponseModel();
    responseModel.setCannotMoveInRequestedDirection(true);
    responseModel.setRequestedDirection(Direction.EAST);
    presenter.printCannotMove(responseModel);
    assertTrue(mc.check("You can't go east from here."));
  }

  public void testPrintCannotGoWest() {
    ResponseModel responseModel = new ResponseModel();
    responseModel.setCannotMoveInRequestedDirection(true);
    responseModel.setRequestedDirection(Direction.WEST);
    presenter.printCannotMove(responseModel);
    assertTrue(mc.check("You can't go west from here."));
  }

  public void testPrintCannotGoNorth() {
    ResponseModel responseModel = new ResponseModel();
    responseModel.setCannotMoveInRequestedDirection(true);
    responseModel.setRequestedDirection(Direction.NORTH);
    presenter.printCannotMove(responseModel);
    assertTrue(mc.check("You can't go north from here."));
  }

  public void testPrintCannotGoSouth() {
    ResponseModel responseModel = new ResponseModel();
    responseModel.setCannotMoveInRequestedDirection(true);
    responseModel.setRequestedDirection(Direction.SOUTH);
    presenter.printCannotMove(responseModel);
    assertTrue(mc.check("You can't go south from here."));
  }
}
