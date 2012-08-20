package impworldtests;

import java.awt.Color;

import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

/**
 * Copyright 2012 Viera K. Proulx, Matthias Felleisen
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 *
 * The classical "Worm" or "Snake" game - adopted from the example in HtDP
 * by Matthias Felleisen
 * 
 */ 

//------------------------------------------------------------------------------
//represent the world of a Worm Game: Worm, Food, and the Bounding Box
public class WormWorld extends World {

  protected Worm w;
  protected Food f;
  protected Box  b;

  public WormWorld(Worm w, Food f, Box b) {
    this.w = w;
    this.f = f;
    this.b = b;
  }

  // what happens when the clock ticks
  public void onTick() {
    //System.out.println("Tick");
    this.move(this.w.move(this.w.whereTo()));
  }

  // what happens when the player presses a key
  public void onKeyEvent(String ke) {
    //System.out.println("Key event " + ke);
    this.move(this.w.move(ke));
  }

  // what happens when the player presses a key
  public void onMouseClicked(Posn loc) {
    //System.out.println("Worm at (" + this.w.head.x + ", " + this.w.head.y + ")");
    //System.out.println("Mouse click at (" + loc.x + ", " + loc.y + ")");
    this.f.moveTo(loc);
  }
  
  // see where the worm moved to - and proceed with the game 
  protected void move(Worm newWorm) {
    
   if (newWorm.canEat(this.f)){
      this.w = newWorm.eat(this.f);
      this.f = this.f.create(this.b);
    }

    // worm just moves ahead
    else // move the worm
      this.w = newWorm;
  }

  /**
   * Check whether the worm ate itself or ran into itself.
   */
  public WorldEnd worldEnds (){
    // worm ate itself
    if (w.ateItself())
      return 
      new WorldEnd(true,
          new OverlayImages(this.makeImage(),
          new TextImage(new Posn(100, 40), "Your worm ate itself.", 
                        13, Color.red)));
    else if (w.ranIntoWall(this.b))
      return 
      new WorldEnd(true, 
          new OverlayImages(this.makeImage(),
          new TextImage(new Posn(100, 40), "Your worm ran into a wall.", 
                        13, Color.red)));
    else
      return
      new WorldEnd(false, this.makeImage());
  }

  // produce an image of this world
  public WorldImage makeImage() {
    return 
    new OverlayImages(this.b.drawImage(),
    new OverlayImages(this.w.drawImage(),f.drawImage()));
  }

  public static void main(String[] argv){
    WormWorld ww = new WormWorld(
        new Worm(new Segment(50, 60, "up"),
            new MtSegment()), 
            new Food(20, 20), 
            new Box(200, 200));

    ww.bigBang(200, 200, 0.5);
  }

}

//-----------------------------------------------------------------------------
//Worm is a head segment plus a potentially empty sequence of segments

class Worm {
  Segment head;
  ListSegment body;

  public Worm(Segment head, ListSegment body) {
    this.head = head;
    this.body = body;
  }

  // where is the head of the worm?
  protected Posn posn() {
    return new Posn(this.head.x,this.head.y);
  }

  // where is this going?
  protected String whereTo() {
    return this.head.whereTo();
  }

  // did this worm run into the wall?
  protected boolean ranIntoWall(Box b) {
    return !b.inside(this.posn());
  }

  // move this worm in the direction of the keyevent
  //   if ke is in left, right, up, down
  // head moves in the direction of the predecessor
  // body element n+1 moves into the position of element n
  // body element 0 moves into the position of head
  protected Worm move(String ke) {
    if (this.head.move(ke).equal(this.head))
      return this; // the key was not a directional change
    else
      return new Worm(this.head.move(ke),this.body.move(this.head));
  }


  // did this worm run into itself after a move?
  protected boolean ateItself() {
    return this.body.overlap(this.head);
  }

  // can the head of this worm eat the food?
  protected boolean canEat(Food f) {
    return this.head.canEat(f);
  }

  // move the head into the direction into which it is going already
  // the old head becomes part of the body
  // @pre: canEat(f)
  // @post: !ateItself()
  protected Worm eat(Food f) {
    return new Worm(this.head.move(this.whereTo()),
        new ConsSegment(this.head,this.body));
  }


  // make an image of this nonempty list of segments
  protected WorldImage drawImage(){
    return 
    new OverlayImages(this.head.drawImage(), this.body.drawImage());
  }
}


//-----------------------------------------------------------------------------
//represents a segment of the worm, represented as solid disk located at (x,y) 
class Segment {
  int x;
  int y;
  String direction;

  int radius;
  IColor color;
  int DX;
  int DY;

  public Segment(int x, int y, String d) {
    this.x = x;
    this.y = y;
    this.radius = 5;
    this.color = new Red();
    this.DX = 2 * this.radius;
    this.DY = 2 * this.radius;
    this.direction = d;
  }

  // where is it?
  protected Posn posn() {
    return new Posn(this.x,this.y);
  }

  // where is it going?
  protected String whereTo() {
    return this.direction;
  }

  // is this segment equal to that?
  protected boolean equal(Segment that) {
    return this.x == that.x && this.y == that.y && this.direction == that.direction;
  }

