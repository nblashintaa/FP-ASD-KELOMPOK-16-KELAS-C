package TicTacToe;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
    CROSS("X", "cat.png"),
    NOUGHT("O", "mouse.jpeg"),
    NO_SEED(" ", null);

    private String displayName;
    private String icon;  // Declare 'icon' here
    private Image img = null;

    // Constructor to initialize the name and icon for each seed type
    Seed(String name, String imageFilename) {
        this.displayName = name;
        this.icon = name;  // Set 'icon' equal to 'name'
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

    public String getIcon() {
        return this.icon;
    }
}
