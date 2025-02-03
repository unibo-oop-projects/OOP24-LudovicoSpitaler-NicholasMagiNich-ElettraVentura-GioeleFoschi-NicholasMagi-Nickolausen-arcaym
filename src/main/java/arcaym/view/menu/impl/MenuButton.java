package arcaym.view.menu.impl;

import java.util.Objects;

import javax.swing.JButton;

import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for a {@link MainMenu} button.
 */
public class MenuButton implements ViewComponent<JButton> {

    private static final float FONT_SCALE = 2.0f;

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
        return button;
    }

}
