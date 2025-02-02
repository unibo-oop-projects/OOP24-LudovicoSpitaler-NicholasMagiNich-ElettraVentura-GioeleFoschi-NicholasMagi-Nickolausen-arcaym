package arcaym.view.game.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.JPanel;

import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.core.objects.api.GameObjectInfo;

/**
 * Panel for the game view.
 */
public class GamePanel extends JPanel {
    private final static long serialVersionUID = 1L;
    private final Rectangle boundaries;
    private Optional<Dimension> dim = Optional.empty();
    private final ConcurrentMap<GameObjectInfo, Image> gameMap = new ConcurrentHashMap<>();

    /**
     * Basic constructor for GamePanel.
     * 
     * @param boundaries
     */
    public GamePanel(final Rectangle boundaries) {
        this.boundaries = boundaries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final double scale = calculateScale();
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        this.gameMap.entrySet().stream().forEach(entry -> {
            g2d.drawImage(entry.getValue(), Double.valueOf(entry.getKey().boundaries().northWest().x()*scale).intValue(), // boundaries?
            Double.valueOf(entry.getKey().boundaries().northWest().y()*scale).intValue(),
                    (int) entry.getKey().boundaries().base(), //to fix 
                    (int) entry.getKey().boundaries().height(), null);
        });
        g2d.drawString("Score", 0, 0);
        g2d.dispose();
    }

    private double calculateScale() {
        return Math.min(
            this.getSize().getHeight() / boundaries.height(),
            this.getSize().getWidth() / boundaries.base()
        );
    }

    /**
     * Creates new object in the game.
     * 
     * @param gameObject
     */
    public void createObject(final GameObjectInfo gameObject) {
        // this.gameMap.add(gameObject);
        this.repaint();
    }

    /**
     * Updates new object in the game.
     * 
     * @param gameObject
     */
    public void updateObject(final GameObjectInfo gameObject) {
        this.repaint();
    }

    /**
     * Destroys new object in the game.
     * 
     * @param gameObject
     */
    public void destroyObject(final GameObjectInfo gameObject) {
        this.gameMap.remove(gameObject);
        this.repaint();
    }
}
