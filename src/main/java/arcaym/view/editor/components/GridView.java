package arcaym.view.editor.components;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JPanel;

import arcaym.common.utils.Position;
import arcaym.view.api.ViewComponent;
/**
 * An implementation of the grid view.
 */
public class GridView implements ViewComponent<JPanel> {

    private static final int COLUMNS = 96;
    private static final int ROWS = 54;

    private final Consumer<Collection<Position>> reciver;

    /**
     * The constructor of the component.
     * @param sendObjects the function that needs to process the list of objects
     */
    public GridView(final Consumer<Collection<Position>> sendObjects) {
        this.reciver = sendObjects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        return buildGrid();
    }

    private JPanel buildGrid() {
        final var grid = new JPanel(new GridLayout(ROWS, COLUMNS));
        final Map<JPanel, Position> cells = new HashMap<>();
        final List<Position> positionInvolved = new ArrayList<>();

        final MouseListener m = new MouseListener() {

            private boolean isClicking = false;

            @Override
            public void mouseClicked(final MouseEvent e) {
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                isClicking = true;
                final var panel = (JPanel) e.getSource();
                if (cells.containsKey(panel)) {
                    positionInvolved.add(cells.get(panel));
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                isClicking = false;
                reciver.accept(positionInvolved);
                positionInvolved.clear();
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
                final var panel = (JPanel) e.getSource();
                if (cells.containsKey(panel)) {
                    if (!positionInvolved.contains(cells.get(panel)) && isClicking) {
                        positionInvolved.add(cells.get(panel));
                    }
                }
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }
        };

        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                var pos = Position.of(i, j);
                final JPanel jp = new JPanel();
                cells.put(jp, pos);
                jp.addMouseListener(m);
                grid.add(jp);
            }
        }

        return grid;
    }
}
