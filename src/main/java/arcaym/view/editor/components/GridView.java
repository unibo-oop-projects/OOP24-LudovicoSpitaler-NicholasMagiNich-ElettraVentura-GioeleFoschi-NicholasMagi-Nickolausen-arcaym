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
import javax.swing.JPanel;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arcaym.common.utils.Position;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.api.ViewComponent;
import arcaym.view.utils.SwingUtils;
/**
 * An implementation of the grid view.
 */
public class GridView implements ViewComponent<JPanel> {

    private static final int COLUMNS = 50;
    private static final int ROWS = 28;

    private final Consumer<Collection<Position>> reciver;
    private final BiMap<JPanel, Position> cells = HashBiMap.create();
    private final int cellDimension;

    /**
     * The constructor of the component.
     * @param sendObjects the function that needs to process the list of objects
     * @param cellDimension the minimum dimension of the cell
     */
    public GridView(final Consumer<Collection<Position>> sendObjects, final int cellDimension) {
        this.reciver = sendObjects;
        this.cellDimension = cellDimension;
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

        final MouseListener m = new MouseListener() {

            private final List<Position> positionInvolved = new ArrayList<>();
            private boolean isClicking;

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
                final JPanel jp = new JPanel();
                jp.setSize(new Dimension(cellDimension, cellDimension));
                jp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                jp.setBackground(Color.BLUE);
                cells.put(jp, Position.of(i, j));
                jp.addMouseListener(m);
                grid.add(jp);
            }
        }
        return grid;
    }

    /**
     * Changes the grid cells view based on the given map.
     * @param positions Contains the list of objets to place in the cell in a specific position
     */
    public void setPositionFromMap(final Map<Position, List<GameObjectType>> positions) {
        positions.entrySet().forEach(e -> {
            var color = Color.BLUE;
            if (e.getValue().size() == 1) {
                if (e.getValue().getFirst().equals(GameObjectType.FLOOR)) {
                    color = Color.LIGHT_GRAY;
                }
            } else {
                color = Color.PINK;
            }
            cells.inverse().get(e.getKey()).setBackground(color);
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
