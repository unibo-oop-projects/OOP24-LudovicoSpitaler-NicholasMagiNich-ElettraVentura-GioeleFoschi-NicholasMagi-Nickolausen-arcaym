package arcaym.view.editor.components.impl;

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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.editor.components.api.SideMenu;
import arcaym.view.objects.GameObjectSwingView;

/**
 * An implementation of the side menu component in swing.
 */
public class SideMenuView extends JScrollPane implements SideMenu {

    private static final long serialVersionUID = 1L;
    private JPanel content;
    private final Map<GameObjectCategory, Set<GameObjectSwingView>> gameObjects;

    /**
     * A constructor of the component.
     */
    public SideMenuView(final Map<GameObjectCategory, Set<GameObjectSwingView>> gameObjects) {
        this.gameObjects = Collections.unmodifiableMap(gameObjects);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void initView() {
        content = new JPanel();
        content.setMinimumSize(this.getSize());
        content.setLayout(new GridLayout(0, 1));
        gameObjects.forEach((category, objects) -> {
            content.add(new JLabel(category.toString(), SwingConstants.CENTER));
            objects.forEach(content::add);
        });
        this.setViewportView(content);
        this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }
}
