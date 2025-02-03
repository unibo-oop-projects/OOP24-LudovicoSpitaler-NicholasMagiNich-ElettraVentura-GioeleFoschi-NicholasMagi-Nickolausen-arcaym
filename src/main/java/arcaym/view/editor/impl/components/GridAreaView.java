package arcaym.view.editor.impl.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import arcaym.common.utils.Position;
import arcaym.controller.app.api.Controller;
import arcaym.controller.editor.api.EditorController;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;

/**
 * The grid view implementation in swing.
 */
public class GridAreaView implements ViewComponent<JPanel> {

    private final JPanel header;
    private final JPanel body;
    private final JPanel footer;
    private final EditorController controller;

    /**
     * The constructor for the page.
     */
    public GridAreaView(final EditorController controller) {
        this.header = new JPanel();
        this.body = new JPanel();
        this.footer = new JPanel();
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public JPanel build(final WindowInfo window) {
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        final var undoButton = initializeHeaderContent();
        initializeBodyContent(undoButton);
        initializeFooterContent();
        contentPanel.add(this.header);
        contentPanel.add(this.body);
        contentPanel.add(this.footer);
        // contentPanel.setLayout(new GridBagLayout());
        // final Dimension headerDimension = calculateComponentDimension(contentPanel.getSize(), HEADER_ROWS);
        // setAllDimension(header, headerDimension);
        // final GridBagConstraints headerConstraints = new GridBagConstraints();
        // headerConstraints.gridx = 0;
        // headerConstraints.gridy = 0;
        // headerConstraints.gridheight = HEADER_ROWS;
        // initializeHeaderContent();
        // contentPanel.add(header, headerConstraints);

        // final Dimension bodyDimension = calculateComponentDimension(contentPanel.getSize(), BODY_ROWS);
        // setAllDimension(body, bodyDimension);
        // final GridBagConstraints bodyConstraints = new GridBagConstraints();
        // bodyConstraints.gridx = 0;
        // bodyConstraints.gridy = headerConstraints.gridy + headerConstraints.gridheight;
        // bodyConstraints.gridheight = BODY_ROWS;
        // initializeBodyContent();
        // contentPanel.add(body, bodyConstraints);

        // final Dimension footerDimension = calculateComponentDimension(contentPanel.getSize(), FOOTER_ROWS);
        // setAllDimension(footer, footerDimension);
        // final GridBagConstraints footerConstraints = new GridBagConstraints();
        // footerConstraints.gridx = 0;
        // footerConstraints.gridy = bodyConstraints.gridy + bodyConstraints.gridheight;
        // footerConstraints.gridheight = FOOTER_ROWS;
        // initializeFooterContent();
        // contentPanel.add(footer, footerConstraints);
        return contentPanel;
    }

    private JButton initializeHeaderContent() {
        header.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton start = new JButton("START");
        start.addActionListener(evt -> {
            this.controller.play();
        });
        final JButton save = new JButton("SAVE");
        save.addActionListener(evt -> {
            this.controller.saveLevel();
        });
        final JButton undo = new JButton("Undo");
        undo.setEnabled(this.controller.canUndo());
        undo.addActionListener( evt -> {
            this.controller.undo();
        });

        header.add(undo);
        header.add(save);
        header.add(new JSeparator(JSeparator.VERTICAL));
        header.add(start);
        header.add(new JSeparator(JSeparator.VERTICAL));

        return undo;
    }

    private void initializeBodyContent(final JButton undoButton) {
        // final var gridView = new GridView(,Position.of(10, 10));
    }

    private void sus(final Collection<Position> coll) {
        // if(erase)
        // controller.eraseArea(coll);
        // controller.applyChange(coll);
    }

    private void initializeFooterContent() {
        // GioGioDix's matter
    }
}
