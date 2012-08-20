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
 * Functional Canvas - allows the drawing of shapes, lines, and text 
 * in the window of the given size, window closing and re-opening.
 * 
 * @author Viera K. Proulx
 * @since July 12 2007, August 2, 2007, October 19, 2009
 */
public class WorldCanvas {

  /** records the number of canvases currently open */
  protected static int WINDOWS_OPEN = 0;

  /** the frame that holds the canvas */
  public transient JFrame f;

  /** the panel that allows us to paint graphics */
  public transient CanvasPanel panel;

  /** the width of the panel */
  protected int width;

  /** the height of the panel */
  protected int height;

  /**
   * <P>Construct a new frame with the 
   * <CODE>{@link CanvasPanel CanvasPanel} panel</CODE> as its component.<P>
   * 
   * @param width the width of the panel
   * @param height the height of the panel
   * @param title the title of the panel
   */
  public WorldCanvas(final int width, final int height, final String title){
    this.width = width;
    this.height = height;

    // Label the frame as "Canvas" and set up the layout
    f = new JFrame(title);
    f.setLayout(new BorderLayout());

    // End the application when the last window closes
    f.addWindowListener(winapt);

    // if the user closes the Canvas window
    // it will only hide and can be reopened by invoking 'show'
    f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    // set up the panel and the graphics
    panel = new CanvasPanel(width, height);
    panel.addNotify();
    f.getContentPane().add(panel, BorderLayout.CENTER);

    f.pack();
    Graphics g = panel.getGraphics();
    f.update(g);
    f.setVisible(false);

    // do not update the counter of open windows
    // this is done when the Canvas is 'show'-n
    // WINDOWS_OPEN = WINDOWS_OPEN + 1;
    // System.out.println(WINDOWS_OPEN + " open windows.");
  }

  /**
   * A WindowAdapter that allows us to close a window and re-open, 
   * provided there is at least one open window. The program ends 
   * when all windows have been closed.
   */
  protected transient WindowAdapter winapt = new WindowAdapter(){ 
    public void windowClosing(WindowEvent e) {
      //System.out.println("hiding the window");
      WINDOWS_OPEN = WINDOWS_OPEN - 1;
      //closeCanvas();
      panel.clearPanel();

      // diagnostics showing the state of the WINDOWS_OPEN constant
      // System.out.println(WINDOWS_OPEN + " open windows.");

      if (WINDOWS_OPEN == 0)
        System.exit(0);
    }
  };

  /**
   * Create a new canvas with the default title "Canvas"
   * 
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  public WorldCanvas(int width, int height){
    this(width, height, "Canvas"); 
  }

  /**
   * <p>Returns a <code>Graphics2D</code> object that permits
   * painting to the internal buffered image for this canvas.</p>
   *
   * <p>The user should always use this object to paint to the
   * buffer and thus indirectly modify this buffered panel.</p>
   *
   * <p>To make painting changes to the buffer visible, the
   * <code>repaint()</code> method must explicitly be called.
   * This allows a number of painting operations to be done
   * prior to screen repaint.</p>
   */
  public final Graphics2D getBufferGraphics() {
    return panel.getBufferGraphics();
  }
  
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
  public boolean drawImage(String fileName, Posn nw){
    ((CanvasPanel)panel).drawImage(fileName, nw.x, nw.y);
    return true;
  }  

  /**
   * Draw the image provided by the <code>{@link ImageMaker ImageMaker}</code> 
   * on the <code>{@link WorldCanvas Canvas}</code> at the given
   * NW corner
   * 
   * @param image the image maker for the image file
   * @param nw the NW corner for the image placement
   */
  public boolean drawImage(ImageMaker image, Posn nw){
    ((CanvasPanel)panel).drawImagePixels(image, nw.x, nw.y);

    //((CanvasPanel)panel).drawImage(image, nw.x, nw.y);
    return true;
  }

