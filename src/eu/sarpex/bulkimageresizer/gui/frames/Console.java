package eu.sarpex.bulkimageresizer.gui.frames;

import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.swing.*;

import java.awt.*;

import static eu.sarpex.bulkimageresizer.storage.StaticProperties.DEFAULT_DIMENSION;

public class Console extends AppFrame {
    private JPanel root;
    private JTextPane textPane1;
    private JTextField textField1;
    private JButton sendButton;

    public Console(GUIController controller)
    {
        super(controller, buildFrameTitle("Developer Console"), new Dimension(700, 500), true);
        init();
    }

    @Override
    protected void init()
    {
        add(root);
        setMinimumSize(new Dimension(700,500));
    }
}
