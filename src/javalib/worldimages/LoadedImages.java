package javalib.sbimages;

import java.util.HashMap;
import java.awt.image.RenderedImage;

/**
 * Singleton class to hold a table of already-loaded images.
 * 
 * @author Stephen Bloch, based on Viera Proulx's ImageMaker class
 * @version Dec. 25, 2012
 */
public class LoadedImages
{
    private LoadedImages () { }
    public static HashMap<String,RenderedImage> table = new HashMap();
}
