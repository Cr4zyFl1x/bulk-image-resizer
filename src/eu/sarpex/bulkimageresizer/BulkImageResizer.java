package eu.sarpex.bulkimageresizer;

import com.bulenkov.darcula.DarculaLaf;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.storage.StaticProperties;

import javax.swing.*;

import java.util.Date;

import static eu.sarpex.bulkimageresizer.storage.StaticProperties.APPNAME;
import static eu.sarpex.bulkimageresizer.storage.StaticProperties.STORAGE_PATH;

public class BulkImageResizer {

    public static void main(String[] args) {

        // Initialize logger
        initLogger();

        // Load LookAndFeel
        loadLookAndFeel(new DarculaLaf());

        // Create instance of App
        BulkImageResizer resizer = new BulkImageResizer();

        // Create & Run GUI
        resizer.setController(new GUIController(resizer));
        resizer.getController().run();

        // Log end of main
        Logger.log(LogType.SYSTEM, "App " + APPNAME + " successfully loaded!");
    }

    /**
     * Initialize Logger
     */
    private static void initLogger() {
        Logger logger = new Logger("BulkImageResizer", new Date(), STORAGE_PATH + "/logs");
        logger.load();
        Logger.log(LogType.SYSTEM, "Logger initialization completed!");
        Logger.log(LogType.INFORMATION, APPNAME + " - Version: " + StaticProperties.VERSION);
    }

    /**
     * Load LookAndFeel
     * @param l LookAndFeel Object
     */
    private static void loadLookAndFeel(LookAndFeel l) {
        try {
            UIManager.setLookAndFeel(l);
            Logger.log(LogType.SYSTEM, "LookAndFeel " + l.getName() + " loaded successfully!");
        } catch (UnsupportedLookAndFeelException e) {
            Logger.log(LogType.CRITICAL, "Unable to load LookAndFeel!");
            JOptionPane.showMessageDialog(null, "We're unable to load the LookAndFeel for this Application.", "Critical application error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void exit(int statuscode)
    {
        Logger.log(LogType.SYSTEM, "Program exit called ... - Status code: " + statuscode);
        System.exit(statuscode);
    }


    private GUIController controller;
    public void setController(GUIController controller) {
        this.controller = controller;
    }
    public GUIController getController() {
        return controller;
    }
}
