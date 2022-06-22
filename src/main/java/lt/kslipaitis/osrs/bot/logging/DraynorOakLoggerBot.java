package lt.kslipaitis.osrs.bot.logging;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;
import net.sourceforge.tess4j.TesseractException;

@Log4j2
public class DraynorOakLoggerBot implements Bot {

  private final RobotUtils robotUtils;
  private final RandomUtils randomUtils;
  private final SleepUtils sleepUtils;
  private final InventoryProcessor inventoryProcessor;
  private final StatusProcessor statusProcessor;
  private final BankProcessor bankProcessor;

  // 950 logs / hour
  // 32k exp / hour
  public DraynorOakLoggerBot(AllUtils allUtils, AllProcessors allProcessors) {
    this.robotUtils = allUtils.getRobotUtils();
    this.randomUtils = allUtils.getRandomUtils();
    this.sleepUtils = allUtils.getSleepUtils();
    this.inventoryProcessor = allProcessors.getInventoryProcessor();
    this.statusProcessor = allProcessors.getStatusProcessor();
    this.bankProcessor = allProcessors.getBankProcessor();
  }

  public void execute()
      throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException {
    int iterations = 1;
    while (true) {
      moveToTree();
      chopTillInventoryFull();
      depositLogs();
      log.info("{} run, gathered {} logs", iterations, iterations * 28);
      iterations++;
    }
  }

  private void moveToTree() throws URISyntaxException, IOException {
    log.info("Moving to tree");
    RandomCoordinate coordinate = randomUtils.getRandomCoordinates(1740, 740, 1828, 840);
    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());

    while (!statusProcessor.textContains("Oak")) {
      log.warn("{} not an oak tree. Retrying in 10...", coordinate);
      sleepUtils.random(10);
    }

    robotUtils.clickLeft();
    sleepUtils.random(5);
  }

  private void chopTillInventoryFull() throws URISyntaxException, IOException {
    boolean treeChopped = false;
    RandomCoordinate coordinate = randomUtils.getRandomCoordinates(1299, 687, 1398, 782);
    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());

    int retryCounter = 0;
    long chopStartTime = System.currentTimeMillis();

    while (inventoryNotFull()) {
      if (statusProcessor.textContains("Oak")) {
        if (treeChopped || retryCounter > 60) {
          log.info("Chopping tree...");
          treeChopped = false;
          robotUtils.clickLeft();
          chopStartTime = System.currentTimeMillis();
        }
        sleepUtils.random(1);
        retryCounter++;
      } else {
        log.info("Tree chopped in {} s. Waiting to respawn.",
            (System.currentTimeMillis() - chopStartTime) / 1000);
        treeChopped = true;
        retryCounter = 0;
        sleepUtils.random(9);
      }
    }
  }

  private void depositLogs() throws URISyntaxException, IOException {
    moveToBank();
    depositToBank();
  }

  private boolean inventoryNotFull() throws URISyntaxException, IOException {
    return !inventoryProcessor.isLastItem(ItemTemplate.OAK_LOG);
  }

  private void moveToBank() throws URISyntaxException, IOException {
    log.info("Moving to bank");
    RandomCoordinate coordinate = randomUtils.getRandomCoordinates(799, 646, 830, 674);
    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());

    if (statusProcessor.textContains("Bank booth")) {
      robotUtils.clickLeft();
      sleepUtils.random(5);
    } else {
      log.error("{} not a bank", coordinate);
      System.exit(123);
    }
  }

  private void depositToBank() throws URISyntaxException, IOException {
    bankProcessor.depositInventory();
  }
}
