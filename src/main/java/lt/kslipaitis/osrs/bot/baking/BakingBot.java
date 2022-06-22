package lt.kslipaitis.osrs.bot.baking;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.OptionsProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.*;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public abstract class BakingBot implements Bot {

    protected final RobotUtils robotUtils;
    protected final RandomUtils randomUtils;
    protected final SleepUtils sleepUtils;
    protected final InventoryProcessor inventoryProcessor;
    protected final BankProcessor bankProcessor;
    protected final OptionsProcessor optionsProcessor;

    public BakingBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        robotUtils = allUtils.getRobotUtils();
        randomUtils = allUtils.getRandomUtils();
        sleepUtils = allUtils.getSleepUtils();
        inventoryProcessor = allProcessors.getInventoryProcessor();
        bankProcessor = allProcessors.getBankProcessor();
        optionsProcessor = allProcessors.getOptionsProcessor();
    }

    public void execute() throws InterruptedException, TesseractException, URISyntaxException, IOException {
        int iterations = 1;
        while (true) {
            takeFromBank();
            closeBankWindow();
            bake();
            openBankWindow();
            depositToBank();
            log.info("{} run, baked {} meals", iterations, iterations * 14);
            iterations++;
        }
    }

    private void takeFromBank() {
        bankProcessor.takeFromFirstSlot();
        bankProcessor.takeFromSecondSlot();
    }

    private void closeBankWindow() {
        bankProcessor.closeWindow();
    }

    protected abstract void bake() throws InterruptedException, URISyntaxException, IOException;

    private void openBankWindow() {
        RandomCoordinate coordinate = randomUtils.getRandomCoordinates(1233, 680, 1245, 725);
        log.info("Open bank at {}", coordinate);
        robotUtils.moveAndLeftClick(coordinate.getX(), coordinate.getY());
    }

    private void depositToBank() throws URISyntaxException, IOException {
        bankProcessor.depositInventory();
    }
}
