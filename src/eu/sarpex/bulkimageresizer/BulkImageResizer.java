package eu.sarpex.bulkimageresizer;

import com.bulenkov.darcula.DarculaLaf;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.storage.StaticProperties;

import javax.swing.*;

import java.io.Console;
import java.util.Date;

import static eu.sarpex.bulkimageresizer.storage.StaticProperties.APPNAME;
import static eu.sarpex.bulkimageresizer.storage.StaticProperties.STORAGE_PATH;

public class BulkImageResizer {

    private static BulkImageResizer resizer;

    public static void main(String[] args) {

        // Initialize logger
        initLogger();

        // Create instance of App
        resizer = new BulkImageResizer();

        // Create GUI Controller
        resizer.setController(new GUIController(resizer));

        // Load LookAndFeel
        loadLookAndFeel(new DarculaLaf());

        // Start with additional args
        paramStart(args);

        // Run GUI
        resizer.getController().run();

        // Log end of main
        Logger.log(LogType.SYSTEM, "App " + APPNAME + " successfully loaded!");
    }

    private static void paramStart(String[] args)
    {
        if (args.length != 0) {
            Logger.log(LogType.SYSTEM, "Software has been started with additional parameters listed below.");
            for (int i = 0; i < args.length; i++) {
                Logger.log(LogType.SYSTEM, "- ("+ (i+1) +") " + args[i]);
            }

            switch (args[0]) {
                case "console":
                    resizer.getController().startConsole();
                    JOptionPane.showMessageDialog(null, "Console is currently unavailable!");
                    break;
                default:
                    Logger.log(LogType.WARNING, "Parameter '" + args[0] + "' is invalid!");
            }
        }
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
