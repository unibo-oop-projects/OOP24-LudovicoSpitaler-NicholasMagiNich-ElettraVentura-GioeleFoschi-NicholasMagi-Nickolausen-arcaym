package arcaym.view.components;

import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import arcaym.view.core.ParentComponent;
import arcaym.view.scaling.WindowInfo;

/**
 * Blank panel that horizontally centers it's content without stretching it.
 */
public class HorizontalCenteredPanel implements ParentComponent<JPanel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window, final Optional<JComponent> childComponent) {
        final var panel = new JPanel();
        childComponent.ifPresent(c -> {
            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
            panel.add(Box.createHorizontalGlue());
            panel.add(c);
            panel.add(Box.createHorizontalGlue());
        });
        return panel;
    }

}
