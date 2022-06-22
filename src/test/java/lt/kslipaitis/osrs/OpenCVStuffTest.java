package lt.kslipaitis.osrs;

import lt.kslipaitis.osrs.file.FileUtils;
import lt.kslipaitis.osrs.file.ImgcodecsUtils;
import lt.kslipaitis.osrs.screenshot.MiddleScreenshot;
import lt.kslipaitis.osrs.screenshot.Screenshot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

class OpenCVStuffTest {

    private Screenshot screenshot;

    @BeforeEach
    void setUp() throws AWTException {
        OpenCVStuff.setupOpenCV();
        screenshot = new MiddleScreenshot(new Robot());
    }

    @Test
    void findTemplateCoords() throws URISyntaxException, IOException {
        screenshot.takeScreenshot();

        CoordsWithScore res = OpenCVStuff.findTemplateCoords("middle", "logging/yew", "template1", 5);
        System.out.println(res);
    }

    @Test
    void findTemplateCoords2() throws URISyntaxException, IOException {
        screenshot.takeScreenshot();

        CoordsWithScore res = OpenCVStuff.findTemplateCoords("middle", "logging/yew", "template2", 5);
        System.out.println(res);
    }

    @Test
    void bilateralFilter_15_80_80() throws URISyntaxException {
        Mat inputMat = ImgcodecsUtils.readResource("opencv", "gold-mine");
        Mat outputMat = new Mat();

        Imgproc.bilateralFilter(inputMat, outputMat, 15, 80, 80, Core.BORDER_DEFAULT);

        Imgcodecs.imwrite(FileUtils.createPath("gold-mine-bilateral-filter-15-80-80.png"), outputMat);
    }

    @Test
    void bilateralFilter_5_80_80() throws URISyntaxException {
        Mat inputMat = ImgcodecsUtils.readResource("opencv", "gold-mine");
        Mat outputMat = new Mat();

        Imgproc.bilateralFilter(inputMat, outputMat, 5, 80, 80, Core.BORDER_DEFAULT);

        Imgcodecs.imwrite(FileUtils.createPath("gold-mine-bilateral-filter-5-80-80.png"), outputMat);
    }

    @Test
    void bilateralFilter_30_80_80() throws URISyntaxException {
        Mat inputMat = ImgcodecsUtils.readResource("opencv", "gold-mine");
        Mat outputMat = new Mat();

        Imgproc.bilateralFilter(inputMat, outputMat, 30, 80, 80, Core.BORDER_DEFAULT);

        Imgcodecs.imwrite(FileUtils.createPath("gold-mine-bilateral-filter-30-80-80.png"), outputMat);
    }
}