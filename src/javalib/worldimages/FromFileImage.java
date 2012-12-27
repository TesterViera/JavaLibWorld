package javalib.worldimages;

import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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
 * @author Viera K. Proulx, slightly modified by Stephen Bloch
 * @since February 4 2012, then Dec 12 2012
 */
public class FromFileImage extends RasterImage{
  
  /** is this being used with an application or an applet? */
  private static boolean isApplet = false;
  
  /** the file name for the image source */
  private String fileName;
  
  /** the file we're reading from */
  private File inputFile;
  
  /**
   * Pseudo-constructor for objects of class FromFileImage
   * 
   * @param fileName
   */
  static WorldImage make (String fileName)
  {
      if (isApplet)
      {
          return new FromURLImage (FromFileImage.class.getResource("/" + fileName));
      }
      else try {
          return new FromFileImage (new File (fileName));
        }
      catch (java.io.IOException e)
      {
          return null;
      }
  }
  
  /**
   * A full constructor for this image created from the file input
   * 
   * @param inputFile    the input file (already opened)
   */ 
  private FromFileImage(File inputFile) throws java.io.IOException
  {
    super();
    this.inputFile = inputFile;
    this.fileName = inputFile.getCanonicalPath();
    
    if (LoadedImages.table.containsKey (this.fileName))
    {
        this.setRendering (LoadedImages.table.get(this.fileName));
    }
    else
    {
        BufferedImage img = ImageIO.read (this.inputFile);
        LoadedImages.table.put(this.fileName, img);
        this.setRendering (img);
    }
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
    return "new FromFileImage(this.fileName = \"" + this.fileName + 
        "\",\n" + newIndent + this.cornerString() +
        ")";
  }
  
  /**
   * Is this <code>FromFileImage</code> same as the given object?
   */
  public boolean equals(Object o){
      return super.equals(o) && this.fileName.equals(((FromFileImage)o).fileName);
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return super.hashCode() + this.fileName.hashCode(); 
  }
}
