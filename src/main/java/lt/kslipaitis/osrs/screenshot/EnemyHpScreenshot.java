package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;

public class EnemyHpScreenshot extends Screenshot {

  public EnemyHpScreenshot(Robot robot) {
    super(7, 65, robot);
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 215, 55);
  }

  @Override
  protected String getFilename() {
    return "enemyHP.png";
  }

}
