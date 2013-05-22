package javalib.worldimages;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * An image with a memoized raster rendering.
 * 
 * @author Stephen Bloch
 * @version Dec. 26, 2012
 */
public class FreezeImage extends RasterImage
{
    private WorldImage base;
    
    /**
     * Constructor for a frozen (memoized) image.
     * 
     * @param base    the existing image to freeze
     */
    private FreezeImage (WorldImage base)
    {
        this.base = base;
    }
    
    /**
     * Pseudo-constructor for a frozen (memoized) image.
     * 
     * @param base    the existing image to freeze
     */
    static RasterImage make (WorldImage base)
    {
        if (base instanceof RasterImage)
            return (RasterImage)base;
        else
            return new FreezeImage (base);
    }
    
    public boolean equals (Object other)
    {
        return super.equals (other) &&
               this.base.equals (((FreezeImage)other).base);
               // note that if super.equals(other), they must be the same class,
               // so since this is a FreezeImage, other must be too, so the cast should work.
    }
    
    protected void renderIfNecessary()
    {
        if (this.rendering == null)
        {
            BufferedImage buffer = new BufferedImage (this.base.getWidth(), this.base.getHeight(), 
                                                      BufferedImage.TYPE_INT_ARGB);
            Graphics2D bufferG = buffer.createGraphics();
            this.base.draw(bufferG);
            this.setRendering (buffer);
        }
    }
    
    public void draw (Graphics2D g)
    {
        this.renderIfNecessary ();
        super.draw (g);
    }
    
    public int getRight ()
    {
        this.renderIfNecessary ();
        return super.getRight ();
    }
    
    public int getBottom ()
    {
        this.renderIfNecessary ();
        return super.getBottom ();
    }
}
