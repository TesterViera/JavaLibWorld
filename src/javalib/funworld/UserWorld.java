package javalib.funworld;

import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;


/**
 * All the methods that students should/may override when writing a world.
 * 
 * @author Stephen Bloch based on Viera Proulx's World class.
 * @version Dec. 26, 2012
 */
public interface UserWorld
{

	/**
	 * <P>User defined method to be invoked by the timer on each tick.
	 * Produces a new <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @return <code>{@link World World}</code>
	 */
	public World onTick();  //
	
	/**
	 * <P>User defined method to be invoked by the key adapter 
	 * on selected key events.
	 * Produces a new <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @return <code>{@link World World}</code>
	 */
	public World onKeyEvent(String s);
	
	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is clicked.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @param mouse the location of the mouse when clicked
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseClicked(Posn mouse);

	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is entered.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @param mouse the location of the mouse when entered
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseEntered(Posn mouse);

	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is exited.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @param mouse the location of the mouse when exited
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseExited(Posn mouse);

	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is pressed.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @param mouse the location of the mouse when pressed
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMousePressed(Posn mouse);

	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is released.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class; default method does nothing.</P>
	 * 
	 * @param mouse the location of the mouse when released
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseReleased(Posn mouse);

	/**
	 * <P>User defined method to draw the <code>{@link World World}</code>.</P>
	 * <P>You <em>must</em> override this method in the game world class; there is no default.</P>
	 * 
	 * @return the image that represents this world at this moment
	 */
	public WorldImage makeImage();

  /**
   * <P>User defined method to draw the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class; default method returns
   * same result as makeImage.</P>
   * 
   * @return the image that represents the last world to be drawn
   */
	public WorldImage lastImage(String s);


}
