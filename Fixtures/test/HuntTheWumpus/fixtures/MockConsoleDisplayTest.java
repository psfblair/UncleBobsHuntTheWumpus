package HuntTheWumpus.fixtures;

import HuntTheWumpus.Presentation.MockConsoleDisplay;
import junit.framework.Assert;
import junit.framework.TestCase;

public class MockConsoleDisplayTest extends TestCase {
  private MockConsoleDisplay mc;

  protected void setUp() throws Exception {
    mc = new MockConsoleDisplay();
  }

  public void testCheckFailsOnNoMessage() throws Exception {
    Assert.assertFalse(mc.check("message"));
  }


  public void testCheckPassesWithOneMessage() {
    mc.print("message");
    Assert.assertTrue(mc.check("message"));
  }

  public void testCheckPassesWithThreeMessages() {
    mc.print("message one");
    mc.print("message two");
    mc.print("message three");
    Assert.assertTrue(mc.check("message three"));
    Assert.assertTrue(mc.check("message one"));
    Assert.assertTrue(mc.check("message two"));
    Assert.assertFalse(mc.check("message x"));
    Assert.assertFalse(mc.check("message"));
  }

  public void testListClearedBetweenCheckAndPrint() {
    mc.print("message one");
    Assert.assertTrue(mc.check("message one"));
    mc.print("message two");
    Assert.assertFalse(mc.check("message one"));
    Assert.assertTrue(mc.check("message two"));
  }
}
