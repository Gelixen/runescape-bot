package lt.kslipaitis.osrs;

import lt.kslipaitis.osrs.file.FileUtils;
import lt.kslipaitis.osrs.file.ImgcodecsUtils;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class OpenCVStuff {

    public static CoordsWithScore findTemplateCoords(String filename,
                                                     String dir,
                                                     String templateName,
                                                     int method) throws URISyntaxException {
        setupOpenCV();

        //        Mat mainImage = lt.kslipaitis.bot.file.ImgcodecsUtils.readResource("iron1", "main");
        Mat mainImage = ImgcodecsUtils.readResource(filename);
        Mat template = ImgcodecsUtils.readResource(dir, templateName);
        Mat result = new Mat();

        //        applyBilateralFilter(inputMat, result);
        //        applyThreshold(mainImage, filename);
        //        blur(mainImage);
        //        bilateralFilter(mainImage, mainImage);
        final CoordsWithScore coordsWithScore = matchTemplate(mainImage, template, result, method);
        Imgcodecs.imwrite(FileUtils.createPath(filename + "-result.png"), mainImage);
        return coordsWithScore;

        //        final String outputFilename = String.format("%s-opencv.png", filename);
        //        String outputPath = file.FileUtils.createPath(outputFilename);
        //        Imgcodecs.imwrite(outputPath, result);

        //        return filename + "-opencv";
    }

    static void setupOpenCV() {
        OpenCV.loadShared();
    }

    private static CoordsWithScore matchTemplate(Mat input,
                                                 Mat template,
                                                 Mat result,
                                                 int method) throws URISyntaxException {
        Imgproc.matchTemplate(input, template, result, method);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        Point matchLoc = mmr.maxLoc;

        //        matchTemplateOthers(input, template, result);
        matchTemplateMostMatching(input, template, result);

        //        System.out.println("--------------------------");
        //        System.out.println(matchLoc.x + " " + matchLoc.y);
        //        System.out.println(Arrays.toString(result.get((int) matchLoc.y, (int) matchLoc.x)));

        Imgproc.rectangle(input,
                          matchLoc,
                          new Point(matchLoc.x + template.rows(), matchLoc.y + template.height()),
                          new Scalar(0, 0, 255));

        //        Imgcodecs.imwrite(FileUtils.createPath("result.png"), input);
        //        Imgcodecs.imwrite(FileUtils.createPath(String.format("result-%s-%s.png", System.currentTimeMillis()/1000, mmr.maxVal)), input);

        final int templateXMiddle = template.rows() / 2;
        final int templateYMiddle = template.height() / 2;
        return new CoordsWithScore(((int) matchLoc.x) + templateXMiddle,
                                   ((int) matchLoc.y) + templateYMiddle,
                                   mmr.maxVal);
    }

    private static void matchTemplateMostMatching(Mat input, Mat template, Mat result) {
        Temp[] arr = new Temp[Math.toIntExact(result.total())];
        int index = 0;
        for (int x = 0; x < result.width() - 1; x++) {
            for (int y = 0; y < result.height() - 1; y++) {
                final double[] doubles = result.get(y, x);
                arr[index++] = new Temp(x, y, doubles[0]);
            }
        }

        Arrays.stream(arr)
              .filter(Objects::nonNull)
              .sorted(Comparator.comparingDouble(Temp::getScore).reversed())
              .limit(10)
              .forEach(a -> {
                  Imgproc.rectangle(input,
                                    new Point(a.getX(), a.getY()),
                                    new Point(a.getX() + template.rows(), a.getY() + template.height()),
                                    new Scalar(255, 255, 255));
              });
    }

    protected static void bilateralFilter(Mat inputMat, Mat outputMat) {
        Imgproc.bilateralFilter(inputMat, outputMat, 15, 80, 80, Core.BORDER_DEFAULT);
    }

    private static void matchTemplateOthers(Mat inputMat, Mat inputMat2, Mat outputMat) {

        for (int x = 0; x < outputMat.width() - 1; x++) {
            for (int y = 0; y < outputMat.height() - 1; y++) {
                final double[] doubles = outputMat.get(y, x);

                if (doubles != null && doubles[0] >= 0.99) {
                    System.out.printf("%s,%s %s\n", x, y, Arrays.toString(doubles));
                    Imgproc.rectangle(inputMat,
                                      new Point(x, y),
                                      new Point(x + inputMat2.rows(), y + inputMat2.height()),
                                      new Scalar(255, 255, 255));
                }
            }
        }
    }

    private static void applyThreshold(Mat inputMat, String filename) throws URISyntaxException {
        Mat result = new Mat();
        Imgproc.threshold(inputMat, inputMat, 200, 1000, Imgproc.THRESH_BINARY);

        //        Imgcodecs.imwrite(FileUtils.createPath(filename + "-blackwhite.png"), result);
    }


    private static void blur(Mat inputMat) {
        Size size = new Size(20, 20);
        Point point = new Point(10, 15);
        Imgproc.medianBlur(inputMat, inputMat, 31);
    }

    private static void resize(Mat inputMat, Mat outputMat) {
        Size size = new Size(inputMat.width() * 2, inputMat.height() * 2);
        Imgproc.resize(inputMat, outputMat, size, 0, 0, Imgproc.INTER_CUBIC);
    }

}
