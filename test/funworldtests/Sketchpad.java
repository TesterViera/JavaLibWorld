package funworldtests;



import javalib.worldimages.*;
import javalib.funworld.*;

/**
 * Write a description of class Sketchpad here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sketchpad extends Animation<WorldImage>
// replace WorldImage throughout with the name of the class you're using as a model
{
    WorldImage blueDot = AImage.makeCircle (3, java.awt.Color.blue, Mode.FILLED);
    WorldImage greenDot = AImage.makeCircle (3, java.awt.Color.green, Mode.FILLED);
    WorldImage redDot = AImage.makeCircle (3, java.awt.Color.red, Mode.FILLED);
    /**
     * Constructor for objects of class Sketchpad
     *
     * @param initial    the initial model
     */
    public Sketchpad(WorldImage initial)
    {
        super(initial);
    }

    /**
     * Get the graphical form of the current model.
     *
     * @return a WorldImage
     */
    public WorldImage makeImage (WorldImage current)
    {
        return current ;
    }

    /*
     * Other event-handlers, e.g.
     * public WorldImage gotTick (WorldImage old) { ... }
     * public WorldImage gotKeyEvent (WorldImage old, String s) { ... }
     * public WorldImage gotMouseClicked (WorldImage old, Posn mouse) { ... }
     * public WorldImage gotMouseEntered (WorldImage old, Posn mouse) { ... }
     * public WorldImage gotMouseExited (WorldImage old, Posn mouse) { ... }
     * public WorldImage gotMousePressed (WorldImage old, Posn mouse) { ... }
     * public WorldImage gotMouseRelease (WorldImage old, Posn mouse) { ... }
     */
    public WorldImage gotMouseDragged (WorldImage old, Posn mouse)
    {
        return old.place (this.blueDot, mouse.getX(), mouse.getY());
    }
    
    public WorldImage gotMouseClicked (WorldImage old, Posn mouse)
    {
        return old.place (this.greenDot, mouse.getX(), mouse.getY());
    }
    
    public WorldImage gotMouseMoved (WorldImage old, Posn mouse)
    {
        return old.place (this.redDot, mouse.getX(), mouse.getY());
    }
    
    public static void run ()
    {
        new Sketchpad (AImage.makeRectangle (300, 200, java.awt.Color.white, Mode.FILLED))
            .bigBang ();
    }
}
