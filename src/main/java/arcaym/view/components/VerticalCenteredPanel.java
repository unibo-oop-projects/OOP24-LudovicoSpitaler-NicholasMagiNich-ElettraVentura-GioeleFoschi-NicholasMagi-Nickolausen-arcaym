package arcaym.view.components;

import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import arcaym.view.core.api.ParentComponent;
import arcaym.view.core.api.WindowInfo;

/**
 * Blank panel that vertically centers it's content without stretching it.
 */
public class VerticalCenteredPanel implements ParentComponent<JPanel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window, final Optional<JComponent> childComponent) {
        final var panel = new JPanel();
        childComponent.ifPresent(c -> {
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.add(Box.createVerticalGlue());
            panel.add(c);
            panel.add(Box.createVerticalGlue());
        });
        return panel;
    }

}
