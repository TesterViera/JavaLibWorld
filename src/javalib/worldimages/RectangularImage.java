package javalib.worldimages;
import java.awt.Color;
import javalib.colors.*;


/**
 * An image that directly stores its own width and height.
 * 
 * @author Stephen Bloch
 * @version Dec. 2, 2012
 */
abstract class RectangularImage extends ColoredImage
{
    private int width, height;
    
    
    /**
     * The full constructor
     * 
     * @param width
     * @param height
     * @param color
     */
    RectangularImage (int width, int height, Color color, Mode mode) {
        super(color, mode);
        this.width = width;
        this.height = height;
    }
    
    RectangularImage (int width, int height, IColor color, Mode mode) {
        super(color, mode);
        this.width = width;
        this.height = height;
    }
    
    public int getRight () {
        return this.width;
    }
    
    public int getBottom () {
        return this.height;
    }
    
    /**
     * Functional setter for width and height.
     * 
     * @param width
     * @param height
     * @return a new image just like this one but with different dimensions.
     */
    abstract RectangularImage replaceDimensions (int width, int height);
    
    /**
     * Functional setter for width.
     * 
     * @param width
     * @return a new image just like this one but with different width.
     */
    RectangularImage replaceWidth (int width) {
        return this.replaceDimensions (width, this.height);
    }
    
    /**
     * Functional setter for height.
     * 
     * @param height
     * @return a new image just like this one but with different height.
     */
    RectangularImage replaceHeight (int height) {
        return this.replaceDimensions (this.width, height);
    }
    
    
    public boolean equals (Object other) {
        return super.equals(other) &&
        this.width == ((RectangularImage)other).getWidth() &&
        this.height == ((RectangularImage)other).getHeight();
    }
    
    public int hashCode () {
        return super.hashCode() + rotate(this.width, 16) + this.height;
    }
}
