package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class InventoryScreenshot extends Screenshot {

  public InventoryScreenshot(Robot robot) {
    super(2210, 950, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 270, 385);
  }

  @Override
  protected String getFilename() {
    return "inventory.png";
  }

}
