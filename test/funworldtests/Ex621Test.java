package funworldtests;


import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

/**
 * Testing class Ex621Test.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ex621Test extends junit.framework.TestCase
{
    private Ex621 showBlueDot = new Ex621(AImage.makeCircle(30,Color.blue,Mode.FILLED));
    private Ex621 showCalendar = new Ex621(SampleImages.calendar);
    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        junit.framework.TestCase.assertTrue(Tester.run (new Ex621Test()));
    }

    public void testMakeImage (Tester t)
    {
        t.checkExpect (this.showBlueDot.makeImage(),
                       AImage.makeCircle(30,Color.blue,Mode.FILLED).moved (50, 50));
        t.checkExpect (this.showCalendar.makeImage(),
                       SampleImages.calendar.moved (50, 50));
        t.checkExpect (this.showCalendar.makeImage(SampleImages.bloch),
                       SampleImages.bloch.moved (50, 50));
    }
    
    public void testGotTick (Tester t)
    {
        t.checkExpect (this.showCalendar.gotTick(SampleImages.calendar),
                       SampleImages.calendar.rotatedInPlace(90));
    }
    
    public void testAnimation (Tester t)
    {
        // this.showBlueDot.bigBang (100, 100, 1); // tick every tenth of a second
        this.showCalendar.bigBang (100, 100, 1);
    }
}
