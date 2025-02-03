package arcaym.view.editor.impl;

import java.awt.BorderLayout;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arcaym.controller.editor.api.EditorController;
import arcaym.controller.editor.impl.EditorControllerInfoImpl;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.editor.impl.components.GridAreaView;
import arcaym.view.editor.impl.components.SideMenuView;
import arcaym.view.utils.SwingUtils;

/**
 * The editor complete page.
 */
public class EditorMainView implements ViewComponent<JPanel> {

    private final EditorController controller = new EditorControllerInfoImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final JPanel out = new JPanel();
        out.setLayout(new BorderLayout());
        final JPanel grid = new GridAreaView().build(window);
        final JScrollPane sideMenu = new SideMenuView(controller.getOwnedObjects())
            .build(window);
        final var sideMenuGap = SwingUtils.getNormalGap(sideMenu);
        sideMenu.setBorder(BorderFactory.createEmptyBorder(
            sideMenuGap,
            sideMenuGap,
            sideMenuGap,
            sideMenuGap));
        out.add(sideMenu, BorderLayout.WEST);
        out.add(grid, BorderLayout.CENTER);
        return out;
    }

    public static void main(String[] args) {
        SwingUtils.testComponent((win) -> 
            new EditorMainView().build(win)
        );
    }
}
