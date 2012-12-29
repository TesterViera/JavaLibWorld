package javalib.worldimages;
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
 * Most of this class is factory methods for various kinds of images.
 * In many case, a factory method has to be written six times, with different
 * combinations of Color and Mode arguments.  Sometimes Java really annoys me....
 * 
 * Convention: the "makeWhatever" methods in AImage are public, and there's a version for
 * each reasonable combination of missing parameters; these simply call the corresponding
 * "make" method in the appropriate class.  The "make" methods in each concrete
 * class are package-private, and there's a version for each reasonable combination of
 * missing parameters; these all call the constructor.  The constructor (in most cases)
 * is protected, and there's only one version.
 * 
 * @author Stephen Bloch
 * @version Dec. 26, 2012
 */
public abstract class AImage implements WorldImage
{  
    public void show ()
    {
        final int MINWIDTH = 50;
        final int MINHEIGHT = 50;
        final int MAXWIDTH = 1000;
        final int XPAD = 1;
        final int YPAD = 1;
        final int MAXHEIGHT = 800;
        int width = Math.min(MAXWIDTH, Math.max (MINWIDTH, this.getRight() + XPAD));
        int height = Math.min(MAXHEIGHT, Math.max (MINHEIGHT, this.getBottom() + YPAD));
        javalib.worldcanvas.WorldCanvas c = new javalib.worldcanvas.WorldCanvas (width, height);
        
        boolean drawn = c.drawImage (this) && c.show();
    }
    
   public Posn getCenter ()
   {
       int x = (this.getRight() + this.getLeft()) / 2;
       int y = (this.getTop() + this.getBottom()) / 2;
       return new Posn (x, y);
    }
    
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
    public static WorldImage makeRectangle (int width, int height, Color color, Mode mode)
    {
        return RectangleImage.make (width, height, color, mode);
    }
    public static WorldImage makeRectangle (int width, int height, IColor color, Mode mode)
    {
        return RectangleImage.make (width, height, color, mode);
    }
    public static WorldImage makeRectangle (int width, int height, Mode mode)
    {
        return RectangleImage.make (width, height, mode);
    }
    public static WorldImage makeRectangle (int width, int height, Color color)
    {
        return RectangleImage.make (width, height, color);
    }
    public static WorldImage makeRectangle (int width, int height, IColor color)
    {
        return RectangleImage.make (width, height, color);
    }
    public static WorldImage makeRectangle (int width, int height)
    {
        return RectangleImage.make (width, height);
    }
    
    public static WorldImage makeEllipse (int width, int height, Color color, Mode mode)
    {
        return EllipseImage.make (width, height, color, mode);
    }
    public static WorldImage makeEllipse (int width, int height, IColor color, Mode mode)
    {
        return EllipseImage.make (width, height, color, mode);
    }
    public static WorldImage makeEllipse (int width, int height, Mode mode)
    {
        return EllipseImage.make (width, height, mode);
    }
    public static WorldImage makeEllipse (int width, int height, Color color)
    {
        return EllipseImage.make (width, height, color);
    }
    public static WorldImage makeEllipse (int width, int height, IColor color)
    {
        return EllipseImage.make (width, height, color);
    }
    public static WorldImage makeEllipse (int width, int height)
    {
        return EllipseImage.make (width, height);
    }
    
    public static WorldImage makeCircle (int radius, Color color, Mode mode)
    {
        return CircleImage.make (radius, color, mode);
    }
    public static WorldImage makeCircle (int radius, IColor color, Mode mode)
    {
        return CircleImage.make (radius, color.thisColor(), mode);
    }
    public static WorldImage makeCircle (int radius, Mode mode)
    {
        return CircleImage.make (radius, Color.black, mode);
    }
    public static WorldImage makeCircle (int radius, Color color)
    {
        return CircleImage.make (radius, color, Mode.OUTLINED);
    }
    public static WorldImage makeCircle (int radius, IColor color)
    {
        return CircleImage.make (radius, color.thisColor(), Mode.OUTLINED);
    }
    public static WorldImage makeCircle (int radius)
    {
        return CircleImage.make (radius, Color.black, Mode.OUTLINED);
    }
    
