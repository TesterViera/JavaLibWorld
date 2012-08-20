package javalib.worldimages;

import java.awt.*;
import java.awt.geom.*;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent an image that came from a .png file and 
 * is to be drawn by the  
 * world when drawing on its <code>Canvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class FromFileImage extends WorldImage{
  
  /** is this being used with an application or an applet? */
  public static boolean isApplet = false;
  
  /** the file name for the image source */
  public String fileName;
  
  /** the instance of the class that handles reading of files just once 
   * set to be transient, so that it is not used in comparisons by tester lib*/
  protected volatile ImageMaker imread;
  
  /** the affine transform for moving the images to their pinhole locations */
  protected transient AffineTransform at;
  
  /**
   * A full constructor for this image created from the file input
   * 
   * @param pinhole the pinhole location (the center) for this image
   * @param fileName the file name for the image source
   */ 
  public FromFileImage(Posn pinhole, String fileName){
    super(pinhole, Color.white);
    
    // determine how to read the file name
    // then read the image, or verify that it has been read already    
    if (isApplet)
      this.imread = new ImageMakerApplet(fileName);
    else
      this.imread = new ImageMaker(fileName);
    /*
    this.pinhole.x = pinhole.x - (this.imread.width / 2);
    this.pinhole.y = pinhole.y - (this.imread.height / 2);

    
    System.out.println("Image width = " + this.imread.width);
    System.out.println("Image height = " + this.imread.height);
    
    System.out.println("pinhole.x = " + this.pinhole.x);
    System.out.println("pinhole.y = " + this.pinhole.y);
    */
    
    // initialize the filename and the affine transform
    this.fileName = fileName;
    
    // for drawing we supply the coordinates of the NW corner of the image
    this.at = 
    AffineTransform.getTranslateInstance(this.pinhole.x - this.imread.width / 2, 
                                       this.pinhole.y - this.imread.height / 2);   
  }
  
  /** 
   * Draw this image in the provided <code>Graphics2D</code> context.
   * 
   * @param g the provided <code>Graphics2D</code> context
   */
  public void draw(Graphics2D g){
    // recompute the affine transform, as teh pinhole may have moved
    this.at = 
        AffineTransform.getTranslateInstance(this.pinhole.x - this.imread.width / 2, 
                                           this.pinhole.y - this.imread.height / 2);   
    // save the current paint
    Paint oldPaint = g.getPaint();
    // set the paint to the given color
    //g.setPaint(color);  
    
    // draw the given image at the given location
    g.drawRenderedImage(this.imread.image, this.at);
    
    // reset the original paint
    g.setPaint(oldPaint);          
  }
  
  /**
   * <p>Provide a method for comparing two images constructed from image files
   * to be used by the <em>tester</em> library.</p>
   * 
   * <p>This requires the import of the tester library. The comparison involves
   * only the file names and the location of the pinholes.</p>
   */
  public boolean same(FromFileImage that){
    return
    this.fileName.equals(that.fileName) &&
    this.pinhole.x == that.pinhole.x &&
    this.pinhole.y == that.pinhole.y;
  }

  /**
   * Produce the file-based images with the pinhole moved by the given (dx, dy)
   * 
   * @param dx the horizontal offset
   * @param dy the vertical offset
   */
  public WorldImage getMovedImage(int dx, int dy){
    return  
        new FromFileImage(this.movePosn(this.pinhole, dx, dy), this.fileName);
  }

  /**
   * Produce the file-based with the pinhole moved to the given location
   * 
   * @param p the given location
   */
  public WorldImage getMovedTo(Posn p){
    int dx = p.x - pinhole.x;
    int dy = p.y - pinhole.y;
    return this.getMovedImage(dx, dy);
  }

  /**
   * Produce the width of this image
   * 
   * @return the width of this image
   */
  public int getWidth(){
    return this.imread.width;
  }
  
  /**
   * Produce the height of this image
   * 
   * @return the height of this image
   */
  public int getHeight(){
    return this.imread.height;
  }

  
  /**
   * Produce a <code>String</code> representation of this from-file image
   */
  public String toString(){
    return "new FromFileImage(this.pinhole = (" 
        + this.pinhole.x + ", " + this.pinhole.y +  
        ")\nthis.fileName = " + this.fileName +")\n";
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
    return classNameString(indent, "FromFileImage") + 
        pinholeString(indent, this.pinhole) + 
        ")\n" + indent + "this.fileName = " + this.fileName +")\n";
  }
  
  /**
   * Is this <code>FromFileImage</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof FromFileImage){
      FromFileImage that = (FromFileImage)o;
      return this.same(that);
    }
    else 
      return false;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return this.pinhole.x + this.pinhole.y + this.color.hashCode() +
        this.fileName.hashCode(); 
  }
}