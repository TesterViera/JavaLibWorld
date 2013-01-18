package javalib.colors;

import java.awt.Color;
/**
 * To represent the black color -- in the style of
 * ProfessorJ <code>draw</code> package.
 * 
 * @author Viera K. Proulx
 * @since March 12, 2008
 */
public class Black implements IColor{
  
  /** An instance of this Color */
  private static Color myColor = new Color(0, 0, 0);
  
  public Black(){ }
  
  /**
   * Provide the <code>Color</code> represented by this class
   * @return black color
   */
  public Color thisColor(){
    return myColor;
  }
  
  /**
   * Produce a <code>String</code> representation of this color
   */
  public String toString(){
    return "new Black()";
  }
}