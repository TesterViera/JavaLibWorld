package tunesdemoapplet;


import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.tunes.*;
import tester.*;

import java.awt.Color;
import java.util.*;

/**
 * A comprehensive demo of the javalib.soundworld and javalib.tunes package.
 * 
 * @author Viera K. Proulx
 * @since 15 August 2012
 */

//-----------------------------------------------------------------------------
// This examples verifies that two worlds can coexist and deal with their
// respective events without a conflict.
public class ExamplesTunesDemo implements SoundConstants{
  
  ExamplesTunesDemo(){}
  
  WorldCanvas c = new WorldCanvas(600, 400);
  Keyboard kb = new Keyboard();
  InstrumentBoard ib = new InstrumentBoard();
  PianoRoll pr = new PianoRoll();
  
  /**
   * Visual test for displaying the three components of this world
   */
  void showKeyboard(){
    this.c.show();
    this.c.drawImage(this.kb.keyboardImage());
    this.c.drawImage(this.ib.instrumentBoardImage());
    this.c.drawImage(this.pr.pianoRollImage());
  }

  /**
   * Tests for the methods in the <code>RollChord</code> class
   * @param t the tester that runs the tests
   */
  void testRollChord(Tester t){
    // check that the silent chord is generated correctly
    RollChord rcsilent = RollChord.noplay;
    t.checkExpect(rcsilent, new RollChord());
    
    // check the constructor that accepts a list of integers
    RollChord rc = new RollChord(60, 64, 67);
    ArrayList<Note> chordnotes = new ArrayList<Note>();
    chordnotes.add(new Note(60));
    chordnotes.add(new Note(64));
    chordnotes.add(new Note(67));
    t.checkExpect(rc.notes, chordnotes);
    
    // check the constructor that accepts a list of Note-s
    rc = new RollChord(new Note(60), new Note(64), new Note(67));
    t.checkExpect(rc.notes, chordnotes);
    
    // test the 'playing' method
    rc.playing();
    t.checkExpect(rc.col, RollChord.playColor);
    
    // test the 'notPlaying method
    rc.notPlaying();
    t.checkExpect(rc.col, RollChord.rollColor);

    // test the constructor that accepts a list of String-s
    rc = new RollChord("C4n1", "E4n1", "G4n1");
    t.checkExpect(rc.notes, chordnotes);
    
    // test the 'copy' method for the class Chord
    Chord rc1 = new Chord("C4n1", "E4n1", "G4n1");
    Chord rc2 = rc1.copyChord();
    t.checkExpect(rc1.equals(rc2), true);
    t.checkExpect(rc1, rc2);
  }
  
  /**
   * Tests for the methods in the <code>PianoRoll<code> class
   * 
   * @param t the tester that runs the tests
   */
  void testPianoRoll(Tester t){
    // check that piano roll has been initialized correctly
    t.checkExpect(this.pr.melody.size(), 78);

    // check a couple of chords in the piano roll
    RollChord ch = this.pr.melody.get(18);
    t.checkExpect(ch, new RollChord(noteE));
    ch = this.pr.melody.get(22);
    t.checkExpect(ch, new RollChord(noteC));
    
    // test the method initPianoRoll
    this.pr.initMelody(1);

    // check that piano roll has been initialized correctly
    t.checkExpect(this.pr.melody.size(), 85);
    t.checkExpect(this.pr.tune, 1);

    // check a couple of chords in the piano roll
    ch = this.pr.melody.get(18);
    t.checkExpect(ch, new RollChord(noteC));
    ch = this.pr.melody.get(22);
    t.checkExpect(ch, new RollChord(noteE));
    
    // check that the current an next were set up correctly
    t.checkExpect(this.pr.current, 0);
    t.checkExpect(this.pr.next, 0);
    
    // test the method initPianoRoll
    this.pr.initMelody(3);
    t.checkExpect(this.pr.tune, 3);
    
    // test the method 'playPianoRoll'
    this.pr.playPianoRoll();
    t.checkExpect(this.pr.current, 0);
    t.checkExpect(this.pr.next, 1);

    // test the method 'playReversePianoRoll'
    this.pr.playReversePianoRoll();
    t.checkExpect(this.pr.current, 0);
    t.checkExpect(this.pr.next, -1);
    
    // test the method 'stopPianoRoll'
    this.pr.stopPianoRoll();
    t.checkExpect(this.pr.current, 0);
    t.checkExpect(this.pr.next, 0);

    // test the method 'nextChord'
    this.pr.playPianoRoll();
    ch = this.pr.nextChord();
    RollChord rc = new RollChord(60, 64, 67);
    rc.col = RollChord.playColor;
    t.checkExpect(this.pr.current, 1);
    t.checkExpect(this.pr.next, 1);
    t.checkExpect(ch.notes, rc.notes);
    t.checkExpect(ch, rc);
    
    // play one more chord and check the status
    ch = this.pr.nextChord();
    rc = new RollChord(60, 64, 67);
    rc.col = RollChord.playColor;
    t.checkExpect(this.pr.nextChord(), rc);

    // test the method pausePianoRoll
    t.checkExpect(this.pr.current, 3);
    this.pr.pausePianoRoll();
    t.checkExpect(this.pr.current, 3);
    t.checkExpect(this.pr.next, 0);
  }
  

