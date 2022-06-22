package lt.kslipaitis.osrs.bot.smelting;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;

@Log4j2
public class IronSmeltingBot extends SmeltingBot {

  private final BankProcessor bankProcessor;
  private final InventoryProcessor inventoryProcessor;

  // 900 / hour
  // 11k smithing / hour
  // 52k / hour profit
  public IronSmeltingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    bankProcessor = allProcessors.getBankProcessor();
    inventoryProcessor = allProcessors.getInventoryProcessor();
  }

  @Override
  public void takeInputFromTheBank() throws URISyntaxException, IOException {
    log.info("Iron withdraw...");
    bankProcessor.takeFromFirstSlotIf("Iron ore");
  }

  @Override
  public void takeRingFromTheBank() throws InterruptedException {
    log.info("Ring withdraw...");
    takeRingFromBank();
    equipRing();
  }

  private void takeRingFromBank() throws InterruptedException {
    bankProcessor.takeFromNthSlotWithNthOption(2, 2);
  }

  private void equipRing() {
    inventoryProcessor.clickFirstItemWithOption(8);
  }

  @Override
  void smeltInput() {
    log.info("Iron smelt...");
    robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD2);
    sleepUtils.random(85);
  }

  @Override
  void depositOutput() throws URISyntaxException, IOException {
    bankProcessor.depositInventory();
  }

}
