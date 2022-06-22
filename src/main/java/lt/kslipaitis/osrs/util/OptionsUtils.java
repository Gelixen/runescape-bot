package lt.kslipaitis.osrs.util;

public class OptionsUtils {

  private static final int INITIAL_HEIGHT_OFFSET = 30;
  private static final int OPTION_HEIGHT = 21;
  private static final int OPTION_USUAL_WIDTH = 21;

  private final RobotUtils robot;
  private final RandomUtils randomUtils;


  public OptionsUtils(RobotUtils robot, RandomUtils randomUtils) {
    this.robot = robot;
    this.randomUtils = randomUtils;
  }

  private int getSecondOptionOffset() {
    return getNthOptionOffset(2);
  }

  public int getNthOptionOffset(int index) {
    int totalGap = calculateTotalVariableGapBetweenOptions(index);

    return INITIAL_HEIGHT_OFFSET + totalGap + index * OPTION_HEIGHT + OPTION_HEIGHT / 2;
  }

  // Every second option has a 1px gap instead of 2px
  // Formula: count/2 -> (flooredHalf + 1) * 2 + celledHalf
  public int calculateTotalVariableGapBetweenOptions(int index) {
    double halvingResult = index / 2.0;

    int flooredHalf = (int) Math.floor(halvingResult);
    int celledHalf = (int) Math.ceil(halvingResult);

    return (flooredHalf + 1) * 2 + celledHalf;
  }

  public RandomCoordinate getSecondOptionRandomCoordinate(int x, int y) {
    int xRadius = 30;
    int yRadius = OPTION_HEIGHT / 3;
    return randomUtils.getRandomCoordinateWithinDifferentRadiuses(x, y + getNthOptionOffset(1),
        xRadius, yRadius);
  }

  public RandomCoordinate getNthOptionRandomCoordinate(int x, int y, int optionNumber) {
    int xRadius = 30;
    int yRadius = OPTION_HEIGHT / 3;
    return randomUtils.getRandomCoordinateWithinDifferentRadiuses(x,
        y + getNthOptionOffset(optionNumber - 1),
        xRadius,
        yRadius);
  }
}
