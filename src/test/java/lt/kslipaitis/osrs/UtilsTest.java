package lt.kslipaitis.osrs;

import lt.kslipaitis.osrs.screenshot.InventoryScreenshot;
import lt.kslipaitis.osrs.screenshot.MiddleScreenshot;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.screenshot.StatusScreenshot;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class UtilsTest {

    @Test
    void track() throws InterruptedException {
        while (true) {
            Point a = MouseInfo.getPointerInfo().getLocation();

            System.out.println(a);
            Thread.sleep(1000);
        }
    }

    @Test
    void mouseMove() throws AWTException {
        final Robot robot = new Robot();
        robot.mouseMove(1377, 1108);
        System.out.println(MouseInfo.getPointerInfo().getLocation());
    }

    @Test
    void takeInventoryScreenshot() throws AWTException, URISyntaxException, IOException {
        final Robot robot = new Robot();
        final Screenshot screenshot = new InventoryScreenshot(robot);
        screenshot.takeScreenshot();
    }

    @Test
    void takeMiddleScreenshot() throws AWTException, URISyntaxException, IOException {
        final Robot robot = new Robot();
        final Screenshot screenshot = new MiddleScreenshot(robot);
        screenshot.takeScreenshot();
    }

    @Test
    void takeStatusScreenshot() throws AWTException, URISyntaxException, IOException {
        final Robot robot = new Robot();
        final Screenshot screenshot = new StatusScreenshot(robot);
        screenshot.takeScreenshot();
    }

}
