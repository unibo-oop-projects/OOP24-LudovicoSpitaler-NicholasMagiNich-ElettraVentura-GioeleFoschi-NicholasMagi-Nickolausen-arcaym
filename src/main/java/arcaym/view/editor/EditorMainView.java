package arcaym.view.editor;

import java.awt.Dimension;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.api.ViewComponent;
import arcaym.view.editor.components.GridAreaView;
import arcaym.view.editor.components.SideMenuView;
import arcaym.view.objects.GameObjectSwingView;

/**
 * The editor complete page.
 */
public class EditorMainView implements ViewComponent<JPanel> {

    private static final int COLUMNS = 5;
    private JPanel grid;
    private JScrollPane sideMenu;
    private JSplitPane splitPane;
    private JPanel out = new JPanel();
    private final Map<GameObjectCategory, Set<GameObjectSwingView>> gameObjects;

    /**
     * The constructor for the page.
     */
    public EditorMainView(final Map<GameObjectCategory, Set<GameObjectSwingView>> gameObjects) {
        this.gameObjects = Collections.unmodifiableMap(Objects.requireNonNull(gameObjects));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        grid = new GridAreaView().build();
        sideMenu = new SideMenuView(gameObjects).build();
        final Dimension sideMenuDimension = new Dimension(Math.floorDiv(out.getWidth(), COLUMNS), out.getHeight());
        sideMenu.setSize(sideMenuDimension);
        sideMenu.setPreferredSize(sideMenuDimension);
        sideMenu.setMinimumSize(sideMenuDimension);
        final Dimension gridDimension = calculateGridDimension();
        grid.setSize(gridDimension);
        grid.setPreferredSize(gridDimension);
        grid.setMinimumSize(gridDimension);
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
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

    /**
     * The space of the screen is organized as follows:
     * 1. The screen is horizontally divided into COLUMNS columns;
     * 2. The side menu always take 1 of the COLUMNS columns;
     * 3. The grid must take the remaining columns COLUMNS (which are 1 - COLUMNS)
     * 
     * @return The dimension of the grid that fills the remaining space 
     */
    private Dimension calculateGridDimension() {
        return new Dimension(Math.floorDiv(out.getWidth(), COLUMNS) * (COLUMNS - 1), out.getHeight());
    }
}
