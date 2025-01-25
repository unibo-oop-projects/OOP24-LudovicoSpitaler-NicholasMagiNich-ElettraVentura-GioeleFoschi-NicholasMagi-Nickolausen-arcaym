package arcaym.view.editor.components;

// import java.awt.Color;
import java.awt.GridLayout;
import java.util.Collections;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Set;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.api.ViewComponent;
import arcaym.view.objects.GameObjectSwingView;

/**
 * An implementation of the side menu component in swing.
 */
public class SideMenuView implements ViewComponent<JScrollPane> {

    private final JScrollPane mainPanel = new JScrollPane();
    private JPanel content;
    private final Map<GameObjectCategory, Set<GameObjectSwingView>> gameObjects;

    /**
     * A constructor of the component.
     * @param gameObjects the map of unlocked items
     */
    public SideMenuView(final Map<GameObjectCategory, Set<GameObjectSwingView>> gameObjects) {
        this.gameObjects = Collections.unmodifiableMap(gameObjects);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public JScrollPane build() {
        content = new JPanel();
        content.setMinimumSize(mainPanel.getSize());
        content.setLayout(new GridLayout(0, 1));
        gameObjects.forEach((category, objects) -> {
            content.add(new JLabel(category.toString(), SwingConstants.CENTER));
            objects.forEach(content::add);
        });
        mainPanel.setViewportView(content);
        mainPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return mainPanel;
    }
}
