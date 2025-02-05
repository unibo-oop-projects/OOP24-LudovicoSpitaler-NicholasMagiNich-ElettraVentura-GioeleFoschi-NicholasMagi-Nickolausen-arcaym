package arcaym.view.shop;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arcaym.controller.shop.ShopController;
import arcaym.model.game.core.objects.GameObjectCategory;
import arcaym.model.game.objects.GameObjectType;
import arcaym.view.app.AbstractView;
import arcaym.view.components.HorizontalCenteredPanel;
import arcaym.view.core.ViewComponent;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View of the shop.
 */
public class ShopViewImpl extends AbstractView<ShopController> implements ViewComponent<JPanel>, ShopView {
    private static final Integer SCALE = 3;
    private static final String SHOP_TITLE = "SHOP";
    private static final String BUY_BUTTON = "PURCHASE";
    private static final String CREDIT = "Credit : ";
    private static final String CATEGORY = "CATEGORY : ";
    private static final String NOT_AVAILABLE = "No Items Available.";
    private final BiMap<JButton, ProductInfo> productsMap = HashBiMap.create();
    private Optional<ProductInfo> selected = Optional.empty();
    private JButton buyButton;

    /**
     * Basic constructor for shop view.
     * 
     * @param controller shop controller
     */
    public ShopViewImpl(final ShopController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final JPanel contentPanel = new JPanel();
        final var gap = SwingUtils.getBigGap(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        final JLabel title = new JLabel(SHOP_TITLE);
        SwingUtils.changeFontSize(title, SCALE);
        contentPanel.add(new HorizontalCenteredPanel().build(window, title));
        final JLabel userCredit = new JLabel();
        updateCreditLable(userCredit);
        contentPanel.add(new HorizontalCenteredPanel().build(window, userCredit));
        final JScrollPane scrollPanel = new JScrollPane();
        final JPanel itemsPanel = new JPanel();
        itemsPanel.setMinimumSize(scrollPanel.getSize());
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.PAGE_AXIS));
        scrollPanel.setViewportView(itemsPanel);
        fillItems(window, itemsPanel);
        contentPanel.add(Box.createVerticalStrut(gap));
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPanel);
        contentPanel.add(Box.createVerticalStrut(gap));
        this.buyButton = new JButton(BUY_BUTTON);
        buyButton.setEnabled(false);
        buyButton.addActionListener(a -> {
            if (selected.isPresent() && controller().requestTransaction(selected.get().type())) {
                updateCreditLable(userCredit);
                buyButton.setEnabled(false);
                productsMap.inverse().get(selected.get()).setBackground(Color.WHITE);
                productsMap.inverse().get(selected.get()).setEnabled(false);
                selected = Optional.empty();
                setAvailableButtons();
            }
        });
        SwingUtils.changeFontSize(buyButton, SCALE);
        contentPanel.add(new HorizontalCenteredPanel().build(window, buyButton));

        contentPanel.add(Box.createVerticalStrut(gap));

        contentPanel.setVisible(true);
        return contentPanel;
    }

    private void updateCreditLable(final JLabel userCredit) {
        userCredit.setText(CREDIT + controller().getUserCredit());
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
        final JLabel titleLabel = new JLabel(CATEGORY + category.name());
        SwingUtils.changeFontSize(titleLabel, SCALE);
        final JPanel title = new HorizontalCenteredPanel().build(window, titleLabel);
        title.setBackground(Color.WHITE);
        card.add(title);
        final JPanel showItemsPanel = new JPanel();
        showItemsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        final int maxAvailableItems = (int) controller().getLockedItems().keySet().stream()
                .filter(type -> type.category() == category).count();
        final int maxAlreadyOwnedItems = (int) controller().getPurchasedItems().keySet().stream()
                .filter(type -> type.category() == category).count();
        if (maxAvailableItems == 0 && maxAlreadyOwnedItems == 0) {
            showItemsPanel.setBackground(Color.LIGHT_GRAY);
            showItemsPanel.add(new JLabel(NOT_AVAILABLE));
        } else {
            if (maxAvailableItems != 0) {
                for (final var object : filterForCategory(controller().getLockedItems().entrySet().stream(),
                        category)) {
                    final JPanel item = new DisplayProductView(new ProductInfo(object.getKey(), object.getValue()))
                            .build(window);
                    item.setLayout(new BoxLayout(item, BoxLayout.PAGE_AXIS));
                    final JButton price = new JButton(String.valueOf(object.getValue()));
                    productsMap.put(price, new ProductInfo(object.getKey(), object.getValue()));
                    price.addActionListener(a -> {
                            final JButton pressedButton = (JButton) a.getSource();
                            if (selected.isEmpty()
                                    || (selected.isPresent()
                                            && !selected.get().equals(productsMap.get(pressedButton)))) {
                                setAvailableButtons();
                                selected = Optional.of(productsMap.get(pressedButton));
                                pressedButton.setBackground(Color.PINK);
                            } else if (selected.isPresent() && selected.get().equals(productsMap.get(pressedButton))) {
                                selected = Optional.empty();
                                pressedButton.setBackground(Color.WHITE);
                            }
                            regulateBuyOption();
                    });
                    item.add(new HorizontalCenteredPanel().build(window, price));
                    showItemsPanel.add(item);
                }
            }
            if (maxAlreadyOwnedItems != 0) {
                for (final var object : filterForCategory(controller().getPurchasedItems().entrySet().stream(),
                        category)) {
                    final JPanel item = new DisplayProductView(new ProductInfo(object.getKey(), object.getValue()))
                            .build(window);
                    item.setLayout(new BoxLayout(item, BoxLayout.PAGE_AXIS));
                    final JButton price = new JButton(String.valueOf(object.getValue()));
                    productsMap.put(price, new ProductInfo(object.getKey(), object.getValue()));
                    price.setEnabled(false);
                    item.add(new HorizontalCenteredPanel().build(window, price));
                    showItemsPanel.add(item);
                }

            }
        }
        card.add(showItemsPanel);
        return card;
    }

    private Collection<Entry<GameObjectType, Integer>> filterForCategory(
            final Stream<Entry<GameObjectType, Integer>> stream, final GameObjectCategory category) {
        return stream.filter(type -> type.getKey().category().equals(category)).toList();
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
            if (controller().canBuy(product.getValue().type())) {
                product.getKey().setEnabled(true);
                product.getKey().setBackground(Color.WHITE);
            } else if (!controller().canBuy(product.getValue().type())
                    && !controller().getPurchasedItems().containsKey(product.getValue().type())) {
                product.getKey().setEnabled(false);
                product.getKey().setBackground(Color.RED);
            }
        });
    }
}
