package arcaym.view.editor.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arcaym.common.utils.Position;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.api.ViewComponent;
import arcaym.view.components.CenteredPanel;
import arcaym.view.utils.SwingUtils;
/**
 * An implementation of the grid view.
 */
public class GridView implements ViewComponent<JScrollPane> {

    private static final int CELL_SCALE = 50;

    private final int COLUMNS;
    private final int ROWS;
    private final Consumer<Collection<Position>> reciver;
    private final BiMap<JLayeredPane, Position> cells = HashBiMap.create();
    private final int cellDimension;

    /**
     * The constructor of the component.
     * 
     * @param sendObjects The function that needs to process the list of objects
     * @param size The size of the map
     */
    public GridView(final Consumer<Collection<Position>> sendObjects, final Position size) {
        this.reciver = sendObjects;
        this.cellDimension = SwingUtils.WINDOW_SIZE.width / CELL_SCALE;
        this.COLUMNS = size.x();
        this.ROWS = size.y();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JScrollPane build() {
        return buildGrid();
    }

    private JScrollPane buildGrid() {
        final var grid = new JPanel(new GridLayout(ROWS, COLUMNS));

        final MouseListener m = new MouseListener() {

            private final List<Position> positionInvolved = new ArrayList<>();
            private boolean isClicking;

            @Override
            public void mouseClicked(final MouseEvent e) {
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                isClicking = true;
                final var panel = (JLayeredPane) e.getSource();
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
                final var panel = (JLayeredPane) e.getSource();
                if (cells.containsKey(panel) && !positionInvolved.contains(cells.get(panel)) && isClicking) {
                    positionInvolved.add(cells.get(panel));
                }
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }
        };

        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                final JLayeredPane jp = new JLayeredPane();
                jp.setSize(new Dimension(cellDimension, cellDimension));
                jp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                jp.setBackground(Color.BLUE);
                cells.put(jp, Position.of(i, j));
                jp.addMouseListener(m);
                grid.add(jp);
            }
        }

        final var scrollPanel = new JScrollPane(new CenteredPanel().build(grid));
        return scrollPanel;
    }

    /**
     * Changes the grid cells view based on the given map.
     * @param positions Contains the list of objets to place in the cell in a specific position
     */
    public void setPositionFromMap(final Map<Position, List<GameObjectType>> positions) {
        // temporal method, to update with images
        positions.entrySet().forEach(e -> {
            // var color = Color.BLUE;
            // if (e.getValue().size() == 1) {
            //     if (e.getValue().getFirst().equals(GameObjectType.FLOOR)) {
            //         color = Color.LIGHT_GRAY;
            //     }
            // } else {
            //     color = Color.PINK;
            // }
            // cells.inverse().get(e.getKey()).setBackground(color);
            // cells.inverse().get(e.getKey()).add(new GameObjectView(null, null), COLUMNS);
        });
    }
}
