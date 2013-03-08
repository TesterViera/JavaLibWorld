package funworldtests;



import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.event.KeyEvent;

/**
 * Write a description of class Ex641 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TypingExample extends Animation<String>
{
// Model is the current text on the screen
    /**
     * Constructor for objects of class Ex641
     *
     * @param initial    the initial model
     */
    public TypingExample()
    {
        super("");
    }

    /**
     * Get the graphical form of the current model.
     *
     * @return a WorldImage
     */
    public WorldImage makeImage (String current)
    {
        return AImage.makeText (current);
    }

    public String gotKeyEvent (String current, String key)
    {
        // current    String
        // key        String
        return "\"" + key + "\"";
    }
    
    public static void printStuff ()
    {
        System.out.println ("VK_BACKSPACE = " + KeyEvent.VK_BACK_SPACE);
        System.out.println ("VK_DELETE = " + KeyEvent.VK_DELETE);
    }
    
    public String gotTick (String current)
    {
        System.out.println ("After " + this.getTickCount() + " ticks and " +
                            this.getElapsedTime() + " seconds, window dimensions are " +
                            new Posn(this.getCurrentWidth(), this.getCurrentHeight()));
        return current;
    }
    
    public static void run ()
    {
        printStuff();
        new TypingExample().bigBang(300, 50, 5);
    }
}
