package soundworldtests;
/**
 * Copyright 2009 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * A sample code to illustrate the use of the imperative sound world library
 * It uses an array of 25 tunes so that it can play a continuous melody.
 * The key events 'chime in' with additional sounds, including the option
 * of silencing the key event generated sound.
 * 
 * The tickTunes TuneBucket notes play until the next tick.
 * The keyTunes TuneBucket notes play until the next key event.
 * 
 * HAPPY BIRTHDAY MATTHIAS.
 * 
 * @author Viera K. Proulx
 * @since 29 October 2009
 */

import javalib.soundworld.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.colors.*;
import javalib.tunes.*;
import tester.*;

// to represent a balloon on a canvas with location, size, color, and text
class Balloon{
	Posn loc;
	int rad;
	IColor col;
	String s;
	WorldImage balloonImage;
	
	Balloon(Posn loc, int rad, IColor col, String s){
		this.loc = loc;
		this.rad = rad;
		this.col = col;
		this.s = s;
		this.balloonImage = 
		    new DiskImage(this.loc, this.rad, this.col).overlayImages(
		    new LineImage(this.loc, new Posn(this.loc.x, this.loc.y + 2 * this.rad), 
            this.col),
        new TextImage(new Posn(this.loc.x, this.loc.y + this.rad / 2), 
          s, new Black()));
	}
	
	// EFFECT:
	// draw this balloon on the given Canvas
	void drawBalloon(WorldCanvas c){
		c.drawImage(new DiskImage(this.loc, this.rad, this.col));
		c.drawImage(new LineImage(this.loc, 
		    new Posn(this.loc.x, this.loc.y + 2 * this.rad), this.col));
		c.drawImage(new TextImage(new Posn(this.loc.x - this.rad / 2, 
		                                   this.loc.y + this.rad / 2), s, new Black()));		
	}
}

// to represent a list of balloons
interface ILoB{
	
	// EFFECT:
	// draw each balloon in this list on the given canvas
	public void draw(WorldCanvas c);
	
	// produce the image of this list of balloons
	public WorldImage makeImage();
}

// to represent an empty list of balloons
class MtLoB implements ILoB{
	MtLoB(){}
	
	// EFFECT:
	// draw each balloon in this empty list on the given canvas
	public void draw(WorldCanvas c){}
  
  // produce the image of this list of balloons
  public WorldImage makeImage(){
    return new RectangleImage(new Posn(0, 0), 1, 1, new White());
  }
}

// to represent a nonempty list of balloons
class ConsLoB implements ILoB{
	Balloon first;
	ILoB rest;
	
	ConsLoB(Balloon first, ILoB rest){
		this.first = first;
		this.rest = rest;
	}
	
	// EFFECT:
	// draw each balloon in this nonempty list on the given canvas
	public void draw(WorldCanvas c){
		this.first.drawBalloon(c);
		this.rest.draw(c);
	}	
  
  // produce the image of this list of balloons
  public WorldImage makeImage(){
    return this.first.balloonImage.overlayImages(this.rest.makeImage());
  }
}



//-----------------------------------------------------------------------------
// The world of sound
class SoundWorld extends World{
  ILoB bloons = new MtLoB();
  int tick = 5;
  
  //Integer[] scale = 
  //  new Integer[]{noteC, noteD, noteE, noteF, noteG, noteA, noteB, noteUpC};
  
  // A melody of 25 notes to play in a continuous loop
  Integer[] melody = //happy birthday
	  new Integer[]{noteG, noteG, noteA, noteG, noteUpC, noteB, 
		            noteG, noteG, noteA, noteG, noteUpD, noteUpC,
		            noteG, noteG, noteUpG, noteUpE, noteUpC, noteB, noteA,
		            noteUpF, noteUpF, noteUpE, noteUpC, noteUpD, noteUpC};
  
  // The message to display on the balloons
  String[] hbm = 
	  new String[]{"Hap", "py", "Birth", "day", 
		  "to", "you",
		  "Hap", "py", "Birth", "day", 
		  "to", "you",
		  "Hap", "py", "Birth", "day", 
		  "Dear", "Matt", "hias",
		  "Hap", "py", "Birth", "day", 
		  "to", "you"	  
  };
 
