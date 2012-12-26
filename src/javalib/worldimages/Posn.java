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

  public Posn(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public String toString () {
      return "(" + this.x + ", " + this.y + ")";
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
  public Posn getMovedPosn (int dx, int dy) {
      return new Posn (this.x + dx, this.y + dy);
    }
}