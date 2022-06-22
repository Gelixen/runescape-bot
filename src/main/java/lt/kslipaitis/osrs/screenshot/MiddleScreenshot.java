package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class MiddleScreenshot extends Screenshot {

  public MiddleScreenshot(Robot robot) {
    super(800, 300, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 1000, 1000);
  }

  @Override
  protected String getFilename() {
    return "middle.png";
  }

}
