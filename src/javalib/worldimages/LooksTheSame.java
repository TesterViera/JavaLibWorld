package javalib.worldimages;
import tester.Equivalence;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.image.DataBuffer;

/**
 * A singleton class to represent the operation of checking whether two images render the same.
 * 
 * @author Stephen Bloch
 * @version Dec. 31, 2012
 */
public class LooksTheSame implements Equivalence<WorldImage>
{
/**
 * The single instance of class LooksTheSame.
 */
    public static final LooksTheSame it = new LooksTheSame();
    
    private LooksTheSame () { }
    
    public boolean equivalent (WorldImage t1, WorldImage t2)
    {
        if (t1.equals(t2)) return true;
        
        RasterImage ft1 = (t1 instanceof RasterImage) ? (RasterImage)t1 : t1.frozen();
        RasterImage ft2 = (t2 instanceof RasterImage) ? (RasterImage)t2 : t2.frozen();
        
        ft1.renderIfNecessary();
        ft2.renderIfNecessary();
        
        // boolean answer = this.rendering.equals(((RasterImage)other).rendering);
        // doesn't work because BufferedImage.equals() doesn't do a deep comparison
        boolean answer = equalBufferedImages (ft1.rendering, ft2.rendering);
        
        return answer;
    }
    
    /**
     * Compare two BufferedImages for equal data, the way equals() should have done.
     * 
     * @param b1    a BufferedImage
     * @param b2    another BufferedImage
     * @return      true if they have the exact same data in their buffers
     * @since 29 Dec, 2012
     */
    static boolean equalBufferedImages (BufferedImage b1, BufferedImage b2)
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

}
