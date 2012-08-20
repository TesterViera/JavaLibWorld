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
 * <p>Tests for the Chord class.</p>
 * 
 * @author Viera K. Proulx
 * @since 15 June 2012
 */

public class ExamplesChordTests implements SoundConstants{
  ExamplesChordTests(){}
  
  // support for the regression tests
  public static ExamplesChordTests examplesInstance = new ExamplesChordTests();

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
  
  void reset(){
    /** a sample note:  */
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
  }

  /**
   * <p>Test the constructors for the Chord class.</p>
   * <p>Test the method addNote (all variants).</p>
   * <p>Test the method size.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testChordConstructorsMethods(Tester t){
    this.reset();
    
    // test the equals method for Chord-s - ignores order of the notes
    Chord cmajorx = new Chord(this.E6n2, this.C6n4, this.C6n4);
    Chord cmajory = new Chord(this.C6n4, this.E6n2, this.C6n4);
    t.checkExpect(cmajorx, cmajory);

    // test the constructor that consumes Strings:
    t.checkExpect(this.cmajor1.notes.get(0), new Note(84, 4));
    t.checkExpect(this.cmajor1.notes.get(1), new Note("E6n2"));
    this.cmajor1.addNote("G6n1");
    t.checkExpect(this.cmajor1.notes.get(2), new Note(91, 1));

    // test the method containsNote
    t.checkExpect(this.cmajor1.containsNote(new Note(84, 4)), true);
    t.checkExpect(this.cmajor1.containsNote(new Note(85, 4)), false);
    t.checkExpect(this.cmajor1.containsNote(new Note(91, 1)), true);
    
    t.checkExpect(this.cmajor1.containsNote(84), true);

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

    // test the method addNote
    this.cmajor3.addNote(67, 1);
    t.checkExpect(this.cmajor3.notes.get(2), new Note(67));
    t.checkExpect(this.cmajor3.size(), 3);
  }

  /**
   * <p>Test the method that produce a <code>String</code> 
   * representation of a <code>Chord</code>.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testToStringMethods(Tester t){
    this.reset();
    
    // test the methods that produce a String representation of the Chord
    t.checkExpect(this.cmajor1.toSimpleString(),
        "new Chord(C6n4, E6n2)");

    String expected = 
        "new Chord(\n  " + this.C6n4.toIndentedString("    ") + ",\n  " 
            + this.E6n2.toIndentedString("    ");
    // added a . at the end of the String to see it displayed
    t.checkExpect(this.cmajor1.toIndentedString("  "),
        expected.substring(0, expected.length()-1) + ").");

    expected = 
        "new Chord(\n" + this.C6n4.toIndentedString("  ") + ",\n" 
            + this.E6n2.toIndentedString("  ");
    // added a . at the end of the String to see it displayed
    t.checkExpect(this.cmajor1.toString(),
        expected.substring(0, expected.length()-1) + ").");
  }

  /**
   * <p>Test all variants of the method <code>addNote</code>.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testAddNoteMethods(Tester t){   
   
    this.cmajor3 = new Chord(60, 64);
    
    // test the method addNote with int arguments
    this.cmajor3.addNote(67, 1);
    t.checkExpect(this.cmajor3.notes.get(2), new Note(67));
    t.checkExpect(this.cmajor3.size(), 3);
    
    // test the method addNote with Note arguments
    this.cmajor3.addNote(new Note(84, 3));
    t.checkExpect(this.cmajor3.notes.get(3), new Note(84, 3));
    t.checkExpect(this.cmajor3.size(), 4);
    
    // test the method addNote with Note arguments
    this.cmajor3.addNote("E6n1");
    t.checkExpect(this.cmajor3.notes.get(4), new Note("E6n1"));
    t.checkExpect(this.cmajor3.size(), 5);
  }

  /**
   * <p>Test all other method for the <code>Chord</code> class.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testOtherMethods(Tester t){   
    this.reset();
    this.cmajor2.addNote(this.G6n1);
    
    // test the method nextBeat
    this.cmajor1.nextBeat();
    t.checkExpect(this.cmajor1.notes.get(0), new Note(84, 3));
    t.checkExpect(this.cmajor1.notes.get(1), new Note("E6n1"));
    t.checkExpect(this.cmajor1.size(), 2);

    // test the method skipBeat
    this.cmajor2.skipBeat();
    t.checkExpect(this.cmajor2.notes.get(0), new Note(84, 5));
    t.checkExpect(this.cmajor2.notes.get(1), new Note("E6n3"));
    t.checkExpect(this.cmajor2.notes.get(2), new Note(91, 2));

    this.cmajor3 = new Chord(60, 64);
    this.cmajor3.addNote(67, 1);
    
    // test the method isSilent()
    t.checkExpect(this.cmajor3.isSilent(), false);
    this.cmajor3.nextBeat();
    t.checkExpect(this.cmajor3.isSilent(), true);
    
    // test the method removeSilent
    this.cmajor3.addNote(67, 2);
    this.cmajor1.removeSilent();
    t.checkExpect(this.cmajor3.isSilent(), false);
    t.checkExpect(this.cmajor3.size(), 1);   
  }

  /**
   * <p>Test all other method for the <code>Chord</code> class.</p>
   * 
   * @param t the tester that runs the tests
   */
  void testCopyMethod(Tester t){   
    this.reset();
    Chord cmajorCopy = new Chord(new Note("C6n4"), new Note("E6n2"));
    
    // check that the copy is the same
    Chord copy = this.cmajor2.copy();
    t.checkExpect(cmajorCopy, copy);
    
    // mutate the original, see that the copy is unchanged
    this.cmajor2.nextBeat();
    t.checkExpect(cmajorCopy, copy);
    t.checkExpect(cmajor2, new Chord(new Note("C6n3"), new Note("E6n1")));
  }

  /**
   * Run the tests
   * @param argv
   */
  public static void main(String[] argv){
    ExamplesChordTests ect = new ExamplesChordTests();
    Tester.runReport(ect, false, false);
  }

}