package worldimagestests;

import javalib.worldimages.*;
import javalib.colors.*;
import tester.*;

import java.util.*;
import java.awt.*;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * Tests for the non-drawing methods in the classes that implement the 
 * <code>WorldImage</code> class.
 * 
 * @author Viera K. Proulx
 * @since May 22 2012
 *
 */
public class ExamplesImageMethods implements IExamples{
  ExamplesImageMethods(){}

  // support for the regression tests
  public static ExamplesImageMethods examplesInstance = 
      new ExamplesImageMethods();

  //------------ CircleImage class ------------------------------------------//

  WorldImage circle1 = new CircleImage(new Posn(2, 3), 4, new Red());
  WorldImage circle2 = new CircleImage(new Posn(2, 3), 4, Color.red);
  WorldImage circle3 = new CircleImage(new Posn(1, 3), 4, new Red());
  WorldImage circle4 = new CircleImage(new Posn(2, 4), 4, new Red());
  WorldImage circle5 = new CircleImage(new Posn(2, 3), 5, new Red());
  WorldImage circle6 = new CircleImage(new Posn(2, 3), 4, new Blue());

  // Tests for the CircleImage class
  void testCircleImage(Tester t){
    t.checkExpect(this.circle1.toString(), 
        "new CircleImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=255,g=0,b=0]" + 
        "\nthis.radius = 4)\n");
    t.checkExpect(this.circle1, this.circle2);
    t.checkExpect(this.circle1, this.circle3, "fails in pinhole.x");
    t.checkExpect(this.circle1, this.circle4, "fails in pinhole.y");
    t.checkExpect(this.circle1, this.circle5, "fails in radius");
    t.checkExpect(this.circle1, this.circle6, "fails in color");
    t.checkExpect(this.circle1.hashCode(), this.circle2.hashCode());
    
    t.checkExpect(this.circle1.getMovedImage(-1, 0), this.circle3);
    t.checkExpect(this.circle1.getMovedImage(0, 1), this.circle4);
    t.checkExpect(this.circle1.getMovedTo(new Posn(1, 3)), this.circle3);
    
    this.circle1.movePinhole(-1,  0);
    t.checkExpect(this.circle1, this.circle3);
    
    this.circle4.moveTo(new Posn(1, 3));
    t.checkExpect(this.circle4, this.circle3);
    
    t.checkExpect(this.circle5.getWidth(), 10);
    t.checkExpect(this.circle5.getHeight(), 10);
  }

  //------------ DiskImage class --------------------------------------------//

  WorldImage disk1 = new DiskImage(new Posn(2, 3), 4, new Red());
  WorldImage disk2 = new DiskImage(new Posn(2, 3), 4, Color.red);
  WorldImage disk3 = new DiskImage(new Posn(1, 3), 4, new Red());
  WorldImage disk4 = new DiskImage(new Posn(2, 4), 4, new Red());
  WorldImage disk5 = new DiskImage(new Posn(2, 3), 5, new Red());
  WorldImage disk6 = new DiskImage(new Posn(2, 3), 4, new Blue());

  // Tests for the DiskImage class
  void testDiskImage(Tester t){
    t.checkExpect(this.disk1.toString(), 
        "new DiskImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=255,g=0,b=0]" + 
        "\nthis.radius = 4)\n");
    t.checkExpect(this.disk1, this.disk2);
    t.checkExpect(this.disk1, this.disk3, "fails in pinhole.x");
    t.checkExpect(this.disk1, this.disk4, "fails in pinhole.y");
    t.checkExpect(this.disk1, this.disk5, "fails in radius");
    t.checkExpect(this.disk1, this.disk6, "fails in color");
    t.checkExpect(this.disk1.hashCode(), this.disk2.hashCode());

    t.checkExpect(this.disk1, this.circle1, "fails - different classes");
    t.checkExpect(this.circle1, this.disk1, "fails - different classes");
    
    t.checkExpect(this.disk1.getMovedImage(-1, 0), this.disk3);
    t.checkExpect(this.disk1.getMovedImage(0, 1), this.disk4);
    t.checkExpect(this.disk1.getMovedTo(new Posn(1, 3)), this.disk3);
    
    this.disk1.movePinhole(-1,  0);
    t.checkExpect(this.disk1, this.disk3);
    
    this.disk4.moveTo(new Posn(1, 3));
    t.checkExpect(this.disk4, this.disk3);
    
    t.checkExpect(this.disk5.getWidth(), 10);
    t.checkExpect(this.disk5.getHeight(), 10);

  }
  
