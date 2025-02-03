package arcaym.view.editor.impl.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.objects.GameObjectView;

/**
 * An implementation of the side menu component in swing.
 */
public class SideMenuView implements ViewComponent<JScrollPane> {

    private final Map<GameObjectCategory, Set<GameObjectView>> gameObjects;

    /**
     * A constructor of the component.
     */
    public SideMenuView(final Set<GameObjectType> gameObjects) {
        this.gameObjects = new EnumMap<>(GameObjectCategory.class);
        gameObjects.forEach(gameObject -> {
            this.gameObjects.putIfAbsent(gameObject.category(), new HashSet<>());
            this.gameObjects.get(gameObject.category()).add(new GameObjectView(gameObject));
        });
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public JScrollPane build(final WindowInfo window) {
        final JScrollPane mainPanel = new JScrollPane();
        final JPanel content = new JPanel();
        content.setMinimumSize(mainPanel.getSize());
        content.setLayout(new GridLayout(0, 1));
        gameObjects.forEach((category, objects) -> {
            content.add(new JLabel(category.toString().concat(objects.isEmpty() ? "" : "S"), SwingConstants.CENTER));
            objects.forEach(obj -> {
                final var btn = new JButton();
                final var btnPanel = new CenteredPanel().build(window, obj);
                btnPanel.setOpaque(false);;
                btn.add(btnPanel);
                content.add(btn);
            });
        });
        content.setBackground(Color.WHITE);
        mainPanel.setViewportView(content);
        mainPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setBackground(Color.WHITE);
        return mainPanel;
    }
}
