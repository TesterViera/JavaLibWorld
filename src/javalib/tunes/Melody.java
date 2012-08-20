package javalib.tunes;

import java.util.*;

/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 *
 * Class that represents a collection of chords
 * and includes a method to iterate over them
 * @author Viera K. Proulx
 * @since 22 June 2010
 *
 */
public class Melody implements SoundConstants{
  
  /** a list of chords to play */
  ArrayList<Chord> chords;
  
  /** index for the current chord to play */
  int current;
  
  /** the number of chords in this melody */
  int size;
  
  /**
   * Construct a new empty melody
   */
  Melody(){
    this.chords = new ArrayList<Chord>();
    this.current = 0;
    this.size = this.chords.size();
  }
  
  /**
   * Construct a melody from the given list of chords.
   * @param chords the given list of chords
   */
  Melody(ArrayList<Chord> chords){
    this.chords = chords;
    this.current = 0;
    this.size = this.chords.size();
  }
  
  /**
   * Initialize the melody from a monotone sequence of notes
   * @param notes the notes to play
   */
  public void initMelody(ArrayList<Note> notes){
    this.chords = new ArrayList<Chord>();
    this.current = 0;
    for(Note note: notes)
      this.chords.add(new Chord(note));
    this.size = this.chords.size();
  }
  
  /**
   * produce the next chord to play
   * @return the next chord to play
   */
  Chord next(){
    if (this.size == 0)
      throw new RuntimeException("No notes to play");
    else{
      Chord tmp = this.chords.get(this.current);
      this.current = (this.current + 1) % this.size;
      return tmp;
    }
  }
  
  /**
   * Is this <code>Melody</code> same as the given object?
   */
  public boolean equals(Object o){
    if (!(o instanceof Melody))
      return false;
    Melody that = (Melody)o;

    // now match the basic values
    if (this.size != that.size)
      return false;
    if (this.current != that.current)
      return false;

    // if all is well, compare the chords 
    for (int i = 0; i < 16; i++){
      if (!(this.chords.get(i).equals(that.chords.get(i))))
        return false;
    }

    // all is well
    return true;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    int hash = this.size * this.current;
    for (Chord c : chords)
      hash = hash + c.hashCode();
    return hash;
  }
  
}
