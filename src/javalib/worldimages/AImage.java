package javalib.worldimages;
import java.awt.geom.AffineTransform;
import javalib.colors.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * The most important class in the <tt>worldimages</tt> package.
 * 
 * <p>Most of the methods you'll call on images live here: <code>makeCircle</code>,
 * <code>makeFromURL</code>, <code>above</code>, <code>scaled</code>, <code>rotated</code>,
 * <code>place</code>, <code>show</code>, <em>etc.</em></p>
 * 
 * <p>All images have a bounding box, which you can get with <code>getLeft()</code>,
 * <code>getTop()</code>, <code>getRight()</code>, <code>getBottom()</code>.</p>
 * 
 * <p>Most of this class is factory methods for various kinds of images.
 * In many case, a factory method has to be written six times, with different
 * combinations of Color and Mode arguments.  Sometimes Java really annoys me....</p>
 * 
 * <p>Convention: the "makeWhatever" methods in AImage are public, and there's a version for
 * each reasonable combination of missing parameters; these simply call the corresponding
 * "make" method in the appropriate class.  The "make" methods in each concrete
 * class are package-private, and there's a version for each reasonable combination of
 * missing parameters; these all call the constructor.  The constructor (in most cases)
 * is protected, and there's only one version.</p>
 * 
 * @author Stephen Bloch
 * @version Dec. 26, 2012
 */
public abstract class AImage implements WorldImage
{  
    /**
     * Display the image in a new window by itself.
     * 
     * Primarily for debugging purposes.
     */
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
    
   /**
    * Retrieve the coordinates of the center of the image.
    * 
    * @return a Posn indicating the center of the bounding box.
    */
   public Posn getCenter ()
   {
       int x = (this.getRight() + this.getLeft()) / 2;
       int y = (this.getTop() + this.getBottom()) / 2;
       return new Posn (x, y);
    }
    
   /**
    * Retrieve the width of the image, in pixels.
    * 
    * @return an int
    */
   public int getWidth ()
   {
       return this.getRight() - this.getLeft();
    }
    
    /**
     * Retrieve the height of the image, in pixels.
     * 
     * @return an int
     */
    public int getHeight ()
    {
        return this.getBottom() - this.getTop();
    }
    
    /**
     * For debugging purposes; shows the coordinates of the bounding box of the image.
     */
    protected String cornerString ()
    {
        return "left=" + this.getLeft() + 
            ", top=" + this.getTop() +
            ", right=" + this.getRight() +
            ", bottom=" + this.getBottom();
    }
    
    /**
     * Convert the image to String form, for debugging purposes.
     */
    public String toString () {
        return this.toIndentedString("");
    }
    
