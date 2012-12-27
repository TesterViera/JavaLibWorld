package javalib.sbimages;

import javalib.colors.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * An outlined circle
 * 
 * @author Stephen Bloch
 * @version Dec. 2, 2012
 */
public class CircleImage extends ColoredImage
{    
    private int radius;
    
    /**
     * The full constructor for a CircleImage.
     * 
     * @param radius
     * @param color
     * @param mode
     */
    public CircleImage (int radius, Color color, Mode mode) {
        super (color, mode);
        this.radius = radius;
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param radius
     * @param color
     * @param mode
     */
    public static CircleImage make (int radius, Color color, Mode mode)
    {
        return new CircleImage (radius, color, mode);
    }
    
    /**
     * Another constructor that takes in an <code>{@link IColor}IColor</code>.
     * 
     * @param radius
     * @param color
     */
    public CircleImage (int radius, IColor color, Mode mode) {
        this(radius, color.thisColor(), mode);
    }
    
    /**
     * Pseudo-constructor.
     */
    public static CircleImage make (int radius, IColor color, Mode mode)
    {
        return new CircleImage (radius, color, mode);
    }
        
    public static Image makeCentered (Posn center, int radius, Color color, Mode mode) {
        return new CircleImage (radius, color, mode)
                .getTranslated (center.getX()-radius, center.getY()-radius);
    }
    
    public static Image makeCentered (Posn center, int radius, IColor color, Mode mode) {
        return makeCentered (center, radius, color.thisColor(), mode);
    }
    
    public CircleImage replaceRadius (int newRadius) {
        return new CircleImage(newRadius, this.getColor(), this.getMode());
    }
    
    public ColoredImage replaceColor (Color newColor) {
        return new CircleImage(this.getRadius(), newColor, this.getMode());
    }

    public ColoredImage replaceMode (Mode newMode) {
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
                                
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    if (this.getWidth() <= 0)
      return;
    if (this.getHeight() <= 0)
      return;
    
    // save the current paint
    Paint oldPaint = g.getPaint();
    // set the paint to the given color
    g.setPaint(this.getColor());  
    // draw the object
    Ellipse2D shape = new Ellipse2D.Double(this.getLeft(), this.getTop(),
                                           this.getWidth(), this.getHeight());
    if (this.getMode() == Mode.FILLED)
    {
        g.fill (shape);
    }
    else if (this.getMode() == Mode.OUTLINED)
    {
        g.draw (shape);
    }
    // reset the original paint
    g.setPaint(oldPaint);   
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
