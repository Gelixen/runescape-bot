package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class CustomScreenshot extends Screenshot {

  public CustomScreenshot(Robot robot) {
    super(0, 81, robot);
  }

  public Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 218, 142);
  }

  public String getFilename() {
    return "custom.png";
  }

}
