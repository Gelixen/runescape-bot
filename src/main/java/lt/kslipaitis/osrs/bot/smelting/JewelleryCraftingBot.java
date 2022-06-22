package lt.kslipaitis.osrs.bot.smelting;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;

@Log4j2
public class JewelleryCraftingBot extends SmeltingBot {

  private final String gem;
  private final BankProcessor bankProcessor;
  private final InventoryProcessor inventoryProcessor;

  // 900 / hour
  // 50k crafting / hour (67k ruby)
  public JewelleryCraftingBot(String gem,
      AllUtils allUtils,
      AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    this.gem = gem;
    bankProcessor = allProcessors.getBankProcessor();
    inventoryProcessor = allProcessors.getInventoryProcessor();
  }

  @Override
  void takeInputFromTheBank() throws URISyntaxException, IOException {
    bankProcessor.takeFromFirstSlotIf(gem);
    bankProcessor.takeFromSecondSlotIf("Gold bar");
  }

  @Override
  void smeltInput() throws URISyntaxException, IOException {
    log.info("Crafting...");
    selectJewellery();
    sleepUtils.random(25);

    while (notAllProcessed()) {
      log.info("Lagging behind - repeating crafting...");
      clickFurnace();
      selectJewellery();
      sleepUtils.random(10);
    }

    log.info("Crafting done.");
  }

  private void selectJewellery() {
    robotUtils.pressAndReleaseKey(KeyEvent.VK_SPACE);
  }

  private boolean notAllProcessed() throws URISyntaxException, IOException {
    return inventoryProcessor.isThirdToLastItem(ItemTemplate.GOLD_BAR);
  }

  private void clickFurnace() {
    // pass coords to robot, instead of parsing and passing x,y separately
    RandomCoordinate coords = randomUtils.getRandomCoordinates(1292, 694, 1312, 725);
    robotUtils.moveAndLeftClick(coords.getX(), coords.getY());
  }

  @Override
  void depositOutput() {
    log.info("Depositing...");
    inventoryProcessor.leftClickFirstItem();
  }

}
