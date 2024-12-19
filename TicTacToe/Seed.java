package TicTacToe;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
    CROSS("X", "cat.png"),
    NOUGHT("O", "mouse.jpeg"),
    NO_SEED(" ", null);

    private String displayName;
    private String icon;  // This will store the filename or image path
    private Image img = null;

    // Constructor to initialize the name and icon for each seed type
    Seed(String name, String imageFilename) {
        this.displayName = name;
        this.icon = imageFilename;  // Set 'icon' to the image filename

        // If imageFilename is not null, try to load the image
        if (imageFilename != null) {
            // Use the class loader to get the image resource
            URL imgURL = this.getClass().getClassLoader().getResource(imageFilename);
            if (imgURL != null) {
                // If the image is found, load it into the ImageIcon and then get the image
                ImageIcon icon = new ImageIcon(imgURL);
                this.img = icon.getImage();
            } else {
                System.err.println("Couldn't find file " + imageFilename);
                // Handle missing image gracefully (assign a default or null)
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
