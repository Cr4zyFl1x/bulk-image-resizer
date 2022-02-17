package eu.sarpex.bulkimageresizer.gui.frames;

import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Licenses extends AppFrame {
    private JPanel root;
    private JTextPane licenseTextpane;
    private JButton closeButton;
    private JScrollPane licenseScrollPane;

    public Licenses (GUIController controller)
    {
        super(controller, buildFrameTitle("Licenses"), new Dimension(700, 600), true);
        init();
        setLicense();
        addActionListeners();
    }

    @Override
    protected void init()
    {
        add(root);
        setResizable(false);
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
        } catch (IOException e) {
            licenseTextpane.setText("Licenses could not be loaded!");
        }
    }

    private void addActionListeners()
    {
        closeButton.addActionListener(e -> dispose());
    }
}
