package impworldtests;


import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 *
 * Simple example of drawing a text, constructing images from the files
 * and seeing the drawings in the Canvas
 * 
 * @author Viera K. Proulx
 * @since 5 February 2012
 */
public class ExamplesImp  {

  public ExamplesImp() {}
  
  // a text inside a red rectangle with a small black line
  public static WorldImage makeImage(Posn pos){
    return 
    new OverlayImages(new RectangleImage(pos, 60, 20, new Red()),
      new OverlayImages(new TextImage(pos, "hello", 12, 0, new Blue()),
        new LineImage(pos, new Posn(pos.x + 5, pos.y - 5), 
            new Black())));
  }

  public static void main(String[] args) {
    
    WorldCanvas c = new WorldCanvas(600, 600);

    WorldImage pic = ExamplesImp.makeImage(new Posn(300, 100));
    
    WorldImage hello = 
        new OverlayImages(new RectangleImage(new Posn(100, 30), 60, 20, new Red()),
        new TextImage(new Posn(100,30), "hello", 12, 0, new Blue()));

   // show several images in the canvas 
    boolean makeDrawing = 
      c.show() && 
      c.drawImage(new LineImage(new Posn(400, 400),new Posn(600, 600), new Red())) &&
      c.drawImage("Images/green-fish.png", new Posn(100, 100)) &&
      c.drawImage("Images/pink-fish.png", new Posn(200, 250)) &&
      c.drawImage("Images/shark.png", new Posn(350, 400)) &&
      c.drawImage(new DiskImage(new Posn(100, 100), 5, new Black())) &&
      c.drawImage(pic);
   
    
    pic = ExamplesImp.makeImage(new Posn(200, 100));
    boolean makeAnotherDrawing = c.drawImage(pic);
 
    pic.movePinhole(0, 100);
    boolean makeAnotherDrawing2 = c.drawImage(pic); 
    
    WorldImage triangle = new TriangleImage(new Posn(20, 50), 
        new Posn(60, 80), new Posn(40, 90), new Green());
   
    WorldImage blueline = 
        new LineImage(new Posn(200, 300), new Posn(300, 200), new Blue());
    
    WorldCanvas c2 = new WorldCanvas(600, 600);
    boolean makeDrawing3 = 
        c2.show() &&
        c2.drawImage(pic) &&
        c2.drawImage(triangle) &&
        c2.drawImage(blueline);
    
    
    triangle.movePinhole(0,  80);
    blueline.moveTo(new Posn(100, 100));
    boolean drawTriangle2 = c2.drawImage(triangle) &&
        c2.drawImage(blueline);
    
    blueline.movePinhole(50, 70);
    boolean drawMovedline = c2.drawImage(blueline);
  
  }
}