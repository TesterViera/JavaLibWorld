package javalib.soundworld;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javalib.tunes.MusicBox;
import javalib.tunes.Note;
import javalib.tunes.SoundConstants;
import javalib.tunes.TuneBucket;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;

import javax.swing.Timer;
import javax.swing.WindowConstants;


/**
 * Copyright 2009, 2010, 2011, 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */


/**
 * World for programming interactive games - with graphics, key events,
 * timer, and sound effects. 
 * 
 * @author Viera K. Proulx, Virag Shah
 * @since October 29, 2009, October 21 2010, July 12 2011, February 22 2012, 15 November 2012
 */
abstract public class World implements SoundConstants, Versions{

  /** the canvas that displays the current world */
  public WorldCanvas theCanvas;

  /** true if 'bigBang' started the world and it did not end, did not stop */
  private volatile boolean worldExists = false;

  /** the timer for this world */
  protected volatile MyTimer mytime;
  
  /** timer events not processed when the mouse event is processed */
  protected volatile boolean stopTimer = false;
  
  /** the key adapter for this world */
  private volatile MyKeyAdapter ka;
  
  /** the currently pressed key */
  private volatile ArrayList<String> pressedKeys = new ArrayList<String>();
  
  /** the mouse adapter for this world */
  private volatile MyMouseAdapter ma;
  
  /** the window closing listener for this world */
  private volatile WindowListener windowClosing;
  
  /** a blank image, to avoid <code>null</code> in the <code>lastWorld</code> */
  private transient WorldImage blankImage = 
      new CircleImage(new Posn(0, 0), 1, Color.white);
  
  /** the last world - if needed */
  public WorldEnd lastWorld = new WorldEnd(false, this.blankImage);
  
  /////////////////////////////////////////////////////////////////////////
  // Data for handling the sound                                         //
  /////////////////////////////////////////////////////////////////////////
  
  /** the MIDI synthesizer that plays the notes */
  private volatile MusicBox myMusicBox;
  
  /** the current program for the MusicBox */
  private transient int[] instruments = new int[16];

  /** the collection of tunes to play on tick */
  public TuneBucket tickTunes = new TuneBucket();
  
  /** the collection of tunes currently playing on tick */
  protected volatile TuneBucket currentTickTunes = new TuneBucket();
  
  /** the collection of tunes to play on key event */
  public TuneBucket keyTunes = new TuneBucket();  
  
  /** the collection of tunes currently playing on keys press */
  protected volatile TuneBucket currentKeyTunes = new TuneBucket();
  
  /** the collection of tunes currently playing on key event */
  protected volatile HashMap<String,TuneBucket> keyReleasedTunes;
  
  /**
   * The default constructor. To start the world one must invoke the
   * <code>bigBang</code> method.
   */
  public World(){ 
    this.initMusic();
  }

  /////////////////////////////////////////////////////////////////////////
  // Methods for interacting with the World                              //
  /////////////////////////////////////////////////////////////////////////

  /**
   * Initialize the MIDI synthesizer and the tune buckets
   */
  void initMusic(){
    /** the MIDI synthesizer that plays the notes */
    this.myMusicBox = new MusicBox();

    if (javalib.tunes.MusicBox.SYNTH_READY){
      /** the collection of tunes to play on tick */
      this.tickTunes = new TuneBucket(this.myMusicBox);

      /** the collection of tunes currently playing on tick */
      this.currentTickTunes = new TuneBucket(this.myMusicBox);

      /** the collection of tunes to play on key event */
      this.keyTunes= new TuneBucket(this.myMusicBox);  

      /** the collection of tunes currently playing on key event */
      this.keyReleasedTunes = new HashMap<String, TuneBucket>();
    }
    else{
      /** notify the user that music cannot play */
      System.out.println("MIDI synthesizer or the soundbank not available.");
      System.out.println("Tunes will not be played.");
      
      /** the collection of tunes to play on tick */
      this.tickTunes = new TuneBucket();

      /** the collection of tunes currently playing on tick */
      this.currentTickTunes = new TuneBucket();

      /** the collection of tunes to play on key event */
      this.keyTunes= new TuneBucket();  

      /** the collection of tunes currently playing on key event */
      this.keyReleasedTunes = new HashMap<String, TuneBucket>();
    }
    
    /** initialize our instrument list to the initial default list */
    this.initInstruments();
  }
  
  /**
   * initialize our copy of the program data to the initial program
   */
  public void initInstruments(){
    for (int i = 0; i < 16; i++){
      this.instruments[i] = SoundConstants.instruments[i];
    }
  }
  
