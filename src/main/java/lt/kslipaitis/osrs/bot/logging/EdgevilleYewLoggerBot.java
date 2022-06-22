package lt.kslipaitis.osrs.bot.logging;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.BankUtils;
import lt.kslipaitis.osrs.util.InventoryUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;
import net.sourceforge.tess4j.TesseractException;

@Log4j2
public class EdgevilleYewLoggerBot implements Bot {

  private static final String BOTTOM_POSITION = "BOTTOM";
  private static final String TOP_POSITION = "TOP";

  private final RobotUtils robotUtils;
  private final RandomUtils randomUtils;
  private final InventoryUtils inventoryUtils;
  private final SleepUtils sleepUtils;
  private final BankUtils bankUtils;
  private final InventoryProcessor inventoryProcessor;
  private final StatusProcessor statusProcessor;
  private final BankProcessor bankProcessor;

  private final RandomCoordinate TOP_TREE_FROM_TOP;
  private final RandomCoordinate BOTTOM_TREE_FROM_TOP;
  private final RandomCoordinate TOP_TREE_FROM_BOTTOM;
  private final RandomCoordinate BOTTOM_TREE_FROM_BOTTOM;
  private final Screenshot screenshotMiddle;

  private final Map<Boolean, MoveAndCheckCoordinate> coordinatesMapByPosition = new HashMap<>();

  // 190 logs / hour
  // 33k exp / hour
  public EdgevilleYewLoggerBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    robotUtils = allUtils.getRobotUtils();
    randomUtils = allUtils.getRandomUtils();
    inventoryUtils = allUtils.getInventoryUtils();
    sleepUtils = allUtils.getSleepUtils();
    bankUtils = allUtils.getBankUtils();
    inventoryProcessor = allProcessors.getInventoryProcessor();
    statusProcessor = allProcessors.getStatusProcessor();
    bankProcessor = allProcessors.getBankProcessor();

    TOP_TREE_FROM_TOP = randomUtils.getRandomCoordinates(1164, 554, 1231, 621);
    BOTTOM_TREE_FROM_TOP = randomUtils.getRandomCoordinates(1112, 1188, 1217, 1300);
    TOP_TREE_FROM_BOTTOM = randomUtils.getRandomCoordinates(1176, 279, 1237, 356);
    BOTTOM_TREE_FROM_BOTTOM = randomUtils.getRandomCoordinates(1140, 737, 1231, 812);
    screenshotMiddle = allScreenshots.getScreenshotMiddle();

    coordinatesMapByPosition.put(true,
        new MoveAndCheckCoordinate(BOTTOM_TREE_FROM_TOP, BOTTOM_TREE_FROM_BOTTOM));
    coordinatesMapByPosition.put(false,
        new MoveAndCheckCoordinate(TOP_TREE_FROM_BOTTOM, TOP_TREE_FROM_TOP));
  }

  public void execute()
      throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException {
    int iterations = 1;
    while (true) {
      moveToTreeFromBank();
      chopTillInventoryFull();
      depositLogs();
      log.info("{} run, gathered {} logs", iterations, iterations * 28);
      iterations++;
    }
  }

  private void moveToTreeFromBank() throws URISyntaxException, TesseractException, IOException {
    log.info("Moving to tree");
    RandomCoordinate coordinate = randomUtils.getRandomCoordinates(780, 1154, 850, 1182);
    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());

    while (!statusProcessor.textContains("Yew")) {
      log.warn("{} not a yew tree. Retrying in 10...", coordinate);
      sleepUtils.random(10);
    }

    robotUtils.clickLeft();
    sleepUtils.random(12);
  }

  private void chopTillInventoryFull()
      throws URISyntaxException, IOException, AWTException, TesseractException {
    //        RandomCoordinate topTreeFromTop = random.getRandomCoordinates(1164, 554, 1231, 621);
    //        RandomCoordinate bottomTreeFromTop = random.getRandomCoordinates(1112, 1188, 1217, 1300);
    //        RandomCoordinate topTreeFromBottom = random.getRandomCoordinates(1176, 279, 1237, 356);
    //        RandomCoordinate bottomTreeFromBottom = random.getRandomCoordinates(1140, 737, 1231, 812);

    checkStatus(TOP_TREE_FROM_TOP);

    boolean isTopPosition = true;

    while (inventoryNotFull()) {
      //            checkStatus(topTreeFromTop);
      moveToWood(isTopPosition);

      isTopPosition = !isTopPosition;
      //            checkStatus(bottomTreeFromBottom);
      //            moveToWood(false);
    }

  }

  private void depositLogs()
      throws URISyntaxException, IOException, AWTException, InterruptedException, TesseractException {
    moveToBank();
    depositToBank();
  }

  private void checkStatus(RandomCoordinate choppingCoordinate)
      throws TesseractException, URISyntaxException, IOException {
    long chopStartTime = System.currentTimeMillis();

    robotUtils.moveMouse(choppingCoordinate.getX(), choppingCoordinate.getY());
    while (statusProcessor.textContains("Yew") && inventoryNotFull()) {
      sleepUtils.random(5);
    }

    log.info("Finished yew chopping in {} s.", (System.currentTimeMillis() - chopStartTime) / 1000);
    screenshotMiddle.takeScreenshot();
  }

  private boolean inventoryNotFull() throws URISyntaxException, IOException {
    return !inventoryProcessor.isLastItem(ItemTemplate.YEW_LOG);
  }

  private void moveToWood(boolean isTopPosition)
      throws TesseractException, URISyntaxException, IOException {
    MoveAndCheckCoordinate coordinates = coordinatesMapByPosition.get(isTopPosition);
    RandomCoordinate moveCoordinate = coordinates.getMoveCoordinate();
    RandomCoordinate checkCoordinate = coordinates.getCheckCoordinate();

    robotUtils.moveMouse(moveCoordinate.getX(), moveCoordinate.getY());
    if (statusProcessor.textContains("Yew")) {
      log.info("Moving to chop...");
      robotUtils.clickLeft();
      sleepUtils.random(5);

      checkStatus(checkCoordinate);
    }
  }

  private void moveToBank() throws URISyntaxException, IOException, AWTException {
    RandomCoordinate bankCoordinateFromTop = randomUtils.getRandomCoordinates(1525, 339, 1555, 367);
    RandomCoordinate bankCoordinateFromBottom = randomUtils.getRandomCoordinates(1488, 162, 1514,
        183);
    log.info("Moving to bank");
    if (foundBankToMoveTo(bankCoordinateFromTop) || foundBankToMoveTo(bankCoordinateFromBottom)) {
      sleepUtils.random(12);
    } else {
      log.error("No banks found");
      System.exit(123);
    }
  }

  private void depositToBank() throws TesseractException, URISyntaxException, IOException {
    bankProcessor.depositInventory();
    //        RandomCoordinate coords = inventory.getFirstSlotCoordinate();
    //        log.info("Deposit {} to bank", coords);
    //        robot.moveAndLeftClick(coords.getX(), coords.getY());
  }

  private boolean foundBankToMoveTo(RandomCoordinate bankCoordinate)
      throws URISyntaxException, IOException, AWTException {
    robotUtils.moveMouse(bankCoordinate.getX(), bankCoordinate.getY());

    if (statusProcessor.isBank()) {
      robotUtils.clickLeft();
      return true;
    }

    log.warn("Bank at {} not found", bankCoordinate);
    return false;
  }
}
