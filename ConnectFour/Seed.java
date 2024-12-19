package ConnectFour;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 *   and define the related variable/constructor/getter.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
    CROSS("X", "connectFour/pixelart-8914_256.gif"),   // displayName, imageFilename
    NOUGHT("O", "connectFour/sepeda.gif"),
    NO_SEED(" ", null),
    PLAYER1("P1", "connectFour/player1.gif"),         // for Tic-Tac-Toe PLAYER1
    PLAYER2("P2", "connectFour/player2.gif"),         // for Tic-Tac-Toe PLAYER2
    EMPTY(" ", null),                                 // for Tic-Tac-Toe EMPTY cell
    WIN("W", "connectFour/win.gif");                  // for indicating a winning cell

    // Private variables
    private String displayName;
    private Image img = null;

    // Constructor (must be private)
    private Seed(String name, String imageFilename) {
        this.displayName = name;

        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            ImageIcon icon = null;
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                //System.out.println(icon);  // debugging
            } else {
                System.err.println("Couldn't find file " + imageFilename);
            }
            img = icon != null ? icon.getImage() : null;
        }
    }

    // Public getters
    public String getDisplayName() {
        return displayName;
    }
    public Image getImage() {
        return img;
    }
}
