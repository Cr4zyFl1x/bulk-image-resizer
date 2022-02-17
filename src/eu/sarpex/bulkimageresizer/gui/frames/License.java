package eu.sarpex.bulkimageresizer.gui.frames;

import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class License extends AppFrame {
    private JTextPane licenseTextpane;
    private JButton closeButton;
    private JPanel root;

    public License(GUIController controller)
    {
        super(controller, buildFrameTitle("License"), new Dimension(700, 600), true);
        init();
        setLicense();
        setActionListeners();
    }


    @Override
    protected void init()
    {
        add(root);
        setResizable(false);
    }

    private void setActionListeners()
    {
        closeButton.addActionListener(e -> dispose());
    }

    private void setLicense()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("resources/license/LICENSE"));
            String line = reader.readLine();
            while (line != null) {
                licenseTextpane.setText(licenseTextpane.getText() + line + "\n");
                line = reader.readLine();
            }
            licenseTextpane.setCaretPosition(0);
        } catch (IOException e) {
            licenseTextpane.setText("License could not be loaded!");
            Logger.log(LogType.ERROR, "License could not be loaded!");
        }
    }

}
