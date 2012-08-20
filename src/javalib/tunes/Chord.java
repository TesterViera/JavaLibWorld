package javalib.tunes;

import java.util.*;

/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <p>A class to represent a collection of notes to be played
 * on one instrument at the same time.</p>
 * <p>Individual notes do not need to have the same duration.</p>
 * 
 * @author Viera K. Proulx
 * @since 8 June 2010
 */
public class Chord{
  
  /** the list of notes in this chord */
  public ArrayList<Note> notes = new ArrayList<Note>();
  
  /**
   * The default constructor
   */
  public Chord(){}
  
  /**
   * A constructor that creates a <code>Chord</code> from 
   * a sequence of <code>String</code>s that represent
   * notes to include in this chord.
   */
  public Chord(String... snotes){
    for (String s: snotes){
      this.addNote(s);
    }
  }
  
  /**
   * A constructor that creates a <code>Chord</code> from 
   * a sequence of <code>int</code>s that represent
   * notes to include in this chord.
   */
  public Chord(int... pitches){
    for (int pitch: pitches){
      if (pitch == 0)
        this.addNote(pitch, 0);
      else
        this.addNote(pitch, 1);
    }
  }
  
  /**
   * A constructor that creates a <code>Chord</code> from 
   * a sequence of <code>Note</code>s to include in this chord.
   */
  public Chord(Note... notes){
    for (Note n: notes){
      this.addNote(n);
    }
  }
  
  /**
   * Make a deep copy of this chord, so we can mutate the note durations as 
   * the chord plays out.
   * 
   * @return a copy of this chord
   */
  public Chord copy(){
    Chord replica = new Chord();
    for (Note n: this.notes){
      replica.notes.add(new Note(n.snote));
    }
    return replica;
  }
  
  /** add a note to this chord */
  public void addNote(Note n){
    this.notes.add(n);
  }
  
  /** add a note to this chord, by converting its String representation*/
  public void addNote(String snote){
    this.notes.add(new Note(snote));
  }
  
  /** add a note to this chord, by converting its 
    * pitch and duration representation*/
  public void addNote(int pitch, int duration){
    this.notes.add(new Note(pitch, duration));
  }
  
  /**
   * EFFECT: reduce the duration of all notes in this chord by one and 
   * remove all silent notes.
   * 
   * Produce the chord that represents the list of notes
   * that have stopped playing at the end of this beat.
   * 
   * @return Chord of notes that should stop playing
   */
  public Chord nextBeat(){
    Chord ch = new Chord();
    for (Note n: this.notes){
      n.nextBeat();
      if (n.duration <= 0)
        ch.addNote(n);
    }
    this.removeSilent();
    return ch;
  }
  
  /**
   * <p>EFFECT: increase the duration of all notes in this chord by one.</p>
   * 
   * <p>Produce this modified chord.</p>
   * 
   * @return this chord after the note durations have been increased
   */
  public Chord skipBeat(){
    for (Note n: this.notes){
      n.skipBeat();
    }
    return this;
  }
  
  /**
   * Have all notes in this chord finish playing?
   * @return true if there are no notes, or all have duration 0 or less
   */
  public boolean isSilent(){
    for (Note n: this.notes){
      if (n.duration > 0)
        return false;
    }
    return true;
  }
  
  /**
   * Remove all silent notes from this chord.
   */
  public void removeSilent(){
    ArrayList<Note> currentNotes = new ArrayList<Note>();
    for(Note n: this.notes){
      if (!n.isSilent())
        currentNotes.add(n);
    }
    this.notes = currentNotes;
  }
  
  /**
   * Make a new copy of this chord - with new notes
   * 
   * @return new chord with the same note values, but distinct instances
   */
  public Chord copyChord(){
	  Chord result = new Chord();
	  result.notes.clear();
	  for (Note n: this.notes){
		  result.addNote(new Note(n.snote));
	  }
	  return result;
  }
  
  
  /** a singleton chord representing silence */
  public static Chord noplay = new Chord();
  
  /** number of notes in this chord */
  public int size(){
    return this.notes.size();
  }
  
  /** does this chord contain the given note?
   * @param n the given note
   * @return true if this chord contains the given note
   */
  public boolean containsNote(Note n){
    for (Note note: this.notes){
      if (note.sameNote(n))
      return true;
    }
    return false;
  }
  
  /** does this chord contain the note with the given pitch?
   * @param pitch the given pitch
   * @return true if this chord contains the given note
   */
  public boolean containsNote(int pitch){
    for (Note note: this.notes){
      if (note.pitch == pitch)
      return true;
    }
    return false;
  }
  
  /**
   * Is this <code>Chord</code> the same as the given one?
   * They must contain the same notes, but not necessarily in the same order.
   * If some notes are repeated, it does not matter.
   * (It could be that this chord has AAD notes and that chord has ADD notes, 
   * and we still think they are the same).
   * 
   * @param that the <code> Chord to compare this one with
   * @return true, if both chord have the same number of <code>Note</code>s, 
   * and every note that appears in one of them also appears in the other 
   * <code>Chord</code>
   */
  public boolean sameChord(Chord that){
    if (this.notes.size() != that.notes.size())
      return false;
    for (Note n : this.notes)
      if (!that.notes.contains(n))
        return false;
    for (Note n : that.notes)
      if (!this.notes.contains(n))
        return false;
    return true;
  }
  
  /**
   * Produce a <code>String</code> that represents this <code>Chord</code>
   * indented by the given <code>String</code>
   * 
   * @param indent the <code>String</code> of desired number of spaces
   * @return properly formatted representation of this <code>Chord</code>
   */
  public String toIndentedString(String indent){
    String result = "new Chord(";
    for (Note n: this.notes)
      result = result + "\n" + indent + n.toIndentedString(indent + "  ") + ",";
    result = result.substring(0, result.length()-2);
    return result + ")";
  }
  
  /**
   * Produce a <code>String</code> that represents this <code>Chord</code>
   * 
   * @return properly formatted representation of this <code>Chord</code>
   */
  public String toString(){
    return this.toIndentedString("");
  }
 
  /**
   * Produce a one line representation of the notes in this <code>Chord</code>
   * @return a simple <code>String</code> representation of this chord
   */
  public String toSimpleString(){
    String result = "new Chord(";
    for (Note n: this.notes)
      result = result + n.snote + ", ";
    result = result.substring(0, result.length()-2);
    return result + ")";
  }
  
  /**
   * Is this <code>Chord</code> same as the given object?
   */
  public boolean equals(Object o){
    if (!(o instanceof Chord))
      return false;

    Chord that = (Chord)o;

    // first make sure both chords have the same number of notes
    if (this.notes.size() != that.notes.size())
      return false;

    // make a clone of this chord's notes
    ArrayList<Note> notesClone = (ArrayList<Note>)this.notes.clone();

    // for each matched note
    for (Note thatNote : that.notes){

      // no match -> false
      if (!this.notes.contains(thatNote))
        return false;
      else
        // match, so remove the matched note from the clone
        // if not there, the parities were wrong
        if (!(notesClone.remove(thatNote)))
          return false;
    }

    // end of the loop - all notes were removed
    // this should never happen - but double check
    if (notesClone.size() != 0)
      return false;

    // OK - all is well
    return true;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    int hash = 42;
    for (Note thisNote : this.notes)
      hash = hash + thisNote.hashCode();
    return hash;
  }
}