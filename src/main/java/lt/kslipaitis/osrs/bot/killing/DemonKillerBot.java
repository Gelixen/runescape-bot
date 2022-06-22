package lt.kslipaitis.osrs.bot.killing;

import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
public class DemonKillerBot implements Bot {

    private final RobotUtils robotUtils;
    private final Screenshot screenshotMiddle;
    private final Screenshot screenshotEnemyHP;

    public DemonKillerBot(AllUtils allUtils, AllProcessors allProcessors, AllScreenshots allScreenshots) {
        robotUtils = allUtils.getRobotUtils();
        screenshotMiddle = allScreenshots.getScreenshotMiddle();
        screenshotEnemyHP = allScreenshots.getScreenshotEnemyHP();
    }

    @Override
    public void execute() throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException {
        do {
            attackDemon();
            Thread.sleep(500);
        } while (true);
    }

    private void attackDemon() throws InterruptedException, URISyntaxException, IOException {
        screenshotMiddle.takeScreenshot();

        CoordsWithScore coordsWithScore = OpenCVStuff.findTemplateCoords("middle", "demon", "template1", 3);

        log.info(coordsWithScore);
        Coordinate adjustedCoordinate = screenshotMiddle.adjustCoordinates(coordsWithScore.getX(),
                                                                           coordsWithScore.getY());

        robotUtils.moveMouse(adjustedCoordinate.getX(), adjustedCoordinate.getY());

        if (coordsWithScore.getScore() > 0.87) {
            robotUtils.clickLeft();
            Thread.sleep(30000);

            Color oneHpPixel;
            Color alive = new Color(0, 200, 0);
            do {
                Thread.sleep(1000);
                oneHpPixel = new Color(screenshotEnemyHP.takeScreenshot().getRGB(10, 40));
            } while (oneHpPixel.equals(alive));

            log.info("DEAD");
            Thread.sleep(27000);
        } else {
            log.info("NO :(");
        }
    }
}
