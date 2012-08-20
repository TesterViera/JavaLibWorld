package appletsoundworldtests;
// DrawFace.java  
//
//  Created by Viera Proulx on 4/2/2012.
//  Copyright 2012 Northeastern University. All rights reserved.

import javalib.appletsoundworld.*;
import tester.*;
import javalib.colors.*;
import javalib.worldimages.*;
import javalib.tunes.*;


/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * <p>DrawFaceSound.java</p>
 * 
 * <p>An example of the use of the drawing methods in the 'worldimages' library,
 * augmented to illustrate the behavior of the 'soundworld':</p>
 * 
 * <p>On tick the face changes every four ticks from happy to sad, 
 * one note plays for four ticks, another plays for the first two ticks only
 * the instrument changes from happy to sad (piano to tube)
 * 
 * <p>key events can play bird-tweet on "up" key,  change state with space bar,
 * force end of the world on "x", induce tick-controlled world ending through 
 * "q" key (by setting the condition that is evaluated on each tick)</p> 
 * 
 * @author Viera K. Proulx
 * @since 8 August 2012
 */
public class DrawFaceSoundApplet extends WorldApplet{

  /** Produce a new BlobWorld */
  public World getNewWorld(){    
    return new DrawFace(true);
  }

  /** Set the size of this world */
  public void setWorldSize(){
    this.WIDTH = 100;
    this.HEIGHT = 100;
  }
}

// an example of the use of the drawing methods in the 'draw' library
class DrawFace extends World{
    
  boolean happy = true;
  
  int happyInstrument = CHOIR;
  int sadInstrument = TUBA;
  Note note = new Note("C4n4");
  Note note2 = new Note("E4n2");
  int tickCount;
  
  
  DrawFace(boolean happy){
    super();
    this.happy = happy;
    this.tickCount = 0;
  }

  // play a happy or sad tune
  int instrument(){
    if (happy)
      return this.happyInstrument;
    else
      return this.sadInstrument;
  }
  // produce the image of a left blue eye with a white pupil
  WorldImage leftEyeImage =
    new DiskImage(new Posn(35, 35), 5, new Blue()).overlayImages(
    new DiskImage(new Posn(37, 35), 2, new White()));
  
  // produce the image of a right blue eye with a white pupil 
  WorldImage rightEyeImage =
    new DiskImage(new Posn(65, 35), 5, new Blue()).overlayImages(
    new DiskImage(new Posn(67, 35), 2, new White()));
  
  // produce an image of a happy red mouth at location 50, 60
  // assume it will be overlaid with the image of the face 
  // that is a yellow disk of radius 40
  WorldImage happyMouthImage =
    new DiskImage(new Posn(50, 60), 20, new Red()).overlayImages(
    new DiskImage(new Posn(50, 50), 20, new Yellow()));
  
  // produce an image of a sad red mouth at location 50, 60
  // assume it will be overlaid with the image of the face 
  // that is a yellow disk of radius 40
  WorldImage sadMouthImage =
    new DiskImage(new Posn(50, 60), 20, new Red()).overlayImages(
    new DiskImage(new Posn(50, 70), 20, new Yellow()));
 
  // produce an image of the nose as black triangle
  WorldImage noseImage = 
    new TriangleImage(new Posn(50, 40), 
                      new Posn(40, 60), 
                      new Posn(50, 60), new Black());
  
  
  // produce the image of a happy face on a green background
  WorldImage happyFaceImage =
    new RectangleImage(new Posn(50, 50), 100, 100, new Green()).overlayImages(
    new DiskImage(new Posn(50, 50), 40, new Yellow()),
    this.happyMouthImage,
    this.noseImage,
    this.leftEyeImage,
    this.rightEyeImage);

  
  // draw the face on a green background on the given Canvas
  WorldImage sadFaceImage =
    new RectangleImage(new Posn(50, 50), 100, 100, new Green()).overlayImages(
    new DiskImage(new Posn(50, 50), 40, new Yellow()),
    this.sadMouthImage,
    this.noseImage,
    this.leftEyeImage,
    this.rightEyeImage);
  
  // Draw this world in the World's Canvas
  public WorldImage makeImage(){
    if (happy)
      return this.happyFaceImage;
    else
      return this.sadFaceImage;
  }
  
  // The last image to draw - with the provided message
  public WorldImage lastImage(String s){
    return
    this.makeImage().overlayImages(
        new TextImage(new Posn(150, 80), s, 
            15, 3, new Red()));
  }
  
  // on every fourth tick change the face from sad to happy and back
  // and play either a happy or a sad tune
  public void onTick(){
    if (tickCount == 0){
      this.happy = !this.happy;
      this.tickTunes.addNote(this.instrument(), this.note);
      this.tickTunes.addNote(this.instrument(), this.note2);
    }
    else
      tickCount = (tickCount + 1) % 4;
  }
  
  // change the mood of the face is space bar is pressed,
  // ignore the key press otherwise
  public void onKeyEvent(String ke){
    if (ke.equals(" "))
      this.happy = !this.happy;
    
    if (ke.equals("x"))
      this.endOfWorld("Have a nice Day!");

    if (ke.equals("q"))
      this.tickCount = 5;
    
    if (ke.equals("up"))
      this.keyTunes.addNote(BIRD_TWEET, new Note("G2n4"));
  }
}

class ExamplesDrawFace{
  ExamplesDrawFace(){}
  
  // two sample worlds
  DrawFace dfw = new DrawFace(true);
  DrawFace dfwSad = new DrawFace(false);
  
  void resetDrawFace(){
    this.dfw = new DrawFace(true);
    this.dfwSad = new DrawFace(false);
  }
  
  // test the method onTick
  void testOnTick(Tester t){
    resetDrawFace();
    this.dfw.onTick();
    t.checkExpect(this.dfw, this.dfwSad);
    resetDrawFace();
    this.dfwSad.onTick();
    t.checkExpect(this.dfwSad, this.dfw);    
  }
  
  // test the method onKeyEvent
  void testOnKeyEvent(Tester t){
    resetDrawFace();
    this.dfw.onKeyEvent(" ");
    t.checkExpect(this.dfw, this.dfwSad);
    resetDrawFace();
    this.dfw.onKeyEvent("b");
    t.checkExpect(this.dfw, this.dfw);
    resetDrawFace();
    this.dfwSad.onKeyEvent(" ");
    t.checkExpect(this.dfwSad, this.dfw);  
    
    // needs more tests...
  }
  
  // add a useless test and start the world
  void testDrawFace(Tester t){
    resetDrawFace();
    t.checkExpect(this.dfw.happy, true);
    // this.dfw = new DrawFace(true);
    this.dfw.bigBang(100, 100, 1.0 );
  }
  
}