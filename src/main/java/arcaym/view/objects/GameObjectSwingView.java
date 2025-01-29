package arcaym.view.objects;
import javax.swing.JButton;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.api.ViewComponent;
import arcaym.view.components.ImageLabel;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectSwingView implements ViewComponent<JPanel> {

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
        return this.type.category();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        final JPanel out = new JPanel();
        out.add(new ImageLabel(spritePath).build());
        return out;
    }
}
