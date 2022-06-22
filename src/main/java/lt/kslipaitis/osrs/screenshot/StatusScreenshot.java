package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import lt.kslipaitis.osrs.file.FileUtils;

public class StatusScreenshot extends Screenshot {

  public StatusScreenshot(Robot robot) {
    super(0, 30, robot);
  }

  @Override
  public BufferedImage takeScreenshot() throws URISyntaxException, IOException {
    Rectangle rectangle = new Rectangle(initialX, initialY, 300, 20);
    BufferedImage image = robot.createScreenCapture(rectangle);

    File resultImage = FileUtils.createFile("status.png");
    ImageIO.write(image, "png", resultImage);

    return image;
  }

  @Override
  protected Rectangle createRectangle() {
    return new Rectangle(initialX, initialY, 300, 20);
  }

  @Override
  protected String getFilename() {
    return "status.png";
  }

}
