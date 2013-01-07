package funworldtests;


import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

/**
 * Testing class Ex623Test.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ex623Test extends junit.framework.TestCase
{
    private Ex623 showBlueDot = new Ex623(AImage.makeCircle(30,Color.blue,Mode.FILLED));
    private Ex623 showCalendar = new Ex623(SampleImages.calendar);
    private Ex623 showFigure = new Ex623 (SampleImages.stickFigure);
    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        junit.framework.TestCase.assertTrue(Tester.run (new Ex623Test()));
    }

    public void testMakeImage (Tester t)
    {
        t.checkExpect (this.showBlueDot.makeImage(),
            showBlueDot.background.place(AImage.makeCircle(30,Color.blue,Mode.FILLED),
                                         50, 50));
        t.checkExpect (this.showCalendar.makeImage(),
            showCalendar.background.place(SampleImages.calendar, 50, 50));
    }
    
    public void testGotTick (Tester t)
    {
        t.checkExpect (this.showCalendar.gotTick(SampleImages.calendar),
                       SampleImages.calendar.yReflected());
        t.checkExpect (this.showFigure.gotTick(SampleImages.stickFigure),
                       SampleImages.stickFigure.yReflected());
        t.checkExpect (this.showFigure.gotTick(this.showFigure.gotTick(SampleImages.stickFigure)),
                       SampleImages.stickFigure);
    }
    
    public void testAnimation (Tester t)
    {
        this.showBlueDot.bigBang (100, 100, 1.5); // tick every tenth of a second
        this.showCalendar.bigBang (100, 100, 1.5);
        this.showFigure.bigBang(100,100,1.5);
    }
}
