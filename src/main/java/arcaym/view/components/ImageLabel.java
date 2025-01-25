package arcaym.view.components;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import arcaym.view.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

public class ImageLabel implements ViewComponent<JLabel> {

    private static final double DEFAULT_SCALE = 1.0;

    private final String path;
    private final double scale;

    public ImageLabel(final String path, final double scale) {
        this.path = path;
        this.scale = scale;
    }

    public ImageLabel(final String path) {
        this(path, DEFAULT_SCALE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLabel build() {
        final var imageIcon = new ImageIcon(SwingUtils.getResource(this.path));
        final var label = new JLabel(imageIcon);
        final var image = imageIcon.getImage();
        imageIcon.setImage(image.getScaledInstance(
            Double.valueOf(image.getWidth(label) * SwingUtils.WINDOW_SCALING.x() * this.scale).intValue(),
            Double.valueOf(image.getHeight(label) * SwingUtils.WINDOW_SCALING.y() * this.scale).intValue(),
            Image.SCALE_DEFAULT
        ));
        return label;
    }

}
