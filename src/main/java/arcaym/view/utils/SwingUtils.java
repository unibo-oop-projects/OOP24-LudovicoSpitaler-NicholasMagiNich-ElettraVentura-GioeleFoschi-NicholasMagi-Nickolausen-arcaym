package arcaym.view.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import arcaym.view.core.api.WindowInfo;
import arcaym.view.core.impl.ScaledWindowInfo;

/**
 * Utility class for swing.
 */
public final class SwingUtils {

    private static final float WINDOW_SIZE_FACTOR = 0.8f;

    private static final float NORMAL_GAP_FACTOR = 1f;
    private static final float LITTLE_GAP_FACTOR = 0.5f;
    private static final float BIG_GAP_FACTOR = 2f;

    private SwingUtils() { }

    /**
     * Scale dimension by factor.
     * @param dimension source
     * @param scaleFactor scale factor
     * @return resulting dimension
     */
    public static Dimension scaleDimension(final Dimension dimension, final float scaleFactor) {
        return new Dimension(
            Math.round(dimension.width * scaleFactor),
            Math.round(dimension.height * scaleFactor)
        );
    }

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
        return Math.round(component.getFontMetrics(component.getFont()).getHeight() * scaleFactor);
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
     * 
     * @param componentCreator creation function for the component to show
     * @return created frame
     */
    public static JFrame testComponent(final Function<WindowInfo, JComponent> componentCreator) {
        final var frame = new JFrame();
        final var screenInfo = new ScaledWindowInfo(WINDOW_SIZE_FACTOR);
        frame.setSize(screenInfo.size());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final var panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new BorderLayout());
        panel.add(componentCreator.apply(screenInfo), BorderLayout.CENTER);
        frame.setVisible(true);
        return frame;
    }

}
