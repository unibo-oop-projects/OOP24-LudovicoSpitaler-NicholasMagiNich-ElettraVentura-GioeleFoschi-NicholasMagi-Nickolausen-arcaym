package arcaym.view.editor.impl.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.Position;
import arcaym.model.game.objects.GameObjectType;
import arcaym.view.core.ViewComponent;
import arcaym.view.objects.GameObjectView;
import arcaym.view.scaling.WindowInfo;

/**
 * An implementation of the grid view.
 */
public class GridView implements ViewComponent<JScrollPane> {

    private static final int DEFAULT_SCALE = 30; // tmp
    private static final int CELL_SIZE = Double.valueOf(DEFAULT_SCALE * GameObjectView.DEFAULT_SCALE).intValue();

    private final int columns;
    private final int rows;
    private final Consumer<Collection<Position>> receiver;
    private final BiMap<JLayeredPane, Position> cells = HashBiMap.create();
    private Optional<WindowInfo> window;

    /**
     * The constructor of the component.
     * 
     * @param sendObjects The function that needs to process the list of objects
     * @param size        The size of the map
     */
    public GridView(final Consumer<Collection<Position>> sendObjects, final Position size) {
        this.receiver = sendObjects;
        this.columns = size.x();
        this.rows = size.y();
        this.window = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JScrollPane build(final WindowInfo window) {
        this.window = Optional.of(window);
        // Create the grid and set its minimum size so every cell is a square.
        final var grid = new JPanel(new GridLayout(rows, columns));
        grid.setPreferredSize(new Dimension(columns * CELL_SIZE, rows * CELL_SIZE));
        // Create the mouse listener used for a "drawing-like" experience.
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
                receiver.accept(positionInvolved);
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
        // Set each in each cell a layered pane, so that it can contain multiple images
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                final JLayeredPane jp = new JLayeredPane();
                jp.setBounds(0, 0, CELL_SIZE, CELL_SIZE);
                // jp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                cells.put(jp, Position.of(x, y));
                jp.addMouseListener(m);
                grid.add(jp);
            }
        }
        // if the grid is too big, add scrollbars.
        return new JScrollPane(grid);
    }

    /**
     * Changes the grid cells view based on the given map.
     * 
     * @param positions Contains the list of objets to place in the cell in a specific position
     * @param gridToUpdate the grid containing the cells, to update after each modification
     */
    public void setPositionFromMap(final Map<Position, List<GameObjectType>> positions, final JScrollPane gridToUpdate) {
        final var window = Optionals.orIllegalState(this.window, "The method build has not been called yet");
        positions.entrySet().forEach(e -> {
            final var panel = cells.inverse().get(e.getKey());
            // Clear the content of the cell
            panel.removeAll();
            // Set all the images in the cell
            e.getValue().forEach(object -> {
                final var objView = new GameObjectView(object, 1 / window.scale()).build(window);
                objView.setBounds(0, 0, CELL_SIZE, CELL_SIZE);
                panel.add(objView, (Object) e.getValue().indexOf(object));
            });
            // Re-Draw the single cell
        });
        gridToUpdate.revalidate();
        gridToUpdate.repaint();
    }
}
