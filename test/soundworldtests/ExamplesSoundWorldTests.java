package soundworldtests;

import javalib.soundworld.*;
import javalib.worldimages.*;
import javalib.colors.*;
import javalib.tunes.*;
import tester.*;

import java.awt.Color;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * A simple example of two coexisting sound worlds that use the tune bucket
 * to represent the direction of the movement of the disk.
 * 
 * <p>MousePress moves the disk to its location</p>
 * 
 * @author Viera K. Proulx
 * @since 29 October 2009, 8 August 2012
 */

//-----------------------------------------------------------------------------
// This examples verifies that two worlds can coexist and deal with their
// respective events without a conflict.
public class ExamplesSoundWorldTests extends World{
  Posn pos;
  int COUNTER;

  Player frereplay = MelodySamples.frere();
  Player frereplay2 = MelodySamples.frere();
  Player jeopardyplay = MelodySamples.jeopardy();
  
  ExamplesSoundWorldTests(){
    this(new Posn(50, 50));
  }
  // the world is just one red disk with a black line across the middle
  ExamplesSoundWorldTests(Posn pos){
    super();
    this.pos = pos;
    this.COUNTER = 0;
    this.frereplay2.nextMeasure();
  }

  // draw this world on the given Canvas
  public void draw(){
    this.theCanvas.drawImage(new DiskImage(this.pos, 20, new Red()));
    this.theCanvas.drawImage(new LineImage(this.pos, 
                            new Posn(this.pos.x + 5, this.pos.y - 5), 
                            new Black()));
  }
  
  // produce an image of this world
  public WorldImage makeImage(){
    return 
    new DiskImage(this.pos, 20, new Red()).overlayImages(
    new LineImage(this.pos, new Posn(this.pos.x + 5, this.pos.y - 5), 
                            new Black() ));
  }
  
  // respond to the mouse click by moving the disk to the mouse location
  public void onMousePressed(Posn mouse){
    this.pos = mouse;
  }
  
  // respond to three arrow keys with moving the disk and playing a tune
  // allow the user to stop the world in three ways
  public void onKeyEvent(String ke){
  // print the coordinates of the disk is now  
    //System.out.println("Key event " + ke);
    
    // move right on "right" key and play a high G note
    if (ke.equals("right")){
      this.keyTunes.addNote(ORGAN, "G5n1");
      this.pos.x = this.pos.x + 20;
    }
 
    // move left on "left" key and play a middle G note
    if (ke.equals("left")){
      this.keyTunes.addNote(ORGAN, 72);
      this.pos.x = this.pos.x - 20;
    }
    
    // on "up" key
    // move the disk back to the start and play the middle C note
    if (ke.equals("up")){
      this.keyTunes.addNote(ORGAN, "C4n2");
      this.pos.x = 0;
    }
    
    // end the world if "e" is pressed
    if (ke.equals("e"))
      this.endOfWorld("End of the World!!");
    
    // stop the world if "w" is pressed
    if (ke.equals("w"))
      this.endOfWorld("Stop of the World...");
    
    // kill the program if "q" is pressed
    if (ke.equals("q"))
      throw new RuntimeException("We quit!!!");
    
    // turn the middle line of the keyboard into a piano keyboard
    // play the middle C note
    if (ke.equals("a"))
      this.keyTunes.addNote(ORGAN, "C4n2");
    
    // play the middle D note
    if (ke.equals("s"))
      this.keyTunes.addNote(ORGAN, "D4n2");
    
    // play the middle E note
    if (ke.equals("d"))
      this.keyTunes.addNote(ORGAN, "E4n2");
    
    // play the middle F note
    if (ke.equals("f"))
      this.keyTunes.addNote(ORGAN, "F4n2");
    
    // play the middle G note
    if (ke.equals("g"))
      this.keyTunes.addNote(ORGAN, "G4n2");
    
    // play the middle H note
    if (ke.equals("h"))
      this.keyTunes.addNote(ORGAN, "A5n2");
    
    // play the middle J note
    if (ke.equals("j"))
      this.keyTunes.addNote(ORGAN, "B5n2");
    
    // play the middle K note
    if (ke.equals("k"))
      this.keyTunes.addNote(ORGAN, "C5n2");

    
    // turn the bottom line of the keyboard into a piano keyboard, one octave up
    // play the middle C note
    if (ke.equals("z"))
      this.keyTunes.addNote(ORGAN, "C5n2");
    
    // play the middle D note
    if (ke.equals("x"))
      this.keyTunes.addNote(ORGAN, "D5n2");
    
    // play the middle E note
    if (ke.equals("c"))
      this.keyTunes.addNote(ORGAN, "E5n2");
    
    // play the middle F note
    if (ke.equals("v"))
      this.keyTunes.addNote(ORGAN, "F5n2");
    
    // play the middle G note
    if (ke.equals("b"))
      this.keyTunes.addNote(ORGAN, "G5n2");
    
    // play the middle H note
    if (ke.equals("n"))
      this.keyTunes.addNote(ORGAN, "A6n2");
    
    // play the middle J note
    if (ke.equals("m"))
      this.keyTunes.addNote(ORGAN, "B6n2");
    
    // play the middle K note
    if (ke.equals(","))
      this.keyTunes.addNote(ORGAN, new Note(84, 2));
  }
 