  //------------ EllipseImage class -----------------------------------------//

  WorldImage ellipse1 = new EllipseImage(new Posn(2, 3), 4, 5, new Green());
  WorldImage ellipse2 = new EllipseImage(new Posn(2, 3), 4, 5, Color.green);
  WorldImage ellipse3 = new EllipseImage(new Posn(1, 3), 4, 5, new Green());
  WorldImage ellipse4 = new EllipseImage(new Posn(2, 4), 4, 5, new Green());
  WorldImage ellipse5 = new EllipseImage(new Posn(2, 3), 5, 5, new Green());
  WorldImage ellipse6 = new EllipseImage(new Posn(2, 3), 4, 6, new Green());
  WorldImage ellipse7 = new EllipseImage(new Posn(2, 3), 4, 5, new Blue());

  // Tests for the EllipseImage class
  void testEllipseImage(Tester t){
    t.checkExpect(this.ellipse1.toString(), 
        "new EllipseImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.ellipse1, this.ellipse2);
    t.checkExpect(this.ellipse1, this.ellipse3, "fails in pinhole.x");
    t.checkExpect(this.ellipse1, this.ellipse4, "fails in pinhole.y");
    t.checkExpect(this.ellipse1, this.ellipse5, "fails in width");
    t.checkExpect(this.ellipse1, this.ellipse6, "fails in height");
    t.checkExpect(this.ellipse1, this.ellipse7, "fails in color");
    t.checkExpect(this.ellipse1.hashCode(), this.ellipse2.hashCode());

    t.checkExpect(this.disk1, this.ellipse1, "fails - different classes");
    t.checkExpect(this.ellipse1, this.disk1, "fails - different classes");
    
    t.checkExpect(this.ellipse1.getMovedImage(-1, 0), this.ellipse3);
    t.checkExpect(this.ellipse1.getMovedImage(0, 1), this.ellipse4);
    t.checkExpect(this.ellipse1.getMovedTo(new Posn(1, 3)), this.ellipse3);
    
    this.ellipse1.movePinhole(-1,  0);
    t.checkExpect(this.ellipse1, this.ellipse3);
    
    this.ellipse4.moveTo(new Posn(1, 3));
    t.checkExpect(this.ellipse4, this.ellipse3);
    
    
    t.checkExpect(this.ellipse6.getWidth(), 4);
    t.checkExpect(this.ellipse6.getHeight(), 6);
  }
  
  //------------ FrameImage class -----------------------------------------//

  WorldImage frame1 = new FrameImage(new Posn(2, 3), 4, 5, new Green());
  WorldImage frame2 = new FrameImage(new Posn(2, 3), 4, 5, Color.green);
  WorldImage frame3 = new FrameImage(new Posn(1, 3), 4, 5, new Green());
  WorldImage frame4 = new FrameImage(new Posn(2, 4), 4, 5, new Green());
  WorldImage frame5 = new FrameImage(new Posn(2, 3), 5, 5, new Green());
  WorldImage frame6 = new FrameImage(new Posn(2, 3), 4, 6, new Green());
  WorldImage frame7 = new FrameImage(new Posn(2, 3), 4, 5, new Blue());

  // Tests for the FrameImage class
  void testFrameImage(Tester t){
    t.checkExpect(this.frame1.toString(), 
        "new FrameImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.frame1, this.frame2);
    t.checkExpect(this.frame1, this.frame3, "fails in pinhole.x");
    t.checkExpect(this.frame1, this.frame4, "fails in pinhole.y");
    t.checkExpect(this.frame1, this.frame5, "fails in width");
    t.checkExpect(this.frame1, this.frame6, "fails in height");
    t.checkExpect(this.frame1, this.frame7, "fails in color");
    t.checkExpect(this.frame1.hashCode(), this.frame2.hashCode());

    t.checkExpect(this.disk1, this.frame1, "fails - different classes");
    t.checkExpect(this.frame1, this.disk1, "fails - different classes");
    
    t.checkExpect(this.frame1.getMovedImage(-1, 0), this.frame3);
    t.checkExpect(this.frame1.getMovedImage(0, 1), this.frame4);
    t.checkExpect(this.frame1.getMovedTo(new Posn(1, 3)), this.frame3);
    
    this.frame1.movePinhole(-1,  0);
    t.checkExpect(this.frame1, this.frame3);
    
    this.frame4.moveTo(new Posn(1, 3));
    t.checkExpect(this.frame4, this.frame3);
    
    t.checkExpect(this.frame6.getWidth(), 4);
    t.checkExpect(this.frame6.getHeight(), 6);
  }
  
  //------------ OvalImage class -----------------------------------------//

  WorldImage oval1 = new OvalImage(new Posn(2, 3), 4, 5, new Green());
  WorldImage oval2 = new OvalImage(new Posn(2, 3), 4, 5, Color.green);
  WorldImage oval3 = new OvalImage(new Posn(1, 3), 4, 5, new Green());
  WorldImage oval4 = new OvalImage(new Posn(2, 4), 4, 5, new Green());
  WorldImage oval5 = new OvalImage(new Posn(2, 3), 5, 5, new Green());
  WorldImage oval6 = new OvalImage(new Posn(2, 3), 4, 6, new Green());
  WorldImage oval7 = new OvalImage(new Posn(2, 3), 4, 5, new Blue());

  // Tests for the OvalImage class
  void testOvalImage(Tester t){
    t.checkExpect(this.oval1.toString(), 
        "new OvalImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.oval1, this.oval2);
    t.checkExpect(this.oval1, this.oval3, "fails in pinhole.x");
    t.checkExpect(this.oval1, this.oval4, "fails in pinhole.y");
    t.checkExpect(this.oval1, this.oval5, "fails in width");
    t.checkExpect(this.oval1, this.oval6, "fails in height");
    t.checkExpect(this.oval1, this.oval7, "fails in color");
    t.checkExpect(this.oval1.hashCode(), this.oval2.hashCode());

    t.checkExpect(this.frame1, this.oval1, "fails - different classes");
    t.checkExpect(this.oval1, this.frame1, "fails - different classes");
    
    t.checkExpect(this.oval1.getMovedImage(-1, 0), this.oval3);
    t.checkExpect(this.oval1.getMovedImage(0, 1), this.oval4);
    t.checkExpect(this.oval1.getMovedTo(new Posn(1, 3)), this.oval3);
    
    this.oval1.movePinhole(-1,  0);
    t.checkExpect(this.oval1, this.oval3);
    
    this.oval4.moveTo(new Posn(1, 3));
    t.checkExpect(this.oval4, this.oval3);
    
    t.checkExpect(this.oval6.getWidth(), 4);
    t.checkExpect(this.oval6.getHeight(), 6);
  }
  
  //------------ RectangleImage class ---------------------------------------//

  WorldImage rectangle1 = new RectangleImage(new Posn(2, 3), 4, 5, new Green());
  WorldImage rectangle2 = new RectangleImage(new Posn(2, 3), 4, 5, Color.green);
  WorldImage rectangle3 = new RectangleImage(new Posn(1, 3), 4, 5, new Green());
  WorldImage rectangle4 = new RectangleImage(new Posn(2, 4), 4, 5, new Green());
  WorldImage rectangle5 = new RectangleImage(new Posn(2, 3), 5, 5, new Green());
  WorldImage rectangle6 = new RectangleImage(new Posn(2, 3), 4, 6, new Green());
  WorldImage rectangle7 = new RectangleImage(new Posn(2, 3), 4, 5, new Blue());

  // Tests for the RectangleImage class
  void testRectangleImage(Tester t){
    t.checkExpect(this.rectangle1.toString(), 
        "new RectangleImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.rectangle1, this.rectangle2);
    t.checkExpect(this.rectangle1, this.rectangle3, "fails in pinhole.x");
    t.checkExpect(this.rectangle1, this.rectangle4, "fails in pinhole.y");
    t.checkExpect(this.rectangle1, this.rectangle5, "fails in width");
    t.checkExpect(this.rectangle1, this.rectangle6, "fails in height");
    t.checkExpect(this.rectangle1, this.rectangle7, "fails in color");
    t.checkExpect(this.rectangle1.hashCode(), this.rectangle2.hashCode());

    t.checkExpect(this.frame1, this.rectangle1, "fails - different classes");
    t.checkExpect(this.rectangle1, this.frame1, "fails - different classes");
    
    t.checkExpect(this.rectangle1.getMovedImage(-1, 0), this.rectangle3);
    t.checkExpect(this.rectangle1.getMovedImage(0, 1), this.rectangle4);
    t.checkExpect(this.rectangle1.getMovedTo(new Posn(1, 3)), this.rectangle3);
    
    this.rectangle1.movePinhole(-1,  0);
    t.checkExpect(this.rectangle1, this.rectangle3);
    
    this.rectangle4.moveTo(new Posn(1, 3));
    t.checkExpect(this.rectangle4, this.rectangle3);
    
    t.checkExpect(this.rectangle6.getWidth(), 4);
    t.checkExpect(this.rectangle6.getHeight(), 6);
  }
  
  //------------ LineImage class --------------------------------------------//

  WorldImage line1 = new LineImage(new Posn(2, 3), new Posn(4, 5), new Green());
  WorldImage line2 = new LineImage(new Posn(2, 3), new Posn(4, 5), Color.green);
  WorldImage line3 = new LineImage(new Posn(1, 3), new Posn(4, 5), new Green());
  WorldImage line4 = new LineImage(new Posn(2, 4), new Posn(4, 5), new Green());
  WorldImage line5 = new LineImage(new Posn(2, 3), new Posn(5, 5), new Green());
  WorldImage line6 = new LineImage(new Posn(2, 3), new Posn(4, 6), new Green());
  WorldImage line7 = new LineImage(new Posn(2, 3), new Posn(4, 5), new Blue());
  WorldImage line8 = new LineImage(new Posn(1, 3), new Posn(3, 5), new Green());
  WorldImage line9 = new LineImage(new Posn(2, 4), new Posn(4, 6), new Green());
  WorldImage line10 = new LineImage(new Posn(3, 6), new Posn(5, 8), new Green());

  // Tests for the LineImage class
  void testLineImage(Tester t){
    t.checkExpect(this.line1.toString(), 
        "new LineImage(this.pinhole = (3, 4), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.startPoint = (2, 3), \nthis.endPoint = (4, 5))\n");
    t.checkExpect(this.line1, this.line2);
    t.checkExpect(this.line1, this.line3, "fails in startPoint.x");
    t.checkExpect(this.line1, this.line4, "fails in startPoint.y");
    t.checkExpect(this.line1, this.line5, "fails in endPoint.x");
    t.checkExpect(this.line1, this.line6, "fails in endPoint.y");
    t.checkExpect(this.line1, this.line7, "fails in color");
    t.checkExpect(this.line1.hashCode(), this.line2.hashCode());

    t.checkExpect(this.frame1, this.line1, "fails - different classes");
    t.checkExpect(this.line1, this.frame1, "fails - different classes");
    
    t.checkExpect(this.line1.getMovedImage(-1, 0), this.line8);
    t.checkExpect(this.line1.getMovedImage(0, 1), this.line9);
    t.checkExpect(this.line1.getMovedTo(new Posn(4, 7)), this.line10);
    
    this.line1.movePinhole(-1,  0);
    t.checkExpect(this.line1, this.line3);
    
    this.line4.moveTo(new Posn(1, 3));
    t.checkExpect(this.line4, this.line1);
    
    t.checkExpect(this.line6.getWidth(), 2);
    t.checkExpect(this.line6.getHeight(), 3);
  }
  
  //------------ TriangleImage class ----------------------------------------//

  WorldImage triangle1 = 
    new TriangleImage(new Posn(2, 3), new Posn(4, 5), new Posn(6, 7), new Black());
  WorldImage triangle2 = 
    new TriangleImage(new Posn(2, 3), new Posn(4, 5), new Posn(6, 7), Color.black);
  WorldImage triangle3 = 
    new TriangleImage(new Posn(1, 3), new Posn(4, 5), new Posn(6, 7), new Black());
  WorldImage triangle4 = 
    new TriangleImage(new Posn(2, 4), new Posn(4, 5), new Posn(6, 7), new Black());
  WorldImage triangle5 = 
    new TriangleImage(new Posn(2, 3), new Posn(5, 5), new Posn(6, 7), new Black());
  WorldImage triangle6 = 
    new TriangleImage(new Posn(2, 3), new Posn(4, 6), new Posn(6, 7), new Black());
  WorldImage triangle7 = 
      new TriangleImage(new Posn(2, 3), new Posn(4, 5), new Posn(7, 7), Color.black);
  WorldImage triangle8 = 
      new TriangleImage(new Posn(2, 3), new Posn(4, 5), new Posn(6, 8), Color.black);
  WorldImage triangle9 = 
    new TriangleImage(new Posn(2, 3), new Posn(4, 5), new Posn(6, 7), new Blue());
  WorldImage triangle10 = 
      new TriangleImage(new Posn(3, 3), new Posn(5, 5), new Posn(7, 7), new Black());
  WorldImage triangle11 = 
      new TriangleImage(new Posn(2, 6), new Posn(4, 8), new Posn(6, 10), new Black());
  WorldImage triangle12 = 
      new TriangleImage(new Posn(3, 5), new Posn(5, 7), new Posn(7, 9), new Black());

  // Tests for the TriangleImage class
  void testTriangleImage(Tester t){
    t.checkExpect(this.triangle1.toString(), 
        "new TriangleImage(this.pinhole = (4, 5), " + 
            "\nthis.color = java.awt.Color[r=0,g=0,b=0]" + 
        "\nthis.p1 = (2, 3), \nthis.p2 = (4, 5), \nthis.p3 = (6, 7))\n");
    t.checkExpect(this.triangle1, this.triangle2);
    t.checkExpect(this.triangle1, this.triangle3, "fails in p1.x");
    t.checkExpect(this.triangle1, this.triangle4, "fails in p1.y");
    t.checkExpect(this.triangle1, this.triangle5, "fails in p2.x");
    t.checkExpect(this.triangle1, this.triangle6, "fails in p2.y");
    t.checkExpect(this.triangle1, this.triangle7, "fails in p3.x");
    t.checkExpect(this.triangle1, this.triangle8, "fails in p3.y");
    t.checkExpect(this.triangle1, this.triangle9, "fails in color");
    t.checkExpect(this.triangle1.hashCode(), this.triangle2.hashCode());

    t.checkExpect(this.line1, this.triangle1, "fails - different classes");
    t.checkExpect(this.triangle1, this.line1, "fails - different classes");
    
    t.checkExpect(this.triangle1.getMovedImage(1, 0), this.triangle10);
    t.checkExpect(this.triangle1.getMovedImage(0, 3), this.triangle11);
    t.checkExpect(this.triangle1.getMovedTo(new Posn(5, 7)), this.triangle12);
    
    this.triangle1.movePinhole(1,  0);
    t.checkExpect(this.triangle1, this.triangle10);
    
    this.triangle5.moveTo(new Posn(5, 7));
    t.checkExpect(this.triangle5, this.triangle12);
    
    t.checkExpect(this.triangle8.getWidth(), 4);
    t.checkExpect(this.triangle8.getHeight(), 5);
  }
  
  //------------ TextImage class --------------------------------------------//
   
  WorldImage text1 = 
    new TextImage(new Posn(2, 3), "hello", 6, 2, new Black());
  WorldImage text2 = 
    new TextImage(new Posn(2, 3), "hello", 6, 2, Color.black);
  WorldImage text3 = 
    new TextImage(new Posn(1, 3), "hello", 6, 2, new Black());
  WorldImage text4 = 
    new TextImage(new Posn(2, 4), "hello", 6, 2, new Black());
  WorldImage text5 = 
    new TextImage(new Posn(2, 3), "hello", 7, 2, new Black());
  WorldImage text6 = 
    new TextImage(new Posn(2, 3), "hello", 6, 1, new Black());
  WorldImage text7 = 
      new TextImage(new Posn(2, 3), "hello", 6.0f, 2, Color.black);
  WorldImage text8 = 
      new TextImage(new Posn(2, 3), "hello", 6.0f, 2, new Black());
  WorldImage text9 = 
    new TextImage(new Posn(2, 3), "hello", 6, 2, new Blue());

  // Tests for the TextImage class
  void testTextImage(Tester t){
    char c = '"';
    t.checkExpect(this.text1.toString(), 
        "new TextImage(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=0,b=0]" + 
        "\nthis.size = 6.0, this.style = 2, this.alignment = 1" + 
        "\n" + c + "hello" + c + ")\n");
    t.checkExpect(this.text1, this.text2);
    t.checkExpect(this.text1, this.text3, "fails in pinhole.x");
    t.checkExpect(this.text1, this.text4, "fails in pinhole.y");
    t.checkExpect(this.text1, this.text5, "fails in size");
    t.checkExpect(this.text1, this.text6, "fails in style");
    t.checkExpect(this.text1, this.text7, "fails in alignment");
    t.checkExpect(this.text7, this.text8);
    t.checkExpect(this.text1, this.text9, "fails in color");
    t.checkExpect(this.text1.hashCode(), this.text2.hashCode());
    t.checkExpect(this.text7.hashCode(), this.text8.hashCode());

    t.checkExpect(this.line1, this.text1, "fails - different classes");
    t.checkExpect(this.text6, this.line1, "fails - different classes");
    
    t.checkExpect(this.text1.getMovedImage(-1, 0), this.text3);
    t.checkExpect(this.text1.getMovedImage(0, 1), this.text4);
    t.checkExpect(this.text3.getMovedTo(new Posn(2, 4)), this.text4);
    
    this.text1.movePinhole(11,  0);
    t.checkExpect(this.text1, this.text3);
    
    this.text3.moveTo(new Posn(5, 7));
    t.checkExpect(this.text3, this.text4);
    
    t.checkExpect(this.text1.getWidth(), 16);
    t.checkExpect(this.text1.getHeight(), 6);
  }
  
  //------------ OverlayImages class ----------------------------------------//

  WorldImage overlay1 = 
    new OverlayImages(this.circle1, this.rectangle1);
  WorldImage overlay2 = 
    new OverlayImages(this.circle2, this.rectangle2);
  WorldImage overlay3 = 
    new OverlayImages(this.circle1, this.overlay1);
  WorldImage overlay4 = 
    new OverlayImages(this.overlay1, this.circle1);
  WorldImage overlay5 = 
    new OverlayImages(this.circle1, this.ellipse1);
  WorldImage overlay6 = 
    new OverlayImages(this.overlay1, this.overlay2);
  WorldImage overlay7 = 
      new OverlayImages(this.overlay2, this.overlay1);
  WorldImage overlay8 = 
      new OverlayImages(this.circle2, this.overlay2);
  WorldImage overlay9 = 
    new OverlayImages(this.circle1, this.rectangle2);
  WorldImage overlay10 = 
      new OverlayImages(this.circle3, this.rectangle3);
  WorldImage overlay11 = 
      new OverlayImages(this.circle4, this.rectangle4);

  // Tests for the OverlayImages class
  void testOverlayImages(Tester t){
    t.checkExpect(this.overlay1.toString(), 
        "new OverlayImages(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=255,g=255,b=255]" + 
        "\nthis.bot = new CircleImage(this.pinhole = (2, 3), " + 
        "\nthis.color = java.awt.Color[r=255,g=0,b=0]" + 
            "\nthis.radius = 4)\n" + 
        "\nthis.top = new RectangleImage(this.pinhole = (2, 3), " + 
        "\nthis.color = java.awt.Color[r=0,g=255,b=0]" +  
            "\nthis.width = 4, this.height = 5)\n)\n");
          
    t.checkExpect(this.overlay1, this.overlay2);
    t.checkExpect(this.overlay1, this.overlay3, "fails in top");
    t.checkExpect(this.overlay1, this.overlay4, "fails in bot");
    t.checkExpect(this.overlay1, this.overlay5, "fails in top");
    t.checkExpect(this.overlay6, this.overlay7);
    t.checkExpect(this.overlay4, this.overlay7, "fails in top");
    t.checkExpect(this.overlay3, this.overlay8);
    t.checkExpect(this.overlay1, this.overlay9);
    t.checkExpect(this.overlay1.hashCode(), this.overlay2.hashCode());
    t.checkExpect(this.overlay1.hashCode(), this.overlay9.hashCode());

    t.checkExpect(this.line1, this.overlay1, "fails - different classes");
    t.checkExpect(this.overlay6, this.oval1, "fails - different classes"); 
    
    t.checkExpect(this.overlay1.getMovedImage(-1, 0), this.overlay10);
    t.checkExpect(this.overlay1.getMovedImage(0, 1), this.overlay11);
    t.checkExpect(this.overlay10.getMovedTo(new Posn(2, 4)), this.overlay11);
    
    t.checkExpect(this.overlay1.getWidth(), 8);
    t.checkExpect(this.overlay1.getHeight(), 8);
  }
  
  //------------ OverlayImages class ----------------------------------------//

  WorldImage overlayXY1 = 
    new OverlayImagesXY(this.circle1, this.rectangle1, 5, 3);
  WorldImage overlayXY2 = 
    new OverlayImagesXY(this.circle2, this.rectangle2, 5, 3);
  WorldImage overlayXY3 = 
    new OverlayImagesXY(this.circle1, this.overlay1, 4, 5);
  WorldImage overlayXY4 = 
    new OverlayImagesXY(this.overlay1, this.circle1, 5, 3);
  WorldImage overlayXY5 = 
    new OverlayImagesXY(this.circle1, this.ellipse1, 5, 3);
  WorldImage overlayXY6 = 
    new OverlayImagesXY(this.overlay1, this.overlay2, 5, 3);
  WorldImage overlayXY7 = 
      new OverlayImagesXY(this.overlay2, this.overlay1, 5, 3);
  WorldImage overlayXY8 = 
      new OverlayImagesXY(this.circle2, this.overlay2, 4, 5);
  WorldImage overlayXY9 = 
    new OverlayImagesXY(this.circle1, this.rectangle2, 5, 3);
  WorldImage overlayXY10 = 
      new OverlayImagesXY(this.circle3, this.rectangle3, 5, 3);
  WorldImage overlayXY11 = 
      new OverlayImagesXY(this.circle4, this.rectangle4, 5, 3);

  // Tests for the OverlayImagesXY class
  void testOverlayImagesXY(Tester t){
    t.checkExpect(this.overlayXY1.toString(), 
        "new OverlayImagesXY(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=255,g=255,b=255]" + 
            "\nthis.dx = 5, this.dy = 3," +
        "\nthis.bot = new CircleImage(this.pinhole = (2, 3), " + 
        "\nthis.color = java.awt.Color[r=255,g=0,b=0]" + 
            "\nthis.radius = 4)\n" + 
        "\nthis.top = new RectangleImage(this.pinhole = (2, 3), " + 
        "\nthis.color = java.awt.Color[r=0,g=255,b=0]" +  
            "\nthis.width = 4, this.height = 5)\n)\n");
          
    t.checkExpect(this.overlayXY1, this.overlayXY2);
    t.checkExpect(this.overlayXY1, this.overlayXY3, "fails in dx");
    t.checkExpect(this.overlayXY1, this.overlayXY4, "fails in bot");
    t.checkExpect(this.overlayXY1, this.overlayXY5, "fails in top");
    t.checkExpect(this.overlayXY6, this.overlayXY7);
    t.checkExpect(this.overlayXY4, this.overlayXY7, "fails in top");
    t.checkExpect(this.overlayXY3, this.overlayXY8);
    t.checkExpect(this.overlayXY1, this.overlayXY9);
    t.checkExpect(this.overlayXY1.hashCode(), this.overlayXY2.hashCode());
    t.checkExpect(this.overlayXY1.hashCode(), this.overlayXY9.hashCode());

    t.checkExpect(this.line1, this.overlayXY1, "fails - different classes");
    t.checkExpect(this.overlayXY6, this.oval1, "fails - different classes");  
    
    t.checkExpect(this.overlayXY1.getMovedImage(-1, 0), this.overlayXY10);
    t.checkExpect(this.overlayXY1.getMovedImage(0, 1), this.overlayXY11);
    t.checkExpect(this.overlayXY10.getMovedTo(new Posn(2, 4)), this.overlayXY11);
    
    t.checkExpect(this.overlayXY1.getWidth(), 8);
    t.checkExpect(this.overlayXY1.getHeight(), 8);
  }

  //------------ OverlayImages class ----------------------------------------//

  WorldImage fromFile1 = 
    new FromFileImage(new Posn(20, 30), "Images/shark.png");
  WorldImage fromFile2 = 
      new FromFileImage(new Posn(10, 30), "Images/shark.png");
  WorldImage fromFile3 = 
      new FromFileImage(new Posn(20, 40), "Images/shark.png");
  WorldImage fromFile4 = 
      new FromFileImage(new Posn(20, 40), "Images/fish.png");
  
  // Tests for the FromFileImage class
  void testFromFileImage(Tester t){
    t.checkExpect(this.fromFile1.toString(), 
        "new FromFileImage(this.pinhole = (20, 30)" + 
            "\nthis.fileName = Images/shark.png)\n");
    WorldImage fromFile5 = this.fromFile2.getMovedImage(10, 0);
          
    t.checkExpect(this.fromFile1, this.fromFile2, "fails in pinhole.x");
    t.checkExpect(this.fromFile1, this.fromFile3, "fails in pinhole.y");
    t.checkExpect(this.fromFile3, this.fromFile4, "fails in fileName");
    t.checkExpect(this.fromFile1, fromFile5);

    t.checkExpect(this.fromFile1, this.text1, "fails - different classes");
    t.checkExpect(this.line2, this.fromFile4, "fails - different classes");   
    
    t.checkExpect(this.fromFile1.getMovedImage(-10, 0), this.fromFile2);
    t.checkExpect(this.fromFile1.getMovedImage(0, 10), this.fromFile3);
    t.checkExpect(this.fromFile2.getMovedTo(new Posn(20, 40)), this.fromFile3);
    
    t.checkExpect(this.fromFile1.getWidth(), 108);
    t.checkExpect(this.fromFile1.getHeight(), 51);
  }
  
  // Run all tests - comment out those you want to skip
  public void tests(Tester t){
    //testCircleImage(t);
    //testDiskImage(t);
    //testEllipseImage(t);
    //testFrameImage(t);
    //testOvalImage(t);
    //testRectangleImage(t);
    //testLineImage(t);
    //testTriangleImage(t);
    //testTextImage(t);
    //testOverlayImages(t);
    //testOverlayImagesXY(t);
    testFromFileImage(t);
  }

  
  public static void main(String[] argv){
    ExamplesImageMethods e = new ExamplesImageMethods();
    Tester.runReport(e, false, true); 
  }
}