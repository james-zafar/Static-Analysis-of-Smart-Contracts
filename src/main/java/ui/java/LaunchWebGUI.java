package ui.java;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class LaunchWebGUI {

    /**
     * Class for launching Web GUI in default browser
     */
    public LaunchWebGUI() {
        try {
            //Path of the main file
            String url = System.getProperty("user.dir") + "/src/main/java/ui/html/index.html";
            File file = new File(url);
            Desktop.getDesktop().browse(file.toURI());
        }catch(IOException e) {
            System.out.println("Error, the web GUI could not be loaded at this time.");
            System.out.println(e.getMessage());
        }
    }
}
