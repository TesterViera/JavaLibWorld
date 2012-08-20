package appletsoundworldtests;

import javalib.tunes.*;

import java.util.*;

import javalib.appletsoundworld.*;

class SampleTunes implements SoundConstants{
  ArrayList<Tune> playlist = new ArrayList<Tune>();
  
  //Tune: channel, note, beats
  
  public static int frereTunes[][] = {
    {ORGAN, 0, 4},
    {ORGAN, noteC, 4},
    {ORGAN, noteD, 4},
    {ORGAN, noteE, 4},
    {ORGAN, noteC, 4},
    
    {ORGAN, noteC, 4},
    {ORGAN, noteD, 4},
    {ORGAN, noteE, 4},
    {ORGAN, noteC, 4},
    
    {ORGAN, noteE, 4},
    {ORGAN, noteF, 4},
    {ORGAN, noteG, 8},
    
    {ORGAN, noteE, 4},
    {ORGAN, noteF, 4},
    {ORGAN, noteG, 8},
    
    {ORGAN, noteG, 2},
    {ORGAN, noteA, 2},
    {ORGAN, noteG, 1},
    {ORGAN, noteF, 2},
    {ORGAN, noteE, 4},
    {ORGAN, noteC, 4},
    
    {ORGAN, noteG, 2},
    {ORGAN, noteA, 2},
    {ORGAN, noteG, 1},
    {ORGAN, noteF, 2},
    {ORGAN, noteE, 4},
    {ORGAN, noteC, 4},

    {ORGAN, noteC, 4},
    {ORGAN, noteDownG, 4},
    {ORGAN, noteC, 8},

    {ORGAN, noteC, 4},
    {ORGAN, noteDownG, 4},
    {ORGAN, noteC, 8}
  };
  
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
  
}