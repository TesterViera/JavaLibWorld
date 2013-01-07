package funworldtests;

import javalib.worldimages.*;

import tester.*;

/**
 * Testing class Ex833Test.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ex833Test extends junit.framework.TestCase
{
    // Instance variables go here, typically examples to test.
    // For example, if you're testing the Date class, you might say
    // private Date myBirthday = new Date(1964, 1, 27);
    // private Date millennium = new Date(2001, 1, 1);
    private Ex833 it = new Ex833(0);

    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        Tester.run (new Ex833Test());
    }

    /**
    * A sample testing method
    * 
    * @param  t   the Tester to use
    */
    public void testGotTick (Tester t)
    {
        t.checkExpect (it.gotTick (5), 6);
        t.checkExpect (it.gotTick (0), 1);
    }
    
    public void testMakeImage (Tester t)
    {
        t.checkExpect (it.makeImage (0), SampleImages.calendar.centerMoved(0,50));
        t.checkExpect (it.makeImage (15), SampleImages.calendar.centerMoved(15,50));
    }
    
    public void testAnimation (Tester t)
    {
        it.bigBang (200, 100, .1);
    }
}
