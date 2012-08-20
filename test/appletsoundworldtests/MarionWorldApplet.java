package appletsoundworldtests;

import javalib.appletsoundworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

import java.util.*;

//Created by Viera Proulx on 4/2/2012.
//LGPL license.

public class MarionWorldApplet extends WorldApplet{

  /** Produce a new MarionWorld */
  public World getNewWorld(){    
    return new Marion();
  }

  /** Set the size of this world */
  public void setWorldSize(){
    this.WIDTH = 200;
    this.HEIGHT = 200;
  }
  
  /** Set the speed to be twice the normal, so the music plays better */
  public double setSpeedFactor(){
    return 4.0;
  }
}

class Marion extends World{
  ArrayList<Integer> obstacles = new ArrayList<Integer>();
  int mario; // Marion's height - horizontal is always in the middle
  int COUNTER; // the tick counter
  int tunelength = 33 * 4;
  int tickCount = 0;

  Marion(){
    this.initWorld();
    this.mario = 50;
    this.COUNTER = 0;
  }

  void initWorld(){
    for (int i = 1; i <= 20; i++){
      this.obstacles.add(randomInt(150));
    }
  }

  /** helper method to generate a random number in the range 0 to n */
  int randomInt(int n){
    return new Random().nextInt(n);
  }

  void moveObstacles(){
    this.obstacles.remove(0);
    this.obstacles.add(randomInt(150));
  }

  // does Marion collide with the middle obstacle?
  boolean collide(){
    return this.mario <= this.obstacles.get(10);   
  }

  // move the queue, check for collision, play the tune
  // Marion falls down slowly
  public void onTick(){
    this.moveObstacles();
    this.COUNTER = this.COUNTER + 1;
    this.tickTunes.addNote(CHOIR, jeopardyNotes[this.tickCount]);
    this.tickCount = (this.tickCount + 1) % this.tunelength;
    //if (collide())
    //  this.endOfWorld("Good Bye"); 
    this.mario = this.mario + 3;
  }

  public void onKeyEvent(String ke){
    if (ke.equals("up")){
      this.mario = this.mario - 10;
      this.keyTunes.addNote(BAGPIPE, noteG);
      //this.keyTunes.addNote(BIRD_TWEET, noteG);
    }
  }

  // produce the image of this Marion's world
  public WorldImage makeImage(){
    return 
    this.obstaclesImage().overlayImages(
        new DiskImage(new Posn(100, this.mario), 10, new Red()));
  }
  // produce the image of all obstacles on Marion's journey
  public WorldImage obstaclesImage(){
    WorldImage oImage = 
        new RectangleImage(new Posn(100, 100), 200, 200, new White());
    for (int i = 0; i < 20; i++){
      oImage = oImage.overlayImages(
        new RectangleImage(new Posn(i * 10 - 10, 200 - this.obstacles.get(i)/2),
                           20, this.obstacles.get(i), new Black()));
    }
    return oImage;
  }

  /**
   * The last image to draw - with the provided message
   */
  public WorldImage lastImage(String s){
    return
    this.makeImage().overlayImages(
        new TextImage(new Posn(150, 80), s, 
            13, 3, new Red()));
  }

  public static int jeopardyNotes[] = {
    0,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,noteC,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,0,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,noteUpC,0,0,0,
    noteUpE,0,0,0,0,0,noteUpD,0,noteUpC,0,noteB,0,noteA,0,noteG,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,noteC,0,0,0,
    noteG,0,0,0,noteUpC,0,0,0,noteG,0,0,0,0,0,0,0,
    noteUpC,0,0,0,0,0,noteA,0,noteG,0,0,0,noteF,0,0,0,noteE,0,0,0,noteD,0,0,0,noteC,0,0,0,
    0,0,0,0
  };


  public static void main(String[] arv){
    Marion world = new Marion();
    world.bigBang(200, 200, 0.1);
  }
}