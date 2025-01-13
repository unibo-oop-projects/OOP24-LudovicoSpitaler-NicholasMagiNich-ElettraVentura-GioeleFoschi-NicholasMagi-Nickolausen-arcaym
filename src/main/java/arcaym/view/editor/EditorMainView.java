package arcaym.view.editor;

import java.awt.Dimension;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.editor.components.api.GeneralSwingView;
import arcaym.view.editor.components.impl.EditorGridView;
import arcaym.view.editor.components.impl.SideMenuView;
import arcaym.view.objects.GameObjectSwingView;

/**
 * The editor complete page.
 */
public class EditorMainView extends JPanel implements GeneralSwingView {

    private static final long serialVersionUID = 1L;
    private static final int COLUMNS = 3;
    private EditorGridView grid;
    private SideMenuView sideMenu;
    private JSplitPane splitPane;
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
    public void initView() {
        grid = new EditorGridView();
        sideMenu = new SideMenuView(gameObjects);
        sideMenu.setSize(new Dimension(Math.floorDiv(this.getWidth(), COLUMNS), this.getHeight()));
        grid.setSize(new Dimension(Math.floorDiv(this.getWidth(), COLUMNS) * 2, this.getHeight()));

        sideMenu.initView();
        grid.initView();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setSize(this.getSize());
        splitPane.setLeftComponent(sideMenu);
        splitPane.setRightComponent(grid);
        splitPane.setDividerLocation(Math.floorDiv(this.getWidth(), COLUMNS));
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        this.add(splitPane);
        this.setVisible(true);
    }
}
