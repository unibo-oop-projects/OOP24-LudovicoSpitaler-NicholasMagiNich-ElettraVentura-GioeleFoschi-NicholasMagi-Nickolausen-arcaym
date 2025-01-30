package arcaym.view.shop.api;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.*;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.app.impl.MainViewImpl;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.objects.GameObjectView;
import arcaym.view.utils.SwingUtils;

public class DisplayProductView implements ViewComponent<JPanel> {
    private static final Color TITLE_COLOR = Color.WHITE;
    private final GameObjectType productType;
    private final int price;

    public DisplayProductView(ProductInfo info) {
        this.productType = info.type();
        this.price = info.price();
    }

    @Override
    public JPanel build(final WindowInfo window) {
        JPanel contenPanel = new JPanel();
        contenPanel.setLayout(new BoxLayout(contenPanel,BoxLayout.PAGE_AXIS));
        var productName = new CenteredPanel().build(window, new JLabel(productType.name()));
        productName.setBackground(TITLE_COLOR);
        contenPanel.add(productName);
        JPanel item = new GameObjectView(productType).build(window);
        //scale image please
        contenPanel.add(item);
        JButton priceButton = new JButton(String.valueOf(price));
        contenPanel.add(new CenteredPanel().build(window, priceButton));
        return new CenteredPanel().build(window, contenPanel);
    }

    public static void main(String[] args) {
        SwingUtils.testComponent(window -> new DisplayProductView(new ProductInfo(GameObjectType.COIN, 10)).build(window));
    }
}
