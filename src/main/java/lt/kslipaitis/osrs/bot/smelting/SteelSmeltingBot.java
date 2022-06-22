package lt.kslipaitis.osrs.bot.smelting;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;

public class SteelSmeltingBot extends SmeltingBot {

  private final BankProcessor bankProcessor;

  // 870 / hour
  // 10.5k smithing / hour
  // 18k / hour profit
  public SteelSmeltingBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    super(allUtils, allProcessors, allScreenshots);
    bankProcessor = allProcessors.getBankProcessor();
  }

  @Override
  void takeInputFromTheBank() throws URISyntaxException, IOException {
    bankProcessor.takeFromFirstSlotIf("Iron ore");
    bankProcessor.takeFromSecondSlotNTimesIf("Coal", 2);
  }

  @Override
  void smeltInput() {
    robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD4);
    sleepUtils.random(27);
  }

  @Override
  void depositOutput() throws URISyntaxException, IOException {
    bankProcessor.depositInventory();
  }
}
