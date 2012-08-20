package javalib.worldimages;

import javalib.colors.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;


/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent circle outline images drawn by the 
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class CircleImage extends WorldImage{
  
  /** the radius of this circle */
  public int radius;
  
  /**
   * A full constructor for this circle image.
   * 
   * @param pinhole the pinhole location for this image
   * @param radius the radius of this circle
   * @param color the color for this image
   */ 
  public CircleImage(Posn pinhole, int radius, Color color){
    super(pinhole, color);
    this.radius = radius;
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code>.
   * 
   * @param pinhole the pinhole location for this image
   * @param radius the radius of this circle
   * @param color the color for this image
   */
  public CircleImage(Posn pinhole, int radius, IColor color){
    super(pinhole, color);
    this.radius = radius;
  }
  
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    if (this.radius <= 0)
      return;
    if (this.color == null)
      this.color = new Color(0, 0, 0);
    
    // save the current paint
    Paint oldPaint = g.getPaint();
    // set the paint to the given color
    g.setPaint(this.color);  
    // draw the object
    g.draw(new Ellipse2D.Double(this.pinhole.x - this.radius, 
        this.pinhole.y - this.radius, 
        2 * this.radius, 
        2 * this.radius));
    // reset the original paint
    g.setPaint(oldPaint);   
  }  
  
  /**
   * Produce the circle with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return new CircleImage(new Posn(this.pinhole.x + dx, this.pinhole.y + dy), 
        this.radius, this.color);
  }
  
  /**
   * Produce the circle with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    return new CircleImage(p, this.radius, this.color);
  }

  /**
   * Produce the width of this image
   * 
   * @return the width of this image
   */
  public int getWidth(){
    return 2 * this.radius;
  }

  /**
   * Produce the height of this image
   * 
   * @return the height of this image
   */
  public int getHeight(){
    return 2 * this.radius;
  }
  
  /**
   * Produce a <code>String</code> representation of this circle image
   */
  public String toString(){
    return "new CircleImage(this.pinhole = (" 
        + this.pinhole.x + ", " + this.pinhole.y + 
        "), \nthis.color = " + this.color.toString() + 
        "\nthis.radius = " + this.radius + ")\n";
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
    return classNameString(indent, "CircleImage") + 
        pinholeString(indent, this.pinhole) + 
        colorString(indent, this.color) + 
        "\n" + indent + "this.radius = " + this.radius + ")\n";
  }
  
  /**
   * Is this <code>CircleImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof CircleImage){
      CircleImage that = (CircleImage)o;
      return this.pinhole.x == that.pinhole.x 
        && this.pinhole.y == that.pinhole.y
        && this.radius == that.radius
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
        this.radius; 
  }
}