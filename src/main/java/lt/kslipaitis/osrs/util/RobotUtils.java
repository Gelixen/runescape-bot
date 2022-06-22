package lt.kslipaitis.osrs.util;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class RobotUtils {

  private final Robot robot;
  private final SleepUtils sleep;

  public RobotUtils(Robot robot, SleepUtils sleepUtils) {
    this.robot = robot;
    sleep = sleepUtils;
  }

  public void moveAndRightClick(int x, int y) {
    robot.mouseMove(x, y);
    sleep.randomMillis(500);
    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    sleep.randomMillis(500);
  }

  public void moveAndLeftClick(int x, int y) {
    moveAndLeftClickWithDelay(x, y, 500);
  }

  public void moveAndLeftClickWithDelay(int x, int y, int delay) {
    robot.mouseMove(x, y);
    sleep.randomMillis(delay);
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    sleep.randomMillis(delay);
  }

  public void pressAndReleaseKey(int key) {
    robot.keyPress(key);
    robot.keyRelease(key);
  }

  public void pressKey(int key) {
    robot.keyPress(key);
  }

  public void releaseKey(int key) {
    robot.keyRelease(key);
  }

  public void moveMouse(int x, int y) {
    robot.mouseMove(x, y);
    sleep.random(1);
  }

  public void clickLeft() {
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    sleep.randomMillis(500);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    sleep.randomMillis(500);
  }

  public Color getPixelColor(int x, int y) {
    return robot.getPixelColor(x, y);
  }

}
