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
 * @author Viera K. Proulx, tweaked by Stephen Bloch to use a different API to worldimages
 * @since Dec 26 2012
 *
 */
public class ExamplesImageMethods implements IExamples{
  ExamplesImageMethods(){}

  // support for the regression tests
  public static ExamplesImageMethods examplesInstance = 
      new ExamplesImageMethods();

  //------------ CircleImage class ------------------------------------------//

  WorldImage circle1 = AImage.makeCenteredCircle(new Posn(2, 3), 4, new Red());
  WorldImage circle2 = AImage.makeCenteredCircle(new Posn(2, 3), 4, Color.red);
  WorldImage circle3 = AImage.makeCenteredCircle(new Posn(1, 3), 4, new Red());
  WorldImage circle4 = AImage.makeCenteredCircle(new Posn(2, 4), 4, new Red());
  WorldImage circle5 = AImage.makeCenteredCircle(new Posn(2, 3), 5, new Red());
  WorldImage circle6 = AImage.makeCenteredCircle(new Posn(2, 3), 4, new Blue());

  // Tests for the CircleImage class
  void testCircleImage(Tester t){
    t.checkExpect(this.circle1.toString(), 
        "AImage.makeCenteredCircle(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=255,g=0,b=0]" + 
        "\nthis.radius = 4)\n");
    t.checkExpect(this.circle1, this.circle2);
    t.checkFail(this.circle1, this.circle3, "fails in x");
    t.checkFail(this.circle1, this.circle4, "fails in y");
    t.checkFail(this.circle1, this.circle5, "fails in radius");
    t.checkFail(this.circle1, this.circle6, "fails in color");
    t.checkExpect(this.circle1.hashCode(), this.circle2.hashCode());
    
    t.checkExpect(this.circle1.moved(-1, 0), this.circle3);
    t.checkExpect(this.circle1.moved(0, 1), this.circle4);
    t.checkExpect(this.circle1.normalized().moved(1, 3), this.circle3);
    
    /* Mutating tests: I haven't implemented mutation yet.
    this.circle1.movePinhole(-1,  0);
    t.checkExpect(this.circle1, this.circle3);
    
    this.circle4.moveTo(new Posn(1, 3));
    t.checkExpect(this.circle4, this.circle3);
    */
    
    t.checkExpect(this.circle5.getWidth(), 10);
    t.checkExpect(this.circle5.getHeight(), 10);
    
    t.checkExpect (this.circle1.getCenter(), new Posn(2,3));
    t.checkExpect (this.circle4.getCenter(), new Posn(2,4));
  }

  //------------ DiskImage class --------------------------------------------//

  WorldImage disk1 = AImage.makeCenteredCircle(new Posn(2, 3), 4, new Red(), Mode.FILLED);
  WorldImage disk2 = AImage.makeCenteredCircle(new Posn(2, 3), 4, Color.red, Mode.FILLED);
  WorldImage disk3 = AImage.makeCenteredCircle(new Posn(1, 3), 4, new Red(), Mode.FILLED);
  WorldImage disk4 = AImage.makeCenteredCircle(new Posn(2, 4), 4, new Red(), Mode.FILLED);
  WorldImage disk5 = AImage.makeCenteredCircle(new Posn(2, 3), 5, new Red(), Mode.FILLED);
  WorldImage disk6 = AImage.makeCenteredCircle(new Posn(2, 3), 4, new Blue(), Mode.FILLED);

