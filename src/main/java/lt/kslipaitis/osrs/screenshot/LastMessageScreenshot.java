package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class LastMessageScreenshot extends Screenshot {

  public LastMessageScreenshot(Robot robot) {
    super(15, 1163, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY + 147, 725, 23);
  }

  @Override
  protected String getFilename() {
    return "last-message.png";
  }

}
