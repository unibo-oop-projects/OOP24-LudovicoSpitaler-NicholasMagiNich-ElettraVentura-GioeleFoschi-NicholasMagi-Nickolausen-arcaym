package arcaym.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import arcaym.common.geometry.impl.Point;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Utility class for swing.
 */
public final class SwingUtils {

    private static final float WINDOW_SIZE_FACTOR = 0.8f;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension DEFAULT_SIZE = new Dimension(1920, 1080);

    /**
     * Window size.
     */
    public static final Dimension WINDOW_SIZE = new Dimension(
        Math.round(SCREEN_SIZE.width * WINDOW_SIZE_FACTOR),
        Math.round(SCREEN_SIZE.height * WINDOW_SIZE_FACTOR)
    );

    /**
     * Window scaling relative to the default size.
     */
    public static final Point WINDOW_SCALING = Point.of(
        WINDOW_SIZE.getWidth() / DEFAULT_SIZE.getWidth(),
        WINDOW_SIZE.getHeight() / DEFAULT_SIZE.getHeight()
    );

    /**
     * {@link Color} that represents transparency.
     */
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

    private static final float NORMAL_GAP_FACTOR = 1f;
    private static final float LITTLE_GAP_FACTOR = 0.5f;
    private static final float BIG_GAP_FACTOR = 2f;

    private SwingUtils() { }

    /**
     * Change font size of a component.
     * 
     * @param component swing component
     * @param scaleFactor font scale factor
     */
    public static void changeFontSize(final JComponent component, final float scaleFactor) {
        final var font = component.getFont();
        component.setFont(font.deriveFont(font.getSize() * scaleFactor));
    }

    /**
     * Get gap value based on the font size of the component.
     * 
     * @param component swing component
     * @param scaleFactor gap scale factor
     * @return gap value
     */
    public static int getGap(final JComponent component, final float scaleFactor) {
        return Math.round(component.getFont().getSize() * scaleFactor);
    }

    /**
     * Call {@link #getGap(JComponent, float)} with a normal size factor.
     * 
     * @param component swing component
     * @return gap value
     */
    public static int getNormalGap(final JComponent component) {
        return getGap(component, NORMAL_GAP_FACTOR);
    }

    /**
     * Call {@link #getGap(JComponent, float)} with a little size factor.
     * 
     * @param component swing component
     * @return gap value
     */
    public static int getLittleGap(final JComponent component) {
        return getGap(component, LITTLE_GAP_FACTOR);
    }

    /**
     * Call {@link #getGap(JComponent, float)} with a big size factor.
     * 
     * @param component swing component
     * @return gap value
     */
    public static int getBigGap(final JComponent component) {
        return getGap(component, BIG_GAP_FACTOR);
    }

    /**
     * Get URL to project resource.
     * 
     * @param path relative path to resource
     * @return resource URL
     */
    public static URL getResource(final String path) {
        return Thread.currentThread().getContextClassLoader().getResource(path);
    }

    /**
     * Show a component in a test window.
     * NEEDS TO BE REMOVED.
     * 
     * @param component component to show
     * @return created frame
     */
    public static JFrame testComponent(final JComponent component) {
        // TODO remove
        final var frame = new JFrame();
        frame.setSize(WINDOW_SIZE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final var panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        frame.setVisible(true);
        return frame;
    }

}
