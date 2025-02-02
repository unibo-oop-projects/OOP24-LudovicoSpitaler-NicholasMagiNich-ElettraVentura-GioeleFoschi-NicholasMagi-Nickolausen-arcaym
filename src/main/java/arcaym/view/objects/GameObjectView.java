package arcaym.view.objects;

import javax.swing.ImageIcon;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
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
    private final GameObjectType type;

    /**
     * Default constructor.
     * 
     * @param type the category (OBSTACLE, WALL, PLAYER...)
     * @param path the path of the image 
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
     * @return the category the game object belongs to
     */
    public GameObjectCategory getCategory() {
        return this.type.category();
    }

    /**
     * 
     * @return an image not wrapped in any component
     */
    public ImageIcon getImage() {
        return new ImageIcon(SwingUtils.getResource(getImagePath(type)));
    }

    private static String getImagePath(GameObjectType type) {
        return switch (type) {
            case 
                USER_PLAYER, 
                WIN_GOAL,
                COIN -> "coin.png";
            case 
                MOVING_X_OBSTACLE,
                MOVING_Y_OBSTACLE,
                SPIKE -> "static_obstacle.png";
            case
                WALL, 
                FLOOR -> "wall.png";
        };
    }
}