  // Tests for the DiskImage class
  void testDiskImage(Tester t){
    t.checkExpect(this.disk1.toString(), 
        "AImage.makeCenteredCircleFILLED(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=255,g=0,b=0]" + 
        "\nthis.radius = 4)\n");
    t.checkExpect(this.disk1, this.disk2);
    t.checkFail(this.disk1, this.disk3, "fails in x");
    t.checkFail(this.disk1, this.disk4, "fails in y");
    t.checkFail(this.disk1, this.disk5, "fails in radius");
    t.checkFail(this.disk1, this.disk6, "fails in color");
    t.checkExpect(this.disk1.hashCode(), this.disk2.hashCode());

    t.checkFail(this.disk1, this.circle1, "fails - different classes");
    t.checkFail(this.circle1, this.disk1, "fails - different classes");
    
    t.checkExpect(this.disk1.moved(-1, 0), this.disk3);
    t.checkExpect(this.disk1.moved(0, 1), this.disk4);
    t.checkExpect(this.disk1.normalized().moved(1, 3), this.disk3);
    
    /* Mutating tests... not implemented yet.
    this.disk1.movePinhole(-1,  0);
    t.checkExpect(this.disk1, this.disk3);
    
    this.disk4.moveTo(new Posn(1, 3));
    t.checkExpect(this.disk4, this.disk3);
    */
    t.checkExpect(this.disk5.getWidth(), 10);
    t.checkExpect(this.disk5.getHeight(), 10);

    t.checkExpect (this.disk1.getCenter(), new Posn(2,3));
    t.checkExpect (this.disk4.getCenter(), new Posn(2,4));
  }
  
  //------------ EllipseImage class -----------------------------------------//

  WorldImage ellipse1 = AImage.makeEllipse(4, 5, new Green()).moved(2,3);
  WorldImage ellipse2 = AImage.makeEllipse(4, 5, Color.green).moved(2,3);
  WorldImage ellipse3 = AImage.makeEllipse(4, 5, new Green()).moved(1,3);
  WorldImage ellipse4 = AImage.makeEllipse(4, 5, new Green()).moved(2,4);
  WorldImage ellipse5 = AImage.makeEllipse(5, 5, new Green()).moved(2,3);
  WorldImage ellipse6 = AImage.makeEllipse(4, 6, new Green()).moved(2,3);
  WorldImage ellipse7 = AImage.makeEllipse(4, 5, new Blue()).moved(2,3);

