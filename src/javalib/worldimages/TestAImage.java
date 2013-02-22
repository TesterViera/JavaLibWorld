package javalib.worldimages;


import tester.*;
import java.awt.Color;

/**
 * A bunch of test cases for various methods in AImage.
 * 
 * @author Stephen Bloch
 * @version Dec. 28, 2012
 */
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
        TestAImage it = new TestAImage();
        boolean worked = Tester.run (it);
        junit.framework.TestCase.assertTrue (worked);
    }

    /**
    * Test the pixel-for-pixel equivalence of images.
    * 
    * @param  t   the Tester to use
    */
    public void testSame (Tester t)
    {
        t.checkExpect (redRect,
                       redRect,
                       "comparing red rect with itself");
        
        WorldImage doubleRedRect = redRect.above(redRect);
        t.checkExpect (doubleRedRect,
                       AImage.makeRectangle (50, 60, Color.red, Mode.FILLED),
                       "comparing redRect.above(redRect) with a double-height rect");
        // Another way to do the same thing...
        t.checkExpect (WorldImage.LOOKS_SAME.equivalent (doubleRedRect,
                                                         AImage.makeRectangle (50, 60, Color.red, Mode.FILLED)),
                       true,
                       "comparing redRect.above(redRect) with a double-height rect, using equivalent()");
        
        WorldImage blah = AImage.makeCenteredCircle(new Posn(10,10),5,Color.blue).overlayCentered(redRect);
        t.checkExpect (blah,
                       redRect,
                       "comparing redRect overlaid on small disk with redRect itself");
                           
        WorldImage blerg = redRect.overlayCentered(AImage.makeCenteredCircle(new Posn(10,10),5,Color.blue));
        t.checkFail (blerg, redRect, "comparing redRect under a small disk with redRect itself");
        WorldImage blueCirc = AImage.makeCircle (30, Color.blue, Mode.OUTLINED);
        t.checkExpect (blueCirc,
                       blueCirc.rotatedInPlace(90),
                       "comparing circle with circle rotated 90 degrees");
        // t.checkExpect (blueCirc, blueCirc.rotatedInPlace(-72), "comparing circle with circle rotated 72 degrees");
        // That one doesn't quite work.
    }
    
    public void testGetPixelColor (Tester t)
    {
        WorldImage rect = AImage.makeRectangle (5, 3, Color.yellow, Mode.FILLED);
        Color transparent = new Color(0,0,0,0);
        t.checkExpect (rect.getPixelColor(-1,0), transparent);
        t.checkExpect (rect.getPixelColor(0,-2), transparent);
        t.checkExpect (rect.getPixelColor(5,2), transparent);
        t.checkExpect (rect.getPixelColor(1,4), transparent);
        for (int x = 0; x < 5; ++x)
            for (int y = 0; y < 3; ++y)
                t.checkExpect (rect.getPixelColor(x,y), Color.yellow);
        
        WorldImage circ = AImage.makeCircle (5, Color.blue, Mode.FILLED);
        t.checkExpect (circ.getPixelColor(5,5), Color.blue);
        t.checkExpect (circ.getPixelColor(1,9), transparent);
        t.checkExpect (circ.getPixelColor(2,8), Color.blue);
    }
}
