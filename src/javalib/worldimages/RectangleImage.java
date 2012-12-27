package javalib.sbimages;

import javalib.colors.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * A filled  or outlined rectangle
 * 
 * @author Stephen Bloch
 * @version Dec. 5, 2012
 */
public class RectangleImage extends RectangularImage
{    
    /**
     * The full constructor for a RectangleImage.
     * 
     * @param width
     * @param height
     * @param color
     */
    public RectangleImage (int width, int height, Color color, Mode mode) {
        super (width, height, color, mode);
    }
    
    /**
     * Pseudo-constructor
     * 
     * @param width
     * @param height
     * @param color
     * @param mode
     */
    public static RectangleImage make (int width, int height, Color color, Mode mode)
    {
        return new RectangleImage (width, height, color, mode);
    }
    
    /**
     * Another constructor that takes in an <code>{@link IColor}IColor</code>.
     * 
     * @param width
     * @param height
     * @param color
     * @param mode
     */
    public RectangleImage (int width, int height, IColor color, Mode mode) {
        super(width, height, color, mode);
    }
    
    /**
     * Pseudo-constructor
     * 
     * @param width
     * @param height
     * @param color
     */
    public static RectangleImage make (int width, int height, IColor color, Mode mode)
    {
        return new RectangleImage (width, height, color, mode);
    }
    
    public RectangularImage replaceDimensions (int width, int height) {
        return new RectangleImage (getWidth(), getHeight(), this.getColor(), this.getMode());
    }
    
    public ColoredImage replaceColor (Color newColor) {
        return new RectangleImage (this.getWidth(), this.getHeight(), newColor, this.getMode());
    }
    
    public ColoredImage replaceMode (Mode newMode)
    {
        return new RectangleImage (this.getWidth(), this.getHeight(), this.getColor(), newMode);
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
    
    Rectangle2D rect = new Rectangle2D.Double(this.getLeft(), this.getTop(),
                                              this.getWidth(), this.getHeight());
    if (this.getMode() == Mode.FILLED)
    {
        g.fill(rect);
    }
    else if (this.getMode() == Mode.OUTLINED)
    {
        g.draw(rect);
    }
    // reset the original paint
    g.setPaint(oldPaint);   
  }
  
    public String toIndentedString (String indent) {
        String newIndent = indent + "  ";
        return "new RectangleImage(this.width = " + this.getWidth() + ", this.height = " + this.getHeight() + 
        ",\n" + newIndent + "this.color = " + this.getColor() + 
        ",\n" + newIndent + this.cornerString() +
        ")";
    }

}
