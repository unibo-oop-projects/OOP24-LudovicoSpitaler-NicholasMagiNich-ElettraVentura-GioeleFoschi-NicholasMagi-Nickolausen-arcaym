package arcaym.view.app.panels;

import java.util.Objects;

import javax.swing.JPanel;

import arcaym.view.api.ViewComponent;

/**
 * Abstract implementation of a {@link PanelsSwitcher} panel.
 */
public abstract class SwitchablePanel implements ViewComponent<JPanel> {

    private final Switcher switcher;

    /**
     * Initialize switchable panel with a switcher.
     * 
     * @param switcher
     */
    public SwitchablePanel(final Switcher switcher) {
        this.switcher = Objects.requireNonNull(switcher);
    }

    /**
     * Get parent panel switcher.
     * 
     * @return switcher
     */
    protected Switcher switcher() {
        return this.switcher;
    }

    /**
     * Perform closing operations.
     */
    public void close() { }

}
