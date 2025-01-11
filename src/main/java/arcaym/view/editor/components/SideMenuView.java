package arcaym.view.editor.components;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SideMenuView extends JPanel {
    public SideMenuView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLUE);
    }
}
