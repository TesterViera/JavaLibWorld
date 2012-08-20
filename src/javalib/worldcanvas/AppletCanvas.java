package javalib.worldcanvas;

import javalib.colors.*;
import javalib.worldimages.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.util.*;


import javax.swing.*;

/**
 * Copyright 2008, 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * A class to represent a canvas for the applet to draw on
 * 
 * @author Viera K. Proulx
 * @since November 28, 2007
 */
public class AppletCanvas extends JPanel {

  /** The buffered image that maintains the persistent graphics state. */
  protected BufferedImage buffer = null;

  /** The internal painter panel. */
  public AppletCanvas.Painter painter = null;

  /** The width and height for this buffered panel. */
  protected int WIDTH;
  protected int HEIGHT;
  
  public static AppletCanvas theCanvas;
  
  /**
   * Create an instance of a CanvasPanel of the given dimensions.
   * 
   * @param width the width of the panel
   * @param height the height of the panel
   */
  public AppletCanvas(int width, int height) {
    super(true);
    
    // build the buffered image as a side effect
    setBufferSize(width, height);

    // build the painter panel using the inner Painter class
    //makePainterPanelIfNeeded();
    painter = new AppletCanvas.Painter(this);

    // install the painter panel
    add(painter);
    
    this.setPreferredSize(new Dimension(width, height));
    this.addNotify();
    this.setVisible(true);
  }

  /**
   * We must implement this method to make sure the 
   * canvas panel gets focus when moused over - and responds to the
   * key events appropriately.
   */
  public boolean isFocusable(){
    return true;
  }



  /** 
   * If the painter panel does not exist, create one
   */
  private void makePainterPanelIfNeeded() {
    if (painter == null)
      painter = new AppletCanvas.Painter(this);
  }

  /**
   * <p>Returns a <code>Graphics2D</code> object that permits
   * painting to the internal buffered image for this panel.</p>
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
    return buffer.createGraphics();
  }

  /**
   * <P>Sets the size of the buffered image 
   * to the given height and width.</P>
   *
   * <P>If the given width or height 
   * is less than 1 pixel, it is set to 1 pixel.</P>
   *
   * <P>Any image area gained by an size increase in either direction
   * will be painted with the current background color.</P>
   *
   * <P>Any image area lost by a size decrease in either direction
   * will be clipped on the right and/or bottom of the image.</P>
   *
   * <P>For a short time both the image of the previous size 
   * and an image of the new size are maintained in memory.</P>
   *
   * @param width  the new width  for the image
   * @param height the new height for the image
   */
  public void setBufferSize(int width, int height) {

    // ensure positive width and height
    width  = (int)Math.max(width,  1);
    height = (int)Math.max(height, 1);

    // save current buffer so we can later paint onto new buffer
    BufferedImage oldBuffer = buffer;

    // build the new buffered image
    buffer =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // clear the new buffered image
    clearPanel();

    // paint the old image to the new buffer if necessary
    if (oldBuffer != null) {
      Graphics2D g2 = getBufferGraphics();
      g2.drawImage(oldBuffer, 0, 0, this);
    }
  }

  /**
   * Returns the width of the buffered image.
   */
  public final int getBufferWidth() {
    return buffer.getWidth();
  }

  /**
   * Returns the height of the buffered image.
   */
  public  final int getBufferHeight() {
    return buffer.getHeight();
  }

  /**
   * <p>Returns the internal buffered image for this panel.</p>
   */
  public final BufferedImage getBuffer() {
    return buffer;
  }

  /**
   * <p>Returns the internal panel for this buffered panel,
   * that is, the panel that paints the buffered image
   * and handles the mouse and key adapters.</p>
   *
   * <p>This panel may be used when access to the panel on
   * which the graphics is drawn is needed.</p>
   *
   * <p>Do not set a border on this internal panel.  Set a
   * border on the outer <code>BufferedPanel</code> object.</p>
   */
  public final JPanel getInnerPanel() {
    makePainterPanelIfNeeded();

    return painter;
  }

  /**
   * <p>Fills this buffered panel 
   * with its background color or <code>Paint</code>.</p>
   */
  public final void clearPanel() {
    Graphics2D g2 = getBufferGraphics();
    g2.setPaint(Color.lightGray);
    g2.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
  }

