package javalib.worldimages;

import java.net.URL;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * An image loaded from a URL over the network.
 * 
 * @author Stephen Bloch
 * @version Dec. 25, 2012
 */
public class FromURLImage extends RasterImage
{
    private String urlString;
    private URL url;
    
    /**
     * Pseudo-constructor for objects of class FromURLImage
     * 
     * @param urlString
     */
    static WorldImage make (String urlString)
    {
        try
        {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            return new FromURLImage (url);
        }
        catch (MalformedURLException e)
        {
            return null;
        }
        catch (URISyntaxException e)
        {
            return null;
        }
    }
    
    /**
     * Constructor for objects of class FromURLImage
     * 
     * @param url          the URL from which to get the image
     * @param urlString    
     */
    FromURLImage(URL url)
    {
        super();
        if (url == null) return;
        this.url = url;
        this.urlString = url.toString(); // should be normalized and encoded by now.
        
        if (LoadedImages.table.containsKey (this.urlString))
        {
            this.setRendering (LoadedImages.table.get(this.urlString));
        }
        else
        {
            try {
                BufferedImage img = ImageIO.read (this.url);
                LoadedImages.table.put(this.urlString, img);
                this.setRendering (img);
            }
            catch (java.io.IOException e)
            {
            }
        }
    }
    
    public String toIndentedString (String indent)
    {
        String newIndent = indent + "  ";
        return "new FromURLImage(this.url = \"" + this.urlString +
        "\",\n" + newIndent + this.cornerString() + ")";
    }
}
