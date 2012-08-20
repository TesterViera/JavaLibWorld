package javalib.tunes;

import javalib.worldimages.DiskImage;


/**
 * Copyright 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <p>A class to represent one note: its pitch and duration.</p>
 * <p>Automatic conversion to and from a <String> representation.</p>
 * 
 * <p>The <code>String Nomd</code> represents note <code>name</code> <em>N</em>
 * in the octave <em>o</em>, modified by <em>m</em>, of duration <em>d</em>, where 
 * <ul>
 * <li> Note name --- one of <code>A B C D E F G</code></li>
 * <li> Note octave --- one digit: the octave on the keyboard</li>
 * <li> Modifier --- <strong>n</strong> natural, 
 *                   <strong>s</strong> sharp, 
 *                   <strong>f</strong> flat</li>
 * <li> Duration --- the number of beats given as a number 
 *                   in the range from 1 to 16</li>
 * </ul>
 * 
 * @author Viera K. Proulx
 * @since 5 June 2010
 */
public class Note{
  /** the <code>MIDI</code> pitch of this note: range from 21 to 108 */
  public int pitch;
  
  /** the duration: maximum of 16 beats for a whole note in a 4/4 measure */
  public int duration;
  
  /** a letter that represents this note ('A' through 'G') */
  public char noteName;
  
  /** 's' sharp, 'f' flat, 'n' natural */
  public char modifier;
  
  /** the <code>MIDI</code> octave on a piano keyboard (0 - 8) */
  public int octave;
  
  /** the <code>String</code> representation of this note</p> */
  public String snote;
  
  /** a representation of offsets of notes A B C D E F G from A */
  private static int[] noteMap = new int[]{0, 2, 3, 5, 7, 8, 10};

  /** 
   * <p>a representation of note names 
   * A a B C c D d E F f G g from the pitch.
   * Lower case letters denote note plus 'sharp' modifier.</p>
   * <p>The first note is 'E', as "E0" is a MIDI note 0 (unused)</p>
   */
  private static Character[] noteNameMap = 
    new Character[]{'E', 'F', 'f', 'G', 'g', 'A', 'a', 
                    'B', 'C', 'c', 'D', 'd'};
  
  /** the numeric value for <code>Character 'A'</code> 
   * to use in conversions */
  private static int charA = Character.getNumericValue('A');
  
  /**
   * <p>A constructor that accepts a note given as its <code>MIDI</code>
   * pitch and duration in the number of beats (in the range from 1 to 16).</p>
   * <p>The fields that describe the note name, modifier, octave, as well
   * as a <code>String</code> representation of the note are initialized.</p>
   * <p>The constructor guarantees that the pitch and duration are valid.</p>
   * 
   * @param pitch
   * @param duration
   */
  public Note(int pitch, int duration){
    this.pitch = this.adjustPitch(pitch);
    this.duration = this.adjustDuration(duration);
    this.initNote(this.pitch, this.duration);
  }  
  
  /**
   * <p>A constructor that accepts a note given as its <code>MIDI</code>
   * pitch and duration in the number of beats (in the range from 1 to 16).</p>
   * <p>The fields that describe the note name, modifier, octave, as well
   * as a <code>String</code> representation of the note are initialized.</p>
   * <p>The constructor guarantees that the pitch and duration are valid.</p>
   * 
   * @param pitch
   */
  public Note(int pitch){
    this.initNote(pitch);
  }
  
  /**
   * Reject pitch below 0 or above 128 - just set it within bounds
   * @param pitch the given pitch
   * @return the pitch within the bounds [0, 128]
   */
  private int adjustPitch(int pitch){
    if (pitch < 12)
      return 0;
    else if (pitch > 128)
      return 128;
    else
      return pitch;
  }
  
