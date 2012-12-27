package javalib.sbimages;

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
    private Image base;
    
    /**
     * Constructor for a frozen (memoized) image.
     * 
     * @param base    the existing image to freeze
     */
    private FreezeImage (Image base)
    {
        this.base = base;
    }
    
    /**
     * Pseudo-constructor for a frozen (memoized) image.
     * 
     * @param base    the existing image to freeze
     */
    static Image make (Image base)
    {
        if (base instanceof FreezeImage)
            return base;
        else
            return new FreezeImage (base);
    }
    
    private void renderIfNecessary()
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
