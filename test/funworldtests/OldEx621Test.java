package funworldtests;


import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;


/**
 * Testing class OldEx621Test.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OldEx621Test extends junit.framework.TestCase
{
    private OldEx621 showBlueDot = new OldEx621(AImage.makeCircle(30,Color.blue,Mode.FILLED));
    private OldEx621 showCalendar = new OldEx621(SampleImages.calendar);
    
    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        boolean worked = Tester.run (new OldEx621Test());
        junit.framework.TestCase.assertTrue(worked);
    }

    public void testMakeImage (Tester t)
    {
        t.checkExpect (this.showBlueDot.makeImage(),
                       AImage.makeCircle(30,Color.blue,Mode.FILLED).moved (50, 50));
        t.checkExpect (this.showCalendar.makeImage(),
                       SampleImages.calendar.moved (50, 50));
    }
    
    public void testGotTick (Tester t)
    {
        t.checkExpect (this.showCalendar.onTick(),
                       new OldEx621(SampleImages.calendar.rotatedInPlace(90)));
    }
    
    public void testAnimation (Tester t)
    {
        // this.showBlueDot.bigBang (100, 100, 1); // tick every tenth of a second
        this.showCalendar.bigBang (100, 100, 1);
    }
}
