package eu.sarpex.bulkimageresizer.gui.frames;

import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThirdParty extends AppFrame {
    private JPanel root;
    private JTextPane thirdPartyTextpane;
    private JButton closeButton;

    public ThirdParty(GUIController controller)
    {
        super(controller, buildFrameTitle("Third-Party licenses"), new Dimension(700, 600), true);
        init();
        setThirdParty();
        addActionListeners();
    }

    @Override
    protected void init()
    {
        add(root);
        setResizable(false);
    }

    private void setThirdParty()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("resources/license/THIRD-PARTY"));
            String line = reader.readLine();
            while (line != null) {
                thirdPartyTextpane.setText(thirdPartyTextpane.getText() + line + "\n");
                line = reader.readLine();
            }
            thirdPartyTextpane.setCaretPosition(0);
        } catch (IOException e) {
            thirdPartyTextpane.setText("Licenses could not be loaded!");
        }
    }

    private void addActionListeners()
    {
        closeButton.addActionListener(e -> dispose());
    }
}
