package javalib.sbimages;
import java.awt.geom.AffineTransform;
import javalib.colors.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Implementation stuff that all images in my version should share.
 * 
 * All images have a bounding box, which you can get with getLeft(), getTop(),
 * getRight(), getBottom().  
 * 
 * @author Stephen Bloch
 * @version Dec. 2, 2012
 */
public abstract class AImage implements Image
{      
    /**
     * Getter for the translation field.
     * 
     * @return the translation of the given object
     * /
    public Posn getTranslation() {
        return Posn.origin; // default
    }
    
    /**
     * Getter for the x coordinate of the translation field (just for brevity).
     * 
     * @return this.translation.getX()
     * /
    public int getX() {
        return this.getTranslation().getX();
    }
    
    /**
     * Getter for the y coordinate of the translation field (just for brevity).
     * 
     * @return this.translation.getY()
     * /
    public int getY() {
        return this.getTranslation().getY();
    }
    */
    
   public int getWidth ()
   {
       return this.getRight() - this.getLeft();
    }
    
    public int getHeight ()
    {
        return this.getBottom() - this.getTop();
    }
    
    protected String cornerString ()
    {
        return "left=" + this.getLeft() + 
            ", top=" + this.getTop() +
            ", right=" + this.getRight() +
            ", bottom=" + this.getBottom();
    }
    
    public String toString () {
        return this.toIndentedString("");
    }
    
    /**
     * Is this the same class as some other object?
     * 
     * @param other
     * @return true iff they're exactly the same class
     */
    public boolean sameClass (Object other) {
        return this.getClass().equals(other.getClass());
    }
    
    /**
     * Getter for the top of the bounding box.
     * 
     * @return the minimum y coordinate of the image
     */
    public int getTop () {
        return 0; // default
    }
    
    /**
     * Getter for the left edge of the bounding box.
     * 
     * @return the minimum x coordinate of the image
     */
    public int getLeft () {
        return 0; // default
    }
        
    public boolean equals (Object other) {
        return this.sameClass(other);
    }
    
    protected static int rotate (int x, int bits) {
        return x << bits + x >>> (32-bits);
    }
    

// Functions to build primitive images
    public static Image makeRectangle (int width, int height, Color color, Mode mode)
    {
        return new RectangleImage (width, height, color, mode);
    }
    
    public static Image makeRectangle (int width, int height, IColor color, Mode mode)
    {
        return new RectangleImage (width, height, color.thisColor(), mode);
    }
    
    public static Image makeEllipse (int width, int height, Color color, Mode mode)
    {
        return new EllipseImage (width, height, color, mode);
    }
    
    public static Image makeEllipse (int width, int height, IColor color, Mode mode)
    {
        return new EllipseImage (width, height, color.thisColor(), mode);
    }
    
    public static Image makeCircle (int radius, Color color, Mode mode)
    {
        return new CircleImage (radius, color, mode);
    }
    
    public static Image makeCircle (int radius, IColor color, Mode mode)
    {
        return new CircleImage (radius, color.thisColor(), mode);
    }
    
    public static Image makeCenteredCircle (Posn center, int radius, Color color, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, color, mode);
    }
    
    public static Image makeCenteredCircle (Posn center, int radius, IColor color, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, color.thisColor(), mode);
    }
    
    public static Image makeText (String text, float size, TextStyle style, Color color)
    {
        return new TextImage (text, size, style, color);
    }
    
    public static Image makeText (String text, float size, TextStyle style, IColor color)
    {
        return new TextImage (text, size, style, color);
    }
    
    public static Image makeTriangle (Posn p1, Posn p2, Posn p3, Color color, Mode mode)
    {
        return new PolygonImage (color, mode, p1, p2, p3);
    }
    
    public static Image makeTriangle (Posn p1, Posn p2, Posn p3, IColor color, Mode mode)
    {
        return new PolygonImage (color.thisColor(), mode, p1, p2, p3);
    }
    
    public static Image makeLine (Posn p1, Posn p2, Color color)
    {
        return new PolygonImage (color, Mode.OUTLINED, p1, p2);
    }
    
    public static Image makeLine (Posn p1, Posn p2, IColor color)
    {
        return new PolygonImage (color.thisColor(), Mode.OUTLINED, p1, p2);
    }
    
    public static Image makeFromFile (String filename)
    {
        return FromFileImage.make (filename);
    }
    
    public static Image makeFromURL (String urlString)
    {
        return FromURLImage.make (urlString);
    }
    
