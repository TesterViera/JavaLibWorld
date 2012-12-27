package funworldtests;

import java.awt.Color;
import java.util.Random;

import tester.*;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * Not completely ported to new API yet as of Dec 26, 2012.  -- sbloch
 * 
 */


/** Represent the world of a Blob */
public class BlobWorldFun extends World {
    
    int width = 200;
    int height = 300;
    Blob blob;
    
    /** The constructor */
    public BlobWorldFun(Blob blob) {
        super();
        this.blob = blob;
    }
    
    /** Move the Blob when the player presses a key */
    public World onKeyEvent(String ke) {
      if (ke.equals("x"))
        return this.endOfWorld("Goodbye");
      else
        return new BlobWorldFun(this.blob.moveBlob(ke));
    }
    
    /** On tick check whether the Blob is out of bounds,
     * or fell into the black hole in the middle.
     * If all is well, move the Blob in a random direction.
     */
    public World onTick() {
        /*
         // if the blob is outside the canvas, stop
         if (this.blob.outsideBounds(this.width, this.height)){
         return this.endOfWorld("Blob is outside the bounds");
         }
         
         // time ends is the blob falls into the black hole in the middle
         if (this.blob.nearCenter(this.width, this.height) &&
         this.endOfTime("Black hole ate the blob"))
         return this;
         
         // else move the blob randomly at most 5 pixels in any direction
         else
         */
      return new BlobWorldFun(this.blob.randomMove(5));
    }
    
    /**
     * On mouse click move the blob to the mouse location, make the color red.
     */
    public World onMouseClicked(Posn loc){
        return new BlobWorldFun(new Blob(loc, 20, new Red()));
    }
    /**
     * The entire background image for this world
     * It illustrates the use of most of the <code>WorldImage</code> shapes
     */
    WorldImage frame = AImage.makeRectangle (this.width-1, this.height-1, new Blue(), Mode.OUTLINED);
    WorldImage ellipse = AImage.makeEllipse (25, 25, new Green(), Mode.OUTLINED);
    WorldImage disk = AImage.makeCenteredCircle (new Posn(100,150), 10, new Black(), Mode.FILLED);
    WorldImage circ = AImage.makeCenteredCircle (new Posn(100,150), 10, new White(), Mode.OUTLINED);
    WorldImage box = AImage.makeRectangle (10, 10, new White(), Mode.OUTLINED).moved(95, 95);
    WorldImage line = AImage.makeLine (new Posn(95,145), new Posn(105, 155), new Red());
    WorldImage oval = AImage.makeEllipse (25, 25, new Green(), Mode.FILLED).moved(175, 275);
    
    public WorldImage blackHole = 
        frame.overlay(ellipse, disk, circ, box, line, oval);
        /*
        new OverlayImages(new RectangleImage(new Posn(100, 150), 
                                         this.width, this.height, new Blue()),
        new OverlayImages(new EllipseImage(new Posn(12, 12), 25, 25, new Green()),
        new OverlayImages(new DiskImage(new Posn(100, 150), 10, new Black()),
        new OverlayImages(new CircleImage(new Posn(100, 150), 10, new White()),
        new OverlayImages(new RectangleImage(new Posn(100, 150), 10, 10, new White()),
        new OverlayImages(new LineImage(new Posn(95, 145), 
                                                                        new Posn(105, 155), new Red()),
        new OverlayImages(
            new LineImage(new Posn(95, 155), new Posn(105, 145), new Red()),
        new OvalImage(new Posn(187,287), 25, 25, new Green()))))))));
        */
       
    /**
     * produce the image of this world by adding the moving blob 
     * to the background image
     */
    public WorldImage makeImage(){
        return this.blackHole.overlay (this.blob.blobImage()); 
    }
    
  /**
   * produce the image of this world by adding the moving blob 
   * to the background image
   */
  public WorldImage lastImage(String s){
    return this.makeImage().overlayXY (AImage.makeText (s, Color.red), 100, 40);
  }
    
    /**
     * Check whether the Blob is out of bounds,
     * or fell into the black hole in the middle.
     */
  public WorldEnd worldEnds(){
        // if the blob is outside the canvas, stop
        if (this.blob.outsideBounds(this.width, this.height)){
            return 
            new WorldEnd(true,
                this.makeImage().overlayXY (AImage.makeText ("Blob is outside the bounds", Color.red),
                                            100, 40));
        }
        // time ends is the blob falls into the black hole in the middle
        if (this.blob.nearCenter(this.width, this.height)){
            return 
            new WorldEnd(true,
              this.makeImage().overlayXY (AImage.makeText ("Black hole at the blob", 13, TextStyle.BOLD_ITALIC, Color.red),
                                          100, 40));
        }
        else{
            return new WorldEnd(false, this.makeImage());
        }
    }
  
  // support for the regression tests
  public static BlobExamples examplesInstance = new BlobExamples();
}

