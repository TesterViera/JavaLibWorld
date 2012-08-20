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

public class ExamplesTuneTests implements SoundConstants{
  ExamplesTuneTests(){}

  // support for the regression tests
  public static ExamplesTuneTests examplesInstance = new ExamplesTuneTests();
  
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
   * <p>Test the constructors and methods for the class Tune</p>
   * <p>Test the methods isSilent, size, removeSilent, addNote, clearChord
   * addChord.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testTune(Tester t){
    reset();

    t.checkExpect(this.cmajorTune.toString(), 
        "Channel: 0" +
            "\n  note: C6n4 pitch: 84, duration: 4" + 
            "\n  note: E6n2 pitch: 88, duration: 2\n**");

    t.checkExpect(this.cmajorTune.toIndentedString("  "), 
        "Channel: 0" +
            "\n    note: C6n4 pitch: 84, duration: 4" + 
            "\n    note: E6n2 pitch: 88, duration: 2\n**");

    t.checkExpect(this.cmajorTune.toString("AcousticGrandPiano"), 
        "AcousticGrandPiano(C6n4, E6n2)**");
  }

  /**
   * <p>Test the methods <code>getChannel</code> and <code>contains</code>.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testContainsNote(Tester t){
    reset();

    t.checkExpect(this.cmajorTune.getChannel(), PIANO);
    t.checkExpect(this.cmajorTune.containsNote(this.C4n2), false);
    t.checkExpect(this.cmajorTune.containsNote(this.C6n4), true); 
    t.checkExpect(this.cmajorTune.containsNote(this.E6n2), true); 
  }

  /**
   * <p>Test the methods <code>isSilent</code> and <code>removeSilent</code>.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testSilent(Tester t){
    reset();

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

    System.out.println("** silentTune.toString()\n" + silentTune.toString() + "\n\n");
    System.out.println("** badTune.toString()\n" + badTune.toString() + "\n");

  }

  /**
   * <p>Test the methods <code>isSilent</code> and <code>removeSilent</code>.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testAddRemoveChord(Tester t){
    reset();
    
    // test the constructor with channel only
    Tune silentTune = new Tune(4);

    // test the method clearChord
    silentTune.clearChord();
    t.checkExpect(silentTune.isSilent(), true);

    // test the method addChord
    silentTune.addChord(this.aminor);
    t.checkExpect(silentTune.size(), 3);
    t.checkExpect(silentTune.containsNote(new Note("A6n4")), true);
    t.checkExpect(silentTune.containsNote(new Note("G4n2")), false); 
    t.checkExpect(silentTune.containsNote(new Note("E7n1")), true); 
    t.checkExpect(silentTune.isSilent(), false);

    // test the method clearChord
    silentTune.clearChord();
    t.checkExpect(silentTune.isSilent(), true);
    t.checkExpect(silentTune.size(), 0);
  }

  // run the tests and run the game
  public static void main(String[] argv){
    ExamplesTuneTests est = new ExamplesTuneTests();
    Tester.runReport(est, false, false);
  }

}