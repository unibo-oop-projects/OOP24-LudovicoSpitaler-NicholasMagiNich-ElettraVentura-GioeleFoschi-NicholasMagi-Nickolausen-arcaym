package arcaym.view.shop;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.*;

import arcaym.controller.shop.api.ShopController;
import arcaym.controller.shop.impl.ShopControllerImpl;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View of shop.
 */
public class ShopView implements ViewComponent<JPanel> {
    private static final Integer SCALE = 3;
    private static final String SHOP_TITLE = "SHOP";
    private final ShopController controller = new ShopControllerImpl();
    private final Map<JButton, ProductInfo> productsMap = new HashMap<>();
    private Optional<ProductInfo> selected = Optional.empty();
    private JButton buyButton;

    Map<GameObjectType, Integer> mockController = new HashMap<>(); // mock

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        mockController.put(GameObjectType.COIN, 100);
        mockController.put(GameObjectType.MOVING_X_OBSTACLE, 50);
        mockController.put(GameObjectType.MOVING_Y_OBSTACLE, 50);
        mockController.put(GameObjectType.FLOOR, 50);

        final JPanel contentPanel = new JPanel();
        var gap = SwingUtils.getBigGap(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel(SHOP_TITLE);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        contentPanel.add(new CenteredPanel().build(window, title));

        JScrollPane scrollPanel = new JScrollPane();
        JPanel itemsPanel = new JPanel();
        itemsPanel.setMinimumSize(scrollPanel.getSize());
        itemsPanel.setLayout(new GridLayout(0, 1));
        scrollPanel.setViewportView(itemsPanel);
        fillItems(window, itemsPanel);

        contentPanel.add(Box.createVerticalStrut(gap));

        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPanel);

        contentPanel.add(Box.createVerticalStrut(gap));

        this.buyButton = new JButton("BUY");
        buyButton.setEnabled(false);

        ActionListener selectPriceAl = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg) {
                JButton pressedButton = (JButton) arg.getSource();
                if (selected.isEmpty()
                        || (selected.isPresent() && !selected.get().equals(productsMap.get(pressedButton)))) {
                    selected = Optional.of(productsMap.get(pressedButton));
                }
                if (true) { // controller.canbuy?
                    buyButton.setEnabled(true);
                }
                pressedButton.setBackground(Color.BLUE);
            }

        };

        ActionListener purchaseAl = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg) {
                if (selected.isPresent()) {
                    // controllerBUY();
                    buyButton.setEnabled(false);
                    selected = Optional.empty();
                    setAvailableButtons();
                }
            }

        };

        buyButton.addActionListener(purchaseAl);
        SwingUtils.changeFontSize(buyButton, SCALE);
        contentPanel.add(new CenteredPanel().build(window, buyButton));

        contentPanel.add(Box.createVerticalStrut(gap));

        JButton backToMenuButton = new JButton("Back to menu");
        contentPanel.add(new CenteredPanel().build(window, backToMenuButton));
        contentPanel.add(Box.createVerticalStrut(gap));

        contentPanel.setVisible(true);
        return contentPanel;
    }

    private void fillItems(WindowInfo window, JPanel itemsPanel) {
        for (var category : GameObjectCategory.values()) {
            JPanel categoryPanel = createCategoryCard(window, category);
            itemsPanel.add(categoryPanel);
        }
        setAvailableButtons();
    }

    private JPanel createCategoryCard(WindowInfo window, GameObjectCategory category) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));

        JLabel titleLabel = new JLabel("NEW " + category.name() + "S");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel title = new CenteredPanel().build(window, titleLabel);
        title.setBackground(Color.WHITE);
        card.add(title);

        JPanel showItemsPanel = new JPanel();
        showItemsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // final int maxItems = (int)
        // controller.getLockedGameObjects().keySet().stream()
        // .filter(type -> type.category() == category).count();
        // if (maxItems == 0) {
        // showItemsPanel.setBackground(Color.GRAY);
        // showItemsPanel.add(new JLabel("No Items Available."));
        // } else {
        // for (var object : controller.getLockedGameObjects().entrySet()) {
        // JPanel item = new DisplayProductView(object.getKey(),
        // object.getValue()).build(window);
        // item.setPreferredSize(SwingUtils.scaleDimension(item.getPreferredSize(),
        // SCALE));
        // showItemsPanel.add(item);
        // }
        // }

        final int maxItems = (int) mockController.keySet().stream()
                .filter(type -> type.category().equals(category)).count();
        if (maxItems == 0) {
            showItemsPanel.setBackground(Color.GRAY);
            showItemsPanel.add(new JLabel("No Items Available."));
        } else {
            for (var object : mockController.entrySet().stream()
                    .filter(type -> type.getKey().category().equals(category)).toList()) {
                JPanel item = new DisplayProductView(new ProductInfo(object.getKey(), object.getValue())).build(window);
                item.setLayout(new BoxLayout(item, BoxLayout.PAGE_AXIS));
                JButton price = new JButton(String.valueOf(object.getValue()));
                productsMap.put(price, new ProductInfo(object.getKey(), object.getValue()));
                price.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg) {
                        JButton pressedButton = (JButton) arg.getSource();
                        if (selected.isEmpty()
                                || (selected.isPresent() && !selected.get().equals(productsMap.get(pressedButton)))) {
                            setAvailableButtons();
                            selected = Optional.of(productsMap.get(pressedButton));
                            pressedButton.setBackground(Color.PINK);
                        } else if (selected.isPresent() && selected.get().equals(productsMap.get(pressedButton))) {
                            selected = Optional.empty();
                            pressedButton.setBackground(Color.WHITE);
                        }
                        regulateBuyOption();
                    }

                });
                item.add(new CenteredPanel().build(window, price));
                showItemsPanel.add(item);
            }
        }

        card.add(showItemsPanel);
        return card;
    }

    protected void regulateBuyOption() {
        if (selected.isPresent()) {
            buyButton.setEnabled(true);
        } else {
            buyButton.setEnabled(false);
        }
    }

    protected void setAvailableButtons() {
        productsMap.entrySet().forEach(product -> {
            product.getKey().setEnabled(true);
            product.getKey().setBackground(Color.WHITE);
        });
        // .setEnabled(controller.requestTransaction(product.getValue().type()) ? true :
        // false));
    }

    public static void main(String[] args) {
        SwingUtils.testComponent(new ShopView()::build);
    }
}
