package tunesdemoapplet;

import javalib.worldimages.*;
import javalib.tunes.*;


import java.awt.Color;
import java.util.*;

/**
 * A comprehensive demo of the javalib.worldimages and javalib.tunes package: 
 * An implementation of a keyboard demo. 
 * Two rows of the computer keyboard are designed
 * to play the white keys of the piano keyboard.
 * 
 * @author Viera K. Proulx
 * @since 15 August 2012
 */
public class Keyboard{
  
  /** the list of labels for the two rows of piano keyboard keys */
  protected ArrayList<String> keystrings = new ArrayList<String>(Arrays.asList
      ("a", "s", "d", "f", "g", "h", "j", "k",
       "z", "x", "c", "v", "b", "n", "m", ","));
       

  /** the list of notes played for the two rows of piano keyboard keys */
  protected ArrayList<String> keylabels = new ArrayList<String>(Arrays.asList
    ("C", "D", "E", "F", "G", "A", "B", "C",
     "C", "D", "E", "F", "G", "A", "B", "C"));
     
  /** the list of pitches for the two rows of piano keyboard keys */
  protected ArrayList<Integer> pitchlist = new ArrayList<Integer>(Arrays.asList
    (60,  62,  64,  65,  67,  69,  71,  72,
     72,  74,  76,  77,  79,  81,  83,  84));
  
  /** the keys that can be played on this keyboard */
  protected ArrayList<Key> keys = new ArrayList<Key>();
  
  /** the NW corners of the (inactive) black keys */
  protected ArrayList<Posn> blackKeys = new ArrayList<Posn>();
  
  /** the NW corners of the (inactive) black keys */
  protected ArrayList<Posn> blackKeysCtr = new ArrayList<Posn>();
  
  /**
   * the constructor: initializes the keyboard key positions, labels, 
   * note names to show, notes to play, and positions of (inactive) black keys
   */
  public Keyboard(){
    this.initKeyboard();
  }
  

