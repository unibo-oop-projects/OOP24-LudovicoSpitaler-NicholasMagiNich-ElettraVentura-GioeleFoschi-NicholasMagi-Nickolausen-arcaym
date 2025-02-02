package arcaym.view.components;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for an image.
 */
public class ImageLabel implements ViewComponent<JLabel> {

    /**
     * Default image scaling value.
     */
    public static final double DEFAULT_SCALE = 1.0;

    private final String path;
    private final double scale;

    /**
     * Initialize view for image with given scale.
     * 
     * @param path resource path
     * @param scale image scale
     */
    public ImageLabel(final String path, final double scale) {
        this.path = path;
        this.scale = scale;
    }

    /**
     * Initialize view for image.
     * 
     * @param path resource path
     */
    public ImageLabel(final String path) {
        this(path, DEFAULT_SCALE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLabel build(final WindowInfo window) {
        final var imageIcon = new ImageIcon(SwingUtils.getResource(this.path));
        final var label = new JLabel(imageIcon);
        label.setOpaque(false);
        final var image = imageIcon.getImage();
        imageIcon.setImage(image.getScaledInstance(
            Double.valueOf(image.getWidth(label) * window.ratio().x() * this.scale).intValue(),
            Double.valueOf(image.getHeight(label) * window.ratio().y() * this.scale).intValue(),
            Image.SCALE_FAST
        ));
        return label;
    }

}
