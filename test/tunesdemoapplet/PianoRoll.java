package tunesdemoapplet;

import javalib.worldimages.*;
import javalib.tunes.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A comprehensive demo of the javalib.soundworld and javalib.tunes library.
 * 
 * @author Viera K. Proulx
 * @since 15 August 2012
 */

/**
 * A world that represents three melodies to be played by the demo program.
 * It provides the <code>nextChord</code> method that traverses the chords 
 * in a circular manner. (In effect it is a circular iterator.)
 */
public class PianoRoll implements SoundConstants{
  
  /** the melody on the piano roll that is to be played */
  protected ArrayList<RollChord> melody = new ArrayList<RollChord>();
  
  /** the index of the current note to play */
  protected int current = 0;
  
  /** 0 = no play, 1 = play forwards, -1 = play backwards */
  protected int next = 0;
  
  /** report on whether the piano roll is currently playing */
  public boolean isPlaying = false;
  
  // top left corner of the area for the PianoRoll
  protected static int x = 20;
  protected static int y = 150;
  protected static int width = 400;
  
  protected ArrayList<ArrayList<Note>> mytunes = 
    new ArrayList<ArrayList<Note>>();
  
  /** Frere Jacques melody  
   * !! the note DownG is below the staff lines: not shown but played.
   */
  protected static ArrayList<Note> mytune1 = new Chord(0,0,0,0,
      noteC,0,noteD,0,noteE,0,noteC,0,
      noteC,0,noteD,0,noteE,0,noteC,0,
      noteE,0,noteF,0,noteG,0,0,0,
      noteE,0,noteF,0,noteG,0,0,0,
      noteG,noteA,noteG,noteF,noteE,0,noteC,0,
      noteG,noteA,noteG,noteF,noteE,0,noteC,0,
      noteC,0,noteDownG,0,noteC,0,0,0,
      noteC,0,noteDownG,0,noteC,0,0,0,      
      0,0,0).notes;
  
  /** Humoresque melody */
  protected static ArrayList<Note> mytune2 = new Chord(0,0,0,0,
      noteC,noteD,noteE,noteF,noteG,0,noteG,0,noteA,0,noteA,0,noteG,0,0,0,
      noteA,0,noteA,0,noteG,0,0,0,
      noteF,noteF,noteF,noteF,noteE,0,noteE,0,noteD,0,noteD,0,noteG,0,0,0,
      noteF,noteF,noteF,noteF,noteE,0,noteE,0,noteD,0,noteD,0,noteC,0,0,0,
      0,0,0).notes;

  
  /** 'Mary had a little lamb' melody */
  protected static ArrayList<Note> mytune3 = new Chord(0,0,0,0,
      noteE,0,noteD,0,noteC,0,noteD,0,noteE,0,noteE,0,noteE,0,0,0,
      noteD,0,noteD,0,noteD,0,0,0,noteE,0,noteG,0,noteG,0,0,0,
      noteE,0,noteD,0,noteC,0,noteD,0,noteE,0,noteE,0,noteE,0,noteE,0,
      noteD,0,noteD,0,noteE,0,noteD,0,noteC,0,0,0).notes;

  /** the tune that is currently shown and played */
  protected int tune = 3;
  
  /**
   * A constructor that initializes the piano roll with the tune of
   * 'Mary had a little lamb'
   */
  public PianoRoll(){
    this.mytunes.add(mytune1);
    this.mytunes.add(mytune2);
    this.mytunes.add(mytune3);
    this.initMelody(3);
  }
  
