package javalib.sbimages;
import javalib.worldimages.WorldImage;


/**
 * An adapter to allow an sbimage to be used in Viera's worlds.
 * Not a good strategy for deployment with actual students; this is
 * just for side-by-side testing.
 * 
 * All SBImageAdapters have pinhole (0,0); we'll deal with locations
 * internally without bothering Viera's code.
 * 
 * @author Stephen Bloch
 * @version Dec. 5, 2012
 */
public class SBImageAdapter extends WorldImage
{
    private Image it;
    
    public SBImageAdapter (Image it)
    {
        super (new javalib.worldimages.Posn(0,0), java.awt.Color.white);
        this.it = it;
    }
    
    public String toIndentedString (String indent)
    {
        return this.it.toIndentedString (indent);
    }
    
    public String toString ()
    {
        return this.it.toString();
    }
    
    public int getHeight ()
    {
        return this.it.getHeight();
    }
    
    public int getWidth ()
    {
        return this.it.getWidth();
    }
    
    public WorldImage getMovedTo (javalib.worldimages.Posn p)
    {
        return new SBImageAdapter (this.it.getTranslated (p.x, p.y));
    }
    
    public WorldImage getMovedImage (int dx, int dy)
    {
        return new SBImageAdapter (this.it.getTranslated (dx, dy));
    }
    
    public void draw (java.awt.Graphics2D g)
    {
        this.it.draw (g);
    }
    
}
