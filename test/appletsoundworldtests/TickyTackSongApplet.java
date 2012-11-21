package appletsoundworldtests;

import java.awt.Color;
import java.util.Iterator;

import javalib.appletsoundworld.World;
import javalib.appletsoundworld.WorldApplet;
import javalib.colors.Red;
import javalib.tunes.Note;
import javalib.tunes.SoundConstants;
import javalib.tunes.TuneBucket;
import javalib.worldimages.CircleImage;
import javalib.worldimages.DiskImage;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.LineImage;
import javalib.worldimages.OvalImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.TriangleImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;
import tester.Tester;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @author Viera K. Proulx, Virag Shah
 * @since 8 August 2012, 20 November 2012
 */

public class TickyTackSongApplet extends WorldApplet{

	/** Produce a new MarionWorld */
	public World getNewWorld(){   
		Cloud cloud = new Cloud(new Posn(200, 100), 90, 60);  
		return new TickyTackSong(cloud, new Sun(25));
	}

	/** Set the size of this world */
	public void setWorldSize(){
		this.WIDTH = 600;
		this.HEIGHT = 300;
	}

	/** Set the speed to be twice the normal, so the music plays better */
	public double setSpeedFactor(){
		return 2.0;
	}
}

//A class to display one of the ticky-tacky houses on the hillside.
class House{
	Posn loc;    // of the SW corner of the house base
	int width;   // the width of the house
	int height;  // the height of the house
	Color color; // the color of the house
	Person person;

	House(Posn loc, int width, int height, Color color){
		this.loc = loc;
		this.width = width;
		this.height = height;
		this.color = color;
		this.person = 
				new Person(new Posn(this.loc.x + this.width / 2,
						this.loc.y - this.height / 4), this.width / 2, this.height / 2,
						this.color);
	}

	// make the image of this house
	WorldImage houseImage(){
		return 
				new RectangleImage(
						// the pinhole in the center
						new Posn(this.loc.x + this.width / 2, this.loc.y - this.height / 2), 
						this.width, this.height, this.color).
						overlayImages(
								// the roof - the height is half the height of the house
								new TriangleImage(new Posn(this.loc.x, this.loc.y - this.height), 
										new Posn(this.loc.x + this.width, this.loc.y - this.height),
										new Posn(this.loc.x + this.width / 2, 
												this.loc.y - 3 * this.height / 2),
												Color.red),
												// the door - black in the middle
												new RectangleImage(new Posn(this.loc.x + this.width / 2,
														this.loc.y - this.height / 4),
														this.width / 2, this.height / 2, Color.gray),
														this.person.personImage());
	}
}

class Person{
	Posn pinhole;
	int width;
	int height;
	Color color;

	Person(Posn pinhole, int width, int height, Color color){
		this.pinhole = pinhole;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	// make the image of this house
	WorldImage personImage(){
		return 
				// draw the legs
				new LineImage(this.pinhole, 
						new Posn(this.pinhole.x - this.width / 2, this.pinhole.y + this.height / 2),
						Color.black).overlayImages(
								new LineImage(this.pinhole, 
										new Posn(this.pinhole.x + this.width / 2, this.pinhole.y + this.height / 2),
										Color.black),
										// draw the arms
										new LineImage(this.pinhole, 
												new Posn(this.pinhole.x + this.width / 2, this.pinhole.y),
												Color.black),        
												new LineImage(this.pinhole, 
														new Posn(this.pinhole.x - this.width / 2, this.pinhole.y),
														Color.black),
														// draw the torso
														//new RectangleImage(this.pinhole, this.width / 3, 2 * this.height / 3, 
														//    this.color),
														// draw the head
														new CircleImage(new Posn(this.pinhole.x, this.pinhole.y - this.height / 4), 
																this.height / 4, Color.black));
	}
}
// a class to add some trees to the ticky-tack world
class Tree{
	Posn loc;         // the left base of the trunk
	int trunkHeight;  // the height of the trunk
	int width;        // the width of the crown of the tree
	int height;       // the height of the crown of the tree

