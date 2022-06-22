package lt.kslipaitis.osrs.processor;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.file.FileUtils;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.InventoryUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RobotUtils;

@Log4j2
public class InventoryProcessor {

  private final Screenshot screenshotInventory;
  private final InventoryUtils inventoryUtils;
  private final RobotUtils robotUtils;
  private final OptionsProcessor optionsProcessor;
  private final RobotProcessor robotProcessor;

  public InventoryProcessor(Screenshot screenshotInventory,
      InventoryUtils inventoryUtils,
      lt.kslipaitis.osrs.util.RobotUtils robotUtils,
      OptionsProcessor optionsProcessor,
      StatusProcessor statusProcessor,
      RobotProcessor robotProcessor) {
    this.screenshotInventory = screenshotInventory;
    this.inventoryUtils = inventoryUtils;
    this.robotUtils = robotUtils;
    this.optionsProcessor = optionsProcessor;
    this.robotProcessor = robotProcessor;
  }

  public void dropAllItems(ItemTemplate item) {
    robotUtils.pressKey(KeyEvent.VK_SHIFT);

    for (int row = 1; row <= InventoryUtils.MAX_ROW_INDEX; row++) {
      for (int column = 0; column <= InventoryUtils.MAX_COLUMN_INDEX; column++) {
        RandomCoordinate coordinate = inventoryUtils.getNthSlotCoords(row, column);
        robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
      }
    }

    robotUtils.releaseKey(KeyEvent.VK_SHIFT);
  }

  public boolean isSecondToLastItem(ItemTemplate itemTemplate)
      throws URISyntaxException, IOException {
    BufferedImage image = screenshotInventory.takeScreenshot();
    Coordinate coordinate = inventoryUtils.getSecondToLastSlotImageCoordinate();

    BufferedImage itemImage = image.getSubimage(coordinate.getX(),
        coordinate.getY(),
        InventoryUtils.ITEM_LENGTH,
        InventoryUtils.ITEM_HEIGHT);

    File resultImage = FileUtils.createFile("inventory-second-to-last.png");
    ImageIO.write(itemImage, "png", resultImage);

    CoordsWithScore coordsWithScore = OpenCVStuff.findTemplateCoords("inventory-second-to-last",
        "inventory",
        itemTemplate.getTemplateName(),
        3);
    log.info(coordsWithScore);

    return coordsWithScore.getScore() > 0.997;
  }

  public boolean isThirdToLastItem(ItemTemplate itemTemplate)
      throws URISyntaxException, IOException {
    BufferedImage image = screenshotInventory.takeScreenshot();
    Coordinate coordinate = inventoryUtils.getThirdToLastSlotImageCoordinate();

    BufferedImage itemImage = image.getSubimage(coordinate.getX(),
        coordinate.getY(),
        InventoryUtils.ITEM_LENGTH,
        InventoryUtils.ITEM_HEIGHT);

    File resultImage = FileUtils.createFile("inventory-third-to-last.png");
    ImageIO.write(itemImage, "png", resultImage);

    CoordsWithScore coordsWithScore = OpenCVStuff.findTemplateCoords("inventory-third-to-last",
        "inventory",
        itemTemplate.getTemplateName(),
        3);

    log.info("isThirdToLastItem " + coordsWithScore);
    return coordsWithScore.getScore() > 0.99;
  }

  public boolean isLastItem(ItemTemplate itemTemplate) throws URISyntaxException, IOException {
    BufferedImage image = screenshotInventory.takeScreenshot();
    Coordinate coordinate = inventoryUtils.getLastSlotImageCoordinate();

    BufferedImage itemImage = image.getSubimage(coordinate.getX(),
        coordinate.getY(),
        InventoryUtils.ITEM_LENGTH,
        InventoryUtils.ITEM_HEIGHT);

    File resultImage = FileUtils.createFile("inventory-last.png");
    ImageIO.write(itemImage, "png", resultImage);

    CoordsWithScore coordsWithScore = OpenCVStuff.findTemplateCoords("inventory-last",
        "inventory",
        itemTemplate.getTemplateName(),
        3);

    log.trace(coordsWithScore);
    return coordsWithScore.getScore() > 0.999;
  }

  public void leftClickLastItem() {
    RandomCoordinate coordinate = inventoryUtils.getLastSlotCoordinate();
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }

  public void clickFirstItemWithOption(int optionNumber) {
    rightClickFirstItem();
    optionsProcessor.selectNthOption(optionNumber);
  }

  public void rightClickFirstItem() {
    RandomCoordinate coordinate = inventoryUtils.getFirstSlotCoordinate();
    robotUtils.moveAndRightClick(coordinate.getX(), coordinate.getY());
  }

  public void leftClickFirstItem() {
    RandomCoordinate coordinate = inventoryUtils.getFirstSlotCoordinate();
    robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
  }

  public void leftClickFirstItemIf(String item) throws URISyntaxException, IOException {
    RandomCoordinate coordinate = inventoryUtils.getFirstSlotCoordinate();
    robotUtils.moveMouse(coordinate.getX(), coordinate.getY());
    robotProcessor.leftClickIf(item);
  }
}
