package javalib.worldimages;

import javalib.colors.*;
import javalib.worldcanvas.CanvasPanel;


import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent text as an image to be drawn by the
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012, April 25 2012
 */
public class TextImage extends WorldImage{
  
  /** the text to be shown */
  public String text;
  
  /** the size of the font to use: the default here is 13 */
  public float size;
  
  /** the style of the font:
   *  0 = regular, 1 = bold, 2 = italic, 3 = italic bold */
  public int style = 0;
  
  /** the width of the bounding box */
  public int width = 0;
  
  /** the height of the bounding box */
  public int height = 0;  
  
  /** <p>the desired alignment for this text:
   * 0 = left, 1 = center, 2 = right</p>
   * <em>not yet implemented - currently the text is centered</em>
   */
  public int alignment = 1; 
  
  /** the Canvas for defining the font for this text image */
  public static CanvasPanel c = new CanvasPanel(600, 600);
  
  /** the graphics context where the text is to be shown */
  protected static Graphics2D g = c.getBufferGraphics();
  
  /** the current default font in our graphics context */
  protected static Font font = g.getFont(); 
  
  
  /**
   * A full constructor for this text image.
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param style the style of the font: (regular, bold, italic, italic/bold)
   * @param color the color for this image
   */ 
  public TextImage(Posn pinhole, String text, float size, int style, Color color){
    super(pinhole, color);
    // bad things happen if we want to display a null String 
    // or a String of length 0
    if (text == null || text.equals(""))
      text = " ";
    this.text = text;
    this.size = size;
    this.style = style;
    this.setWidthHeight();
  }
  
  /**
   * A full constructor for this text image.
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param style the style of the font: (regular, bold, italic, italic/bold)
   * @param color the color for this image
   */ 
  public TextImage(Posn pinhole, String text, int size, int style, Color color){
    super(pinhole, color);
    // bad things happen if we want to display a null String 
    // or a String of length 0
    if (text == null || text.equals(""))
      text = " ";
    this.text = text;
    this.size = size;
    this.style = style;
    this.setWidthHeight();
  }

  /**
   * A convenience constructor providing the default style (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, float size, Color color){
    this(pinhole, text, size, 0, color);
  }

  /**
   * A convenience constructor providing the default style (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, int size, Color color){
    this(pinhole, text, size, 0, color);
  }
  
  /**
   * A convenience constructor providing the default style (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, Color color){
    this(pinhole, text, 13, 0, color);
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code> and providing the default style 
   * (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param style the style of the font: (regular, bold, italic, italic/bold)
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, float size, int style, IColor color){
    this(pinhole, text, size, style, color.thisColor());
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code> and providing the default style 
   * (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param style the style of the font: (regular, bold, italic, italic/bold)
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, int size, int style, IColor color){
    this(pinhole, text, size, style, color.thisColor());
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code> and providing the default style 
   * (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, float size, IColor color){
    this(pinhole, text, size, 0, color);
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code> and providing the default style 
   * (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param size the size of the font to use (the default is 13)
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, int size, IColor color){
    this(pinhole, text, size, 0, color);
  }

  /**
   * A convenience constructor to supply the color in the form of
   * <code>{@link IColor IColor}</code> and providing the default style 
   * (regular).
   * 
   * @param pinhole the pinhole location for this image
   * @param text the text to be shown
   * @param color the color for this image
   */
  public TextImage(Posn pinhole, String text, IColor color){
    this(pinhole, text, 13, 0, color);
  }
  
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    if (this.text == null)
      this.text = "";
    if (this.color == null)
      this.color = new Color(0, 0, 0);
    
    
    // save the current paint and font
    Paint oldPaint = g.getPaint();
    Font oldFont = g.getFont();
    
    // change the font style and size as given
    g.setFont(oldFont.deriveFont(this.style, this.size));
    // set the paint to the given color
    g.setPaint(this.color);  

    if (alignment == 1)
    
    // draw the object
    g.drawString(this.text, this.pinhole.x - this.width / 2, 
                 this.pinhole.y + this.height / 4);
    
    // reset the original paint and font
    g.setPaint(oldPaint);   
    g.setFont(oldFont);
  }

  /**
   * Produce the file-based images with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return  
        new TextImage(this.movePosn(this.pinhole, dx, dy), 
                          this.text, this.size, this.style, this.color);
  }

  /**
   * Produce the file-based image with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    return new TextImage(p, this.text, this.size, this.style, this.color);
  }
  
  /**
   * Compute and set the width and the height for this text 
   * in the given style and size
   */
  protected void setWidthHeight(){
    
    // change the font style and size as given
    g.setFont(font.deriveFont(this.style, this.size));
    // now get this new font
    Font newFont = g.getFont();
    
    FontRenderContext frc = g.getFontRenderContext();
    
    Point2D loc = new Point2D.Double(300, 300);
    
    TextLayout layout = new TextLayout(text, newFont, frc);
    layout.draw(g, (float)loc.getX(), (float)loc.getY());

    g.setFont(font);
    
    Rectangle2D bounds = layout.getBounds();
    
    /*
    System.out.println("Bounds: x = " + bounds.getX() +
                       "  y = " + bounds.getX() +
                       "  width = " + bounds.getWidth() +
                       "  height = " + bounds.getHeight());
    */
    
    this.width = (int)bounds.getWidth();
    this.height = (int)bounds.getHeight(); 

  }

  /**
   * Produce the width of this image - assuming the default font
   * (generated by trial and error with the default font)
   * 
   * @return the (approximate) width of this image
   */
  public int getWidth(){
    //return (int)(this.text.length() * 0.53 * this.size);
    return this.width;
  }

  
  /**
   * Produce the height of this text image (based on its size)
   * 
   * @return the height of this image
   */
  public int getHeight(){
    //return (int)this.size;
    return this.height;
  }

  
  /**
   * Produce a <code>String</code> representation of this text image
   */
  public String toString(){
    char c = '"';
    return "new TextImage(this.pinhole = (" 
        + this.pinhole.x + ", " + this.pinhole.y +  
        "), \nthis.color = " + this.color.toString() + 
        "\nthis.size = " + this.size + ", this.style = " + this.style +
        ", this.alignment = " + this.alignment + 
        "\n" + c + this.text + c + ")\n";
  }
 
  /**
   * Produce a <code>String</code> that represents this image, 
   * indented by the given <code>indent</code>
   * 
   * @param indent the given prefix representing the desired indentation
   * @return the <code>String</code> representation of this image
   */
  public String toIndentedString(String indent){
    char c = '"';
    indent = indent + "  ";
    return classNameString(indent, "TextImage") + 
        pinholeString(indent, this.pinhole) + 
        colorString(indent, this.color) + 
        "\n" + indent + "this.size = " + this.size + 
        "\n" + indent + "this.style = " + this.style + 
        "\n" + indent + "this.alignment = " + this.alignment + 
        "\n" + indent + c + this.text + c + ")\n";
  }
  
  /**
   * Is this <code>TextImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof TextImage){
      TextImage that = (TextImage)o;
      return this.pinhole.x == that.pinhole.x 
        && this.pinhole.y == that.pinhole.y
        && this.size == that.size
        && this.style == that.style
        && this.alignment == that.alignment
        && this.text.equals(that.text)
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
        (int)this.size + this.style + this.alignment + this.text.hashCode(); 
  }
}