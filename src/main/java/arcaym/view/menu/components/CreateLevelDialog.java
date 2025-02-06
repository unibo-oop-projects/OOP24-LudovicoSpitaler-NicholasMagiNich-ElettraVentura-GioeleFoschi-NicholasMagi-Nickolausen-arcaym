package arcaym.view.menu.components;

import java.awt.Component;
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
import arcaym.view.components.HorizontalCenteredPanel;
import arcaym.view.core.ViewDialog;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Show a dialog to create a level and directly switch to the editor.
 */
public class CreateLevelDialog implements ViewDialog<Component, Void> {

    private static final String TITLE = "Level creation";
    private static final String MESSAGE = "Please enter the level details";
    private static final String DEFAULT_NAME = "Untitled";
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 20;
    private static final EditorType DEFAULT_TYPE = EditorType.NORMAL;
    private static final float LABELS_FONT_SCALE = 1.5f;
    private static final float MESSAGE_FONT_SCALE = 2f;

    private final LevelCreator levelCreator;

    /**
     * Interface for the level creation function.
     */
    @FunctionalInterface
    public interface LevelCreator {

        /**
         * Create an editor from the given attributes.
         * 
         * @param width grid width
         * @param height grid height
         * @param type editor type
         * @param name level name
         */
        void createEditor(int width, int height, EditorType type, String name);

    } 

    /**
     * Initialize with level creator.
     * 
     * @param levelCreator level creation function
     */
    public CreateLevelDialog(final LevelCreator levelCreator) {
        this.levelCreator = Objects.requireNonNull(levelCreator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Void show(final WindowInfo window, final Component parent) {
        final var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        final var nameInput = new JTextField(DEFAULT_NAME);
        final var widthInput = this.createNumberInput(DEFAULT_WIDTH);
        final var heightInput = this.createNumberInput(DEFAULT_HEIGHT);
        final var typesInput = new JComboBox<>(EditorType.values());
        typesInput.setSelectedItem(DEFAULT_TYPE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        mainPanel.add(new HorizontalCenteredPanel().build(
            window, 
            SwingUtils.changeFontSize(new JLabel(MESSAGE), MESSAGE_FONT_SCALE)
        ));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Name", nameInput, gap));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Width", widthInput, gap));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(this.createRow("Height", heightInput, gap));
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
            this.levelCreator.createEditor(
                (int) widthInput.getValue(),
                (int) heightInput.getValue(),
                (EditorType) typesInput.getSelectedItem(),
                nameInput.getText()
            );
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

}
