package arcaym.view.editor;

import javax.swing.*;

import arcaym.view.editor.components.EditorGridView;
import arcaym.view.editor.components.SideMenuView;

import java.awt.*;

public class EditorMainView extends JFrame {

    JPanel leftSideMenu = new SideMenuView();
    Component rightSideGrid = new EditorGridView();
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSideMenu, rightSideGrid);;
    
    public EditorMainView() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);        
        this.setSize(500, 500);
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);
        this.getContentPane().add(panel);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        // CENTRE-BOTTOM ROW
        this.setVisible(true);
    }

    public static void main(final String ...args) {
        new EditorMainView();
    }
}
