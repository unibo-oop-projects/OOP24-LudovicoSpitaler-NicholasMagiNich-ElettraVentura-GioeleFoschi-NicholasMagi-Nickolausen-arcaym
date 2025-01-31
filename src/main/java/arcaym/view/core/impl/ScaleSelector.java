package arcaym.view.core.impl;

import java.awt.BorderLayout;
import java.util.Objects;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import arcaym.view.app.menu.MainMenu;
import arcaym.view.app.panels.PanelsSwitcher;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

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
    public WindowInfo askScale(final JFrame frame) {
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
            SwingUtils.closeFrame(frame);
        }
        final var scale = Scale.values()[result];
        final var window = new ScaledWindowInfo(scale.value(), scale.isFullScreen());
        frame.dispose(); // prevent java.awt.IllegalComponentStateException: The frame is displayable
        frame.setUndecorated(window.isFullscreen());
        frame.setSize(window.size());
        frame.setResizable(false);
        return window;
    }
    
    public static void main(String[] args) {
        final var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final var window = new ScaleSelector().askScale(frame);
        frame.setContentPane(new JPanel(new BorderLayout()));
        frame.add(new PanelsSwitcher(
            switcher -> () -> new MainMenu(switcher),
            () -> SwingUtils.closeFrame(frame)
            ).build(window), BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
