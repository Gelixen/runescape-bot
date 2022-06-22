package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class WholeScreenshot extends Screenshot {

  public WholeScreenshot(Robot robot) {
    super(0, 81, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 2170, 1260);
  }

  @Override
  protected String getFilename() {
    return "whole.png";
  }

}
