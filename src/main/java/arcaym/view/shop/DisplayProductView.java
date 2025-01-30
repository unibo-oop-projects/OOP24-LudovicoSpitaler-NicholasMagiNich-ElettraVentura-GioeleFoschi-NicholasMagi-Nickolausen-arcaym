package arcaym.view.shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

import com.google.common.base.Predicate;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.app.impl.MainViewImpl;
import arcaym.view.components.CenteredPanel;
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
    //private final int price;
    //private final Predicate<Integer> canBuy;

    /**
     * Contructor of single ProductView.
     * @param info
     */
    public DisplayProductView(ProductInfo info) {
        this.productType = info.type();
    }

    @Override
    public JPanel build(final WindowInfo window) {
        JPanel contenPanel = new JPanel();
        contenPanel.setLayout(new BoxLayout(contenPanel,BoxLayout.PAGE_AXIS));
        var productName = new CenteredPanel().build(window, new JLabel(productType.name()));
        productName.setBackground(TITLE_COLOR);
        contenPanel.add(productName);
        JLabel productImgae = new GameObjectView(productType,4).build(window);
        JPanel productImagePanel = new CenteredPanel().build(window, productImgae);
        contenPanel.add(Box.createVerticalStrut(SwingUtils.getBigGap(contenPanel)));
        contenPanel.add(productImagePanel);
        contenPanel.add(Box.createVerticalStrut(SwingUtils.getBigGap(contenPanel)));
        // JButton priceButton = new JButton(String.valueOf(price));
        // contenPanel.add(new CenteredPanel().build(window, priceButton));
        // priceButton.setEnabled(canBuy.apply(price) ? true : false);
        return new CenteredPanel().build(window, contenPanel);
    }

    public static void main(String[] args) {
        SwingUtils.testComponent(window -> new DisplayProductView(new ProductInfo(GameObjectType.FLOOR, 10)).build(window));
    }
}
