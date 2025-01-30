package arcaym.view.app.menu;

import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * View of a {@link JOptionPane} to select the windo scale.
 */
public class ScaleSelector {

    private static final String TITLE = "Scale selector";
    private static final String MESSAGE = "What scale would you like to use?";
    private static final Float[] SCALES = {
        0.9f,
        .75f,
        .50f
    };
    private static final int DEFAULT_SCALE_INDEX = 1;

    /**
     * Show dialog to get window scale value.
     * 
     * @param frame parent frame
     * @return selected value
     */
    public float show(final JFrame frame) {
        final var result = JOptionPane.showOptionDialog(
            Objects.requireNonNull(frame),
            MESSAGE,
            TITLE,
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            SCALES,
            SCALES[DEFAULT_SCALE_INDEX]
        );
        if (result == JOptionPane.CLOSED_OPTION) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        return SCALES[result];
    }

}
