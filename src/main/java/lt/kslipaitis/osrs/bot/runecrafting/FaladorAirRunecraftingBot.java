package lt.kslipaitis.osrs.bot.runecrafting;

import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;

@Log4j2
public class FaladorAirRunecraftingBot extends RunecraftingBot {

  private final RandomUtils randomUtils;

  // 28 runs
  // 3.3k exp
  public FaladorAirRunecraftingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    randomUtils = allUtils.getRandomUtils();
  }

  protected void moveToAltar() {
    moveNearGate();
    moveNearAltar();
  }

  private void moveNearGate() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2357, 256, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(28);
  }

  private void moveNearAltar() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2316, 228, 5);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(20);
  }

  protected void enterAltarArea() throws URISyntaxException, IOException {
    log.info("Entering altar area...");
    screenshotMiddle.takeScreenshot();
    CoordsWithScore coordinate = OpenCVStuff.findTemplateCoords("middle", "altar/air", "template1",
        3);
    Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordinate.getX(),
        coordinate.getY());
    robotUtils.moveAndLeftClick(adjustedCoordinate.getX(), adjustedCoordinate.getY());
    sleepUtils.random(3);
  }

  protected void runecraft() {
    log.info("Runecrafting...");
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(1370, 545, 30);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(5);
  }

  public void exitAltarArea() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(1156, 882, 10);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(3);
  }

  public void moveToBank() {
    moveBackNearGate();
    moveBackNearBank();
  }

  private void moveBackNearGate() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2442, 61, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(25);
  }

  private void moveBackNearBank() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2388, 38, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(32);
  }

  @Override
  String getBankTemplatePath() {
    return "bank/falador/east";
  }

}
