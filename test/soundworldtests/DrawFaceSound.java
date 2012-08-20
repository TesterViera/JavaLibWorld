package soundworldtests;

import javalib.soundworld.*;
import javalib.worldimages.*;
import javalib.colors.*;
import javalib.tunes.*;
import tester.*;

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
class DrawFace extends World{
    
  boolean happy = true;
  
  int happyInstrument = CHOIR;
  int sadInstrument = TUBA;
  Note note = new Note("C4n4");
  Note note2 = new Note("E4n2");
  int tickCount = 0;
  
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
  
  // If this class did not extend the World and did not have the 
  // methods draw, onTick, and onKeyEvent, the following two
  // field definitions would display the image
  
  //Canvas c = new Canvas(100, 100);
  
  //boolean showFace = this.c.show() &&
  //                   this.c.drawImage(this.happyFaceImage());
  
  // Draw this world in the World's Canvas
  public WorldImage makeImage(){
    if (happy)
      return this.happyFaceImage;
    else
      return this.sadFaceImage;
  }
  
  // on every fourth tick change the face from sad to happy and back
  // and play either a happy or a sad tune
  public void onTick(){
    if (this.tickCount == 0){
      this.happy = !this.happy;
      this.tickTunes.addNote(this.instrument(), this.note);
      this.tickTunes.addNote(this.instrument(), this.note2);
    }
    System.out.println("tick count: " + this.tickCount);
    this.tickCount  = (this.tickCount + 1) % 4;    
  }
  
  // change the mood of the face is space bar is pressed,
  // end the world on "x"
  // signal the end on Tick on "q"
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
  
  // end the world if the key event upped the tick count
  public WorldEnd worldEnds(){
    if (this.tickCount > 4)
      return new WorldEnd(true, this.makeImage());
    else
      return new WorldEnd(false, this.makeImage());
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
    TuneBucket nowPlaying = this.dfw.nowPlaying();
    t.checkExpect(nowPlaying.bucketSize(), 0);
    
    this.dfw.testOnTick();
    t.checkExpect(this.dfw.tickCount, 1);
    t.checkExpect(this.dfw.happy, false);
    t.checkExpect(this.dfw.instrument(), this.dfw.sadInstrument);
    nowPlaying = this.dfw.nowPlaying();
    t.checkExpect(nowPlaying.bucketSize(), 1);
    t.checkExpect(nowPlaying.contains(this.dfw.sadInstrument, new Note("A4n3")), true);
    System.out.println("Now playing on tick count " + this.dfw.tickCount + 
                       "\n" + nowPlaying.toIndentedString(" "));
    
        
    this.dfw.testOnTick();
    t.checkExpect(this.dfw.tickCount, 2);
    t.checkExpect(this.dfw.happy, false);
    t.checkExpect(this.dfw.instrument(), this.dfw.sadInstrument);
    nowPlaying = this.dfw.nowPlaying();
    t.checkExpect(nowPlaying.bucketSize(), 1);
    t.checkExpect(nowPlaying.contains(this.dfw.sadInstrument, new Note("A4n2")), true);
    System.out.println("Now playing on tick count " + this.dfw.tickCount + 
        "\n" + nowPlaying.toIndentedString(" "));
          
    this.dfw.testOnTick();
    t.checkExpect(this.dfw.tickCount, 3);
    t.checkExpect(this.dfw.happy, false);
    t.checkExpect(this.dfw.instrument(), this.dfw.sadInstrument);
    nowPlaying = this.dfw.nowPlaying();
    t.checkExpect(nowPlaying.bucketSize(), 1);
    t.checkExpect(nowPlaying.contains(this.dfw.sadInstrument, new Note("A4n1")), true);
    System.out.println("Now playing on tick count " + this.dfw.tickCount + 
        "\n" + nowPlaying.toIndentedString(" "));

    this.dfw.testOnTick();
    t.checkExpect(this.dfw.tickCount, 0);
    t.checkExpect(this.dfw.happy, false);
    t.checkExpect(this.dfw.instrument(), this.dfw.sadInstrument);
    nowPlaying = this.dfw.nowPlaying();
    t.checkExpect(nowPlaying.bucketSize(), 0);
    System.out.println("Now playing on tick count " + this.dfw.tickCount + 
        "\n" + nowPlaying.toIndentedString(" "));
    
    this.dfw.testOnTick();
    t.checkExpect(this.dfw.tickCount, 1);
    t.checkExpect(this.dfw.happy, true);
    t.checkExpect(this.dfw.instrument(), this.dfw.happyInstrument);
    nowPlaying = this.dfw.nowPlaying();
    t.checkExpect(nowPlaying.bucketSize(), 1);
    t.checkExpect(nowPlaying.contains(this.dfw.happyInstrument, new Note("A4n3")), true);
    System.out.println("Now playing on tick count " + this.dfw.tickCount + 
                       "\n" + nowPlaying.toIndentedString(" "));
    
  }
  
  // test the method onKeyEvent
  void testOnKeyEvent(Tester t){
    
    // test space key event once
    resetDrawFace();
    this.dfw.onKeyEvent(" ");
    t.checkExpect(this.dfw, this.dfwSad);
    
    // test ignored key event
    resetDrawFace();
    this.dfw.onKeyEvent("b");
    t.checkExpect(this.dfw, this.dfw);
    
    // test space key event again
    resetDrawFace();
    this.dfwSad.onKeyEvent(" ");
    t.checkExpect(this.dfwSad, this.dfw); 
    
    // test world ending key event
    this.dfw.onKeyEvent("x");
    t.checkExpect(this.dfw.lastWorld, new WorldEnd(true, this.dfw.makeImage()));

    // test signal for world ending key event
    this.dfw.onKeyEvent("q");
    t.checkExpect(this.dfw.tickCount, 5);
    this.dfw.testOnTick();
    t.checkExpect(this.dfw.lastWorld, new WorldEnd(true, this.dfw.makeImage()));   
    
    // we need more tests here ....
  }
  
  // run the tests and run the game
  public static void main(String[] argv){
    ExamplesDrawFace edf = new ExamplesDrawFace();
    Tester.runReport(edf, false, false);
    
    edf.resetDrawFace();
    edf.dfw.bigBang(100, 100, 1.0 );
  }
  
}