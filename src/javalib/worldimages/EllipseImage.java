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
 * <p>The class to represent filled ellipse images drawn by the  
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class EllipseImage extends WorldImage{
  
  /** the width of this ellipse */
  public int width;
  
  /** the height of this ellipse */
  public int height;
  
  /**
   * A full constructor for this ellipse image.
   * 
   * @param pinhole the pinhole location for this image
   * @param width the width of this ellipse
   * @param height the height of this ellipse
   * @param color the color for this image
   */   
  public EllipseImage(Posn pinhole, int width, int height, Color color){
    super(pinhole, color);
    this.width = width;
    this.height = height;
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code>.
   * 
   * @param pinhole the pinhole location for this image
   * @param width the width of this ellipse
   * @param height the height of this ellipse
   * @param color the color for this image
   */
  public EllipseImage(Posn pinhole, int width, int height, IColor color){
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
    g.draw(new Ellipse2D.Double(this.pinhole.x - this.width / 2, 
                                  this.pinhole.y - this.height / 2, 
                                  this.width, this.height));
    // reset the original paint
    g.setPaint(oldPaint);   
  } 
  
  /**
   * Produce the ellipse with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return new EllipseImage(new Posn(this.pinhole.x + dx, this.pinhole.y + dy), 
        this.width, this.height, this.color);
  }
  
  /**
   * Produce the ellipse with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    return new EllipseImage(p, this.width, this.height, this.color);
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
   * Produce a <code>String</code> representation of this ellipse image
   */
  public String toString(){
    return "new EllipseImage(this.pinhole = (" 
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
    return classNameString(indent, "EllipseImage") + 
        pinholeString(indent, this.pinhole) + 
        colorString(indent, this.color) + 
        "\n" + indent + "this.width = " + width + 
        ", this.height = " + height +")\n";
  }
  
  /**
   * Is this <code>EllipseImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof EllipseImage){
      EllipseImage that = (EllipseImage)o;
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