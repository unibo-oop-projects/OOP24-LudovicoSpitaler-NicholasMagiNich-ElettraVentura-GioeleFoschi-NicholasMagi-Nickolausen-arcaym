package arcaym.view.editor.components.impl;

// import java.awt.Color;
import java.awt.GridLayout;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arcaym.view.editor.components.api.SideMenu;
import arcaym.view.objects.CoinView;
//import arcaym.view.objects.GameObjectSwingView;
import arcaym.view.objects.StaticObstacleView;
//import arcaym.controller.game.core.objects.api.GameObjectCategory;

/**
 * An implementation of the side menu component in swing.
 */
public class SideMenuView extends JScrollPane implements SideMenu {

    private static final long serialVersionUID = 1L;
    private final JPanel content;
    private static final int NR_OBJECTS = 40;
    //private final Map<GameObjectCategory, Set<GameObjectSwingView>> objects = new HashMap<>();

    /**
     * A constructor of the component.
     */
    public SideMenuView() {
        content = new JPanel();
        content.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < NR_OBJECTS; i++) {
            content.add(i % 2 == 0 ? new CoinView() : new StaticObstacleView());
        }
        this.setViewportView(content);
        this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }
}
