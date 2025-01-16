package arcaym.common.geometry.impl;

/**
 * Utility class for {@link Rectangle}.
 */
public class Rectangles {

    public static boolean intersecting(final Rectangle rect1, final Rectangle rect2) {
        if (rect1.northWest().x() + rect1.base() <= rect2.northWest().x()
                || rect1.northWest().x() >= rect2.northWest().x() + rect2.base()
                || rect1.northWest().y() + rect1.height() <= rect2.northWest().y()
                || rect1.northWest().y() >= rect2.northWest().y() + rect2.height()) {
                    return false;
        }
        return true;
    }
}
