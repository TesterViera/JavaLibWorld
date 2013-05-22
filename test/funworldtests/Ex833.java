package funworldtests;



import javalib.worldimages.*;
import javalib.funworld.*;

/**
 * Write a description of class Ex641 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ex833 extends Animation<Integer> // Model is the x coordinate of the picture
{
    /**
     * Constructor for objects of class Ex641
     *
     * @param initial    the initial model
     */
    public Ex833(Integer initial)
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
        return SampleImages.calendar.centerMoved(current, 50);
    }

    /**
     * Handle a tick event.
     * 
     * @return the new model
     */
    public Integer gotTick(Integer current)
    {
        return current + 1;
    }
}
