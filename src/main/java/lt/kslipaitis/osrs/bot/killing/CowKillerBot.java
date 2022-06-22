package lt.kslipaitis.osrs.bot.killing;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public class CowKillerBot implements Bot {

    private final RobotUtils robotUtils;
    private final SleepUtils sleepUtils;
    private final Screenshot screenshotMiddle;
    private final Screenshot screenshotEnemyHP;
    private final StatusProcessor statusProcessor;

    public CowKillerBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        this.robotUtils = allUtils.getRobotUtils();
        this.sleepUtils = allUtils.getSleepUtils();
        this.screenshotMiddle = allScreenshots.getScreenshotMiddle();
        this.screenshotEnemyHP = allScreenshots.getScreenshotEnemyHP();
        this.statusProcessor = allProcessors.getStatusProcessor();
    }


    public void execute() throws InterruptedException, URISyntaxException, IOException, AWTException {
        while (true) {
            cowBot(statusProcessor);
            sleepUtils.random(1);
        }
    }

    private void cowBot(StatusProcessor statusProcessor) throws URISyntaxException, IOException {
        // Method 5 crap
        screenshotMiddle.takeScreenshot();
        CoordsWithScore coordsWithScore = OpenCVStuff.findTemplateCoords("middle", "cow", "template5", 3);

        Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordsWithScore.getX(),
                                                                           coordsWithScore.getY());
        robotUtils.moveMouse(adjustedCoordinate.getX(), adjustedCoordinate.getY());

        final boolean isACow = statusProcessor.isCow();
        //    if (coordsWithScore.getScore() >= 0.948 && isACow) {
        if (isACow) {
            robotUtils.clickLeft();
            sleepUtils.random(3);

            Color oneHpPixel;
            Color aliveCow = new Color(0, 200, 0);
            long startTime2 = System.currentTimeMillis();
            do {
                sleepUtils.random(1);
                oneHpPixel = new Color(screenshotEnemyHP.takeScreenshot().getRGB(20, 40));
            } while (oneHpPixel.equals(aliveCow));
            long endTime2 = System.currentTimeMillis();

            final long timeToKillCow = 3 + (endTime2 - startTime2) / 1000;

            log.info("Took {} s to kill.", timeToKillCow);
        } else {
            log.info("Probably not a cow {} {}", coordsWithScore.getScore(), isACow);
        }
    }
}
