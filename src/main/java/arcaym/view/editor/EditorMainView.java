package arcaym.view.editor;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import arcaym.view.editor.components.api.AbstractGrid;
import arcaym.view.editor.components.api.AbstractSideMenu;
import arcaym.view.editor.components.impl.EditorGridView;
import arcaym.view.editor.components.impl.SideMenuView;

public class EditorMainView extends JPanel {

    AbstractSideMenu leftSideMenu = new SideMenuView();
    AbstractGrid rightSideGrid = new EditorGridView();
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSideMenu, rightSideGrid);
    
    public EditorMainView() {
        // this.setDefaultCloseOperation(EXIT_ON_CLOSE);        
        // this.setSize(500, 500);
        // final JPanel panel = new JPanel(new BorderLayout());
        // panel.add(splitPane, BorderLayout.CENTER);
        // this.getContentPane().add(panel);
        // splitPane.setContinuousLayout(true);
        // splitPane.setOneTouchExpandable(true);
        // CENTRE-BOTTOM ROW
        // this.setVisible(true);
    }
}
