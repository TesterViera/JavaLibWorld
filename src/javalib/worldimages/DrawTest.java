package javalib.worldimages;


import tester.*;

import javalib.worldcanvas.WorldCanvas;
import javalib.colors.*;
import java.awt.Color;

/**
 * A bunch of test cases that draw examples on the screen.  Always passes;
 * need a human eye to look at them and confirm that they look right.
 * 
 * @author Stephen Bloch
 * @version Dec. 28, 2012
 */
public class DrawTest extends junit.framework.TestCase
{
    // Instance variables go here, typically examples to test.
    // For example, if you're testing the Date class, you might say
    // private Date myBirthday = new Date(1964, 1, 27);
    // private Date millennium = new Date(2001, 1, 1);

    /**
     * Run all test methods in the class.
     */
    public static void testEverything ()
    {
        junit.framework.TestCase.assertTrue (Tester.run (new DrawTest()));
    }

    /*
    public static void showSingleImage (WorldImage ii)
    {
        if (ii == null) return;
        int width = Math.min(1000, ii.getRight() + 50);
        int height = Math.min(800, ii.getBottom() + 50);
        WorldCanvas c = new WorldCanvas (width, height);
        
        boolean drawn = c.drawImage (ii) && c.show();
    }
    */
    
    public void testOtherStuff (Tester t)
    {
          // WorldImage text1 = AImage.makeText("hello", 6, TextStyle.BOLD, new Black()).moved(2,3);
          // WorldImage fromFile1 = AImage.makeFromFile ("Images/shark.png").moved(20,30);
          // text1 = text1;
    }
    
    public void testPrimitives (Tester t)
    {
        // This method won't actually make any Tester assertions; it just draws things
        // on the screen, to be assessed as correct or not by eye.
       
        // Primitive shapes: Rectangle, Frame, Ellipse, Oval,
        // Circle, Disk, Triangle, Text,
        // FilledPoly, FilledTriangle, PolyLine, OutlinedTriangle, Line
        
        AImage.makeRectangle (30,50, new Black(), Mode.FILLED).show();
        // should be black, 30 wide by 50 high, at top left corner.
        AImage.makeRectangle (40, 40, java.awt.Color.blue, Mode.OUTLINED).show();
        // should be blue, 40x40 square, at top left corner.
        AImage.makeRectangle (50, 20, new Green(), Mode.FILLED).moved(100,50).show();
        // should be green, 50x20, with top-left corner at (100,50).
        AImage.makeRectangle (80, 60, new Red(), Mode.OUTLINED)
                            .moved(50,100)
                            .show();
        // should be a red outlined box, 80x60, with top-left corner at (50, 100).
        AImage.makeEllipse (50, 30, new Yellow(), Mode.FILLED)
                            .moved(50, 30)
                            .show();
        // should be... you get the idea
        AImage.makeEllipse (20, 80, new Blue(), Mode.OUTLINED)
                            .moved(80, 20)
                            .show();
        AImage.makeCircle (30, new Red(), Mode.FILLED)
                            .moved(new Posn(50, 30))
                            .show();
        // should have its top-left corner at (50,30)
        AImage.makeCenteredCircle (new Posn(50, 30), 30, new Blue(), Mode.OUTLINED).show();
        // should have its center at (50,30)
        AImage.makeCenteredCircle (new Posn(50,30), 30, new Green(), Mode.FILLED).show();
        
       
        WorldImage tri = AImage.makeTriangle (new Posn(100,30),
                                         new Posn(80,100),
                                         new Posn(120,110),
                                         java.awt.Color.green,
                                         Mode.FILLED);
        tri.show();
        
        AImage.makeTriangle (new Posn(30, 100),
                             new Posn(100, 80),
                             new Posn(110, 120),
                             java.awt.Color.blue, 
                             Mode.OUTLINED).show();

        
        WorldImage pent1 = new PolygonImage (java.awt.Color.pink,
                                     Mode.FILLED,
                                     new Posn(100, 20),
                                     new Posn(160, 50),
                                     new Posn(150, 90),
                                     new Posn(50, 90),
                                     new Posn(40, 50));
        pent1.show();
        
        WorldImage pent2 = new PolygonImage (java.awt.Color.blue,
                                     Mode.OUTLINED,
                                     new Posn(100, 20),
                                     new Posn(160, 50),
                                     new Posn(150, 90),
                                     new Posn(50, 90),
                                     new Posn(40, 50));
        pent2.show();

        WorldImage hello = TextImage.make ("hello there", java.awt.Color.blue);
        hello.show();

        AImage.makeLine (new Posn(100, 15), new Posn(50, 100), new Green()).show();
        
        SampleImages.greenFish.show();
        SampleImages.pinkFish.moved(200, 250).show();
        SampleImages.shark.moved(350, 400).show();
    }
    
