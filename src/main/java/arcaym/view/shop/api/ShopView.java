package arcaym.view.shop.api;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arcaym.controller.shop.api.ShopController;
import arcaym.controller.shop.impl.ShopControllerImpl;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.api.ViewComponent;
import arcaym.view.components.CenteredPanel;
import arcaym.view.utils.SwingUtils;

public class ShopView implements ViewComponent<JPanel> {
    private static final Integer SCALE = 5;
    private static final String SHOP_TITLE = "SHOP";
    private final ShopController controller = new ShopControllerImpl();

    @Override
    public JPanel build() {
        final JPanel contentPanel = new JPanel();
        var gap = SwingUtils.getBigGap(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel(SHOP_TITLE);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        contentPanel.add(new CenteredPanel().build(title));

        JScrollPane scrollPanel = new JScrollPane();
        JPanel itemsPanel = new JPanel();
        itemsPanel.setMinimumSize(scrollPanel.getSize());
        itemsPanel.setLayout(new GridLayout(1, 0));
        scrollPanel.setViewportView(itemsPanel);
        fillItems(itemsPanel);

        contentPanel.add(Box.createVerticalStrut(gap));

        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPanel.add(scrollPanel);

        contentPanel.add(Box.createVerticalStrut(gap));

        JButton buyButton = new JButton("BUY");
        SwingUtils.changeFontSize(buyButton, SCALE);
        contentPanel.add(new CenteredPanel().build(buyButton));

        contentPanel.add(Box.createVerticalStrut(gap));

        JButton backToMenuButton = new JButton("Back to menu");
        contentPanel.add(new CenteredPanel().build(backToMenuButton));
        contentPanel.add(Box.createVerticalStrut(gap));

        contentPanel.setVisible(true);
        return contentPanel;
    }

    private void fillItems(JPanel itemsPanel) {
        for (var category : GameObjectCategory.values()) {
            JPanel categoryPanel = createCategoryCard(category);
            itemsPanel.add(categoryPanel);
        }
    }

    private JPanel createCategoryCard(GameObjectCategory category) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.LINE_AXIS));
        card.add(Box.createHorizontalStrut(SwingUtils.getLittleGap(card)));
        var titleCard = new JLabel(category.name());
        titleCard.setFont(new Font("Arial", Font.BOLD, 20));
        card.add(titleCard);
        card.add(Box.createVerticalStrut(SwingUtils.getBigGap(card)));
        JPanel types = new JPanel();
        types.setLayout(new GridLayout(1,0));
        // controller.getLockedGameObjects().keySet().stream().filter(obj->obj.category().equals(category)).forEach(obj->card.add(new
        // JButton(obj.name())));
        Stream.iterate(0, i -> i + 1).limit(5).forEach(obj -> {
            types.add(Box.createHorizontalStrut(SwingUtils.getLittleGap(card)));
            createPurchaseOption(obj, types);
            types.add(Box.createHorizontalStrut(SwingUtils.getLittleGap(card)));
        });
        card.add(types);
        return card;
    }

    private void createPurchaseOption(Integer obj, JPanel types) {
        JPanel type = new JPanel();
        type.setLayout(new BoxLayout(type, BoxLayout.PAGE_AXIS));
        
        type.add(Box.createVerticalStrut(SwingUtils.getBigGap(type)));
        type.add(new CenteredPanel().build(new JLabel("Type Name")));
        type.add(Box.createVerticalStrut(SwingUtils.getBigGap(type)));
        JButton selectType = new JButton(":)");
        SwingUtils.changeFontSize(selectType, SCALE+SCALE);
        type.add(new CenteredPanel().build(selectType));
        
        type.add(Box.createVerticalStrut(SwingUtils.getBigGap(type)));
        type.add(new CenteredPanel().build(new JLabel("100$")));
        types.add(type);
    }

}
