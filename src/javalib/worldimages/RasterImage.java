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
    {
        return super.equals(other) // same class, etc.
               &&
               this.sameRendering((RasterImage)other); // are the renderings pixel-for-pixel identical?
               // Note that if super.treeEquals(other), they must among other things be the same class,
               // so since this is a RasterImage, the other must be too, so the cast should work.
    }
    
    /**
     * Do this image and another image render the same?
     * 
     * @param other    another WorldImage
     */
    private boolean sameRendering (RasterImage other)
    {
        this.renderIfNecessary();
        other.renderIfNecessary();
        
        // boolean answer = this.rendering.equals(((RasterImage)other).rendering);
        // doesn't work because BufferedImage.equals() doesn't do a deep comparison
        boolean answer = equalBufferedImages (this.rendering, other.rendering);
        
        return answer;
    }
    
    /**
     * Are two WorldImages the same?
     * 
     * We decide this by rendering both of them and comparing the pixel maps.
     */
    public boolean same (WorldImage other)
    {
        if (other == null)
            return false;
        
        if (other instanceof RasterImage)
        {
            return this.sameRendering((RasterImage)other);
        }
        else
        {
            RasterImage rendered = other.frozen();
            boolean answer = this.sameRendering(rendered);
            return answer;
        }
    }

    /**
     * Compare two BufferedImages for equal data, the way equals() should have done.
     * 
     * @param b1    a BufferedImage
     * @param b2    another BufferedImage
     * @return      true if they have the exact same data in their buffers
     * @since 29 Dec, 2012
     */
    private static boolean equalBufferedImages (BufferedImage b1, BufferedImage b2)
    {
        // I'll ignore accelerationPriority.
        // I hope I can ignore colorModel.
        // I'm mostly interested in
        // each.getData(), which is a Raster.  Within the Raster, I'm mostly interested in
        // each.getDataBuffer(), which is a DataBuffer.
        // A DataBuffer doesn't expose its underlying array(s), so one might need to loop
        // over all the banks and all the elements, calling getElem() and comparing them.
        if (b1 == b2) return true;
        
        Raster r1 = b1.getData();
        Raster r2 = b2.getData();
        if (r1 == r2) return true;
        
        DataBuffer db1 = r1.getDataBuffer();
        DataBuffer db2 = r2.getDataBuffer();
        if (db1 == db2) return true;
        
        int banks1 = db1.getNumBanks();
        int banks2 = db2.getNumBanks();
        if (banks1 != banks2)
        {
            banks1 = banks1;
            return false;
        }
        
        int size1 = db1.getSize();
        int size2 = db2.getSize();
        if (size1 != size2)
        {
            size1 = size1;
            return false;
        }
        
        for (int bank = 0; bank < banks1; ++bank)
        {
            for (int ii = 0; ii < size1; ++ii)
            {
                if (db1.getElem(bank, ii) != db2.getElem(bank, ii))
                {   bank = bank;
                    ii = ii;
                    return false;
                }
            }
        }
        return true;
    }
    
    public int hashCode ()
    {
        this.renderIfNecessary ();
        
        return this.rendering.getData().getDataBuffer().hashCode();
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
        BufferedImage buffer = new BufferedImage (width, height,
        BufferedImage.TYPE_INT_ARGB);
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
}
