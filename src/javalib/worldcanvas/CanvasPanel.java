package javalib.worldcanvas;

import javalib.colors.*;
import javalib.worldimages.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.util.*;

import javax.swing.*;

/**
 * Copyright 2007, 2008 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * A buffered panel to hold the drawings.
 */
public class CanvasPanel extends JPanel {
	/////////////////
	// Member Data //
	/////////////////
	
	/** The buffered image that maintains the persistent graphics state. */
	protected transient BufferedImage buffer = null;
	
	/** The internal painter panel. */
	protected transient CanvasPanel.Painter painter = null;
	
	/** The width and height for this buffered panel. */
	protected int WIDTH;
	protected int HEIGHT;
	
	/**
	 * <p>Constructs a BufferedPanel containing a buffered image
	 * with the given width and height, 
	 * and the given background color or <code>Paint</code>
	 * for the image.</p>
	 *
	 * <p>If the given width or height is less than 1 pixel,
	 * that value is set to 1 pixel.</p>
	 *
	 * <p>Though the component itself may grow arbitrarily large,
	 * the buffered image painted to the buffer will
	 * remain the size specified in this constructor
	 * unless the size is reset using the
	 * <code>setBufferSize</code> method.</p>
	 *
	 * @param width      the width  of the buffered image
	 * @param height     the height of the buffered image
	 */
	public CanvasPanel(int width, int height) {
		super(new BorderLayout());
		
		// build the buffered image as a side effect
		setBufferSize(width, height);
		
		// build the painter panel using the inner Painter class
		makePainterPanelIfNeeded();
		
		// install the painter panel
		add(painter);
		
		ImageMaker.canvasColorModel = buffer.getColorModel();
	}
	
	/**
	 * Make a new painter panel if there isn't one already
	 */
	private void makePainterPanelIfNeeded() {
		if (painter == null)
			painter = new CanvasPanel.Painter(this);
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
	public synchronized void setBufferSize(int width, int height) {
		
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
		
		// refresh the enclosing window to account for the new buffer size
		// refreshComponent();
		Refresh.packParentWindow(this);
	}
	
	/** Returns the width of the buffered image. */
	public synchronized final int getBufferWidth() {
		return buffer.getWidth();
	}
	
	/** Returns the height of the buffered image. */
	public synchronized final int getBufferHeight() {
		return buffer.getHeight();
	}
	
	/** <p>Returns the internal buffered image for this panel.</p> */
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
	 *
	 * @since 1.1
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
		g2.setPaint((new White()).thisColor());
		g2.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
	}
	
	/**
	 * Draw the image from the given .png file at the specified 
	 * location
	 * @param fileName the image filename
	 * @param x the x coordinate for the NW corner
	 * @param y the y coordinate for the NW corner
	 */
	public void drawImage(String fileName, int x, int y){
		// read the given image file
		ImageMaker imread = new ImageMaker(fileName);
		
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
	
	/**
	 * Draw the image from the .png file that has been read by the given 
	 * <code>{@link ImageMaker ImageMaker}</code>.
	 * 
	 * @param imread the given image maker
	 * @param x the x coordinate for the NW corner
	 * @param y the y coordinate for the NW corner
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
	
	/**
	 * Draw the image from the .png file that has been read by the given 
	 * <code>{@link ImageMaker ImageMaker}</code>.
	 * 
	 * @param imread the given image maker
	 * @param x the x coordinate for the NW corner
	 * @param y the y coordinate for the NW corner
	 */
	public void drawImagePixels(ImageMaker imread, int x, int y){
		
		Graphics2D g = getBufferGraphics();
		
		// save the current paint
		Paint oldPaint = g.getPaint();
		/*
		 Color cc = imread.getColorPixel(4, 4);
		 
		 System.out.println("color is: " + cc.getRed() + ", " +
		 cc.getGreen() + ", " +  cc.getBlue() + 
		 " is white: " + this.isWhite(cc));
		 */
		Color c;
		for (int col = 0; col < imread.width; col++)
			for (int row = 0; row < imread.height; row++){
				c = imread.getColorPixel(col, row);
				if (!this.isWhite(c)){
					
					// set the paint to the given color
					g.setPaint(c);  
					// draw the object
					g.fillRect(x + col, y + row, 1, 1);
				}
			}
		
		// reset the original paint
		g.setPaint(oldPaint);   
		// repaint the panel
		repaint();        
	}
	
	protected boolean isWhite(Color c){
		return (c.getRed() == 255) && (c.getBlue() == 255) && (c.getGreen() == 255);
	}
	
	/**
	 * Draw the given image into this panel
	 * 
	 * @param image the image to draw
	 */
	public void drawImage(WorldImage image){
		Graphics2D g = getBufferGraphics();
		
		image.draw(g);
		repaint();  
	}
	public void getFonts(){
		Graphics2D g = getBufferGraphics();
		Font f = g.getFont();
		System.out.println("Font is: " + f.getName() + " style = " + f.getStyle() +
						   " size = " + f.getSize());
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
	public final boolean isFocusable() {
		return getInnerPanel().isFocusable();
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
	/* THE CODE BELOW HAS BEEN ADAPTED AND MODIFIED FROM THE FOLLOWING:
	 * 
	 * @(#)BufferedPanel.java    2.6.0e   19 November 2007
	 *
	 * Copyright 2007
	 * College of Computer and Information Science
	 * Northeastern University
	 * Boston, MA  02115
	 *
	 * The Java Power Tools software may be used for educational
	 * purposes as long as this copyright notice is retained intact
	 * at the top of all source files.
	 *
	 * To discuss possible commercial use of this software, 
	 * contact Richard Rasala at Northeastern University, 
	 * College of Computer and Information Science,
	 * 617-373-2462 or rasala@ccs.neu.edu.
	 *
	 * The Java Power Tools software has been designed and built
	 * in collaboration with Viera Proulx and Jeff Raab.
	 *
	 * Should this software be modified, the words "Modified from 
	 * Original" must be included as a comment below this notice.
	 *
	 * All publication rights are retained.  This software or its 
	 * documentation may not be published in any media either
	 * in whole or in part without explicit permission.
	 *
	 * This software was created with support from Northeastern 
	 * University and from NSF grant DUE-9950829.
	 */
	
	/**
	 * <p>Panel that paints the internal buffered image that
	 * maintains the persistent graphics state of the buffered 
	 * panel.</p>
	 *
	 * @author  Jeff Raab
	 * @author  Richard Rasala
	 * @author - adapted by Viera K. Proulx
	 * @version 2.4.0
	 * @since   1.0
	 */
	protected static class Painter extends JPanel {
		
		/**
		 * Reference to the <code>CanvasPanel</code> that created this
		 * <code>Painter</code>.
		 */
		protected CanvasPanel panel = null;
		
		/**
		 * Contructor that should only be called by a
		 * <code>CanvasPanel</code>.
		 *
		 * @param panel the <code>CanvasPanel</code> used to construct
		 *        this <code>Painter</code>
		 */
		protected Painter(CanvasPanel panel) {
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
				//panel.paintablesequence.paint(g);
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
		 * The hash table to collect windows being pack to prevent recursive
		 * calls to packParentWindow for the same window.
		 */
		private static Hashtable windowHashtable = new Hashtable();
		
		
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