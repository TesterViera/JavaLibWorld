package javalib.worldimages;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


/**
 * An image which is a translation of another existing image.
 * 
 * @author Stephen Bloch
 * @version Dec 23, 2012
 */
public class LinearImage extends AImage
{
    private WorldImage base;
    private AffineTransform transform;
    private int top, left, bottom, right;

    /**
     * Constructor for objects of class LinearImage
     * 
     * @param base    the image to transform
     * @param transform  what to do to it
     */
    LinearImage(AffineTransform transform, WorldImage base)
    {
        this.base = base;
        this.transform = transform;
        this.setBBox();
    }
    
    private void setBBox()
    {
        Point2D topLeft = new Point2D.Float (this.base.getLeft(), this.base.getTop());
        Point2D topRight = new Point2D.Float (this.base.getRight(), this.base.getTop());
        Point2D botLeft = new Point2D.Float (this.base.getLeft(), this.base.getBottom());
        Point2D botRight = new Point2D.Float (this.base.getRight(), this.base.getBottom());
        
        Point2D rtl = this.transform.transform(topLeft, null);
        Point2D rtr = this.transform.transform(topRight, null);
        Point2D rbl = this.transform.transform(botLeft, null);
        Point2D rbr = this.transform.transform(botRight, null);
        
        this.top = (int)Math.min(Math.min(rtl.getY(), rtr.getY()),
                            Math.min(rbl.getY(), rbr.getY()));
        this.left = (int)Math.min(Math.min(rtl.getX(), rtr.getX()),
                             Math.min(rbl.getX(), rbr.getX()));
        this.bottom = (int)Math.max(Math.max(rtl.getY(), rtr.getY()),
                               Math.max(rbl.getY(), rbr.getY()));
        this.right = (int)Math.max(Math.max(rtl.getX(), rtr.getX()),
                              Math.max(rbl.getX(), rbr.getX()));
    }

    public boolean equals (Object other)
    {
        if (super.equals (other))
        {
            LinearImage otherLI = (LinearImage)other;
            return this.base.equals(otherLI.base) &&
                   this.transform.equals(otherLI.transform);                   
        }
        else return false;
    }
    
    /**
     * Getter for the translation field.
     * 
     * @return the translation of the given object
     */
    public Posn getTranslation() {
        return new Posn (this.getX(), this.getY());
    }
    
    /**
     * Getter for the x coordinate of the translation field (just for brevity).
     * 
     * @return this.translation.getX()
     */
    public int getX() {
        return (int)Math.round(this.transform.getTranslateX());
    }
    
    /**
     * Getter for the y coordinate of the translation field (just for brevity).
     * 
     * @return this.translation.getY()
     */
    public int getY() {
        return (int)Math.round(this.transform.getTranslateY());
    }
    
    public int getTop ()
    {
        return this.top;
    }
    public int getLeft ()
    {
        return this.left;
    }
    public int getRight ()
    {
        return this.right;
    }
    public int getBottom ()
    {
        return this.bottom;
    }
    public int getWidth ()
    {
        return this.right - this.left;
    }
    public int getHeight ()
    {
        return this.bottom - this.top;
    }

    public void draw(java.awt.Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        
        g.transform (this.transform);
        this.base.draw (g);
        
        g.setTransform (oldTransform);
    }

    public String toIndentedString (String indent)
    {
        String newIndent = indent + "  ";
        return "new LinearImage(this.base = " + 
        "\n" + newIndent + this.base.toIndentedString(newIndent) +
        ",\n" + newIndent + "this.transform = " + this.transform.toString() +
        ",\n" + newIndent + this.cornerString() +
        ")";
    }
    
    /**
     * Get a translated copy of this image.
     * 
     * @param dx
     * @param dy
     * @return a new image just like this one but translated
     */
    public WorldImage getTranslated(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return this;
        }
        else {
            AffineTransform temp = AffineTransform.getTranslateInstance(dx,dy);
            temp.concatenate(this.transform);
            return new LinearImage (temp, this.base);
        }
    }
    
    /**
     * Get a version of this image rotated by a specified number of degrees.
     * 
     * @param degrees
     */
    public WorldImage getRotated (int degrees)
    {
        while (degrees < 0)
        {
            degrees += 360;
        }
        while (degrees >= 360)
        {
            degrees -= 360;
        }
        if (degrees == 0)
        {
            return this;
        }
        else if (degrees % 90 == 0)
        {
            AffineTransform temp = AffineTransform.getQuadrantRotateInstance(degrees / 90);
            temp.concatenate(this.transform);
            return new LinearImage (temp, this.base);
        }
        else
        {
            AffineTransform temp = AffineTransform.getRotateInstance (Math.PI * degrees / 180.0);
            temp.concatenate (this.transform);
            return new LinearImage(temp, this.base);
        }
    }
    
    /**
     * Get a version of this image rotated by a specified number of degrees.
     * This version of the method takes in a double, and doesn't bother checking
     * for exact multiples of 90 degrees.
     * 
     * @param degrees
     */
    public WorldImage getRotated (double degrees)
    {
            AffineTransform temp = AffineTransform.getRotateInstance (Math.PI * degrees / 180.0);
            temp.concatenate (this.transform);
            return new LinearImage(temp, this.base);
    }

    /**
     * get a non-uniformly scaled copy of the image.
     * 
     * @param xFactor
     * @param yFactor
     * @return a new WorldImage scaled by xFactor in the x dimension and yFactor in the y dimension
     */
    public WorldImage getScaled (double xFactor, double yFactor)
    {
        AffineTransform temp = AffineTransform.getScaleInstance (xFactor, yFactor);
        temp.concatenate (this.transform);
        return new LinearImage (temp, this.base);
    }
}
