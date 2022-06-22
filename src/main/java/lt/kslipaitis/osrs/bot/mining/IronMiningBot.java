package lt.kslipaitis.osrs.bot.mining;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.CoordsWithColor;
import lt.kslipaitis.osrs.bot.Bot;
import lt.kslipaitis.osrs.inventory.ItemTemplate;
import lt.kslipaitis.osrs.processor.AllProcessors;
import lt.kslipaitis.osrs.processor.InventoryProcessor;
import lt.kslipaitis.osrs.processor.StatusProcessor;
import lt.kslipaitis.osrs.screenshot.AllScreenshots;
import lt.kslipaitis.osrs.util.AllUtils;
import lt.kslipaitis.osrs.util.RandomCoordinate;
import lt.kslipaitis.osrs.util.RandomUtils;
import lt.kslipaitis.osrs.util.RobotUtils;
import lt.kslipaitis.osrs.util.SleepUtils;

@Log4j2
public class IronMiningBot implements Bot {

  private final RobotUtils robotUtils;
  private final RandomUtils randomUtils;
  private final SleepUtils sleepUtils;
  private final InventoryProcessor inventoryProcessor;
  private final StatusProcessor statusProcessor;

  private final Map<Integer, Integer> retriesMap = new TreeMap<>();
  private final Map<String, Integer> miningTimeMap = new TreeMap<>();

  public IronMiningBot(AllUtils allUtils, AllProcessors allProcessors,
      AllScreenshots allScreenshots) {
    robotUtils = allUtils.getRobotUtils();
    randomUtils = allUtils.getRandomUtils();
    sleepUtils = allUtils.getSleepUtils();
    inventoryProcessor = allProcessors.getInventoryProcessor();
    statusProcessor = allProcessors.getStatusProcessor();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println(retriesMap);
      System.out.println(miningTimeMap);
    }, "Shutdown-thread"));
  }

  // 37k mining exp / hour
  public void execute() throws URISyntaxException, IOException {
    Set<CoordsWithColor> coordWithColor = new HashSet<>();

    coordWithColor.add(
        new CoordsWithColor(new Coordinate(1209, 534), robotUtils.getPixelColor(1209, 534)));
    coordWithColor.add(
        new CoordsWithColor(new Coordinate(875, 835), robotUtils.getPixelColor(875, 835)));
    coordWithColor.add(
        new CoordsWithColor(new Coordinate(1259, 1182), robotUtils.getPixelColor(1259, 1182)));

    printColor(1209, 534);
    printColor(875, 835);
    printColor(1259, 1182);

    while (true) {
      while (inventoryProcessor.isThirdToLastItem(ItemTemplate.EMPTY)) {
        log.trace("--------------------------------");
        coordWithColor.forEach(c -> {
          final Coordinate coordinate = c.getCoordinate();
          Color pixelColor = robotUtils.getPixelColor(coordinate.getX(), coordinate.getY());
          RandomCoordinate ClickCoordinate = randomUtils.getRandomCoordinateWithinRadius(
              coordinate.getX(),
              coordinate.getY(),
              50);
          log.trace("Coords {} with color {}", coordinate, pixelColor);
          if (pixelColor.equals(c.getColor())) {
            robotUtils.moveMouse(ClickCoordinate.getX(), ClickCoordinate.getY());
            try {
              if (statusProcessor.isRock()) {
                log.trace("Mining... {}, {}, {}", ClickCoordinate, pixelColor, c.getColor());
                robotUtils.clickLeft();
                int retryCount = 0;
                long start = System.currentTimeMillis();
                sleepUtils.randomMillis(1000);
                do {
                  sleepUtils.randomMillis(100);
                  pixelColor = robotUtils.getPixelColor(coordinate.getX(), coordinate.getY());
                  log.trace("new {} color {}", coordinate, pixelColor);
                  retryCount++;
                } while (pixelColor.equals(c.getColor()) && retryCount < 20);
                long end = System.currentTimeMillis();
                DecimalFormat df = new DecimalFormat("##.#");

                String miningTime = df.format((end - start) / 1000f);
                miningTimeMap.compute(miningTime, (k, v) -> v == null ? 1 : v + 1);
                retriesMap.compute(retryCount, (k, v) -> v == null ? 1 : v + 1);

                log.info("Mined! In {} retries {}", retryCount, miningTime);
              } else {
                sleepUtils.random(2);
                log.info("{} not a rock!", ClickCoordinate);
                System.exit(123);
              }
            } catch (IOException | URISyntaxException e) {
              throw new RuntimeException(e);
            }
          }
        });
      }

      cleanUp();
    }
  }

  private void printColor(int x, int y) {
    Color color = robotUtils.getPixelColor(x, y);
    log.info("new Color({}, {}, {});", color.getRed(), color.getGreen(), color.getBlue());
  }

  private void cleanUp() {
    log.info("Cleaning up inventory...");
    inventoryProcessor.dropAllItems(ItemTemplate.IRON_ORE);
  }

}
