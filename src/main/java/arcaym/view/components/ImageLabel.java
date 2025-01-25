package arcaym.view.components;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import arcaym.view.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

public class ImageLabel implements ViewComponent<JLabel> {

    private final String path;

    public ImageLabel(final String path) {
        this.path = path;
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
            Double.valueOf(image.getWidth(label) * SwingUtils.WINDOW_SCALING.x()).intValue(),
            Double.valueOf(image.getHeight(label) * SwingUtils.WINDOW_SCALING.y()).intValue(),
            Image.SCALE_DEFAULT
        ));
        return label;
    }

}
