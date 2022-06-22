package lt.kslipaitis.osrs.bot.cooking;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.BankProcessor;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.*;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public abstract class CookingBot implements Bot {

    private final RobotUtils robotUtils;
    private final RandomUtils randomUtils;
    private final SleepUtils sleepUtils;
    private final StatusProcessor statusProcessor;
    private final BankProcessor bankProcessor;

    public CookingBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        robotUtils = allUtils.getRobotUtils();
        randomUtils = allUtils.getRandomUtils();
        sleepUtils = allUtils.getSleepUtils();
        statusProcessor = allProcessors.getStatusProcessor();
        bankProcessor = allProcessors.getBankProcessor();
    }

    public void execute() throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException {
        int toProcess = 3000;
        final int iterations = toProcess / 28;
        for (int i = 0; i < iterations; i++) {
            log.info("{} iteration, {} possibly processed", i, i * 28);
            takeInputFromTheBank();
            closeBankWindow();
            moveToRange();
            cookInput();
            moveToBank();
            depositOutput();
        }
    }

    abstract void takeInputFromTheBank() throws InterruptedException, URISyntaxException, IOException;

    private void closeBankWindow() {
        bankProcessor.closeWindow();
    }

    void moveToRange() throws URISyntaxException, IOException {
        RandomCoordinate coords = randomUtils.getRandomCoordinates(1299, 309, 1328, 363);
        robotUtils.moveMouse(coords.getX(), coords.getY());

        if (statusProcessor.textContains("Ranqe")) {
            robotUtils.clickLeft();
            sleepUtils.random(15);
        } else {
            log.error("{}, {} not a range", coords.getX(), coords.getY());
            System.exit(100);
        }
    }

    abstract void cookInput() throws InterruptedException, URISyntaxException, IOException;

    void moveToBank() throws URISyntaxException, IOException {
        RandomCoordinate coords = randomUtils.getRandomCoordinates(989, 1269, 1023, 1325);
        robotUtils.moveMouse(coords.getX(), coords.getY());
        sleepUtils.random(2);
        if (statusProcessor.textContains("Bank booth")) {
            robotUtils.clickLeft();
            sleepUtils.random(15);
        } else {
            log.error("{}, {} not a bank", coords.getX(), coords.getY());
            System.exit(100);
        }
    }

    abstract void depositOutput() throws InterruptedException, TesseractException, URISyntaxException, IOException;

}
