package funworldtests;



import tester.*;
import javalib.worldimages.*;
import java.awt.Color;

/**
 * Testing class GrowingDiskTest.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrowingDiskTest extends junit.framework.TestCase
{
    // Instance variables go here, typically examples to test.
    // For example, if you're testing the Date class, you might say
    // private Date myBirthday = new Date(1964, 1, 27);
    // private Date millennium = new Date(2001, 1, 1);
    private GrowingDisk it = new GrowingDisk (1);

    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        Tester.run (new GrowingDiskTest());
    }

    /**
    * A sample testing method
    * 
    * @param  t   the Tester to use
    */
    public void testMakeImage (Tester t)
    {
        // put your code here, e.g.
        // t.checkExpect (this.myBirthday.getYear(), 1964);
        // t.checkExpect (new Date(1941,12,7).addDays(10),
        //           new Date(1941,12,17));
        t.checkExpect (it.makeImage (17), AImage.makeCircle (17, Color.blue, Mode.FILLED));
    }
    
    public void testGotTick (Tester t)
    {
        t.checkExpect (it.gotTick (17), 18);
    }
    
    public void testAnimation (Tester t)
    {
        new GrowingDisk (1).bigBang (200, 200, 0.1);
    }
}
