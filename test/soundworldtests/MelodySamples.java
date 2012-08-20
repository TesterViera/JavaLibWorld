package soundworldtests;
import java.lang.reflect.*;

import javalib.tunes.SoundConstants;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * An attempt to separate the sound sequence design from the rest of the code - 
 * for a successful solution, see the TickyTackSong example.
 * 
 * @author Viera K. Proulx
 * @since 8 August 2012
 */
class Melody{
  int mlength; // length of a note in the measure is 1/mlength
  int mnotes;  // number of notes in a measure
  
  // 3/4 measure has mlength = 4 and mnotes = 3
  
  int[] melody;
  
  Melody(int mlength, int mnotes, int[] melody){
    this.mlength = mlength;
    this.mnotes = mnotes;
    this.melody = melody;
  }
  
  int getLength(){
    return Array.getLength(this.melody);
  }
  
  Player getPlayer(){
    return new Player(this);
  }
}


class Player{
  Melody m;
  int current;
  int msize;
  
  Player(Melody m){
    this.m = m;
    this.current = 0;
    this.msize = m.getLength();
  }
  
  int next(){
    int tmp = this.m.melody[this.current];
    this.current = (this.current + 1) % this.msize;
    return tmp;
  }
  
  // move one measure ahead
  // we use 16/mlength ticks for each note
  // so quarter note uses 4 ticks, eighth note uses 2 ticks, 
  // sixteenth note uses one tick, half note uses 8 ticks, etc.
  void nextMeasure(){
    this.current = 
      (this.current + this.m.mlength * this.m.mnotes) % this.msize;
  }
}



class MelodySamples implements SoundConstants{
  MelodySamples(){}
  
  //to represent the NOTES:
  //Frere Jacques
  
  public static int frereNotes[] = {
      0,0,0,0,
      noteC,0,0,0,noteD,0,0,0,noteE,0,0,0,noteC,0,0,0,
      noteC,0,0,0,noteD,0,0,0,noteE,0,0,0,noteC,0,0,0,
      noteE,0,0,0,noteF,0,0,0,noteG,0,0,0,0,0,0,0,
      noteE,0,0,0,noteF,0,0,0,noteG,0,0,0,0,0,0,0,
      noteG,0,noteA,0,noteG,0,noteF,0,noteE,0,0,0,noteC,0,0,0,
      noteG,0,noteA,0,noteG,0,noteF,0,noteE,0,0,0,noteC,0,0,0,
      noteC,0,0,0,noteDownG,0,0,0,noteC,0,0,0,0,0,0,0,
      noteC,0,0,0,noteDownG,0,0,0,noteC,0,0,0,0,0,0,0,
      0,0,0,0,
  };
  
  public static int jeopardyNotes[] = {
    0,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,noteC,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,0,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,noteUpC,0,0,0,
    noteUpE,0,0,0,0,0,noteUpD,0,noteUpC,0,noteB,0,noteA,0,noteG,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,noteC,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,0,0,0,0,
    noteUpC,0,0,0,0,0,noteA,0,noteG,0,0,0,noteF,0,0,0,noteE,0,0,0,noteD,0,0,0,noteC,0,0,0,
    0,0,0,0,
};

  public static Melody frereTune = new Melody(4, 4, frereNotes);
  public static Player frere(){
    return new Player(frereTune);
  }

  public static Melody jeopardyTune = new Melody(4, 4, jeopardyNotes);
  public static Player jeopardy(){
    return new Player(jeopardyTune);
  } 
}