  /**
   * Draw the image on the <code>{@link WorldCanvas Canvas}</code> at its
   * pinhole location.
   * 
   * @param image the image to be drawn
   * @return <code>true</code>
   */
  public boolean drawImage(WorldImage image){
    ((CanvasPanel)panel).drawImage(image);
    return true;
  }  

  public void printCurrentFont(){
    ((CanvasPanel)panel).getFont();
  }



  /////////////////////////////////////////////////////////////////////////
  // Methods for showing and hiding the canvas                           //
  /////////////////////////////////////////////////////////////////////////

  /**
   * Show the window with the canvas cleared
   * 
   * @return <code>true</code> if successfully opened, or opened already
   */
  public boolean show(){
    // check if the widow is already open
    if (!f.isVisible()){

      // account for the open window, make it appear
      WINDOWS_OPEN = WINDOWS_OPEN + 1;
      f.setVisible(true);
      // redraw the background
      return true;
    }
    else{
      // do nothing if the window is open already
      //System.out.println("The window is shown already");
      return true;
    }
  }

  /**
   * Close the window - if it is currently open, do nothing otherwise
   * 
   * @return <code>true</code> if successfully closed, or closed already
   */
  public boolean close(){
    if (f.isVisible()){
      WINDOWS_OPEN = WINDOWS_OPEN - 1;
      f.setVisible(false);
      panel.clearPanel();
    }
    return true;
  }

  /**
   * Clear the canvas before painting the next scene
   */
  public void clear(){
    this.panel.clearPanel();
  }

  /**
   * Helper method to display a message and await RETURN before proceeding
   * 
   * @param message the message to display
   */
  private static void nextStep(String message){
    try{
      System.out.println(message);
      System.out.println("Press RETURN");     
      // no input needed - the default input is a valid integer
      int n = System.in.read();
    }
    catch(IOException e){
      System.out.println("Next step");
    }
  }

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

  /**
   * Self test for the Canvas class
   * 
   * @param argv
   */
  public static void main(String[] argv){
    nextStep("Canvas with default name is constructed");
    WorldCanvas sm1 = new WorldCanvas(200, 200);

    nextStep("To show the canvas ... ");
    sm1.show();

    nextStep("Canvas shown - should be blank - add red and blue disk");
    sm1.drawImage(new CircleImage(new Posn(50, 50), 20, new Red()));
    sm1.drawImage(new CircleImage(new Posn(150, 50), 50, new Blue()));

    nextStep("Show the canvas again - it should not do anything");
    sm1.show();

    nextStep("Draw a green disk");
    sm1.drawImage(new CircleImage(new Posn(50, 150), 50, new Green()));

    nextStep("Close the Canvas");
    sm1.close();

    nextStep("Show the canvas again - it should be cleared");
    sm1.show();

    nextStep("Paint one disks on the canvas");   
    sm1.drawImage(new CircleImage(new Posn(50, 150), 25, new Black()));

    nextStep("Construct a second canvas with the name Smiley");
    WorldCanvas sm2 = new WorldCanvas(200, 200, "Smiley");

    nextStep("Show the second canvas");
    sm2.show();

    nextStep("Paint two disks on the Smiley canvas");   
    sm2.drawImage(new CircleImage(new Posn(50, 50), 20, new Red()));
    sm2.drawImage(new CircleImage(new Posn(150, 150), 50, new Blue()));

    nextStep("Manually close the 'Canvas' window" + 
        "and see if we can bring it back to life");   
    sm1.show();

    nextStep("The first canvas should be shown - cleared");    
    sm1.drawImage(new CircleImage(new Posn(50, 50), 30, new Red()));
    sm1.drawImage(new CircleImage(new Posn(150, 50), 30, new Blue()));
    sm1.drawImage(new CircleImage(new Posn(50, 150), 30, new Green()));
    nextStep("The first canvas has three disks drawn"); 

    System.out.println("Close both canvas windows to end the program");      
  }
}