  // Tests for the EllipseImage class
  void testEllipseImage(Tester t){
    t.checkExpect(this.ellipse1.toString(), 
        "AImage.makeEllipse(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.ellipse1, this.ellipse2);
    t.checkFail(this.ellipse1, this.ellipse3, "fails in x");
    t.checkFail(this.ellipse1, this.ellipse4, "fails in y");
    t.checkFail(this.ellipse1, this.ellipse5, "fails in width");
    t.checkFail(this.ellipse1, this.ellipse6, "fails in height");
    t.checkFail(this.ellipse1, this.ellipse7, "fails in color");
    t.checkExpect(this.ellipse1.hashCode(), this.ellipse2.hashCode());

    t.checkFail(this.disk1, this.ellipse1, "fails - different classes");
    t.checkFail(this.ellipse1, this.disk1, "fails - different classes");
    
    t.checkExpect(this.ellipse1.moved(-1, 0), this.ellipse3);
    t.checkExpect(this.ellipse1.moved(0, 1), this.ellipse4);
    t.checkExpect(this.ellipse1.normalized().moved(1, 3), this.ellipse3);
    
    /* Mutating tests ... not implemented yet.
    this.ellipse1.movePinhole(-1,  0);
    t.checkExpect(this.ellipse1, this.ellipse3);
    
    this.ellipse4.moveTo(new Posn(1, 3));
    t.checkExpect(this.ellipse4, this.ellipse3);
    */
    
    t.checkExpect(this.ellipse6.getWidth(), 4);
    t.checkExpect(this.ellipse6.getHeight(), 6);
    
    t.checkExpect (AImage.makeEllipse(4, 5, new Green()).getCenter(), new Posn(2,3));
    t.checkExpect (this.ellipse1.getCenter(), new Posn(4,6));
  }
  
  //------------ FrameImage class -----------------------------------------//

  WorldImage frame1 = AImage.makeRectangle(4, 5, new Green()).moved(2,3);
  WorldImage frame2 = AImage.makeRectangle(4, 5, Color.green).moved(2,3);
  WorldImage frame3 = AImage.makeRectangle(4, 5, new Green()).moved(1,3);
  WorldImage frame4 = AImage.makeRectangle(4, 5, new Green()).moved(2,4);
  WorldImage frame5 = AImage.makeRectangle(5, 5, new Green()).moved(2,3);
  WorldImage frame6 = AImage.makeRectangle(4, 6, new Green()).moved(2,3);
  WorldImage frame7 = AImage.makeRectangle(4, 5, new Blue()).moved(2,3);

  // Tests for the FrameImage class
  void testFrameImage(Tester t){
    t.checkExpect(this.frame1.toString(), 
        "AImage.makeRectangle(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.frame1, this.frame2);
    t.checkFail(this.frame1, this.frame3, "fails in pinhole.x");
    t.checkFail(this.frame1, this.frame4, "fails in pinhole.y");
    t.checkFail(this.frame1, this.frame5, "fails in width");
    t.checkFail(this.frame1, this.frame6, "fails in height");
    t.checkFail(this.frame1, this.frame7, "fails in color");
    t.checkExpect(this.frame1.hashCode(), this.frame2.hashCode());

    t.checkFail(this.disk1, this.frame1, "fails - different classes");
    t.checkFail(this.frame1, this.disk1, "fails - different classes");
    
    t.checkExpect(this.frame1.moved(-1, 0), this.frame3);
    t.checkExpect(this.frame1.moved(0, 1), this.frame4);
    t.checkExpect(this.frame1.normalized().moved(1, 3), this.frame3);
    
    /* Mutating tests, not implemented yet.
    this.frame1.movePinhole(-1,  0);
    t.checkExpect(this.frame1, this.frame3);
    
    this.frame4.moveTo(new Posn(1, 3));
    t.checkExpect(this.frame4, this.frame3);
    */
   
    t.checkExpect(this.frame6.getWidth(), 4);
    t.checkExpect(this.frame6.getHeight(), 6);
    
    t.checkExpect (this.frame5.getCenter(), new Posn(4,5));
  }
  
  //------------ OvalImage class -----------------------------------------//

  WorldImage oval1 = AImage.makeEllipse(4, 5, new Green(), Mode.FILLED).moved(2,3);
  WorldImage oval2 = AImage.makeEllipse(4, 5, Color.green, Mode.FILLED).moved(2,3);
  WorldImage oval3 = AImage.makeEllipse(4, 5, new Green(), Mode.FILLED).moved(1,3);
  WorldImage oval4 = AImage.makeEllipse(4, 5, new Green(), Mode.FILLED).moved(2,4);
  WorldImage oval5 = AImage.makeEllipse(5, 5, new Green(), Mode.FILLED).moved(2,3);
  WorldImage oval6 = AImage.makeEllipse(4, 6, new Green(), Mode.FILLED).moved(2,3);
  WorldImage oval7 = AImage.makeEllipse(4, 5, new Blue(), Mode.FILLED).moved(2,3);

  // Tests for the OvalImage class
  void testOvalImage(Tester t){
    t.checkExpect(this.oval1.toString(), 
        "AImage.makeEllipse(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.oval1, this.oval2);
    t.checkFail(this.oval1, this.oval3, "fails in pinhole.x");
    t.checkFail(this.oval1, this.oval4, "fails in pinhole.y");
    t.checkFail(this.oval1, this.oval5, "fails in width");
    t.checkFail(this.oval1, this.oval6, "fails in height");
    t.checkFail(this.oval1, this.oval7, "fails in color");
    t.checkExpect(this.oval1.hashCode(), this.oval2.hashCode());

    t.checkFail(this.frame1, this.oval1, "fails - different classes");
    t.checkFail(this.oval1, this.frame1, "fails - different classes");
    
    t.checkExpect(this.oval1.moved(-1, 0), this.oval3);
    t.checkExpect(this.oval1.moved(0, 1), this.oval4);
    t.checkExpect(this.oval1.normalized().moved(1, 3), this.oval3);
    
    /* Mutating tests, not implemented yet.
    this.oval1.movePinhole(-1,  0);
    t.checkExpect(this.oval1, this.oval3);
    
    this.oval4.moveTo(new Posn(1, 3));
    t.checkExpect(this.oval4, this.oval3);
    */
    
    t.checkExpect(this.oval6.getWidth(), 4);
    t.checkExpect(this.oval6.getHeight(), 6);
    
    t.checkExpect (this.oval7.getCenter(), new Posn(4,5));
  }
  
  //------------ RectangleImage class ---------------------------------------//

  WorldImage rectangle1 = AImage.makeRectangle(4, 5, new Green(), Mode.FILLED).moved(2,3);
  WorldImage rectangle2 = AImage.makeRectangle(4, 5, Color.green, Mode.FILLED).moved(2,3);
  WorldImage rectangle3 = AImage.makeRectangle(4, 5, new Green(), Mode.FILLED).moved(1,3);
  WorldImage rectangle4 = AImage.makeRectangle(4, 5, new Green(), Mode.FILLED).moved(2,4);
  WorldImage rectangle5 = AImage.makeRectangle(5, 5, new Green(), Mode.FILLED).moved(2,3);
  WorldImage rectangle6 = AImage.makeRectangle(4, 6, new Green(), Mode.FILLED).moved(2,3);
  WorldImage rectangle7 = AImage.makeRectangle(4, 5, new Blue(), Mode.FILLED).moved(2,3);

  // Tests for the RectangleImage class
  void testRectangleImage(Tester t){
    t.checkExpect(this.rectangle1.toString(), 
        "AImage.makeRectangle(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.width = 4, this.height = 5)\n");
    t.checkExpect(this.rectangle1, this.rectangle2);
    t.checkFail(this.rectangle1, this.rectangle3, "fails in pinhole.x");
    t.checkFail(this.rectangle1, this.rectangle4, "fails in pinhole.y");
    t.checkFail(this.rectangle1, this.rectangle5, "fails in width");
    t.checkFail(this.rectangle1, this.rectangle6, "fails in height");
    t.checkFail(this.rectangle1, this.rectangle7, "fails in color");
    t.checkExpect(this.rectangle1.hashCode(), this.rectangle2.hashCode());

    t.checkFail(this.frame1, this.rectangle1, "fails - different classes");
    t.checkFail(this.rectangle1, this.frame1, "fails - different classes");
    
    t.checkExpect(this.rectangle1.moved(-1, 0), this.rectangle3);
    t.checkExpect(this.rectangle1.moved(0, 1), this.rectangle4);
    t.checkExpect(this.rectangle1.normalized().moved(1, 3), this.rectangle3);
    
    /* Mutating tests, not implemented yet.
    this.rectangle1.movePinhole(-1,  0);
    t.checkExpect(this.rectangle1, this.rectangle3);
    
    this.rectangle4.moveTo(new Posn(1, 3));
    t.checkExpect(this.rectangle4, this.rectangle3);
    */
   
    t.checkExpect(this.rectangle6.getWidth(), 4);
    t.checkExpect(this.rectangle6.getHeight(), 6);
    
    t.checkExpect (this.rectangle3.getCenter(), new Posn(3,5));
  }
  
  //------------ LineImage class --------------------------------------------//

  WorldImage line1 = AImage.makeLine(new Posn(2, 3), new Posn(4, 5), new Green());
  WorldImage line2 = AImage.makeLine(new Posn(2, 3), new Posn(4, 5), Color.green);
  WorldImage line3 = AImage.makeLine(new Posn(1, 3), new Posn(4, 5), new Green());
  WorldImage line4 = AImage.makeLine(new Posn(2, 4), new Posn(4, 5), new Green());
  WorldImage line5 = AImage.makeLine(new Posn(2, 3), new Posn(5, 5), new Green());
  WorldImage line6 = AImage.makeLine(new Posn(2, 3), new Posn(4, 6), new Green());
  WorldImage line7 = AImage.makeLine(new Posn(2, 3), new Posn(4, 5), new Blue());
  WorldImage line8 = AImage.makeLine(new Posn(1, 3), new Posn(3, 5), new Green());
  WorldImage line9 = AImage.makeLine(new Posn(2, 4), new Posn(4, 6), new Green());
  WorldImage line10 = AImage.makeLine(new Posn(3, 6), new Posn(5, 8), new Green());

  // Tests for the LineImage class
  void testLineImage(Tester t){
    t.checkExpect(this.line1.toString(), 
        "AImage.makeLine(this.pinhole = (3, 4), " + 
            "\nthis.color = java.awt.Color[r=0,g=255,b=0]" + 
        "\nthis.startPoint = (2, 3), \nthis.endPoint = (4, 5))\n");
    t.checkExpect(this.line1, this.line2);
    t.checkFail(this.line1, this.line3, "fails in startPoint.x");
    t.checkFail(this.line1, this.line4, "fails in startPoint.y");
    t.checkFail(this.line1, this.line5, "fails in endPoint.x");
    t.checkFail(this.line1, this.line6, "fails in endPoint.y");
    t.checkFail(this.line1, this.line7, "fails in color");
    t.checkExpect(this.line1.hashCode(), this.line2.hashCode());

    t.checkFail(this.frame1, this.line1, "fails - different classes");
    t.checkFail(this.line1, this.frame1, "fails - different classes");
    
    t.checkExpect(this.line1.moved(-1, 0), this.line8);
    t.checkExpect(this.line1.moved(0, 1), this.line9);
    t.checkExpect(this.line1.normalized().moved(4, 7), this.line10);
    
    /* Mutating tests, not implemented yet.
    this.line1.movePinhole(-1,  0);
    t.checkExpect(this.line1, this.line3);
    
    this.line4.moveTo(new Posn(1, 3));
    t.checkExpect(this.line4, this.line1);
    */
    
    t.checkExpect(this.line6.getWidth(), 2);
    t.checkExpect(this.line6.getHeight(), 3);
    
    t.checkExpect (this.line10.getCenter(), new Posn(4,7));
  }
  
  //------------ TriangleImage class ----------------------------------------//

  WorldImage triangle1 = 
    AImage.makeTriangle(new Posn(2, 3), new Posn(4, 5), new Posn(6, 7), new Black());
  WorldImage triangle2 = 
    AImage.makeTriangle(new Posn(2, 3), new Posn(4, 5), new Posn(6, 7), Color.black);
  WorldImage triangle3 = 
    AImage.makeTriangle(new Posn(1, 3), new Posn(4, 5), new Posn(6, 7), new Black());
  WorldImage triangle4 = 
    AImage.makeTriangle(new Posn(2, 4), new Posn(4, 5), new Posn(6, 7), new Black());
  WorldImage triangle5 = 
    AImage.makeTriangle(new Posn(2, 3), new Posn(5, 5), new Posn(6, 7), new Black());
  WorldImage triangle6 = 
    AImage.makeTriangle(new Posn(2, 3), new Posn(4, 6), new Posn(6, 7), new Black());
  WorldImage triangle7 = 
      AImage.makeTriangle(new Posn(2, 3), new Posn(4, 5), new Posn(7, 7), Color.black);
  WorldImage triangle8 = 
      AImage.makeTriangle(new Posn(2, 3), new Posn(4, 5), new Posn(6, 8), Color.black);
  WorldImage triangle9 = 
    AImage.makeTriangle(new Posn(2, 3), new Posn(4, 5), new Posn(6, 7), new Blue());
  WorldImage triangle10 = 
      AImage.makeTriangle(new Posn(3, 3), new Posn(5, 5), new Posn(7, 7), new Black());
  WorldImage triangle11 = 
      AImage.makeTriangle(new Posn(2, 6), new Posn(4, 8), new Posn(6, 10), new Black());
  WorldImage triangle12 = 
      AImage.makeTriangle(new Posn(3, 5), new Posn(5, 7), new Posn(7, 9), new Black());

  // Tests for the TriangleImage class
  void testTriangleImage(Tester t){
    t.checkExpect(this.triangle1.toString(), 
        "AImage.makeTriangle(this.pinhole = (4, 5), " + 
            "\nthis.color = java.awt.Color[r=0,g=0,b=0]" + 
        "\nthis.p1 = (2, 3), \nthis.p2 = (4, 5), \nthis.p3 = (6, 7))\n");
    t.checkExpect(this.triangle1, this.triangle2);
    t.checkFail(this.triangle1, this.triangle3, "fails in p1.x");
    t.checkFail(this.triangle1, this.triangle4, "fails in p1.y");
    t.checkFail(this.triangle1, this.triangle5, "fails in p2.x");
    t.checkFail(this.triangle1, this.triangle6, "fails in p2.y");
    t.checkFail(this.triangle1, this.triangle7, "fails in p3.x");
    t.checkFail(this.triangle1, this.triangle8, "fails in p3.y");
    t.checkFail(this.triangle1, this.triangle9, "fails in color");
    t.checkExpect(this.triangle1.hashCode(), this.triangle2.hashCode());

    t.checkFail(this.line1, this.triangle1, "fails - different classes");
    t.checkFail(this.triangle1, this.line1, "fails - different classes");
    
    t.checkExpect(this.triangle1.moved(1, 0), this.triangle10);
    t.checkExpect(this.triangle1.moved(0, 3), this.triangle11);
    t.checkExpect(this.triangle1.normalized().moved(5, 7), this.triangle12);
    
    /* Mutating tests, not implemented yet.
    this.triangle1.movePinhole(1,  0);
    t.checkExpect(this.triangle1, this.triangle10);
    
    this.triangle5.moveTo(new Posn(5, 7));
    t.checkExpect(this.triangle5, this.triangle12);
    */
    
    t.checkExpect(this.triangle8.getWidth(), 4);
    t.checkExpect(this.triangle8.getHeight(), 5);
    
    t.checkExpect (this.triangle1.getCenter(), new Posn(4,5));
    // note getCenter does NOT return the centroid of the vertices, but rather the center
    // of the x/y-aligned bounding rectangle!
  }
  
  //------------ TextImage class --------------------------------------------//
   
  WorldImage text1 = 
    AImage.makeText("hello", 6, TextStyle.BOLD, new Black()).moved(2,3); // intermittent crash
  WorldImage text2 = 
    AImage.makeText("hello", 6, TextStyle.BOLD, Color.black).moved(2,3);
  WorldImage text3 = 
    AImage.makeText("hello", 6, TextStyle.BOLD, new Black()).moved(1,3);
  WorldImage text4 = 
    AImage.makeText("hello", 6, TextStyle.BOLD, new Black()).moved(2,4);
  WorldImage text5 = 
    AImage.makeText("hello", 7, TextStyle.BOLD, new Black()).moved(2,3);
  WorldImage text6 = 
    AImage.makeText("hello", 6, TextStyle.ITALIC, new Black()).moved(2,3);
  WorldImage text7 = 
    AImage.makeText("hello", 6.0f, TextStyle.BOLD, Color.black).moved(2,3);
  WorldImage text8 = 
    AImage.makeText("hello", 6.0f, TextStyle.BOLD, new Black()).moved(2,3);
  WorldImage text9 = 
    AImage.makeText("hello", 6, TextStyle.BOLD, new Blue()).moved(2,3);

  // Tests for the TextImage class
  void testTextImage(Tester t){
    char c = '"';
    t.checkExpect(this.text1.toString(), 
        "AImage.makeText(this.pinhole = (2, 3), " + 
            "\nthis.color = java.awt.Color[r=0,g=0,b=0]" + 
        "\nthis.size = 6.0, this.style = 2, this.alignment = 1" + 
        "\n" + c + "hello" + c + ")\n");
    t.checkExpect(this.text1, this.text2);
    t.checkFail(this.text1, this.text3, "fails in pinhole.x");
    t.checkFail(this.text1, this.text4, "fails in pinhole.y");
    t.checkFail(this.text1, this.text5, "fails in size");
    t.checkFail(this.text1, this.text6, "fails in style");
    t.checkFail(this.text1, this.text7, "fails in alignment");
    t.checkExpect(this.text7, this.text8);
    t.checkFail(this.text1, this.text9, "fails in color");
    t.checkExpect(this.text1.hashCode(), this.text2.hashCode());
    t.checkExpect(this.text7.hashCode(), this.text8.hashCode());

    t.checkFail(this.line1, this.text1, "fails - different classes");
    t.checkFail(this.text6, this.line1, "fails - different classes");
    
    t.checkExpect(this.text1.moved(-1, 0), this.text3);
    t.checkExpect(this.text1.moved(0, 1), this.text4);
    t.checkExpect(this.text3.normalized().moved(2, 4), this.text4);
    
    /* Mutating tests, not implemented yet.
    this.text1.movePinhole(11,  0);
    t.checkExpect(this.text1, this.text3);
    
    this.text3.moveTo(new Posn(5, 7));
    t.checkExpect(this.text3, this.text4);
    */
   
    t.checkExpect(this.text1.getWidth(), 16);
    t.checkExpect(this.text1.getHeight(), 6);
    
    t.checkExpect (this.text1.getCenter(), new Posn(10, 6));
  }
  
  //------------ OverlayImages class ----------------------------------------//

  WorldImage overlay1 = this.circle1.overlay(this.rectangle1);
  WorldImage overlay2 = this.circle2.overlay(this.rectangle2);
  WorldImage overlay3 = this.circle1.overlay(this.overlay1);
  WorldImage overlay4 = this.overlay1.overlay(this.circle1);
  WorldImage overlay5 = this.circle1.overlay(this.ellipse1);
  WorldImage overlay6 = this.overlay1.overlay(this.overlay2);
  WorldImage overlay7 = this.overlay2.overlay(this.overlay1);
  WorldImage overlay8 = this.circle2.overlay(this.overlay2);
  WorldImage overlay9 = this.circle1.overlay(this.rectangle2);
  WorldImage overlay10 = this.circle3.overlay(this.rectangle3);
  WorldImage overlay11 = this.circle4.overlay(this.rectangle4);

  // Tests for the OverlayImages class
  void testOverlayImages(Tester t){
    t.checkExpect(this.overlay1.toString(), 
        "blah blah");
          
    t.checkExpect(this.overlay1, this.overlay2);
    t.checkFail(this.overlay1, this.overlay3, "fails in top");
    t.checkFail(this.overlay1, this.overlay4, "fails in bot");
    t.checkFail(this.overlay1, this.overlay5, "fails in top");
    t.checkExpect(this.overlay6, this.overlay7);
    t.checkFail(this.overlay4, this.overlay7, "fails in top");
    t.checkExpect(this.overlay3, this.overlay8);
    t.checkExpect(this.overlay1, this.overlay9);
    t.checkExpect(this.overlay1.hashCode(), this.overlay2.hashCode());
    t.checkExpect(this.overlay1.hashCode(), this.overlay9.hashCode());

    t.checkFail(this.line1, this.overlay1, "fails - different classes");
    t.checkFail(this.overlay6, this.oval1, "fails - different classes"); 
    
    t.checkExpect(this.overlay1.moved(-1, 0), this.overlay10);
    t.checkExpect(this.overlay1.moved(0, 1), this.overlay11);
    t.checkExpect(this.overlay10.normalized().moved(2, 4), this.overlay11);
    
    t.checkExpect(this.overlay1.getWidth(), 8);
    t.checkExpect(this.overlay1.getHeight(), 8);
    
    t.checkExpect (this.overlay1.getCenter(), new Posn(3,4));
    // bounding box is from (0,1) to (6,8)
  }
  
  //------------ OverlayImages class ----------------------------------------//

  WorldImage overlayXY1 = this.circle1.overlayXY (this.rectangle1, 5, 3);
  WorldImage overlayXY2 = this.circle2.overlayXY (this.rectangle2, 5, 3);
  WorldImage overlayXY3 = this.circle1.overlayXY (this.overlay1, 4, 5);
  WorldImage overlayXY4 = this.overlay1.overlayXY (this.circle1, 5, 3);
  WorldImage overlayXY5 = this.circle1.overlayXY (this.ellipse1, 5, 3);
  WorldImage overlayXY6 = this.overlay1.overlayXY (this.overlay2, 5, 3);
  WorldImage overlayXY7 = this.overlay2.overlayXY (this.overlay1, 5, 3);
  WorldImage overlayXY8 = this.circle2.overlayXY (this.overlay2, 4, 5);
  WorldImage overlayXY9 = this.circle1.overlayXY (this.rectangle2, 5, 3);
  WorldImage overlayXY10 = this.circle3.overlayXY (this.rectangle3, 5, 3);
  WorldImage overlayXY11 = this.circle4.overlayXY (this.rectangle4, 5, 3);

  // Tests for the OverlayImagesXY class
  void testOverlayImagesXY(Tester t){
    t.checkExpect(this.overlayXY1.toString(), 
        "blah blah");
          
    t.checkExpect(this.overlayXY1, this.overlayXY2);
    t.checkFail(this.overlayXY1, this.overlayXY3, "fails in dx");
    t.checkFail(this.overlayXY1, this.overlayXY4, "fails in bot");
    t.checkFail(this.overlayXY1, this.overlayXY5, "fails in top");
    t.checkExpect(this.overlayXY6, this.overlayXY7);
    t.checkFail(this.overlayXY4, this.overlayXY7, "fails in top");
    t.checkExpect(this.overlayXY3, this.overlayXY8);
    t.checkExpect(this.overlayXY1, this.overlayXY9);
    t.checkExpect(this.overlayXY1.hashCode(), this.overlayXY2.hashCode());
    t.checkExpect(this.overlayXY1.hashCode(), this.overlayXY9.hashCode());

    t.checkFail(this.line1, this.overlayXY1, "fails - different classes");
    t.checkFail(this.overlayXY6, this.oval1, "fails - different classes");  
    
    t.checkExpect(this.overlayXY1.moved(-1, 0), this.overlayXY10);
    t.checkExpect(this.overlayXY1.moved(0, 1), this.overlayXY11);
    t.checkExpect(this.overlayXY10.normalized().moved(2, 4), this.overlayXY11);
    
    t.checkExpect(this.overlayXY1.getWidth(), 11);
    t.checkExpect(this.overlayXY1.getHeight(), 10);
    
    t.checkExpect (this.overlayXY1.getCenter(), new Posn(5,6));
    // bounding box is from (0,1) to (11,11)
  }

  //------------ OverlayImages class ----------------------------------------//

  WorldImage fromFile1 = AImage.makeFromFile ("Images/shark.png").moved(20,30); // intermittent crash
  WorldImage fromFile2 = AImage.makeFromFile ("Images/shark.png").moved(10,30);
  WorldImage fromFile3 = AImage.makeFromFile ("Images/shark.png").moved(20,40);
  WorldImage fromFile4 = AImage.makeFromFile ("Images/fish.png").moved(20,40);
  
  // Tests for the FromFileImage class
  void testFromFileImage(Tester t){
    t.checkExpect(this.fromFile1.toString(), 
        "new FromFileImage (this.filename = \"Images/shark.png\")\n");
    WorldImage fromFile5 = this.fromFile2.moved(10, 0);
          
    t.checkFail(this.fromFile1, this.fromFile2, "fails in pinhole.x");
    t.checkFail(this.fromFile1, this.fromFile3, "fails in pinhole.y");
    t.checkFail(this.fromFile3, this.fromFile4, "fails in fileName");
    t.checkExpect(this.fromFile1, fromFile5);

    t.checkFail(this.fromFile1, this.text1, "fails - different classes");
    t.checkFail(this.line2, this.fromFile4, "fails - different classes");   
    
    t.checkExpect(this.fromFile1.moved(-10, 0), this.fromFile2);
    t.checkExpect(this.fromFile1.moved(0, 10), this.fromFile3);
    t.checkExpect(this.fromFile2.normalized().moved(20, 40), this.fromFile3);
    
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
      int x = 3;
    ExamplesImageMethods e = new ExamplesImageMethods();
    Tester.runReport(e, false, true); 
  }
}