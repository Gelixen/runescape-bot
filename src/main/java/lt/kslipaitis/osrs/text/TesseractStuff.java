package lt.kslipaitis.osrs.text;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import lt.kslipaitis.osrs.file.FileUtils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractStuff {

  private final Tesseract tesseract;

  public TesseractStuff() {
    tesseract = new Tesseract();
  }

  public List<String> ocr(String filename) throws TesseractException, URISyntaxException {
    setupTesseract();

    String text = tesseract.doOCR(FileUtils.getFile(filename));

    return Arrays.asList(text.split("\n"));
  }

  private void setupTesseract() {
    tesseract.setDatapath("C://tessdata");
    tesseract.setLanguage("eng");
    tesseract.setPageSegMode(1);
    tesseract.setOcrEngineMode(1);

    //        tesseract.setVariable("user_defined_dpi", "300");
    tesseract.setVariable("debug_file", "NUL");
  }

  public String ocrLine(String filename) throws URISyntaxException {
    setupTesseract();

    try {
      return tesseract.doOCR(FileUtils.getFile(filename));
    } catch (TesseractException e) {
      throw new RuntimeException(e);
    }
  }

}
