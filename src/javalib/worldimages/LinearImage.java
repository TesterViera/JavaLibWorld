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
    
    /**
     * Pseudo-constructor for objects of class LinearImage.
     * 
     * <p>The main purpose of the pseudo-constructor is to collapse multiple successive linear
     * transforms into one, so we don't get a bunch of deeply-nested LinearImages.</p>
     * 
     * <p>This isn't just an optimization: it affects the calculation of the bounding box.
     * If two or more LinearImages are nested, the bounding box of the outer one is calculated
     * from the four corners of the bounding box of the inner one, which are in turn calculated
     * from the four corners of the bounding box of the inner one's base.  The visible effect of
     * this is that when you repeatedly rotate a single image, the accumulated errors cause it
     * to spiral out of control.  With LinearImages collapsed, the same could still happen
     * (e.g. if there were a Crop or an Overlay between each pair of LinearImages), but it's
     * much less likely.</p>
     * 
     * @param base     the image to transform
     * @param transform what to do to it
     */
    static WorldImage make (AffineTransform transform, WorldImage base)
    {
        if (base instanceof LinearImage) // avoid piling up nested LinearImages
        {
            LinearImage theBase = (LinearImage)base;
            AffineTransform temp = new AffineTransform(transform);
            temp.concatenate(theBase.transform);
            if (temp.isIdentity())
                return theBase.base;
            else
                return new LinearImage (temp, theBase.base);
        }
        else
        {
            return new LinearImage (transform, base);
        }
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
}
