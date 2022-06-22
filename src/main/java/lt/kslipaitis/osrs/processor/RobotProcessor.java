package lt.kslipaitis.osrs.processor;

import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.RobotUtils;

@Log4j2
public class RobotProcessor {

  private final RobotUtils robotUtils;
  private final StatusProcessor statusProcessor;
  private final Screenshot screenshotWhole;

  public RobotProcessor(RobotUtils robotUtils, StatusProcessor statusProcessor,
      Screenshot screenshotWhole) {
    this.robotUtils = robotUtils;
    this.statusProcessor = statusProcessor;
    this.screenshotWhole = screenshotWhole;
  }

  public void leftClickIf(String item) throws URISyntaxException, IOException {
    if (statusProcessor.textContains(item)) {
      robotUtils.clickLeft();
    } else {
      log.error("{} not a {}", statusProcessor.getText(), item);
      screenshotWhole.takeScreenshot();
      System.exit(100);
    }
  }

}
