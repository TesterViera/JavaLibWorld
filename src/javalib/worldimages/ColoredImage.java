package javalib.worldimages;
import javalib.colors.*;
import java.awt.Color;


/**
 * Any image that explicitly stores a color and a mode.
 * 
 * @author Stephen Bloch
 * @version Dec. 24, 2012
 */
abstract class ColoredImage extends AImage
{
    private Color color;
    private Mode mode;
    public static final Color defaultColor = new Color (0,0,0);

    /**
     * The full constructor
     * 
     * @param color
     */
    ColoredImage (Color color, Mode mode) {
        super();
        this.color = correctColor (color);
        this.mode = mode;
    }
    
    /**
     * Another constructor that takes in an <code>{@link IColor}IColor</code>.
     * 
     * @param color
     */
    ColoredImage (IColor color, Mode mode) {
        this(color.thisColor(), mode);
    }
    
    /**
     * Another constructor that takes no color.
     * 
     */
    ColoredImage (Mode mode) {
        this(defaultColor, mode);
    }
    
    /**
     * get the color of an image
     * 
     * @return an Color
     */
    public Color getColor () { return this.color; }
    
    public Mode getMode () { return this.mode; }
    
    /**
     * Functional setter for the color field
     * 
     * Define this in all concrete subclasses.  (I HATE this!  Every one of these definitions
     * will consist of calling the constructor with exactly the same fields but newColor plugged
     * in as the color.  Why do I have to write that over and over in every concrete subclass?)
     * 
     * @param newColor
     * @return a new image just like this one but with a different color
     */
    abstract ColoredImage replaceColor (Color newColor);
    
    ColoredImage replaceColor (IColor newColor) {
        return this.replaceColor (newColor.thisColor());
    }
    
    /**
     * Functional setter for the mode field
     * 
     * Define this in all concrete subclasses.
     * 
     * @param newMode
     * @return a new image just like this one but with a different mode
     */
    abstract ColoredImage replaceMode (Mode newMode);
    
    public boolean equals (Object other) {
        return super.equals(other) &&
               this.getColor().equals(((ColoredImage)other).getColor()) &&
               this.getMode().equals(((ColoredImage)other).getMode());
        }
    
    public int hashCode () {
        return super.hashCode() + this.color.hashCode() + this.mode.hashCode();
    }
    
    /**
     * correct null colors to the default
     * 
     * @param c    a Color, possibly null
     * @return     a Color, not null
     */
    private static Color correctColor (Color c) {
        if (c == null) {
            return defaultColor;
        }
        else {
            return c;
        }
    }
}