  /**
   * <p>Fills this buffered panel 
   * with its background color or <code>Paint</code>.</p>
   */
  public final void clear() {
    Graphics2D g2 = getBufferGraphics();
    g2.setPaint(Color.lightGray);
    g2.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
  }
  /**
   * Draw the image in the specified file at the location of the given
   * NW corner
   * 
   * @param fileName the name of the image file
   * @param nw the NW corner for the image placement
   */
  public void drawImage(String fileName, Posn nw){
    // read the given image file
    ImageMaker imread = new ImageMaker(fileName);
    
    Graphics2D g = getBufferGraphics();
    // set up color conversion if different color models are used
    ColorConvertOp colorOp = 
    new ColorConvertOp(imread.cmodel.getColorSpace(), 
               buffer.getColorModel().getColorSpace(), null);
    // draw the given image at the given location
    g.drawImage(imread.image, colorOp, nw.x, nw.y);   
      
    // repaint the panel
    repaint();        
  }  

  /**
   * Draw the image provided by the <code>{@link ImageMaker ImageMaker}</code> 
   * on the <code>{@link AppletCanvas Canvas}</code> at the given
   * NW corner
   * 
   * @param imread the image maker for the image file
   * @param x the x coordinate for the image placement
   * @param y the y coordinate for the image placement
   */
  public void drawImage(ImageMaker imread, int x, int y){
    
    Graphics2D g = getBufferGraphics();
    // set up color conversion if different color models are used
    ColorConvertOp colorOp = 
    new ColorConvertOp(imread.cmodel.getColorSpace(), 
               buffer.getColorModel().getColorSpace(), null);
    // draw the given image at the given location
    g.drawImage(imread.image, colorOp, x, y);   
    
    // repaint the panel
    repaint();        
  }
  
  protected boolean isWhite(Color c){
    return (c.getRed() == 255) && (c.getBlue() == 255) && (c.getGreen() == 255);
  }

  /**
   * Draw the image on the <code>{@link AppletCanvas Canvas}</code> at its
   * pinhole location.
   * 
   * @param image the image to be drawn
   */
  public void drawImage(WorldImage image){
    Graphics2D g = getBufferGraphics();
    
    image.draw(g);
    repaint();  
  }
  

  /**
   * Produce a <code>String</code> representation of this Canvas
   */
  public String toString(){
    return "new AppletCanvas(" + this.getBufferWidth() + 
        ", " + this.getBufferHeight() + ")";
  }

  /**
   * Produce an indented <code>String</code> representation of this Canvas
   * @param indent the desired indentation: ignored, because we only produce
   * one line of text.
   */
  public String toIndentedString(String indent){
    return "new AppletCanvas(" + this.getBufferWidth() + 
        ", " + this.getBufferHeight() + ")";
  }

  /**
   * <p>Overrides to delegate to the inner panel that is
   * the panel returned by <code>getInnerPanel()</code>.</p>
   */
  public final void setFocusable(boolean focusable) {
    getInnerPanel().setFocusable(focusable);
  }

  /**
   * <p>Overrides to delegate to the inner panel that is
   * the panel returned by <code>getInnerPanel()</code>.</p>
   */
  public final boolean isRequestFocusEnabled() {
    return getInnerPanel().isRequestFocusEnabled();
  }

  /**
   * <p>Overrides to delegate to the inner panel that is
   * the panel returned by <code>getInnerPanel()</code>.</p>
   */
  public final void requestFocus() {
    getInnerPanel().requestFocus();
  }

  /**
   * <p>Overrides to delegate to the inner panel that is
   * the panel returned by <code>getInnerPanel()</code>.</p>
   */
  public final boolean requestFocusInWindow() {
    return getInnerPanel().requestFocusInWindow();
  }

  /**
   * <p>Override this <code>paintOver</code> method to add additional
   * painting actions after the default buffer repaint is done during
   * a <code>repaint()</code> call.</p>
   *
   * <p>The intention of this facility is to enable algorithmic
   * painting to be done via the <code>paintOver</code> method on
   * top of the default painting of the buffer image on the panel.
   * This makes the buffer appear to be the background and what is
   * painted via the <code>paintOver</code> method to be painted in
   * the foreground.</p>
   *
   * <p>The default implementation of the <code>paintOver</code>
   * method is to do nothing.  This enables overrides as desired.</p>
   * 
   * <p>As of 2.4.0, this method is called after both the painting of
   * the buffer and the painting of the internal paintable sequence.
   * Given the power inherent in painting both the buffer bitmap and
   * the internal paintable sequence, it is rare that this method
   * will need to be overridden.</p>
   *
   * @param g2 the <code>Graphics2D</code> context for the buffer
   *           repaint operation
   * @since 1.0.1
   */
  public void paintOver(Graphics2D g2) {
    // intentionally left empty to allow for overrides
  }

