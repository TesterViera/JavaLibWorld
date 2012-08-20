package javalib.tunes;

import java.lang.reflect.Array;
import java.util.*;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;


/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <p>A class that initializes the synthesizer and installs the 
 * desired selection of instruments for the 16 available channels.</p>
 * 
 * <p>A default selection of instruments is defined in the 
 * <code>SoundConstants</code> interface.
 * 
 * @author Viera K. Proulx
 * @since 8 June 2010, 27 July 2010, 5 July 2011
 */
public class MusicBox implements SoundConstants{
  
  /** the MIDI synthesizer that plays the notes */
  private Synthesizer synth; 
  
  /** MIDI channels that the synthesizer needs */
  private MidiChannel[] channels;
  
  /** remember the program settings if no MIDI is present */
  protected int[] program = new int[16];
  
  /** Synthesizer status */
  public static boolean SYNTH_READY = false;
  
  /**
   * The default constructor that initializes the MIDI synthesizer
   * and sets up the default program for the instruments.
   */
  public MusicBox(){
    initMusic();
    initChannels();
  }  

  /**
   * The constructor that initializes the MIDI synthesizer
   * and sets up the desired program for the instruments.
   * 
   * @param instruments the desired instrument assignment 
   * to the 16 MIDI channels.
   */
  public MusicBox(int[] instruments){
    initMusic();
    initChannels(instruments);
  }
  
  
  /**
   * <p>Initialize the synthesizer for playing the sounds from the tune bucket </P>
   */
  public void initMusic(){
      try{
        this.synth = MidiSystem.getSynthesizer();
        this.synth.open();
        
        this.synth.loadAllInstruments(this.synth.getDefaultSoundbank());

        this.channels = this.synth.getChannels();
        
        this.initChannels();       
        
        SYNTH_READY = true;
      } 
      catch(javax.sound.midi.MidiUnavailableException e){
        System.out.println("MidiUnavailableException " +
            e.getMessage());
      }
      catch(RuntimeException e){
        System.out.println("Unable to set up MIDI: \n" + 
            e.getMessage());
      }
    }
  
  /**
   * Initialize the program to the default set of instruments
   * defined in the <code>interface SoundConstants</code>.
   */
  public void initChannels(){
    if (SYNTH_READY)
      for (int i = 0; i < Array.getLength(this.channels); i++){
        if (this.channels[i] != null)
          this.channels[i].programChange(instruments[i]);
        // and record the program even if no channel is available
        this.program[i] = instruments[i];
      }  
    else
      // record the program if no MIDI is present
      for(int i = 0; i < 16; i++){
        this.program[i] = instruments[i];
      }
  }
  
  /**
   * Initialize the program to the default set of instruments
   * defined in the <code>interface SoundConstants</code>.
   * 
   * @param myInstruments a list of instruments to assign the the 16 channels
   */
  public void initChannels(int[] myInstruments){
    if (SYNTH_READY)
      for (int i = 0; i < Array.getLength(this.channels); i++){
        if (this.channels[i] != null)
          this.channels[i].programChange(myInstruments[i]);
        // and record the program even if no channel is available
        this.program[i] = myInstruments[i];
      }
    else
      // record the program if no MIDI is present
      for(int i = 0; i < 16; i++){
        this.program[i] = myInstruments[i];
      }
  }
  
  /**
   * Produce the instrument currently assigned to the given channel.
   * Produce the assigned instrument if channel is null or MIDI is not present.
   * 
   * @param channel the given channel number
   * @return the instrument number assigned to the given channel,
   * or if no MIDI is present or the channel is not available,
   * return the instrument that should have been assigned to the given channel
   */
  public int getProgram(int channel){
    if (SYNTH_READY && this.channels[channel] != null)
      return this.channels[channel].getProgram();
    else
      return this.program[channel];
  }
  
  /**
   * Play all tunes in the given list of <code>Tune</code>s.
   * @param tunes the given tunes
   */
  public void playOn(ArrayList<Tune> tunes){
    for (Tune tune: tunes){
      this.playTune(tune);
    }
  }
  
  /**
   * Play the given tune on the channel assigned to it.
   * @param tune the tune to play
   */
  public void playTune(Tune tune){
    
      for (Note n: tune.chord.notes){
        if (!n.isSilent()){
          if (SYNTH_READY && this.channels[tune.channel] != null)
            this.channels[tune.channel].noteOn(n.pitch, NOTE_VELOCITY);
          n.nextBeat();
          //System.out.println(n.snote);
        }
      }
      tune.removeSilent();    
  }
  
  /**
   * Stop playing all tunes in the given list of <code>Tune</code>s.
   * @param tunes the given tunes
   */
  public void playOff(ArrayList<Tune> tunes){
    for (Tune tune: tunes){
      this.stopTune(tune);
    }
  }  
  
