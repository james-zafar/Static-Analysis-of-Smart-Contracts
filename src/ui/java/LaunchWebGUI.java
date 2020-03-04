package src.ui.java;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class LaunchWebGUI {

    public LaunchWebGUI() throws IOException{
        String url = System.getProperty("user.dir") + "/src/ui/html/index.html";
        File file = new File(url);
        Desktop.getDesktop().browse(file.toURI());
    }
}
