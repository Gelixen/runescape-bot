package lt.kslipaitis.osrs.bot;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;

@Log4j2
public class SmithingBot implements Bot {

  private final RobotUtils robotUtils;
  private final RandomUtils randomUtils;
  private final SleepUtils sleepUtils;
  private final StatusProcessor statusProcessor;
  private final BankProcessor bankProcessor;
  private final Screenshot screenshotMiddle;
  private final InventoryProcessor inventoryProcessor;

  // 1k / hour daggers
  // 25k / hour smithing
  // -15k / hour profit
  public SmithingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    robotUtils = allUtils.getRobotUtils();
    randomUtils = allUtils.getRandomUtils();
    sleepUtils = allUtils.getSleepUtils();
    statusProcessor = allProcessors.getStatusProcessor();
    bankProcessor = allProcessors.getBankProcessor();
    inventoryProcessor = allProcessors.getInventoryProcessor();
    screenshotMiddle = allScreenshots.getScreenshotMiddle();
  }

  public void execute() throws InterruptedException, URISyntaxException, IOException, AWTException {
    int toProcess = 5000;
    final int iterations = toProcess / 27;
    for (int i = 0; i < iterations; i++) {
      takeBarsFromBank();
      moveToAnvil();
      smith();
      moveToBank();
      depositItemsToBank();
      log.info("Processed total of {} items", (i + 1) * 27);
      log.info("-----------------------------");
    }
  }

  private void takeBarsFromBank() throws URISyntaxException, IOException {
    log.info("Bars withdraw...");
    bankProcessor.takeFromFirstSlotIf("Iron bar");
  }

  private void moveToAnvil() throws URISyntaxException, IOException {
    log.info("Moving to anvil...");
    RandomCoordinate coords = randomUtils.getRandomCoordinates(1403, 1187, 1428, 1234);
    robotUtils.moveMouse(coords.getX(), coords.getY());

    if (statusProcessor.textContains("Anvil")) {
      robotUtils.clickLeft();
      sleepUtils.random(5);
    } else {
      screenshotMiddle.takeScreenshot();
      log.error("{}, {} not an anvil", coords.getX(), coords.getY());
      System.exit(123);
    }
  }

  private void smith() throws URISyntaxException, IOException {
    log.info("Dagger smithing...");
    RandomCoordinate coords = randomUtils.getRandomCoordinates(731, 403, 774, 467);
    robotUtils.moveMouse(coords.getX(), coords.getY());

    if (statusProcessor.textContains("Iron dagger")) {
      robotUtils.clickLeft();

      sleepUtils.random(80);
    } else {
      log.error("{}, {} not a dagger", coords.getX(), coords.getY());
      screenshotMiddle.takeScreenshot();
      System.exit(123);
    }
  }


  private void moveToBank() {
    log.info("Moving to bank...");
    RandomCoordinate coords = randomUtils.getRandomCoordinates(1160, 366, 1178, 389);
    robotUtils.moveAndLeftClick(coords.getX(), coords.getY());

    sleepUtils.random(5);
  }

  private void depositItemsToBank() {
    log.info("Depositing to bank...");
    inventoryProcessor.leftClickFirstItem();

    //    RandomCoordinate coords = randomUtils.getRandomCoordinates(2235, 957, 2265, 995);
    //    robotUtils.moveAndLeftClick(coords.getX(), coords.getY());
    //    sleepUtils.random(1);
  }
}
