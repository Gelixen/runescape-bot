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
public class SilverSmeltingBot extends SmeltingBot {

  private final BankProcessor bankProcessor;
  private final InventoryProcessor inventoryProcessor;

  // 870 / hour
  // 12k smithing / hour
  public SilverSmeltingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    bankProcessor = allProcessors.getBankProcessor();
    inventoryProcessor = allProcessors.getInventoryProcessor();
  }

  @Override
  void takeInputFromTheBank() throws URISyntaxException, IOException {
    bankProcessor.takeFromFirstSlotIf("Silver ore");
  }

  @Override
  void smeltInput() throws URISyntaxException, IOException {
    log.info("Smelting...");
    robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD3);
    sleepUtils.random(85);

    while (!inventoryProcessor.isSecondToLastItem(ItemTemplate.SILVER_INGOT)) {
      log.info("Lagging behind - repeating smelting...");
      clickFurnace();
      robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD3);
      sleepUtils.random(30);
    }
    log.info("Smelting done.");
  }

  private void clickFurnace() {
    // pass coords to robot, instead of parsing and passing x,y separately
    RandomCoordinate coords = randomUtils.getRandomCoordinates(1292, 694, 1312, 725);
    robotUtils.moveAndLeftClick(coords.getX(), coords.getY());
    sleepUtils.random(1);
  }

  @Override
  void depositOutput() throws URISyntaxException, IOException {
    bankProcessor.depositInventory();
  }

}
