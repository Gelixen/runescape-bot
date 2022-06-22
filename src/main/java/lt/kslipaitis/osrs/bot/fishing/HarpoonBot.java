package lt.kslipaitis.osrs.bot.fishing;

import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.MessagesProcessor;
import lt.kslipaitis.osrs.processor.OptionsProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HarpoonBot implements Bot {

    private final RobotUtils robotUtils;
    private final SleepUtils sleepUtils;
    private final OptionsProcessor optionsProcessor;
    private final InventoryProcessor inventoryProcessor;
    private final MessagesProcessor messagesProcessor;
    private final Screenshot screenshotMiddle;

    public HarpoonBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {

        robotUtils = allUtils.getRobotUtils();
        sleepUtils = allUtils.getSleepUtils();
        optionsProcessor = allProcessors.getOptionsProcessor();
        inventoryProcessor = allProcessors.getInventoryProcessor();
        messagesProcessor = allProcessors.getMessagesProcessor();
        screenshotMiddle = allScreenshots.getScreenshotMiddle();
    }

    public void execute() throws URISyntaxException, InterruptedException, IOException, AWTException {
        while (true) {
            catchFishes();
            dropFishes();
        }
    }

    private void catchFishes() throws URISyntaxException, IOException {
        while (inventoryProcessor.isLastItem(ItemTemplate.EMPTY)) {
            harpoon();
            String previousMessage;
            String lastMessage = messagesProcessor.getLastMessage();
            do {
                previousMessage = lastMessage;
                sleepUtils.random(60);
                lastMessage = messagesProcessor.getLastMessage();
            } while (!lastMessage.equals(previousMessage) && !lastMessage.equals("Click here to continue"));
        }
    }

    private void dropFishes() {
        inventoryProcessor.dropAllItems(ItemTemplate.RAW_TUNA);
    }

    private void harpoon() throws URISyntaxException, IOException {
        screenshotMiddle.takeScreenshot();
        CoordsWithScore coords = OpenCVStuff.findTemplateCoords("middle", "fishing/harpoon", "template2", 5);

        Coordinate coordinate = screenshotMiddle.adjustCoordinates(coords.getX(), coords.getY());

        int x = coordinate.getX();
        int y = coordinate.getY() + 40;

        robotUtils.moveAndRightClick(x, y);
        optionsProcessor.selectSecondOption();
    }
}
