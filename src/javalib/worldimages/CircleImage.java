package javalib.worldimages;

import javalib.colors.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * An outlined circle
 * 
 * @author Stephen Bloch
 * @version Dec. 2, 2012
 */
class CircleImage extends EllipseImage
{    
    private int radius;
    
    /**
     * The full constructor for a CircleImage.
     * 
     * @param radius
     * @param color
     * @param mode
     */
    private CircleImage (int radius, Color color, Mode mode) {
        super (2*radius, 2*radius, color, mode);
        this.radius = radius;
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param radius
     * @param color
     * @param mode
     */
    static CircleImage make (int radius, Color color, Mode mode)
    {
        return new CircleImage (radius, color, mode);
    }
    
   /**
     * Pseudo-constructor.
     */
    static CircleImage make (int radius, IColor color, Mode mode)
    {
        return new CircleImage (radius, color.thisColor(), mode);
    }
    
    /**
     * Another pseudo-constructor, with a default color.
     */
    static CircleImage make (int radius, Mode mode)
    {
        return new CircleImage (radius, Color.black, mode);
    }
    
    /**
     * Another pseudo-constructor, with a Color and a default mode.
     */
    static CircleImage make (int radius, Color color)
    {
        return new CircleImage (radius, color, Mode.OUTLINED);
    }
    
    /**
     * Another pseudo-constructor, with an IColor and a default mode.
     */
    static CircleImage make (int radius, IColor color)
    {
        return new CircleImage (radius, color.thisColor(), Mode.OUTLINED);
    }
    
    /**
     * Another pseudo-constructor, with default color and mode.
     */
    static CircleImage make (int radius)
    {
        return new CircleImage (radius, Color.black, Mode.OUTLINED);
    }
        
    static WorldImage makeCentered (Posn center, int radius, Color color, Mode mode) {
        return new CircleImage (radius, color, mode)
                .moved (center.getX()-radius, center.getY()-radius);
    }
    
    static WorldImage makeCentered (Posn center, int radius, IColor color, Mode mode) {
        return makeCentered (center, radius, color.thisColor(), mode);
    }
    
    static WorldImage makeCentered (Posn center, int radius, Mode mode)
    {
        return makeCentered (center, radius, Color.black, mode);
    }
    
    static WorldImage makeCentered (Posn center, int radius, Color color)
    {
        return makeCentered (center, radius, color, Mode.OUTLINED);
    }
    
    static WorldImage makeCentered (Posn center, int radius, IColor color)
    {
        return makeCentered (center, radius, color.thisColor(), Mode.OUTLINED);
    }
    
    static WorldImage makeCentered (Posn center, int radius)
    {
        return makeCentered (center, radius, Color.black, Mode.OUTLINED);
    }
    
    
    CircleImage replaceRadius (int newRadius) {
        return new CircleImage(newRadius, this.getColor(), this.getMode());
    }
    
    ColoredImage replaceColor (Color newColor) {
        return new CircleImage(this.getRadius(), newColor, this.getMode());
    }

    ColoredImage replaceMode (Mode newMode) {
        return new CircleImage(this.getRadius(), this.getColor(), newMode);
    }
    
    public int getRight () {
        return (int)(Math.round(2*radius));
    }
    
    public int getBottom () {
        return (int)(Math.round(2*radius));
    }
    
    public int getRadius() {
        return this.radius;
    }
    
    public boolean equals (Object other) {
        return super.equals(other) &&
        this.radius == ((CircleImage)other).getRadius();
    }
    
    public int hashCode () {
        return super.hashCode() + this.radius;
    }
  
    public String toIndentedString (String indent) {
        String newIndent = indent + "  ";
        return "new CircleImage(this.radius = " + this.getRadius() +
        ",\n" + newIndent + "this.color = " + this.getColor() + 
        ",\n" + newIndent + "this.mode = " + this.getMode() +
        ",\n" + newIndent + this.cornerString() +
        ")";
    }

}
