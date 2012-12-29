package javalib.worldimages;


/**
 * The API for an image.
 * 
 * @author Stephen BLoch
 * @version Dec. 2, 2012
 */
public interface WorldImage 
{   
    /**
     * Display an image in a canvas.
     * 
     * @since Dec. 29, 2012
     */
    abstract public void show ();
    
    /**
     * Is this the same as another WorldImage?
     * 
     * Define this at every level that has instance variables.
     * 
     * @param other   the object to compare with this
     */
    abstract public boolean equals (Object other);
    
    /**
     * Get a hash code for the WorldImage.
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
     * Find the approximate center of this image.
     * 
     * @return a Posn
     */
    abstract public Posn getCenter();
    
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
    abstract public WorldImage moved (int dx, int dy);
    abstract public WorldImage moved (Posn dxdy);
    
    /**
     * Produce the image with its center translated to the specified (x,y)
     * Functional; does NOT modify this!
     * 
     * @param x  the new x coordinate of the center
     * @param y  the new y coordinate of the center
     * @since Dec. 29, 2012
     */
    abstract public WorldImage centerMoved (int x, int y);
    abstract public WorldImage centerMoved (Posn xy);

    /**
     * Produce a "normalized" version of this image, with top-left corner at (0,0).
     * 
     * @return an image just like this (in fact it may BE this) whose top-left corner is at (0,0).
     */
    abstract public WorldImage normalized();
    
    /**
     * Get a version of this image rotated by a specified number of degrees.
     * 
     * @param degrees
     */
    abstract public WorldImage rotated (int degrees);

    /**
     * Get a version of this image rotated by a specified number of degrees.
     * This version of the method takes in a double.
     * 
     * @param degrees
     */
    public WorldImage rotated (double degrees);

    /**
     * Get a version of this image rotated by a specified number of degrees.
     * 
     * @param degrees
     */
    abstract public WorldImage rotatedInPlace (int degrees);

    /**
     * Get a version of this image rotated by a specified number of degrees.
     * This version of the method takes in a double.
     * 
     * @param degrees
     */
    public WorldImage rotatedInPlace (double degrees);

    /**
     * get a uniformly scaled copy of the image.
     * 
     * @param factor
     * @return a new WorldImage which is factor times as large as this one.
     */
    public WorldImage scaled (double factor);

    /**
     * get a non-uniformly scaled copy of the image.
     * 
     * @param xFactor
     * @param yFactor
     * @return a new WorldImage scaled by xFactor in the x dimension and yFactor in the y dimension
     */
    public WorldImage scaled (double xFactor, double yFactor);

    /**
     * get a horizontally-reflected copy of the image.
     */
    public WorldImage xReflected ();
    
    /**
     *  get a vertically-reflected copy of the image.
     */
    public WorldImage yReflected ();

    /**
     * Overlay other images on this one, retaining their locations.
     * 
     * @param others    one or more images to overlay on this one.
     * @return a new WorldImage
     */
    public abstract WorldImage overlay (WorldImage... others);
    
    /**
     * Overlay other images on this one, ignoring location of all but centering them.
     * 
     * @param others    one or more images to overlay on this one.
     * @return a new WorldImage
     */
    public abstract WorldImage overlayCentered (WorldImage... others);

    /**
     * Overlay another image on this one, retaining locations and translating the other image.
     * 
     * @param front  the foreground image (to be translated)
     * @param dx     how much to move the foreground image horizontally
     * @param dy     how much to move the foreground image vertically
     * @return       a new WorldImage
     */
    public WorldImage overlayXY (WorldImage front, int dx, int dy);
    
    /**
     * Place another image onto this one, retaining the location of this one but translating
     * the foreground so its center is at the specified location.  Crops to this image.
     * 
     * @param front    the foreground image (to be translated)
     * @param x        where to put the center of the foreground image
     * @param y        where to put the center of the foreground image
     * @return         a new WorldImage
     */
    public WorldImage place (WorldImage front, int x, int y);
    /**
     * Concatenate two or more images vertically, retaining their x locations
     * but moving the others vertically so each one's top edge aligns with
     * the bottom of the previous one.
     * 
     * @param others    the images to concatenate below this, starting from the top
     */
    public abstract WorldImage above (WorldImage... others);
    
    /**
     * Concatenate two or more images vertically.
     * Ignores the translations of all images; matches the top edge of each
     * with the bottom edge of the previous one, and centers horizontally.
     * 
     * @param others     the images to concatenate below this, starting from the top
     * @return a WorldImage formed by concatenating this above the others
     */
    public abstract WorldImage aboveCentered (WorldImage... others);
    
    /**
     * Concatenate two or more images horizontally.
     * Ignores the translations of both images; matches the left edge of each
     * with the right edge of the previous one, and centers vertically.
     * 
     * @param others    the images to concatenate to the right of this, starting from the left
     * @return a WorldImage formed by concatenating this to the left of the others
     */
    public abstract WorldImage besideCentered (WorldImage... others);
    
    /**
     * Concatenate two or more images horizontally, retaining their y locations
     * but moving the others horizontally so each one's left edge aligns with
     * the right edge of the previous one.
     */
    public abstract WorldImage beside (WorldImage... others);
    
    /**
     * get a new WorldImage by cropping this one to a rectangular window.
     * Each pixel in the result is in the same location as the corresponding pixel
     * in the original was.
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return a new WorldImage which is a rectangular portion of this one
     */
    public WorldImage cropped (int left, int right, int top, int bottom);
    
    /**
     * get a WorldImage just like this one, but with a memoized raster rendering.
     * 
     * To be used as a "hint" for large, complex images that are likely to be
     * displayed many times before being modified.
     */
    public WorldImage frozen();
    
    /**
     * create a rectangular image pixel by pixel from an existing image.
     *
     * @param map    an ImageMap encapsulating a function from Color to
     *               Color
     * @param extra  an arbitrary addtional argument to pass to the function
     * @return a new image the same size and shape as this
     * @since  Dec. 28, 2012
     */
    public WorldImage map (ImageMap map, Object extra);
    
    /**
     * create a rectangular image pixel by pixel from an existing image.
     * In this entrypoint, the "extra" parameter defaults to null.
     *
     * @param map    an ImageMap encapsulating a function from Color to
     *               Color
     * @return a new image the same size and shape as this
     * @since  Dec. 28, 2012
     */
    public WorldImage map (ImageMap map);


    /**
     * Save a WorldImage to a .png file.
     * 
     * @param filename
     */
    public boolean save(String filename);

}
