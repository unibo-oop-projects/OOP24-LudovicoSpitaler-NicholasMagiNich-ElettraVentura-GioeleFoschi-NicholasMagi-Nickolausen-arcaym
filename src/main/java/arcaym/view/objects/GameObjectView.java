package arcaym.view.objects;

import java.awt.Image;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.model.game.objects.GameObjectType;
import arcaym.view.components.ImageLabel;
import arcaym.view.utils.SwingUtils;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectView extends ImageLabel {

    /**
     * Default image scaling value.
     */
    public static final double DEFAULT_SCALE = 1.5;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectView.class);
    private final GameObjectType type;

    /**
     * Default constructor.
     * 
     * @param type the category (OBSTACLE, WALL, PLAYER...)
     * @param scaleFactor factor needed to resize the image (default: {@link #DEFAULT_SCALE}) 
     */
    public GameObjectView(final GameObjectType type, final double scaleFactor) {
        super(getImagePath(type), scaleFactor * DEFAULT_SCALE);
        this.type = type;
    }

    /**
     * Default constructor.
     * 
     * @param type the category (OBSTACLE, WALL, PLAYER...)
     */
    public GameObjectView(final GameObjectType type) {
        this(type, ImageLabel.DEFAULT_SCALE);
    }

    /**
     * 
     * @return an image not wrapped in any component
     */
    public Optional<Image> getImage() {
        try {
            return Optional.of(ImageIO.read(SwingUtils.getResource(getImagePath(this.type))));
        } catch (IOException e) {
            LOGGER.error("Cannot load game object image!", e);
            return Optional.empty();
        }
    }

    private static String getImagePath(final GameObjectType type) {
        return switch (type) {
            case USER_PLAYER -> "player.png"; 
            case WIN_GOAL -> "goal.png";
            case COIN -> "coin.png";
            case MOVING_X_OBSTACLE -> "h_obstacle.png";
            case MOVING_Y_OBSTACLE -> "v_obstacle.png";
            case SPIKE -> "spike.png";
            case WALL -> "wall.png"; 
            case FLOOR -> "tile.png";
        };
    }
}
