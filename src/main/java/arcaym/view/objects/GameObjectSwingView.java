package arcaym.view.objects;
import javax.swing.JButton;

import arcaym.model.game.core.objects.api.GameObjectCategory;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectSwingView extends JButton {

    private static final long serialVersionUID = 1L;
    private final GameObjectCategory category;

    /**
     * Default constructor.
     * 
     * @param category the category (OBSTACLE, WALL, PLAYER...)
     */
    public GameObjectSwingView(final GameObjectCategory category) {
        this.category = category;
    }

    /**
     * 
     * @return the category the game object belongs to
     */
    public GameObjectCategory getCategory() {
        return this.category;
    }
}
