package lt.kslipaitis.osrs.processor;

import lt.kslipaitis.osrs.util.OptionsUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;
import lt.kslipaitis.osrs.util.RobotUtils;

import java.awt.*;

public class OptionsProcessor {

  private final RobotUtils robotUtils;
  private final OptionsUtils optionsUtils;

  public OptionsProcessor(RobotUtils robotUtils, OptionsUtils optionsUtils, RandomUtils randomUtils) {
    this.robotUtils = robotUtils;
    this.optionsUtils = optionsUtils;
  }

  public void selectSecondOption() {
    Point position = MouseInfo.getPointerInfo().getLocation();

    RandomCoordinate coordinate = optionsUtils.getSecondOptionRandomCoordinate(position.x, position.y);

    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }

  public void selectNthOption(int optionNumber) {
    Point position = MouseInfo.getPointerInfo().getLocation();

    RandomCoordinate coordinate = optionsUtils.getNthOptionRandomCoordinate(position.x, position.y, optionNumber);

    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }
}