// Miscellaneous operations on images.
    
    public Image getTranslated(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return this;
        }
        else {
            return new LinearImage (AffineTransform.getTranslateInstance(dx, dy), this);
        }
    }
    
    public Image getTranslated (Posn dxdy) {
        return this.getTranslated (dxdy.getX(), dxdy.getY());
    }
    
    /**
     * Get a version of this image translated to have its top-left corner at (0,0).
     */
    public Image getNormalized ()
    {
        return this.getTranslated(-this.getLeft(), -this.getTop());
    }

    public Image getRotated (int degrees)
    {
        while (degrees < 0)  // realistically, loop will almost never happen more than once.
        {
            degrees += 360;
        }
        while (degrees >= 360) // realistically, loop will almost never happen more than once.
        {
            degrees -= 360;
        }
        if (degrees == 0)
        {
            return this;
        }
        else if (degrees % 90 == 0)
        {
            return new LinearImage (
                AffineTransform.getQuadrantRotateInstance (degrees / 90),
                this);
        }
        else
        {
            return new LinearImage(AffineTransform.getRotateInstance(Math.PI * degrees / 180.0), this);
        }
    }
    
    public Image getRotated (double degrees)
    {
        // since rotation is a double, don't bother with the exact comparisons
        // for multiples of 90
        return new LinearImage(AffineTransform.getRotateInstance(Math.PI * degrees / 180.0), this);
    }

    public Image getCenterRotated (int degrees)
    {
        int cx = (this.getLeft() + this.getRight())/2;
        int cy = (this.getTop() + this.getBottom())/2;
        
        return this.getTranslated(-cx, -cy).getRotated(degrees).getTranslated(cx, cy);
    }
    
    public Image getCenterRotated (double degrees)
    {
        int cx = (this.getLeft() + this.getRight())/2;
        int cy = (this.getTop() + this.getBottom())/2;
        
        return this.getTranslated(cx, cy).getRotated(degrees).getTranslated(-cx,-cy);
    }
    
    public Image getScaled (double factor)
    {
        return this.getScaled (factor, factor);
    }

    public Image getScaled (double xFactor, double yFactor)
    {
        return new LinearImage (
            AffineTransform.getScaleInstance (xFactor, yFactor),
            this);
    }
    
    public Image getXReflection ()
    {
        int oldLeft = this.getLeft();
        int oldRight = this.getRight();
        // if we just scale it, new left edge will be at -oldRight,
        // so translate this back to where old left edge was.
        
        return this.getScaled (-1.0, 1.0).getTranslated(oldLeft + oldRight, 0);
    }
    
    public Image getYReflection ()
    {
        int oldTop = this.getTop();
        int oldBottom = this.getBottom();
        // if we just scale it, new top edge will be at -oldBottom,
        // so translate this back to where old top edge was.
        
        return this.getScaled (1.0, -1.0).getTranslated(0, oldTop + oldBottom);
    }
        
    public Image overlay (Image... others)
    {
        Image result = this;
        for (Image other : others)
        {
            result = new OverlayImage (result, other);
        }
        return result;
    }
    
    public Image overlayCentered (Image... others)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        
        for (Image other : others)
        {
            width = Math.max (width, other.getWidth());
            height = Math.max (height, other.getHeight());
        }

        Image result = this.getNormalized().getTranslated
            ((width-this.getWidth())/2, (height-this.getHeight())/2);

        for (Image other : others)
        {
            result = new OverlayImage (result,
                other.getNormalized().getTranslated
                    ((width-other.getWidth())/2, (height-other.getHeight())/2));
        }
        return result;
    }
        
    public Image overlayXY (Image front, int dx, int dy)
    {
        return new OverlayImage (this, front.getTranslated (dx, dy));
    }
    
    public Image place (Image front, int x, int y)
    {
        int left = this.getLeft();
        int right = this.getRight();
        int top = this.getTop();
        int bottom = this.getBottom();
        
        int cx = (front.getRight() + front.getLeft())/2;
        int cy = (front.getTop() + front.getBottom())/2;
        
        return this.overlayXY(front, x-cx, y-cy).getCropped (left, right, top, bottom);
    }

    public Image above (Image... others)
    {
        Image result = this;
        for (Image other : others)
        {
            result = new OverlayImage (result,
                other.getTranslated (0, other.getTop()-result.getBottom()));
        }
        return result;
    }
    
    public Image aboveCentered (Image... others)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        
        for (Image other : others)
        {
            width = Math.max (width, other.getWidth());
            height = height + other.getHeight();
        }

        Image result = this.getNormalized().getTranslated((width-this.getWidth())/2, 0);
        
        for (Image other : others)
        {
            result = new OverlayImage (result,
                other.getNormalized().getTranslated((width-other.getWidth())/2, result.getHeight()));
        }
        return result;
    }
    
    public Image beside (Image... others)
    {
        Image result = this;
        for (Image other : others)
        {
            result = new OverlayImage (result,
                other.getTranslated (result.getRight() - other.getLeft(), 0));
        }
        return result;
    }
        
    public Image besideCentered (Image... others)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        for (Image other : others)
        {
            width = width + other.getWidth();
            height = Math.max (height, other.getHeight());
        }
        
        Image result = this.getNormalized().getTranslated(0, (height-this.getHeight())/2);
        for (Image other : others)
        {
            result = new OverlayImage (result,
                other.getNormalized().getTranslated(result.getWidth(), (height-other.getHeight())/2));
        }
        return result;
    }
    

    public Image getCropped (int left, int right, int top, int bottom)
    {
        return Crop.make(this, left, right, top, bottom);
    }
    
    public Image freeze ()
    {
        return FreezeImage.make (this);
    }
    
    public boolean save(String filename)
    {
        return this.freeze().save(filename);        
    }
}
