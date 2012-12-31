package javalib.worldimages;


import tester.*;
import java.awt.Color;
public class TestAImage extends junit.framework.TestCase
{
    // Instance variables go here, typically examples to test.
    // For example, if you're testing the Date class, you might say
    // private Date myBirthday = new Date(1964, 1, 27);
    // private Date millennium = new Date(2001, 1, 1);
    private WorldImage redRect = AImage.makeRectangle (50, 30, Color.red, Mode.FILLED);
    private WorldImage cal = SampleImages.calendar;

    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        Tester.run (new TestAImage());
    }

    /**
    * A sample testing method
    * 
    * @param  t   the Tester to use
    */
    public void testSame (Tester t)
    {
        t.checkExpect (redRect, redRect, "comparing red rect with itself");
        WorldImage doubleRedRect = redRect.above(redRect);
        t.checkExpect (doubleRedRect, AImage.makeRectangle (50, 60, Color.red, Mode.FILLED),
                       "comparing redRect.above(redRect) with a double-height rect");
        WorldImage blah = AImage.makeCenteredCircle(new Posn(10,10),5,Color.blue).overlayCentered(redRect);
        blah.show();
        t.checkExpect (blah, redRect, "comparing redRect overlaid on small disk with redRect itself");
        WorldImage blerg = redRect.overlayCentered(AImage.makeCenteredCircle(new Posn(10,10),5,Color.blue));
        t.checkFail (blerg, redRect, "comparing redRect under a small disk with redRect itself");
        WorldImage blueCirc = AImage.makeCircle (30, Color.blue, Mode.OUTLINED);
        t.checkExpect (blueCirc, blueCirc.rotatedInPlace(90), "comparing circle with circle rotated 90 degrees");
        // t.checkExpect (blueCirc, blueCirc.rotatedInPlace(-72), "comparing circle with circle rotated 72 degrees");
        // That one doesn't quite work.
    }
}
