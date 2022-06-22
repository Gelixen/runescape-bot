package lt.kslipaitis.osrs.bot.smelting;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;
import net.sourceforge.tess4j.TesseractException;

@Log4j2
public abstract class SmeltingBot implements Bot {

  protected final RobotUtils robotUtils;
  protected final RandomUtils randomUtils;
  protected final SleepUtils sleepUtils;
  protected final StatusProcessor statusProcessor;
  protected final Screenshot screenshotMiddle;

  public SmeltingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    this.robotUtils = allUtils.getRobotUtils();
    this.randomUtils = allUtils.getRandomUtils();
    this.sleepUtils = allUtils.getSleepUtils();
    this.statusProcessor = allProcessors.getStatusProcessor();
    this.screenshotMiddle = allScreenshots.getScreenshotMiddle();
  }

  // 425 / hour
  // 11.5 k smith exp / hour
  // 14k profit / hour
  public void execute()
      throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException {
    int toProcess = 10_000;
    final int iterations = toProcess / 13;
    for (int i = 0; i < iterations; i++) {
      log.info("{} iteration, {} ({}) possibly processed", i, i * 28, i * 13);

      if (i % 5 == 0) {
        takeRingFromTheBank();
      }

      takeInputFromTheBank();
      moveToFurnace();
      smeltInput();
      moveToBank();
      depositOutput();
    }
  }

  public void takeRingFromTheBank() throws InterruptedException {

  }

  abstract void takeInputFromTheBank() throws InterruptedException, URISyntaxException, IOException;

  void moveToFurnace() throws URISyntaxException, IOException {
    RandomCoordinate coords = randomUtils.getRandomCoordinates(1717, 524, 1736, 555);
    robotUtils.moveMouse(coords.getX(), coords.getY());

    if (statusProcessor.textContains("Furnace")) {
      robotUtils.clickLeft();

      sleepUtils.random(10);
    } else {
      log.error("{}, {} not a furnace, but {}", coords.getX(), coords.getY(),
          statusProcessor.getText());
      screenshotMiddle.takeScreenshot();

      System.exit(100);
    }
  }

  abstract void smeltInput() throws InterruptedException, URISyntaxException, IOException;

  void moveToBank() throws URISyntaxException, IOException {
    RandomCoordinate coords = randomUtils.getRandomCoordinates(648, 970, 729, 1006);
    robotUtils.moveMouse(coords.getX(), coords.getY());

    if (statusProcessor.isBank()) {
      robotUtils.clickLeft();

      sleepUtils.random(10);
    } else {
      log.error("{}, {} not a bank", coords.getX(), coords.getY());
      screenshotMiddle.takeScreenshot();
      System.exit(100);
    }
  }

  abstract void depositOutput()
      throws InterruptedException, TesseractException, URISyntaxException, IOException;

}