  /**
   * Tests for the methods in the <code>Keyboard<code> class
   * 
   * @param t the tester that runs the tests
   */
  void testKeyboard(Tester t){
    t.checkExpect(this.kb.keys.get(1), new Key(new Posn(43, 70), "s", "D", 62));
    t.checkExpect(this.kb.keyIndex("s"), 1);
    t.checkExpect(this.kb.playNote(1), new Note("D4n16"));
    t.checkExpect(this.kb.keys.get(1).col, Color.red);
    this.kb.stopNote(1);
    t.checkExpect(this.kb.keys.get(1).col, Color.black);
    t.checkExpect(this.kb.keys.get(1).pitch, 62);

    t.checkExpect(this.kb.keys.get(2).col, Color.black);
    this.kb.keys.get(2).playKey();
    t.checkExpect(this.kb.keys.get(2).col, Color.red);
    this.kb.keys.get(2).stopPlayKey();
    t.checkExpect(this.kb.keys.get(2).col, Color.black);

    t.checkExpect(this.kb.keys.get(5), new Key(new Posn(135, 70), "h", "A", 69));
    t.checkExpect(this.kb.keys.get(5).col, Color.black);
    this.kb.keys.get(5).playKey();
    t.checkExpect(this.kb.keys.get(5).col, Color.red);
    this.kb.keys.get(5).stopPlayKey();
    t.checkExpect(this.kb.keys.get(5).col, Color.black);

    t.checkExpect(this.kb.keys.get(6).col, Color.black);
    this.kb.keys.get(6).playKey();
    t.checkExpect(this.kb.keys.get(6).col, Color.red);
    this.kb.keys.get(6).stopPlayKey();
    t.checkExpect(this.kb.keys.get(6).col, Color.black);
  }
  
  /**
   * Test for the methods for the <code>InstrumentBoard</code> class
   * 
   * @param t the tester that runs the tests
   */
  void testInstrumentBoard(Tester t){
    // test the method that provides the NW corner of the selected choice box
    t.checkExpect(this.ib.getNW(0), new Posn(471, 31));
    t.checkExpect(this.ib.getNW(3), new Posn(471, 91));
    
    // test the method move for the class InstrumentBoard
    Posn p1 = new Posn(20, 30);
    t.checkExpect(this.ib.move(p1, 5, -10), new Posn(25, 20));
    
    // test the method that provides the position for the given label
    t.checkExpect(this.ib.getStringPos(0), new Posn(494, 69));
    t.checkExpect(this.ib.getStringPos(3), new Posn(494, 129));
    
    // test the selectInstrument method for several mouse click locations
    t.checkExpect(this.ib.selectInstrument(new Posn(475, 5), SAX), SAX);
    t.checkExpect(this.ib.selectInstrument(new Posn(475, 55), SAX), 0);
    t.checkExpect(this.ib.selectInstrument(new Posn(475, 115), SAX), 3);
    t.checkExpect(this.ib.selectInstrument(new Posn(495, 95), SAX), SAX);
    t.checkExpect(this.ib.selectInstrument(new Posn(470, 385), SAX), SAX);
  }
  
  
  public static void main(String[] argv){
    ExamplesTunesDemo e = new ExamplesTunesDemo();
    e.showKeyboard();
    
    Tester.runReport(e,false,false);
    
    TunesWorld tw = new TunesWorld();
    tw.bigBang(600, 400, 0.2);
  }
}