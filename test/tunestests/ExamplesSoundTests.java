package tunestests;
import java.util.*;

import javalib.tunes.*;
import tester.*;

/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <p>Test suite for the <code>tunes</code> package.</p>
 * <p>Tests for the Note class, especially all of its constructors.</p>
 * <p>Tests for the MusicBox class.</p>
 * <p>Tests for the Tune and Chord classes.</p>
 * 
 * @author Viera K. Proulx
 * @since 5 June 2010
 */

public class ExamplesSoundTests implements SoundConstants{
  ExamplesSoundTests(){}

  // support for the regression tests
  public static ExamplesSoundTests examplesInstance = new ExamplesSoundTests();
  
  /** a sample note:  */
  Note C4n2 = new Note("C4n2");
  Note C4n4 = new Note("C4n4");
  Note C4n2a = new Note(60, 2);
  Note D4f4 = new Note("D4f4");
  Note C4s4 = new Note("C4s4");
  Note C4s4a = new Note(61, 4);
  Note A4n1a = new Note(57, 1);
  Note A4n1b = new Note("A4n1");
  
  void testNoteConstruction(Tester t){
    System.out.println("A4n1a pitch: " + this.A4n1a.pitch + 
                       " snote: "  + this.A4n1a.snote + 
                       " octave: " + this.A4n1a.octave);
    System.out.println("A4n1b pitch: " + this.A4n1b.pitch + 
        " snote: "  + this.A4n1b.snote + 
        " octave: " + this.A4n1b.octave);
    t.checkExpect(this.A4n1a, A4n1b);
    t.checkExpect(this.C4n2.duration, 2);
    t.checkExpect(this.C4n2.octave, 4);
    t.checkExpect(this.C4n2.modifier, 'n');
    t.checkExpect(this.C4n2.pitch, 60);
    
    t.checkExpect(this.C4n2a.duration, 2);
    t.checkExpect(this.C4n2a.octave, 4);
    t.checkExpect(this.C4n2a.modifier, 'n');
    t.checkExpect(this.C4n2a.pitch, 60);
    t.checkExpect(this.C4n2a.snote, "C4n2");
  }
  
