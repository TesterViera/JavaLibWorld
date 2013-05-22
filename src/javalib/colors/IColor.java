package javalib.colors;

import java.awt.Color;

/**
 * An interface to create a union of six basic colors used in ProfessorJ
 * draw teachpack.
 * 
 * @author Viera K. Proulx
 * @since 12 March 2008
 *
 */
public interface IColor{
  
  /**
   * Convert an IColor to <tt>java.awt.Color</tt> for use with standard Java libraries.
   */
  public Color thisColor();
}