    public static WorldImage makeCenteredCircle (Posn center, int radius, Color color, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, color, mode);
    }
    public static WorldImage makeCenteredCircle (Posn center, int radius, IColor color, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, color.thisColor(), mode);
    }
    public static WorldImage makeCenteredCircle (Posn center, int radius, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, Color.black, mode);
    }
    public static WorldImage makeCenteredCircle (Posn center, int radius, Color color)
    {
        return CircleImage.makeCentered (center, radius, color, Mode.OUTLINED);
    }
    public static WorldImage makeCenteredCircle (Posn center, int radius, IColor color)
    {
        return CircleImage.makeCentered (center, radius, color.thisColor(), Mode.OUTLINED);
    }
    public static WorldImage makeCenteredCircle (Posn center, int radius)
    {
        return CircleImage.makeCentered (center, radius, Color.black, Mode.OUTLINED);
    }
    
    public static WorldImage makeText (String text, float size, TextStyle style, Color color)
    {
        return TextImage.make (text, size, style, color);
    }
    public static WorldImage makeText (String text, float size, TextStyle style, IColor color)
    {
        return TextImage.make (text, size, style, color.thisColor());
    }
    public static WorldImage makeText (String text, float size, TextStyle style)
    {
        return TextImage.make (text, size, style, Color.black);
    }
    public static WorldImage makeText (String text, float size, Color color)
    {
        return TextImage.make (text, size, TextStyle.REGULAR, color);
    }
    public static WorldImage makeText (String text, float size, IColor color)
    {
        return TextImage.make (text, size, TextStyle.REGULAR, color.thisColor());
    }
    public static WorldImage makeText (String text, float size)
    {
        return TextImage.make (text, size, TextStyle.REGULAR, Color.black);
    }
    public static WorldImage makeText (String text, TextStyle style, Color color)
    {
        return TextImage.make (text, TextImage.defaultSize, style, color);
    }
    public static WorldImage makeText (String text, TextStyle style, IColor color)
    {
        return TextImage.make (text, TextImage.defaultSize, style, color.thisColor());
    }
    public static WorldImage makeText (String text, TextStyle style)
    {
        return TextImage.make (text, TextImage.defaultSize, style, Color.black);
    }
    public static WorldImage makeText (String text, Color color)
    {
        return TextImage.make (text, TextImage.defaultSize, TextStyle.REGULAR, color);
    }
    public static WorldImage makeText (String text, IColor color)
    {
        return TextImage.make (text, TextImage.defaultSize, TextStyle.REGULAR, color.thisColor());
    }
    public static WorldImage makeText (String text)
    {
        return TextImage.make (text, TextImage.defaultSize, TextStyle.REGULAR, Color.black);
    }
    
    
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, Color color, Mode mode)
    {
        return new PolygonImage (color, mode, p1, p2, p3);
    }    
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, IColor color, Mode mode)
    {
        return new PolygonImage (color.thisColor(), mode, p1, p2, p3);
    }
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, Mode mode)
    {
        return new PolygonImage (Color.black, mode, p1, p2, p3);
    }
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, Color color)
    {
        return new PolygonImage (color, Mode.OUTLINED, p1, p2, p3);
    }    
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, IColor color)
    {
        return new PolygonImage (color.thisColor(), Mode.OUTLINED, p1, p2, p3);
    }
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3)
    {
        return new PolygonImage (Color.black, Mode.OUTLINED, p1, p2, p3);
    }
    
    public static WorldImage makePolygon (Color color, Mode mode, Posn... points)
    {
        return PolygonImage.make (color, mode, points);
    }
    public static WorldImage makePolygon (IColor color, Mode mode, Posn... points)
    {
        return PolygonImage.make (color, mode, points);
    }
    public static WorldImage makePolygon (Mode mode, Posn... points)
    {
        return PolygonImage.make (mode, points);
    }
    public static WorldImage makePolygon (Color color, Posn... points)
    {
        return PolygonImage.make (color, points);
    }
    public static WorldImage makePolygon (IColor color, Posn... points)
    {
        return PolygonImage.make (color, points);
    }
    public static WorldImage makePolygon (Posn... points)
    {
        return PolygonImage.make (points);
    }
    
    
    public static WorldImage makeLine (Posn p1, Posn p2, Color color)
    {
        return new PolygonImage (color, Mode.OUTLINED, p1, p2);
    }
    public static WorldImage makeLine (Posn p1, Posn p2, IColor color)
    {
        return new PolygonImage (color.thisColor(), Mode.OUTLINED, p1, p2);
    }
    public static WorldImage makeLine (Posn p1, Posn p2)
    {
        return new PolygonImage (Color.black, Mode.OUTLINED, p1, p2);
    }
    
    public static WorldImage makeFromFile (String filename)
    {
        return FromFileImage.make (filename);
    }
    
    public static WorldImage makeFromURL (String urlString)
    {
        return FromURLImage.make (urlString);
    }
    
