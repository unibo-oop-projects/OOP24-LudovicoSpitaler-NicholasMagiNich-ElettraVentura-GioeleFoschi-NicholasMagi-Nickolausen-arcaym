package arcaym.view.editor.components.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import arcaym.view.editor.components.api.EditorGrid;

/**
 * The grid view implementation in swing.
 */
public class EditorGridView extends JPanel implements EditorGrid {

    private static final long serialVersionUID = 1L;
    private final JPanel header;
    private final JPanel body;
    private final JPanel footer;

    private static final int ROWS = 13;
    private static final int HEADER_ROWS = 1;
    private static final int BODY_ROWS = 10;
    private static final int FOOTER_ROWS = 2;

    /**
     * The constructor for the page.
     */
    public EditorGridView() {
        this.header = new JPanel();
        this.body = new JPanel();
        this.footer = new JPanel();
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void initView() {
        this.setLayout(new GridBagLayout());
        final Dimension headerDimension = calculateComponentDimension(HEADER_ROWS);
        setAllDimension(header, headerDimension);
        final GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.gridheight = HEADER_ROWS;
        initializeHeaderContent();
        this.add(header, headerConstraints);

        final Dimension bodyDimension = calculateComponentDimension(BODY_ROWS);
        setAllDimension(body, bodyDimension);
        final GridBagConstraints bodyConstraints = new GridBagConstraints();
        bodyConstraints.gridx = 0;
        bodyConstraints.gridy = headerConstraints.gridy + headerConstraints.gridheight;
        bodyConstraints.gridheight = BODY_ROWS;
        initializeBodyContent();
        this.add(body, bodyConstraints);

        final Dimension footerDimension = calculateComponentDimension(FOOTER_ROWS);
        setAllDimension(footer,footerDimension);
        final GridBagConstraints footerConstraints = new GridBagConstraints();
        footerConstraints.gridx = 0;
        footerConstraints.gridy = bodyConstraints.gridy + bodyConstraints.gridheight;
        footerConstraints.gridheight = FOOTER_ROWS;
        initializeFooterContent();
        this.add(footer, footerConstraints);
    }

    private Dimension calculateComponentDimension(final int fraction) {
        return new Dimension(this.getWidth(), (this.getHeight() / ROWS) * fraction);
    }

    private void setAllDimension(final Component component, final Dimension size) {
        component.setPreferredSize(size);
        component.setMinimumSize(size);
        component.setSize(size);
    }

    private void initializeHeaderContent() {
        header.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton start = new JButton("START");
        final JButton redo = new JButton("Redo");
        final JButton save = new JButton("SAVE");
        final JButton undo = new JButton("Undo");
        
        header.add(undo);
        header.add(save);
        header.add(redo);
        header.add(new JSeparator(JSeparator.VERTICAL));
        header.add(start);
        header.add(new JSeparator(JSeparator.VERTICAL));
    }

    private void initializeBodyContent() {
        // GioGioDix's matter
    }

    private void initializeFooterContent() {
        // GioGioDix's matter
    }
}
