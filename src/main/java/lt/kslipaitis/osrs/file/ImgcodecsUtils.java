package lt.kslipaitis.osrs.file;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.net.URISyntaxException;

public class ImgcodecsUtils {

  public static Mat readResource(String filename) throws URISyntaxException {
    return readResource("ss", filename);
  }

  public static Mat readResource(String dir, String filename) throws URISyntaxException {
    String file = FileUtils.getPath(dir, filename);

    return Imgcodecs.imread(file);
  }
}
