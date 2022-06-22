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
public class EdgevilleBodyRunecraftingBot extends RunecraftingBot {

  private final RandomUtils randomUtils;

  //  runs
  // k exp
  public EdgevilleBodyRunecraftingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    randomUtils = allUtils.getRandomUtils();
  }

  protected void moveToAltar() {
    moveNearGate();
    moveNearAltar();
  }

  private void moveNearGate() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2333, 246, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(28);
  }

  private void moveNearAltar() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2294, 187, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(25);
  }

  protected void enterAltarArea() throws URISyntaxException, IOException {
    log.info("Entering altar area...");
    screenshotMiddle.takeScreenshot();
    CoordsWithScore coordinate = OpenCVStuff.findTemplateCoords("middle", "altar/body", "template1",
        3);
    System.out.println(coordinate);
    Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordinate.getX(),
        coordinate.getY());
    robotUtils.moveAndLeftClick(adjustedCoordinate.getX(), adjustedCoordinate.getY());
    sleepUtils.random(1);
  }

  protected void runecraft() throws URISyntaxException, IOException {
    log.info("Runecrafting...");
    screenshotMiddle.takeScreenshot();
    CoordsWithScore coordinate = OpenCVStuff.findTemplateCoords("middle", "altar/body", "template2",
        3);
    System.out.println(coordinate);
    Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordinate.getX(),
        coordinate.getY());
    robotUtils.moveAndLeftClick(adjustedCoordinate.getX(), adjustedCoordinate.getY());
    sleepUtils.random(7);
  }

  public void exitAltarArea() throws URISyntaxException, IOException {
    screenshotMiddle.takeScreenshot();
    CoordsWithScore coordinate = OpenCVStuff.findTemplateCoords("middle", "altar/body", "template3",
        3);
    System.out.println(coordinate);
    Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordinate.getX(),
        coordinate.getY());

    //    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(1200, 1170, 5);
    robotUtils.moveAndLeftClick(adjustedCoordinate.getX(), adjustedCoordinate.getY());
    sleepUtils.random(6);
  }

  public void moveToBank() {
    moveBackNearGate();
    moveBackNearBank();
  }

  private void moveBackNearGate() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2467, 97, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(25);
  }

  private void moveBackNearBank() {
    RandomCoordinate coordinate = randomUtils.getRandomCoordinateWithinRadius(2411, 60, 2);
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    sleepUtils.random(28);
  }

  @Override
  String getBankTemplatePath() {
    return "bank/edgeville";
  }

}
