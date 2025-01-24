package arcaym.view.objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.model.game.core.objects.api.GameObjectCategory;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectSwingView extends JButton {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectSwingView.class);
    private static final long serialVersionUID = 1L;

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
        } catch (IOException e) {
            LOGGER.warn("Error while loading the sprite", e);
        }
    }

    /**
     * Loads the corresponding image of the object created.
     * 
     * @throws IOException if the loading was not possible
     */
    private void loadSprite() throws IOException {
        final BufferedImage icon = ImageIO.read(new File(RESOURCES_PATH + spritePath));
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
