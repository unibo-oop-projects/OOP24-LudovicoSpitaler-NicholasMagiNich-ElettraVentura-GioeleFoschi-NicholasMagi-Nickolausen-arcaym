package arcaym.view.shop.components;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.components.HorizontalCenteredPanel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.objects.GameObjectView;
import arcaym.view.utils.SwingUtils;

/**
 * View of products with their description.
 */
public class DisplayProductView implements ViewComponent<JPanel> {
    private static final Color TITLE_COLOR = Color.WHITE;
    private final GameObjectType productType;
    // private final int price;
    // private final Predicate<Integer> canBuy;

    /**
     * Contructor of single ProductView.
     * 
     * @param info
     */
    public DisplayProductView(final ProductInfo info) {
        this.productType = info.type();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final JPanel contenPanel = new JPanel();
        contenPanel.setLayout(new BoxLayout(contenPanel, BoxLayout.PAGE_AXIS));
        final JPanel productName = new HorizontalCenteredPanel().build(window, new JLabel(productType.name()));
        productName.setBackground(TITLE_COLOR);
        contenPanel.add(productName);
        final JLabel productImgae = new GameObjectView(productType, 4).build(window);
        final JPanel productImagePanel = new HorizontalCenteredPanel().build(window, productImgae);
        contenPanel.add(Box.createVerticalStrut(SwingUtils.getBigGap(contenPanel)));
        contenPanel.add(productImagePanel);
        contenPanel.add(Box.createVerticalStrut(SwingUtils.getBigGap(contenPanel)));
        // JButton priceButton = new JButton(String.valueOf(price));
        // contenPanel.add(new CenteredPanel().build(window, priceButton));
        // priceButton.setEnabled(canBuy.apply(price) ? true : false);
        return new HorizontalCenteredPanel().build(window, contenPanel);
    }

}
