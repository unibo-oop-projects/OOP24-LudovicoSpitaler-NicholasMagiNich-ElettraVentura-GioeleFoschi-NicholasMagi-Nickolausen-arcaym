package arcaym.view.objects;
import java.io.Serializable;

import javax.swing.JPanel;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.api.ViewComponent;
import arcaym.view.components.ImageLabel;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectView implements ViewComponent<JPanel>, Serializable {

    private static final long serialVersionUID = 1L;
    private final GameObjectType type;

    /**
     * Default constructor.
     * 
     * @param type the category (OBSTACLE, WALL, PLAYER...)
     */
    public GameObjectView(final GameObjectType type) {
        this.type = type;
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
    public JPanel build() {
        final JPanel out = new JPanel();
        out.add(new ImageLabel(getImagePath()).build());
        return out;
    }

    private String getImagePath() {
        return switch (this.type) {
            case USER_PLAYER -> "coin.png";
            case WIN_GOAL -> "coin.png";
            case COIN -> "coin.png";
            case FLOOR -> "coin.png";
            case MOVING_X_OBSTACLE -> "static_obstacle.png";
            case MOVING_Y_OBSTACLE -> "static_obstacle.png";
            case SPIKE -> "static_obstacle.png";
            case WALL -> "wall.png";
        };
    }
}
