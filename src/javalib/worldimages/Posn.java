package javalib.worldimages;


/**
 * To represent a point on the drawing 
 * <CODE>WorldCanvas</CODE> or <CODE>AppletCanvaas</CODE>
 * 
 * @author Viera K. 
 * @since August 2, 2007
 */
public class Posn {
  public int x;
  public int y;
  
  public static final Posn origin = new Posn(0,0);

  public Posn(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
      return this.x;
    }
    
  public int getY() {
      return this.y;
    }
  
  public String toString ()
  {
      return "(" + this.x + ", " + this.y + ")";
  }
    
  public String toIndentedString (String indent)
  {
      return this.toString(); // no internal newlines, so ignore the indent
  }
    

    /**
     * Get a translated version of the given posn.
     * Functional: does NOT modify this!
     * 
     * @param dx     the amount to translate in x dimension
     * @param dy     the amount to translate in y dimension
     * @return a new Posn
     * @since Dec. 2, 2012
     * @author Stephen Bloch
     */
  public Posn moved (int dx, int dy) {
      return new Posn (this.x + dx, this.y + dy);
    }
    
  /**
   * Add another Posn to this one.
   * Functional: does NOT modify this!
   * 
   * @param other   another Posn
   * @return a new Posn whose coordinates are the pointwise sum of this and other
   * @since Dec. 12, 2012
   * @author Stephen Bloch
   */
  public Posn plus (Posn other) {
      return this.moved (other.x, other.y);
    }
  
  /**
   * Subtract another Posn from this one.
   * Functional: does NOT modify this!
   * 
   * @param other   another Posn
   * @return a new Posn whose coordinates are the pointwise difference of this and other
   * @since Dec. 12, 2012
   * @author Stephen Bloch
   */
  public Posn minus (Posn other) {
      return this.moved (- other.x, - other.y);
    }
    
  public boolean equals (Object other) {
        return this.getClass().equals(other.getClass()) &&
            ((Posn)other).getX() == this.x &&
            ((Posn)other).getY() == this.y;
        }
    
  public int hashCode () {
        return super.hashCode() + AImage.rotate(this.x, 16) + this.y;
    }
}
