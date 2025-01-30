package arcaym.view.components;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import arcaym.view.core.api.WindowInfo;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

/**
 * View for an image.
 */
public class ImageLabel implements ViewComponent<JLabel> {

    private static final double DEFAULT_SCALE = 1.0;
    private static final Map<String, ImageIcon> IMAGES_CACHE = new HashMap<>();

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
        if (!IMAGES_CACHE.containsKey(this.path)) {
            IMAGES_CACHE.put(this.path, new ImageIcon(SwingUtils.getResource(this.path)));
        }
        final var imageIcon = IMAGES_CACHE.get(this.path);
        final var label = new JLabel(imageIcon);
        final var image = imageIcon.getImage();
        imageIcon.setImage(image.getScaledInstance(
            Double.valueOf(image.getWidth(label) * window.ratio().x() * this.scale).intValue(),
            Double.valueOf(image.getHeight(label) * window.ratio().y() * this.scale).intValue(),
            Image.SCALE_DEFAULT
        ));
        return label;
    }

}
