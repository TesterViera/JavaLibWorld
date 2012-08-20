package tunesdemo;

import javalib.worldimages.*;
import javalib.tunes.*;

import java.awt.Color;
import java.util.*;

/**
 * A comprehensive demo of the javalib.soundworld and javalib.tunes package: 
 * The board that allows the programmer to choose the current instrument.
 * 
 * @author Viera K. Proulx
 * @since 15 August 2012
 */
public class InstrumentBoard implements SoundConstants{
  
  protected boolean programSwitch = true;

  /** the list of instrument names to display */
  protected static ArrayList<String> instrumentNames = 
    new ArrayList<String>(Arrays.asList(
    "PIANO", "ORGAN", "TIMPANI", "VIOLIN", "CLARINET", "STEELDRUM", 
    "CHOIR", "TUBA", "SAX", "PERCUSSION", "WOOD_BLOCK", "BAGPIPE", 
    "BIRD_TWEET", "SEASHORE", "APPLAUSE", "BRASS SECTION"));

  /** the list of alternative instrument names to display */
  protected static ArrayList<String> instrumentAltNames = 
    new ArrayList<String>(Arrays.asList(
    "GUITAR", "PICOLO", "OCARINA", "TRUMPET", "HARPSICHORD", "MARIMBA", 
    "ACCORDION", "HARP", "OBOE", "PERCUSSION", "HELICOPTER", "WHISTLE", 
    "GOBLINS", "BANJO", "STRINGS", "SITAR"));
  
  protected int[] altInstruments = new int[]{
    AcousticGuitar_nylon, Piccolo, Ocarina, Trumpet, Harpsichord, Marimba, 
    Accordion, OrchestralHarp, Oboe, PERCUSSION, Helicopter, Whistle, 
    FX6_goblins, Banjo, StringEnsemble2, Sitar};
  
  /** the list of colors to use for instrument choice boxes */
  protected static ArrayList<Color> colors = 
    new ArrayList<Color>(Arrays.asList(
    Color.red, Color.blue, Color.yellow, Color.green, Color.cyan,
    Color.orange, Color.magenta, new Color(25, 200, 100), 
    new Color(200, 50, 50), Color.pink, 
    new Color(210, 105, 30), new Color(173, 255, 47), 
    new Color(255, 20, 147), new Color(220, 220, 220), 
    new Color(128, 128, 0), new Color(75, 0, 130))); 
  
  /** the NW corner of the panel */
  protected static Posn nw = new Posn(460, 0);
  
  /** the center of the panel */
  protected static Posn center = new Posn(530, 200);
  
  /** x offset for the choice boxes */
  protected static int xInset = 11;
  
  /** y offset for the first choice box */
  protected static int yInset = 51;
  
  /** x offset for the instrument name Strings */
  protected static int xStringInset = 34;
  
  /** y offset for the instrument name String for the first instrument */
  protected static int yStringInset = 69;
  
  /** the size of the choice box (add 1 pixel on each side for separation) */
  protected static int size = 20;
  
  /** the currently selected instrument */
  protected int current = ORGAN;
  
  /**
   * The constructor. All information here is static, except for the current
   * instrument.
   */
  public InstrumentBoard(){}
  
