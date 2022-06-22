package lt.kslipaitis.osrs.screenshot;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lt.kslipaitis.osrs.Coordinate;
import lt.kslipaitis.osrs.file.FileUtils;

public abstract class Screenshot {

  @Getter
  protected final int initialX;
  @Getter
  protected final int initialY;

  protected final Robot robot;

  protected Screenshot(int x, int y, Robot robot) {
    this.initialX = x;
    this.initialY = y;
    this.robot = robot;
  }

  public BufferedImage takeScreenshot() throws IOException, URISyntaxException {
    Rectangle rectangle = createRectangle();
    BufferedImage image = robot.createScreenCapture(rectangle);

    File resultImage = FileUtils.createFile(getFilename());
    ImageIO.write(image, "png", resultImage);

    return image;
  }

  protected abstract Rectangle createRectangle();

  protected abstract String getFilename();

  public Coordinate adjustCoordinates(Coordinate coordinate) {
    int adjustedX = initialX + coordinate.getX();
    int adjustedY = initialY + coordinate.getY();
    return new Coordinate(adjustedX, adjustedY);
  }

  public Coordinate adjustCoordinates(int x, int y) {
    int adjustedX = initialX + x;
    int adjustedY = initialY + y;
    return new Coordinate(adjustedX, adjustedY);
  }
}
