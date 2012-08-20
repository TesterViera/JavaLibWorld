 package javalib.tunes;
import java.util.*;
 
/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 *
 *  <p>A class that represents one tune (a chord) to be played on the given
 * channel.</p>
 * <p>The channel value is forced to be between 0 and 15, as we support only 
 * 16 channels.</p>
 * 
 * @author Viera K. Proulx
 * @since 29 October 2009, 9 June 2010
 *
 */
public class Tune implements SoundConstants{
  protected int channel;
  protected Chord chord;
  
  /**
   * The constructor makes sure the channel value is in the range [0, 15].
   * 
   * @param channel the channel for this tune
   */
  public Tune(int channel, Chord chord){
    // verify that we have a correct channel number
    if (channel < 0)
      channel = 0;
    else if (channel > 15)
      channel = 15;
    this.channel = channel;
    
    this.chord = chord;
  }
  
  /**
   * Accessor for the channel field.
   * 
   * @return the current channel assigned to this {link Tune}
   */
  public int getChannel(){
	  return this.channel;
  }
  
  /**
   * Accessor for the chord field.
   * 
   * @return the current chord assigned to this {link Tune}
   */
  public Chord getChord(){
	  return this.chord;
  }
  
  /**
   * The constructor makes sure the channel value is in the range [0, 15].
   * 
   * @param channel the desired channel
   */
  public Tune(int channel){
    this(channel, new Chord());
  }
  
  /**
   * The constructor makes sure the channel value is in the range [0, 15].
   * 
   * @param channel the desired channel
   * @param note the desired note
   */
  public Tune(int channel, Note note){
    this(channel);
    this.chord.addNote(note);
  }
  
  /**
   * Add a note to this tune: make a new instance of the note
   * so when it plays, the original is untouched.
   * 
   * @param note the note to add
   */
  public void addNote(Note note){
    this.chord.addNote(new Note(note.snote));
  }
  
  /**
   * Add a chord to this tune
   * @param myChord the chord to add
   */
  public void addChord(Chord myChord){
    for (Note note: myChord.notes)
      this.chord.addNote(note);
  }
  
  /**
   * Produce a readable representation of this tune
   * or an empty <code>String</code> if no chords are present
   * @return a <code>String</code> that represents this tune
   */
  public String toString(){
    return this.toIndentedString("");
    /*
    if (!this.chord.notes.isEmpty()){
      String header = "Channel: " + this.channel + "\n";
      String notes = "";
      for (Note n: this.chord.notes){
        notes = notes + "  note: " + n.snote + " pitch: " + n.pitch + 
            ", duration: " + n.duration + "\n";
      }
      return header + notes;
    }
    else
      return "";
      */
  }
  
  /**
   * Produce a readable representation of this tune
   * or an empty <code>String</code> if no chords are present
   * @return a <code>String</code> that represents this tune
   */
  public String toIndentedString(String indent){
    if (!this.chord.notes.isEmpty()){
      String header = "Channel: " + this.channel + "\n";
      String notes = "";
      for (Note n: this.chord.notes){
        notes = notes + indent + "  note: " + n.snote + " pitch: " + n.pitch + 
            ", duration: " + n.duration + "\n";
      }
      return header + notes;
    }
    else
      return "Channel: " + this.channel + "\n";
  }
  
  /**
   * Produce a readable representation of this tune
   * or an empty <code>String</code> if no chords are present
   * 
   * @param instrument the name of the instrument assigned to the channel
   * played by this tune
   * @return a <code>String</code> that represents this tune
   */
  public String toString(String instrument){
    String tuneString = instrument + "(";

    for (Note n: this.chord.notes){
      tuneString = tuneString + n.snote + ", ";
    }
    if (this.chord.notes.size() == 0)
      return tuneString + ")";
    else
     return tuneString.substring(0, tuneString.length()-2) + ")";
  }
  
  /**
   * Has this tune finished playing?
   * @return true if there are no notes in the chord, or all have 
   * duration 0 or less
   */
  public boolean isSilent(){
    return this.chord.isSilent();
  }
  
  /**
   * Remove all silent notes from this tune.
   */
  public void removeSilent(){
    this.chord.removeSilent();
  }
  
  /**
   * Remove all notes from this tune.
   */
  public void clearChord(){
    this.chord = new Chord();
    //this.chord.notes.clear();
  }
  
  /** 
   * number of notes in this tune 
   **/
  public int size(){
    return this.chord.size();
  }
  
  /** does this tune contain the given note? */
  public boolean containsNote(Note n){
    return this.chord.containsNote(n);
  }
  
  /**
   * Is this <code>Tune</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof Tune){
      Tune that = (Tune)o;
      if (this.channel == that.channel)
        return this.chord.equals(that.chord);
      else
        return false;
    }
    else
      return false;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return this.channel + this.chord.hashCode();
  }
}