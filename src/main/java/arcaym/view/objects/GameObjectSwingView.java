package arcaym.view.objects;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectSwingView extends JPanel {

    private final String resourcesPath = "src/main/resources/";

    /**
     * 
     * @return the path of the resources folder 
     */
    public final String getResourcesPath() {
        return resourcesPath;
    }

    /**
     * Loads the corresponding image of the object created.
     *   
     * @param spritePath the path to the image of the object (must be inside the resources folder)
     * @throws IOException if {@link @param spritePath} not found
     */
    public void loadSprite(final String spritePath) throws IOException {
        BufferedImage icon = ImageIO.read(new File(spritePath));
        JLabel picLabel = new JLabel(new ImageIcon(icon));
        this.add(picLabel);
    }
}