  // move in the direction of ke as specified if ke is in left, right, up, down
  protected Segment move(String ke) {
    if (ke.equals("left"))
      return new Segment(this.x-this.DX,this.y,ke);
    else if (ke.equals("right"))
      return new Segment(this.x+this.DX,this.y,ke);
    else if (ke.equals("up"))
      return new Segment(this.x,this.y-this.DY,ke);
    else if (ke.equals("down"))
      return new Segment(this.x,this.y+this.DY,ke);
    else // don't move
      return this;   
  }

  // does _this_ segment overlap with segment s
  protected boolean overlap(Segment s) {
    return this.distance(this.x-s.x,this.y-s.y) < 2 * this.radius;
  }

  // can this (head) segment eat the food if it moves in the direction of ke
  protected boolean canEat(Food f) {
    // is there an overlap of the two geometric shapes
    // approximation: is any of the four corners of the food inside s?
    return f.inside(this.posn());
  }
  
  protected WorldImage drawImage(){
    return new DiskImage(new Posn(this.x,this.y),this.radius,this.color);
  }

  // --- auxiliaries that are only needed for this class: motivate private ---

  // is the Posn inside the disk represented by this segment
  private boolean inside(Posn p) {
    return this.distance(this.x-p.x,this.y-p.y) <= this.radius;
  }

  // how far is (x,y) from the origin
  private double distance(int x, int y) {
    return Math.sqrt(x*x+y*y);
  }
}


//-----------------------------------------------------------------------------
//a list of worm segments that represent the worm's body
//composite pattern: it is a composite because every segment behaves the same way
class MtSegment extends ListSegment {
  public MtSegment() {}

  protected boolean overlap(Segment s) { return false; }

  protected ListSegment move(Segment pred) { return new MtSegment(); }
 
  // make an image of this empty segment
  protected WorldImage drawImage(){
    return new RectangleImage(new Posn(0, 0), 0, 0, new Blue());
  }
}

//-----------------------------------------------------------------------------
//a list of worm segments that represent the worm's body
//composite pattern: it is a composite because every segment behaves the same way
class ConsSegment extends ListSegment {
  Segment first;
  ListSegment rest;

  public ConsSegment(Segment first, ListSegment rest) {
    this.first = first;
    this.rest = rest;
  }

  protected boolean overlap(Segment s) {
    return this.first.overlap(s) || this.rest.overlap(s);
  }

  protected ListSegment move(Segment pred) {
    return new ConsSegment(pred,this.rest.move(this.first));
  }
  
  // make an image of this nonempty list of segments
  protected WorldImage drawImage(){
    return 
    new OverlayImages(this.first.drawImage(), this.rest.drawImage());
  }

}



//-----------------------------------------------------------------------------
//a list of worm segments that represent the worm's body
//composite pattern: it is a composite because every segment behaves the same way
abstract class ListSegment {

  // does s overlap with any of the segments in _this_ body
  protected abstract boolean overlap(Segment s);

  // move each segment in this list of segments into the position of its
  // predecessor, given the new position for the predecessor (head originally)
  protected abstract ListSegment move(Segment pred);

  // make an image of this list of segments
  protected abstract WorldImage drawImage();
}


//-----------------------------------------------------------------------------
//represents a piece of food as a solid rectangle with northwest corner at (x,y)
class Food {
  int x;
  int y;

  int width;
  int height;
  IColor color;

  public Food(int x, int y) {
    this.x = x;
    this.y = y;
    this.width = 20;
    this.height = 10;
    this.color = new Green();
  }
  
  public void moveTo(Posn p){
    this.x = p.x;
    this.y = p.y;
  }
  
  // is p inside of this Food shape
  protected boolean inside(Posn p) {
    return
    (this.x - this.width/2 <= p.x && p.x <= this.x + this.width/2) &&
    (this.y - this.height/2 <= p.y && p.y <= this.y + this.height/2);
  }

  // create a food pile that is at a different location than this (trial and error)
  protected Food create(Box b) {
    return this.createAux(
        b,
        (this.x + 22) % (b.width - 20),
        (this.y + 33) % (b.height - 30));
  }

  // make sure the new food is different from the last one
  protected Food createAux(Box b, int x, int y) {
    if ((this.x != x) && this.y != y)
      return new Food(x,y);
    else
      return this.create(b);
  }
  
  // make an image of this food
  protected WorldImage drawImage(){
    return new RectangleImage(
        new Posn(this.x,this.y),this.width,this.height,this.color);
  }
}

//-----------------------------------------------------------------------------
//Box: represents the playing field for the worm game

class Box {
  int width;
  int height;

  protected Box(int width, int height) {
    this.width = width;
    this.height = height;   
  }

  // is p inside of _this_ box?
  protected boolean inside(Posn p) {
    return
    (0 <= p.x && p.x <= this.width) &&
    (0 <= p.y && p.y <= this.height);
  }
  
  // make an image of this box
  protected WorldImage drawImage(){
    return new RectangleImage(new Posn(100, 100), width, height, new Blue());
  }
}

