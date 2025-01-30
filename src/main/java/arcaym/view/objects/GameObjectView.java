package arcaym.view.objects;
import java.io.Serializable;

import javax.swing.JPanel;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.components.ImageLabel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;

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
    public JPanel build(final WindowInfo window) {
        final JPanel out = new JPanel();
        out.add(new ImageLabel(getImagePath()).build(window));
        return out;
    }

    private String getImagePath() {
        return switch (this.type) {
            case 
                USER_PLAYER, 
                WIN_GOAL,
                COIN,
                FLOOR -> "coin.png";
            case 
                MOVING_X_OBSTACLE,
                MOVING_Y_OBSTACLE,
                SPIKE -> "static_obstacle.png";
            case WALL -> "wall.png";
        };
    }
}