  // The locations of the centers of the balloons
  Posn[] posnsOld = 
	  new Posn[]{new Posn(25, 25), new Posn(75, 25), new Posn(125, 25), new Posn(175, 25),
			  new Posn(50, 50), new Posn(150, 50),
			  new Posn(25, 75), new Posn(75, 75), new Posn(125, 75), new Posn(175, 75),
			  new Posn(50, 100), new Posn(150, 100),
			  new Posn(25, 125), new Posn(75, 125), new Posn(125, 125), new Posn(175, 125),
			  new Posn(50, 150), new Posn(100, 150), new Posn(150, 150),
			  new Posn(25, 175), new Posn(55, 175), new Posn(85, 175), new Posn(125, 175),
			  new Posn(155, 175), new Posn(185, 175)	  
  };
  
  // The locations of the centers of the balloons
  Posn[] posns = 
	  new Posn[]{new Posn(50, 50), new Posn(150, 50), new Posn(250 ,50), new Posn(350, 50),
			  new Posn(100, 100), new Posn(300, 100),
			  new Posn(50, 150), new Posn(150, 150), new Posn(250, 150), new Posn(350, 150),
			  new Posn(100, 200), new Posn(300, 200),
			  new Posn(50, 250), new Posn(150, 250), new Posn(250, 250), new Posn(350, 250),
			  new Posn(100, 300), new Posn(200, 300), new Posn(300, 300),
			  new Posn(50, 350), new Posn(110, 350), new Posn(170, 350), new Posn(250, 350),
			  new Posn(310, 350), new Posn(370, 350)	  
  };
  
  
  // The colors of the balloons
  IColor[] colors = 
	  new IColor[]{new Red(), new Green(), new Blue(), new Yellow(), new Red(), new Green(),
		  new Blue(), new Yellow(), new Red(), new Green(), new Blue(), new Yellow(),
		  new Red(), new Green(), new Blue(), new Yellow(), new Red(), new Green(), new Blue(),
		  new Blue(), new Yellow(), new Green(), new Red(), new Yellow(), new Green()
  };
  
  // The radii of the balloons (if the text is longer, the balloon radius increases)
  Integer[] radsold = 
	  new Integer[]{ 20, 20, 23, 20, 20, 20, 
		  20, 20, 23, 20, 20, 20,
		  20, 20, 23, 20, 25, 25, 25,
		  20, 20, 23, 20, 20, 20
  };
  
  // The radii of the balloons (if the text is longer, the balloon radius increases)
  Integer[] rads = 
	  new Integer[]{ 40, 40, 43, 40, 40, 40, 
		  40, 40, 43, 40, 40, 40,
		  40, 40, 43, 40, 50, 50, 50,
		  40, 40, 43, 40, 40, 40
  };
  
  // The constructor for the world 
  SoundWorld(int tick, ILoB bloons){
	  this.bloons = bloons;
	  this.tick = tick;
  }

  // EFFECT:
  // draw this world by drawing the current list of balloons
  public void draw(){
	  this.bloons.draw(this.theCanvas);
  }
  
  public WorldImage makeImage(){
    return this.bloons.makeImage();
  }

  // EFFECT:
  // a couple of special effects triggered by the key events:
  // space bar generates a 'shhh' sound
  // up arrow adds a tweeting bird sound
  // 'q' stops the annoying tweeting bird
  // 's' causes the end of time with a final bird tweet
  // 'x' causes the world to stop with a final bagpipe tune
  public void onKeyEvent(String ke){
    if (ke.equals(" ")){
      this.keyTunes.addNote(APPLAUSE, noteC);
    }
    if (ke.equals("up")){
      this.keyTunes.addNote(BIRD_TWEET, noteUpC);
      this.keyTunes.addNote(BAGPIPE, new Note("C4n4"));
      this.keyTunes.addNote(BAGPIPE, new Note("E4n4"));
      this.keyTunes.addNote(BAGPIPE, new Note("G4n4"));
    }
    if (ke.equals("s")){
      this.tickTunes.addNote(BIRD_TWEET, noteUpC);
      this.endOfWorld("Stop of the World...");
    }     
    if (ke.equals("q")){
      this.keyTunes.clearBucket();
    }     
    if (ke.equals("x")){
      this.keyTunes.addNote(BAGPIPE, new Note("C4n4"));
      this.keyTunes.addNote(BAGPIPE, new Note("E4n4"));
      this.keyTunes.addNote(BAGPIPE, new Note("G4n4"));
      this.endOfWorld("We quit!!!");
    }
  }
  
