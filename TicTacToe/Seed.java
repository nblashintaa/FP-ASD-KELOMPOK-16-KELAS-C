//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package TicTacToe;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
    CROSS("X", "cat.png"),
    NOUGHT("O", "mouse.jpeg"),
    NO_SEED(" ", (String)null);

    private String displayName;
    private Image img = null;

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
}