  /**
   * initializer: for the keyboard key positions, labels, note names to show, 
   * notes to play, and positions of (inactive) black keys
   */
  protected void initKeyboard(){
    // top left corner
    int x = 20;
    int y = 70;
    int octave = 8;
    
    // the fourth octave
    for (int i = 0; i < octave; i++){
      this.keys.add(new Key(new Posn(x + i * 23, y),
                    this.keystrings.get(i),
                    this.keylabels.get(i),
                    this.pitchlist.get(i)));
    }
    this.blackKeys.add(new Posn(x + 13, y));
    this.blackKeys.add(new Posn(23 + x + 13, y));
    this.blackKeys.add(new Posn(3 * 23 + x + 13, y));
    this.blackKeys.add(new Posn(4 * 23 + x + 13, y));
    this.blackKeys.add(new Posn(5 * 23 + x + 13, y));
    
    this.blackKeysCtr.add(new Posn(x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(23 + x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(3 * 23 + x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(4 * 23 + x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(5 * 23 + x + 13 + 11, y + 13));
    
    // the fifth octave
    x = x + 197;
    for (int i = 0; i < octave; i++){
      this.keys.add(new Key(new Posn(x + i * 23, y),
                    this.keystrings.get(i + octave),
                    this.keylabels.get(i + octave),
                    this.pitchlist.get(i + octave)));
    }

    this.blackKeys.add(new Posn(x + 13, y));
    this.blackKeys.add(new Posn(23 + x + 13, y));
    this.blackKeys.add(new Posn(3 * 23 + x + 13, y));
    this.blackKeys.add(new Posn(4 * 23 + x + 13, y));
    this.blackKeys.add(new Posn(5 * 23 + x + 13, y));

    this.blackKeysCtr.add(new Posn(x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(23 + x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(3 * 23 + x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(4 * 23 + x + 13 + 11, y + 13));
    this.blackKeysCtr.add(new Posn(5 * 23 + x + 13 + 11, y + 13));
  }
  
  /** 
   * Find the index for the piano key that corresponds to the given key event.
   * Produce -1 if the key event does not represent a piano key to be played.
   * 
   * @param ke
   * @return the index for the key to be played
   */
  public int keyIndex(String ke){
    int index = -1;
    for (int i = 0; i < this.keystrings.size(); i++){
      if (this.keystrings.get(i).equals(ke))
        index = i;
    }
    return index;
  }
  
  /**
   * Produce the note to be played in response to the given key event.
   * Assumes a valid key event
   * 
   * @param ke the key event to respond to
   * @return the note to be played or <code>null</code>
   */
  public Note playNote(int keyIndex){
    Key key = this.keys.get(keyIndex);
    key.playKey();
      return new Note(key.pitch, 16);
  }
  
  /**
   * Stop playing the note activated by the given key press. 
   * Ignore if the key event is not applicable.
   * 
   * @param ke the given key press
   */
  public void stopNote(int keyIndex){
    Key key = this.keys.get(keyIndex);
    key.stopPlayKey();
  }

  /**
   * <p>Produce the image of the keyboard panel:</p>
   * <p>draw the header and the instructions for playing</p>
   * <p>draw the white keys on this keyboard</p>
   * <p>draw the (inactive) black keys</p>
   */
  public WorldImage keyboardImage(){
    String label = "Play the Keyboard using the shown keys";
    String rules = "a plays C in 4th octave, x plays D in 5th octave, ...";
    return 
    new TextImage(new Posn(25 + label.length()*3, 26), label, Color.red).
    overlayImages(
        new TextImage(new Posn(25 + rules.length()*3, 41), rules, Color.blue),
        new TextImage(new Posn(20 + 100, 61), "4th octave", Color.red),
        new TextImage(new Posn(20 + 297, 61), "5th octave", Color.red),
        this.allKeyImages());
  }

  /**
   * produce the image of the white and black keys on the piano keyboard
   * @return the image of the white and black keys 
   */
  protected WorldImage allKeyImages(){
    WorldImage base = new RectangleImage(new Posn(0, 0), 1, 1, Color.white);
    for (Key k: this.keys)
      base = base.overlayImages(k.keyImage());

    for (Posn pos: this.blackKeysCtr)
      base = base.overlayImages(new RectangleImage(pos, 10, 24, Color.black));
      
    return base;
  }  
}

/**
 * A class that represents one key on a piano keyboard.
 * It records its position in the Canvas, the pitch it plays,
 * the key that activates it, and the color (that changes when the key is 
 * playing).
 * 
 * @author Viera K. Proulx
 * @since 15 August 2012
 *
 */
class Key{
  
  /** the position of the NW corner of the displayed piano key */
  protected Posn pos;
  /** the position of the center of the inner white rectangle */
  protected Posn inPos;
  
  /** the position of the label that shows what note this piano key plays */
  protected Posn labelPos;
  
  /** the position of the String that shows which key event activated this 
   * piano key */
  protected Posn kePos;
  
  /** the position of the center of the label 
   * that shows what note this piano key plays */
  protected Posn labelPosCtr;
  
  /** the position of the enter of the String 
   * that shows which key event activated this piano key */
  protected Posn kePosCtr;
  
  /** the position of the center of the displayed piano key */
  protected Posn posCtr;
  
  
  /** the key event that activates this piano key play */
  public String ke;
  
  /** the name of the note that is played by this piano key */
  public String label;
  
  /** the pitch of the note that is played by this piano key */
  public int pitch;
  
  /** the color of this piano key's label - red when the piano key plays */
  protected Color col;
  
  /**
   * the constructor that initializes a non-playing piano key 
   * 
   * @param pos the position of the NW corner of this piano key
   * @param ke the key event that plays this piano key
   * @param label the note name of the note this piano key plays
   * @param pitch the pitch of the note this piano key plays
   */
  public Key(Posn pos, String ke, String label, int pitch){
    this.pos = pos;
    this.ke = ke;
    this.label = label;
    this.pitch = pitch;
    this.col = Color.black;
    this.posCtr = new Posn(this.pos.x + 11, this.pos.y + 30);
    // inset by one to get a black border
    this.inPos = new Posn(this.pos.x + 1, this.pos.y + 1);
    this.labelPos = new Posn(this.pos.x + 5, this.pos.y + 40);
    this.kePos = new Posn(this.pos.x + 5, this.pos.y + 52);
    this.labelPosCtr = new Posn(this.pos.x + 11, this.pos.y + 36);
    this.kePosCtr = new Posn(this.pos.x + 11, this.pos.y + 48);
  }
  
  /**
   * produce the image of this piano key
   */
  public WorldImage keyImage(){
    return new RectangleImage(this.posCtr, 23, 59, Color.black).overlayImages(
           new RectangleImage(this.posCtr, 21, 57, Color.lightGray),
           new TextImage(this.labelPosCtr, this.label, this.col),
           new TextImage(this.kePosCtr, this.ke, Color.darkGray));
  }
  
  /**
   * record state change: the piano key is being played
   */
  public void playKey(){
    this.col = Color.red;
  }
  
  /**
   * record state change: the piano key is no longer playing
   */
  public void stopPlayKey(){
    this.col = Color.black;
  }
}