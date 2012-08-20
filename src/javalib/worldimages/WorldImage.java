package javalib.worldimages;

import javalib.colors.*;

import java.awt.*;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The parent class for all images drawn by the 
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012, April 25 2012
 */
public abstract class WorldImage{
  public Posn pinhole;
  public Color color;

  /**
   * Every image has a pinhole (<code>Posn</code>) and a color 
   * (<code>Color</code>). The color for the
   * images derived from image files, or constructed by a combination
   * of several images is set to <code>Color.white</code> and ignored 
   * in drawing the images.
   * 
   * @param pinhole the pinhole location for this image
   * @param color the color for this image
   */
  public WorldImage(Posn pinhole, Color color){
    this.pinhole = new Posn(pinhole.x, pinhole.y);
    this.color = color;
  }  

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code>.
   * 
   * @param pinhole the pinhole location for this image
   * @param color the color for this image
   */
  public WorldImage(Posn pinhole, IColor color){
    this.pinhole = pinhole;
    this.color = color.thisColor();
  }
  
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  abstract public void draw(Graphics2D g);
  
  /**
   * Produce the image with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  abstract public WorldImage getMovedImage(int dx, int dy);
  
  /**
   * Produce the image with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  abstract public WorldImage getMovedTo(Posn p);
  
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
  }
  
  /**
   * EFFECT:
   * Move the pinhole for this image to the given location.
   * 
   * @param p the given location
   */
  public void moveTo(Posn p){
    this.pinhole.x = p.x;
    this.pinhole.y = p.y;
  }
  
  /**
   * Helper method to produce a <code>Posn</code> moved by the given (dx, dy)
   * @param p the given <code>Posn</code>
   * @param dx the horizontal distance to move by
   * @param dy the vertical distance to move by
   * @return the moved <code>Posn</code>
   */
  protected Posn movePosn(Posn p, int dx, int dy){
    return new Posn(p.x + dx, p.y + dy);
  }

  /**
   * <p>A convenience method that allows us to combine several images into
   * one on top of this image without the need to explicitly construct 
   * <code>{@link OverlayImages OverlayImages}</code></p>
   * <p>The pinhole is placed in the middle of all overlayed images. </p>
   * 
   * @param args an arbitrarily long list of 
   * <code>{@link WorldImage WorldImage}</code> to add to this image
   * @return the composite image
   **/
  public WorldImage overlayImages(WorldImage... args){
    WorldImage image = this;
    
    // start adding up all x and y pinhole coordinates
    int xTotal = this.pinhole.x;
    int yTotal = this.pinhole.y;
    
    // compute the length of the argument list
    int length = (args != null) ? args.length : 0;
    
    // add each of the images to this one
    for (int i = 0; i < length; i++) {
      image = new OverlayImages(image, args[i]);
      
      // add to the pinhole coordinates calculation
      xTotal = xTotal + args[i].pinhole.x;
      yTotal = yTotal + args[i].pinhole.y;
    }
    
    // set the pinhole to the middle of all images
    image.pinhole.x = xTotal/(length + 1);
    image.pinhole.y = yTotal/(length + 1);
    
    return image;
  }
  
  /**
   * Produce the width of this image
   * 
   * @return the width of this image
   */
  abstract public int getWidth();

  
  /**
   * Produce the height of this image
   * 
   * @return the height of this image
   */
  abstract public int getHeight();
  
  /**
   * Produce a <code>String</code> that represents this image, 
   * indented by the given <code>indent</code>
   * 
   * @param indent the given prefix representing the desired indentation
   * @return the <code>String</code> representation of this image
   */
  abstract public String toIndentedString(String indent);
  
  /**
   * produce the <code>String</code> that represent the color
   * without the extra narrative.
   * 
   * @param color the given color
   * @return the rgb representation S  <code>String</code>
   */
  protected static String colorString(String indent, Color color){
    String result = color.toString();
    int start = result.indexOf('[');
    result = result.substring(start, result.length());
    return "\n" + indent + "this.color = " + result + ",";
  }  
  
  protected static String classNameString(String indent, String className){
    return "\n" + indent + "new " + className + "(";
  }
  
  protected static String pinholeString(String indent, Posn pin){
    return "\n" + indent + "this.pinhole = (" + pin.x + ", " + pin.y + "),";
  }
  
  public static void main(String[] argv){
    System.out.println(colorString("  ", new Color(255, 255, 0, 50)));
  }

}