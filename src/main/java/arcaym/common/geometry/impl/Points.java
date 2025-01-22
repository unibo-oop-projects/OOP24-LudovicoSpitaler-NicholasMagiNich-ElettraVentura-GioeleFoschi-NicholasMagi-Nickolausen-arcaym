package arcaym.common.geometry.impl;

import java.util.Objects;

/**
 * Utility class for {@link Point}.
 */
public final class Points {

    private Points() { }

    /**
     * Calculate euclidean distance between two points.
     * 
     * @param p1 first point
     * @param p2 second point
     * @return distance value
     */
    public static double distance(final Point p1, final Point p2) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        return Math.sqrt(Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2));
    }

}
