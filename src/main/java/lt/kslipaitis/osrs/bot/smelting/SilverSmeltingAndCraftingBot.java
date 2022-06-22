package lt.kslipaitis.osrs.bot.smelting;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public class SilverSmeltingAndCraftingBot extends SmeltingBot {

    private final BankProcessor bankProcessor;
    private final InventoryProcessor inventoryProcessor;

    // 600 / hour
    // 8k smithing / hour
    // 29k crafting / hour
    public SilverSmeltingAndCraftingBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        super(allUtils, allProcessors, allScreenshots);
        inventoryProcessor = allProcessors.getInventoryProcessor();
        bankProcessor = allProcessors.getBankProcessor();
    }

    @Override
    void takeInputFromTheBank() throws URISyntaxException, IOException {
        bankProcessor.takeFromFirstSlotIf("Silver ore");
    }

    @Override
    void smeltInput() throws URISyntaxException, IOException {
        smeltSilver();
        craftSymbols();
    }

    private void smeltSilver() throws URISyntaxException, IOException {
        log.info("Silver smelting...");
        robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD3);
        sleepUtils.random(85);

        while (!inventoryProcessor.isSecondToLastItem(ItemTemplate.SILVER_INGOT)) {
            log.info("Lagging behind - repeating silver smelting...");
            clickFurnace();
            robotUtils.pressAndReleaseKey(KeyEvent.VK_NUMPAD3);
            sleepUtils.random(30);
        }
        log.info("Silver smelting done.");
    }

    private void craftSymbols() {
        log.info("Crafting symbols...");
        clickFurnace();
        clickSymbol();
        log.info("Crafting symbols done.");
    }

    private void clickFurnace() {
        // pass coords to robot, instead of parsing and passing x,y separately
        RandomCoordinate coords = randomUtils.getRandomCoordinates(1292, 694, 1312, 725);
        robotUtils.moveAndLeftClick(coords.getX(), coords.getY());
        sleepUtils.random(1);
    }

    private void clickSymbol() {
        RandomCoordinate coords = randomUtils.getRandomCoordinates(1147, 410, 1232, 462);
        robotUtils.moveAndLeftClick(coords.getX(), coords.getY());
        sleepUtils.random(50);
    }

    @Override
    void depositOutput() {
        log.info("Depositing symbols...");
        inventoryProcessor.leftClickFirstItem();
    }

}
