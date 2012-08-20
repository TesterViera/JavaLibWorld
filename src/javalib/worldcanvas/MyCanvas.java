package javalib.worldcanvas;

import javalib.worldimages.*;
import javalib.colors.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

/**
 * Copyright 2007, 2008, 2009, 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * Abstract Canvas - allows the drawing of shapes, lines, and text 
 * in the window of the given size, window closing and re-opening.
 * 
 * @author Viera K. Proulx
 * @since July 12 2007, August 2, 2007, October 19, 2009
 */
public abstract class MyCanvas {

  /** the width of the panel */
  protected int width;

  /** the height of the panel */
  protected int height;

  /**
   * <P>Construct a <code>MyCanvas</code> with the given
   * width and height.<P>
   * 
   * @param width the width of the panel
   * @param height the height of the panel
   */
  public MyCanvas(final int width, final int height){
    this.width = width;
    this.height = height;
  }

  /**
   * <p>Returns a <code>Graphics2D</code> object that permits
   * painting to the internal buffered image for this canvas.</p>
   */
  public abstract Graphics2D getBufferGraphics(); 
  
  /////////////////////////////////////////////////////////////////////////
  // Methods for drawing and erasing shapes and text                     //
  /////////////////////////////////////////////////////////////////////////

  /**
   * Draw the image in the specified file at the location of the given
   * NW corner
   * 
   * @param fileName the name of the image file
   * @param nw the NW corner for the image placement
   */
  public abstract boolean drawImage(String fileName, Posn nw);
  
  /**
   * Draw the image provided by the <code>{@link ImageMaker ImageMaker}</code> 
   * on the <code>{@link WorldCanvas Canvas}</code> at the given
   * NW corner
   * 
   * @param image the image maker for the image file
   * @param nw the NW corner for the image placement
   */
  public abstract boolean drawImage(ImageMaker image, Posn nw);

  /**
   * Draw the image on the <code>{@link WorldCanvas Canvas}</code> at its
   * pinhole location.
   * 
   * @param image the image to be drawn
   * @return <code>true</code>
   */
  public abstract boolean drawImage(WorldImage image);

  /////////////////////////////////////////////////////////////////////////
  // Methods for showing and hiding the canvas                           //
  /////////////////////////////////////////////////////////////////////////

  /**
   * Clear the canvas before painting the next scene
   */
  public abstract void clear();

  /**
   * Produce a <code>String</code> representation of this Canvas
   */
  public String toString(){
    return "new Canvas(" + this.width + ", " + this.height + ")";
  }

  /**
   * Produce an indented <code>String</code> representation of this Canvas
   * @param indent the desired indentation: ignored, because we only produce
   * one line of text.
   */
  public String toIndentedString(String indent){
    return "new Canvas(" + this.width + ", " + this.height + ")";
  }
}
