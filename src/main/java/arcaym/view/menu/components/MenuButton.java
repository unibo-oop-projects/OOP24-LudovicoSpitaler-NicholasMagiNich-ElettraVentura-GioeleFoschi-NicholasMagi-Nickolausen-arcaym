package arcaym.view.menu.components;

import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JButton;

import arcaym.view.core.ViewComponent;
import arcaym.view.menu.MenuViewImpl;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for a {@link MenuViewImpl} button.
 */
public class MenuButton implements ViewComponent<JButton> {

    private static final float FONT_SCALE = 2.0f;
    private static final float BUTTON_WIDTH_FACTOR = 0.15f;

    private final String text;

    /**
     * Initialize button with text.
     * 
     * @param text button text
     */
    public MenuButton(final String text) {
        this.text = Objects.requireNonNull(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton build(final WindowInfo window) {
        final var button = new JButton(this.text);
        SwingUtils.changeFontSize(button, FONT_SCALE);
        final var initialSize = button.getPreferredSize();
        button.setPreferredSize(new Dimension(
            Math.max(Math.round(window.size().width * BUTTON_WIDTH_FACTOR), initialSize.width),
            initialSize.height
        ));
        return button;
    }

}