  /**
   * Reject duration below 0 or above 16 - just set it within bounds
   * @param duration the given duration
   * @return the duration within the bounds [0, 16]
   */
  private int adjustDuration(int duration){
    if (duration < 0)
      return 0;
    else if (duration > 16)
      return 16;
    else
      return duration;
  }
  
  /**
   * <p>A constructor that accepts note description encoded
   * as a <code>String</code>.</p>
   * <p>The encoding consists of four or five characters, 
   * interpreted as follows:
   * 
   * <ul>
   * <li> Note name --- one of <code>A B C D E F G</code></li>
   * <li> Note octave --- one digit: the octave on the keyboard</li>
   * <li> Modifier --- <strong>n</strong> natural, 
   *                   <strong>s</strong> sharp, 
   *                   <strong>f</strong> flat</li>
   * <li> Duration --- the number of beats given as a number 
   *                   in the range from 1 to 16</li>
   * </ul>
   * 
   * <p>for example <code>C6n4</code> represents a C natural,
   * MIDI note 72, that plays for 4 beats</p>
   * 
   * @param snote The note encoding as a <code>String</code>
   */
  public Note(String snote){
    this.initNote(snote);
  }
  
  /**
   * initialize the note from its representation as a <code>String</code>
   * @param snote the <code>String</code> representation of the note
   */
  private void initNote(String snote){
    this.snote = snote;
    this.noteName = this.adjustNoteName(snote.charAt(0));
    this.modifier = this.adjustModifier(snote.charAt(2));
    this.octave = this.adjustOctave(Character.digit(snote.charAt(1), 10));
    this.duration = Character.digit(snote.charAt(3), 10);
    if (snote.length() == 5)
      this.duration = 10 * this.duration + 
                      Character.digit(snote.charAt(4), 10);
    this.pitch = 
      this.computePitch(this.noteName, 
                        this.octave, 
                        this.modifier);
  }
  
  /**
   * Make sure the note name is a valid note name.
   * Set the invalid note name to <code>'A'</code>.
   * @param noteName the given note name
   * @return a valid note name
   */
  private Character adjustNoteName(char noteName){
    if (noteName == 'A' ||
        noteName == 'B' ||
        noteName == 'C' ||
        noteName == 'D' ||
        noteName == 'E' ||
        noteName == 'F' ||
        noteName == 'G')
      return noteName;
    else
      return 'A';
  }
  
  /**
   * Make sure the note modifier is a valid modifier.
   * Set the invalid modifier name to <code>'n'</code>.
   * 
   * @param mod the given modifier
   * @return a valid modifier
   */
  private Character adjustModifier(char mod){
    if (mod == 'n' ||
        mod == 's' ||
        mod == 'f')
      return mod;
    else
      return 'n';
  }
  
  /**
   * Make sure the octave is a valid octave.
   * Set the invalid octave name to <code>'0'</code>.
   * 
   * @param oct the given octave
   * @return a valid octave
   */
  private int adjustOctave(int oct){
    if (oct < 0 ||
        oct > 8)
      return 0;
    else
      return oct;
  }
  
  /**
   * Compute the MIDI pitch of the given note.
   * @param noteName --- one of: A B C D E F G
   * @param modifier --- s (sharp), f (flat), n (natural)
   * @param octave --- between 1 and 8 -- on the keyboard
   * @return the MIDI pitch number 
   */
  public int computePitch(Character noteName, int octave, Character modifier){
    int base = 
      noteMap[Character.getNumericValue(noteName) - charA];
    int note = base + 12 * octave + 9;
    if (modifier == 's')
      return note + 1;
    else if (modifier == 'f')
      return note - 1;
    else
      return note;
  }
  
