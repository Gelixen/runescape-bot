package lt.kslipaitis.osrs.processor;

import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.BankUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;

@Log4j2
public class BankProcessor {

  private final SleepUtils sleepUtils;
  private final RobotUtils robotUtils;
  private final BankUtils bankUtils;
  private final StatusProcessor statusProcessor;
  private final OptionsProcessor optionsProcessor;
  private final Screenshot screenshotWhole;

  public BankProcessor(SleepUtils sleepUtils,
      RobotUtils robotUtils,
      BankUtils bankUtils,
      StatusProcessor statusProcessor,
      OptionsProcessor optionsProcessor,
      Screenshot screenshotWhole) {
    this.sleepUtils = sleepUtils;
    this.robotUtils = robotUtils;
    this.bankUtils = bankUtils;
    this.statusProcessor = statusProcessor;
    this.optionsProcessor = optionsProcessor;
    this.screenshotWhole = screenshotWhole;
  }

  public void closeWindow() {
    RandomCoordinate coordinate = bankUtils.getCloseWindowCoordinate();
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }

  public void takeFromFirstSlot() {
    RandomCoordinate coordinate = bankUtils.getFirstSlotCoords();
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }

  public void takeFromSecondSlot() {
    RandomCoordinate coordinate = bankUtils.getSecondSlotCoords();
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }

  public void depositInventory() throws URISyntaxException, IOException {
    log.info("Depositing...");

    RandomCoordinate coordinate = bankUtils.getDepositInventoryCoordinate();

    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());
    sleepUtils.randomMillis(500);
    if (statusProcessor.textContains("Deposit inventory")) {
      robotUtils.clickLeft();
      sleepUtils.random(1); // <--- ???
    } else {
      log.error("{}, {} not a deposit inventory button", coordinate.getX(), coordinate.getY());
      System.exit(100);
    }
  }

  public void takeFromNthSlotWithNthOption(int slotNumber, int optionNumber)
      throws InterruptedException {
    int slotIndex = slotNumber - 1;

    RandomCoordinate coordinate = bankUtils.getNthSlotCoords(slotIndex / 8, slotIndex % 8);
    robotUtils.moveAndRightClick(coordinate.getX(), coordinate.getY());

    optionsProcessor.selectNthOption(optionNumber);
  }

  public void takeFromFirstSlotIf(String item) throws URISyntaxException, IOException {
    RandomCoordinate coordinate = bankUtils.getFirstSlotCoords();
    getItemFrom(item, coordinate);
  }

  private void getItemFrom(String item, RandomCoordinate coordinate)
      throws URISyntaxException, IOException {
    getItemFrom(item, coordinate, 1);
  }

  private void getItemFrom(String item,
      RandomCoordinate coordinate,
      int timesToClick) throws URISyntaxException, IOException {
    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());
    if (statusProcessor.textContains(item)) {
      for (int i = 0; i < timesToClick; i++) {
        robotUtils.clickLeft();
      }
    } else {
      log.error("{} not a {}", statusProcessor.getText(), item);
      screenshotWhole.takeScreenshot();
      System.exit(100);
    }
  }

  public void takeFromSecondSlotIf(String item) throws URISyntaxException, IOException {
    takeFromSecondSlotNTimesIf(item, 1);
  }

  public void takeFromSecondSlotNTimesIf(String item, int timesToClick)
      throws URISyntaxException, IOException {
    RandomCoordinate coordinate = bankUtils.getSecondSlotCoords();
    getItemFrom(item, coordinate, timesToClick);
  }
}
