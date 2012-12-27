package javalib.worldimages;

import javalib.colors.*;

import java.awt.*;

import java.util.Arrays;

 /**
 * <p>The class to represent filled Polygon images drawn by the
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author adapted by Stephen Bloch from Viera Proulx's TriangleImage class
 * @since Dec 24 2012
 */
class PolygonImage extends ColoredImage{
  protected Polygon poly;
  protected int[] xcoord, ycoord;
  private int left, top, right, bottom;

  /**
   * A full constructor for this Polygon image.
   * 
   * @param color the color for this image
   * @param mode either FILLED or OUTLINED
   * @param points the vertices of this Polygon
   */ 
  protected PolygonImage(Color color, Mode mode, Posn... points){
    super(color, mode);
    this.xcoord = new int[points.length];
    this.ycoord = new int[points.length];
    
    for (int ii = 0; ii < points.length; ++ii)
    {
        this.xcoord[ii] = points[ii].getX();
        this.ycoord[ii] = points[ii].getY();
    }
    this.poly = new Polygon(xcoord, ycoord, points.length);
    
    this.computeBounds();
  }
  
    static PolygonImage make(Color color, Mode mode, Posn... points)
    {
      return new PolygonImage (color, mode, points);
    }
    static PolygonImage make(IColor color, Mode mode, Posn... points)
    {
        return new PolygonImage (color.thisColor(), mode, points);
    }
    static PolygonImage make(Mode mode, Posn... points)
    {
        return new PolygonImage (Color.black, mode, points);
    }
    static PolygonImage make(Color color, Posn... points)
    {
      return new PolygonImage (color, Mode.OUTLINED, points);
    }
        static PolygonImage make(IColor color, Posn... points)
    {
        return new PolygonImage (color.thisColor(), Mode.OUTLINED, points);
    }
    static PolygonImage make(Posn... points)
    {
        return new PolygonImage (Color.black, Mode.OUTLINED, points);
    }
    
  
  private void computeBounds()
  {
      this.left = Integer.MAX_VALUE;
      this.right = Integer.MIN_VALUE;
      for (int coord : this.xcoord)
      {
          this.left = Math.min (this.left, coord);
          this.right = Math.max (this.right, coord);
      }
      
      this.top = Integer.MAX_VALUE;
      this.bottom = Integer.MIN_VALUE;
      for (int coord : this.ycoord)
      {
          this.top = Math.min (this.top, coord);
          this.bottom = Math.max (this.bottom, coord);
      }
    }

  /**
   * A protected constructor that takes in arrays of coordinates.
   * Assumes both arrays are the same, and that neither will be mutated after the call.
   * 
   * @param color
   * @param xcoord   an array of the x coordinates of the vertices
   * @param ycoord   an array of the y coordinates of the vertices
   */
  protected PolygonImage(Color color, Mode mode, int[] xcoord, int[] ycoord)
  {
      super(color, mode);
      this.xcoord = xcoord;
      this.ycoord = ycoord;
      this.poly = new Polygon(xcoord, ycoord, xcoord.length);
    }
      
  public int getLeft ()
  {
      return this.left;
  }
    
  public int getRight ()
  {
      return this.right;
  }
    
  public int getTop ()
  {
      return this.top;
  }
    
  public int getBottom ()
  {
      return this.bottom;
  }
 
  public void draw(Graphics2D g){
    // save the current paint
    Paint oldPaint = g.getPaint();
    // set the paint to the given color
    g.setPaint(this.getColor());  
    // draw the triangle
    if (this.getMode() == Mode.FILLED)
    {
        g.fill(poly);
    }
    else if (this.getMode() == Mode.OUTLINED)
    {
        g.draw(poly);
    }
    // reset the original paint
    g.setPaint(oldPaint);   
  }

  public ColoredImage replaceColor (Color newColor)
  {
      return new PolygonImage (newColor, this.getMode(), this.xcoord, this.ycoord);
  }
    
  public ColoredImage replaceMode (Mode newMode)
  {
      return new PolygonImage (this.getColor(), newMode, this.xcoord, this.ycoord);
  }
  
  /**
   * Is this <code>PolygonImage</code> same as the given object?
   */
  public boolean equals(Object o){
      return super.equals(o) &&
        Arrays.equals(this.xcoord, ((PolygonImage)o).xcoord) &&
        Arrays.equals(this.ycoord, ((PolygonImage)o).ycoord);
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
      int result = super.hashCode();
      for (int ii = 0; ii < xcoord.length; ++ii)
      {
          result = rotate(result, 2) +
          rotate(xcoord[ii], 1) +
          ycoord[ii];
        }
        return result;
  }
  
  public String toIndentedString (String indent) {
        String newIndent = indent + "  ";
        return "new PolygonImage(this.color = " + this.getColor() + 
        ",\n" + newIndent + "this.mode = " + this.getMode() +
        ",\n" + newIndent + "this.xcoord = " + this.xcoord +
        ",\n" + newIndent + "this.ycoord = " + this.ycoord +
        ",\n" + newIndent + this.cornerString() +
        ")";
    }

}