  // EFFECT:
  // at each tick add a new balloon and play the next tune in the melody roll
  // after all 25 balloons have been displayed, start all over again
  // play an extra tune at the end of each roll
  public void onTick(){
	// add a balloon on each tick; reset when done
	if (this.tick == 0)
		this.bloons = 
	    	new ConsLoB(new Balloon(posns[this.tick], rads[this.tick], colors[this.tick], hbm[this.tick]), 
	    				new MtLoB());
	else
		this.bloons = 
			new ConsLoB(new Balloon(posns[this.tick], rads[this.tick], colors[this.tick], hbm[this.tick]), 
				    	this.bloons);
	// play the tune
    //this.tickTunes.add(ORGAN, this.melody[this.tick]);
    this.tickTunes.addNote(ORGAN, this.melody[this.tick]);

    // play the drum roll
	if (this.tick == 24)
    	this.tickTunes.addNote(WOOD_BLOCK, noteC);
	
    // update the tick count
    this.tick = (this.tick + 25 + 1) % 25;
    
      }

  // start the world
  public static void main(String[] argv){
    SoundWorldExamples swe = new SoundWorldExamples();
    Tester.runReport(swe, false, false);
    
    SoundWorld sw = 
    	new SoundWorld(0, new MtLoB());
    
    sw.bigBang(400, 400, 0.5);
  }
}

// a proper program should be tested
class SoundWorldExamples implements SoundConstants{
	SoundWorldExamples(){}
	
	// sample instances of baloons
	Balloon blueBloon = new Balloon(new Posn (20, 40), 10, new Blue(), "blue");
	Balloon redBloon = new Balloon(new Posn (60, 40), 15, new Red(), "red");
	Balloon greenBloon = new Balloon(new Posn (60, 80), 20, new Green(), "green");
	
	// sample lists of baloons
	ILoB mtlob = new MtLoB();
	ILoB threeBloons = 
		new ConsLoB(this.blueBloon,
				new ConsLoB(this.redBloon,
						new ConsLoB(this.greenBloon, this.mtlob)));
	
			
	// a canvas for a visual test for drawing baloons
	WorldCanvas c1 = new WorldCanvas(100, 200);
	
	// a visual test of drawing the balloons
	void testBalloonDraw(Tester t){
		this.c1.show();
		this.blueBloon.drawBalloon(c1);
		this.redBloon.drawBalloon(c1);
		this.greenBloon.drawBalloon(c1);
		t.checkExpect(true, "drawing of single baloons completed");
	}
	
	// a canvas for a visual test for drawing baloons
	WorldCanvas c2 = new WorldCanvas(100, 200);
	
	// a visual test of drawing the balloons
	void testBalloonListDraw(Tester t){
		this.c2.show();
		this.threeBloons.draw(c2);
		t.checkExpect(true, "drawing of balloon list completed");
	}
	
	// tests for the method onTick for the class SoundWorld
	void testOnTick(Tester t){
		// set up the world at the tick == 0
		SoundWorld initWorld = new SoundWorld(0, mtlob);
		initWorld.onTick();
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 1, "tick count check");
		t.checkExpect(initWorld.bloons, 
				new ConsLoB(new Balloon(new Posn(50, 50), 40, new Red(), "Hap"),
						    this.mtlob));
		t.checkExpect(initWorld.tickTunes.bucketSize(), 1);
		t.checkExpect(initWorld.tickTunes.contains(ORGAN, noteG));
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		
		// one more tick and test:
		initWorld.onTick();
		
		// check the bloons list
		t.checkExpect(initWorld.tick, 2, "tick count check");
		t.checkExpect(initWorld.bloons, 
				new ConsLoB(new Balloon(new Posn(150, 50), 40, new Green(), "py"),
					new ConsLoB(new Balloon(new Posn(50, 50), 40, new Red(), "Hap"),
						this.mtlob)));
		
		// check the tune played: the tune bucket is emptied only on tick event, not
		// when the onTick method is invoked -- so after two ticks the bucket has
		// two tunes (here they are the same)
		t.checkExpect(initWorld.tickTunes.bucketSize(), 2);
		t.checkExpect(initWorld.tickTunes.contains(ORGAN, noteG), true, "organ note G");
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		

		// one more tick and test:
		initWorld.onTick();
		
