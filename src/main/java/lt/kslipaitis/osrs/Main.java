package lt.kslipaitis.osrs;

import lt.kslipaitis.osrs.processor.*;
import lt.kslipaitis.osrs.screenshot.*;
import lt.kslipaitis.osrs.text.TesseractStuff;
import lt.kslipaitis.osrs.util.*;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import static lt.kslipaitis.osrs.BotName.COW;

public class Main {

    public static void main(String[] args) throws IOException, TesseractException, AWTException, URISyntaxException, InterruptedException {

        Robot robot = new Robot();
        Random random = new Random();
        TesseractStuff tesseractStuff = new TesseractStuff();

        AllUtils allUtils = initAllUtils(robot, random);
        AllScreenshots allScreenshots = initAllScreenshots(robot);
        AllProcessors allProcessors = initAllProcessors(allUtils, allScreenshots, tesseractStuff);

        COW.construct(allUtils, allScreenshots, allProcessors).execute();
    }

    private static AllUtils initAllUtils(Robot robot, Random random) {
        RandomUtils randomUtils = new RandomUtils(random);
        SleepUtils sleepUtils = new SleepUtils(randomUtils);
        RobotUtils robotUtils = new RobotUtils(robot, sleepUtils);
        BankUtils bankUtils = new BankUtils(randomUtils);
        InventoryUtils inventoryUtils = new InventoryUtils(randomUtils);
        OptionsUtils optionsUtils = new OptionsUtils(robotUtils, randomUtils);

        return new AllUtils(randomUtils, sleepUtils, robotUtils, bankUtils, inventoryUtils, optionsUtils);
    }

    private static AllScreenshots initAllScreenshots(Robot robot) {
        Screenshot screenshotWhole = new WholeScreenshot(robot);
        Screenshot screenshotMiddle = new MiddleScreenshot(robot);
        Screenshot screenshotStatus = new StatusScreenshot(robot);
        Screenshot screenshotEnemyHP = new EnemyHpScreenshot(robot);
        Screenshot screenshotCustom = new CustomScreenshot(robot);
        Screenshot screenshotInventory = new InventoryScreenshot(robot);
        Screenshot screenshotMessages = new MessagesScreenshot(robot);
        Screenshot screenshotMap = new MapScreenshot(robot);

        return new AllScreenshots(screenshotWhole,
                                  screenshotMiddle,
                                  screenshotStatus,
                                  screenshotEnemyHP,
                                  screenshotCustom,
                                  screenshotInventory,
                                  screenshotMessages,
                                  screenshotMap);
    }

    private static AllProcessors initAllProcessors(AllUtils allUtils,
                                                   AllScreenshots allScreenshots,
                                                   TesseractStuff tesseractStuff) throws URISyntaxException, IOException, AWTException {
        StatusProcessor statusProcessor = new StatusProcessor(allScreenshots.getScreenshotStatus(), tesseractStuff);
        MessagesProcessor messagesProcessor = new MessagesProcessor(allScreenshots.getScreenshotMessages(),
                                                                    tesseractStuff);
        OptionsProcessor optionsProcessor = new OptionsProcessor(allUtils.getRobotUtils(),
                                                                 allUtils.getOptionsUtils(),
                                                                 allUtils.getRandomUtils());
        BankProcessor bankProcessor = new BankProcessor(allUtils.getSleepUtils(),
                                                        allUtils.getRobotUtils(),
                                                        allUtils.getBankUtils(),
                                                        statusProcessor,
                                                        optionsProcessor,
                                                        allScreenshots.getScreenshotWhole());
        RobotProcessor robotProcessor = new RobotProcessor(allUtils.getRobotUtils(),
                                                           statusProcessor,
                                                           allScreenshots.getScreenshotWhole());
        InventoryProcessor inventoryProcessor = new InventoryProcessor(allScreenshots.getScreenshotInventory(),
                                                                       allUtils.getInventoryUtils(),
                                                                       allUtils.getRobotUtils(),
                                                                       optionsProcessor,
                                                                       statusProcessor,
                                                                       robotProcessor);

        return new AllProcessors(statusProcessor,
                                 messagesProcessor,
                                 optionsProcessor,
                                 bankProcessor,
                                 robotProcessor,
                                 inventoryProcessor);
    }

}
