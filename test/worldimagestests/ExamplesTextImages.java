package worldimagestests;

import javalib.colors.*;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;
import tester.*;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * Simple example of drawing a text, constructing images from the files
 * and seeing the drawings in the Canvas
 * 
 * @author Viera K. Proulx
 * @since 5 February 2012
 */
public class ExamplesTextImages  {

  public ExamplesTextImages() {}

  // support for the regression tests
  public static ExamplesTextImages examplesInstance = 
      new ExamplesTextImages();

  // a text inside a red rectangle with a small black line
  public static WorldImage makeImage(Posn pos, int size){
    WorldImage hello = 
      new TextImage(pos, "quickbrownfoxjumedoveralazydog", size, 0, new Blue());

    WorldImage helloRed = 
        new OverlayImages(
            new RectangleImage(pos, 
                hello.getWidth(), hello.getHeight(), 
                new Red()),
                hello);
    return 
        new OverlayImages(helloRed,
            new LineImage(pos, new Posn(pos.x + 5, pos.y - 5), new Black()));
  }
  
  public void testAll(Tester t){
    String[] args = new String[]{};
    ExamplesTextImages.main(args);
  }

  public static void main(String[] args) {

    WorldCanvas c = new WorldCanvas(600, 600);

    WorldImage pic = ExamplesTextImages.makeImage(new Posn(300, 100), 12);

    // show several images in the canvas 
    boolean makeDrawing = 
        c.show() && 
        c.drawImage(new LineImage(new Posn(400, 400), new Posn(600, 600), 
            new Red())) &&
        c.drawImage(new FromFileImage(new Posn(100, 100), 
        		"Images/green-fish.png")) &&
        c.drawImage(new FromFileImage(new Posn(200, 250),
        		"Images/pink-fish.png")) &&
        c.drawImage(new FromFileImage(new Posn(350, 400),
        		"Images/shark.png")) &&
        c.drawImage(new DiskImage(new Posn(100, 100), 5, new Black())) &&
        c.drawImage(pic);


    pic = ExamplesTextImages.makeImage(new Posn(200, 100), 12);

    boolean makeAnotherDrawing = c.drawImage(pic);

    pic = pic.getMovedImage(0, 100);
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
        c2.drawImage(blueline) &&
        c2.drawImage(triangle.getMovedImage(20,30)) &&
        c2.drawImage(blueline.getMovedImage(20,30));



    WorldImage newTriangle = triangle.getMovedImage(0,  80);
    WorldImage newBlueline = blueline.getMovedTo(new Posn(100, 100));

    boolean drawTriangle2 = c2.drawImage(triangle) &&
        c2.drawImage(blueline);

    boolean drawTriangle3 = c2.drawImage(newTriangle) &&
        c2.drawImage(newBlueline);

    blueline.getMovedImage(50, 70);
    boolean drawMovedline = c2.drawImage(blueline);

    WorldCanvas c3 = new WorldCanvas(600, 600);

    WorldImage pic2 = ExamplesTextImages.makeImage(new Posn(300, 100), 20);

    WorldImage triangleX = new TriangleImage(new Posn(20, 50), 
        new Posn(60, 80), new Posn(40, 90), new Green());

    WorldImage frameTriangle = 
        new FrameImage(triangleX.pinhole, 
            triangleX.getWidth(), triangleX.getHeight(),
            new Black());

    boolean makeDrawing4 = 
        c3.show() &&
        c3.drawImage(pic2) &&
        c3.drawImage(triangleX) &&
        c3.drawImage(frameTriangle);


  }
}