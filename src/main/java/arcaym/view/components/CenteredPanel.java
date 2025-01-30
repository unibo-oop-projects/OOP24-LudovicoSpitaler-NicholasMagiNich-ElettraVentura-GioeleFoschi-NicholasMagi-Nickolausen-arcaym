package arcaym.view.components;

import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import arcaym.view.core.api.ParentComponent;
import arcaym.view.core.api.ScreenInfo;

/**
 * Blank panel that centers it's content without stretching it.
 */
public class CenteredPanel implements ParentComponent<JPanel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final ScreenInfo screenInfo, final Optional<JComponent> childComponent) {
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
