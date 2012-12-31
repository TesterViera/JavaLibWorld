package javalib.worldimages;


/**
 * A rectangular window on an existing image.
 * 
 * @author Stephen Bloch
 * @version Dec. 12, 2012
 */
public class Crop extends AImage
{
    private WorldImage base;
    private int left, right, top, bottom;

    /**
     * Pseudo-constructor for objects of class Crop
     * 
     * @param base     the image to crop
     * @param left, right, top, bottom   the borders to crop to
     */
    static WorldImage make (WorldImage base, int left, int right, int top, int bottom)
    {
        if (left <= base.getLeft() &&
            right >= base.getRight() &&
            top <= base.getTop() &&
            bottom >= base.getBottom())
            return base;
        else
            return new Crop (base, left, right, top, bottom);
    }
    
    /**
     * Constructor for objects of class Crop
     * 
     * @param base     the image to crop
     * @param left, right, top, bottom   the borders to crop to
     */
    Crop(WorldImage base, int left, int right, int top, int bottom)
    {
        this.base = base;
        this.left = Math.max(base.getLeft(), left);
        this.right = Math.min(base.getRight(), right);
        this.top = Math.max(base.getTop(), top);
        this.bottom = Math.min(base.getBottom(), bottom);
    }
    
    public void draw(java.awt.Graphics2D g) {
        java.awt.Shape oldClip = g.getClip();
        g.clipRect(this.left, this.top, this.getWidth(), this.getHeight());
        
        this.base.draw (g);
        
        g.setClip (oldClip);
    }
    
    public String toIndentedString (String indent)
    {
        String newIndent = indent + "  ";
        return "new Crop(this.base = " +
        "\n" + newIndent + this.base.toIndentedString (newIndent) +
        ",\n" + newIndent + this.cornerString() +
        ")";
    }
    
    public int getLeft()
    {
        return this.left;
    }
    
    public int getTop ()
    {
        return this.top;
    }
    
    public int getRight ()
    {
        return this.right;
    }
    
    public int getBottom ()
    {
        return this.bottom;
    }
    
    public boolean equals (Object other)
    {
        if (super.equals(other))
        {
            Crop otherCrop = (Crop)other;
            return this.base.equals(otherCrop.base) &&
                   this.left == otherCrop.left &&
                   this.right == otherCrop.right &&
                   this.top == otherCrop.top &&
                   this.bottom == otherCrop.bottom;
        }
        else return false;
    }
}
