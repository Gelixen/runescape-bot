package lt.kslipaitis.osrs.bot.mining;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.MessagesProcessor;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;
import net.sourceforge.tess4j.TesseractException;

@Log4j2
public class SilverMiningBot implements Bot {

  private final RobotUtils robotUtils;
  private final SleepUtils sleepUtils;
  private final Screenshot screenshotMiddle;
  private final InventoryProcessor inventoryProcessor;
  private final StatusProcessor statusProcessor;
  private final MessagesProcessor messagesProcessor;
  private final Screenshot screenshotMap;

  public SilverMiningBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    robotUtils = allUtils.getRobotUtils();
    sleepUtils = allUtils.getSleepUtils();
    screenshotMiddle = allScreenshots.getScreenshotMiddle();
    inventoryProcessor = allProcessors.getInventoryProcessor();
    statusProcessor = allProcessors.getStatusProcessor();
    messagesProcessor = allProcessors.getMessagesProcessor();
    screenshotMap = allScreenshots.getScreenshotMap();
  }

  public void execute()
      throws URISyntaxException, IOException, InterruptedException, AWTException, TesseractException {
    while (true) {
      while (inventoryProcessor.isLastItem(ItemTemplate.EMPTY)) {
        if (mineOre()) {
          int counter = 0;
          do {
            sleepUtils.random(4);
            counter++;
          } while (!isMined() && counter < 5);
        }
      }
      screenshotMiddle.takeScreenshot();
      tripToBank();
      depositItemsToBank();
      tripFromBank();
      sleepUtils.random(1);
    }
  }

  private boolean mineOre() throws URISyntaxException, IOException {
    BufferedImage middleImage = screenshotMiddle.takeScreenshot();

    for (int x = 0; x < middleImage.getWidth(); x++) {
      for (int y = 0; y < middleImage.getHeight(); y++) {
        Color color = new Color(middleImage.getRGB(x, y));
        if (color.getRed() > 193 && color.getRed() < 200 && color.getGreen() > 180
            && color.getGreen() < 190 &&
            color.getBlue() > 180 && color.getBlue() < 190) {

          robotUtils.moveMouse(screenshotMiddle.getInitialX() + x,
              screenshotMiddle.getInitialY() + y);
          sleepUtils.randomMillis(100);
          System.out.println("should be rock " + color);
          if (statusProcessor.isRock()) {
            System.out.println("should be silver " + color);
            robotUtils.clickLeft();
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isMined() throws URISyntaxException, IOException {
    return messagesProcessor.getLastMessage().contains("silver") ||
        messagesProcessor.getLastMessage().contains("no ore");
  }

  private void tripToBank() throws URISyntaxException, IOException, InterruptedException {
    MoveToCorner1();
    Thread.sleep(25000);
    MoveToCorner2();
    Thread.sleep(11000);
    MoveToBank1();
    Thread.sleep(15000);
    MoveToBank2();
    Thread.sleep(3000);
  }

  private void depositItemsToBank() {
    log.info("deposit");
    robotUtils.moveMouse(screenshotMiddle.getInitialX() + 565,
        screenshotMiddle.getInitialY() + 815);
    robotUtils.clickLeft();
  }

  private void tripFromBank() throws URISyntaxException, IOException, InterruptedException {
    MoveToCorner2();
    Thread.sleep(15000);
    MoveToCorner1();
    Thread.sleep(12000);
    MoveToOre1();
    Thread.sleep(25000);
  }

  private void MoveToCorner1() throws URISyntaxException, IOException {
    log.info("to corner1");
    screenshotMap.takeScreenshot();
    CoordsWithScore coords = OpenCVStuff.findTemplateCoords("map", "mining", "corner1", 5);

    robotUtils.moveMouse(screenshotMap.getInitialX() + coords.getX() - 9,
        screenshotMap.getInitialY() + coords.getY());
    robotUtils.clickLeft();
  }

  private void MoveToCorner2() throws URISyntaxException, IOException {
    log.info("to corner2");
    screenshotMap.takeScreenshot();
    CoordsWithScore coords = OpenCVStuff.findTemplateCoords("map", "mining", "corner2", 5);

    robotUtils.moveMouse(screenshotMap.getInitialX() + coords.getX() - 9,
        screenshotMap.getInitialY() + coords.getY());
    robotUtils.clickLeft();
  }

  private void MoveToBank1() throws URISyntaxException, IOException {
    log.info("to bank1");
    screenshotMap.takeScreenshot();
    CoordsWithScore coords = OpenCVStuff.findTemplateCoords("map", "mining", "bank1", 5);

    robotUtils.moveMouse(screenshotMap.getInitialX() + coords.getX() + 10,
        screenshotMap.getInitialY() + coords.getY() - 10);

    robotUtils.clickLeft();
  }

  private void MoveToBank2() throws URISyntaxException, IOException {
    log.info("to bank2");
    screenshotMiddle.takeScreenshot();
    CoordsWithScore coords = OpenCVStuff.findTemplateCoords("middle", "mining", "bank2", 5);

    robotUtils.moveMouse(screenshotMiddle.getInitialX() + coords.getX(),
        screenshotMiddle.getInitialY() + coords.getY());
    robotUtils.clickLeft();
  }

  private void MoveToOre1() {
    log.info("to ore1");
    //        screenshotMap.screenMap();
    //        CoordsWithScore coords = OpenCVStuff.findTemplateCoords("map", "mining", "ore1", 5);

    robotUtils.moveMouse(screenshotMap.getInitialX() + 128, screenshotMap.getInitialY() + 220);
    robotUtils.clickLeft();
  }
}
