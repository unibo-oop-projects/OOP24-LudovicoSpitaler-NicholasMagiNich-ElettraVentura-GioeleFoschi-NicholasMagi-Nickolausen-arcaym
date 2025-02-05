package arcaym.view.core.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import arcaym.view.core.api.WindowInfo;

/**
 * View of a {@link JOptionPane} to select the windo scale.
 */
public class ScaleSelector {

    private static final String TITLE = "Scale selector";
    private static final String MESSAGE = "What scale would you like to use?";
    private static final Scale DEFAULT_SCALE = Scale.X75;

    /**
     * Ask the user what scale would he like to use
     * and adjust frame accordingly.
     * 
     * @param frame window frame
     * @return window infos
     */
    public Optional<WindowInfo> askScale(final JFrame frame) {
        final var scalesLabels = Stream.of(Scale.values()).map(Scale::label).toArray();
        final var result = JOptionPane.showOptionDialog(
            Objects.requireNonNull(frame),
            MESSAGE,
            TITLE,
            JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, // default icon
            scalesLabels,
            DEFAULT_SCALE.label()
        );
        if (result == JOptionPane.CLOSED_OPTION) {
            return Optional.empty();
        }
        final var scale = Scale.values()[result];
        final var window = new ScaledWindowInfo(scale);
        frame.dispose(); // prevent java.awt.IllegalComponentStateException: The frame is displayable
        frame.setResizable(false);
        if (window.isFullscreen()) {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            frame.setSize(window.size());
        }
        return Optional.of(window);
    }

}
