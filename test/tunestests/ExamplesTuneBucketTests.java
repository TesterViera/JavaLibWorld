package tunestests;
import java.util.*;

import javalib.tunes.*;
import tester.*;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <p>Test suite for the <code>tunes</code> package.</p>
 * <p>Tests for the Tune class.</p>
 * 
 * @author Viera K. Proulx
 * @since 15 June 2012
 */

public class ExamplesTuneBucketTests implements SoundConstants{
  ExamplesTuneBucketTests(){}

  // support for the regression tests
  public static ExamplesTuneBucketTests examplesInstance = new ExamplesTuneBucketTests();
  
  /** a sample note:  */
  Note C4n2 = new Note("C4n2");
  Note C4n4 = new Note("C4n4");
  Note C4n2a = new Note(60, 2);
  Note D4f4 = new Note("D4f4");
  Note C4s4 = new Note("C4s4");
  Note C4s4a = new Note(61, 4);
  Note A4n1a = new Note(57, 1);
  Note A4n1b = new Note("A4n1");
  
  Chord cmajor1 = new Chord("C6n4", "E6n2");
  
  Note C6n4 = new Note("C6n4");
  Note E6n2 = new Note("E6n2");
  Note G6n1 = new Note("G6n1");

  Chord cmajor2 = new Chord(this.C6n4, this.E6n2);
  Chord cmajor3 = Chord.noplay;

  /** mapping of channels to the instruments they represent */
  int[] myInstrumentNames = new int[]{
      PIANO,
      ORGAN,
      TIMPANI,
      VIOLIN,
      CLARINET,
      STEELDRUM,
      CHOIR,
      TUBA,
      SAX,
      PERCUSSION,
      WOOD_BLOCK,
      BAGPIPE,
      BIRD_TWEET,
      SEASHORE,
      APPLAUSE, 
      BRASS_SECTION};
  
  /** mapping of channels to the instruments they represent */
  int[] myInstruments = new int[]{
      Piano,
      ChurchOrgan,
      Timpani,
      Violin,
      Clarinet,
      SteelDrums,
      ChoirAahs,
      Tuba,
      AltoSax,
      PERCUSSION,
      Woodblock,
      BagPipe,
      BirdTweet,
      Seashore,
      Applause,
      BrassSection};
   
  MusicBox mbox = new MusicBox(this.myInstruments);
  MusicBox mbox2 = new MusicBox(this.myInstruments);
  
  Tune cmajorTune = new Tune(PIANO, this.cmajor2);

  Chord aminor = new Chord("A6n4", "C7n2", "E7n1");
  Tune aminorTune = new Tune(PIANO, this.aminor);
  
  void reset(){

    this.C4n2 = new Note("C4n2");
    this.C4n4 = new Note("C4n4");
    this.C4n2a = new Note(60, 2);
    this.D4f4 = new Note("D4f4");
    this.C4s4 = new Note("C4s4");
    this.C4s4a = new Note(61, 4);
    this.A4n1a = new Note(57, 1);
    this.A4n1b = new Note("A4n1");

    this.cmajor1 = new Chord("C6n4", "E6n2");

    this.C6n4 = new Note("C6n4");
    this.E6n2 = new Note("E6n2");
    this.G6n1 = new Note("G6n1");

    this.cmajor2 = new Chord(this.C6n4, this.E6n2);
    this.cmajor3 = Chord.noplay;

    this.cmajorTune = new Tune(PIANO, this.cmajor2);

    this.aminor = new Chord("A6n4", "C7n2", "E7n1");
    this.aminorTune = new Tune(PIANO, this.aminor);
  }
  