  /**
   * Set the values of the <code>noteName</code>, <code>modifier</code>,
   * <code>octave</code> and <code>snote</code> fields from the given 
   * <code>pitch</code> and <code>duration</code>.
   * 
   * @param pitch the given pitch
   * @param duration the given duration
   */
  void initNote(int pitch, int duration){
    if (pitch == 0){
      this.duration = duration;
      this.pitch = pitch;
      this.octave = 0;
      this.noteName = 0;
      this.modifier = 'n';
      this.snote = "A0n0";
    }
    else{
      this.duration = duration;
      this.pitch = pitch;
      this.octave = pitchToOctave(pitch);
      this.noteName = noteNameMap[(pitch + 8) % 12];
      if (Character.isLowerCase(this.noteName)){
        this.modifier = 's';
        this.noteName = Character.toUpperCase(this.noteName);
      }
      else
        this.modifier = 'n';
      this.snote = "" + this.noteName + 
      this.octave + this.modifier + this.duration;
    }
  }
  
  int pitchToOctave(int pitch){
    if (pitch == 0)
      return 0;
    else
      return (pitch - 8) / 12;
  }
  
  /**
   * Set the values of the <code>noteName</code>, <code>modifier</code>,
   * <code>octave</code> and <code>snote</code> fields from the given 
   * <code>pitch</code> using <code>duration = 1</code>.
   * 
   * @param pitch the given pitch
   */
  void initNote(int pitch){
    if (pitch == 0){
      initNote(0, 0);
    }
    else{
      initNote(pitch, 1);
    }
  }
  
  /**
   * Does the given <code>Note</code> represent the same note as this one?
   * Note, for example, that <code>"E4s2"</code> 
   * is the same as <code>"F4n2"</code> and 
   * <code>"G4s2"</code> is the same as <code>"A4f2"</code>.
   * 
   * @param that the <code>Note</code> to compare with this one
   * @return true if the two notes have the same pitch and duration
   */
  public boolean sameNote(Note that){
    return this.pitch == that.pitch &&
           this.duration == that.duration;
  }
  
  /**
   * Did this note stop playing?
   * 
   * @return true if the duration has expired
   */
  public boolean isSilent(){
    return this.duration == 0;
  }
  
  /**
   * decrease the duration of this note - measuring one beat
   */
  public void nextBeat(){
    if (this.duration > 0)
      this.duration = this.duration - 1;
    else
      this.duration = 0;
    this.snote = "" + this.noteName + 
    this.octave + this.modifier + this.duration;
    //this.initNote(this.pitch, this.duration - 1);
  }

  /**
   * increase the duration of this note by one - adding a beat
   */
  public void skipBeat(){
    this.duration = this.duration + 1;
    this.snote = "" + this.noteName + 
    this.octave + this.modifier + this.duration;
  }
  
  /**
   * Produce the representation of this <code>Note</code> 
   * as a <code>String</code>
   */
  public String toString(){
    return this.toIndentedString("");
  }
  
  /**
   * Produce the representation of this <code>Note</code> 
   * as a <code>String</code> indented as specified
   *
   * @param indent a <code>String</code> of the desired number of spaces for
   * indentation
   * @return well formatted indented <code>String</code> that represents this 
   * <code>Note</code>
   */
  public String toIndentedString(String indent){
    return "new Note(" +
        "\n" + indent + "this.pitch = " + this.pitch + "," +
        "\n" + indent + "this.duration = " + this.duration + "," + 
        "\n" + indent + "this.noteName = " + this.noteName + "," +
        "\n" + indent + "this.modifier = " + this.modifier + "," + 
        "\n" + indent + "this.octave = " + this.octave + "," +
        "\n" + indent + "this.snote = " + this.snote + ")\n";
  }
  
  /**
   * Is this <code>Note</code> same as the given object?
   */
  public boolean equals(Object o){
    if (o instanceof Note){
      Note that = (Note)o;
      return this.pitch == that.pitch 
        && this.duration == that.duration
        && this.noteName == that.noteName
        && this.octave == that.octave;
    }
    else 
      return false;
  }
  
  /**
   * The hashCode to match the equals method
   */
  public int hashCode(){
    return this.pitch * this.duration + this.noteName +
        this.octave; 
  }
  
}