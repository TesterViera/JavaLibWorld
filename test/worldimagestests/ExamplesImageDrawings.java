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
      AImage.makeText("quickbrownfoxjumpedoveralazydog", size, TextStyle.BOLD_ITALIC, new Blue()).moved(pos);

    WorldImage helloRed = 
            AImage.makeRectangle(hello.getWidth(), hello.getHeight(), new Red(), Mode.FILLED).moved(pos)
                .overlay (hello);
    return helloRed.overlayCentered (AImage.makeCircle(2, new Yellow(), Mode.FILLED));
  }
  
  WorldImage circleText = 
      AImage.makeText ("CircleImage(new Posn(200, 60), 10, new Red())", Color.red).moved(200, 20);
  WorldImage circle = AImage.makeCircle(10, new Red(), Mode.OUTLINED).moved(200, 60);
  
  WorldImage diskText = 
      AImage.makeText ("AImage.makeCircle(new Posn(200, 140), 10, new Red(), Mode.FILLED)", Color.red).moved(200, 100);
  WorldImage disk = AImage.makeCircle(10, new Red(), Mode.FILLED).moved(200, 140);

  WorldImage lineText = 
      AImage.makeText ("LineImage(new Posn(200, 220), new Posn(280, 230), Color.green)", Color.green).moved(220,180);
  WorldImage line = 
      AImage.makeLine (new Posn(200, 220), new Posn(280, 230), Color.green);

  WorldImage triangleText = 
      AImage.makeText ("TriangleImage(new Posn(250, 300), new Posn(200, 340), new Posn(150, 310), Color.cyan)", 
      Color.cyan).moved(280, 260);
  WorldImage triangle = 
      AImage.makeTriangle(new Posn(250, 300), new Posn(200, 340), new Posn(150, 310), Color.cyan, Mode.FILLED);
  

  WorldImage ellipseText = 
      AImage.makeText("EllipseImage(new Posn(600, 60), 60, 20, new Blue())", new Blue()).moved(600, 20);
  WorldImage ellipse = AImage.makeEllipse(60, 20, new Blue()).moved(600, 60);
  
  WorldImage frameText = 
      AImage.makeText("FrameImage(new Posn(600, 120), 60, 20, new Black())", new Black()).moved(600, 100);
  WorldImage frame = AImage.makeRectangle(60, 20, new Black()).moved(600, 120);
  
  WorldImage ovalText = 
      AImage.makeText("OvalImage(60, 20, new Yellow())", new Yellow()).moved(600, 180);
  WorldImage oval = AImage.makeEllipse(60, 20, new Yellow(), Mode.FILLED).moved(600, 220);
  
  WorldImage rectangleText = 
      AImage.makeText("RectangleImage(new Posn(600, 330), 60, 20, Color.orange)", Color.orange).moved(600, 300);
  WorldImage rectangle = AImage.makeRectangle(60, 20, Color.orange, Mode.FILLED).moved(600, 330);
  
  WorldImage fromFileText = 
      AImage.makeText("FromFileImage(new Posn(600, 480), Images/fish.png)", Color.black).moved(600, 420);
  WorldImage fish = AImage.makeFromFile("Images/fish.png").moved(600, 480);
  
  WorldImage combined = 
      this.circleText.overlay(
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
    
//    Tester.run (e);
    
  }

}
