package arcaym.common.geometry.impl;

/**
 * Utility class for {@link Rectangle}.
 */
public final class Rectangles {

    private Rectangles() { } 

    /**
     * Checks if two rectangles are intesecting.
     * 
     * @param rect1 first rectangles
     * @param rect2 second rectangles
     * @return if they are intersecting
     */
    public static boolean intersecting(final Rectangle rect1, final Rectangle rect2) {
        return rect1.southWest().x() < rect2.northEast().x()
                && rect1.northEast().x() > rect2.southWest().x()
                && rect1.southWest().y() < rect2.northEast().y()
                && rect1.northEast().y() > rect2.southWest().y();
    }

}
