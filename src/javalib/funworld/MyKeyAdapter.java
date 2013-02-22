package javalib.funworld;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * <p>The implementation of callbacks for the key events.</p>
 * <p>Report all regular key presses and the four arrow keys</p>
 * <p>Ignore other key pressed events, key released events, 
 * special keys, etc.</p>
 * 
 * @author Viera K. Proulx
 * @since August 2, 2007
 */
class MyKeyAdapter extends KeyAdapter {
	
	/** the current <code>{@link World World}</code> 
	 * that handles the key events */
	protected World currentWorld;
	
	/** the <code>KeyAdapter</code> that handles the key events */
	protected MyKeyAdapter(World currentWorld){
		this.currentWorld = currentWorld;
	}
	
	/**
	 * The callback for the key events
	 */
	protected void keyEventCallback(String keyString){
		this.currentWorld.processKeyEvent(keyString); 
	}
	
	// --------------------------------------------------------------------//
	// the key event handlers                                              //
	// --------------------------------------------------------------------//-
	
	/** 
	 * <p>Handle the key typed event from the canvas.</p>
	 * <p>This is where we get the letter keys.</p>
	 *
	 * @param e the <code>KeyEvent</code> that caused the callback
	 */
	public void keyTyped(KeyEvent e) {
		displayInfo(e, "KEY TYPED: ");
	}
	
	/** 
	 * <p>Handle the key pressed event from the canvas.</p>
	 * <p>This is where we get the arrow keys.</p>
	 *
	 * @param e the <code>KeyEvent</code> that caused the callback
	 */  
	public void keyPressed(KeyEvent e) {
		displayInfo(e, "KEY PRESSED: ");
	}
	
	/**
	 * <p>The key event major processor. The code is adopted from the code
	 * provided by the Java documentation for key event processing.</p>
	 * 
	 * <p><em>We have to jump through some hoops to avoid
	 * trying to print non-printing characters 
	 * such as Shift.  (Not only do they not print, 
	 * but if you put them in a String, the characters
	 * afterward won't show up in the text area.)</em></P>
	 * 
	 * <p>There may be unneeded code here. An earlier comment stated:
	 * <em>needs to add conversion table from code to 
	 * </em><code>String</code>. The code for converting a single character
	 * to <code>String</code> is included here.</p>
	 */
	protected void displayInfo(KeyEvent e, String s){
		String keyString ="";
		
		//You should only rely on the key char if the event
		//is a key typed event.
		int id = e.getID();
		if (id == KeyEvent.KEY_TYPED) {
			// regular letter has been pressed and released
			char c = e.getKeyChar();
			keyString = "" + c;
			
			// process four arrow keys when pressed
		} else if (id == KeyEvent.KEY_PRESSED){
			// check if pressed is one of the arrows
			int keyCode = e.getKeyCode();
			if (keyCode == 37)
				keyString = "left"; 
			else if (keyCode == 38)
				keyString = "up"; 
			else if (keyCode == 39)
				keyString = "right"; 
			else if (keyCode == 40)
				keyString = "down"; 
			
			else
				; // ignore other keys pressed
			
			// record the KEY_RELEASED event
		} else {
			keyString = "released";
		}
		
		//////////////////////////////////////////////////////////////////////////
		// here is the actual callback                                          //
		if (!(keyString.length() == 0 ||    // ignore key pressed except arrows //
			  keyString.equals("released")))  // ignore key released              //
			keyEventCallback(keyString);                                          //
		//////////////////////////////////////////////////////////////////////////
	} 
	// ---------------------------------------------------------------------
	// end of the key event major processor
}