  /**
   * <p>Test the method sameNote in the class Note.</p>
   * <p>Test the methods nextBeat and skipBeat in the class Note.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testSameNote(Tester t){

    t.checkExpect(this.C4n2.sameNote(this.C4n2a), true);
    t.checkExpect(this.C4s4.sameNote(this.D4f4), true);
    t.checkExpect(this.D4f4.sameNote(this.C4s4a), true);
    t.checkExpect(this.C4s4.sameNote(this.C4n2a), false);
    t.checkExpect(this.C4s4.sameNote(this.C4s4a), true);
    t.checkExpect(this.C4n4.sameNote(this.C4n2), false);
    
    this.C4n4.nextBeat();
    t.checkExpect(this.C4n4, new Note("C4n3"));
    
    // test the method skipBeat
    this.C4n4.skipBeat();
    t.checkExpect(this.C4n4, new Note("C4n4"));
    
    // test the method isSilent
    this.C4n4.nextBeat();
    this.C4n4.nextBeat();
    this.C4n4.nextBeat();
    t.checkExpect(this.C4n4, new Note("C4n1"));
    this.C4n4.nextBeat();
    t.checkExpect(this.C4n4.isSilent(), true);
    
  }
  
  Chord cmajor1 = new Chord("E6n2", "C6n4");
  
  Note C6n4 = new Note("C6n4");
  Note E6n2 = new Note("E6n2");
  Note G6n1 = new Note("G6n1");

  Chord cmajor2 = new Chord(this.C6n4, this.E6n2);
  Chord cmajor3 = Chord.noplay;
  
  /**
   * <p>Test the constructors for the Chord class.</p>
   * <p>Test the method addNote (all variants).</p>
   * <p>Test the method size.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testChordConstructorsMethods(Tester t){
    // test the equals method for Chord-s
    t.checkExpect(this.cmajor1, this.cmajor2);
    
    // test the constructor that consumes Strings:
    t.checkExpect(this.cmajor1.notes.get(1), new Note(84, 4));
    t.checkExpect(this.cmajor1.notes.get(0), new Note("E6n2"));
    this.cmajor1.addNote("G6n1");
    t.checkExpect(this.cmajor1.notes.get(2), new Note(91, 1));
    
    // test the method containsNote
    t.checkExpect(this.cmajor1.containsNote(new Note(84, 4)), true);
    t.checkExpect(this.cmajor1.containsNote(new Note(85, 4)), false);
    t.checkExpect(this.cmajor1.containsNote(new Note(91, 1)), true);

    // test the constructor that consumes Notes:
    t.checkExpect(this.cmajor2.notes.get(0), new Note(84, 4));
    t.checkExpect(this.cmajor2.notes.get(1), new Note("E6n2"));
    this.cmajor2.addNote(this.G6n1);
    t.checkExpect(this.cmajor2.notes.get(2), new Note(91, 1));
    
    // test the singleton noplay
    t.checkExpect(this.cmajor3.size(), 0);
    
    this.cmajor3 = new Chord(60, 64);

    // test the constructor that consumes integers:
    t.checkExpect(this.cmajor3.notes.get(0), new Note(60));
    t.checkExpect(this.cmajor3.notes.get(1), new Note(64));
    this.cmajor3.addNote(67, 1);
    t.checkExpect(this.cmajor3.notes.get(2), new Note(67));
    t.checkExpect(this.cmajor3.size(), 3);
    
    // test the method nextBeat
    this.cmajor1.nextBeat();
    t.checkExpect(this.cmajor1.notes.get(1), new Note(84, 3));
    t.checkExpect(this.cmajor1.notes.get(0), new Note("E6n1"));
    
    // test the method skipBeat
    this.cmajor2.skipBeat();
    t.checkExpect(this.cmajor2.notes.get(0), new Note(84, 5));
    t.checkExpect(this.cmajor2.notes.get(1), new Note("E6n3"));
    t.checkExpect(this.cmajor2.notes.get(2), new Note(91, 2));
    
    // test the method isSilent()
    t.checkExpect(this.cmajor3.isSilent(), false);
    this.cmajor3.nextBeat();
    t.checkExpect(this.cmajor3.isSilent(), true);
  }
  
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
  
  Tune cmajorTune = new Tune(PIANO, new Chord(this.C6n4, this.E6n2));
  
  Tune cmajorTune2 = new Tune(PIANO, new Chord("E6n2", "C6n4"));

  Chord aminor = new Chord("A6n4", "C7n2", "E7n1");
  Tune aminorTune = new Tune(PIANO, this.aminor);
  
  /**
   * <p>Test the constructors and methods for the class Tune</p>
   * <p>Test the methods isSilent, size, removeSilent, addNote, clearChord
   * addChord.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testTune(Tester t){
    // reset
    this.cmajorTune2 = new Tune(PIANO, new Chord("E6n2", "C6n4"));
    this.cmajorTune = 
        new Tune(PIANO, new Chord(new Note("C6n4"), new Note("E6n2")));
    this.E6n2 = new Note("E6n2");
    this.C6n4 = new Note("C6n4");
    
    // compare Note-s
    t.checkExpect(new Note("C6n4"), this.C6n4);
    
    // test the equals method for Tune-s
    t.checkExpect(this.cmajorTune, this.cmajorTune2);
    
    // test constructor with channel and Tune
    t.checkExpect(this.cmajorTune.getChannel(), PIANO);
    t.checkExpect(this.cmajorTune.containsNote(this.C4n2), false);
    t.checkExpect(this.cmajorTune.containsNote(this.C6n4), true); 
    t.checkExpect(this.cmajorTune.containsNote(this.E6n2), true); 
    
    // test the constructor with channel only
    Chord silent = new Chord();
    Tune silentTune = new Tune(4);
    t.checkExpect(silentTune.getChannel(), 4);
    t.checkExpect(silentTune.getChord(), silent);
    t.checkExpect(silentTune.isSilent(), true);
    
    // test the method isSilent
    silentTune.addNote(new Note("C4n0"));
    t.checkExpect(silentTune.isSilent(), true);
    t.checkExpect(this.cmajorTune.isSilent(), false);

    // test the method removeSilent, size
    silentTune.addNote(new Note("E4n0"));
    t.checkExpect(silentTune.size(), 2);
    silentTune.addNote(new Note("G4n2"));
    silentTune.removeSilent();
    t.checkExpect(silentTune.size(), 1);
    t.checkExpect(silentTune.containsNote(new Note("G4n2")), true);
    t.checkExpect(silentTune.isSilent(), false);
    
    Tune badTune = new Tune(4);
    t.checkExpect(silentTune, badTune, "should fail");
    
    System.out.println("silentTune.toString()\n" + silentTune.toString() + "\n");
    
    // test the method clearChord
    silentTune.clearChord();
    t.checkExpect(silentTune.isSilent(), true);
    
    // test the method addChord
    silentTune.addChord(this.aminor);
    t.checkExpect(silentTune.size(), 3);
    t.checkExpect(silentTune.containsNote(new Note("A6n4")), true);
    t.checkExpect(silentTune.containsNote(new Note("G4n2")), false); 
    t.checkExpect(silentTune.containsNote(new Note("E7n1")), true); 
    
    System.out.println("silentTune.toString()\n" + silentTune.toString() + "\n");
    
  }

  /**
   * <p>Test the constructors and methods for the class TuneBucket</p>
   * <p>Test the methods isSilent, size, removeSilent, addNote, clearChord
   * addChord.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testTuneBucketPlay(Tester t){
    TuneBucket tuneBucket = new TuneBucket(this.mbox);
    
    // test the methods addNote(Note), contains(channel, Note), bucketSize
    t.checkExpect(tuneBucket.bucketSize(), 0);
    tuneBucket.addNote(PIANO, new Note("E4n2"));
    tuneBucket.addNote(PIANO, new Note("G4n2"));
    t.checkExpect(tuneBucket.bucketSize(), 2);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("E4n2")), true);
    
    // test the method addNote(String), bucketSize
    tuneBucket.addNote(VIOLIN, "E4n2");
    tuneBucket.addNote(VIOLIN, "G4n2");
    t.checkExpect(tuneBucket.bucketSize(), 4);
    
    // test the method addNote(String), contains(channel, pitch), bucketSize
    tuneBucket.addNote(TUBA, 60);
    tuneBucket.addNote(TUBA, 64);
    t.checkExpect(tuneBucket.bucketSize(), 6);
    t.checkExpect(tuneBucket.contains(TUBA, 60), true);
    
    System.out.println("tuneBucket.toString()\n" + tuneBucket.toString() + "\n");
    
    
    // test the method copyBucket by playing the new bucket
    TuneBucket copyBucket = tuneBucket.copy();
    t.checkExpect(copyBucket.bucketSize(), 6);

    // test the method playTunes, clearBucket
    tuneBucket.playTunes();
    
    // wait one second
    this.mbox.sleepSome(1000);
    tuneBucket.clearBucket();
    t.checkExpect(tuneBucket.bucketSize(), 0);
    t.checkExpect(copyBucket.bucketSize(), 6);

    // wait one second
    this.mbox.sleepSome(1000);
    
    copyBucket.playTunes();
    
    // wait one second
    this.mbox.sleepSome(1000);
    copyBucket.clearBucket();
    
    // wait one second
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
    TuneBucket tuneBucket2 = new TuneBucket(this.mbox);
    
    // test the methods addTune(Tune), contains(channel, Note), bucketSize
    t.checkExpect(tuneBucket.bucketSize(), 0);
    tuneBucket.addTune(new Tune(PIANO, new Note("E4n2")));
    tuneBucket.addTune(new Tune(PIANO, new Note("G4n2")));

    tuneBucket2.addTune(new Tune(PIANO, new Note("G4n2")));
    tuneBucket2.addTune(new Tune(PIANO, new Note("E4n2")));
    
    // test the method equals for TuneBucket-s
    t.checkExpect(tuneBucket, tuneBucket2);
    
    
    tuneBucket2.addTune(new Tune(PIANO, new Note("E4n2")));
    t.checkExpect(tuneBucket2.bucketSize(), 3);
    t.checkExpect(tuneBucket, tuneBucket2, "should fail");
    t.checkExpect(tuneBucket2, tuneBucket, "should fail");
    
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
  void testTuneBucket(Tester t){
    TuneBucket tuneBucket = new TuneBucket(this.mbox);
    
    // test the methods addNote(Note), contains(channel, Note), bucketSize
    t.checkExpect(tuneBucket.bucketSize(), 0);
    tuneBucket.addNote(PIANO, new Note("E4n2"));
    tuneBucket.addNote(PIANO, new Note("G4n2"));
    t.checkExpect(tuneBucket.bucketSize(), 2);
    t.checkExpect(tuneBucket.contains(PIANO, new Note("E4n2")), true);
    
    // test the method addNote(String), bucketSize
    tuneBucket.addNote(VIOLIN, "E4n2");
    tuneBucket.addNote(VIOLIN, "G4n2");
    t.checkExpect(tuneBucket.bucketSize(), 4);
    
    // test the method addNote(String), contains(channel, pitch), bucketSize
    tuneBucket.addNote(TUBA, 60);
    tuneBucket.addNote(TUBA, 64);
    t.checkExpect(tuneBucket.bucketSize(), 6);
    t.checkExpect(tuneBucket.contains(TUBA, 60), true);

    // test the method copyBucket by testing the contents of the new bucket
    TuneBucket copyBucket = tuneBucket.copy();
    t.checkExpect(copyBucket.bucketSize(), 6);
    t.checkExpect(copyBucket.contains(TUBA, 60), true);
    
    tuneBucket.clearBucket();
    t.checkExpect(tuneBucket.bucketSize(), 0);
    t.checkExpect(tuneBucket.contains(TUBA, 60), false);

    t.checkExpect(copyBucket.bucketSize(), 6);
    t.checkExpect(copyBucket.contains(TUBA, 60), true);
    
    copyBucket.clearBucket();
    t.checkExpect(copyBucket.bucketSize(), 0);
    t.checkExpect(copyBucket.contains(TUBA, 60), false);
    
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
    this.mbox.sleepSome(1000);
    
    // test the method stopTune in the class MusicBox
    System.out.println("Stop playing C major chord on a piano");
    tunes.add(this.cmajorTune);
    this.mbox.stopTune(this.cmajorTune);
    
    // wait one second
    this.mbox.sleepSome(1000);
    
    // test the method playOn in the class MusicBox
    tunes.clear();
    tunes.add(this.aminorTune);
    System.out.println("Play A minor chord on a piano");
    this.mbox.playOn(tunes);
    
    // wait one second
    this.mbox.sleepSome(1000);
    
    // test the method playOff in the class MusicBox
    System.out.println("Stop playing A minor chord on a piano");
    this.mbox.playOff(tunes);
    
    // wait one second
    this.mbox.sleepSome(1000);
    
    t.checkExpect(this.mbox, this.mbox2);
    this.myInstruments[0] = SoundConstants.Ocarina;
    this.mbox.initChannels(this.myInstruments);
    t.checkExpect(this.mbox, this.mbox2, "should fail");
    
    System.out.println("Goodbye.");   
  }
  

  
  // run the tests and run the game
  public static void main(String[] argv){
    ExamplesSoundTests est = new ExamplesSoundTests();
    Tester.runReport(est, false, false);
  }
  
}