package arcaym.view.app.menu.levels;

import javax.swing.JPanel;

import arcaym.view.app.panels.SwitchablePanel;
import arcaym.view.app.panels.Switcher;
import arcaym.view.components.CenteredPanel;

public class LevelsPanel extends SwitchablePanel {

    public LevelsPanel(final Switcher switcher) {
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
