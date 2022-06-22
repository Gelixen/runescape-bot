package lt.kslipaitis.osrs.processor;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.text.TesseractStuff;

@Log4j2
public class StatusProcessor {

  private static final Color MATERIAL = new Color(0, 255, 255);
  private static final Color ENTITY = new Color(255, 255, 0);
  private static final Color ITEM = new Color(255, 149, 66);

  private final Screenshot screenshot;
  private final TesseractStuff tesseractStuff;

  public StatusProcessor(Screenshot screenshot,
      TesseractStuff tesseractStuff) throws URISyntaxException, IOException, AWTException {
    this.screenshot = screenshot;
    this.tesseractStuff = tesseractStuff;
  }

  public boolean isTree() throws IOException, URISyntaxException {
    BufferedImage image = screenshot.takeScreenshot();
    return new Color(image.getRGB(125, 10)).equals(MATERIAL);
  }

  public boolean isCow() throws IOException, URISyntaxException {
    BufferedImage image = screenshot.takeScreenshot();
    return new Color(image.getRGB(75, 10)).equals(ENTITY);
  }

  public boolean isRock() throws IOException, URISyntaxException {
    BufferedImage image = screenshot.takeScreenshot();
    return new Color(image.getRGB(91, 10)).equals(MATERIAL);
  }

  public boolean isBank() throws IOException, URISyntaxException {
    BufferedImage image = screenshot.takeScreenshot();
    return new Color(image.getRGB(58, 10)).equals(MATERIAL);
  }

  public boolean textContains(String textToContain) throws URISyntaxException, IOException {
    screenshot.takeScreenshot();
    final String message = tesseractStuff.ocrLine("status").trim();
    log.trace(message);
    return message.contains(textToContain);
  }

  public Object getText() throws URISyntaxException, IOException {
    screenshot.takeScreenshot();
    return tesseractStuff.ocrLine("status");
  }
}
