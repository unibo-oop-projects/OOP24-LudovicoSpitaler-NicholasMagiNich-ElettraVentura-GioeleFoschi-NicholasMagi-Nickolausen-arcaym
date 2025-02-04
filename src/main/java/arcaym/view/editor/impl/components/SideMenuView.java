package arcaym.view.editor.impl.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JSeparator;
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
    private final Runnable setNotErase;

    /**
     * A constructor of the component.
     * 
     * @param gameObjects
     * @param gameObjectConsumer
     * @param setObjectPlace used to set the editor to placing mode.
     */
    public SideMenuView(
        final Set<GameObjectType> gameObjects,
        final Consumer<GameObjectType> gameObjectConsumer,
        final Runnable setObjectPlace) {
        this.gameObjects = new EnumMap<>(GameObjectCategory.class);
        gameObjects.forEach(gameObject -> {
            this.gameObjects.putIfAbsent(gameObject.category(), EnumSet.noneOf(GameObjectType.class));
            this.gameObjects.get(gameObject.category()).add(gameObject);
        });
        this.gameObjectConsumer = gameObjectConsumer;
        this.menuItems = new HashMap<>();
        this.setNotErase = setObjectPlace;
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public JScrollPane build(final WindowInfo window) {
        final JScrollPane mainPanel = new JScrollPane();
        final JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        final var gap = SwingUtils.getNormalGap(content);
        gbc.insets = new Insets(0, 0, gap, gap);
        gameObjects.forEach((category, objectsView) -> {
            content.add(
                new JLabel(category.toString().concat(objectsView.isEmpty() ? "" : "S"), SwingConstants.CENTER),
                gbc);
            objectsView.forEach(obj -> {
                final var btn = new JButton();
                final var btnPanel = new CenteredPanel().build(window, new GameObjectView(obj));
                btnPanel.setOpaque(false);
                btn.add(btnPanel);
                btn.addActionListener(evt -> {
                    menuItems.keySet().forEach(b -> b.setEnabled(true));
                    final var src = (JButton) evt.getSource();
                    src.setEnabled(false);
                    gameObjectConsumer.accept(menuItems.get(src));
                    setNotErase.run();
                });
                content.add(btn, gbc);
                menuItems.put(btn, obj);
            });
            content.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
        });
        content.remove(content.getComponentCount() - 1);
        content.setBackground(Color.WHITE);
        final var horizontalContentGap = SwingUtils.getNormalGap(content);
        content.setBorder(BorderFactory.createEmptyBorder(0, horizontalContentGap, 0, horizontalContentGap));
        mainPanel.setViewportView(content);
        mainPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.setBackground(Color.WHITE);
        final var sideMenuHorizontalGap = SwingUtils.getNormalGap(mainPanel);
        final var sideMenuVerticalGap = SwingUtils.getBigGap(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                sideMenuVerticalGap,
                sideMenuHorizontalGap,
                sideMenuVerticalGap,
                sideMenuHorizontalGap));
        return mainPanel;
    }
}
