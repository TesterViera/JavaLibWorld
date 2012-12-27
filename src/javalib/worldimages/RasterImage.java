package javalib.worldimages;

import java.awt.image.RenderedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * An image stored explicitly in raster form.
 * 
 * @author Stephen Bloch
 * @version Dec. 25, 2012
 */
public class RasterImage extends AImage
{
    protected RenderedImage rendering; // includes width and height
    public static final AffineTransform id = AffineTransform.getTranslateInstance (0,0);
    
    /**
     * Constructor that takes in an already-rendered image.
     * 
     * @param rendering
     */
    protected RasterImage (RenderedImage rendering)
    {
        this.rendering = rendering;
    }
    
    /**
     * Default constructor so subclasses can start without a rendering and fill it in later.
     */
    protected RasterImage ()
    {
        this.rendering = null; // let's be explicit about this
    }
    
    /**
     * Pseudo-constructor.
     * 
     * @param rendering
     */
    static RasterImage make (RenderedImage rendering)
    {
        return new RasterImage (rendering);
    }
    
    /**
     * Setter so subclasses can fill in the rendering later.
     * 
     * @param rendering
     */
    protected void setRendering (RenderedImage rendering)
    {
        this.rendering = rendering;
    }
    
    public void draw (Graphics2D g)
    {
        if (this.rendering == null)
        {
            System.err.println ("This shouldn't happen: drawing a null RasterImage.");
            // TODO: display a "broken image" icon.
        }
        else
        {
            g.drawRenderedImage (this.rendering, RasterImage.id);
        }
    }
    
    public String toIndentedString (String indent)
    {
        String newIndent = indent + "  ";
        return "new RasterImage(hashCode = " + this.rendering.hashCode() +
            ",\n" + newIndent + this.cornerString() + ")";
    }
    
    public int getRight ()
    {
        return this.rendering.getWidth();
    }
    
    public int getBottom ()
    {
        return this.rendering.getHeight();
    }
    
    public boolean save (String filename)
    {
        try
        {
            java.io.File outputfile = new java.io.File(filename);
            boolean created = outputfile.createNewFile();
            return outputfile.canWrite() && javax.imageio.ImageIO.write (this.rendering, "png", outputfile);        
        }
        catch (java.io.IOException e)
        {
            return false;
        }
    }
}
