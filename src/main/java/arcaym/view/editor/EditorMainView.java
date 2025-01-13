package arcaym.view.editor;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import arcaym.view.editor.components.impl.EditorGridView;
import arcaym.view.editor.components.impl.SideMenuView;

/**
 * The editor complete page.
 */
public class EditorMainView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int COLUMNS = 3;
    private static final int TEST_WIDTH = 1000;
    private static final int TEST_HEIGHT = 500;

    private final EditorGridView grid;
    private final SideMenuView sideMenu;
    private final JSplitPane splitPane;

    /**
     * The constructor for the page.
     */
    public EditorMainView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(TEST_WIDTH, TEST_HEIGHT));

        grid = new EditorGridView();
        sideMenu = new SideMenuView();
        sideMenu.setMinimumSize(new Dimension(Math.floorDiv(this.getWidth(), COLUMNS), this.getHeight()));
        grid.setMinimumSize(new Dimension(Math.floorDiv(this.getWidth(), COLUMNS) * 2, this.getHeight()));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(sideMenu);
        splitPane.setRightComponent(grid);
        splitPane.setDividerLocation(Math.floorDiv(this.getWidth(), COLUMNS));
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        this.add(splitPane);
        this.setVisible(true);
    }

    /**
     * Debug-only main!
     * @param args ignored
     */
    public static void main(final String... args) {
        new EditorMainView();
    }
}
