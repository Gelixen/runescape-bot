package lt.kslipaitis.osrs.bot.baking;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;

import java.awt.event.KeyEvent;

@Log4j2
public class PizzaBakingBot extends BakingBot {

    // 1800 / hour
    // 110k exp / hour
    // 35k profit / hour
    public PizzaBakingBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        super(allUtils, allProcessors, allScreenshots);
    }

    @Override
    protected void bake() {
        inventoryProcessor.rightClickFirstItem();
        optionsProcessor.selectSecondOption();

        inventoryProcessor.leftClickLastItem();
        sleepUtils.random(1);
        robotUtils.pressAndReleaseKey(KeyEvent.VK_SPACE);
        sleepUtils.random(17);
    }
}
