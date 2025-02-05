package arcaym.view.editor.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import arcaym.common.utils.Position;
import arcaym.controller.editor.api.EditorController;
import arcaym.model.editor.EditorGridException;
import arcaym.view.app.AbstractView;
import arcaym.view.core.ViewComponent;
import arcaym.view.editor.api.EditorView;
import arcaym.view.editor.impl.components.GridView;
import arcaym.view.editor.impl.components.SideMenuView;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * The editor complete page.
 */
public class EditorMainView extends AbstractView<EditorController> implements ViewComponent<JPanel>, EditorView {

    private static final String ERASER_ICON_PATH = "eraser.png";

    private boolean isErasing;

    /**
     * Default constructor.
     * @param controller
     */
    public EditorMainView(final EditorController controller) {
        super(controller);
        isErasing = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var out = new JPanel();
        out.setLayout(new BorderLayout());
        final var sideMenu = new SideMenuView(
            this.controller().getOwnedObjects(),
            this.controller()::setSelectedObject,
            this::setNotErase).build(window);
        out.add(sideMenu, BorderLayout.WEST);

        final var rightSide = new JPanel();
        rightSide.setLayout(new BorderLayout());

        final var footer = new JTextArea();
        footer.setEnabled(false);
        final var header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        final var eraserBtn = new JButton(new ImageIcon(SwingUtils.getResource(ERASER_ICON_PATH)));
        eraserBtn.addActionListener(evt -> isErasing = !isErasing);
        final var btnContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JButton start = new JButton("START");
        start.addActionListener(evt -> {
            try {
                this.controller().play();
            } catch (EditorGridException e) {
                footer.setText(e.getMessage());
            }
        });
        final JButton save = new JButton("SAVE");
        save.addActionListener(evt -> {
            this.controller().saveLevel();
        });
        final JButton undo = new JButton("Undo");
        undo.setEnabled(this.controller().canUndo());
        undo.addActionListener(evt -> {
            this.controller().undo();
            undo.setEnabled(this.controller().canUndo());
        });
        btnContainer.add(eraserBtn);
        btnContainer.add(undo);
        btnContainer.add(save);
        btnContainer.add(new JSeparator(JSeparator.VERTICAL));
        btnContainer.add(start);
        btnContainer.add(new JSeparator(JSeparator.VERTICAL));
        header.add(btnContainer, BorderLayout.EAST);
        rightSide.add(header, BorderLayout.NORTH);

        final var gap = SwingUtils.getBigGap(footer);
        footer.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        footer.setBackground(Color.BLACK);
        final var grid =  new GridView(t -> {
            this.executeCommand(t, footer::setText);
            undo.setEnabled(this.controller().canUndo());
        }, this.controller().getSize());

        final JScrollPane body = grid.build(window);

        this.controller().setView(map -> grid.setPositionFromMap(map, body));
        this.controller().setupMap();

        rightSide.add(body, BorderLayout.CENTER);
        rightSide.add(footer, BorderLayout.SOUTH);

        out.add(rightSide, BorderLayout.CENTER);
        return out;
    }

    private void executeCommand(final Collection<Position> positions, final Consumer<String> displayError) {
        try {
            if (isErasing) {
                this.controller().eraseArea(positions);
            } else {
                this.controller().applyChange(positions);
            }
        } catch (EditorGridException e) {
            displayError.accept(e.getMessage());
        }
    }

    private void setNotErase() {
        this.isErasing = false;
    }
}
