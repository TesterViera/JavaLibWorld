package javalib.worldimages;

import javalib.colors.*;

import java.awt.*;
import java.awt.geom.*;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent filled rectangle images drawn by the 
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class RectangleImage extends WorldImage{
  public int width;
  public int height;
 
  /**
   * A full constructor for this rectangle image.
   * 
   * @param pinhole the pinhole location for this image
   * @param width the width of this rectangle
   * @param height the height of this rectangle
   * @param color the color for this image
   */
  public RectangleImage(Posn pinhole, int width, int height, Color color){
    super(pinhole, color);
    this.width = width;
    this.height = height;
  }
  
  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code>.
   * 
   * @param pinhole the pinhole location for this image
   * @param width the width of this rectangle
   * @param height the height of this rectangle
   * @param color the color for this image
   */
  public RectangleImage(Posn pinhole, int width, int height, IColor color){
    super(pinhole, color);
    this.width = width;
    this.height = height;
  }
  
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    if (this.width <= 0)
      return;
    if (this.height <= 0)
      return;
    if (this.color == null)
      this.color = new Color(0, 0, 0);
    
    // save the current paint
    Paint oldPaint = g.getPaint();
    // set the paint to the given color
    g.setPaint(this.color);  
    // draw the object
    g.fill(new Rectangle2D.Double(this.pinhole.x - this.width / 2, 
                                  this.pinhole.y - this.height / 2, 
                                  this.width, this.height));
    // reset the original paint
    g.setPaint(oldPaint);   
  }
  
  /**
   * Produce the rectangle with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return new RectangleImage(new Posn(this.pinhole.x + dx, this.pinhole.y + dy), 
        this.width, this.height, this.color);
  }
  
  /**
   * Produce the rectangle with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    return new RectangleImage(p, this.width, this.height, this.color);
  }
  
  
  /**
   * Produce the width of this image
   * 
   * @return the width of this image
   */
  public int getWidth(){
    return this.width;
  }

  
  /**
   * Produce the height of this image
   * 
   * @return the height of this image
   */
  public int getHeight(){
    return this.height;
  }

  
  /**
   * Produce a <code>String</code> representation of this rectangle image
   */
  public String toString(){
    return "new RectangleImage(this.pinhole = ("
        + this.pinhole.x + ", " + this.pinhole.y + 
        "), \nthis.color = " + this.color.toString() + 
        "\nthis.width = " + width + ", this.height = " + height +")\n";
  }
 
  /**
   * Produce a <code>String</code> that represents this image, 
   * indented by the given <code>indent</code>
   * 
   * @param indent the given prefix representing the desired indentation
   * @return the <code>String</code> representation of this image
   */
  public String toIndentedString(String indent){
    indent = indent + "  ";
    return classNameString(indent, "RectangleImage") + 
        pinholeString(indent, this.pinhole) + 
        colorString(indent, this.color) + 
    "\n" + indent + "this.width = " + width + 
    ", this.height = " + height +")\n";
  }
  
  /**
   * Is this <code>RectangleImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof RectangleImage){
      RectangleImage that = (RectangleImage)o;
      return this.pinhole.x == that.pinhole.x 
        && this.pinhole.y == that.pinhole.y
        && this.width == that.width
        && this.height == that.height
        && this.color.equals(that.color);
    }
    else 
      return false;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return this.pinhole.x + this.pinhole.y + this.color.hashCode() +
        this.width + this.height; 
  }
}