	Tree(Posn loc, int trunkHeight, int width, int height){
		this.loc = loc;
		this.trunkHeight = trunkHeight;
		this.width = width;
		this.height = height;
	}

	//make the image of this tree
	WorldImage treeImage(){
		return
				// the trunk
				new RectangleImage(new Posn(this.loc.x + 5, 
						this.loc.y - this.trunkHeight / 2),
						10, this.trunkHeight, 
						new Color(0x84, 0x3c, 0x24)).overlayImages(
								new OvalImage(new Posn(this.loc.x + 5, 
										this.loc.y - this.trunkHeight - this.height / 2 + 3),
										this.width, this.height, Color.green));
	}
}

// a white cloud floating over the ticky-tack houses
class Cloud{
	Posn loc;
	int width;
	int height;

	Cloud(Posn loc, int width, int height){
		this.loc = loc;
		this.width = width;
		this.height = height;
	}

	// move this cloud east with the wind
	void move(int dx){
		this.loc.x = this.loc.x + dx;
	}

	// when cloud moves off the canvas, move it back to the west
	void moveInBounds(int dx, int hbound){
		if (this.loc.x + dx > hbound)
			this.loc.x = dx;
		else
			this.loc.x = this.loc.x + dx;
	}

	//make the image of this cloud
	WorldImage cloudImage(){
		return
				new OvalImage(this.loc, this.width, this.height, Color.white).overlayImages(
						new OvalImage(new Posn(this.loc.x - this.width / 4, 
								this.loc.y - this.height / 4),
								this.width / 2, this.width / 2, Color.white),
								new OvalImage(new Posn(this.loc.x + this.width / 4, 
										this.loc.y - this.height / 2),
										this.width / 2, this.height / 2, Color.white));       
	}
}

// the sun above this pretty world
class Sun{
	int size; // limited to less than 30

	Sun(int size){
		this.size = size % 30;
	}

	//make the image of this sun - somewhat transparent
	WorldImage sunImage(){
		return 
				new DiskImage(new Posn(50, 50), this.size, new Color(255, 255, 0, 230));
	}
}

// the ticky-tack song
class Song implements SoundConstants, Iterable<Note> {

	Note a = new Note("A5n1");
	Note c2 = new Note("C4n2");
	Note d2 = new Note("D4n2");
	Note e2 = new Note("E4n2");
	Note e = new Note("E4n1");
	Note f2 = new Note("F4n2");
	Note f = new Note("F4n1");
	Note g2 = new Note("G4n2");
	Note g = new Note("G4n1");
	Note cup2 = new Note("C5n2");
	Note cup = new Note("C5n1");
	Note dup = new Note("D5n1");
	Note eup = new Note("E5n1");
	Note silent = new Note(0, 0);


	Note[] tickyTackSong = new Note[]{
			this.g, this.a, this.e2, this.silent, this.c2, this.silent,     // li-ttle bo-xes
			this.g, this.a, this.e2, this.silent, this.c2, this.silent,     // on the hill side
			this.g, this.g, this.cup, this.silent, this.cup, this.silent,   // li-ttle bo-xes
			this.cup, this.dup, this.eup, this.dup, this.cup2, this.silent, // made of ti-cky ta-cky
			this.cup, this.a, this.g2, this.silent, this.g2, this.silent,   // li-ttle bo-xes
			this.g, this.a, this.f2, this.silent, this.f2, this.silent,     // li-ttle bo-xes
			this.f, this.g, this.e2, this.silent, this.e2, this.silent,     // and they all look
			this.e, this.f, this.d2, this.silent, this.silent, this.silent  // just the same	
	};

	public Iterator<Note> iterator(){
		return new SongIterator(this.tickyTackSong);
	}
}

// a circular iterator for the given Array of notes
// must play a silent note when pausing
class SongIterator implements Iterator<Note>{

	Note[] song;
	int i;
	int len;

	SongIterator(Note[] song){
		this.song = song;
		this.len = song.length;
		this.i = this.len - 1;
	}


