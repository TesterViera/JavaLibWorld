package worldimagestests;

import javalib.colors.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;

import java.awt.*;

import tester.Tester;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * A complete set of images displayed in the Canvas
 * 
 * @author Viera K. Proulx
 * @since 5 February 2012
 */
public class ExamplesImageDrawings  {

  public ExamplesImageDrawings() {}

  // support for the regression tests
  public static ExamplesImageDrawings examplesInstance = 
      new ExamplesImageDrawings();
  
  // a text inside a red rectangle with a yellow dot in its pinhole location
  public static WorldImage makeText(Posn pos, int size){
    WorldImage hello = 
      new TextImage(pos, "quickbrownfoxjumpedoveralazydog", size, 3, new Blue());

    WorldImage helloRed = 
        new OverlayImages(
            new RectangleImage(pos, 
                hello.getWidth(), hello.getHeight(), 
                new Red()),
                hello);
    return 
        new OverlayImages(helloRed,
            new DiskImage(hello.pinhole, 2, new Yellow()));
  }
  
  WorldImage circleText = 
      new TextImage(new Posn(200, 20),
          "CircleImage(new Posn(200, 60), 10, new Red())", Color.red);
  WorldImage circle = new CircleImage(new Posn(200, 60), 10, new Red());
  

  WorldImage diskText = 
      new TextImage(new Posn(200, 100),
          "DiskImage(new Posn(200, 140), 10, new Red())", Color.red);
  WorldImage disk = new DiskImage(new Posn(200, 140), 10, new Red());
  

  WorldImage lineText = 
      new TextImage(new Posn(220, 180),
          "LineImage(new Posn(200, 220), new Posn(280, 230), Color.green)", Color.green);
  WorldImage line = 
      new LineImage(new Posn(200, 220), new Posn(280, 230), Color.green);
  

  WorldImage triangleText = 
      new TextImage(new Posn(280, 260),
      "TriangleImage(new Posn(250, 300), new Posn(200, 340), new Posn(150, 310), Color.cyan)", 
      Color.cyan);
  WorldImage triangle = 
      new TriangleImage(new Posn(250, 300), new Posn(200, 340), new Posn(150, 310), Color.cyan);
  

  WorldImage ellipseText = 
      new TextImage(new Posn(600, 20),
          "EllipseImage(new Posn(600, 60), 60, 20, new Blue())", new Blue());
  WorldImage ellipse = new EllipseImage(new Posn(600, 60), 60, 20, new Blue());
  
  WorldImage frameText = 
      new TextImage(new Posn(600, 100),
          "FrameImage(new Posn(600, 120), 60, 20, new Black())", new Black());
  WorldImage frame = new FrameImage(new Posn(600, 120), 60, 20, new Black());
  
  WorldImage ovalText = 
      new TextImage(new Posn(600, 180),
          "OvalImage(new Posn(600, 220), 60, 20, new Yellow())", new Yellow());
  WorldImage oval = new OvalImage(new Posn(600, 220), 60, 20, new Yellow());
  
  WorldImage rectangleText = 
      new TextImage(new Posn(600, 300),
          "RectangleImage(new Posn(600, 330), 60, 20, Color.orange)", Color.orange);
  WorldImage rectangle = new RectangleImage(new Posn(600, 330), 60, 20, Color.orange);
  
  WorldImage fromFileText = 
      new TextImage(new Posn(600, 420),
          "FromFileImage(new Posn(600, 480), Images/fish.png)", Color.black);
  WorldImage fish = new FromFileImage(new Posn(600, 480), "Images/fish.png");
  
  WorldImage combined = 
      this.circleText.overlayImages(
          this.circle, 
          this.diskText,
          this.disk,
          this.lineText,
          this.line,
          this.triangleText,
          this.triangle,
          this.ellipseText,
          this.ellipse,
          this.frameText,
          this.frame,
          this.ovalText,
          this.oval,
          this.rectangleText,
          this.rectangle,
          this.fromFileText,
          this.fish);

  public void testAll(Tester t){
    String[] args = new String[]{};
    ExamplesImageDrawings.main(args);
  }

  public static void main(String[] args) {

    WorldCanvas c = new WorldCanvas(800, 600);

    WorldImage pic = ExamplesImageDrawings.makeText(new Posn(300, 400), 15);

    ExamplesImageDrawings e = new ExamplesImageDrawings();
    
    // show several images in the canvas 
    boolean makeDrawing = 
        c.show() && 
        c.drawImage(e.combined) &&
        c.drawImage(pic);
  }
}
