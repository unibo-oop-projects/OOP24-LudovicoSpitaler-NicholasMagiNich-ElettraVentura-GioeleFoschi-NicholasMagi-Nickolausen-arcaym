package arcaym.view.app.menu.levels;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JPanel;

import arcaym.view.app.panels.impl.SwitchablePanel;
import arcaym.view.components.CenteredPanel;

public class LevelsPanel extends SwitchablePanel {

    public LevelsPanel(final Consumer<Supplier<SwitchablePanel>> switcher) {
        super(switcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        return new CenteredPanel().build(new LevelsList());
    }
    
}
