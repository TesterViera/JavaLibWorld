package javalib.worldimages;

import java.util.HashMap;
import java.awt.image.BufferedImage;

/**
 * Singleton class to hold a table of already-loaded images.
 * 
 * @author Stephen Bloch, based on Viera Proulx's ImageMaker class
 * @version Dec. 25, 2012
 */
public class LoadedImages
{
    private LoadedImages () { }
    public static HashMap<String,BufferedImage> table =
	new HashMap<String,BufferedImage> ();
}