  /**
   * <p>Change the program for the {link MusicBox MusicBox} to the given
   * list of instruments.</p>
   * <p>If there are fewer than 16 instruments given, all the remaining ones
   * are set to play piano.</p>
   * 
   * @param myInstruments an <code>ArrayList</code> of 16 instrument numbers, 
   * must be in the range from 1 to 128
   */
  public void programChange(ArrayList<Integer> myInstruments){

    // make sure there are 16 instruments (add PIANO-s as many as you need)
    if (myInstruments.size() < 16){
      for (int i = myInstruments.size(); i < 16; i++){
        myInstruments.add(1);
      }
    }
    
    // record the current program for this world
    for (int i = 0; i < 16; i++){
      this.instruments[i] = myInstruments.get(i);
    }
    
    // if the synthesizer is running, notify the MusicBox of the program change
    if (javalib.tunes.MusicBox.SYNTH_READY){

      this.myMusicBox.initChannels(instruments);
    }
    this.recordProgramChange();
  }
  
  /**
   * Record the program changes for all relevant <code>TuneBucket</code>s.
   */
  private void recordProgramChange(){
    this.tickTunes.recordProgramChange(this.instruments);
    this.currentTickTunes.recordProgramChange(this.instruments);
    this.keyTunes.recordProgramChange(this.instruments);
    for (String key: this.keyReleasedTunes.keySet())
      this.keyReleasedTunes.get(key).recordProgramChange(this.instruments);
  }
  
  /**
   * <p>Change the program for the {link MusicBox MusicBox} to the given
   * list of instruments.</p>
   * <p>If there are fewer than 16 instruments given, all the remaining ones
   * are set to play piano.</p>
   * 
   * @param myInstruments an <code>Array</code> of 16 instrument numbers, 
   * must be in the range from 1 to 128
   */
  public void programChange(int[] myInstruments){
    if (javalib.tunes.MusicBox.SYNTH_READY){
      // make sure the program change represents 16 channels
      int[] okInstruments = new int[16];
      for (int i = 0; i < Math.min(16, Array.getLength(myInstruments)); i++){
        okInstruments[i] = myInstruments[i];
      }
      if (Array.getLength(myInstruments) < 16){
        for (int i = Array.getLength(myInstruments); i < 16; i++){
          okInstruments[i] = 1;
        }
      }
      this.myMusicBox.initChannels(okInstruments);
    }
  }
  
  /** 
   * Pause at the start when building GUIs and installing listeners
   * and at the end so the last tunes are heard
   */
  void sleepSome(int miliseconds){
    // pause a bit so that two canvases do not compete when being opened
    // almost at the same time
    long start = System.currentTimeMillis();
    long tmp = System.currentTimeMillis();
    //System.out.println("Going to sleep.");

    while(tmp - start < miliseconds){
      tmp = System.currentTimeMillis();
    }
  }
  
