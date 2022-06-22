package lt.kslipaitis.osrs.file;

import com.google.common.io.Resources;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static File getFile(String filename) throws URISyntaxException {
        return new File(getPath("ss", filename));
    }

    public static String getPath(String dir, String filename) throws URISyntaxException {
        URI resourceURI = Resources.getResource(String.format("%s/%s.png", dir, filename)).toURI();
        return Paths.get(resourceURI).toString();
    }

    public static File createFile(String filename) throws URISyntaxException {
        return new File(createPath(filename));
    }

    public static String createPath(String filename) throws URISyntaxException {
        Path mainDirPath = Paths.get(Resources.getResource("ss").toURI());
        return mainDirPath + "\\" + filename;
    }
}