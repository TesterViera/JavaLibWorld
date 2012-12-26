package javalib.sbimages;


/**
 * The API for an image.
 * 
 * @author Stephen BLoch
 * @version Dec. 2, 2012
 */
public interface Image extends Cloneable
{   
    /**
     * Is this the same as another Image?
     * 
     * Define this at every level that has instance variables.
     * 
     * @param other   the object to compare with this
     */
    abstract public boolean equals (Object other);
    
    /**
     * Get a hash code for the Image.
     * 
     * Define this at every level that has instance variables.
     */
    abstract public int hashCode ();
    
    /**
     * Produce the top of the bounding box.
     * 
     * @return  the y coordinate of the top of the bounding box
     */
    abstract public int getTop();
    
    /**
     * Produce the bottom of the bounding box.
     * 
     * @return  the y coordinate of the bottom of the bounding box
     */
    abstract public int getBottom();
    
    /**
     * Produce the left edge of the bounding box.
     * 
     * @return  the x coordinate of the left edge of the bounding box
     */
    abstract public int getLeft();
    
    /**
     * Produce the bottom of the bounding box.
     * 
     * @return  the x coordinate of the right edge of the bounding box
     */
    abstract public int getRight();
    
    /**
     * Produce the width of this image
     * 
     * @return the width of this image
     */
    abstract public int getWidth();
  
    /**
     * Produce the height of this image
     * 
     * @return the height of this image
     */
    abstract public int getHeight();

    /**
     * Produce a <code>String</code> that represents this image.
     * 
     * Define this in all concrete subclasses.
     * Convention: The result of toString() will neither begin nor end with a newline,
     * although there may be newlines in the middle.
     * 
     * @return the <code>String</code> representation of this image
     * @since Dec. 2, 2012
     */
    abstract public String toString ();

    /**
     * Produce a <code>String</code> that represents this image, 
     * indented by the given <code>indent</code>.
     * 
     * Define this in all concrete subclasses.
     * Convention: The result of toIndentedString() will neither begin nor end with a newline;
     * the specified indent will immediately follow each internal newline.     * 
     * 
     * @param indent the given prefix representing the desired indentation
     * @return the <code>String</code> representation of this image
     */
    abstract public String toIndentedString(String indent);

    /** 
     * Draw this image in the provided <code>Graphics2D</code> context.
     * 
     * @param g the provided <code>Graphics2D</code> context
     */
    abstract public void draw(java.awt.Graphics2D g);
    
    /**
     * Produce the image translated by the given (dx, dy).
     * Functional; does NOT modify this!
     * 
     * @param dx the horizontal offset
     * @param dy the vertical offset
     */
    abstract public Image getTranslated (int dx, int dy);
    abstract public Image getTranslated (Posn dxdy);

    /**
     * Produce a "normalized" version of this image, with top-left corner at (0,0).
     * 
     * @return an image just like this (in fact it may BE this) whose top-left corner is at (0,0).
     */
    abstract public Image getNormalized();
    
    /**
     * Get a version of this image rotated by a specified number of degrees.
     * 
     * @param degrees
     */
    abstract public Image getRotated (int degrees);

    /**
     * Get a version of this image rotated by a specified number of degrees.
     * This version of the method takes in a double.
     * 
     * @param degrees
     */
    public Image getRotated (double degrees);

    /**
     * Get a version of this image rotated by a specified number of degrees.
     * 
     * @param degrees
     */
    abstract public Image getCenterRotated (int degrees);

    /**
     * Get a version of this image rotated by a specified number of degrees.
     * This version of the method takes in a double.
     * 
     * @param degrees
     */
    public Image getCenterRotated (double degrees);

    /**
     * get a uniformly scaled copy of the image.
     * 
     * @param factor
     * @return a new Image which is factor times as large as this one.
     */
    public Image getScaled (double factor);

    /**
     * get a non-uniformly scaled copy of the image.
     * 
     * @param xFactor
     * @param yFactor
     * @return a new Image scaled by xFactor in the x dimension and yFactor in the y dimension
     */
    public Image getScaled (double xFactor, double yFactor);

    /**
     * get a horizontally-reflected copy of the image.
     */
    public Image getXReflection ();
    
    /**
     *  get a vertically-reflected copy of the image.
     */
    public Image getYReflection ();

    /**
     * Overlay other images on this one, retaining their locations.
     * 
     * @param others    one or more images to overlay on this one.
     * @return a new Image
     */
    public abstract Image overlay (Image... others);
    
    /**
     * Overlay other images on this one, ignoring location of all but centering them.
     * 
     * @param others    one or more images to overlay on this one.
     * @return a new Image
     */
    public abstract Image overlayCentered (Image... others);

    /**
     * Overlay another image on this one, retaining locations and translating the other image.
     * 
     * @param front  the foreground image (to be translated)
     * @param dx     how much to move the foreground image horizontally
     * @param dy     how much to move the foreground image vertically
     * @return       a new Image
     */
    public Image overlayXY (Image front, int dx, int dy);
    
    /**
     * Place another image onto this one, retaining the location of this one but translating
     * the foreground so its center is at the specified location.  Crops to this image.
     * 
     * @param front    the foreground image (to be translated)
     * @param x        where to put the center of the foreground image
     * @param y        where to put the center of the foreground image
     * @return         a new Image
     */
    public Image place (Image front, int x, int y);
    /**
     * Concatenate two or more images vertically, retaining their x locations
     * but moving the others vertically so each one's top edge aligns with
     * the bottom of the previous one.
     * 
     * @param others    the images to concatenate below this, starting from the top
     */
    public abstract Image above (Image... others);
    
    /**
     * Concatenate two or more images vertically.
     * Ignores the translations of all images; matches the top edge of each
     * with the bottom edge of the previous one, and centers horizontally.
     * 
     * @param others     the images to concatenate below this, starting from the top
     * @return an Image formed by concatenating this above other
     */
    public abstract Image aboveCentered (Image... others);
    
    /**
     * Concatenate two or more images horizontally.
     * Ignores the translations of both images; matches the left edge of each
     * with the right edge of the previous one, and centers vertically.
     * 
     * @param others    the images to concatenate to the right of this, starting from the left
     * @return an Image formed by concatenating this to the left of the others
     */
    public abstract Image besideCentered (Image... others);
    
    /**
     * Concatenate two or more images horizontally, retaining their y locations
     * but moving the others horizontally so each one's left edge aligns with
     * the right edge of the previous one.
     */
    public abstract Image beside (Image... others);
    
    /**
     * get a new Image by cropping this one to a rectangular window.
     * Each pixel in the result is in the same location as the corresponding pixel
     * in the original was.
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return a new Image which is a rectangular portion of this one
     */
    public Image getCropped (int left, int right, int top, int bottom);
    
    /**
     * get an Image just like this one, but with a memoized raster rendering.
     * 
     * To be used as a "hint" for large, complex images that are likely to be
     * displayed many times before being modified.
     */
    public Image freeze();
    
    /**
     * Save an Image to a .png file.
     * 
     * @param filename
     */
    public boolean save(String filename);
}
