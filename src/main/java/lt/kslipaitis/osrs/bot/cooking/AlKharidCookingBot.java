package lt.kslipaitis.osrs.bot.cooking;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public class AlKharidCookingBot extends CookingBot {

    private final BankProcessor bankProcessor;
    private final SleepUtils sleepUtils;
    private final InventoryProcessor inventoryProcessor;
    private final RobotUtils robotUtils;
    private final RandomUtils randomUtils;

    // 930 / hour
    // 28k cooking / hour (anchovies)
    // 129k cooking / hour (swordfish)
    public AlKharidCookingBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        super(allUtils, allProcessors, allScreenshots);
        bankProcessor = allProcessors.getBankProcessor();
        sleepUtils = allUtils.getSleepUtils();
        inventoryProcessor = allProcessors.getInventoryProcessor();
        robotUtils = allUtils.getRobotUtils();
        randomUtils = allUtils.getRandomUtils();
    }

    @Override
    void takeInputFromTheBank() throws URISyntaxException, IOException {
        bankProcessor.takeFromFirstSlotIf("Raw swordfish");
    }

    @Override
    void cookInput() throws URISyntaxException, IOException {
        log.info("Cooking...");
        selectOption();
        sleepUtils.random(66);

        while (!inventoryProcessor.isLastItem(ItemTemplate.SWORDFISH)) {
            log.info("Lagging behind - repeating cooking...");
            clickRange();
            selectOption();
            sleepUtils.random(16);
        }

        log.info("Cooking done.");
    }

    private void selectOption() {
        robotUtils.pressAndReleaseKey(KeyEvent.VK_SPACE);
    }

    private void clickRange() {
        // pass coords to robot, instead of parsing and passing x,y separately
        RandomCoordinate coords = randomUtils.getRandomCoordinates(1180, 655, 1215, 715);
        robotUtils.moveAndLeftClick(coords.getX(), coords.getY());
        sleepUtils.random(1);
    }

    @Override
    void depositOutput() throws URISyntaxException, IOException {
        bankProcessor.depositInventory();
    }

}
