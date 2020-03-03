package src.ui.java;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class LaunchWebGUI {

    public LaunchWebGUI() throws IOException{
        String url = "./../html/index.html";
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
}
