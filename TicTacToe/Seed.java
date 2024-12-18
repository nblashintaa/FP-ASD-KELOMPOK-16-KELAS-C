package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public enum Seed {
    CROSS("X", "cat.gif.gif"),
    NOUGHT("O", "mouse.gif.gif"),
    NO_SEED(" ", (String)null);

    private String displayName;
    private Image img = null;
    private ImageIcon icon = null;

    private Seed(String name, String imageFilename) {
        this.displayName = name;
        if (imageFilename != null) {
            URL imgURL = this.getClass().getClassLoader().getResource(imageFilename);
            ImageIcon icon = null;
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file " + imageFilename);
            }

            this.img = icon.getImage();
        }

    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Image getImage() {
        return this.img;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }
}