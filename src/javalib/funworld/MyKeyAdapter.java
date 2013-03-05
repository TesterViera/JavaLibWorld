package javalib.funworld;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * <p>The implementation of callbacks for the key events.</p>
 * <p>Report all regular key presses, the four arrow keys, and some other
 * special keys (backspace, delete, escape, page up, page down, home, end,
 * tab, fkeys)</p>
 * <p>Ignore other key pressed events, key released events, etc.</p>
 * 
 * @author Viera K. Proulx and Stephen Bloch
 * @since August 2, 2007
 * @version March 5, 2013
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
     */
    protected void displayInfo(KeyEvent e, String s){
        String keyString = "ignore";
        
        int id = e.getID();
        
        if (id == KeyEvent.KEY_TYPED) {
            //You should only rely on the key char if the event
            //is a key typed event.
            // regular letter has been pressed and released
            char c = e.getKeyChar();
            int code = (int)c;
            keyString = code == 8 ? "backspace" :
                        code == 9 ? "tab" :
                        code == 10 ? "newline" :
                        code == 127 ? "delete" :
                        // These produce BOTH a KEY_TYPED and a KEY_PRESSED event
                        "" + c;
            System.err.println ("Got KEY_TYPED event with char " + (int)c);
            
            // process four arrow keys when pressed
        } else if (id == KeyEvent.KEY_PRESSED){
            int keyCode = e.getKeyCode();
            System.err.println ("Got KEY_PRESSED event with code " + keyCode);
            keyString = // keyCode == 8 ? "backspace" :
                        // keyCode == 9 ? "tab" :
                        // keyCode == 10 ? "newline" :
                        // keyCode == 127 ? "delete" :
                        // already got these from the KEY_TYPED event, so don't return them again
                        // 16 => shift
                        // 17 => control
                        // 18 => alt/option/menu
                        keyCode == 27 ? "escape" :
                        keyCode == 33 ? "page up" :
                        keyCode == 34 ? "page down" :
                        keyCode == 35 ? "end" :
                        keyCode == 36 ? "home" :
                        keyCode == 37 ? "left" :
                        keyCode == 38 ? "up" :
                        keyCode == 39 ? "right" :
                        keyCode == 40 ? "down" :
                        // 155 => insert
                        // 157 => Mac apple key
                        (keyCode >= 112 && keyCode <= 123) ? "f" + (keyCode-111) :
                        "ignore"; // ignore other characters
            
            // record the KEY_RELEASED event
        } else if (id == KeyEvent.KEY_RELEASED) {
            keyString = "released";
            System.err.println ("Got key-released event");
        }
        System.err.println ("keyString is \"" + keyString + "\"");
        
        //////////////////////////////////////////////////////////////////////////
        // here is the actual callback                                          //
        if (!(keyString.equals("ignore") ||    // ignore key pressed except arrows //
              keyString.equals("released")))  // ignore key released              //
            keyEventCallback(keyString);                                          //
        //////////////////////////////////////////////////////////////////////////
    } 
    // ---------------------------------------------------------------------
    // end of the key event major processor
}

