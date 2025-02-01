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
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.Position;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.objects.GameObjectView;
/**
 * An implementation of the grid view.
 */
public class GridView implements ViewComponent<JScrollPane> {

    private static final int CELL_SCALE = 50;

    private final int columns;
    private final int rows;
    private final Consumer<Collection<Position>> receiver;
    private final BiMap<JLayeredPane, Position> cells = HashBiMap.create();
    private Optional<WindowInfo> window;

    /**
     * The constructor of the component.
     * 
     * @param sendObjects The function that needs to process the list of objects
     * @param size The size of the map
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
        final int cellDimension = window.size().width / CELL_SCALE;
        // Create the grid and set its minimum size so every cell is a square.
        final var grid = new JPanel(new GridLayout(rows, columns));
        grid.setPreferredSize(new Dimension(columns * cellDimension, rows * cellDimension));
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
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                final JLayeredPane jp = new JLayeredPane();
                jp.setBounds(0, 0, cellDimension, cellDimension);
                jp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                cells.put(jp, Position.of(i, j));
                jp.addMouseListener(m);
                grid.add(jp);
            }
        }
        // if the grid is too big, add scrollbars.
        return new JScrollPane(grid);
    }

    /**
     * Changes the grid cells view based on the given map.
     * @param positions Contains the list of objets to place in the cell in a specific position
     */
    public void setPositionFromMap(final Map<Position, List<GameObjectType>> positions) {
        final var window = Optionals.orIllegalState(this.window, "The method build has not been called yet");
        final int cellDimension = window.size().width / CELL_SCALE;
        positions.entrySet().forEach(e -> {
            final var panel = cells.inverse().get(e.getKey());
            // Clear the content of the cell
            panel.removeAll();
            // Set all the images in the cell
            e.getValue().forEach(object -> {
                final var objView = new GameObjectView(object).build(window);
                objView.setBounds(0, 0, cellDimension, cellDimension);
                panel.add(objView, (Object) e.getValue().indexOf(object));
            });
            // Re-Draw the single cell
            panel.revalidate();
            panel.repaint();
        });
    }
}