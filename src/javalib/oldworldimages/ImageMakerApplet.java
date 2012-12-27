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
 * is used for the <code>World</code> when drawing on its 
 * <code>WorldCanvas</code>.</p>
 *
 * @author Viera K. Proulx
 * @since August 13 2012
 */
public class ImageMakerApplet extends ImageMaker{
  
  public ImageMakerApplet(String filename){   
    
    java.net.URL url = this.getClass().getResource("/" + filename);  
    
    /** now we set up the image file for the user to process */
    try{
      if (url == null)
        throw new IOException("file /" + filename + " not found");

      String abs = url.getFile();
      if(loadedImages.containsKey(abs)){
        this.image = loadedImages.get(abs);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
      }
      else{
        //System.out.println("loading image " + filename);
        //System.out.println("Path: " + url.getPath());
        this.imageSource = ImageIO.read(url);
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
}  
