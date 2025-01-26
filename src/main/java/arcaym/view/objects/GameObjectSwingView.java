package arcaym.view.objects;

import javax.swing.JPanel;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.api.ViewComponent;
import arcaym.view.components.ImageLabel;

/**
 * Generic class to represent all the views of the objects implemented via Swing. 
 */
public class GameObjectSwingView implements ViewComponent<JPanel> {

    private final GameObjectType type;
    private final String spritePath;

    /**
     * Default constructor.
     * 
     * @param category the category (OBSTACLE, WALL, PLAYER...)
     * @param spritePath the path of the corresponding image.
     */
    public GameObjectSwingView(final GameObjectType type, final String spritePath) {
        this.type = type;
        this.spritePath = spritePath;
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