		// check the bloons list
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, 
				new ConsLoB(new Balloon(new Posn(250, 50), 43, new Blue(), "Birth"),
						new ConsLoB(new Balloon(new Posn(150, 50), 40, new Green(), "py"),
								new ConsLoB(new Balloon(new Posn(50, 50), 40, new Red(), "Hap"),
										this.mtlob))));
		
		// check the tune played: the tune bucket is emptied only on tick event, not
		// when the onTick method is invoked -- so after two ticks the bucket has
		// three tunes (here they are the same)
		t.checkExpect(initWorld.tickTunes.bucketSize(), 3);
		t.checkExpect(initWorld.tickTunes.contains(ORGAN, noteG), true, "organ note G");
		t.checkExpect(initWorld.tickTunes.contains(ORGAN, noteA), true, "organ note A");
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		
		// set up for the special case when tick == 24
		initWorld = new SoundWorld(24, threeBloons);

		// one more tick and test:
		initWorld.onTick();

		t.checkExpect(initWorld.tick, 0, "tick count check");
		t.checkExpect(initWorld.bloons, 
				new ConsLoB(new Balloon(new Posn(370, 350), 40, new Green(), "you"),
						    this.threeBloons));
		t.checkExpect(initWorld.tickTunes.bucketSize(), 2);
		t.checkExpect(initWorld.tickTunes.contains(ORGAN, noteUpC), true, "organ note");
		t.checkExpect(initWorld.tickTunes.contains(WOOD_BLOCK, noteC), true, "wood block note");
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		
	}
	
	// tests for the method onKeyEvent for the class SoundWorld
	void testOnKeyEvent(Tester t){
		// set up the world at the tick == 3
		SoundWorld initWorld = new SoundWorld(3, this.threeBloons);
		
		// test space bar key event:
		initWorld.onKeyEvent(" ");
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, this.threeBloons);
		t.checkExpect(initWorld.keyTunes.bucketSize(), 1);
		t.checkExpect(initWorld.keyTunes.contains(APPLAUSE, noteC), true);
		t.checkExpect(initWorld.tickTunes.bucketSize(), 0);

		initWorld.onKeyEvent("q");
		
		// test 'up' key event:
		initWorld.onKeyEvent("up");
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, this.threeBloons);
		t.checkExpect(initWorld.keyTunes.bucketSize(), 4);
		t.checkExpect(initWorld.keyTunes.contains(APPLAUSE, noteC), false);
		t.checkExpect(initWorld.keyTunes.contains(BIRD_TWEET, noteUpC), true);
		t.checkExpect(initWorld.tickTunes.bucketSize(), 0);
    
		// test 'z' key event (nothing should happen here):
		initWorld.onKeyEvent("z");
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, this.threeBloons);
		t.checkExpect(initWorld.keyTunes.bucketSize(), 4);
		t.checkExpect(initWorld.keyTunes.contains(APPLAUSE, noteC), true);
		t.checkExpect(initWorld.keyTunes.contains(BIRD_TWEET, noteUpC), true);
		t.checkExpect(initWorld.tickTunes.bucketSize(), 0);

    initWorld.onKeyEvent("q");
    
		// test 's' key event:
		initWorld.onKeyEvent("s");
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, this.threeBloons);
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		t.checkExpect(initWorld.tickTunes.bucketSize(), 0);

		// test 'q' key event:
		initWorld.onKeyEvent("q");
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, this.threeBloons);
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		t.checkExpect(initWorld.tickTunes.bucketSize(), 0);
		

		// test 'x' key event:
		initWorld.onKeyEvent("x");
		
		// check the bloons list and the tune played
		t.checkExpect(initWorld.tick, 3, "tick count check");
		t.checkExpect(initWorld.bloons, this.threeBloons);
		
		// this cannot be tested as the endOfWorld method plays the tunes,
		// but then empties the keyTunes tune bucket
		// t.checkExpect(initWorld.keyTunes.bucketSize(), 3);
		// t.checkExpect(initWorld.keyTunes.contains(BAGPIPE, noteDownC));
		// t.checkExpect(initWorld.keyTunes.contains(BAGPIPE, noteDownE));
		// t.checkExpect(initWorld.keyTunes.contains(BAGPIPE, noteDownF));
		
		t.checkExpect(initWorld.keyTunes.bucketSize(), 0);
		t.checkExpect(initWorld.tickTunes.bucketSize(), 0);
		

	}
		
		 
}