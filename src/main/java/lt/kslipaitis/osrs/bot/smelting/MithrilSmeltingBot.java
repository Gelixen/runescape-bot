package lt.kslipaitis.osrs.bot.smelting;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;

public class MithrilSmeltingBot extends SmeltingBot {

  private final BankProcessor bankProcessor;

  public MithrilSmeltingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    this.bankProcessor = allProcessors.getBankProcessor();
  }

  @Override
  void takeInputFromTheBank() throws URISyntaxException, IOException {
    bankProcessor.takeFromFirstSlotIf("Mithril ore");
    bankProcessor.takeFromSecondSlotNTimesIf("Coal", 4);
  }

  @Override
  void smeltInput() {
    robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD6);
    sleepUtils.random(15);
  }

  @Override
  void depositOutput() throws URISyntaxException, IOException {
    bankProcessor.depositInventory();
  }
}
