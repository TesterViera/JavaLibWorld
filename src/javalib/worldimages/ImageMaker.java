package javalib.worldimages;

import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.*;

/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

/**
 * <p>This class allows the user to read image data from a .png file
 * and save it as a <code>RenderedImage</code> in the format that
 * is used for the <code>World</code> when drawing on its canvas.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012, 9 August 2012
 */
public class ImageMaker{
	public int width;
	public int height;
    
	File inputfile;
	
	public static HashMap<String,BufferedImage> loadedImages = 
	new HashMap<String,BufferedImage>();
	
	/** the buffer that saves the user-generated image */
	public BufferedImage imageSource;
	public BufferedImage image;
	
	public ColorModel cmodel;  
	public static ColorModel canvasColorModel;
	public ColorConvertOp colorOp;
  
  /**
   * Default constructor, so we can define a subclass that uses
   * url for file name - used by applets
   */
  protected ImageMaker(){}
	
	/**
   * Construct the <code>BufferedImage</code> from the given file.
   * The file can be given as an URL, or a reference to local image file. 
   * If this image has been loaded already, use the saved version.
   * 
   * @param filename the file name for the desired image
   */
  public ImageMaker(String filename){
    /** now we set up the image file for the user to process */
    try{
      this.inputfile = new File(filename);
      String abs = inputfile.getCanonicalPath();
      if(loadedImages.containsKey(abs)){
        this.image = loadedImages.get(abs);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
      }
      else{
        this.imageSource = ImageIO.read(this.inputfile);
        this.width = this.imageSource.getWidth();
        this.height = this.imageSource.getHeight();
        
        this.cmodel = this.imageSource.getColorModel();
        this.image = 
        new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.colorOp = new ColorConvertOp(this.cmodel.getColorSpace(), 
                          image.getColorModel().getColorSpace(), null);
        this.colorOp.filter(this.imageSource, this.image);
        loadedImages.put(abs, this.image);
      }
    }    
		catch(IOException e){
			System.out.println("Could not open the file");
		}
	}
	
	/**
	 * Get the specified pixel in the <code>image</code>
	 * as an RGB color.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 */
	public Color getColorPixel(int x, int y){ 
		int pixel = this.image.getRGB(x, y);
		return new Color(pixel);
	}
	
}