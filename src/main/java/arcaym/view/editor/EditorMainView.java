package arcaym.view.editor;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import arcaym.view.editor.components.GridAreaView;
import arcaym.view.core.api.ScreenInfo;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.editor.components.SideMenuView;

/**
 * The editor complete page.
 */
public class EditorMainView implements ViewComponent<JPanel> {

    private static final int COLUMNS = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final ScreenInfo screenInfo) {
        final JPanel out = new JPanel();
        final JPanel grid = new GridAreaView().build(screenInfo);
        final JScrollPane sideMenu = new SideMenuView().build(screenInfo);
        final Dimension sideMenuDimension = new Dimension(Math.floorDiv(out.getWidth(), COLUMNS), out.getHeight());
        sideMenu.setSize(sideMenuDimension);
        sideMenu.setPreferredSize(sideMenuDimension);
        sideMenu.setMinimumSize(sideMenuDimension);
        final Dimension gridDimension = calculateGridDimension(out.getSize());
        grid.setSize(gridDimension);
        grid.setPreferredSize(gridDimension);
        grid.setMinimumSize(gridDimension);

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setSize(out.getSize());
        splitPane.setLeftComponent(sideMenu);
        splitPane.setRightComponent(grid);
        splitPane.setDividerLocation(Math.floorDiv(out.getWidth(), COLUMNS));
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        out.add(splitPane);
        out.setVisible(true);
        return out;
    }

    private Dimension calculateGridDimension(final Dimension panelDimension) {
        return new Dimension(Math.floorDiv(
            (int) panelDimension.getWidth(), COLUMNS) * (COLUMNS - 1), 
            (int) panelDimension.getHeight()
        );
    }
}
