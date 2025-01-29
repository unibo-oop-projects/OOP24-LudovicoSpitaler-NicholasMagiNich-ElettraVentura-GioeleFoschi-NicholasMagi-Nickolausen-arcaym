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
public class GameObjectSwingView implements ViewComponent<JPanel>, Serializable {

    private static final long serialVersionUID = 1L;
    private final GameObjectType type;
    private final String assetPath;

    /**
     * Default constructor.
     * 
     * @param type the category (OBSTACLE, WALL, PLAYER...)
     * @param assetPath the path of the image
     */
    public GameObjectSwingView(final String assetPath, final GameObjectType type) {
        this.type = type;
        this.assetPath = assetPath;
    }

    /**
     * 
     * @return the category the game object belongs to
     */
    public GameObjectCategory getType() {
        return this.type.category();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        final JPanel out = new JPanel();
        out.add(new ImageLabel(this.assetPath).build());
        return out;
    }
}