	// circular iterator - never runs out of notes ot play
	public boolean hasNext(){
		return true;
	}

	// produce the next note to play
	public Note next(){
		this.i = (i + 1) % 48;
		return this.song[i];
	}

	// do nothing on remove
	public void remove(){}
}


/** Class that represents little houses with clouds above */
class TickyTackSong extends World{
	House h1 = new House(new Posn(0, 300), 60, 80, Color.red);
	House h2 = new House(new Posn(60, 300), 60, 40, Color.green);
	House h3 = new House(new Posn(120, 300), 80, 60, Color.pink);
	House h4 = new House(new Posn(200, 300), 70, 50, Color.cyan);
	House h5 = new House(new Posn(270, 300), 90, 70, Color.yellow);
	House h6 = new House(new Posn(360, 300), 80, 60, Color.magenta);
	House h7 = new House(new Posn(440, 300), 90, 70, Color.orange);

	WorldImage familyImage = new FromFileImage(new Posn(30,272), "Images/family.png");

	Tree t1 = new Tree(new Posn(550, 300), 40, 50, 80);
	Tree t2 = new Tree(new Posn(580, 300), 20, 30, 30);

	Cloud cloud;

	Sun sun = new Sun(20);


	Iterator<Note> tuneToPlay = (new Song()).iterator();

	int play = 0;

	int[] tickyTackTune = new int[]{
			67, 69, 64,  0, 62,  0,	  
			67, 69, 64,  0, 62,  0,	
			67, 67, 72, 72, 
			72, 74, 76, 74, 72,  0,
			72, 69, 65,  0, 65,  0,
			67, 69, 64,  0, 64,  0,
			64,  0, 65,  0, 62,  0
	};

	TickyTackSong(Cloud cloud, Sun sun){
		this.cloud = cloud;
		this.sun = sun;
	}

	// move the cloud, making sure there is always a cloud in the sky
	public void onTick(){
		this.cloud.moveInBounds(8, 600);
		this.tickTunes.addNote(PIANO, tuneToPlay.next());
		//this.tickTunes.addTune(new Tune(TUBA, tuneToPlay.next()));
		//this.tickTunes.addTune(new Tune(TUBA, new Note(this.tickyTackTune[(play++) % 48])));
		//this.tickTunes.addNote(TUBA, new Note(this.tickyTackTune[(play++) % 48]));
	}

	// the space bar makes the sun grow till max, then shrink and grow again
	public void onKeyEvent(String ke){
		if (ke.equals(" "))
			this.sun = new Sun(this.sun.size + 3);

		else if (ke.equals("x"))
			this.endOfWorld("Have a nice Day!");

		else if (ke.equals("up"))
			this.keyTunes.addNote(BIRD_TWEET, new Note("C4n2"));

		else if (ke.equals("a"))
			this.keyTunes.addNote(BIRD_TWEET, new Note("G2n4"));

		else if (ke.equals("s"))
			this.keyTunes.addNote(BIRD_TWEET, new Note("G2n4"));

		else if (ke.equals("d")) {
			this.keyTunes.addNote(BAGPIPE, new Note("E2f4"));
			this.keyTunes.addNote(BAGPIPE, new Note("C2n4"));
			this.keyTunes.addNote(CLARINET, new Note("B2n4"));
		}

		else if (ke.equals("f"))
			this.keyTunes.addNote(CLARINET, new Note("B4n4"));

		else if (ke.equals("g"))
			this.keyTunes.addNote(CLARINET, new Note("G2n4"));
	}

	// move the cloud to the location of the mouse click - if it is high enough
	public void onMouseClicked(Posn p){
		if (p.y < 100)
			this.cloud.loc = p;
	}

	//say goodbye to the sun, when it gets to be big enough again
	public WorldEnd worldEnds(){
		if (10 < sun.size && sun.size < 20)
			return new WorldEnd(true, 
					this.makeImage().overlayImages(
							new TextImage(new Posn(150, 80), "Goodbye sun!", 
									15, 3, new Red())));
		else 
			return new WorldEnd(false, this.makeImage());
	}

