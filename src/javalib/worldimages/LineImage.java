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
 * <p>The class to represent line images drawn by the  
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class LineImage extends WorldImage{
  
  /** the starting point of this line */
  public Posn startPoint;
  
  /** the starting point of this line */
  public Posn endPoint;
  
  /**
   * A full constructor for this line image.
   * 
   * @param startPoint the starting point of this line
   * @param endPoint the ending point of this line
   * @param color the color for this image
   */ 
  public LineImage(Posn startPoint, Posn endPoint, Color color){
    super(startPoint, color);
    this.startPoint = new Posn(startPoint.x, startPoint.y);
    this.endPoint = new Posn(endPoint.x, endPoint.y);
    this.pinhole.x = (this.startPoint.x + this.endPoint.x) / 2;
    this.pinhole.y = (this.startPoint.y + this.endPoint.y) / 2;
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code>.
   * 
   * @param startPoint the starting point of this line
   * @param endPoint the ending point of this line
   * @param color the color for this image
   */
  public LineImage(Posn startPoint, Posn endPoint, IColor color){
    this(startPoint, endPoint, color.thisColor());
  }
  
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    if (color == null)
      color = new Color(0, 0, 0);
    
    // save the current paint
    Paint oldPaint = g.getPaint();
    // set the paint to the given color
    g.setPaint(color);  
    // draw the object
    g.draw(new Line2D.Double(this.startPoint.x, this.startPoint.y, 
        this.endPoint.x, this.endPoint.y));
    // reset the original paint
    g.setPaint(oldPaint);   
  }
  
  /**
   * Produce the line image with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return new LineImage(this.movePosn(this.startPoint, dx, dy), 
                         this.movePosn(this.endPoint, dx, dy), this.color);
  }
  
  /**
   * Produce the line image  with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    int dx = p.x - this.pinhole.x;
    int dy = p.y - this.pinhole.y;
    return this.getMovedImage(dx, dy);
  }
  
  /**
   * EFFECT:
   * Move the pinhole for this image by the given offset.
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public void movePinhole(int dx, int dy){
    this.pinhole.x = this.pinhole.x + dx;
    this.pinhole.y = this.pinhole.y + dy;
    this.startPoint.x = this.startPoint.x + dx;
    this.startPoint.y = this.startPoint.y + dy; 
    this.endPoint.x = this.endPoint.x + dx;
    this.endPoint.y = this.endPoint.y + dy;   
  }

  /**
   * EFFECT:
   * Move the pinhole for this image to the given location.
   * 
   * @param p the given location
   */
  public void moveTo(Posn p){
    int dx = p.x - pinhole.x;
    int dy = p.y - pinhole.y;
    this.movePinhole(dx, dy);
  }
  
  
  /**
   * Produce the width of this image (of its bounding box)
   * 
   * @return the width of this image
   */
  public int getWidth(){
    return Math.abs(this.startPoint.x - this.endPoint.x);
  }

  
  /**
   * Produce the height of this image (of its bounding box)
   * 
   * @return the height of this image
   */
  public int getHeight(){
    return Math.abs(this.startPoint.y - this.endPoint.y);
  }
  
  /**
   * Produce a <code>String</code> representation of this triangle image
   */
  public String toString(){
    return "new LineImage(this.pinhole = (" 
        + this.pinhole.x + ", " + this.pinhole.y +  
        "), \nthis.color = " + this.color.toString() + 
        "\nthis.startPoint = (" + this.startPoint.x + ", " + this.startPoint.y +
        "), \nthis.endPoint = (" + this.endPoint.x + ", " + this.endPoint.y +
        "))\n";
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
    return classNameString(indent, "LineImage") + 
        pinholeString(indent, this.pinhole) + 
        colorString(indent, this.color) + 
        "\n" + indent + "this.startPoint = (" + 
            this.startPoint.x + ", " + this.startPoint.y +
        "), \n" + indent + "this.endPoint = (" + 
            this.endPoint.x + ", " + this.endPoint.y +
        "))\n";
  }
  
  /**
   * Is this <code>LineImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof LineImage){
      LineImage that = (LineImage)o;
      return this.pinhole.x == that.pinhole.x 
        && this.pinhole.y == that.pinhole.y
      && this.startPoint.x == that.startPoint.x
      && this.startPoint.y == that.startPoint.y
      && this.endPoint.x == that.endPoint.x
      && this.endPoint.y == that.endPoint.y
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
        this.startPoint.x + this.startPoint.y + 
        this.endPoint.x + this.endPoint.y; 
  }
}