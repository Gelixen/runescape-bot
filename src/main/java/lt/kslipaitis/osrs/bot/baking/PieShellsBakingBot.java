package lt.kslipaitis.osrs.bot.baking;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public class PieShellsBakingBot extends BakingBot {

    //  / hour
    // k profit / hour
    public PieShellsBakingBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        super(allUtils, allProcessors, allScreenshots);
    }

    @Override
    protected void bake() throws URISyntaxException, IOException {
        inventoryProcessor.leftClickFirstItemIf("");
        inventoryProcessor.leftClickFirstItemIf("");
        sleepUtils.random(1);
        robotUtils.pressAndReleaseKey(KeyEvent.VK_SPACE);
        sleepUtils.random(17);
    }
}