  /**
   * Initialize the melody with the given tune. Start with a couple of chords
   * to illustrate that this is possible.
   */
  protected void initMelody(int i){
    this.tune = i;
    this.melody.clear();
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(60, 64, 67));
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(60, 64, 67));
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(62, 67, 71));
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(62, 67, 71));
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(60, 64, 67));
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(60, 64, 67));
    this.melody.add(new RollChord());
    this.melody.add(new RollChord(72));
    
    // Mary had a little lamb...
    for (Note n: this.mytunes.get(i - 1)){
      this.melody.add(new RollChord(n));
    }
  }
  
  
  /** 
   * <p>Produce the image of the piano roll notes on a staff.</p>
   * <p>Include instructions for playing:</p>
   * <p>Arrow keys play, pause, stop, and reverse</p>
   * <p>Three number keys choose the melody</p>
   */
  public WorldImage pianoRollImage(){
    WorldImage base = new RectangleImage(new Posn(0, 0), 1, 1, Color.white);
    // The header
    base = base.overlayImages(
        new TextImage(new Posn(x + 10 + 40, y + 26), "The Piano Roll: ", 
            Color.red),
        new TextImage(new Posn(x + 10 + 170, y + 26), "Select tune 1, 2, or 3 ", 
            Color.blue),
        new TextImage(new Posn(x + 150 + 180, y + 26), "Current tune: " + 
        this.tune, Color.red),
    
        // The instructions for playing
        new TextImage(new Posn(x + 30 + 170, y + 63), 
        "up: play    down: play reverse    left: stop    right: pause", 
        Color.blue),
        new TextImage(new Posn(x + 20 + 170, y + 44), 
          "1: Frere Jacques    2: Humoresque    3: Mary had a little lamb",
          Color.black));
    
        // The note staff (five lines)
        base = this.staffImage(base);
        base = this.melodyImage(base);
    return base;
  }

  protected WorldImage melodyImage(WorldImage base){
    
    // Display the melody
    for (int index = 0; index < this.melody.size(); index++){
      base = this.melody.get(index).rollChordImage(base, index);
    }
    return base;
  }
  
  /**
   * <p>Draw there lines of staff on the given <code>Canvas</code></p>
   * 
   * <p>Top line offset from the nw corner: 60</p>
   * <p>One note takes up 8 pixels square (disk of radius 4)</p>
   * <p>Next line of staff is 55 pixels below the previous one</p>
   * 
   * @param c The <code>Canvas</code> where to draw
   */
  /*
  protected void drawStaff(Canvas c){
    // three rows of staff
    for (int row = 0; row < 3; row++){
      
      // find the base line for this staff
      int yline = y + 80 + row * 55;
      // draw the five lines
      for (int i = 0; i < 5; i++)
        c.drawLine(new Posn(x, yline + 8 * i), 
            new Posn(x + width, yline + 8 * i), Color.black);
    }
  }
  */

  /**
   * <p>Produce there lines of staff as an image</p>
   * 
   * <p>Top line offset from the nw corner: 60</p>
   * <p>One note takes up 8 pixels square (disk of radius 4)</p>
   * <p>Next line of staff is 55 pixels below the previous one</p>
   * 
   * @param c The <code>Canvas</code> where to draw
   */
  protected WorldImage staffImage(WorldImage base){
    // three rows of staff
    for (int row = 0; row < 3; row++){

      // find the base line for this staff
      int yline = y + 80 + row * 55;
      // draw the five lines
      for (int i = 0; i < 5; i++)
        base = base.overlayImages(new LineImage(new Posn(x, yline + 8 * i), 
            new Posn(x + width, yline + 8 * i), Color.black));
    }
    return base;
  }

  /**
   * Produce the next chord to be played by the piano roll
   * 
   * @return the <code>Chord</code> to be played next
   */
  public RollChord nextChord(){
    if (this.next != 0){
      // previously playing chord is off
      this.melody.get(this.current).notPlaying();
      
      // find the next playing chord
      this.current = 
        (this.current + this.next + this.melody.size()) % this.melody.size();
      
      // indicate that this chord is playing
      this.melody.get(this.current).playing();
      
      // provide this chord for the tick tune bucket
      return this.melody.get(this.current);
    }
    else
      return null;
  }

  /**
   * record the state change: the piano roll should play forward
   */
  public void playPianoRoll(){
    this.next = 1;
    this.isPlaying = true;
  }
  
  /**
   * record the state change: the piano roll should play in reverse
   */
  public void playReversePianoRoll(){
    this.next = -1;
    this.isPlaying = true;
  }
  
  /**
   * record the state change: the piano roll should stop playing and reset
   * to the start of the melody
   */
  public void stopPianoRoll(){
    // previously playing chord is off
    this.melody.get(this.current).notPlaying();
    
    this.next = 0;
    this.current = 0;
    this.isPlaying = false;
  }
  
  /**
   * <p>Record the state change: the piano roll should pause at the current note
   * and stop playing.</p>
   * <p>To restart at the current place the user should select either 'play'
   * or 'playReverse' --- the current direction of playing is not retained.</p>
   */
  public void pausePianoRoll(){
    this.next = 0;
    this.isPlaying = false;
  }
}

