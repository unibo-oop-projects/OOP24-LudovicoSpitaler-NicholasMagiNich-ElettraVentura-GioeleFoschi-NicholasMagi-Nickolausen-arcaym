package arcaym.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Utility class for swing.
 */
public final class SwingUtils {

    /**
     * Default screen size.
     */
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

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
     */
    public static void testComponent(final JComponent component) {
        // TODO remove
        final var gap = 100;
        final var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final var panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        panel.add(component, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