  ///////////////////
  // Inner classes //
  ///////////////////

  /**
   * <p>Panel that paints the internal buffered image that
   * maintains the persistent graphics state of the buffered 
   * panel.</p>
   *
   * <p>As of 2.4.0, paints the internal paintable sequence of
   * the buffered panel after painting the buffered image.</p>
   *
   * @author  Jeff Raab
   * @author  Richard Rasala
   * @version 2.4.0
   * @since   1.0
   */
  public static class Painter extends JPanel {

    /**
     * Reference to the <code>BufferedPanel</code> that created this
     * <code>Painter</code>.
     */
    protected AppletCanvas panel = null;
    
    /**
     * Constructor that should only be called by a
     * <code>BufferedPanel</code>.
     *
     * @param panel the <code>BufferedPanel</code> used to construct
     *        this <code>Painter</code>
     */
    protected Painter(AppletCanvas panel) {
      this.panel = panel;
    }

    /**
     * <p>Returns the size of the buffer as the size of this panel.</p>
     *
     * <p>Ignores any calls to <code>setPreferredSize</code>.</p>
     */
    public Dimension getPreferredSize() {
      return new Dimension(
          panel.getBufferWidth(), panel.getBufferHeight());
    }

    /**
     * <p>Paints the image buffer of the buffered panel in this panel
     * and then paints the buffered panel paintable sequence.</p>
     * 
     * <p>As of 2.6.0c, is synchronized on the enclosing buffered panel.</p>
     * 
     * @param g the standard graphics state for this panel
     */
    protected void paintComponent(Graphics g) {
      synchronized (panel) {
        Insets in = getInsets();
        int x = in.left;
        int y = in.top;

        g.drawImage(panel.getBuffer(), x, y, this);

        g.translate( x,  y);
        g.translate(-x, -y);
      }
    }


    /**
     * <p>Paints the component and then adds the work done by the
     * paintOver function.</p>
     * 
     * <p>As of 2.6.0c, is synchronized on the enclosing buffered panel.</p>
     * 
     * @param g the standard graphics state for this panel
     */
    public void paint(Graphics g) {
      synchronized (panel) {
        // this call will call paintComponent to paint the buffer
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        Insets in = getInsets();
        int x = in.left;
        int y = in.top;

        g2.translate( x,  y);
        panel.paintOver(g2);
        g2.translate(-x, -y);
      }
    }
  }

  /**
   * <P>Class <CODE>Refresh</CODE> encapsulates methods for graphics refresh.</P>
   *
   * <P>Class <CODE>Refresh</CODE> cannot be instantiated.</P>
   *
   * @author  Richard Rasala
   * @version 2.6.0c
   * @since   2.3
   */
  public static class Refresh {

    /** Private constructor to prevent instantiation. */
    private Refresh() {}

    /**
     * The hash table to collect windows being packed to prevent recursive
     * calls to packParentWindow for the same window.
     */
    private static Hashtable<Window,Window> windowHashtable = 
      new Hashtable<Window,Window>();


    /**
     * <p>Revalidates the given component, packs its parent window, and then
     * repaints the component.</p>
     *
     * <p>As of 2.3.3, prevents indirect recursive calls to this method that
     * attempt to pack the same window object.</p>
     * 
     * <p>As of 2.6.0c, makes the parent window invisible, then packs, and
     * then makes the parent window visible.</p>
     *
     * @param component the component whose parent window should be packed
     */
    public static void packParentWindow(JComponent component) {
      if (component == null)
        return;

      component.revalidate();

      JRootPane pane = component.getRootPane();

      if (pane != null) {
        Object parent = ((JRootPane) pane).getParent();

        if (parent instanceof Window) {

          synchronized(windowHashtable) {
            Window window = (Window) parent;

            if (! windowHashtable.containsKey(window)) {
              windowHashtable.put(window, window);
              window.setVisible(false);
              window.pack();
              window.setVisible(true);
              windowHashtable.remove(window);
            }
          }
        }
      }

      component.repaint();
    }
  }
}