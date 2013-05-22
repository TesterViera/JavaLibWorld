package javalib.funworld;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javalib.worldimages.*;
import java.awt.Insets;


/**
 * <p>The implementation of callbacks for the mouse events: 
 * mouse clicked, entered, exited, pressed, and released.</p>
 * <p>We do not handle mouse motion events: mouse dragged and 
 * mouse moved.</p>
 * 
 * @author Viera K. Proulx
 * @since August 2, 2007
 */
class MyMouseAdapter extends MouseAdapter {
	
	/** the current <code>{@link World World}</code> 
	 * that handles the mouse events */
	protected World currentWorld;
	
	/** the mouse position recorded by the mouse event handler */
	protected Posn mousePosn;
	
	/**
	 * Create the mouse listener for the given <code>{@link World World}</code>.
	 * 
	 * @param currentWorld the given <code>{@link World World}</code>
	 */
	protected MyMouseAdapter(World currentWorld){
		this.currentWorld = currentWorld;
	}
	
	// ---------------------------------------------------------------------
	// the mouse event handlers
	
	/**
	 * Adjust the reported mouse position to account for the top bar
	 * 
	 * @param mousePosn the recorded mouse position
	 * @return the actual mouse position
	 */ 
	Posn adjustMousePosn(Posn mousePosn){
		// .... use this to find the height of the top bar
		Insets ins = this.currentWorld.theCanvas.f.getInsets();
		mousePosn.y -= ins.top;
		return mousePosn;
	}
	
	/**
	 * Invoked when the mouse has been clicked on a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mouseClicked(MouseEvent e) {
//	    System.out.println ("mouseClicked(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMouseClicked(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}

	/**
	 * Invoked when the mouse has been moved in a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mouseMoved(MouseEvent e) {
//	    System.out.println ("mouseMoved(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMouseMoved(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}
	
	/**
	 * Invoked when the mouse has been dragged in a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mouseDragged(MouseEvent e) {
//	    System.out.println ("mouseDragged(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMouseDragged(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}
	
	/**
	 * Invoked when the mouse enters a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mouseEntered(MouseEvent e) {
//	    System.out.println ("mouseEntered(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMouseEntered(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}
	
	/**
	 * Invoked when the mouse exits a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mouseExited(MouseEvent e) {
//	    System.out.println ("mouseExited(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMouseExited(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}
	
	/**
	 * Invoked when the mouse button has been pressed on a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mousePressed(MouseEvent e) {
//	    System.out.println ("mousePressed(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMousePressed(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}
	
	/**
	 * Invoked when a mouse button has been released on a component.
	 * 
	 * @param e the mouse event that invoked this callback
	 */
	public void mouseReleased(MouseEvent e) {
//	    System.out.println ("mouseReleased(" + e.getX() + ", " + e.getY() + ")");
		this.currentWorld.stopTimer = true;
		this.mousePosn = new Posn(e.getX(), e.getY());
		this.currentWorld = 
		this.currentWorld.processMouseReleased(adjustMousePosn(this.mousePosn));
		this.currentWorld.stopTimer = false;
	}
}