  // move right on each tick up to 100 pixels, then jump back
  // play a middle D note on a tuba instrument
  public void onTick(){
  // show the current disk position
    // System.out.println("Tick -- pos = (" + pos.x + ", " + pos.y + ")");
    
    // test that the initial world is shown before the timer starts ticking
    //if (this.pos.x == 50)
    //  this.pos.y = 20;
    
    this.pos.x = (this.pos.x + 2) % 200;
    // this.tickTunes.add(TUBA, noteD);
    this.COUNTER = this.COUNTER + 1;
   // this.tickTunes.add(PIANO, this.jeopardyplay.next());
    
    this.tickTunes.addNote(CHOIR, this.frereplay.next());
    this.tickTunes.addNote(CHOIR, this.frereplay2.next());
    
    //this.tickTunes.add(PIANO, this.sheetplay(frere, -1, 0, 0));
    //this.tickTunes.add(CHOIR, this.sheetplay(frere, -1, 0, 8));
    //this.tickTunes.add(PIANO, this.sheetplay(frere, -1, 5, 0));
  }
  

  //return an instrument from the given sheetmusic, 
  // the quiet MIDI bass note, and the octave-transposition
  public int sheetplay(int[] sheetmusicar, int base, int scaling, int offset){
    //if the counter has reached the end of the sheetmusic array
    //if(this.COUNTER >= sheetmusicar.length){          
    // this no longer happens
    
    //if the counter is negative - not expected to happen
    if(this.COUNTER < 0){          
          return base;                   //return the silent note
          
    //if the note is 0
    }if(sheetmusicar[(this.COUNTER + offset) % sheetmusicar.length] == 0){
      return base;                           //return the silent note
      
      }else{                                 //else
        //return the note with the appropriate transposition
        return sheetmusicar[(this.COUNTER + offset) % sheetmusicar.length] 
               + scaling;   
      }
    }
  
  //to represent the NOTES:
  //each instrument has an array of 2048 notes.
  
  public int synthhigh[] = {
      noteAp,0,0,0,noteAp,0,0,0,
      noteA,0,0,0,noteA,0,0,0,
      noteG,0,0,0,0,0,noteG,0,
      0,0,noteG,0,0,0,noteG,0,
      
      noteAp,0,0,0,noteAp,0,0,0,
      noteA,0,0,0,noteA,0,0,0,
      noteG,0,0,0,0,0,noteG,0,
      0,0,noteG,0,0,0,noteG,0,
  };  
  
  //to represent the NOTES:
  //Frere Jacques
  
  public int frere[] = {
      0,0,0,0,
      noteC,0,0,0,noteD,0,0,0,noteE,0,0,0,noteC,0,0,0,
      noteC,0,0,0,noteD,0,0,0,noteE,0,0,0,noteC,0,0,0,
      noteE,0,0,0,noteF,0,0,0,noteG,0,0,0,0,0,0,
      noteE,0,0,0,noteF,0,0,0,noteG,0,0,0,0,0,0,
      noteG,0,noteA,0,noteG,0,noteF,0,noteE,0,0,noteC,0,0,0,
      noteG,0,noteA,0,noteG,0,noteF,0,noteE,0,0,noteC,0,0,0,
      noteC,0,0,0,noteDownG,0,0,0,noteC,0,0,0,0,0,0,
      noteC,0,0,0,noteDownG,0,0,0,noteC,0,0,0,0,0,0,
      0,0,0,0,
  };

  // create two worlds running at a different speed and show the animation
  public static void main(String[] argv){
    ExamplesSoundWorldTests ew = new ExamplesSoundWorldTests(new Posn(50, 60));
    ExamplesSoundWorldTests ew2 = new ExamplesSoundWorldTests(new Posn(50, 20));
    
    /*
     * There is a mismatch here with onTick() method.
     * When invoked in a testing mode, it only adds to the 
     * TuneBucket what we have said to add.
     * When processed in a running program, the 
     * processTick method first deals with the current
     * tickTune bucket - stopping notes that finished
     * playing and decreasing the duration of all others,
     * then it invokes onTick to add new tunes to the
     * bucket and plays them.
     * 
     * THINK CAREFULLY ABOUT HOW TO TEST THIS !!!
     *
     
    //MusicBox testBox = new MusicBox();
    //ew.tickTunes = new TuneBucket(testBox);
    
    
    ew.onTick();
    System.out.println("1: " + ew.tickTunes.toString());
    System.out.println("1: " + ew2.tickTunes.toString());
    /*
    ew.onTick();
    System.out.println("2: " + ew.tickTunes.toString());
    
    ew.onTick();
    System.out.println("3: " + ew.tickTunes.toString());
    ew.tickTunes.clearBucket();
    */
    
    ew.bigBang(200, 300, 0.1);
    ew2.bigBang(400, 200, 0.5);
  }

}

