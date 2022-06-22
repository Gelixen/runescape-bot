package lt.kslipaitis.osrs.bot.runecrafting;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;
import net.sourceforge.tess4j.TesseractException;

@Log4j2
public abstract class RunecraftingBot implements Bot {

  protected final RobotUtils robotUtils;
  protected final SleepUtils sleepUtils;
  protected final BankProcessor bankProcessor;
  protected final Screenshot screenshotMiddle;

  public RunecraftingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {

    robotUtils = allUtils.getRobotUtils();
    sleepUtils = allUtils.getSleepUtils();
    bankProcessor = allProcessors.getBankProcessor();
    screenshotMiddle = allScreenshots.getScreenshotMiddle();
  }

  //  / hour
  //  exp / hour
  public void execute()
      throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException {
    int iterations = 1;
    while (true) {
      takeEssence();
      moveToAltar();
      enterAltarArea();
      runecraft();
      exitAltarArea();
      moveToBank();
      depositRunes();

      log.info("{} run, gathered {} runes", iterations, iterations * 28);
      iterations++;
    }
  }

  void takeEssence() throws URISyntaxException, IOException {
    bankProcessor.takeFromFirstSlotIf("Pure essence");
  }

  abstract void moveToAltar() throws InterruptedException;

  abstract void enterAltarArea() throws InterruptedException, URISyntaxException, IOException;

  abstract void runecraft() throws InterruptedException, URISyntaxException, IOException;

  abstract void exitAltarArea() throws InterruptedException, URISyntaxException, IOException;

  abstract void moveToBank() throws InterruptedException;

  void depositRunes() throws URISyntaxException, IOException {
    screenshotMiddle.takeScreenshot();
    CoordsWithScore coordinate = OpenCVStuff.findTemplateCoords("middle", getBankTemplatePath(),
        "template1", 3);
    Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordinate.getX(),
        coordinate.getY());
    robotUtils.moveAndLeftClick(adjustedCoordinate.getX(), adjustedCoordinate.getY());
    sleepUtils.random(10);

    bankProcessor.depositInventory();
    sleepUtils.random(1);
  }

  abstract String getBankTemplatePath();

}
