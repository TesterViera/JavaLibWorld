package javalib.sbimages;


import tester.*;

import javalib.worldcanvas.WorldCanvas;
import javalib.colors.*;

/**
 * Testing class SBImageAdapterTest.
 * 
 * @author Stephen Bloch
 * @version Dec. 5, 2012
 */
public class SBImageAdapterTest extends junit.framework.TestCase
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
        Tester.run (new SBImageAdapterTest());
    }

    public static void showSingleImage (Image ii)
    {
        int width = Math.min(1000, ii.getRight() + 50);
        int height = Math.min(800, ii.getBottom() + 50);
        WorldCanvas c = new WorldCanvas (width, height);
        
        SBImageAdapter pic = new SBImageAdapter (ii);
        
        boolean drawn = c.drawImage (pic) && c.show();
    }
    
    public void testDrawing (Tester t)
    {
        // This method won't actually make any Tester assertions; it just draws things
        // on the screen, to be assessed as correct or not by eye.
       
        // Primitive shapes: Rectangle, Frame, Ellipse, Oval,
        // Circle, Disk, Triangle, Text,
        // FilledPoly, FilledTriangle, PolyLine, OutlinedTriangle, Line
        
        /*
        showSingleImage (AImage.makeRectangle (30,50, new Black(), Mode.FILLED));
        // should be black, 30 wide by 50 high, at top left corner.
        showSingleImage (AImage.makeRectangle (40, 40, java.awt.Color.blue, Mode.OUTLINED));
        // should be blue, 40x40 square, at top left corner.
        showSingleImage (AImage.makeRectangle (50, 20, new Green(), Mode.FILLED).getTranslated(100,50));
        // should be green, 50x20, with top-left corner at (100,50).
        showSingleImage (AImage.makeRectangle (80, 60, new Red(), Mode.OUTLINED)
                            .getTranslated(50,100));
        // should be a red outlined box, 80x60, with top-left corner at (50, 100).
        showSingleImage (AImage.makeEllipse (50, 30, new Yellow(), Mode.FILLED)
                            .getTranslated(50, 30));
        // should be... you get the idea
        showSingleImage (AImage.makeEllipse (20, 80, new Blue(), Mode.OUTLINED)
                            .getTranslated(80, 20));
        showSingleImage (AImage.makeCircle (30, new Red(), Mode.FILLED)
                            .getTranslated(new Posn(50, 30)));
        // should have its top-left corner at (50,30)
        showSingleImage (AImage.makeCenteredCircle (new Posn(50, 30), 30, new Blue(), Mode.OUTLINED));
        // should have its center at (50,30)
        showSingleImage (AImage.makeCenteredCircle (new Posn(50,30), 30, new Green(), Mode.FILLED));
        * /
       
        Image tri = AImage.makeTriangle (new Posn(100,30),
                                         new Posn(80,100),
                                         new Posn(120,110),
                                         java.awt.Color.green,
                                         Mode.FILLED);
        showSingleImage (tri);
        
        showSingleImage (AImage.makeTriangle (new Posn(30, 100),
                                              new Posn(100, 80),
                                              new Posn(110, 120),
                                              java.awt.Color.blue, 
                                              Mode.OUTLINED));

        
        Image pent1 = new PolygonImage (java.awt.Color.pink,
                                     Mode.FILLED,
                                     new Posn(100, 20),
                                     new Posn(160, 50),
                                     new Posn(150, 90),
                                     new Posn(50, 90),
                                     new Posn(40, 50));
        showSingleImage (pent1);
        
        Image pent2 = new PolygonImage (java.awt.Color.blue,
                                     Mode.OUTLINED,
                                     new Posn(100, 20),
                                     new Posn(160, 50),
                                     new Posn(150, 90),
                                     new Posn(50, 90),
                                     new Posn(40, 50));
        showSingleImage (pent2);

        Image hello = new TextImage ("hello there", java.awt.Color.blue);
        showSingleImage (hello);

        showSingleImage (AImage.makeLine (new Posn(100, 15), new Posn(50, 100), new Green()));
        
        showSingleImage (AImage.makeFromFile ("Images/green-fish.png"));
        showSingleImage (AImage.makeFromFile ("Images/pink-fish.png").getTranslated(200, 250));
        showSingleImage (AImage.makeFromFile ("Images/shark.png").getTranslated(350, 400));
        
        
        
        // Operators on images: translate, rotate, scale, reflect, overlay, overlayXY,
        // above, beside, aboveCentered, besideCentered, crop
        
        showSingleImage (
                AImage.makeRectangle(100, 100, new Blue(), Mode.FILLED)
                    .overlay(AImage.makeCenteredCircle (new Posn(50,50), 30, new Red(), Mode.FILLED)));
        // should be a red disk on a blue square background
        
        showSingleImage (
            AImage.makeRectangle (100, 50, new Blue(), Mode.FILLED).getTranslated(50,0)
                .overlay(AImage.makeEllipse (100, 50, new Red(), Mode.FILLED)));
        // should be a blue rectangle whose left half is overlapped by a red oval.
        
        showSingleImage (
            AImage.makeRectangle (100, 50, new Blue(),Mode.FILLED)
                .getTranslated(50,0)
                .aboveCentered(
                    AImage.makeEllipse(50, 100, new Red(),Mode.FILLED)
                    .getTranslated(400, 100)));
        // should be a wide blue rectangle stacked on top of a tall red oval
        // completely ignores both locations
        
        showSingleImage (
            AImage.makeRectangle (100, 300, new Blue(),Mode.FILLED)
                .besideCentered(
                    AImage.makeCircle (50, new Red(), Mode.FILLED)
                    .aboveCentered(
                        AImage.makeCircle(100, new Green(), Mode.FILLED))));
        // should be a vertical blue box on the left, a small red disk above a larger green disk on the right
        
       
        Image label = new TextImage ("A snowman:", 18, TextStyle.ITALIC, java.awt.Color.black);
        Image snowman = AImage.makeCircle (10, new Black(), Mode.OUTLINED)
                        .aboveCentered(AImage.makeCircle (20, new Black(), Mode.OUTLINED),
                                       AImage.makeCircle (30, new Black(), Mode.OUTLINED));
        Image labelledSnowman = label.aboveCentered(snowman);
        showSingleImage (labelledSnowman);
        // System.out.println ("Labelled snowman: " + labelledSnowman);
        
        showSingleImage (snowman.getCropped (20, 40, 20, 200));  // works
        
        showSingleImage (labelledSnowman.getCropped (10, 40, 10, 200));  // strange
        showSingleImage (labelledSnowman.getScaled (1.3));  // works
        
        showSingleImage (labelledSnowman.getScaled (0.5, 0.8));  // works

        Image labelledTri = AImage.makeText ("A green triangle:", 18, TextStyle.ITALIC, java.awt.Color.blue)
            .aboveCentered(tri);
       
       showSingleImage (labelledTri);
       // System.out.println ("Labelled triangle: " + labelledTri);
       
       Image croppedTri = labelledTri.getCropped (10, 90, 10, 90);
       showSingleImage (croppedTri);
       // System.out.println ("Cropped triangle: " + croppedTri);
       
       Image xTri = labelledTri.getXReflection();
       showSingleImage (xTri);
       // System.out.println ("X-reflected triangle: " + xTri);
       
       Image yTri = labelledTri.getYReflection();
       showSingleImage (yTri);
       // System.out.println ("Y-reflected triangle: " + yTri);
        
        Image backwardSnowman = labelledSnowman.getXReflection();
        showSingleImage (backwardSnowman);
        System.out.println ("Backwards snowman: " + backwardSnowman);
        
        Image invertedSnowman = labelledSnowman.getYReflection();
        showSingleImage (invertedSnowman);
        System.out.println ("Inverted snowman: " + invertedSnowman);
        
        // showSingleImage (labelledSnowman.getRotated (35)); // part of it disappears off the screen
        
        showSingleImage (labelledSnowman.getRotated (35).getNormalized()); // should be visible
        
        showSingleImage (labelledSnowman.getCenterRotated (35));
        
        // showSingleImage (snowman.getRotated (-90)); // disappears off the screen
        
        showSingleImage (snowman.getRotated (-90).getNormalized()); // should be visible
        
        showSingleImage (snowman.getCenterRotated (-90)); // invisible
        */
        
        Image bloch = AImage.makeFromURL ("http://picturingprograms.com/pictures/bloch.jpg");
        showSingleImage (bloch);
        showSingleImage (bloch.getCenterRotated (45));
        showSingleImage (bloch.getXReflection());
        showSingleImage (bloch.getYReflection());
        
        Image twoBlochs = bloch.beside(bloch);
        showSingleImage (twoBlochs);

        Image threeBlochs = bloch.aboveCentered(twoBlochs).freeze();
        
        showSingleImage (threeBlochs);
        showSingleImage (threeBlochs);
        
        System.out.println ("Saving threeBlochs: " + threeBlochs.save("threeblochs.png"));
        
        showSingleImage (AImage.makeFromFile ("threeblochs.png").getYReflection());
        
        showSingleImage (AImage.makeRectangle(89, 55, new Blue(), Mode.FILLED)
                            .place(AImage.makeFromFile("Images/shark.png"), 0, 0));
    }
}
