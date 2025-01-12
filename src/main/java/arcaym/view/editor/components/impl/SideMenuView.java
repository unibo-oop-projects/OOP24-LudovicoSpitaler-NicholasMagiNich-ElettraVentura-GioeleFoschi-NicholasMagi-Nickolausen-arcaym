package arcaym.view.editor.components.impl;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;

import arcaym.view.editor.components.api.SideMenu;

/**
 * An implementation of the side menu component in swing.
 */
public class SideMenuView extends Container implements SideMenu {

    private static final long serialVersionUID = 1L;

    /**
     * A constructor of the component.
     */
    public SideMenuView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLUE);
    }
}
