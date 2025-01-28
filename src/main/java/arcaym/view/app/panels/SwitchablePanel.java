package arcaym.view.app.panels;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JPanel;

import arcaym.view.api.ViewComponent;

public abstract class SwitchablePanel implements ViewComponent<JPanel> {

    private final Consumer<Supplier<SwitchablePanel>> switcher;

    public SwitchablePanel(final Consumer<Supplier<SwitchablePanel>> switcher) {
        this.switcher = Objects.requireNonNull(switcher);
    }

    protected Consumer<Supplier<SwitchablePanel>> switcher() {
        return this.switcher;
    }

    public void onClose() {
        System.out.println("Closing " +  this.getClass().getSimpleName());
        return;
    }

}