/**
 * A class to allow for the display of the current chord
 * 
 * @author Viera K. Proulx
 * @since 30 August 2010
 *
 */
class RollChord extends Chord{
  
  /** the limited list of pitches that can be displayed */
  protected static ArrayList<Integer> pitchlist = 
    new ArrayList<Integer>(Arrays.asList
      (60, 62, 64, 65, 67, 69, 71, 72,
       72, 74, 76, 77, 79, 81, 83, 84));
  
  /** show in black all notes */
  protected static Color rollColor = Color.black;
  
  /** show in red the currently playing chord */
  protected static Color playColor = Color.red;
  
  /** the current color to show for this chord */
  protected Color col = rollColor;
  
  /** the NW corner for the panel that shows the piano roll */
  protected static Posn nw = new Posn(0, 200);
  
  /**
   * Enabling the default constructor
   */
  public RollChord(){
    super();
  }
  
  /**
   * Enabling the constructor that consumes a list of <code>String</code>s
   * @param snotes the <code>String<code> that represent the notes 
   * that comprise this chord
   */
  public RollChord(String... snotes){
    super(snotes);
  }

  /**
   * Enabling the constructor that consumes a list of pitches
   * @param pitches the pitches for the notes that comprise this chord
   */
  public RollChord(int... pitches){
    super(pitches);
  }
  
  /**
   * Enabling the constructor that consumes a list of <code>Note</code>s
   * @param notes the notes that comprise this chord
  */
 public RollChord(Note... notes){
    super(notes);
  }
 
 /** a static instance of a silent chord */
 public static RollChord noplay = new RollChord();
  
 /** set the color of the chord to show to the playing color (red) */
  public void playing(){
    this.col = playColor;
  }

  /** set the color of the chord to show the non-playing color (black) */
  public void notPlaying(){
    this.col = rollColor;
  }
  
  /**
   * draw this chord on the given <code>Canvas</code>
   * at its place in the piano roll given by the <code>index</code>
   * 
   * @param c the given <code>Canvas</code>
   * @param index the given location of this chord in the piano roll
   */
  /*
  public void drawRollChord(Canvas c, int index){
    for (Note note: this.notes){
      this.drawNote(c, note.pitch, index);
    }
  }
  */

  
  /**
   * draw this chord on the given <code>Canvas</code>
   * at its place in the piano roll given by the <code>index</code>
   * 
   * @param c the given <code>Canvas</code>
   * @param index the given location of this chord in the piano roll
   */
  public WorldImage rollChordImage(WorldImage base, int index){
    for (Note note: this.notes){
      base = base.overlayImages(this.noteImage(note.pitch, index));
    }
    return base;
  }
  
  /**
   * <p>
   * <ul><li>There are 50 notes per line of staff for a total width of 400</li>
   * <li>The lower C (the lowest pitch shown) is at 200 + 9 * 8 + 2</li>
   * <li>9 the number of notes on the five staff line and in between</li>
   * <li>8 the height of each note</li>
   * <li>2 the additional offset for the center of the circle of 
   * radius 4</li></ul></p>
   * 
   * <p>Nothing is drawn if the pitch is other than chosen</p>
   * 
   * @param c the <code>Canvas</code> where to draw
   * @param pitch the pitch of this note (should be one of the available ones)
   * @param index the position of this chord in the melody
   */
  public WorldImage noteImage(int pitch, int index){
    int pitchIndex = pitchlist.indexOf(pitch);
    if (pitchIndex != -1){
      int row = index / 50;
      int base = 270 + row * 55;
      int x = 24 + 8 * (index % 50);
      int y = base - pitchIndex * 4;
      return new DiskImage(new Posn(x, y), 3, this.col);
    }
    else
      return new RectangleImage(new Posn(0, 0), 1, 1, Color.white);
  }
}