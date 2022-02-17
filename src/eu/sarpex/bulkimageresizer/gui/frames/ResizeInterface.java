package eu.sarpex.bulkimageresizer.gui.frames;

import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.sarpex.bulkimageresizer.gui.GUIController;
import eu.sarpex.bulkimageresizer.gui.frametype.AppFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static eu.sarpex.bulkimageresizer.storage.StaticProperties.APPNAME;
import static eu.sarpex.bulkimageresizer.storage.StaticProperties.DEFAULT_DIMENSION;

public class ResizeInterface extends AppFrame {

    private JPanel root;
    private JTextField imageOpenPathTextfield;
    private JButton selectImageButton;
    private JTextField imageSavePathTextfield;
    private JButton selectSaveFolderButton;
    private JButton convertButton;
    private JTextField pixelsTextfield;
    private JCheckBox keepTransparencyCheckbox;
    private JButton aboutButton;
    private JLabel appnameLabel;

    protected final int PIXEL_SYNTAX_OK = 0;
    protected final int PIXEL_SYNTAX_ERROR = 1;
    protected final int MISSING_PIXELS_ERROR = 2;


    JFileChooser openImageChooser = new JFileChooser();
    JFileChooser openFolderChooser = new JFileChooser();

    public ResizeInterface(GUIController controller)
    {
        super(controller, buildFrameTitle("Resize image"), DEFAULT_DIMENSION, false);
        init();
        setChooserConfig();
        setActionListeners();
    }

    @Override
    protected void init()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(root);
        pack();
        setVisible(true);
        setResizable(false);
        appnameLabel.setText(APPNAME);
    }

    private void setChooserConfig()
    {
        FileFilter fileFilter = new FileNameExtensionFilter("Image files", "png");

        openImageChooser.setDialogTitle("Choose image ...");
        openImageChooser.setFileFilter(fileFilter);

        openFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        openFolderChooser.setAcceptAllFileFilterUsed(false);
        openFolderChooser.setDialogTitle("Choose save folder ...");
    }

    private int[] getPixelsArray()
    {
        String pixelsRaw = pixelsTextfield.getText();
        String pixelsProcessed = pixelsRaw.replaceAll("\\s", "");
        int[] pixels = new int[pixelsProcessed.split(",").length];
        for (int i = 0; i < pixelsProcessed.split(",").length; i++) {
            try {
                pixels[i] = Integer.parseInt(pixelsProcessed.split(",")[i]);
            } catch (NumberFormatException e) {
                return new int[]{};
            }
        }
        return pixels;
    }

    private int checkPixelInput()
    {
        if (pixelsTextfield.getText().isEmpty()) return MISSING_PIXELS_ERROR;
        if (getPixelsArray().length == 0) return PIXEL_SYNTAX_ERROR;
        return PIXEL_SYNTAX_OK;
    }

    private String getFileExtension(String filename)
    {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    private String getRawFilename(String filename)
    {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    private String generateFilename(String filename, int dimension)
    {
        return getRawFilename(filename) + "_" + dimension + "x." + getFileExtension(filename);
    }

    private void setProccessing(boolean proccessing)
    {
        selectImageButton.setEnabled(!proccessing);
        selectSaveFolderButton.setEnabled(!proccessing);
        convertButton.setEnabled(!proccessing);
        pixelsTextfield.setEditable(!proccessing);
    }


    protected void setActionListeners()
    {
        selectImageButton.addActionListener(e -> {
            openImageChooser.showOpenDialog(this);
            Logger.log(LogType.INFORMATION, "User selected source: '" + openImageChooser.getSelectedFile().getPath() + "'");
            if (openImageChooser.getSelectedFile() != null) imageOpenPathTextfield.setText(openImageChooser.getSelectedFile().getPath());
        });

        aboutButton.addActionListener(getController());
        aboutButton.setName("button_about");

        selectSaveFolderButton.addActionListener(e -> {
            openFolderChooser.showOpenDialog(this);
            Logger.log(LogType.INFORMATION, "User selected folder: '" + openFolderChooser.getSelectedFile().getPath() + "'");
            if (openFolderChooser.getSelectedFile() != null) imageSavePathTextfield.setText(openFolderChooser.getSelectedFile().getPath());
        });

        convertButton.addActionListener(e -> {
            if (!(openFolderChooser.getSelectedFile() != null && openImageChooser.getSelectedFile() != null)) {
                JOptionPane.showMessageDialog(this, "Please select an image & a save location for the converted images.", buildFrameTitle("Error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            switch (checkPixelInput()) {
                case MISSING_PIXELS_ERROR -> JOptionPane.showMessageDialog(this, "Please type in the targeted resolutions for the new images.", buildFrameTitle("Error"), JOptionPane.ERROR_MESSAGE);
                case PIXEL_SYNTAX_ERROR -> JOptionPane.showMessageDialog(this, "The syntax of the targeted resolutions contains errors.\n\nSyntax: (int)pixel1, (int)pixel2, ..., (int)pixeln", buildFrameTitle("Error"), JOptionPane.ERROR_MESSAGE);
            }
            if (checkPixelInput() == PIXEL_SYNTAX_OK) {
                setProccessing(true);
                int[] pixels = getPixelsArray();
                BufferedImage image;
                for (int i = 0; i < pixels.length; i++) {
                    try {
                        image = createResizedCopy(ImageIO.read(openImageChooser.getSelectedFile()), pixels[i], pixels[i], !keepTransparencyCheckbox.isSelected());
                        File output = new File(openFolderChooser.getSelectedFile().getPath() + "/" + generateFilename(openImageChooser.getSelectedFile().getName(), pixels[i]));
                        ImageIO.write(image, getFileExtension(openImageChooser.getSelectedFile().getName()), output);
                        Logger.log(LogType.INFORMATION, "Resized image '" + openImageChooser.getSelectedFile().getName() + "' to image '" + generateFilename(openImageChooser.getSelectedFile().getName(), pixels[i]) + "'");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "An error occoured while converting the image.", buildFrameTitle("Error"), JOptionPane.ERROR_MESSAGE);
                        Logger.log(LogType.ERROR, "An error occoured while converting image '" + generateFilename(openImageChooser.getSelectedFile().getName(), pixels[i]) + "'. Size:" + pixels[i] + "x");
                        return;
                    }
                }
                setProccessing(false);
                Logger.log(LogType.INFORMATION, "The image '" + openImageChooser.getSelectedFile().getName() + "' was successfully converted into the given resulutions.");
                JOptionPane.showMessageDialog(this, "The image was successfully converted into the targeted resolutions.", buildFrameTitle("Conversion successful!"), JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha)
    {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
}