  /**
   * Start the world by creating a canvas of the given size, creating
   * and adding the key and mouse adapters, and starting the timer at the
   * given speed.
   * 
   * @param w the width of the <code>Canvas</code>
   * @param h the height of the <code>Canvas</code>
   * @param speed the speed at which the clock runs
   */
  public void bigBang(int w, int h, double speed){
    if (this.worldExists){
      System.out.println("Only one world can run at a time");
      return;
    }
    // throw runtime exceptions if w, h <= 0
    this.theCanvas = new WorldCanvas(w, h);
    this.worldExists = true;
    
    // if the user closes the Canvas window
    // it will only hide and can be reopened by invoking 'show'
    this.theCanvas.f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    
    this.windowClosing = new MyWindowClosingListener(this);
    this.theCanvas.f.addWindowListener(this.windowClosing);
    
    this.initMusic();
    this.pressedKeys = new ArrayList<String>();
    
    // pause a bit so that two canvases do not compete when being opened
    // almost at the same time
    this.sleepSome(100); // miliseconds
    
    // add the key listener to the frame for our canvas
    this.ka = new MyKeyAdapter(this);
    this.theCanvas.f.addKeyListener(this.ka);
    
    // add the mouse listener to the frame for our canvas
    this.ma = new MyMouseAdapter(this);
    this.theCanvas.f.addMouseListener(this.ma);

    // make sure the canvas responds to events
    this.theCanvas.f.setFocusable(true);
    // finally, show the canvas and draw the initial world
    this.theCanvas.show();
   
    // pause again after the Canvas is shown to make sure 
    // all listeners and the timer are installed for theCanvas
    this.sleepSome(100); //miliseconds

    // add the timer
    this.mytime = new MyTimer(this, speed);
    
    this.drawWorld("");

    // start it if speed is greater than 0
    if (speed > 0.0)
      this.mytime.timer.start();
    
    // print a header that specifies the current version of the World
    System.out.println(Versions.CURRENT_VERSION);
  }

  
  /**
   * EFFECT:
   * <p>top the world, close all listeners and the timer, draw the last</p> 
   * <code>Scene</code>.
   */
  protected void stopWorld(){
    if (worldExists){

      // remove listeners and set worldExists to false
      this.mytime.timer.stop();
      this.worldExists = false;
      this.mytime.stopTimer();
      this.theCanvas.f.removeKeyListener(this.ka);
      this.theCanvas.f.removeMouseListener(this.ma);

      // draw the final scene of the world with the end of time message
      this.theCanvas.clear();
      this.theCanvas.drawImage(this.lastWorld.lastImage);
    }
    
    // do this even if the world does not exist
    if (javalib.tunes.MusicBox.SYNTH_READY){ 
      // first add all tunes to be played to the currentTickTunes
      this.currentTickTunes.add(this.tickTunes);
      
      // play the final drumroll
      this.tickTunes.playTunes();

      this.sleepSome(1000); // milliseconds - to play the final tune
    }

    // do this even if there is no MusicBox
    // stop the playing of tunes in the tickTunes, the currentTickTunes
    this.tickTunes.clearBucket();
    this.currentTickTunes.clearBucket();
    
    // stop the playing of tunes in the keyTunes bucket
    // and the tunes for the keys still held
    this.keyTunes.clearBucket();
    for (String ke : this.keyReleasedTunes.keySet())
      this.keyReleasedTunes.get(ke).clearBucket();
    
    System.out.println("The world stopped.");
  }
  
  /**
   * <p>This method is invoked at each tick. It checks if the world 
   * should end now.</p>
   * <p>The saved image will be shown when the world ends,
   * otherwise it is ignored.</p>
   * 
   * @return pair (true, last image) or (false, any image)
   */
  public WorldEnd worldEnds(){
    return this.lastWorld;
    //return new WorldEnd(false, this.blankImage);
  }
  
  /**
   * End the world interactions - leave the canvas open, 
   * show the image of the last world with the given message
   * 
   * @param s the message to display
   */
  public void endOfWorld(String s){
    // set up the last world pair and finish as usual
    this.lastWorld = new WorldEnd(true, this.lastImage(s));
    this.stopWorld();
  }
  
  /**
   * The <code>onTick</code> method is invoked only if the world exists. 
   * To test the method <code>onTick</code> we provide this method that
   * will invoke the <code>onTick</code> method for the testing purposes.
   */
  public void testOnTick(){
    // the world does not exist, but run the test for the end of the world
    // and handle the last sound effect as needed
    this.lastWorld = this.worldEnds();
    if (this.lastWorld.worldEnds){
      this.stopWorld();
    }
    // now invoke the onTick method - 
    // it will ignore the code similar to that above
    // but will handle the tickTunes manipulation
    this.processTick();
  }

