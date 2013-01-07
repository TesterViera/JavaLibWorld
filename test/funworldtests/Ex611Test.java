package funworldtests;


import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

public class Ex611Test  extends junit.framework.TestCase 
{
    // Instance variables go here, typically examples to test.
    // For example, if you're testing the Date class, you might say
    // private Date myBirthday = new Date(1964, 1, 27);
    // private Date millennium = new Date(2001, 1, 1);
    private WorldImage greenDot = AImage.makeCircle (30, Color.green, Mode.FILLED);
    private Ex611 showGreenDot = new Ex611(this.greenDot);
    private WorldImage cal = SampleImages.calendar;
    private Ex611 showCalendar = new Ex611(this.cal);

    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        System.out.println ("Testing everything...");
        boolean worked = Tester.run (new Ex611Test());
        System.out.println ("worked: " + worked);
        junit.framework.TestCase.assertTrue(worked);
        System.out.println ("Tested everything!");
    }

    /**
    * Make sure the makeImage function works.
    * 
    * @param  t   the Tester to use
    */
    public void testMakeImage (Tester t)
    {
        t.checkExpect (this.showGreenDot.makeImage(), AImage.makeCircle(30,Color.green,Mode.FILLED));
        t.checkExpect (this.showCalendar.makeImage(), SampleImages.calendar);
    }
    
    public void testAnimation (Tester t)
    {
        this.showGreenDot.bigBang (100, 100);
        this.showCalendar.bigBang (100, 100);
    }
}
