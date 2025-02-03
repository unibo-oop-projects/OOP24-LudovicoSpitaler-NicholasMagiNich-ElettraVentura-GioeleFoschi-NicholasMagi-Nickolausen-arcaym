package arcaym.view.app.impl;

import java.util.function.Supplier;

/**
 * Interface for a {@link PanelsSwitcher} switch function.
 */
@FunctionalInterface
public interface Switcher {

    /**
     * Switch to the panel given from the supplier.
     * 
     * @param panelSupplier panel supplier
     */
    void switchTo(Supplier<SwitchablePanel> panelSupplier);

}