	// produce the image of the whole world - 
	// with all houses, both trees, the cloud and the sun
	public WorldImage makeImage(){
		return 
				new RectangleImage(new Posn(300, 150), 600, 300, Color.blue).overlayImages(
						this.h1.houseImage(),
						this.h2.houseImage(),
						this.h3.houseImage(),
						this.h4.houseImage(),
						this.h5.houseImage(),
						this.h6.houseImage(),
						this.h7.houseImage(),
						this.t1.treeImage(),
						this.t2.treeImage(),
						this.cloud.cloudImage(),
						this.familyImage,
						this.sun.sunImage());
	}

	public WorldImage lastImage(String s){
		return
				this.makeImage().overlayImages(
						new TextImage(new Posn(150, 80), s, 
								15, 3, new Red()));
	} 
}

class ExamplesTickyTackSong{
	ExamplesTickyTackSong(){}

	House h1 = new House(new Posn(0, 300), 60, 80, Color.red);
	House h2 = new House(new Posn(60, 300), 60, 40, Color.green);
	House h3 = new House(new Posn(120, 300), 80, 60, Color.pink);
	House h4 = new House(new Posn(200, 300), 70, 50, Color.cyan);
	House h5 = new House(new Posn(270, 300), 90, 70, Color.yellow);
	House h6 = new House(new Posn(360, 300), 80, 60, Color.magenta);
	House h7 = new House(new Posn(440, 300), 90, 70, Color.orange);

	WorldImage familyImage = new FromFileImage(new Posn(30,272), "Images/family.png");

	Tree t1 = new Tree(new Posn(550, 300), 40, 50, 80);
	Tree t2 = new Tree(new Posn(580, 300), 20, 30, 30);

	Cloud cloud = new Cloud(new Posn(200, 100), 90, 60);

	Sun sun = new Sun(25);

	WorldImage wholeworld = 
			new RectangleImage(new Posn(300, 150), 600, 300, Color.blue).overlayImages(
					this.h1.houseImage(),
					this.h2.houseImage(),
					this.h3.houseImage(),
					this.h4.houseImage(),
					this.h5.houseImage(),
					this.h6.houseImage(),
					this.h7.houseImage(),
					this.t1.treeImage(),
					this.t2.treeImage(),
					this.cloud.cloudImage(),
					this.familyImage,
					this.sun.sunImage());

	TickyTackSong tworld = new TickyTackSong(this.cloud, new Sun(25));

	void reset(){
		this.sun = new Sun(25);
		this.cloud = new Cloud(new Posn(200, 100), 90, 60);
		this.tworld = new TickyTackSong(this.cloud, new Sun(25));
	}

	// test the method move in the class Cloud
	void testMove(Tester t){
		this.reset();
		this.cloud.move(5);
		t.checkExpect(this.cloud, new Cloud(new Posn(205, 100), 90, 60));
	}

	// test the method moveInBounds in the class Cloud
	void testMoveInBounds(Tester t){
		this.reset();
		this.cloud.moveInBounds(5, 300);
		t.checkExpect(this.cloud, 
				new Cloud(new Posn(205, 100), 90, 60));
		this.cloud.moveInBounds(5, 200);
		t.checkExpect(this.cloud, 
				new Cloud(new Posn(5, 100), 90, 60));
	}

	// test the constructor for the sun
	void testSunConstructor(Tester t){
		t.checkExpect(new Sun(35), new Sun(5));
	}

	// test the method onTick for the ticky-tack world
	void testOnTick(Tester t){
		this.reset();
		TuneBucket nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 0);
		t.checkExpect(this.tworld, this.tworld);

