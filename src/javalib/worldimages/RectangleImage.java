package javalib.worldimages;

import javalib.colors.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * A filled  or outlined rectangle
 * 
 * @author Stephen Bloch
 * @version Dec. 5, 2012
 */
class RectangleImage extends RectangularImage
{    
    /**
     * The full constructor for a RectangleImage.
     * 
     * @param width
     * @param height
     * @param color
     */
    protected RectangleImage (int width, int height, Color color, Mode mode) {
        super (width, height, color, mode);
    }
    
    /**
     * The full pseudo-constructor
     * 
     * @param width
     * @param height
     * @param color
     * @param mode
     */
    static RectangleImage make (int width, int height, Color color, Mode mode)
    {
        return new RectangleImage (width, height, color, mode);
    }
    
    /**
     * Pseudo-constructor with an IColor
     * 
     * @param width
     * @param height
     * @param color
     * @param mode
     */
    static RectangleImage make (int width, int height, IColor color, Mode mode)
    {
        return new RectangleImage (width, height, color.thisColor(), mode);
    }
    
    /**
     * Pseudo-constructor with a default color
     * 
     * @param width
     * @param height
     * @param mode
     */
    static RectangleImage make (int width, int height, Mode mode)
    {
        return new RectangleImage (width, height, Color.black, mode);
    }
    
    /**
     * Pseudo-constructor with a Color and a default mode.
     * 
     * @param width
     * @param height
     * @param color
     */
    static RectangleImage make (int width, int height, Color color)
    {
        return new RectangleImage (width, height, color, Mode.OUTLINED);
    }
    
    /**
     * Pseudo-constructor with an IColor and a default mode.
     * 
     * @param width
     * @param height
     * @param color
     */
    static RectangleImage make (int width, int height, IColor color)
    {
        return new RectangleImage (width, height, color.thisColor(), Mode.OUTLINED);
    }
    
    /**
     * Pseudo-constructor with a default color and mode.
     * 
     * @param width
     * @param height
     */
    static RectangleImage make (int width, int height)
    {
        return new RectangleImage (width, height, Color.black, Mode.OUTLINED);
    }
    
    
    RectangularImage replaceDimensions (int width, int height) {
        return new RectangleImage (getWidth(), getHeight(), this.getColor(), this.getMode());
    }
    
    ColoredImage replaceColor (Color newColor) {
        return new RectangleImage (this.getWidth(), this.getHeight(), newColor, this.getMode());
    }
    
    ColoredImage replaceMode (Mode newMode)
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
