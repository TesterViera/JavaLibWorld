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
 * <p>Test suite for the <code>javalib.tunes</code> package.</p>
 * <p>Tests for the Note class.</p>
 * 
 * @author Viera K. Proulx
 * @since 15 June 2012
 */

public class ExamplesNoteTests implements SoundConstants{
  ExamplesNoteTests(){}
  
  // support for the regression tests
  public static ExamplesNoteTests examplesInstance = new ExamplesNoteTests();
  
  /** a sample note:  */
  Note C4n2 = new Note("C4n2");
  Note C4n4 = new Note("C4n4");
  Note C4n2a = new Note(60, 2);
  Note D4f4 = new Note("D4f4");
  Note C4s4 = new Note("C4s4");
  Note C4s4a = new Note(61, 4);
  Note A4n1a = new Note(57, 1);
  Note A4n1b = new Note("A4n1");
  
  /**
   * <p>Test the toString method for the class Note.</p>
   * 
   * @param t
   */
  void testToString(Tester t){
    t.checkExpect(this.C4n2.toString(),
        "new Note(\nthis.pitch = 60," +
        "\nthis.duration = 2," + 
        "\nthis.noteName = C," + 
        "\nthis.modifier = n," +
        "\nthis.octave = 4," +
        "\nthis.snote = C4n2)\n*");  
  }

  /**
   * <p>Test the toIndentedString method for the class Note.</p>
   * 
   * @param t
   */
  void testToIndentedString(Tester t){
    t.checkExpect(this.C4n2.toIndentedString("  "),
        "new Note(" + 
        "\n  this.pitch = 60," +
        "\n  this.duration = 2," + 
        "\n  this.noteName = C," + 
        "\n  this.modifier = n," +
        "\n  this.octave = 4," +
        "\n  this.snote = C4n2)\n*");  
  }
  
  /**
   * <p>Test the method adjustPitch in the class Note.</p>
   * 
   * @param t
   */
  void testAdjustPitch(Tester t){
    t.checkMethod(0, this.A4n1b, "adjustPitch", 11);
    t.checkMethod(128, this.A4n1b, "adjustPitch", 131);
    t.checkMethod(26, this.A4n1b, "adjustPitch", 26);
  }

  /**
   * <p>Test the various constructors in the class Note.</p>
   * 
   * @param t
   */
  void testNoteConstruction(Tester t){
    t.checkExpect(this.C4s4a.toString(),
        "new Note(\nthis.pitch = 61,\nthis.duration = 4,\nthis.noteName = C," +
        "\nthis.modifier = s,\nthis.octave = 4,\nthis.snote = C4s4)\n");
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
    t.checkExpect(this.C4s4.equals(this.D4f4), false);
    
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

  /**
   * Run the tests
   * @param argv
   */
  public static void main(String[] argv){
    ExamplesNoteTests ent = new ExamplesNoteTests();
    Tester.runReport(ent, false, false);
  }
  
}