  /**
   * Draw the instrument selection board in a panel at the given location
   * 
   * @param c the <code>Canvas</code> where to draw
   */
  protected WorldImage instrumentBoardImage(){
    // the background rectangle
    WorldImage baseImage = 
        new RectangleImage(center, 140, 400, Color.lightGray);
    
    // the title line and the instructions for use
    baseImage = baseImage.overlayImages(
      // horizontal center is 80 off left edge
      // first line 20 at base, -6 in the middle
      // second line 15 below that
      new TextImage(this.move(nw, 80, 20 - 6), "Instrument Choice", Color.red),
      new TextImage(this.move(nw, 80, 35 - 6), "via mouse click", Color.red));

    // The frame for the currently selected instrument choice
    baseImage = baseImage.overlayImages(
      new RectangleImage(this.move(this.getCenter(this.current), -1, -1), 
          size + 1, size + 1, Color.black));
    
    if (this.programSwitch){
      // the choice boxes and instrument names for all 16 instruments
      for (int i = 0; i < 16; i = i+1){
        baseImage = baseImage.overlayImages(
          new RectangleImage(this.getCenter(i), size - 2, size - 2,
            colors.get(i)),
          new TextImage(this.getStringCenter(i), instrumentNames.get(i), 
                Color.blue));
      }
    } else {
      // the alternate choice boxes and instrument names for all 16 instruments
      for (int i = 0; i < 16; i = i+1){
        baseImage = baseImage.overlayImages(
          new RectangleImage(this.getCenter(i), size - 2, size - 2,
            colors.get(i)),
          new TextImage(this.getStringCenter(i), instrumentAltNames.get(i), 
                Color.blue));
      }
    }
   
    // add the program change button to the display
    baseImage = 
        baseImage.overlayImages(
          new DiskImage(this.getCenter(16), size/2 - 1, Color.black),
          new TextImage(this.getStringCenter(16), "PRGM CHANGE", Color.black));
    
    return baseImage;
  }
  
  
  /**
   * Produce a position offset from the given one by the given dx and dy
   * 
   * @param p the given position
   * @param dx the desired change in the horizontal direction
   * @param dy the desired change in the vertical direction
   * @return the resulting <code>Posn</code>
   */
  protected Posn move(Posn p, int dx, int dy){
    return new Posn(p.x + dx, p.y + dy);
  }
  
  /**
   * Get the position of the nw corner for the choice box 
   * for the given instrument choice
   * 
   * @param index the given instrument choice
   * @return the position of nw corner for this choice box
   */
  protected Posn getNW(int index){
    return new Posn(nw.x + xInset, // + size, 
                    nw.y + yInset + index * (size) - size);
  }
  
  /**
   * Get the position of the center corner for the choice box 
   * for the given instrument choice
   * 
   * @param index the given instrument choice
   * @return the position of nw corner for this choice box
   */
  protected Posn getCenter(int index){
    return new Posn(nw.x + xInset + (size  - xInset) / 2, 
                    nw.y + yInset + index * (size));// + size / 2);
  }
  
  /**
   * Get the position of the nw corner for the annotation for the choice box 
   * for the given instrument choice
   * 
   * @param index the given instrument choice
   * @return the position of nw corner for the desired annotation
   */
  protected Posn getStringPos(int index){
    return new Posn(nw.x + xStringInset, 
                    nw.y + yStringInset + index * (size));
  }
  
  /**
   * Get the position of the nw corner for the annotation for the choice box 
   * for the given instrument choice
   * 
   * @param index the given instrument choice
   * @return the position of nw corner for the desired annotation
   */
  protected Posn getStringCenter(int index){
    return new Posn(nw.x + xStringInset + (140 - xStringInset) / 2, 
                    nw.y + yStringInset + index * (size) - size / 2 - 4);
  }
  
  /**
   * Determine which instrument has been selected by a mouse click.
   * Produce the given instrument if mouse click does not indicate 
   * an instrument selection.
   * 
   * @param mousepos the mouse position when it is clicked
   * @param currentInstrument the current instrument
   * @return the selected instrument
   */
  public int selectInstrument(Posn mousepos, int currentInstrument){
    // verify vertical bounds
    if (mousepos.x < nw.x + xInset || 
        mousepos.x > (nw.x + xInset + size))
      return currentInstrument;
    
    // check for program change
    if (mousepos.y > nw.y + yInset + 16 * size && 
        nw.y + mousepos.y < (yInset + 17 * size))
      return 17;
    
    // check if out of bounds -- return current instrument
    if (mousepos.y < nw.y + yInset || 
        nw.y + mousepos.y > (yInset + 16 * size))
      return currentInstrument;
    
    // now select the desired instrument
    this.current = (mousepos.y - yInset - nw.y) /size;
    return this.current;
  }
  
  /**
   * Produce the instrument choice for the alternative selection
   * @return
   */
  public int[] switchProgram(){
    if (this.programSwitch){
      this.programSwitch = false;
      return this.altInstruments;
    }
    else{
      this.programSwitch = true;
      return instruments;
    }
  }
  
}
