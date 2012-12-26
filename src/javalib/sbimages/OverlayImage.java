package javalib.sbimages;
// import java.awt.geom.AffineTransform;


/**
 * The overlay of two images.
 * 
 * @author Stephen Bloch
 * @version Dec. 5, 2012
 */
public class OverlayImage extends AImage
{
    private Image back, front;

    /**
     * Constructor for objects of class OverlayImage
     * 
     * @param back   an image to be drawn "in the background"
     * @param front  an image to be drawn "in the foreground"
     */
    public OverlayImage(Image back, Image front)
    {
        // initialise instance variables
        super();
        this.back = back;
        this.front = front;
    }    
        
    public boolean equals (Object other)
    {
        return super.equals(other) &&
               this.back.equals (((OverlayImage)other).back) &&
               this.front.equals (((OverlayImage)other).front);
    }
    
    public int hashCode ()
    {
        return super.hashCode() + rotate(this.back.hashCode(), 16) + this.front.hashCode();
    }
            
    /**
     * Getter for the background part of an Overlay.
     * 
     * @return the background image
     */
    public Image getBack ()
    {
        return this.back;
    }
    
    /**
     * Getter for the foreground part of an Overlay.
     * 
     * @return the foreground image
     */
    public Image getFront ()
    {
        return this.front;
    }
    
    public int getTop ()
    {
        return Math.min (this.back.getTop(), this.front.getTop());
    }
    
    public int getBottom ()
    {
        return Math.max (this.back.getBottom(), this.front.getBottom());
    }
    
    public int getLeft () 
    {
        return Math.min (this.back.getLeft(), this.front.getLeft());
    }
    
    public int getRight ()
    {
        return Math.max (this.back.getRight(), this.front.getRight());
    }
    
    public int getWidth ()
    {
        return this.getRight() - this.getLeft();
    }
    
    public int getHeight ()
    {
        return this.getBottom() - this.getTop();
    }
    
    public String toIndentedString (String indent)
    {
        String newIndent = indent + "  ";
        return "new OverlayImage(" +
               "\n" + newIndent + "this.back = " +
               this.back.toIndentedString(newIndent) +
               ",\n" + newIndent + "this.front = " +
               this.front.toIndentedString(newIndent) +
               ",\n" + newIndent + this.cornerString() +
        ")";
    }
    
    public void draw(java.awt.Graphics2D g) {
        this.back.draw (g);
        this.front.draw (g);
    }
            
    
    


}
