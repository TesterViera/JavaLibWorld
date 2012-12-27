package javalib.worldimages;

import javalib.colors.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * An outlined ellipse
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
class EllipseImage extends RectangularImage
{    
    /**
     * The full constructor for an EllipseImage.
     * 
     * @param width
     * @param height
     * @param color
     */
    protected EllipseImage (int width, int height, Color color, Mode mode) {
        super (width, height, color, mode);
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param width
     * @param height
     * @param color
     * @param mode
     */
  static EllipseImage make (int width, int height, Color color, Mode mode)
    {
        return new EllipseImage (width, height, color, mode);
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param width
     * @param height
     * @param color
     * @param mode
     */
    static EllipseImage make (int width, int height, IColor color, Mode mode)
    {
        return new EllipseImage (width, height, color.thisColor(), mode);
    }
    
    /**
     * Another pseudo-constructor with a default color.
     * 
     * @param width
     * @param height
     * @param mode
     */
    static EllipseImage make (int width, int height, Mode mode)
    {
        return new EllipseImage (width, height, Color.black, mode);
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param width
     * @param height
     * @param color
     */
    static EllipseImage make (int width, int height, Color color)
    {
        return new EllipseImage (width, height, color, Mode.OUTLINED);
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param width
     * @param height
     * @param color
     */
    static EllipseImage make (int width, int height, IColor color)
    {
        return new EllipseImage (width, height, color.thisColor(), Mode.OUTLINED);
    }
    
    /**
     * Another pseudo-constructor with a default color.
     * 
     * @param width
     * @param height
     */
    static EllipseImage make (int width, int height)
    {
        return new EllipseImage (width, height, Color.black, Mode.OUTLINED);
    }
    
    
    RectangularImage replaceDimensions (int width, int height) {
        return new EllipseImage (width, height, this.getColor(), this.getMode());
    }
    
    ColoredImage replaceColor (Color color) {
        return new EllipseImage (this.getWidth(), this.getHeight(), color, this.getMode());
    }
    
    ColoredImage replaceMode (Mode mode)
    {
        return new EllipseImage (this.getWidth(), this.getHeight(), this.getColor(), mode);
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
        return "new EllipseImage(this.width = " + this.getWidth() + ", this.height = " + this.getHeight() +
        ",\n" + newIndent + "this.color = " + this.getColor() + 
        ",\n" + newIndent + this.cornerString() +
        ")";
    }

}
