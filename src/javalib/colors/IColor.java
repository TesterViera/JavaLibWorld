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
  
  // the only method - provides the color represented by this class
  public Color thisColor();
}