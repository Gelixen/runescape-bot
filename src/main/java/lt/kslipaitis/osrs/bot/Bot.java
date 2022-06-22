package lt.kslipaitis.osrs.bot;

import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Bot {

    void execute() throws InterruptedException, URISyntaxException, IOException, AWTException, TesseractException;
}
