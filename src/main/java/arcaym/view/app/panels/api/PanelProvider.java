package arcaym.view.app.panels.api;

import java.util.function.Consumer;
import java.util.function.Supplier;

import arcaym.view.app.panels.impl.SwitchablePanel;

@FunctionalInterface
public interface PanelProvider {

    Supplier<SwitchablePanel> create(Consumer<Supplier<SwitchablePanel>> switcher);

}
