package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class MapScreenshot extends Screenshot {

  public MapScreenshot(Robot robot) {
    super(2260, 35, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 230, 230);
  }

  @Override
  protected String getFilename() {
    return "map.png";
  }

}
