package arcaym.view.objects;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import arcaym.model.game.core.objects.api.GameObjectCategory;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectSwingView extends JButton {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private final GameObjectCategory category;
    private final String spritePath;

    /**
     * Default constructor.
     * 
     * @param category the category (OBSTACLE, WALL, PLAYER...)
     * @param spritePath the path of the corresponding image.
     */
    public GameObjectSwingView(final GameObjectCategory category, final String spritePath) {
        this.category = category;
        this.spritePath = spritePath;
        try {
            this.loadSprite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the corresponding image of the object created.
     * 
     * @throws IOException if the loading was not possible
     */
    private void loadSprite() throws IOException {
        BufferedImage icon = ImageIO.read(new File(RESOURCES_PATH + spritePath));
        this.setIcon(new ImageIcon(icon));
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }

    /**
     * 
     * @return the category the game object belongs to
     */
    public GameObjectCategory getCategory() {
        return this.category;
    }
}