  /**
   * The method invoked by the timer on each tick.
   * Delegates to the user to define a new state of the world,
   * then resets the canvas and event handlers for the new world
   * to those currently used.
   */
  protected void processTick(){
    try{

      // check if the world should stop only if the world exists
      // and the clock is ticking
      if (this.worldExists && !this.stopTimer){
        this.lastWorld = this.worldEnds();
        if (this.lastWorld.worldEnds){
          this.stopWorld();
        }
      }

      // handle the tickTunes even when the world does not run

      // advance the tick on current tunes
      // and stop playing those that are done
      this.currentTickTunes.nextBeat();

      // process the changes to the world on this tick
      this.onTick();

      // first add all tunes to be played to the currentTickTunes
      this.currentTickTunes.add(this.tickTunes);
      
      // play the tunes collected in the tick tune bucket
      // Note: we must first add tunes to the currentTickTunes
      //       because playTunes advances the tunes it played
      //       and removes silent notes
      // thus currentTickTunes would never know that one beat note even started playing
      this.tickTunes.playTunes();
            
      // remove all tunes in the tickTunes bucket
      this.tickTunes.clearTunes();

      // but draw the world only if the world exists
      if (this.worldExists)
        this.drawWorld("");
      /*
      else
        // the world has stopped - clear the tick tune and key tunes buckets
        this.stopWorld();
      */
    }

      catch(RuntimeException re){
        re.printStackTrace();
        this.lastWorld.lastImage.overlayImages(
            new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
        this.stopWorld();
        //throw re;
        Runtime.getRuntime().halt(1);
      }
    }  

  /**
   * <P>User defined method to be invoked by the timer on each key event.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   */
  abstract public void onTick();
    
  /**
   * Allow the programmer to check that the currently playing tunes are those
   * that have been provided by the previous <code>onTick</code> method 
   * invocations.
   * 
   * @return the <code>TuneBucket</code> that contains all currently playing 
   * tunes
   */
  public TuneBucket nowPlaying(){
    return this.currentTickTunes;
  }
  
  /**
   * Return the currently playing MIDI program
   * @return the currently playing MIDI program
   */
  public int[] getProgram(){
    return this.instruments;
  }
  
  /**
   * Allow the programmer to check the currently selected MIDI program.
   * 
   * @return the <code>ArrayList</code> of <code>Integer</code> that 
   * represents the current MIDI program.
   */
  ArrayList<Integer> getCurrentProgram(){
    ArrayList<Integer> program = new ArrayList<Integer>();
    for (int i = 0; i < 16; i++){
      program.add(instruments[i]);
    }
    return program;
  }

  /**
   * The method invoked by the key adapter on key pressed event.
   * Clears the contents of the keyTunes bucket.
   * Delegates to the user to define a new state of the world,
   * then plays the tunes deposited in the keyTunes bucket.
   * It then copies these tunes to the keyReleasedTunes hashmap,
   * using the key pressed as the hashmap key.
   */
  protected void processKeyEvent(String ke){
    try{
      if (this.worldExists && !this.pressedKeys.contains(ke)){

        this.pressedKeys.add(ke);
        
        // empty the key tune bucket
        this.keyTunes.clearTunes();

        if (this.worldExists){
            this.onKeyEvent(ke); 

            // play the tunes collected in the key tune bucket
            // save what is currently playing so it plays for the desired
            // number of ticks
            this.keyReleasedTunes.put(ke, this.keyTunes.copy());
            this.keyTunes.playTunes();
            
            if (!this.lastWorld.worldEnds)
              this.drawWorld("");
            else{
              // stop the world
              this.stopWorld();
            }
        }    
      }
    }
    catch(RuntimeException re){
      re.printStackTrace();
      this.lastWorld.lastImage.overlayImages(
          new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
      this.stopWorld();
      //throw re;
      Runtime.getRuntime().halt(1);
    }
  }

  /**
   * The method invoked by the key adapter when key is released.
   * Delegates to the user to define a new state of the world,
   * then removes from the KeyReleasedTunes bucket those tunes that 
   * started playing when the given key was pressed.
   * 
   */
  void processKeyReleasedEvent(String ke){
	  TuneBucket tmp = this.keyReleasedTunes.remove(ke);
	  this.pressedKeys.remove(ke);
	  if (tmp != null) {
		  if(!isPlaying(tmp))
			  tmp.clearBucket();
	  }
	  // invoke user-defined onKeyReleased method
	  this.onKeyReleased(ke);
  }

  /**
   * The method checks if the given TuneBucket is still being played.
   * Used to incorporate the scenario where two different keys are playing the 
   * same tune on the same channel and if one key is released the tune should 
   * still continue to play until the second key is released.
   * @param tuneBucket the TuneBucket to check for
   * @return true if keyReleasedTunes contains the TuneBucket as value. 
   */
  public boolean isPlaying(TuneBucket tuneBucket) {
	  return this.keyReleasedTunes.containsValue(tuneBucket);
  }

  /**
   * <P>User defined method to be invoked by the key adapter 
   * when a key is released.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   */  
  public void onKeyReleased(String ke){}

  /**
   * <P>User defined method to be invoked by the key adapter 
   * when a key is pressed.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Implement this method in the game world class</P>
   */
  abstract public void onKeyEvent(String s);
  
  /**
   * Test the onKey method effect on the keyTune bucket
   * 
   * @param ke the key that was pressed
   */
  public void testOnKey(String ke){

	  if (!this.pressedKeys.contains(ke)) {

		  this.lastWorld = this.worldEnds();
		  if (this.lastWorld.worldEnds) {
			  this.stopWorld();
		  }

		  this.pressedKeys.add(ke);

		  // empty the key tune bucket
		  this.keyTunes.clearTunes();

		  this.onKeyEvent(ke);

		  // Only if tunes are added to the keyTunes bucket on key press,
		  // add it to the keyReleasedTunes hashmap and the currentKeyTunes 
		  // list so that it can be tested against.
		  // Play the collected tunes in the keyTunes bucket.
		  if(this.keyTunes.bucketSize() > 0) {
			  this.keyReleasedTunes.put(ke, this.keyTunes.copy());
			  this.currentKeyTunes.add(this.keyTunes);
			  this.keyTunes.playTunes();
		  }

		  if (this.worldExists)
			  this.drawWorld("");
	  } 
  }

  /**
   * Test the release of the key after some time since the press
   * 
   * @param ke the key that was released
   */
  public void testOffKey(String ke) {
	  TuneBucket tmp = this.keyReleasedTunes.remove(ke);
	  this.pressedKeys.remove(ke);

	  if (tmp != null) {
		  /** Remove only the tunes/notes associated with the corresponding key released. */
		  for (int i = 0; i < 16; i++) {
			  ArrayList<Note> notes = tmp.tunes.get(i).getChord().notes;
			  for(Note n : notes)
				  this.currentKeyTunes.tunes.get(i).getChord().notes.remove(n);		
		  }
		  tmp.clearBucket();
	  }
	  // invoke user-defined onKeyReleased method
	  this.onKeyReleased(ke);
  }


  /**
   * Allow the programmer to check that the currently playing tunes are those
   * that have been provided by the previous <code>onKeyEvent</code> method 
   * invocations.
   * 
   * @return the <code>TuneBucket</code> that contains all currently playing 
   * tunes on key press
   */
  public TuneBucket nowPlayingOnKeyPress(){
	  return this.currentKeyTunes;
  }
  
 
  /**
   * The method invoked by the mouse adapter on mouse clicked event.
   * Delegates to the user to define a new state of the world.
   * 
   * @param mouse the location of the mouse when clicked
   */
  protected void processMouseClicked(Posn mouse){
    try{
      if (this.worldExists){
        // process the mouse click
        this.onMouseClicked(mouse);
        
        if (!this.lastWorld.worldEnds)
          this.drawWorld("");
        else{
          // draw the last world
          this.theCanvas.drawImage(lastWorld.lastImage);
        }
      }
    }
    catch(RuntimeException re){
      re.printStackTrace();
      this.lastWorld.lastImage.overlayImages(
          new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
      this.stopWorld();
      //throw re;
      Runtime.getRuntime().halt(1);
    }
  }

  /**
   * <P>User defined method to be invoked by the mouse adapter 
   * when a mouse is clicked.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @param mouse the location of the mouse when clicked
   */
  public void onMouseClicked(Posn mouse){}
  
  /**
   * The method invoked by the mouse adapter on mouse entered event.
   * Delegates to the user to define a new state of the world.
   * 
   * @param mouse the location of the mouse when entered
   */
  protected void processMouseEntered(Posn mouse){
    try{
      if (this.worldExists){
        // process the mouse click
        this.onMouseEntered(mouse);
        
        if (!this.lastWorld.worldEnds)
          this.drawWorld("");
        else{
          // draw the last world
          this.theCanvas.drawImage(lastWorld.lastImage);
        }
      }
    }
    catch(RuntimeException re){
      re.printStackTrace();
      this.lastWorld.lastImage.overlayImages(
          new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
      this.stopWorld();
      //throw re;
      Runtime.getRuntime().halt(1);
    }
  }

  /**
   * <P>User defined method to be invoked by the mouse adapter 
   * when a mouse is entered.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @param mouse the location of the mouse when entered
   */
  public void onMouseEntered(Posn mouse){}  
  
  
  /**
   * The method invoked by the mouse adapter on mouse exited event.
   * Delegates to the user to define a new state of the world.
   * 
   * @param mouse the location of the mouse when exited
   */
  protected void processMouseExited(Posn mouse){
    try{
      if (this.worldExists){
        // process the mouse click
        this.onMouseExited(mouse);
        
        if (!this.lastWorld.worldEnds)
          this.drawWorld("");
        else{
          // draw the last world
          this.theCanvas.drawImage(lastWorld.lastImage);
        }
      }
    }
    catch(RuntimeException re){
      re.printStackTrace();
      this.lastWorld.lastImage.overlayImages(
          new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
      this.stopWorld();
      //throw re;
      Runtime.getRuntime().halt(1);
    }
  }

  /**
   * <P>User defined method to be invoked by the mouse adapter 
   * when a mouse is exited.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @param mouse the location of the mouse when exited
   */
  public void onMouseExited(Posn mouse){}  
  
  
  /**
   * The method invoked by the mouse adapter on mouse pressed event.
   * Delegates to the user to define a new state of the world.
   * 
   * @param mouse the location of the mouse when pressed
   */
  protected void processMousePressed(Posn mouse){
    try{
      if (this.worldExists){
        // process the mouse click
        this.onMousePressed(mouse);
        
        if (!this.lastWorld.worldEnds)
          this.drawWorld("");
        else{
          // draw the last world
          this.theCanvas.drawImage(lastWorld.lastImage);
        }
      }
    }
    catch(RuntimeException re){
      re.printStackTrace();
      this.lastWorld.lastImage.overlayImages(
          new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
      this.stopWorld();
      //throw re;
      Runtime.getRuntime().halt(1);
    }
  }

  /**
   * <P>User defined method to be invoked by the mouse adapter 
   * when a mouse is pressed.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @param mouse the location of the mouse when pressed
   */
  public void onMousePressed(Posn mouse){}
  
  /**
   * The method invoked by the mouse adapter on mouse released event.
   * Delegates to the user to define a new state of the world.
   * 
   * @param mouse the location of the mouse when released
   */
  protected void processMouseReleased(Posn mouse){
    try{
      if (this.worldExists){
        // process the mouse click
        this.onMouseReleased(mouse);
        
        if (!this.lastWorld.worldEnds)
          this.drawWorld("");
        else{
          // draw the last world
          this.theCanvas.drawImage(lastWorld.lastImage);
        }
      }
    }
    catch(RuntimeException re){
      re.printStackTrace();
      this.lastWorld.lastImage.overlayImages(
          new TextImage(new Posn(40, 40), re.getMessage(), Color.red));
      this.stopWorld();
      //throw re;
      Runtime.getRuntime().halt(1);
    }
  }
  
  /**
   * <P>User defined method to be invoked by the mouse adapter 
   * when a mouse is released.
   * Update the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @param mouse the location of the mouse when released
   */
  public void onMouseReleased(Posn mouse){}

  /**
   * EFFECT:
   * <p>Invoke the user defined <code>makeImage</code> method, if this 
   * <code>{@link World World}</code> has been initialized 
   * via <code>bigBang</code> and did not stop or end, otherwise 
   * invoke the user defined <code>lastImage</code> method</p>
   */
  protected synchronized void drawWorld(String s){
    if (this.worldExists){
      this.theCanvas.clear();
      this.theCanvas.drawImage(this.makeImage());
    }
    else{
      this.theCanvas.clear();
      this.theCanvas.drawImage(this.lastImage(s));
    }
  }
  
  /**
   * <P>User defined method to draw the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @return the image that represents this world at this moment
   */
  abstract public WorldImage makeImage();

  /**
   * <P>User defined method to draw the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @return the image that represents the last world to be drawn
   */
  public WorldImage lastImage(String s){
    return this.makeImage();
  }
  
}

/**
 * The window closing listener - it stops the timer when the frame is closed
 * and stops playing all tunes - in the tickTunes bucket and in the 
 * keyTunes bucket.
 * 
 * @author Viera K. Proulx
 * @since 5 August 2010
 *
 */
class MyWindowClosingListener extends WindowAdapter{
  World w;
  MyWindowClosingListener(World w){
    this.w = w;
  }
  public void windowClosing(WindowEvent we){
    this.w.mytime.stopTimer();

    if (javalib.tunes.MusicBox.SYNTH_READY){ 
      // clear the tick tune bucket
      this.w.tickTunes.clearBucket();
      // clear the key tune bucket
      this.w.keyTunes.clearBucket();
    }
  }
}

/**
 * The action listener for the timer events.
 * 
 * @author Viera K. Proulx
 * @since August 2, 2007
 */
class MyTimer{

  /** the current <code>{@link World World}</code> 
   * that handles the timer events */
  protected World currentWorld;

  /** the <code>Timer</code> that generates the time events */
  protected Timer timer;
  
  public boolean running = true;

  /** the timer speed */
  protected int speed;

  /**
   * Create the initial timer for the given 
   * <code>{@link World World}</code> at the given <code>speed</code>.
   * 
   * @param currentWorld the given <code>{@link World World}</code>
   * @param speed the given <code>speed</code>
   */
  protected MyTimer(World currentWorld, double speed){
    this.currentWorld = currentWorld;
    this.timer = new Timer((new Double(speed * 1000)).intValue(), 
        this.timerTasks);
    this.speed = (new Double(speed * 1000)).intValue();
  }

  /**
   * The callback for the timer events
   */
  protected ActionListener timerTasks = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      if (running)
        currentWorld.processTick();
    }
  };

  /**
   * A helper method to convert the <code>speed</code> given as 
   * a delay time into milliseconds
   */
  protected void setSpeed(){ 
    this.timer.setDelay(this.speed);
  }
  
  protected void stopTimer(){
    this.running = false;
  }
}

/**
 * <p>The implementation of callbacks for the key events.</p>
 * <p>Report all regular key presses and the four arrow keys</p>
 * <p>Ignore other key pressed events, key released events, 
 * special keys, etc.</p>
 * 
 * @author Viera K. Proulx
 * @since August 2, 2007
 */
class MyKeyAdapter extends KeyAdapter {

  /** the current <code>{@link World World}</code> 
   * that handles the key events */
  protected World currentWorld;

  /** the <code>KeyAdapter</code> that handles the key events */
  protected MyKeyAdapter(World currentWorld){
    this.currentWorld = currentWorld;
  }

  /**
   * The callback for the key pressed event
   */
  protected void keyEventCallback(String keyString){
    this.currentWorld.processKeyEvent(keyString); 
  }

  /**
   * The callback for the key released event
   */
  protected void keyReleasedEventCallback(String keyString){
    this.currentWorld.processKeyReleasedEvent(keyString); 
  }

  // --------------------------------------------------------------------//
  // the key event handlers                                              //
  // --------------------------------------------------------------------//-

  /** 
   * <p>Handle the key typed event from the canvas.</p>
   * <p>This is where we get the letter keys.</p>
   *
   * @param e the <code>KeyEvent</code> that caused the callback
   */
  public void keyTyped(KeyEvent e) {
    displayInfo(e, "KEY TYPED: ");
  }

  /** 
   * <p>Handle the key pressed event from the canvas.</p>
   * <p>This is where we get the arrow keys.</p>
   *
   * @param e the <code>KeyEvent</code> that caused the callback
   */  
  public void keyPressed(KeyEvent e) {
    displayInfo(e, "KEY PRESSED: ");
  }
  
  /** 
   * <p>Handle the key typed event from the canvas.</p>
   * <p>This is where we get the letter keys.</p>
   *
   * @param e the <code>KeyEvent</code> that caused the callback
   */
  public void keyReleased(KeyEvent e) {
    displayInfo(e, "KEY RELEASED: ");
  }


  /**
   * <p>The key event major processor. The code is adopted from the code
   * provided by the Java documentation for key event processing.</p>
   * 
   * <p><em>We have to jump through some hoops to avoid
   * trying to print non-printing characters 
   * such as Shift.  (Not only do they not print, 
   * but if you put them in a String, the characters
   * afterward won't show up in the text area.)</em></P>
   * 
   * <p>There may be unneeded code here. An earlier comment stated:
   * <em>needs to add conversion table from code to 
   * </em><code>String</code>. The code for converting a single character
   * to <code>String</code> is included here.</p>
   */
  protected void displayInfo(KeyEvent e, String s){
    String keyString ="";
    String modString, tmpString,
    actionString, locationString;

    //You should only rely on the key char if the event
    //is a key pressed or released event.
    int id = e.getID();
    //if (id == KeyEvent.KEY_TYPED) {
      // regular letter has been pressed and released
      //char c = e.getKeyChar();
      //keyString = "" + c;

      // process four arrow keys when pressed
    if (//id == KeyEvent.KEY_TYPED ||
        id == KeyEvent.KEY_PRESSED ||
        id == KeyEvent.KEY_RELEASED){
      
      // check if pressed is one of the arrows
      int keyCode = e.getKeyCode();
      if (keyCode == 37)
        keyString = "left"; 
      else if (keyCode == 38)
        keyString = "up"; 
      else if (keyCode == 39)
        keyString = "right"; 
      else if (keyCode == 40)
        keyString = "down"; 

      else{
        // regular letter has been pressed or released
        char c = e.getKeyChar();
        keyString = "" + c;
      } 
/*
      else{
        keyString = KeyEvent.getKeyText(e.getKeyCode());
      }
*/
    }

    /*
    if (id == KeyEvent.KEY_RELEASED)
      System.out.println("Key " + keyString + 
          " keyCode " //+ e.getKeyCode() + " event: " 
          + "released");
    */
    /*
    if (id == KeyEvent.KEY_TYPED)
      System.out.println("Key " + keyString + 
          " keyCode " + e.getKeyCode() + " event: " + "typed");
    */
    /*
    if (id == KeyEvent.KEY_PRESSED)
      System.out.println("Key " + keyString + 
          " keyCode " //+ e.getKeyCode() + " event: " 
          + "pressed");
    */
    // ignore other keys pressed

    //////////////////////////////////////////////////////////////////////////
    // here are the actual callbacks for key pressed and key released                                         //
    if (keyString.length() != 0)
      
       if (id == KeyEvent.KEY_PRESSED) 
       //if (id == KeyEvent.KEY_TYPED) 
           keyEventCallback(keyString); 
    
       else if (id == KeyEvent.KEY_RELEASED) 
            keyReleasedEventCallback(keyString);
     
    //
    //////////////////////////////////////////////////////////////////////////
  } 
  // ---------------------------------------------------------------------
  // end of the key event major processor
}


/**
 * <p>The implementation of callbacks for the mouse events: 
 * mouse clicked, entered, exited, pressed, and released.</p>
 * <p>We do not handle mouse motion events: mouse dragged and 
 * mouse moved.</p>
 * 
 * @author Viera K. Proulx
 * @since August 2, 2007
 */
class MyMouseAdapter extends MouseAdapter {

  /** the current <code>{@link World World}</code> 
   * that handles the mouse events */
  protected World currentWorld;

  /** the mouse position recorded by the mouse event handler */
  protected Posn mousePosn;
  
  /**
   * Create the mouse listener for the given <code>{@link World World}</code>.
   * 
   * @param currentWorld the given <code>{@link World World}</code>
   */
  protected MyMouseAdapter(World currentWorld){
    this.currentWorld = currentWorld;
  }
  
  // ---------------------------------------------------------------------
  // the mouse event handlers
  
  /**
   * Adjust the reported mouse position to account for the top bar
   * 
   * @param mousePosn the recorded mouse position
   * @return the actual mouse position
   */ 
  Posn adjustMousePosn(Posn mousePosn){
    // .... use this to find the height of the top bar
    Insets ins = this.currentWorld.theCanvas.f.getInsets();
    mousePosn.y -= ins.top;
    return mousePosn;
  }
   
  /**
   * Invoked when the mouse has been clicked on a component.
   * 
   * @param e the mouse event that invoked this callback
   */
  public void mouseClicked(MouseEvent e) {
    this.currentWorld.stopTimer = true;
    this.mousePosn = new Posn(e.getX(), e.getY());
    this.currentWorld.processMouseClicked(adjustMousePosn(this.mousePosn));
    this.currentWorld.stopTimer = false;
  }
  
  /**
   * Invoked when the mouse enters a component.
   * 
   * @param e the mouse event that invoked this callback
   */
  public void mouseEntered(MouseEvent e) {
    this.currentWorld.stopTimer = true;
    this.mousePosn = new Posn(e.getX(), e.getY());
    this.currentWorld.processMouseEntered(adjustMousePosn(this.mousePosn));
    this.currentWorld.stopTimer = false;
  }
  
  /**
   * Invoked when the mouse exits a component.
   * 
   * @param e the mouse event that invoked this callback
   */
  public void mouseExited(MouseEvent e) {
    this.currentWorld.stopTimer = true;
    this.mousePosn = new Posn(e.getX(), e.getY());
    this.currentWorld.processMouseExited(adjustMousePosn(this.mousePosn));
    this.currentWorld.stopTimer = false;
  }
  
  /**
   * Invoked when the mouse button has been pressed on a component.
   * 
   * @param e the mouse event that invoked this callback
   */
  public void mousePressed(MouseEvent e) {
    this.currentWorld.stopTimer = true;
    this.mousePosn = new Posn(e.getX(), e.getY());
    this.currentWorld.processMousePressed(adjustMousePosn(this.mousePosn));
    this.currentWorld.stopTimer = false;
  }
  
  /**
   * Invoked when a mouse button has been released on a component.
   * 
   * @param e the mouse event that invoked this callback
   */
  public void mouseReleased(MouseEvent e) {
    this.currentWorld.stopTimer = true;
    this.mousePosn = new Posn(e.getX(), e.getY());
    this.currentWorld.processMouseReleased(adjustMousePosn(this.mousePosn));
    this.currentWorld.stopTimer = false;
   }
}
