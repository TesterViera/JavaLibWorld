package soundworldtests;

import javalib.soundworld.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.colors.*;
import javalib.tunes.*;
import tester.*;

import java.util.*;


/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * A silly jumping game - very primitive
 * 
 * Illustrates how to play the 'Jeopardy" tune
 * and how to simulate a moving background.
 * 
 * @author Viera K. Proulx
 * @since 26 June 2012
 */
class Mario2 extends World{
  ArrayList<Integer> obstacles = new ArrayList<Integer>();
  int mario; // Mario's height - horizontal is always in the middle
  int COUNTER; // the tick counter
  int tunelength = 33 * 4;
  int tickCount = 0;

  Mario2(){
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

  // does Mario collide with the middle obstacle?
  boolean collide(){
    return this.mario <= this.obstacles.get(10);   
  }

  // move the queue, check for collision, play the tune
  // Mario falls down slowly
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
  
  // produce the image of this Mario world
  public WorldImage makeImage(){
    return 
    this.obstaclesImage().overlayImages(
        new DiskImage(new Posn(100, this.mario), 10, new Red()));
  }
  // produce the image of all obstacles on Mario's journey
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
    Mario2 world = new Mario2();
    world.bigBang(200, 200, 0.1);
  }
}