// Miscellaneous operations on images.
    
    public WorldImage moved(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return this;
        }
        else {
            return new LinearImage (AffineTransform.getTranslateInstance(dx, dy), this);
        }
    }
    
    public WorldImage moved (Posn dxdy) {
        return this.moved (dxdy.getX(), dxdy.getY());
    }
    
    public WorldImage centerMoved (int x, int y)
    {
        Posn center = this.getCenter();
        return this.moved(x-center.x, y-center.y);
    }
    
    public WorldImage centerMoved (Posn xy)
    {
        return this.moved(xy.minus(this.getCenter()));
    }
    
    /**
     * Get a version of this image translated to have its top-left corner at (0,0).
     */
    public WorldImage normalized ()
    {
        return this.moved(-this.getLeft(), -this.getTop());
    }

    public WorldImage rotated (int degrees)
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
    
    public WorldImage rotated (double degrees)
    {
        // since rotation is a double, don't bother with the exact comparisons
        // for multiples of 90
        return new LinearImage(AffineTransform.getRotateInstance(Math.PI * degrees / 180.0), this);
    }

    public WorldImage rotatedInPlace (int degrees)
    {
        int cx = (this.getLeft() + this.getRight())/2;
        int cy = (this.getTop() + this.getBottom())/2;
        
        return this.moved(-cx, -cy).rotated(degrees).moved(cx, cy);
    }
    
    public WorldImage rotatedInPlace (double degrees)
    {
        int cx = (this.getLeft() + this.getRight())/2;
        int cy = (this.getTop() + this.getBottom())/2;
        
        return this.moved(cx, cy).rotated(degrees).moved(-cx,-cy);
    }
    
    public WorldImage scaled (double factor)
    {
        return this.scaled (factor, factor);
    }

    public WorldImage scaled (double xFactor, double yFactor)
    {
        return new LinearImage (
            AffineTransform.getScaleInstance (xFactor, yFactor),
            this);
    }
    
    public WorldImage xReflected ()
    {
        int oldLeft = this.getLeft();
        int oldRight = this.getRight();
        // if we just scale it, new left edge will be at -oldRight,
        // so translate this back to where old left edge was.
        
        return this.scaled (-1.0, 1.0).moved(oldLeft + oldRight, 0);
    }
    
    public WorldImage yReflected ()
    {
        int oldTop = this.getTop();
        int oldBottom = this.getBottom();
        // if we just scale it, new top edge will be at -oldBottom,
        // so translate this back to where old top edge was.
        
        return this.scaled (1.0, -1.0).moved(0, oldTop + oldBottom);
    }
        
    public WorldImage overlay (WorldImage... others)
    {
        WorldImage result = this;
        for (WorldImage other : others)
        {
            result = OverlayImage.make (result, other);
        }
        return result;
    }
    
    /**
     * And for those who prefer a static function that overlays a bunch of images...
     * 
     * @param others
     * @return a new image, or null if there were none
     */
    public static WorldImage overlayImages (WorldImage... others)
    {
        WorldImage result = null;
        for (WorldImage other : others)
        {
            if (result == null)
            {
                result = other;
            }
            else
            {
                result = OverlayImage.make (result, other);
            }
        }
        return result;
    }
    
    public WorldImage overlayCentered (WorldImage... others)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        
        for (WorldImage other : others)
        {
            width = Math.max (width, other.getWidth());
            height = Math.max (height, other.getHeight());
        }

        WorldImage result = this.normalized().moved
            ((width-this.getWidth())/2, (height-this.getHeight())/2);

        for (WorldImage other : others)
        {
            result = OverlayImage.make (result,
                other.normalized().moved
                    ((width-other.getWidth())/2, (height-other.getHeight())/2));
        }
        return result;
    }
        
    public WorldImage overlayXY (WorldImage front, int dx, int dy)
    {
        return OverlayImage.make (this, front.moved (dx, dy));
    }
    
    public WorldImage place (WorldImage front, int x, int y)
    {
        int left = this.getLeft();
        int right = this.getRight();
        int top = this.getTop();
        int bottom = this.getBottom();
        
        int cx = (front.getRight() + front.getLeft())/2;
        int cy = (front.getTop() + front.getBottom())/2;
        
        return this.overlayXY(front, x-cx, y-cy).cropped (left, right, top, bottom);
    }

    public WorldImage above (WorldImage... others)
    {
        WorldImage result = this;
        for (WorldImage other : others)
        {
            result = OverlayImage.make (result,
                other.moved (0, result.getBottom() - other.getTop()));
        }
        return result;
    }
    
    public WorldImage aboveCentered (WorldImage... others)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        
        for (WorldImage other : others)
        {
            width = Math.max (width, other.getWidth());
            height = height + other.getHeight();
        }

        WorldImage result = this.normalized().moved((width-this.getWidth())/2, 0);
        
        for (WorldImage other : others)
        {
            result = OverlayImage.make (result,
                other.normalized().moved((width-other.getWidth())/2, result.getHeight()));
        }
        return result;
    }
    
    public WorldImage beside (WorldImage... others)
    {
        WorldImage result = this;
        for (WorldImage other : others)
        {
            result = OverlayImage.make (result,
                other.moved (result.getRight() - other.getLeft(), 0));
        }
        return result;
    }
        
    public WorldImage besideCentered (WorldImage... others)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        for (WorldImage other : others)
        {
            width = width + other.getWidth();
            height = Math.max (height, other.getHeight());
        }
        
        WorldImage result = this.normalized().moved(0, (height-this.getHeight())/2);
        for (WorldImage other : others)
        {
            result = OverlayImage.make (result,
                other.normalized().moved(result.getWidth(), (height-other.getHeight())/2));
        }
        return result;
    }
    

    public WorldImage cropped (int left, int right, int top, int bottom)
    {
        return Crop.make(this, left, right, top, bottom);
    }
    
    public WorldImage frozen ()
    {
        return FreezeImage.make (this);
    }
    
    public boolean save(String filename)
    {
        return this.frozen().save(filename);        
    }

    /**
     * Build a rectangular image pixel by pixel.
     *
     * @param width   the width in pixels of the desired image
     * @param height  the height in pixels of the desired image
     * @param builder an ImageBuilder specifying how to choose colors
     * @param extra   an arbitrary Object to be passed to each call of the builder
     * @since Dec. 27, 2012
     */
    public static WorldImage build (int width, int height, ImageBuilder b, Object extra)
    {
    return RasterImage.build (width, height, b, extra);
    }
    
    /**
     * Build a rectangular image pixel by pixel.  "extra" defaults to null.
     *
     * @param width   the width in pixels of the desired image
     * @param height  the height in pixels of the desired image
     * @param builder an ImageBuilder specifying how to choose colors
     * @since Dec. 27, 2012
     */
    public static WorldImage build (int width, int height, ImageBuilder b)
    {
    return build (width, height, b, null);
    }

    public WorldImage map (ImageMap map, Object extra)
    {
        return this.frozen().map (map, extra);
    }

    public WorldImage map (ImageMap map)
    {
        return this.map (map, null);
    }
    
    /**
     * Record whether or not we're in an applet.
     * 
     * @param flag
     */
    public static void isApplet (boolean flag)
    {
        FromFileImage.setIsApplet (flag);
    }

}
