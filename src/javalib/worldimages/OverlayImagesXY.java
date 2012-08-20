package javalib.worldimages;

import java.awt.*;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent an overlay of the top image on the bottom one
 * with the top offset by the given <code>dx</code> and <code>dy</code> 
 * combined into <code>{@link WorldImage WorldImage}</code> to be drawn by the 
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class OverlayImagesXY extends WorldImage{
  
  /** the bottom image for the combined image */
  public WorldImage bot;
  
  /** the top image for the combined image */
  public WorldImage top;
  
  /** the horizontal offset for the top image */
  public int dx;
  
  /** the vertical offset for the top image */
  public int dy;
  
  /** The full constructor that produces the top image overlaid over the bottom
   * one with the given offset.
   *
   * @param bot the bottom image for the combined image
   * @param top the bottom image for the combined image
   * @param dx the horizontal offset for the top image
   * @param dy the vertical offset for the top image
   */
  public OverlayImagesXY(WorldImage bot, WorldImage top, int dx, int dy){
    super(bot.pinhole, Color.white);
    this.bot = bot;
    this.top = top;
    this.dx = dx;
    this.dy = dy;
    this.pinhole.x = (this.bot.pinhole.x + this.top.pinhole.x) / 2;
    this.pinhole.y = (this.bot.pinhole.y + this.top.pinhole.y) / 2;
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
    // draw the two objects
   this.bot.draw(g);
   this.top.getMovedImage(dx,  dy);
   this.top.draw(g);
   this.top.getMovedImage(-dx, -dy);
    
    // reset the original paint
    g.setPaint(oldPaint);   
  }

  /**
   * Produce the overlay of images with the pinhole moved by the given (dx, dy)
   * 
   * @param ddx the horizontal offset
   * @param ddy the vertical offset
   */
  public WorldImage getMovedImage(int ddx, int ddy){
    return 
        new OverlayImagesXY(this.bot.getMovedImage(ddx, ddy),
                            this.top.getMovedImage(ddx, ddy), this.dx, this.dy);
  }

  /**
   * Produce the overlay of images with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    int dx = p.x - pinhole.x;
    int dy = p.y - pinhole.y;
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
    this.bot.movePinhole(dx, dy);
    this.top.movePinhole(dx, dy);  
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
   * Produce the width of this image
   * 
   * @return the width of this image
   */
  public int getWidth(){
    int leftBot = this.bot.pinhole.x - this.bot.getWidth() / 2;
    int leftTop = this.top.pinhole.x - this.top.getWidth() / 2;
    int rightBot = this.bot.pinhole.x + this.bot.getWidth() / 2;
    int rightTop = this.top.pinhole.x + this.top.getWidth() / 2;
    return Math.max(rightBot, rightTop) - Math.min(leftBot, leftTop);
  }

  
  /**
   * Produce the height of this image
   * 
   * @return the height of this image
   */
  public int getHeight(){
    int upperBot = this.bot.pinhole.y - this.bot.getHeight() / 2;
    int upperTop = this.top.pinhole.y - this.top.getHeight() / 2;
    int lowerBot = this.bot.pinhole.y + this.bot.getHeight() / 2;
    int lowerTop = this.top.pinhole.y + this.top.getHeight() / 2;
    return Math.max(lowerBot, lowerTop) - Math.min(upperBot, upperTop);
  }
  
  /**
   * Produce a <code>String</code> representation of this overlay of images
   */
  public String toString(){
    return "new OverlayImagesXY(this.pinhole = (" 
        + this.pinhole.x + ", " + this.pinhole.y + 
        "), \nthis.color = " + this.color.toString() + 
        "\nthis.dx = " + this.dx + 
        ", this.dy = " + this.dy + "," +
        "\nthis.bot = " + this.bot.toString() + 
        "\nthis.top = " + this.top.toString() + ")\n";
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
    return classNameString(indent, "OverlayImagesXY") + 
        pinholeString(indent, this.pinhole) + 
        "\n" + indent + "this.dx = " + this.dx + 
        ", this.dy = " + this.dy + 
        "\n" + indent + "this.bot = " + this.bot.toString() + 
        "\n" + indent + "this.top = " + this.top.toString() + ")\n";
  }
  
  /**
   * Is this <code>OverlayImagesXY</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof OverlayImagesXY){
      OverlayImagesXY that = (OverlayImagesXY)o;
      return this.pinhole.x == that.pinhole.x 
        && this.pinhole.y == that.pinhole.y
        && this.bot.equals(that.bot)
        && this.top.equals(that.top)
        && this.dx == that.dx
        && this.dy == that.dy;
    }
    else 
      return false;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return this.pinhole.x + this.pinhole.y + this.color.hashCode() +
        this.dx + this.dy + 
        this.bot.hashCode() + this.top.hashCode(); 
  }
}