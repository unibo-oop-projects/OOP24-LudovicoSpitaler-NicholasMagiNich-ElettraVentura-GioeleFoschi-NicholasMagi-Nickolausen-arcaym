package arcaym.view.app.menu.levels;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import arcaym.model.editor.EditorType;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.ViewDialog;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Show a dialog to create a level and directly switch to the editor.
 */
public class CreateLevelDialog implements ViewDialog<Component, Void> {

    private static final String TITLE = "Level creation";
    private static final String MESSAGE = "Please enter the level details";
    private static final String DEFAULT_NAME_PREFIX = "Untitled ";
    private static final String DATE_FORMAT = "yyyy-MM-dd-hh-mm-ss";
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 20;
    private static final EditorType DEFAULT_TYPE = EditorType.NORMAL;
    private static final float LABELS_FONT_SCALE = 1.5f;
    private static final float MESSAGE_FONT_SCALE = 2f;

    // private final Switcher switcher;

    // /**
    //  * Initialize with panel switcher.
    //  * 
    //  * @param switcher panel switcher
    //  */
    // public CreateLevelDialog(final Switcher switcher) {
    //     this.switcher = Objects.requireNonNull(switcher);
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public Void show(final WindowInfo window, final Component parent) {
        final var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        mainPanel.add(new CenteredPanel().build(window, SwingUtils.changeFontSize(new JLabel(MESSAGE), MESSAGE_FONT_SCALE)));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Name", new JTextField(this.getDefaultTitle()), gap));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Width", this.createNumberInput(DEFAULT_WIDTH), gap));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Height", this.createNumberInput(DEFAULT_HEIGHT), gap));
        final var typesInput = new JComboBox<>(EditorType.values());
        typesInput.setSelectedItem(DEFAULT_TYPE);
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Type", typesInput, gap));
        final var result = JOptionPane.showOptionDialog(
            Objects.requireNonNull(parent),
            mainPanel,
            TITLE,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null
        );
        if (result == JOptionPane.OK_OPTION) {
            // TODO create editor and switch to it
        }
        return null;
    }

    private JSpinner createNumberInput(final int defaultValue) {
        return new JSpinner(new SpinnerNumberModel(defaultValue, 0, null, 1));
    }

    private JPanel createRow(final String key, final JComponent input, final int gap) {
        final var rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.LINE_AXIS));
        rowPanel.add(SwingUtils.changeFontSize(new JLabel(key), LABELS_FONT_SCALE));
        rowPanel.add(Box.createHorizontalStrut(gap));
        rowPanel.add(Box.createHorizontalGlue());
        rowPanel.add(input);
        return rowPanel;
    }

    private String getDefaultTitle() {
        final var formatter = new SimpleDateFormat(DATE_FORMAT);
        return new StringBuilder(DEFAULT_NAME_PREFIX)
            .append(formatter.format(new Date()))
            .toString();
    }

}
