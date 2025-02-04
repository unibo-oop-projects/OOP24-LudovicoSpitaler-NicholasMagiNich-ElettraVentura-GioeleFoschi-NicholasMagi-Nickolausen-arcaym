package arcaym.view.editor.impl.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
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
import arcaym.view.utils.SwingUtils;

/**
 * An implementation of the side menu component in swing.
 */
public class SideMenuView implements ViewComponent<JScrollPane> {

    private final Map<GameObjectCategory, Set<GameObjectType>> gameObjects;
    private final Map<JButton, GameObjectType> menuItems;
    private final Consumer<GameObjectType> gameObjectConsumer;

    /**
     * A constructor of the component.
     * 
     * @param gameObjects
     * @param gameObjectConsumer
     */
    public SideMenuView(final Set<GameObjectType> gameObjects, final Consumer<GameObjectType> gameObjectConsumer) {
        this.gameObjects = new EnumMap<>(GameObjectCategory.class);
        gameObjects.forEach(gameObject -> {
            this.gameObjects.putIfAbsent(gameObject.category(), EnumSet.noneOf(GameObjectType.class));
            this.gameObjects.get(gameObject.category()).add(gameObject);
        });
        this.gameObjectConsumer = gameObjectConsumer;
        this.menuItems = new HashMap<>();
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public JScrollPane build(final WindowInfo window) {
        final JScrollPane mainPanel = new JScrollPane();
        final JPanel content = new JPanel();
        content.setLayout(new GridLayout(0, 1));
        gameObjects.forEach((category, objectsView) -> {
            content.add(new JLabel(category.toString().concat(objectsView.isEmpty() ? "" : "S"), SwingConstants.CENTER));
            objectsView.forEach(obj -> {
                final var btn = new JButton();
                final var btnPanel = new CenteredPanel().build(window, new GameObjectView(obj));
                btnPanel.setOpaque(false);
                btn.add(btnPanel);
                btn.addActionListener(evt -> {
                    final var src = (JButton) evt.getSource();
                    gameObjectConsumer.accept(menuItems.get(src));
                });
                menuItems.put(btn, obj);
                content.add(btn);
            });
        });
        content.setBackground(Color.WHITE);
        mainPanel.setViewportView(content);
        mainPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setBackground(Color.WHITE);
        final var sideMenuGap = SwingUtils.getNormalGap(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                sideMenuGap,
                sideMenuGap,
                sideMenuGap,
                sideMenuGap));
        return mainPanel;
    }
}
