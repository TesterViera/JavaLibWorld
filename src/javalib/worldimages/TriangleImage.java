package javalib.worldimages;

import javalib.colors.*;

import java.awt.*;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent filled triangle images drawn by the
 * world when drawing on its <code>Canvas</code>.</p>
 * <p>The pinhole for the triangle is in the center of the triangle.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class TriangleImage extends WorldImage{
  public Posn p1;
  public Posn p2;
  public Posn p3;
  private Polygon poly;

  /**
   * A full constructor for this triangle image.
   * 
   * @param p1 the first point of this triangle
   * @param p2 the second point of this triangle
   * @param p3 the third point of this triangle
   * @param color the color for this image
   */ 
  public TriangleImage(Posn p1, Posn p2, Posn p3, Color color){
    super(p1, color);
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
    int[] xCoord = new int[]{p1.x, p2.x, p3.x};
    int[] yCoord = new int[]{p1.y, p2.y, p3.y};
    this.poly = new Polygon(xCoord, yCoord, 3);
    
    // set the pinhole in the center of the triangle
    this.pinhole.x = (this.p1.x + this.p2.x + this.p3.x) / 3;
    this.pinhole.y = (this.p1.y + this.p2.y + this.p3.y) / 3;
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code>.
   * 
   * @param p1 the first point of this triangle
   * @param p2 the second point of this triangle
   * @param p3 the third point of this triangle
   * @param color the color for this image
   */
  public TriangleImage(Posn p1, Posn p2, Posn p3, IColor color){
    this(p1, p2, p3, color.thisColor());
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
    // draw the triangle
    g.fill(poly);
    // reset the original paint
    g.setPaint(oldPaint);   
  }
  
  /**
   * Produce the triangle with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return new TriangleImage(this.movePosn(this.p1, dx, dy),
                             this.movePosn(this.p2, dx, dy),
                             this.movePosn(this.p3, dx, dy), this.color);
  }

  /**
   * Produce the triangle with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    int dx = p.x - this.pinhole.x;
    int dy = p.y - this.pinhole.y;
    return this.getMovedImage(dx,  dy);
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
    this.p1.x = this.p1.x + dx;
    this.p1.y = this.p1.y + dy;
    this.p2.x = this.p2.x + dx;
    this.p2.y = this.p2.y + dy;
    this.p3.x = this.p3.x + dx;
    this.p3.y = this.p3.y + dy;
  }

  /**
   * EFFECT:
   * Move the pinhole for this image to the given location.
   * 
   * @param p the given location
   */
  public void moveTo(Posn p){
    int dx = p.x - this.pinhole.x;
    int dy = p.y - this.pinhole.y;
    this.movePinhole(dx,  dy);
  }

  /**
   * Produce the width of this triangle image
   * 
   * @return the width of this image
   */
  public int getWidth(){
    return Math.max(p1.x, Math.max(p2.x, p3.x)) - 
           Math.min(p1.x, Math.min(p2.x, p3.x));
  }

  /**
   * Produce the height of this triangle image
   * 
   * @return the height of this image
   */
  public int getHeight(){
    return Math.max(p1.y, Math.max(p2.y, p3.y)) - 
           Math.min(p1.y, Math.min(p2.y, p3.y));
  }
  
  /**
   * Produce a <code>String</code> representation of this triangle image
   */
  public String toString(){
    return "new TriangleImage(this.pinhole = (" 
        + this.pinhole.x + ", " + this.pinhole.y +  
        "), \nthis.color = " + this.color.toString() +
        "\nthis.p1 = (" + this.p1.x + ", " + this.p1.y +
        "), \nthis.p2 = (" + this.p2.x + ", " + this.p2.y +
        "), \nthis.p3 = (" + this.p3.x + ", " + this.p3.y +
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
    indent = indent + " ";
    return classNameString(indent, "TriangleImage") + 
        pinholeString(indent, this.pinhole) + 
        colorString(indent, this.color) + 
        "\n" + indent + "this.p1 = (" + this.p1.x + ", " + this.p1.y +
        "), \n" + indent + "this.p2 = (" + this.p2.x + ", " + this.p2.y +
        "), \n" + indent + "this.p3 = (" + this.p3.x + ", " + this.p3.y +
        "))\n";
  }
  
  /**
   * Is this <code>TriangleImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof TriangleImage){
      TriangleImage that = (TriangleImage)o;
      return this.pinhole.x == that.pinhole.x 
        && this.pinhole.y == that.pinhole.y
      && this.p1.x == that.p1.x
      && this.p1.y == that.p1.y
      && this.p2.x == that.p2.x
      && this.p2.y == that.p2.y
      && this.p3.x == that.p3.x
      && this.p3.y == that.p3.y
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
        this.p1.x * this.p1.y + this.p2.x * this.p2.y + 
        this.p3.x * this.p3.y; 
  }
}