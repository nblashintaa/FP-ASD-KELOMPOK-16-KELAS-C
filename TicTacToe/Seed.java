package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display icon (text or image) for each of the item,
 * and define the related variable/constructor/getter.
 *
 * Ideally, we should define two enums with inheritance, which is,
 * however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
    CROSS("X", "Resource/cat.jpg"),   // displayName, imageFilename
    NOUGHT("O", "Resource/mouse.jpg"),
    NO_SEED(" ", null);

    // Private variables
    private String displayName;
    private Image img;

    // Constructor (must be private)
    private Seed(String displayName, String imageFilename) {
        this.displayName = displayName;

        // Set image if imageFilename is provided
        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                img = icon.getImage();
            } else {
                System.err.println("Couldn't find file " + imageFilename);
            }
        }
    }

    // Public getters
    public String getDisplayName() {
        return displayName;
    }

    public Image getImage() {
        return img;
    }

    // Optional getIcon method to return the displayName (like "X" or "O")
    public String getIcon() {
        return displayName;
    }
}