    public void testOperators (Tester t)
    {
        // This method won't actually make any Tester assertions; it just draws things
        // on the screen, to be assessed as correct or not by eye.
        
        // Operators on images: translate, rotate, scale, reflect, overlay, overlayXY,
        // above, beside, aboveCentered, besideCentered, crop
        
        WorldImage redDiskOnBlue =
            AImage.makeRectangle(100, 100, new Blue(), Mode.FILLED)
                    .overlay(AImage.makeCenteredCircle (new Posn(50,50), 30, new Red(), Mode.FILLED));
        redDiskOnBlue.show();
        // should be a red disk on a blue square background
        
        AImage.makeRectangle (100, 50, new Blue(), Mode.FILLED).moved(50,0)
                .overlay(AImage.makeEllipse (100, 50, new Red(), Mode.FILLED))
                .show();
        // should be a blue rectangle whose left half is overlapped by a red oval.
        
        AImage.makeRectangle (100, 50, new Blue(),Mode.FILLED)
            .moved(50,0)
            .aboveCentered(
                AImage.makeEllipse(50, 100, new Red(),Mode.FILLED)
                .moved(400, 100))
            .show();
        // should be a wide blue rectangle stacked on top of a tall red oval
        // completely ignores both locations
        
        AImage.makeRectangle (100, 300, new Blue(),Mode.FILLED)
              .besideCentered(
                    AImage.makeCircle (50, new Red(), Mode.FILLED)
                    .aboveCentered(
                        AImage.makeCircle(100, new Green(), Mode.FILLED)))
              .show();
        // should be a vertical blue box on the left, a small red disk above a larger green disk on the right
        
       
        WorldImage label = TextImage.make ("A snowman:", 18, TextStyle.ITALIC, java.awt.Color.black);
        WorldImage snowman = AImage.makeCircle (10, new Black(), Mode.OUTLINED)
                        .aboveCentered(AImage.makeCircle (20, new Black(), Mode.OUTLINED),
                                       AImage.makeCircle (30, new Black(), Mode.OUTLINED));
        WorldImage labelledSnowman = label.aboveCentered(snowman);
        labelledSnowman.show();
        // System.out.println ("Labelled snowman: " + labelledSnowman);
        
        snowman.cropped (20, 40, 20, 200).show();
        
        labelledSnowman.cropped (10, 40, 10, 200).show();
        labelledSnowman.scaled (1.3).show();
        
        labelledSnowman.scaled (0.5, 0.8).show();

        WorldImage tri = AImage.makeTriangle (new Posn(100,30),
                                         new Posn(80,100),
                                         new Posn(120,110),
                                         java.awt.Color.green,
                                         Mode.FILLED);
        
        WorldImage labelledTri = AImage.makeText ("A green triangle:", 18, TextStyle.ITALIC, java.awt.Color.blue)
            .aboveCentered(tri);
       
       labelledTri.show();
       
       WorldImage croppedTri = labelledTri.cropped (10, 90, 10, 90);
       croppedTri.show();
       
       WorldImage xTri = labelledTri.xReflected();
       xTri.show();
       
       WorldImage yTri = labelledTri.yReflected();
       yTri.show();
        
        WorldImage backwardSnowman = labelledSnowman.xReflected();
        backwardSnowman.show();
        
        WorldImage invertedSnowman = labelledSnowman.yReflected();
        invertedSnowman.show();
        
        labelledSnowman.rotated (35).show(); // part of it disappears off the screen
        
        labelledSnowman.rotated (35).normalized().show();
        
        labelledSnowman.rotatedInPlace (35).show();
        
        snowman.rotated (-90).show(); // disappears off the screen
        
        snowman.rotated (-90).normalized().show();
        
        snowman.rotatedInPlace (-90).show();
        
        
        WorldImage bloch = AImage.makeFromURL ("http://picturingprograms.com/pictures/bloch.jpg");
        bloch.show();
        bloch.rotatedInPlace (45).show();
        bloch.xReflected().show();
        bloch.yReflected().show();
        
        WorldImage twoBlochs = bloch.beside(bloch);
        twoBlochs.show();

        WorldImage threeBlochs = bloch.aboveCentered(twoBlochs).frozen();
        
        threeBlochs.show();
        
        System.out.println ("Saving threeBlochs: " + threeBlochs.save("threeblochs.png"));
        
        AImage.makeFromFile ("threeblochs.png").yReflected().show();
        
        AImage.makeRectangle(89, 55, new Blue(), Mode.FILLED)
              .place(SampleImages.shark, 0, 0)
              .show();
                            
        WorldImage gradient1 = AImage.build (100, 100,
            new ImageBuilder () {
                public Color pixelColor (int x, int y, Object other)
                {
                    Posn dimensions = (Posn)other;
                    return new Color (255 * x / dimensions.x, 255 * y / dimensions.y, 0, 255);
                }
            }
            , new Posn(100,100));
        gradient1.show();
        
        ImageMap killRed = new ImageMap () {
            public Color pixelColor (int x, int y, Color oldColor, Object other)
            {
                // ignore x, y, other
                return new Color (0, oldColor.getGreen(), oldColor.getBlue(), oldColor.getAlpha());
            }
        };
        bloch.map(killRed).show();
        redDiskOnBlue.map(killRed).show(); // should be a black disk on blue rect
        
        ImageMap replaceWithWhite = new ImageMap () {
            public Color pixelColor (int x, int y, Color oldColor, Object other)
            {
                Color target = (Color)other;
                return oldColor.equals(target) ? Color.white : oldColor;
            }
        };
        redDiskOnBlue.map(replaceWithWhite, Color.blue).show(); // should be just a red disk
        redDiskOnBlue.map(replaceWithWhite, Color.red).show(); // should be a white disk on blue rect
        redDiskOnBlue.map(replaceWithWhite, Color.yellow).show(); // should be unchanged
    }
    
    
}
