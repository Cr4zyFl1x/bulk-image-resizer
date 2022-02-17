package eu.sarpex.bulkimageresizer.gui;

import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.sarpex.bulkimageresizer.BulkImageResizer;
import eu.sarpex.bulkimageresizer.gui.frames.About;
import eu.sarpex.bulkimageresizer.gui.frames.Licenses;
import eu.sarpex.bulkimageresizer.gui.frames.ResizeInterface;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController implements ActionListener {

    private BulkImageResizer app;
    private boolean running = false;

    private AppFrame resizeInterface;
    private AppFrame about;
    private AppFrame licenses;

    public GUIController(BulkImageResizer app)
    {
        init(app, false);
    }

    public GUIController(BulkImageResizer app, boolean run)
    {
        init(app, run);
    }

    /**
     * Initializes the GUIController
     * @param app App object
     * @param run Run GUIController after initialization?
     */
    private void init(BulkImageResizer app, boolean run)
    {
        if (app == null) {
            if (Logger.isLoaded()) {
                Logger.log(LogType.CRITICAL, "GUIController could not be initialized! (Invalid App instance)");
            }
            System.exit(-1);
        }

        if (run) run();
    }

    /**
     * Gets the App object for this GUIController
     * @return App object
     */
    public BulkImageResizer getApp()
    {
        return app;
    }

    /**
     * Runs the GUIController if it is not already running
     */
    public void run() {
        if (running) {
            Logger.log(LogType.WARNING, "GUIController is already running!");
            return;
        }
        running = true;
        Logger.log(LogType.INFORMATION, "Running GUI Controller...");
        resizeInterface = new ResizeInterface(this);
    }

    /**
     * Tries to focus a frame by its object
     * @param frame Object of the frame that should be focussed
     * @return true if frame was focussed
     */
    private boolean focusFrame(JFrame frame)
    {
        if (frame != null && frame.isDisplayable()) {

            // Set frame visible
            frame.setVisible(true);

            // Try focussing frame
            frame.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((Component) e.getSource()).getName();
        switch (name) {
            case "button_about":
                if (!focusFrame(about)) {
                    about = new About(this);
                }
                break;
                case "button_licenses":
                    if (!focusFrame(licenses)) {
                        about.dispose();
                        licenses = new Licenses(this);
                    }
                break;
            default:
                Logger.log(LogType.WARNING, "Invalid compnent name '"+ name +"'");
        }

    }
}
