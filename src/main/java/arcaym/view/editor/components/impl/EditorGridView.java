package arcaym.view.editor.components.impl;

import java.awt.Color;

import javax.swing.JComponent;

import arcaym.view.editor.components.api.EditorGrid;

/**
 * The grid view implementation in swing.
 */
public class EditorGridView extends JComponent implements EditorGrid {

    private static final long serialVersionUID = 1L;

    /**
     * The constructor for the page.
     */
    public EditorGridView() {
        this.setBackground(Color.RED);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void initView() {

    }
}
