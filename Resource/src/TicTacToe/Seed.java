package Resource.src.TicTacToe;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
    CROSS("X", "cat.png"),
    NOUGHT("O", "mouse.jpeg"),
    NO_SEED(" ", null);

    private String displayName;
    private Image img = null;

    private Seed(String name, String imageFilename) {
        this.displayName = name;
        if (imageFilename != null) {
            URL imgURL = this.getClass().getClassLoader().getResource(imageFilename);
            ImageIcon icon = null;
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                this.img = icon.getImage();
            } else {
                System.err.println("Couldn't find file " + imageFilename);
                // Handle missing image gracefully (e.g., assign a default image or null)
                this.img = new ImageIcon(this.getClass().getClassLoader().getResource("placeholder.png")).getImage();
            }
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Image getImage() {
        return this.img;
    }
}
