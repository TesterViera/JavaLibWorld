package javalib.worldimages;

import java.awt.Color;

/**
 * A function from (x,y) to Color, suitable for use in the "build" method.
 *
 * @author Stephen Bloch
 * @since Dec. 28, 2012
 */
interface ImageBuilder
{
    /**
     * Given the location of a pixel, determine what color it should be
     * 
     * @param x     the x coordinate of the existing pixel
     * @param y     the y coordinate of the existing pixel
     * @param other an arbitrary piece of extra information provided by the caller of "build"
     * @return a Color to be used in the pixel of the resulting image
     */
   public Color pixelColor (int x, int y, Object other);
}
