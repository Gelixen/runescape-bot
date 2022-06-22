package lt.kslipaitis.osrs.processor;

import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import lt.kslipaitis.osrs.text.TesseractStuff;

@Log4j2
public class MessagesProcessor {

  private final Screenshot screenshotMessages;
  private final TesseractStuff tesseractStuff;

  public MessagesProcessor(Screenshot screenshotMessages, TesseractStuff tesseractStuff) {
    this.screenshotMessages = screenshotMessages;
    this.tesseractStuff = tesseractStuff;
  }

  public String getLastMessage() throws URISyntaxException, IOException {
    screenshotMessages.takeScreenshot();
    final String message = tesseractStuff.ocrLine("last-message");
    log.info(message);
    return message;
  }
}
