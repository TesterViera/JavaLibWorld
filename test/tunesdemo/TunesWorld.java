package tunesdemo;

import javalib.soundworld.*;
import javalib.worldimages.*;

/**
 * A comprehensive demo of the javalib.soundworld and javalib.tunes library.
 * 
 * @author Viera K. Proulx
 * @since 15 August 2012
 */

/**
 * A world that plays a piano keyboard, 
 * and plays a melody provided by a piano roll,
 * on the instrument selected by using mouse clicks.
 */
public class TunesWorld extends World{
  
  /** The piano keyboard that plays notes for the given key presses. */
  Keyboard kb = new Keyboard();
  
  /** The display of the list of available instruments that supports 
   * selection of the current instrument using a mouse click */
  InstrumentBoard ib = new InstrumentBoard();
  
  PianoRoll pr = new PianoRoll();
  
  /** the current instrument used by the keyboard and by the piano roll */
  int instrument = this.ib.current;
  
  /**
   * The constructor. All data is already defined.
   */
  TunesWorld (){
    super();
  }
  
  /**
   * On tick the piano roll plays the next chord 
   * - if it is in the playing state.
   */
  public void onTick(){
    if (this.pr.isPlaying)
      this.tickTunes.addChord(this.instrument, this.pr.nextChord());
  }
  
  /**
   * <p>There are two kinds of key events.<p>
   * <p>The two bottom rows of character keys represent two octaves of white 
   * keys on a piano and play the corresponding notes - using the currently
   * selected instrument.<p>
   * <p> The arrow keys are used to control the playing and stopping of the 
   * piano roll:<p>
   * <ul>
   * <li> right: play</li>
   * <li>left: play in reverse</li>
   * <li>up: stop, return to the start<li>
   * <li>down: pause at the current place</li><ul>
   */
  public void onKeyEvent(String ke){
    if (this.arrowKey(ke))
      this.controlPianoRoll(ke);
    else {
      if (this.numKey(ke)){
        this.initPianoRoll(ke);
      }
      else {
        int keyIndex = this.kb.keyIndex(ke);
        if (keyIndex != -1){
          this.keyTunes.addNote(this.instrument, this.kb.playNote(keyIndex));
        }}}
  }
  
  /**
   * determine whether the given <code>String</code> represents an arrow key.
   * 
   * @param ke the given <code>String</code>
   * @return true is the key is one of <em>up, down, left, right</em>
   */
  protected boolean arrowKey(String ke){
    return ke.equals("up") || 
           ke.equals("down") || 
           ke.equals("left") || 
           ke.equals("right"); 
  }
  
  /**
   * determine whether the given <code>String</code> represents a numeric key
   * between 1 and 3
   * 
   * @param ke the given <code>String</code>
   * @return true is the key is one of <em>1, 2, 3</em>
   */
  protected boolean numKey(String ke){
    return ke.equals("1") || 
           ke.equals("2") || 
           ke.equals("3"); 
  }
  
  /**
   * <p> The arrow keys are used to control the playing and stopping of the 
   * piano roll:<p>
   * <ul>
   * <li> right: play</li>
   * <li>left: play in reverse</li>
   * <li>up: stop, return to the start<li>
   * <li>down: pause at the current place</li><ul>
   * 
   * @param ke the key that has been pressed
   */
  protected void controlPianoRoll(String ke){
    if (ke.equals("up"))
      this.pr.playPianoRoll();
    else if (ke.equals("down"))
      this.pr.playReversePianoRoll();
    else if (ke.equals("left"))
      this.pr.stopPianoRoll();
    else // (ke.equals("right"))
      this.pr.pausePianoRoll();
  }
  
  /**
   * <p> The arrow keys are used to control the playing and stopping of the 
   * piano roll:<p>
   * <ul>
   * <li> right: play</li>
   * <li>left: play in reverse</li>
   * <li>up: stop, return to the start<li>
   * <li>down: pause at the current place</li><ul>
   * 
   * @param ke the key that has been pressed
   */
  protected void initPianoRoll(String ke){
    if (ke.equals("1"))
      this.pr.initMelody(1);
    else if (ke.equals("2"))
      this.pr.initMelody(2);
    else if (ke.equals("3"))
      this.pr.initMelody(3);
    else
      System.out.println("Nothing changed for ke " + ke);
  }
  
  /**
   * Stop playing the note that corresponds to the given released key
   * 
   * @param keyIndex the index for key that has been released
   */
  public void onKeyReleased(String ke){
    if (!arrowKey(ke) && !numKey(ke)){
      int keyIndex = this.kb.keyIndex(ke);
      if (keyIndex != -1){
        this.kb.stopNote(keyIndex);
      }
    }
  }
  
  /**
   * Mouse click in one of the selection boxes of the 
   * <code>InstrumentBoard</code> changes the current instrument 
   * to the selected one.
   * 
   * @param pos the mouse position when the mouse click registered
   */
  public void onMouseClicked(Posn pos){
    this.instrument = this.ib.selectInstrument(pos, this.instrument);
    this.instrument = this.ib.selectInstrument(pos, this.instrument);
    
    // program choice has been selected
    if (this.instrument == 17){
      this.programChange(this.ib.switchProgram());
      this.instrument = 0;
    }
  }
  
  /**
   * Draw the three components of the world: the keyboard, the instrument
   * selection board, and the piano roll player.
   */
  public WorldImage makeImage(){
    WorldImage image = 
    this.kb.keyboardImage().overlayImages(
    this.ib.instrumentBoardImage(),
    this.pr.pianoRollImage());
    return image;
  }
}