    public WorldImage makeImage ()
    {
        return this;
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
    
    /**
     * Do two WorldImages appear the same?
     * 
     * If they're equal as expression trees, they certainly appear the same.
     * Failing that, render them both and compare the pixel maps.
     */
    public boolean same (WorldImage other)
    {
        return this.equals(other) ||
               WorldImage.LOOKS_SAME.equivalent(this, other);
    }

    /**
     * A utility function for producing hash codes.
     * 
     * @param x   a hashCode for some field
     * @param bits how many bits to rotate it within a 32-bit int
     * @return x rotated left by the specified number of bits (wrapping around to the low bits)
     */
    protected static int rotate (int x, int bits) {
        return x << bits + x >>> (32-bits);
    }
    

// Functions to build primitive images
    /**
     * Produce a rectangle.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     * @param mode     either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeRectangle (int width, int height, Color color, Mode mode)
    {
        return RectangleImage.make (width, height, color, mode);
    }
    /**
     * Produce a rectangle.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     * @param mode     either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeRectangle (int width, int height, IColor color, Mode mode)
    {
        return RectangleImage.make (width, height, color, mode);
    }
    /**
     * Produce a black rectangle.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param mode     either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeRectangle (int width, int height, Mode mode)
    {
        return RectangleImage.make (width, height, mode);
    }
    /**
     * Produce an outlined rectangle.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     */
    public static WorldImage makeRectangle (int width, int height, Color color)
    {
        return RectangleImage.make (width, height, color);
    }
    /**
     * Produce an outlined rectangle.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     */
    public static WorldImage makeRectangle (int width, int height, IColor color)
    {
        return RectangleImage.make (width, height, color);
    }
    /**
     * Produce an outlined black rectangle.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     */
    public static WorldImage makeRectangle (int width, int height)
    {
        return RectangleImage.make (width, height);
    }
    
    /**
     * Produce an ellipse.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     * @param mode     either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeEllipse (int width, int height, Color color, Mode mode)
    {
        return EllipseImage.make (width, height, color, mode);
    }
    /**
     * Produce an ellipse.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     * @param mode     either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeEllipse (int width, int height, IColor color, Mode mode)
    {
        return EllipseImage.make (width, height, color, mode);
    }
    /**
     * Produce a black ellipse.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param mode     either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeEllipse (int width, int height, Mode mode)
    {
        return EllipseImage.make (width, height, mode);
    }
    /**
     * Produce an outlined ellipse.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     */
    public static WorldImage makeEllipse (int width, int height, Color color)
    {
        return EllipseImage.make (width, height, color);
    }
    /**
     * Produce an outlined ellipse.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     * @param color    the color
     */
    public static WorldImage makeEllipse (int width, int height, IColor color)
    {
        return EllipseImage.make (width, height, color);
    }
    /**
     * Produce an outlined black ellipse.
     * 
     * @param width    the width in pixels
     * @param height   the height in pixels
     */
    public static WorldImage makeEllipse (int width, int height)
    {
        return EllipseImage.make (width, height);
    }
    
    /**
     * Produce a circle.
     * 
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     * @param mode      either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeCircle (int radius, Color color, Mode mode)
    {
        return CircleImage.make (radius, color, mode);
    }
    /**
     * Produce a circle.
     * 
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     * @param mode      either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeCircle (int radius, IColor color, Mode mode)
    {
        return CircleImage.make (radius, color.thisColor(), mode);
    }
    /**
     * Produce a black circle.
     * 
     * @param radius    the radius of the circle
     * @param mode      either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeCircle (int radius, Mode mode)
    {
        return CircleImage.make (radius, Color.black, mode);
    }
    /**
     * Produce an outlined circle.
     * 
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     */
    public static WorldImage makeCircle (int radius, Color color)
    {
        return CircleImage.make (radius, color, Mode.OUTLINED);
    }
    /**
     * Produce an outlined circle.
     * 
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     */
    public static WorldImage makeCircle (int radius, IColor color)
    {
        return CircleImage.make (radius, color.thisColor(), Mode.OUTLINED);
    }
    /**
     * Produce an outlined black circle.
     * 
     * @param radius    the radius of the circle
     */
    public static WorldImage makeCircle (int radius)
    {
        return CircleImage.make (radius, Color.black, Mode.OUTLINED);
    }
    
    /**
     * Produce a circle centered at a specified location.
     * 
     * @param center    the coordinates of the center
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     * @param mode      either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeCenteredCircle (Posn center, int radius, Color color, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, color, mode);
    }
    /**
     * Produce a circle centered at a specified location.
     * 
     * @param center    the coordinates of the center
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     * @param mode      either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeCenteredCircle (Posn center, int radius, IColor color, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, color.thisColor(), mode);
    }
    /**
     * Produce a black circle centered at a specified location.
     * 
     * @param center    the coordinates of the center
     * @param radius    the radius of the circle
     * @param mode      either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeCenteredCircle (Posn center, int radius, Mode mode)
    {
        return CircleImage.makeCentered (center, radius, Color.black, mode);
    }
    /**
     * Produce an outlined circle centered at a specified location.
     * 
     * @param center    the coordinates of the center
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     */
    public static WorldImage makeCenteredCircle (Posn center, int radius, Color color)
    {
        return CircleImage.makeCentered (center, radius, color, Mode.OUTLINED);
    }
    /**
     * Produce an outlined circle centered at a specified location.
     * 
     * @param center    the coordinates of the center
     * @param radius    the radius of the circle
     * @param color     the color of the circle
     */
    public static WorldImage makeCenteredCircle (Posn center, int radius, IColor color)
    {
        return CircleImage.makeCentered (center, radius, color.thisColor(), Mode.OUTLINED);
    }
    /**
     * Produce an outlined black circle centered at a specified location.
     * 
     * @param center    the coordinates of the center
     * @param radius    the radius of the circle
     */
    public static WorldImage makeCenteredCircle (Posn center, int radius)
    {
        return CircleImage.makeCentered (center, radius, Color.black, Mode.OUTLINED);
    }

    /**
     * Produce an image of some text.
     * 
     * @param text    the String to convert into an image
     * @param size    the size in points (<em>e.g.</em> 12.0 is 12-point font)
     * @param style   either <tt>TextStyle.NORMAL</tt> (the default, also known as
     *                <tt>TextStyle.REGULAR</tt>), <tt>TextStyle.BOLD</tt>, <tt>TextStyle,ITALIC</tt>,
     *                or <tt>TextStyle.BOLD_ITALIC</tt> (also known as <tt>TextStyle.ITALIC_BOLD</tt>)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, float size, TextStyle style, Color color)
    {
        return TextImage.make (text, size, style, color);
    }
    /**
     * Produce an image of some text.
     * 
     * @param text    the String to convert into an image
     * @param size    the size in points (<em>e.g.</em> 12.0 is 12-point font)
     * @param style   either <tt>TextStyle.NORMAL</tt> (the default, also known as
     *                <tt>TextStyle.REGULAR</tt>), <tt>TextStyle.BOLD</tt>, <tt>TextStyle,ITALIC</tt>,
     *                or <tt>TextStyle.BOLD_ITALIC</tt> (also known as <tt>TextStyle.ITALIC_BOLD</tt>)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, float size, TextStyle style, IColor color)
    {
        return TextImage.make (text, size, style, color.thisColor());
    }
    /**
     * Produce an image of some text, defaulting to black.
     * 
     * @param text    the String to convert into an image
     * @param size    the size in points (<em>e.g.</em> 12.0 is 12-point font)
     * @param style   either <tt>TextStyle.NORMAL</tt> (the default, also known as
     *                <tt>TextStyle.REGULAR</tt>), <tt>TextStyle.BOLD</tt>, <tt>TextStyle,ITALIC</tt>,
     *                or <tt>TextStyle.BOLD_ITALIC</tt> (also known as <tt>TextStyle.ITALIC_BOLD</tt>)
     */
    public static WorldImage makeText (String text, float size, TextStyle style)
    {
        return TextImage.make (text, size, style, Color.black);
    }
    /**
     * Produce an image of some text, defaulting to normal style.
     * 
     * @param text    the String to convert into an image
     * @param size    the size in points (<em>e.g.</em> 12.0 is 12-point font)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, float size, Color color)
    {
        return TextImage.make (text, size, TextStyle.REGULAR, color);
    }
    /**
     * Produce an image of some text, defaulting to normal style.
     * 
     * @param text    the String to convert into an image
     * @param size    the size in points (<em>e.g.</em> 12.0 is 12-point font)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, float size, IColor color)
    {
        return TextImage.make (text, size, TextStyle.REGULAR, color.thisColor());
    }
    /**
     * Produce an image of some text, defaulting to normal style and black.
     * 
     * @param text    the String to convert into an image
     * @param size    the size in points (<em>e.g.</em> 12.0 is 12-point font)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, float size)
    {
        return TextImage.make (text, size, TextStyle.REGULAR, Color.black);
    }
    /**
     * Produce an image of some text, defaulting to 12-point.
     * 
     * @param text    the String to convert into an image
     * @param style   either <tt>TextStyle.NORMAL</tt> (the default, also known as
     *                <tt>TextStyle.REGULAR</tt>), <tt>TextStyle.BOLD</tt>, <tt>TextStyle,ITALIC</tt>,
     *                or <tt>TextStyle.BOLD_ITALIC</tt> (also known as <tt>TextStyle.ITALIC_BOLD</tt>)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, TextStyle style, Color color)
    {
        return TextImage.make (text, TextImage.defaultSize, style, color);
    }
    /**
     * Produce an image of some text, defgaulting to 12-point.
     * 
     * @param text    the String to convert into an image
     * @param style   either <tt>TextStyle.NORMAL</tt> (the default, also known as
     *                <tt>TextStyle.REGULAR</tt>), <tt>TextStyle.BOLD</tt>, <tt>TextStyle,ITALIC</tt>,
     *                or <tt>TextStyle.BOLD_ITALIC</tt> (also known as <tt>TextStyle.ITALIC_BOLD</tt>)
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, TextStyle style, IColor color)
    {
        return TextImage.make (text, TextImage.defaultSize, style, color.thisColor());
    }
    /**
     * Produce an image of some text, defaulting to 12-point black.
     * 
     * @param text    the String to convert into an image
     * @param style   either <tt>TextStyle.NORMAL</tt> (the default, also known as
     *                <tt>TextStyle.REGULAR</tt>), <tt>TextStyle.BOLD</tt>, <tt>TextStyle,ITALIC</tt>,
     *                or <tt>TextStyle.BOLD_ITALIC</tt> (also known as <tt>TextStyle.ITALIC_BOLD</tt>)
     */
    public static WorldImage makeText (String text, TextStyle style)
    {
        return TextImage.make (text, TextImage.defaultSize, style, Color.black);
    }
    /**
     * Produce an image of some text, defaulting to regular 12-point.
     * 
     * @param text    the String to convert into an image
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, Color color)
    {
        return TextImage.make (text, TextImage.defaultSize, TextStyle.REGULAR, color);
    }
    /**
     * Produce an image of some text, defaulting to regular 12-point.
     * 
     * @param text    the String to convert into an image
     * @param color   the color of the text (default black)
     */
    public static WorldImage makeText (String text, IColor color)
    {
        return TextImage.make (text, TextImage.defaultSize, TextStyle.REGULAR, color.thisColor());
    }
    /**
     * Produce an image of some text, defaulting to regular, 12-point, black.
     * 
     * @param text    the String to convert into an image
     */
    public static WorldImage makeText (String text)
    {
        return TextImage.make (text, TextImage.defaultSize, TextStyle.REGULAR, Color.black);
    }
    
    
    /**
     * Produce a triangle with specified vertices.
     * 
     * @param p1   the coordinates of one vertex
     * @param p2   the coordinates of another vertex
     * @param p3   the coordinates of the third vertex
     * @param color the color of the triangle
     * @param mode either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, Color color, Mode mode)
    {
        return new PolygonImage (color, mode, p1, p2, p3);
    }    
    /**
     * Produce a triangle with specified vertices.
     * 
     * @param p1   the coordinates of one vertex
     * @param p2   the coordinates of another vertex
     * @param p3   the coordinates of the third vertex
     * @param color the color of the triangle
     * @param mode either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, IColor color, Mode mode)
    {
        return new PolygonImage (color.thisColor(), mode, p1, p2, p3);
    }
    /**
     * Produce a blacktriangle with specified vertices.
     * 
     * @param p1   the coordinates of one vertex
     * @param p2   the coordinates of another vertex
     * @param p3   the coordinates of the third vertex
     * @param mode either Mode.FILLED or Mode.OUTLINED
     */
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, Mode mode)
    {
        return new PolygonImage (Color.black, mode, p1, p2, p3);
    }
    /**
     * Produce an outlined triangle with specified vertices.
     * 
     * @param p1   the coordinates of one vertex
     * @param p2   the coordinates of another vertex
     * @param p3   the coordinates of the third vertex
     * @param color the color of the triangle
     */
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, Color color)
    {
        return new PolygonImage (color, Mode.OUTLINED, p1, p2, p3);
    }    
    /**
     * Produce an outlined triangle with specified vertices.
     * 
     * @param p1   the coordinates of one vertex
     * @param p2   the coordinates of another vertex
     * @param p3   the coordinates of the third vertex
     * @param color the color of the triangle
     */
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3, IColor color)
    {
        return new PolygonImage (color.thisColor(), Mode.OUTLINED, p1, p2, p3);
    }
    /**
     * Produce an outlined black triangle with specified vertices.
     * 
     * @param p1   the coordinates of one vertex
     * @param p2   the coordinates of another vertex
     * @param p3   the coordinates of the third vertex
     */
    public static WorldImage makeTriangle (Posn p1, Posn p2, Posn p3)
    {
        return new PolygonImage (Color.black, Mode.OUTLINED, p1, p2, p3);
    }

    /**
     * Produce a polygon with specified vertices.
     * 
     * @param color   the color of the polygon
     * @param mode    either Mode.FILLED or Mode.OUTLINED
     * @param points  one or more <tt>Posn</tt>s to indicate vertices
     */
    public static WorldImage makePolygon (Color color, Mode mode, Posn... points)
    {
        return PolygonImage.make (color, mode, points);
    }
    /**
     * Produce a polygon with specified vertices.
     * 
     * @param color   the color of the polygon
     * @param mode    either Mode.FILLED or Mode.OUTLINED
     * @param points  one or more <tt>Posn</tt>s to indicate vertices
     */
    public static WorldImage makePolygon (IColor color, Mode mode, Posn... points)
    {
        return PolygonImage.make (color, mode, points);
    }
    /**
     * Produce a black polygon with specified vertices.
     * 
     * @param mode    either Mode.FILLED or Mode.OUTLINED
     * @param points  one or more <tt>Posn</tt>s to indicate vertices
     */
    public static WorldImage makePolygon (Mode mode, Posn... points)
    {
        return PolygonImage.make (mode, points);
    }
    /**
     * Produce an outlined polygon with specified vertices.
     * 
     * @param color   the color of the polygon
     * @param points  one or more <tt>Posn</tt>s to indicate vertices
     */
    public static WorldImage makePolygon (Color color, Posn... points)
    {
        return PolygonImage.make (color, points);
    }
    /**
     * Produce an outlined polygon with specified vertices.
     * 
     * @param color   the color of the polygon
     * @param points  one or more <tt>Posn</tt>s to indicate vertices
     */
    public static WorldImage makePolygon (IColor color, Posn... points)
    {
        return PolygonImage.make (color, points);
    }
    /**
     * Produce an outlined black polygon with specified vertices.
     * 
     * @param points  one or more <tt>Posn</tt>s to indicate vertices
     */
    public static WorldImage makePolygon (Posn... points)
    {
        return PolygonImage.make (points);
    }
    

    /**
     * Produce a line segment with specified endpoints.
     * 
     * @param p1     the coordinates of one endpoint
     * @param p2     the coordinates of the other endpoint
     * @param color  the color of the line segment
     */
    public static WorldImage makeLine (Posn p1, Posn p2, Color color)
    {
        return new PolygonImage (color, Mode.OUTLINED, p1, p2);
    }
    /**
     * Produce a line segment with specified endpoints.
     * 
     * @param p1     the coordinates of one endpoint
     * @param p2     the coordinates of the other endpoint
     * @param color  the color of the line segment
     */
    public static WorldImage makeLine (Posn p1, Posn p2, IColor color)
    {
        return new PolygonImage (color.thisColor(), Mode.OUTLINED, p1, p2);
    }
    /**
     * Produce a black line segment with specified endpoints.
     * 
     * @param p1     the coordinates of one endpoint
     * @param p2     the coordinates of the other endpoint
     */
    public static WorldImage makeLine (Posn p1, Posn p2)
    {
        return new PolygonImage (Color.black, Mode.OUTLINED, p1, p2);
    }
    
    /**
     * Produce an image from a disk file (<em>e.g.</em> PNG, GIF, JPG, <em>etc.</em>)
     * 
     * @param filename    the name of the disk file, interpreted relative to the project directory.
     */
    public static WorldImage makeFromFile (String filename)
    {
        return FromFileImage.make (filename);
    }
    
    /**
     * Produce an image from a URL (<em>e.g.</em> "copy image location" in a Web browser)
     * 
     * @param urlString     the URL, written as a string
     */
    public static WorldImage makeFromURL (String urlString)
    {
        return FromURLImage.make (urlString);
    }
    
