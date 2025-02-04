package arcaym.view.editor.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import arcaym.controller.editor.api.EditorController;
import arcaym.view.app.impl.AbstractView;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.editor.api.EditorView;
import arcaym.view.editor.impl.components.SideMenuView;
import arcaym.view.utils.SwingUtils;

/**
 * The editor complete page.
 */
public class EditorMainView extends AbstractView<EditorController> implements ViewComponent<JPanel>, EditorView {

    private static final String ERASER_ICON_PATH = new StringBuilder()
        .append("buttons")
        .append(System.getProperty("file.separator"))
        .append("rubber.png").toString();

    /**
     * Default constructor.
     * @param controller
     */
    public EditorMainView(final EditorController controller) {
        super(controller);
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
        this.controller()::setSelectedObject).build(window);
        out.add(sideMenu, BorderLayout.WEST);

        final var rightSide = new JPanel();
        rightSide.setLayout(new BorderLayout());

        final var header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        final var eraserBtn = new JButton(new ImageIcon(SwingUtils.getResource(ERASER_ICON_PATH)));
        sideMenu.setEnabled(eraserBtn.isEnabled());
        final var btnContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JButton start = new JButton("START");
        start.addActionListener(evt -> {
            this.controller().play();
        });
        final JButton save = new JButton("SAVE");
        save.addActionListener(evt -> {
            this.controller().saveLevel();
        });
        final JButton undo = new JButton("Undo");
        undo.setEnabled(this.controller().canUndo());
        undo.addActionListener(evt -> {
            this.controller().undo();
        });
        btnContainer.add(eraserBtn);
        btnContainer.add(undo);
        btnContainer.add(save);
        btnContainer.add(new JSeparator(JSeparator.VERTICAL));
        btnContainer.add(start);
        btnContainer.add(new JSeparator(JSeparator.VERTICAL));
        header.add(btnContainer, BorderLayout.EAST);
        rightSide.add(header, BorderLayout.NORTH);

        final var footer = new JTextArea();
        footer.setEnabled(false);
        footer.setText("MAMMAMIA CHE ROBBA!!");

        // JScrollPane body = new GridView(t -> {
        //     try {
        //         controller().applyChange(t);
        //     } catch (EditorGridException e) {
        //         footer.setText(e.getMessage());
        //     }
        // }, Position.of(20,20)).build(window);

        // rightSide.add(body, BorderLayout.CENTER);
        rightSide.add(footer, BorderLayout.SOUTH);

        out.add(rightSide, BorderLayout.CENTER);
        return out;
    }
}