  /** 
   * Pause at the start when building GUIs and installing listeners
   * and at the end so the last tunes are heard
   */
  public void sleepSome(int miliseconds){
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
   * Stop playing the given tune on the channel assigned to it.
   * @param tune the tune to play
   */
  public void stopTune(Tune tune){
    for (Note n: tune.chord.notes){
      if (SYNTH_READY && this.channels[tune.channel] != null)
        this.channels[tune.channel].noteOff(n.pitch, NOTE_VELOCITY);
    }
  }
  
  public boolean ready(){
	  return SYNTH_READY;
  }
  
  /**
   * Check if this <code>MusicBox</code> plays the same instruments
   * as the given <code>MusicBox</code>
   * @param that the given <code>MusicBox</code>
   * @return true if the two the given <code>MusicBox</code>-es have the same 
   * program
   */
  public boolean same(MusicBox that){
    for (int i = 0; i < 16; i++){
      if (this.program[i] != that.program[i])
        return false;
    }
    return true;
  }
  
  /**
   * Produce a <code>String</code> representation
   * of this music box.
   * @return a readable channels and the instruments assigned to them
   */
  public String toString(){
    return this.toIndentedString("");
    /*
    String result = "MusicBox: \n";
    for (int i = 0; i < 16; i++){
      result = result + instrumentNames[this.program[i]]+ "\n";
      //result = result + "i: " + i + " program[i]: " + this.program[i] +
      //    " channels[i].getProgram(): " + channels[i].getProgram() +
      //    "this.getProgram(i): " + this.getProgram(i)+ "\n";
    }
    return result;
    */
  }
  
  /**
   * Produce a <code>String</code> representation
   * of this music box with the given indentation level
   * 
   * @param indent the <code>String</code> that represents the indentation level
   * @return a readable channels and the instruments assigned to them
   * indented as desired
   */
  public String toIndentedString(String indent){
    String result = "MusicBox: \n";
    for (int i = 0; i < 16; i++){
      result = result + indent + instrumentNames[this.program[i]]+ "\n";
      //result = result + "i: " + i + " program[i]: " + this.program[i] +
      //    " channels[i].getProgram(): " + channels[i].getProgram() +
      //    "this.getProgram(i): " + this.getProgram(i)+ "\n";
    }
    return result;
  }
  
  /**
   * Is this <code>MusicBox</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o == null)
      return false;
    
    // is the given object a MusicBox?
    if (! (o instanceof MusicBox))
      return false;
    MusicBox that = (MusicBox)o;
    
    // match their synthesizers
    if (this.synth == null)
      // if both synthesizers are null
      // compare programs
      if (that.synth == null)
        return this.comparePrograms(that);
      else
        // this is null, that is not null
        return false;
    
    // now this is not null
    else
      // but that is null
      if (that.synth == null)
        return false;
      
    // now both are non-null
    //if (!this.synth.equals(that.synth))
    //  return false;
    
    // all is well = now match the programs only
    if (SYNTH_READY){

      // compare the programs for all channels
      for (int i = 0; i < 16; i++){

        // this.channel[i] not null - the other one must be too
        // and their programs must match
        if (this.channels[i] != null){
          if (that.channels[i] == null)
            return false;

          if (this.channels[i].getProgram() != that.channels[i].getProgram())
            return false;
        }
        // this.channel[i] is null - the other one must be too
        // and their default programs must match
        else{
          if (that.channels[i] != null)
            return false;

          if (this.program[i] != that.program[i])
            return false;
        }            
      }
    }
    // no synthesizer - match the default programs only
    else{
      // compare the programs for all channels
      for (int i = 0; i < 16; i++){
        if (this.program[i] != that.program[i])
          return false;
      }
    }
    // programs have been matched - all is well
    return true;
  }

  private boolean comparePrograms(MusicBox that){
    // compare the programs for all channels
    for (int i = 0; i < 16; i++){
      if (this.program[i] != that.program[i])
        return false;
    }
    return true;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    int hash = this.synth.hashCode();
    for (int i = 0; i < 16; i++){
      hash = hash + this.program[i];
    }
    return hash;
  }

  /**
   * Play a sample Frere Jacques tune.
   * @param argv
   */
  public static void main(String[] argv){
    MusicBox mb = new MusicBox();
    
    // convenience only, as the Chord constructor converts int-s to Note-s
    ArrayList<Note> mytune = new Chord(
        0,0,0,0,       
        noteC,0,0,0,noteD,0,0,0,noteE,0,0,0,noteC,0,0,0,
        noteC,0,0,0,noteD,0,0,0,noteE,0,0,0,noteC,0,0,0,
        noteE,0,0,0,noteF,0,0,0,noteG,0,0,0,0,0,0,0,
        noteE,0,0,0,noteF,0,0,0,noteG,0,0,0,0,0,0,0,
        noteG,0,noteA,0,noteG,0,noteF,0,noteE,0,0,0,noteC,0,0,0,
        noteG,0,noteA,0,noteG,0,noteF,0,noteE,0,0,0,noteC,0,0,0,
        noteC,0,0,0,noteDownG,0,0,0,noteC,0,0,0,0,0,0,0,
        noteC,0,0,0,noteDownG,0,0,0,noteC,0,0,0,0,0,0,0,      
        0,0,0,0).notes;
    
    Melody frereMelody = new Melody();
    frereMelody.initMelody(mytune);
    Tune t; 
    
    for (int i = 0; i < 300; i++){
      t = new Tune(PIANO, frereMelody.next());
      mb.playTune(t);
      mb.sleepSome(80);
      mb.stopTune(t);
    }
  } 
}