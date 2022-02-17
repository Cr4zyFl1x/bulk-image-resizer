package eu.sarpex.bulkimageresizer.gui.frames;

import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static eu.sarpex.bulkimageresizer.storage.StaticProperties.*;

public class About extends AppFrame {
    private JPanel root;
    private JButton gitHubButton;
    private JButton thirdPartyButton;
    private JButton closeButton;
    private JLabel appVersionLabel;
    private JButton licenseButton;


    public About(GUIController controller)
    {
        super(controller, buildFrameTitle("About"), new Dimension(500, 375), true);
        init();
        setActionListeners();
    }

    @Override
    protected void init()
    {
        add(root);
        setResizable(false);
        appVersionLabel.setText(APPNAME + " - v" + VERSION);

    }

    private void setActionListeners()
    {
        closeButton.addActionListener(e -> this.dispose());
        gitHubButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Cr4zyFl1x/bulk-image-resizer"));
                Logger.log(LogType.INFORMATION, "Opened web page 'https://github.com/Cr4zyFl1x/bulk-image-resizer' in a web browser.");
            } catch (IOException | URISyntaxException ex) {
                JOptionPane.showMessageDialog(this, "Unable to open web page 'https://github.com/Cr4zyFl1x/bulk-image-resizer' in a web browser.");
                Logger.log(LogType.WARNING, "Unable to open web page 'https://github.com/Cr4zyFl1x/bulk-image-resizer' in a web browser.");
            }
        });

        thirdPartyButton.setName("button_thirdparty");
        thirdPartyButton.addActionListener(controller);

        licenseButton.setName("button_license");
        licenseButton.addActionListener(controller);
    }
}
