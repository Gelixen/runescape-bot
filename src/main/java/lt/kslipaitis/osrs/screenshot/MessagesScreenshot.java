package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class MessagesScreenshot extends Screenshot {

  public MessagesScreenshot(Robot robot) {
    super(15, 1163, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 725, 165);
  }

  @Override
  protected String getFilename() {
    return "messages.png";
  }

}