		this.tworld.testOnTick();
		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(208, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 1);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("G4n1")), true);

		this.tworld.testOnTick();
		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(216, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 1);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("G4n1")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("A5n1")), true);

		this.tworld.testOnTick();
		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(224, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 1);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("A5n1")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("E4n2")), true);

		this.tworld.testOnTick();
		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(232, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 2);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("E4n2")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("E4n1")), true);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("A0n0")), true);

		this.tworld.testOnTick();
		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(240, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 1);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("E4n2")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("E4n1")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("A0n0")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("C4n2")), true);

		for(int i = 0; i < 45; i++)
			this.tworld.testOnTick();

		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(600, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 1);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("A5n1")), true);

		this.tworld.testOnTick();
		t.checkExpect(this.tworld.cloud, new Cloud(new Posn(8, 100), 90, 60));
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 1);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("A5n1")), false);
		t.checkExpect(nowPlaying.contains(TickyTackSong.PIANO, new Note("E4n2")), true);

		this.reset();
		nowPlaying = this.tworld.nowPlaying();
		t.checkExpect(nowPlaying.bucketSize(), 0);
		t.checkExpect(this.tworld, this.tworld);
	}

	// test the method onKeyEvent for the ticky-tack world
	void testOnKeyEvent(Tester t){
		this.reset();
		t.checkExpect(this.tworld, this.tworld);

		this.tworld.testOnKey(" ");
		t.checkExpect(this.tworld.sun, new Sun(28));

		this.tworld.testOffKey(" ");

		this.tworld.testOnKey(" ");
		t.checkExpect(this.tworld.sun, new Sun(1));

		this.tworld.testOnKey("x");
		t.checkExpect(this.tworld, this.tworld);

		this.reset();
		this.tworld.testOnKey("b");
		t.checkExpect(this.tworld, this.tworld);

		this.reset();
		this.tworld.testOnKey(" ");
		t.checkExpect(this.tworld.sun, new Sun(28));

		// test world ending key event
		this.tworld.testOnKey("x");
		t.checkExpect(this.tworld, this.tworld);

		this.reset();
		TuneBucket nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 0);
		this.tworld.testOnKey("up");
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 1);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BIRD_TWEET, new Note("C4n2")), true);

		this.tworld.testOffKey("up");
		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 0);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BIRD_TWEET, new Note("C4n2")), false);

		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 0);
		this.tworld.testOnKey("a");
		this.tworld.testOnKey("s");
		this.tworld.testOnKey("d");
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 5);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BIRD_TWEET, new Note("G2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("E2f4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("C2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B2n4")), true);

		this.tworld.testOffKey("a");
		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 4);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BIRD_TWEET, new Note("G2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("E2f4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("C2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B2n4")), true);

		this.tworld.testOnKey("f");
		this.tworld.testOnKey("g");
		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 6);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BIRD_TWEET, new Note("G2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("E2f4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B4n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("G2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("C2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B2n4")), true);

		this.tworld.testOffKey("s");
		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 5);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BIRD_TWEET, new Note("G2n4")), false);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("E2f4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B4n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("G2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("C2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B2n4")), true);

		this.tworld.testOffKey("d");
		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 2);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("E2f4")), false);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B4n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("G2n4")), true);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.BAGPIPE, new Note("C2n4")), false);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B2n4")), false);

		this.reset();
		nowPlayingOnKeyPress = this.tworld.nowPlayingOnKeyPress();
		t.checkExpect(nowPlayingOnKeyPress.bucketSize(), 0);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("B4n4")), false);
		t.checkExpect(nowPlayingOnKeyPress.contains(SoundConstants.CLARINET, new Note("G2n4")), false);
	}

	// test the method onMouseClicked in the class Cloud
	void testOnMouseClicked(Tester t){
		this.reset();
		this.tworld.onMouseClicked(new Posn(150, 80)); 
		t.checkExpect(this.tworld, 
				new TickyTackSong(new Cloud(new Posn(150, 80), 90, 60), this.sun));
		this.tworld.onMouseClicked(new Posn(150, 180));
		t.checkExpect(this.tworld,
				this.tworld);
	}

	// run the ticky-tack game
	void testWholeWorld(Tester t){
		//this.tworld.bigBang(600, 300, 0.2);
	}


	public static void main(String[] argv){
		ExamplesTickyTackSong ett = new ExamplesTickyTackSong();
		Tester.runReport(ett, false, false);
	}

}