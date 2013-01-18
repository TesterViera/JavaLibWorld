package javalib.colors;


/**
 * To represent the entirely-transparent color.
 * 
 * @author Stephen Bloch
 * @version Dec. 2012
 */
public class Transparent implements IColor
{
  
  /** An instance of this Color */
  private static java.awt.Color myColor = new java.awt.Color(0, 0, 0, 0);
  
  public Transparent(){ 
    int i = 1;
    myColor = new java.awt.Color (0, 0, 0, 0);
    i = 2;
    }
  
  /**
   * Provide the <code>Color</code> represented by this class
   * @return black color
   */
  public java.awt.Color thisColor(){
    return new java.awt.Color (0, 0, 0, 0);
  }
  
  /**
   * Produce a <code>String</code> representation of this color
   */
  public String toString(){
    return "new Transparent()";
  }
}
