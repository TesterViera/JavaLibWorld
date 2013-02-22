package javalib.worldimages;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.image.DataBuffer;
import java.awt.Color;

/**
 * An image stored explicitly in raster form.
 * 
 * @author Stephen Bloch
 * @version Dec. 25, 2012
 */
public class RasterImage extends AImage
{
    protected BufferedImage rendering; // includes width and height
    // I don't like declaring this by its implementation type -- I would have preferred RenderedImage --
    // but in fact it IS always a BufferedImage, and I need to know that in order to compare two of them
    // because BufferedImage doesn't have a sensible equals() method.
    public static final AffineTransform id = AffineTransform.getTranslateInstance (0,0);
    static final Color transparent = new Color(0,0,0,0);
    
    /**
     * Constructor that takes in an already-rendered image.
     * 
     * @param rendering
     */
    protected RasterImage (BufferedImage rendering)
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
    static RasterImage make (BufferedImage rendering)
    {
        return new RasterImage (rendering);
    }
    
    /**
     * Setter so subclasses can fill in the rendering later.
     * 
     * @param rendering
     */
    protected void setRendering (BufferedImage rendering)
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
    
    /**
     * If it's already a RasterImage, that's good enough.
     */
    public RasterImage frozen ()
    {
        return this;
    }

    /**
     * make sure rendering isn't null
     */
    protected void renderIfNecessary()
    {
        if (this.rendering == null)
        {
            throw new RuntimeException
                ("This shouldn't happen: RasterImage has null rendering but isn't a FreezeImage.");
        }
    }
    
    public boolean equals (Object other)
    {   if (super.equals(other)) // same class, etc.
        {
            this.renderIfNecessary();
            RasterImage otherR = (RasterImage)other;
            otherR.renderIfNecessary();
            return LooksTheSame.equalBufferedImages (this.rendering, otherR.rendering);
        }
        else return false;
    }
        
    public int hashCode ()
    {
        this.renderIfNecessary ();
        
        return super.hashCode() + this.rendering.hashCode();
    }
    
    public int getRight ()
    {
        this.renderIfNecessary();
        return this.rendering.getWidth();
    }
    
    public int getBottom ()
    {
        this.renderIfNecessary();
        return this.rendering.getHeight();
    }
    
    public boolean save (String filename)
    {
        this.renderIfNecessary();
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
    private static void colorToIntArray (Color c, int[] components)
    {
        components[0] = c.getRed();
        components[1] = c.getGreen();
        components[2] = c.getBlue();
        components[3] = c.getAlpha();
    }

    private static Color intArrayToColor (int[] components)
    {
        return new Color (components[0],
                          components[1],
                          components[2],
                          components[3]);
    }

    public static WorldImage build (int width, int height, ImageBuilder b, Object extra)
    {
        BufferedImage buffer = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);
        // see DirectColorModel, SinglePixelPackedSampleModel
        WritableRaster raster = buffer.getRaster();
    /*
       Can probably ignore the following...
       SampleModel sm = raster.getSampleModel();
       DataBuffer db = raster.getDataBuffer();
       int bands = raster.getNumBands(); // what is this?
    */
        int[] colorComponents = new int[4];

        for (int col=0; col<width; ++col)
        {
            for (int row=0; row<height; ++row)
            {
                Color c = b.pixelColor (col, row, extra);
                colorToIntArray (c, colorComponents);
                raster.setPixel (col, row, colorComponents);
            }
        }
        return new RasterImage (buffer);
    }

    public WorldImage map (ImageMap b, Object extra)
    {
        int width = this.getWidth();
        int height = this.getHeight();

        Raster srcRaster = this.rendering.getData();
        boolean hasAlpha = this.rendering.getColorModel().hasAlpha();
                           // (srcRaster.getNumBands()==4);
        
        BufferedImage buffer = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster dstRaster = buffer.getRaster();
        

        int[] colorComponents = new int[4];
        
        if (! hasAlpha) colorComponents[3] = 255;
    
        for (int col = 0; col<width; ++col)
        {
            for (int row = 0; row<height; ++row)
            {
                srcRaster.getPixel (col, row, colorComponents);
                // if (! hasAlpha), this won't touch colorComponents[3] so it's still 255
                Color srcColor = intArrayToColor (colorComponents);
                Color dstColor = b.pixelColor (col, row, srcColor, extra);
                colorToIntArray (dstColor, colorComponents);
                dstRaster.setPixel (col, row, colorComponents);
            }
        }
        return new RasterImage (buffer);
    }


    public Color getPixelColor (int x, int y)
    {
        this.renderIfNecessary();
        int width = this.getWidth();
        int height = this.getHeight();
        if (x < 0 || y < 0 || x >= width || y >= height)
            return transparent;
        
        Raster srcRaster = this.rendering.getData();
        boolean hasAlpha = this.rendering.getColorModel().hasAlpha();
        int[] colorComponents = new int[4];
        if (! hasAlpha) colorComponents[3] = 255;
        srcRaster.getPixel (x, y, colorComponents);
        return intArrayToColor (colorComponents);
    }
}
