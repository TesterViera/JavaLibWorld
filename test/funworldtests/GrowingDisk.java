package funworldtests;



import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

/**
 * Animation of a growing disk.  The model is the radius of the disk, as an Integer.
 * 
 * @author Stephen Bloch
 * @version Feb. 22, 2013
 */
public class GrowingDisk extends Animation<Integer>
{
    /**
     * Constructor for objects of class GrowingDisk
     *
     * @param initial    the initial model
     */
    public GrowingDisk(Integer initial)
    {
        super(initial);
    }

    /**
     * Get the graphical form of the current model.
     *
     * @return a WorldImage
     */
    public WorldImage makeImage (Integer current)
    {
        return AImage.makeCircle (current, Color.blue, Mode.FILLED) ;
    }

    /*
     * Other event-handlers, e.g.
     * public Integer gotTick (Integer old) { ... }
     * public Integer gotKeyEvent (Integer old, String s) { ... }
     * public Integer gotMouseClicked (Integer old, Posn mouse) { ... }
     * public Integer gotMouseEntered (Integer old, Posn mouse) { ... }
     * public Integer gotMouseExited (Integer old, Posn mouse) { ... }
     * public Integer gotMousePressed (Integer old, Posn mouse) { ... }
     * public Integer gotMouseReleased (Integer old, Posn mouse) { ... }
     * public Integer gotMouseMoved (Integer old, Posn mouse) { ... }
     * public Integer gotMouseDragged (Integer old, Posn mouse) { ... }
     */
    
    public Integer gotTick (Integer old)
    {
        // old    an Integer
        return old + 1;
    }
    
    public WorldEnd worldEnds (Integer old)
    {
        // old    an Integer
        return new WorldEnd (old >= 100, AImage.makeText ("Bye now!"));
    }
}
