package lt.kslipaitis.osrs.bot.mining;

import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.CoordsWithScore;
import lt.kslipaitis.osrs.OpenCVStuff;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.MessagesProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;
import net.sourceforge.tess4j.TesseractException;

@Log4j2
public class GoldMiningBot implements Bot {

  private final RobotUtils robotUtils;
  private final Screenshot screenshotMiddle;
  private final InventoryProcessor inventoryProcessor;
  private final MessagesProcessor messagesProcessor;
  private final SleepUtils sleepUtils;

  public GoldMiningBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    robotUtils = allUtils.getRobotUtils();
    inventoryProcessor = allProcessors.getInventoryProcessor();
    screenshotMiddle = allScreenshots.getScreenshotMiddle();
    messagesProcessor = allProcessors.getMessagesProcessor();
    sleepUtils = allUtils.getSleepUtils();
  }

  public void execute()
      throws URISyntaxException, IOException, InterruptedException, TesseractException {
    while (inventoryProcessor.isLastItem(ItemTemplate.EMPTY)) {
      screenshotMiddle.takeScreenshot();
      CoordsWithScore coordsWithScore = OpenCVStuff.findTemplateCoords("middle",
          "mining/gold", "template1", 5);

      if (coordsWithScore.getScore() > 0.6) {
        robotUtils.moveAndLeftClick(screenshotMiddle.getInitialX() + coordsWithScore.getX(),
            screenshotMiddle.getInitialY() + coordsWithScore.getY());

        sleepUtils.random(5);
        while (!messagesProcessor.getLastMessage().contains("You manage to mine")) {
          Thread.sleep(2000);
        }
      }
      log.info(coordsWithScore);
    }
  }
}
