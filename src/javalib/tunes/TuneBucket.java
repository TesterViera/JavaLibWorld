package javalib.tunes;

import java.util.*;

/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * A tune bucket for carrying instruments and notes
 * to play with the tunes library.
 * 
 * @author Viera K. Proulx
 * @since 29 October 2009, 5 June 2010, 2 August 2010, 5 July 2011
 */
public class TuneBucket implements SoundConstants{
  
  /** 
   * <p>The list of tunes to play at the commencement of the next event.</p>
   * <p>There are exactly 16 tunes: one for each channel in the 
   * {link MusicBox MusicBox} associated with this 
   * {link TuneBucket TuneBucket}.</p>
   */
  public ArrayList<Tune> tunes;
  
  /** the music box that plays the tunes in this bucket */
  protected volatile MusicBox musicBox;
  
  /** the currently selected program for this <code>TuneBucket</code> */
  protected volatile int[] channels = new int[]{
      0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
  
  /** 
   * The constructor provides the available channels and invokes 
   * the appropriate program change for the {link MusicBox MusicBox}.
   * 
   * @param musicBox the {link MusicBox MusicBox} associated 
   *        with this {link TuneBucket TuneBucket}.
   * @param channels the channel selection for the desired program
   */
  public TuneBucket(MusicBox musicBox, int[] channels){
    this.tunes = new ArrayList<Tune>();
    this.musicBox = musicBox;
    this.musicBox.initChannels(channels);
    this.recordProgramChange(channels);
    this.initTunes();
  }
  
  /** 
   * The constructor that assumes the {link MusicBox MusicBox} has already 
   * set up its program.
   * @param musicBox the {link MusicBox MusicBox} associated 
   *        with this {link TuneBucket TuneBucket}.
   */
  public TuneBucket(MusicBox musicBox){
    this.tunes = new ArrayList<Tune>();
    this.musicBox = musicBox;
    this.initTunes();
    this.initChannels();
  }
  
  /** 
   * The constructor to use when the {link MusicBox MusicBox} is not available.
   * 
   * @param channels the channel selection for the desired program
   */
  public TuneBucket(int[] channels){
    this.tunes = new ArrayList<Tune>();
    this.musicBox = null;
    this.initTunes();
    this.recordProgramChange(channels);
  }
  
  /** 
   * The constructor to use when the {link MusicBox MusicBox} is not available.
   */
  public TuneBucket(){
    this.tunes = new ArrayList<Tune>();
    this.musicBox = null;
    this.initTunes();
    this.initChannels();
  }
  
  /**
   * record the fact that someone had changed the program for your MusicBox
   * (even if no MusicBox may be available).

   * @param channels the channel selection for the newly changed program
   */
  public void recordProgramChange(int[]channels){
    for (int i = 0; i < 16; i++)
      channels[i] = channels[i];
  }
  
  /**
   * Initialize tunes for all instruments to silent chords.
   */
  public void initTunes(){
    for (int i = 0; i < 16; i++){
      this.tunes.add(new Tune(i));
    }
  }
  
  /**
   * Initialize all instruments to the default program from the SoundConstants
   */
  public void initChannels(){
    for (int i = 0; i < 16; i++){
      this.channels[i] = instruments[i];
    }
  }
  
  /**
   * <p>Add the entire tune bucket to this one.</p> 
   * <p>Used 'onTick' to keep track of all notes currently playing
   * and those that started playing on this tick</p>
   * 
   * @param tb the tune bucket to add to this one
   */
  public void add(TuneBucket tb){
    for (int i = 0; i < 16; i++){
      this.tunes.get(i).addChord(tb.tunes.get(i).chord.copyChord());
    }
  }
  
  /** 
   * <p>Allow the user to add to the tune bucket 
   * a note to be played on the given instrument.</p>
   * <p>The note of the given pitch will play for one tick duration.</p>
   * 
   * @param channel the number assigned to our desired instrument
   * @param pitch the number assigned to the desired note
   */
  public void addNote(int channel, int pitch){    
    this.tunes.get(channel).addNote(new Note(pitch));
  }
  
  /** 
   * Allow the user to add to the tune bucket 
   * a note to be played on the given instrument
   * 
   * @param channel the number assigned to our desired instrument
   * @param note the desired note
   */
  public void addNote(int channel, Note note){    
    this.tunes.get(channel).addNote(new Note(note.snote));
  }
  
  /** 
   * Allow the user to add to the tune bucket 
   * a note to be played on the given instrument
   * 
   * @param channel the number assigned to our desired instrument
   * @param snote a <code>String</code> that represents the desired note
   */
  public void addNote(int channel, String snote){    
    this.tunes.get(channel).addNote(new Note(snote));
  }
  
  /** 
   * Allow the user to add to the tune bucket 
   * a chord to be played on the given instrument
   * 
   * @param channel the number assigned to our desired instrument
   * @param chord the chord to add to this tune bucket
   */
  public void addChord(int channel, Chord chord){    
    this.tunes.get(channel).addChord(chord.copy());
  }

  /** 
   * <p>Allow the user to add to the tune bucket 
   * a predefined <code>Iterable</code> 
   * collection of <code>Tune</code> objects.</p>
   * <p>For each channel add the chords from the given tune.</p>
   * 
   * @param tunes the <code>Iterable</code> 
   * collection of tune objects that represents 
   * our desired instruments and notes
   */
  public void addTunes(Iterable<Tune> tunes){
    for (Tune tune: tunes)
    this.tunes.get(tune.channel).addChord(tune.chord);
  }
  
  /** 
   * Allow the user to add to the tune bucket 
   * a predefined <code>Tune</code> object.
   * @param tune the tune object that represents our desired instrument and note
   */
  public void addTune(Tune tune){
    this.tunes.get(tune.channel).addChord(tune.chord);
  }
  
  /**
   * Stop playing the currently playing notes and empty the tune bucket.
   */
  public void clearBucket(){
    if (this.musicBox != null)
      this.musicBox.playOff(this.tunes);
    this.clearTunes();
  }


  /**
   * Clear all chords from the 16 tunes
   */
  public void clearTunes(){
    for(Tune tune: this.tunes)
      tune.clearChord();
  }
  
  /**
   * Remove all silent notes from this tune.
   */
  public void removeSilent(){
    for(Tune tune: this.tunes)
      tune.removeSilent();
  }
  
  /**
   * Stop playing the notes whose time has expired and remove them from 
   * the bucket.
   */
  public void nextBeat(){

    for (Tune tune: this.tunes){
      // get the notes to stop playing and advance timer on all 
      // and remove the silent notes from the tune chord
      Chord stopPlay = tune.chord.nextBeat();
      
      if (this.musicBox != null)
        // stop playing notes that are done
        this.musicBox.stopTune(new Tune(tune.channel, stopPlay));
    } 
  }

  /**
   * Add 1 to the duration of every note in the bucket.
   */
  public void skipBeat(){

    for (Tune tune: this.tunes){
      // advance timer on all notes
      tune.chord.skipBeat();
    } 
  }
  
  /**  
   * Start playing all tunes in the bucket 
   * on the appropriate <code>MIDI</code> channels.
   * If no <code>MusicBox</code> available, advance all chords 
   * (and consequently remove silent notes from the chords).
   */
  public void playTunes(){
    if (this.musicBox != null)
      for (Tune tune: this.tunes){
        this.musicBox.playTune(tune);
        // remove notes no longer playing
        tune.removeSilent();
      }
    else
      // if no MusicBox, emulate advancing the tunes
      for (Tune tune: this.tunes){
        tune.chord.nextBeat();
      }
  }
  
  /**
   * Make a new copy of this tune bucket
   */
  public TuneBucket copy(){
    TuneBucket newCopy = new TuneBucket(this.musicBox);
    for (Tune t: this.tunes){
      newCopy.addChord(t.channel, t.chord.copy());
    }
    return newCopy;
  }
  
  /**
   * Method to support testing: report the current size of the tune bucket
   * 
   * @return the current size of the tune bucket, i.e. the number of notes
   * for all instruments 
   */
  public int bucketSize(){
    int count = 0;
    for (Tune t: this.tunes){
      count = count + t.size();
    }
    return count;
  }
  
  /**
   * Method to support testing: report whether the tune bucket
   * contains the given channel and note tune
   * @param channel the channel that should be playing
   * @param note the note the channel should be playing
   * @return true if the given channel is playing the given note
   */
  public boolean contains(int channel, Note note){
    for (Tune t : this.tunes){
      if (t.channel == channel && t.chord.containsNote(note))
        return true;
    }
    return false;
  }  
  
  /**
   * Method to support testing: report whether the tune bucket
   * contains the given channel and note tune
   * @param channel the channel that should be playing
   * @param pitch the pitch the channel should be playing
   * @return true if the given channel is playing the given note
   */
  public boolean contains(int channel, int pitch){
    for (Tune t : this.tunes){
      if (t.channel == channel && t.chord.containsNote(pitch))
        return true;
    }
    return false;
  }
  
  /**
   * Produce a <code>String</code> representation
   * of all tunes in this tune bucket.
   * @return a readable list of tunes in this bucket
   */
  public String toXString(){
    String result = "Tune bucket: size = " + this.bucketSize() + "\n";
    Tune t;
    String instrument;
    for (int i = 0; i < 16; i++){
      t = this.tunes.get(i);
      
      if (this.musicBox != null)
        instrument = instrumentNames[this.musicBox.program[t.channel]];
      else
        instrument = instrumentNames[this.channels[i]];
      
      //String tune = t.toString(instrument);
      //if (!tune.equals(""))
      result = result + "[" + i + "]: " + t.toString(instrument) + "\n";
    }
    return result + "\n";
  }  
  
  /**
   * Produce a <code>String</code> representation
   * of all tunes in this tune bucket.
   * @return a readable list of tunes in this bucket
   */
  public String toString(){
    return this.toIndentedString("");
  }
  
  /**
   * Produce a <code>String</code> representation
   * of all tunes in this tune bucket.
   * @return a readable list of tunes in this bucket
   */
  public String toIndentedString(String indent){
    String result = "Tune bucket: \n" + 
                    indent + "size = " + this.bucketSize() + "\n";
    Tune t;
    String instrument;
    for (int i = 0; i < 16; i++){
      t = this.tunes.get(i);


      if (this.musicBox != null){
        instrument = instrumentNames[this.musicBox.program[t.channel]];
      }
      else{
        instrument = instrumentNames[this.channels[i]];
      }

      result = 
          result + indent + "  [" + i + "]: " + t.toString(instrument) + "\n";
    }
    return result + "\n";
  }
  
  /**
   * Is this <code>TuneBucket</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o == null)
      return false;
    
    // wrong data type
    if (!(o instanceof TuneBucket))
      return false;

    TuneBucket that = (TuneBucket)o;

    // first make sure that both bucket have the same number of tunes
    // and that their music boxes match
    if (this.tunes.size() != that.tunes.size())
      return false;
    
    if (this.musicBox != null && that.musicBox != null &&
        !this.musicBox.equals(that.musicBox))
      return false;

    // match each tune and each program entry
    for (int i = 0; i < 16; i++){
      
      // match the current program for this channel
      if (this.channels[i] != that.channels[i])
        return false;
      
      // match the tunes for this channel
      if (!this.tunes.get(i).equals(that.tunes.get(i)))
        return false;     
    }  

    // OK - all is well
    return true;
  }

  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return this.channels.hashCode() + this.tunes.hashCode();
  }
}