  /**
   * <p>Test the constructors and methods addNote, addChord, bucketSize.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testAddMethods(Tester t){
    TuneBucket tuneBucket = new TuneBucket(this.mbox);
    
    // test the methods addNote(int, Note), addNote(int, int)
    // test the methods contains(channel, Note), bucketSize
    t.checkExpect(tuneBucket.bucketSize(), 0);
    tuneBucket.addNote(PIANO, new Note("E4n2"));
    tuneBucket.addNote(PIANO, new Note("G4n2"));
    tuneBucket.addNote(TUBA, 64);
    t.checkExpect(tuneBucket.bucketSize(), 3);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("E4n2")), true);
    t.checkExpect(tuneBucket.contains(TUBA, 64), true);
    
    // test the method addNote(int, String), bucketSize
    tuneBucket.addNote(VIOLIN, "E4n2");
    tuneBucket.addNote(VIOLIN, "G4n2");
    t.checkExpect(tuneBucket.contains(VIOLIN, new Note("E4n2")), true);
    t.checkExpect(tuneBucket.bucketSize(), 5);
    
    // test the method addChord(Chord), contains(channel, pitch), bucketSize
    tuneBucket.addChord(TUBA, aminor);
    t.checkExpect(tuneBucket.bucketSize(), 7);
    t.checkExpect(tuneBucket.contains(TUBA, 81), true);
    t.checkExpect(tuneBucket.contains(TUBA, 96), true);
    t.checkExpect(tuneBucket.contains(TUBA, 100), false);
    
    reset();
    tuneBucket = new TuneBucket(this.mbox);
    tuneBucket.addTune(this.aminorTune);
    t.checkExpect(tuneBucket.bucketSize(), 3);
    t.checkExpect(tuneBucket.contains(PIANO, 81), true);
    t.checkExpect(tuneBucket.contains(PIANO, 96), true);
    t.checkExpect(tuneBucket.contains(PIANO, 100), true);
    
    reset();
    tuneBucket = new TuneBucket(this.mbox);
    ArrayList<Tune> tunes = new ArrayList<Tune>();
    tunes.add(this.aminorTune);
    tunes.add(this.cmajorTune);
    tuneBucket.addTunes(tunes);
    t.checkExpect(tuneBucket.bucketSize(), 5);
    t.checkExpect(tuneBucket.contains(PIANO, 81), true);
    t.checkExpect(tuneBucket.contains(PIANO, 96), true);
    t.checkExpect(tuneBucket.contains(PIANO, 100), true);
    t.checkExpect(tuneBucket.contains(PIANO, this.C6n4), true);
    t.checkExpect(tuneBucket.contains(PIANO, this.E6n2), true);


    // test the constructor that specifies the program for the MusicBox
    this.myInstruments[0] = Ocarina;
    tuneBucket = new TuneBucket(this.mbox, myInstruments);
    tuneBucket.addTunes(tunes);
    t.checkExpect(tuneBucket.bucketSize(), 5);
    // PIANO just represents channel 0 - only the MusicBox knows it is ocarina
    t.checkExpect(tuneBucket.contains(PIANO, 81), true);
    t.checkExpect(tuneBucket.contains(PIANO, 96), true);
    t.checkExpect(tuneBucket.contains(PIANO, 100), true);
    t.checkExpect(this.mbox.getProgram(0), Ocarina);
    
    // undo the damage to mbox
    this.myInstruments[0] = AcousticGrandPiano;
    this.mbox = new MusicBox(this.myInstruments);
  }
  
  /**
   * <p>Test the methods for the class TuneBucket</p>
   * <p>Test the methods copy, nextBeat, skipBeat, playTunes.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testTuneBucketPlay(Tester t){
    TuneBucket tuneBucket = new TuneBucket(this.mbox);
    
    tuneBucket.addChord(PIANO, this.aminor);
    tuneBucket.addChord(PIANO, this.cmajor2);
    t.checkExpect(tuneBucket.bucketSize(), 5);
    
    // test the method copyBucket by playing the new bucket
    TuneBucket copyBucket = tuneBucket.copy();
    t.checkExpect(copyBucket.bucketSize(), 5);

    // test the method playTunes, clearBucket
    tuneBucket.playTunes();
    
    t.checkExpect(tuneBucket.contains(PIANO, 81), true);
    t.checkExpect(tuneBucket.contains(PIANO, 96), true);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("C6n3")), true);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("E6n1")), true);

    // wait one half of a second
    this.mbox.sleepSome(1000);
    
    // test the method playTunes, clearBucket
    tuneBucket.playTunes();
    
    t.checkExpect(tuneBucket.contains(PIANO, 81), true);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("C6n2")), true);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("E6n0")), false);
    
    // wait one half of a second
    this.mbox.sleepSome(1000);
    tuneBucket.clearBucket();
    t.checkExpect(tuneBucket.bucketSize(), 0);
    t.checkExpect(copyBucket.bucketSize(), 5);

    t.checkExpect(copyBucket.contains(PIANO, 81), true);
    t.checkExpect(copyBucket.contains(PIANO, 96), true);
    t.checkExpect(copyBucket.contains(PIANO, new Note("C6n4")), true);
    t.checkExpect(copyBucket.contains(PIANO, new Note("E6n2")), true);
    
    copyBucket.playTunes();
    
    // playTunes and nextBeat reduced the duration by 2
    // and eliminated all silent notes
    copyBucket.nextBeat();
    t.checkExpect(copyBucket.contains(PIANO, 81), true);
    t.checkExpect(copyBucket.contains(PIANO, new Note("C6n2")), true);

    // first add a silent note to the bucket
    copyBucket.addNote(PIANO, new Note("C4n0"));
    t.checkExpect(copyBucket.bucketSize(), 3);
    
    // remove it and test the effect
    copyBucket.removeSilent();
    t.checkExpect(copyBucket.contains(PIANO, 81), true);
    t.checkExpect(copyBucket.contains(PIANO, new Note("C6n2")), true);
    t.checkExpect(copyBucket.bucketSize(), 2);
    
    // we now increrase the duration by one
    copyBucket.skipBeat();
    t.checkExpect(copyBucket.contains(PIANO, 81), true);
    t.checkExpect(copyBucket.contains(PIANO, new Note("C6n3")), true);
    
    
    
    // wait one half of a second
    this.mbox.sleepSome(1000);
  }


  /**
   * <p>Test the constructors and methods for the class TuneBucket</p>
   * <p>Test the methods isSilent, size, removeSilent, addNote, clearChord
   * addChord.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testTuneBucketBeats(Tester t){
    TuneBucket tuneBucket = new TuneBucket(this.mbox);
    
    // test the methods addTune(Tune), contains(channel, Note), bucketSize
    t.checkExpect(tuneBucket.bucketSize(), 0);
    tuneBucket.addTune(new Tune(PIANO, new Note("E4n2")));
    tuneBucket.addTune(new Tune(PIANO, new Note("G4n2")));
    t.checkExpect(tuneBucket.bucketSize(), 2);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("E4n2")), true);
    
    // test the method addTunes(Iterable<Tune>), bucketSize
    ArrayList<Tune> manytunes = new ArrayList<Tune>();
    manytunes.add(new Tune(VIOLIN, new Note("E4n2")));
    manytunes.add(new Tune(VIOLIN, new Note("G4n2")));
    tuneBucket.addTunes(manytunes);
    t.checkExpect(tuneBucket.bucketSize(), 4);
    t.checkExpect(tuneBucket.contains(VIOLIN, new Note("E4n2")), true);
    
    tuneBucket.skipBeat();
    t.checkExpect(tuneBucket.bucketSize(), 4);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("G4n3")), true);
    t.checkExpect(tuneBucket.contains(VIOLIN, new Note("E4n3")), true);
    
    TuneBucket newBucket = new TuneBucket(this.mbox);
    newBucket.add(tuneBucket);
    t.checkExpect(newBucket.bucketSize(), 4);
    t.checkExpect(newBucket.contains(PIANO, new Note("G4n3")), true);
    t.checkExpect(newBucket.contains(VIOLIN, new Note("E4n3")), true);
    
    newBucket.nextBeat();

    tuneBucket.skipBeat();
    t.checkExpect(newBucket.bucketSize(), 4);
    t.checkExpect(newBucket.contains(PIANO, new Note("G4n2")), true);
    //t.checkExpect(newBucket, tuneBucket); -- should fail
    t.checkExpect(newBucket.contains(VIOLIN, new Note("E4n2")), true);
  }
  
  /**
   * <p>Test the constructors and methods for the class TuneBucket</p>
   * <p>Test the methods isSilent, size, removeSilent, addNote, clearChord
   * addChord.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testClearTunes(Tester t){
    TuneBucket tuneBucket = new TuneBucket(this.mbox);
    
    // test the method clearTunes
    tuneBucket.addNote(TUBA, 60);
    tuneBucket.addNote(TUBA, 64);
    tuneBucket.clearTunes();
    t.checkExpect(tuneBucket.bucketSize(), 0);
    t.checkExpect(tuneBucket.contains(TUBA, 60), false);
  }
  
  /**
   * Test that the constructor for MusicBox assigns the channels
   * correctly.
   * @param t the tester that runs the tests
   */
  void testMusicBox(Tester t){    
    ArrayList<Tune> tunes = new ArrayList<Tune>();
    
    t.checkExpect(this.mbox.getProgram(15), BrassSection);
    t.checkExpect(this.mbox.getProgram(5), SteelDrums);
    
    // test the method playTune in the class MusicBox
    System.out.println("Play C major chord on a piano");
    this.mbox.playTune(this.cmajorTune);
    
    // wait one second
    this.mbox.sleepSome(500);
    
    // test the method stopTune in the class MusicBox
    System.out.println("Stop playing C major chord on a piano");
    tunes.add(this.cmajorTune);
    this.mbox.stopTune(this.cmajorTune);
    
    // wait one second
    this.mbox.sleepSome(500);
    
    // test the method playOn in the class MusicBox
    tunes.clear();
    tunes.add(this.aminorTune);
    System.out.println("Play A minor chord on a piano");
    this.mbox.playOn(tunes);
    
    // wait one half of a second
    this.mbox.sleepSome(500);
    
    // test the method playOff in the class MusicBox
    System.out.println("Stop playing A minor chord on a piano");
    this.mbox.playOff(tunes);
    
    // wait one half of a second
    this.mbox.sleepSome(500);
    
    
    t.checkExpect(this.mbox, this.mbox2);
    this.myInstruments[0] = SoundConstants.Ocarina;
    this.mbox.initChannels(this.myInstruments);
    
    System.out.println("MusicBox mbox = " + this.mbox.toIndentedString("  "));
    System.out.println("MusicBox mbox2 = " + this.mbox2.toIndentedString("  "));

    t.checkExpect(this.mbox, this.mbox2);
    
    System.out.println("Goodbye.");   
  }
  

  
  // run the tests and run the game
  public static void main(String[] argv){
    ExamplesTuneBucketTests etbt = new ExamplesTuneBucketTests();
    Tester.runReport(etbt, false, false);
  }
  
}