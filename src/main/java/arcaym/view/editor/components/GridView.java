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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arcaym.common.utils.Position;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.api.ViewComponent;
import arcaym.view.objects.GameObjectView;
import arcaym.view.utils.SwingUtils;
/**
 * An implementation of the grid view.
 */
public class GridView implements ViewComponent<JScrollPane> {

    private static final int CELL_SCALE = 50;

    private final int columns;
    private final int rows;
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
        this.columns = size.x();
        this.rows = size.y();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JScrollPane build() {
        return buildGrid();
    }

    private JScrollPane buildGrid() {
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
        positions.entrySet().forEach(e -> {
            final var panel = cells.inverse().get(e.getKey());
            // Clear the content of the cell
            panel.removeAll();
            // Set all the images in the cell
            e.getValue().forEach(object -> {
                final var objView = new GameObjectView(object).build();
                objView.setBounds(0, 0, cellDimension, cellDimension);
                panel.add(objView, (Object) e.getValue().indexOf(object));
            });
            // Re-Draw the single cell
            panel.revalidate();
            panel.repaint();
        });
    }

    public static void main(final String[] args) {

        class InternalTest {
            private int i = 0;
            private final GridView grid;

            public InternalTest(int col, int row){
                this.grid = new GridView(this::compute,Position.of(col, row));
            }

            public void draw(final Map<Position, List<GameObjectType>> map){
                this.grid.setPositionFromMap(map);
            }

            public void compute(final Collection<Position> positions){
                if (i % 2 == 0){
                    this.draw(positions.stream().collect(Collectors.toMap(p -> p, p ->
                    List.of(GameObjectType.WALL))));
                } else {
                    this.draw(positions.stream().collect(Collectors.toMap(p -> p, p ->
                    List.of(GameObjectType.FLOOR, GameObjectType.COIN))));
                }
                i++;
            }
        }
        final var test = new InternalTest(55, 28);
        SwingUtils.testComponent(test.grid.build());
        test.draw(IntStream.range(0, 55)
            .mapToObj(i -> i)
            .flatMap(x -> IntStream.range(0, 28)
            .mapToObj(y -> Position.of(x, y)))
            .collect(Collectors.toMap(p -> p, p -> List.of(GameObjectType.WALL))));
    }
}
