package javalib.worldimages;

import java.awt.Color;

/**
 * A function from Color to Color, suitable for use in the "map" method.
 *
 * @author Stephen Bloch
 * @since Dec. 28, 2012
 * @version Feb. 25, 2013
 */
public interface ImageMap<OtherInfo>
{
    /**
     * Given information about a pixel in an existing image, determine the color of a pixel in a new image.
     * 
     * @param x     the x coordinate of the existing pixel
     * @param y     the y coordinate of the existing pixel
     * @param oldColor the Color of the existing pixel
     * @param other an arbitrary piece of extra information provided by the caller of "map"
     * @return a Color to be used in the corresponding pixel of the resulting image
     */
    public Color pixelColor (int x, int y, Color oldColor, OtherInfo other);
}
