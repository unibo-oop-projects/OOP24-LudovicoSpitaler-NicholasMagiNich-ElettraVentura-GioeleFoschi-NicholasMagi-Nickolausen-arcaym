package arcaym.view.components;

import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JPanel;

import arcaym.view.core.api.ParentComponent;
import arcaym.view.core.api.WindowInfo;

/**
 * Blank panel that centers it's content without stretching it.
 */
public class CenteredPanel implements ParentComponent<JPanel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window, final Optional<JComponent> childComponent) {
        return new HorizontalCenteredPanel().build(window, new VerticalCenteredPanel().build(window, childComponent));
    }

}