// Miscellaneous operations on images.
    
    /**
     * Produce a translated copy of this image.
     * 
     * @param dx    how far to move to the right (or left, if dx is negative)
     * @param dy    how far to move down (or up, if dy is negative)
     */
    public WorldImage moved(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return this;
        }
        else {
            return LinearImage.make (AffineTransform.getTranslateInstance(dx, dy), this);
        }
    }
    
    /**
     * Produce a translated copy of this image.
     * 
     * @param dxdy    a Posn indicating how far to move
     */
    public WorldImage moved (Posn dxdy) {
        return this.moved (dxdy.getX(), dxdy.getY());
    }
    
    /**
     * Produce a copy of this image, with its center moved to the specified coordinates.
     * 
     * @param x     where the center of the image should be after translation
     * @param y     where the center of the image should be after translation
     */
    public WorldImage centerMoved (int x, int y)
    {
        Posn center = this.getCenter();
        return this.moved(x-center.x, y-center.y);
    }
    
    /**
     * Produce a copy of this image, with its center moved to the specified coordinates.
     * 
     * @param xy     where the center of the image should be after translation
     */
    public WorldImage centerMoved (Posn xy)
    {
        return this.moved(xy.minus(this.getCenter()));
    }
    
    /**
     * Produce a copy of this image translated to have its top-left corner at (0,0).
     */
    public WorldImage normalized ()
    {
        return this.moved(-this.getLeft(), -this.getTop());
    }

    private int normalizeDegrees (int degrees)
    {
        int answer = degrees;
        while (answer < 0)
        {
            answer += 360;
        }
        while (answer >= 360)
        {
            answer -= 360;
        }
        return answer;
    }
    
    /**
     * Produce a copy of this image rotated by the specified number of degrees around the origin.
     * 
     * @param degrees   how many degrees to rotate, counter-clockwise (I think!)
     */
    public WorldImage rotated (int degrees) // overridden in LinearImage
    {
        degrees = normalizeDegrees (degrees);
        if (degrees == 0)
        {
            return this;
        }
        else if (degrees % 90 == 0)
        {
            return LinearImage.make (
                AffineTransform.getQuadrantRotateInstance (degrees / 90),
                this);
        }
        else
        {
            return LinearImage.make (
                AffineTransform.getRotateInstance(Math.PI * degrees / 180.0),
                this);
        }
    }
    
    /**
     * Produce a copy of this image rotated by the specified number of degrees around the origin.
     * 
     * @param degrees   how many degrees to rotate, counter-clockwise (I think!)
     */
    public WorldImage rotated (double degrees) // overridden in LinearImage
    {
        // since rotation is a double, don't bother with the exact comparisons
        // for multiples of 90
        return LinearImage.make (
            AffineTransform.getRotateInstance(Math.PI * degrees / 180.0),
            this);
    }

    /**
     * Produce a copy of this image rotated by the specified number of degrees around the specified <tt>Posn</tt>.
     * 
     * @param degrees   how many degrees to rotate, counter-clockwise (I think!)
     * @param anchor    the <tt>Posn</tt> to rotate around
     */
    public WorldImage rotatedAround (int degrees, Posn anchor) // overridden in LinearImage
    {
        degrees = normalizeDegrees (degrees);
        if (degrees == 0)
        {
            return this;
        }
        else if (degrees % 90 == 0)
        {
            return LinearImage.make (
                AffineTransform.getQuadrantRotateInstance (degrees / 90, anchor.x, anchor.y),
                this);
        }
        else
        {
            return LinearImage.make (
                AffineTransform.getRotateInstance(Math.PI * degrees / 180.0, anchor.x, anchor.y),
                this);
        }
    }

    /**
     * Produce a copy of this image rotated by the specified number of degrees around the specified <tt>Posn</tt>.
     * 
     * @param degrees   how many degrees to rotate, counter-clockwise (I think!)
     * @param anchor    the <tt>Posn</tt> to rotate around
     */
    public WorldImage rotatedAround (double degrees, Posn anchor)
    {
        return LinearImage.make (
            AffineTransform.getRotateInstance(Math.PI * degrees / 180.0,
                                              anchor.x, anchor.y),
            this);
    }
    
    /**
     * Produce a copy of this image rotated by the specified number of degrees around its center.
     * 
     * @param degrees   how many degrees to rotate, counter-clockwise (I think!)
     */
    public WorldImage rotatedInPlace (int degrees)
    {
        return this.rotatedAround (degrees, this.getCenter());
    }
    
    /**
     * Produce a copy of this image rotated by the specified number of degrees around its center.
     * 
     * @param degrees   how many degrees to rotate, counter-clockwise (I think!)
     */
    public WorldImage rotatedInPlace (double degrees)
    {
        return this.rotatedAround (degrees, this.getCenter());
    }
    
    /**
     * Produce a copy of this image scaled by the specified factor.
     * 
     * @param factor    the scaling factor (<em>e.g.</em> 2.0 means it doubles in size)
     */
    public WorldImage scaled (double factor)
    {
        return this.scaled (factor, factor);
    }

    /**
     * Produce a copy of this image scaled differently in x and y dimensions.
     * 
     * @param xFactor    the scaling factor in the x dimension
     * @param yFactor    the scaling factor in the y dimension
     */
    public WorldImage scaled (double xFactor, double yFactor)
    {
        return LinearImage.make (
            AffineTransform.getScaleInstance (xFactor, yFactor),
            this);
    }
    
    /**
     * Produce a copy of this image reflected left to right, in place.
     */
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
     * Produce an image by overlaying a bunch of existing images.
     * 
     * @param others     two or more images to be overlaid
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
    
    public RasterImage frozen ()
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
    public static WorldImage build (int width, int height, ImageBuilder builder, Object extra)
    {
    return RasterImage.build (width, height, builder, extra);
    }
    
    /**
     * Build a rectangular image pixel by pixel, with no "extra" information.
     *
     * @param width   the width in pixels of the desired image
     * @param height  the height in pixels of the desired image
     * @param builder an ImageBuilder specifying how to choose colors
     * @since Dec. 27, 2012
     */
    public static WorldImage build (int width, int height, ImageBuilder builder)
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
