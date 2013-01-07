package funworldtests;


import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

public class Ex613Test extends junit.framework.TestCase
{
    // Instance variables go here, typically examples to test.
    // For example, if you're testing the Date class, you might say
    // private Date myBirthday = new Date(1964, 1, 27);
    // private Date millennium = new Date(2001, 1, 1);
    private Ex613 showBlueDot = new Ex613(AImage.makeCircle(30,Color.blue,Mode.FILLED));
    private Ex613 showCalendar = new Ex613(SampleImages.calendar);
    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        junit.framework.TestCase.assertTrue(Tester.run (new Ex613Test()));
    }

    public void testMakeImage (Tester t)
    {
        t.checkExpect (this.showBlueDot.makeImage(),
            showBlueDot.background.place(AImage.makeCircle(30,Color.blue,Mode.FILLED),
                                         50, 50));
        t.checkExpect (this.showCalendar.makeImage(),
            showCalendar.background.place(SampleImages.calendar, 50, 50));
    }
    
    public void testAnimation (Tester t)
    {
        this.showBlueDot.bigBang (100, 100);
        this.showCalendar.bigBang (100, 100);
    }
}
