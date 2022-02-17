package eu.sarpex.bulkimageresizer.gui.frametype;

import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.storage.StaticProperties;

import javax.swing.*;

import java.awt.*;

import static eu.sarpex.bulkimageresizer.storage.StaticProperties.APPNAME;

public abstract class AppFrame extends JFrame {

    /**
     * GUIController for this Frame
     */
    protected GUIController controller = null;

    public AppFrame(GUIController gc, String title, Dimension dim, boolean visible) {

        // Set Frame title
        super(title);

        // Set Dimension of frame
        this.setSize(dim);

        // Frame icon
        this.setIconImage(StaticProperties.FRAME_ICON.getImage());

        // Set Visibility
        this.setVisible(visible);

        // Set GUI Controller
        this.setController(gc);

    }

    public AppFrame(GUIController gc, String title, int[] dim, boolean visible) {

        // Set Frame title
        super(title);

        // Set Dimension of frame
        if (dim.length == 2) this.setSize(dim[0], dim[1]);

        // Frame icon
        this.setIconImage(StaticProperties.FRAME_ICON.getImage());

        // Set Visibility
        this.setVisible(visible);

        // Set GUI Controller
        this.setController(gc);

    }

    public AppFrame(GUIController gc, String title, boolean visible) {

        // Set Frame title
        super(title);

        // Frame icon
        this.setIconImage(StaticProperties.FRAME_ICON.getImage());

        // Set Visibility
        this.setVisible(visible);

        // Set GUI Controller
        this.setController(gc);
    }

    protected void setController(GUIController gc)
    {
        this.controller = gc;
    }

    protected GUIController getController()
    {
        return this.controller;
    }

    protected static String buildFrameTitle(String title)
    {
        return APPNAME + " | " + title;
    }

    protected abstract void init();

}
