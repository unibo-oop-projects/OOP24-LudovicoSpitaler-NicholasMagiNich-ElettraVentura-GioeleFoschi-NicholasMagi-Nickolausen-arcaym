package arcaym.view.shop;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
    private static final String BUY_BUTTON = "PURCHASE";
    private static final String BACK_TO_MAIN_PAGE = "BACK TO MENU";
    // private final ShopController controller = new ShopControllerImpl();
    private final Map<JButton, ProductInfo> productsMap = new HashMap<>();
    private Optional<ProductInfo> selected = Optional.empty();
    private JButton buyButton;
    private int counterTHISISAMOCKTODELETE = 10;

    private final Map<GameObjectType, Integer> mockController = new EnumMap<>(GameObjectType.class); // mock

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        mockController.put(GameObjectType.COIN, SCALE);
        mockController.put(GameObjectType.MOVING_X_OBSTACLE, SCALE);
        mockController.put(GameObjectType.MOVING_Y_OBSTACLE, SCALE);
        mockController.put(GameObjectType.FLOOR, SCALE); //

        final JPanel contentPanel = new JPanel();
        final var gap = SwingUtils.getBigGap(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        final JLabel title = new JLabel(SHOP_TITLE);
        SwingUtils.changeFontSize(title, SCALE);
        contentPanel.add(new CenteredPanel().build(window, title));

        final JLabel userCredit = new JLabel("Credit: " + String.valueOf(counterTHISISAMOCKTODELETE));
        contentPanel.add(new CenteredPanel().build(window, userCredit));

        final JScrollPane scrollPanel = new JScrollPane();
        final JPanel itemsPanel = new JPanel();
        itemsPanel.setMinimumSize(scrollPanel.getSize());
        itemsPanel.setLayout(new GridLayout(0, 1));
        scrollPanel.setViewportView(itemsPanel);
        fillItems(window, itemsPanel);

        contentPanel.add(Box.createVerticalStrut(gap));

        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPanel);

        contentPanel.add(Box.createVerticalStrut(gap));

        this.buyButton = new JButton(BUY_BUTTON);
        buyButton.setEnabled(false);

        final ActionListener purchaseAl = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg) {
                if (selected.isPresent()) {
                    // controllerBUY();
                    counterTHISISAMOCKTODELETE--;
                    userCredit.setText("Credit: " + String.valueOf(counterTHISISAMOCKTODELETE));
                    //
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

        final JButton backToMenuButton = new JButton(BACK_TO_MAIN_PAGE);
        contentPanel.add(new CenteredPanel().build(window, backToMenuButton));
        contentPanel.add(Box.createVerticalStrut(gap));

        contentPanel.setVisible(true);
        return contentPanel;
    }

    private void fillItems(final WindowInfo window, final JPanel itemsPanel) {
        for (final var category : GameObjectCategory.values()) {
            final JPanel categoryPanel = createCategoryCard(window, category);
            itemsPanel.add(categoryPanel);
        }
        setAvailableButtons();
    }

    private JPanel createCategoryCard(final WindowInfo window, final GameObjectCategory category) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));

        final JLabel titleLabel = new JLabel("NEW " + category.name() + "S");
        SwingUtils.changeFontSize(titleLabel, SCALE);
        final JPanel title = new CenteredPanel().build(window, titleLabel);
        title.setBackground(Color.WHITE);
        card.add(title);

        final JPanel showItemsPanel = new JPanel();
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
            for (final var object : mockController.entrySet().stream()
                    .filter(type -> type.getKey().category().equals(category)).toList()) {
                final JPanel item = new DisplayProductView(new ProductInfo(object.getKey(), object.getValue()))
                        .build(window);
                item.setLayout(new BoxLayout(item, BoxLayout.PAGE_AXIS));
                final JButton price = new JButton(String.valueOf(object.getValue()));
                productsMap.put(price, new ProductInfo(object.getKey(), object.getValue()));
                price.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent arg) {
                        final JButton pressedButton = (JButton) arg.getSource();
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

    private void regulateBuyOption() {
        if (selected.isPresent()) {
            buyButton.setEnabled(true);
        } else {
            buyButton.setEnabled(false);
        }
    }

    private void setAvailableButtons() {
        productsMap.entrySet().forEach(product -> {
            product.getKey().setEnabled(true);
            product.getKey().setBackground(Color.WHITE);
        });
        // .setEnabled(controller.requestTransaction(product.getValue().type()) ? true :
        // false));
    }

    /**
     * Debug only.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        SwingUtils.testComponent(new ShopView()::build);
    }
}
