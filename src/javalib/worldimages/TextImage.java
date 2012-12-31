package javalib.worldimages;


/**
 * an image of some text
 * 
 * @author Stephen Bloch, mostly cribbed from Viera's TextImage class
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 * @version Dec. 5, 2012
 */
import javalib.colors.*;
import javalib.worldcanvas.CanvasPanel;


import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
//import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


class TextImage extends ColoredImage{
  
  /** the text to be shown */
  private String text;
  
  /** the size of the font to use: the default here is 13 */
  private float size;
  static final float defaultSize = 13.0f;
  
  private TextStyle style;
  
  /** the width of the bounding box */
  private int width;
  
  /** the height of the bounding box */
  private int height;
  
  private int ascent;
  
  /** the Canvas for defining the font for this text image */
  private static CanvasPanel c = new CanvasPanel(600, 600);
  
  /** the graphics context where the text is to be shown */
  private static Graphics2D g = c.getBufferGraphics();
  
  /** the current default font in our graphics context */
  private static Font font = g.getFont(); 
  
  /**
   * A full constructor for this text image.
   * 
   * @param text the text to be shown
   * @param size the size of the font to use
   * @param style the style of the font: (regular, bold, italic, italic/bold)
   * @param color the color for this image
   */ 
  private TextImage(String text, float size, TextStyle style, Color color){
    super(color, Mode.FILLED);
    // bad things happen if we want to display a null String 
    // or a String of length 0
    if (text == null || text.equals(""))
      text = " ";
    this.text = text;
    this.size = size;
    this.style = style;
    this.setWidthHeight();
  }
  
  static TextImage make (String text, float size, TextStyle style, Color color)
  {
      return new TextImage (text, size, style, color);
  }
  static TextImage make (String text, float size, TextStyle style, IColor color)
  {
      return new TextImage (text, size, style, color.thisColor());
  }
  static TextImage make (String text, float size, TextStyle style)
  {
      return new TextImage (text, size, style, Color.black);
  }
  static TextImage make (String text, float size, Color color)
  {
      return new TextImage (text, size, TextStyle.REGULAR, color);
  }
  static TextImage make (String text, float size, IColor color)
  {
      return new TextImage (text, size, TextStyle.REGULAR, color.thisColor());
  }
  static TextImage make (String text, float size)
  {
      return new TextImage (text, size, TextStyle.REGULAR, Color.black);
  }
  static TextImage make (String text, TextStyle style, Color color)
  {
      return new TextImage (text, TextImage.defaultSize, style, color);
  }
  static TextImage make (String text, TextStyle style, IColor color)
  {
      return new TextImage (text, TextImage.defaultSize, style, color.thisColor());
  }
  static TextImage make (String text, TextStyle style)
  {
      return new TextImage (text, TextImage.defaultSize, style, Color.black);
  }
  static TextImage make (String text, Color color)
  {
      return new TextImage (text, TextImage.defaultSize, TextStyle.REGULAR, color);
  }
  static TextImage make (String text, IColor color)
  {
      return new TextImage (text, TextImage.defaultSize, TextStyle.REGULAR, color.thisColor());
  }
  static TextImage make (String text)
  {
      return new TextImage (text, TextImage.defaultSize, TextStyle.REGULAR, Color.black);
  }
    
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    if (this.text == null)
      this.text = "";   
    // this.color cannot be null
    
    // save the current paint and font
    Paint oldPaint = g.getPaint();
    Font oldFont = g.getFont();
    
    // change the font style and size as given
    g.setFont(oldFont.deriveFont(this.style.toInt(), this.size));
    // set the paint to the given color
    g.setPaint(this.getColor());  
    
    // draw the object
    g.drawString(this.text, 0, this.ascent);
                 
    
    // reset the original paint and font
    g.setPaint(oldPaint);   
    g.setFont(oldFont);
  }

  
  /**
   * Compute and set the width and the height for this text 
   * in the given style and size.
   * 
   * Note that our translation tells where to put the top-left corner of the text, but
   * Graphics2D.draw works from the baseline, so we need to adjust by the ascent.
   */
  protected void setWidthHeight(){
    
    // change the font style and size as given
    g.setFont(font.deriveFont(this.style.toInt(), this.size));
    // now get this new font
    Font newFont = g.getFont();
    
    FontRenderContext frc = g.getFontRenderContext();
    
    TextLayout layout = new TextLayout(text, newFont, frc);

    g.setFont(font);
    
    Rectangle bounds = layout.getBounds().getBounds();
    // This is silly.  layout.getBounds() returns a Rectangle2D, but doesn't promise
    // what implementation of Rectangle2D it'll be.  Rectangle2D provides no way to
    // get the coordinates (although Rectangle2D.Double and Rectangle2D.Float both do),
    // so I have to use "getBounds()" on the Rectangle2D to get an int rectangle that
    // encloses the Rectangle2D.
    this.ascent = - bounds.y;
    this.height = bounds.height;
    this.width = bounds.width;
    
    
    System.out.println("Bounds: x = " + bounds.getX() +
                       "  y = " + bounds.getX() +
                       "  width = " + bounds.getWidth() +
                       "  height = " + bounds.getHeight());
  }

  /**
   * Produce the width of this image.
   * 
   * @return the (approximate) width of this image
   */
  public int getRight(){
    //return (int)(this.text.length() * 0.53 * this.size);
    return this.width;
  }

  
  /**
   * Produce the height of this text image (based on its size)
   * 
   * @return the height of this image
   */
  public int getBottom(){
    //return (int)this.size;
    return this.height;
  }
 
  /**
   * Produce a <code>String</code> that represents this image, 
   * indented by the given <code>indent</code>
   * 
   * @param indent the given prefix representing the desired indentation
   * @return the <code>String</code> representation of this image
   */
  public String toIndentedString(String indent){
    String newIndent = indent + "  ";
    return "new TextImage(this.text = \"" + this.text + 
        "\",\n" + newIndent + "this.style = " + this.style + 
        ",\n" + newIndent + "this.size = " + this.size + 
        ",\n" + newIndent + "this.color = " + this.getColor() +
        ",\n" + newIndent + this.cornerString() +
        ")";
  }
  
  /**
   * Is this <code>TextImage</code> same as the given object?
   */
  public boolean equals(Object o)
  {
      if (super.equals(o))
      {
          TextImage that = (TextImage)o;
          return this.size == that.size
            && this.style == that.style
            && this.text.equals(that.text)
            ;
      }
      else 
          return false;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode()
  {
    return super.hashCode() +
        this.getColor().hashCode() +
        (int)(this.size) +
        this.style.hashCode() +
        this.text.hashCode(); 
  }
  
  ColoredImage replaceColor (Color color)
  {
      return new TextImage (this.text, this.size, this.style, color);
    }    
    
    ColoredImage replaceMode (Mode newMode)
    {
        // do nothing, as text is always FILLED
        return this;
    }
    
}
