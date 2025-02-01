package arcaym.view.objects;

import javax.swing.JLabel;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.components.ImageLabel;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.core.api.ViewComponent;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectView implements ViewComponent<JLabel> {

    private static final double DEFAULT_SCALE = 2;
    private final GameObjectType type;
    private double scale;

    /**
     * Default constructor.
     * 
     * @param type the category (OBSTACLE, WALL, PLAYER...)
     * @param scaleFactor factor needed to resize the image (default: {@link #DEFAULT_SCALE}) 
     */
    public GameObjectView(final GameObjectType type, final double scaleFactor) {
        this.type = type;
        this.scale = scaleFactor * DEFAULT_SCALE;
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
     * {@inheritDoc}
     */
    @Override
    public JLabel build(final WindowInfo window) {
        return new ImageLabel(getImagePath(), scale).build(window);
    }

    private String getImagePath() {
        return switch (this.type) {
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
