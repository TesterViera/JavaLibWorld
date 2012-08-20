package impworldtests;

import javalib.impworld.*;
import javalib.worldimages.*;

import java.awt.Color;
import java.util.Random;

import tester.*;

import javalib.colors.*;

/**
 * Copyright 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @author Viera K. Proulx
 * @since 5 February 2012
 */

//A class to display one of the ticky-tacky houses on the hillside.
class House{
  Posn loc;    // of the SW corner of the house base
  int width;   // the width of the house
  int height;  // the height of the house
  Color color; // the color of the house
  Person person;
  
  House(Posn loc, int width, int height, Color color){
    this.loc = loc;
    this.width = width;
    this.height = height;
    this.color = color;
    this.person = 
        new Person(new Posn(this.loc.x + this.width / 2,
            this.loc.y - this.height / 4), this.width / 2, this.height / 2,
            this.color);
  }
  
  // make the image of this house
  WorldImage houseImage(){
    return 
    new RectangleImage(
        // the pinhole in the center
        new Posn(this.loc.x + this.width / 2, this.loc.y - this.height / 2), 
        this.width, this.height, this.color).
    overlayImages(
        // the roof - the height is half the height of the house
        new TriangleImage(new Posn(this.loc.x, this.loc.y - this.height), 
                          new Posn(this.loc.x + this.width, this.loc.y - this.height),
                          new Posn(this.loc.x + this.width / 2, 
                                   this.loc.y - 3 * this.height / 2),
                          Color.red),
        // the door - black in the middle
        new RectangleImage(new Posn(this.loc.x + this.width / 2,
                                    this.loc.y - this.height / 4),
                           this.width / 2, this.height / 2, Color.gray),
        this.person.personImage());
  }
}

class Person{
  Posn pinhole;
  int width;
  int height;
  Color color;
  
  Person(Posn pinhole, int width, int height, Color color){
    this.pinhole = pinhole;
    this.width = width;
    this.height = height;
    this.color = color;
  }
  
  // make the image of this house
  WorldImage personImage(){
    return 
    // draw the legs
    new LineImage(this.pinhole, 
        new Posn(this.pinhole.x - this.width / 2, this.pinhole.y + this.height / 2),
        Color.black).overlayImages(
    new LineImage(this.pinhole, 
        new Posn(this.pinhole.x + this.width / 2, this.pinhole.y + this.height / 2),
        Color.black),
    // draw the arms
    new LineImage(this.pinhole, 
        new Posn(this.pinhole.x + this.width / 2, this.pinhole.y),
            Color.black),        
    new LineImage(this.pinhole, 
        new Posn(this.pinhole.x - this.width / 2, this.pinhole.y),
            Color.black),
    // draw the torso
    //new RectangleImage(this.pinhole, this.width / 3, 2 * this.height / 3, 
    //    this.color),
    // draw the head
    new CircleImage(new Posn(this.pinhole.x, this.pinhole.y - this.height / 4), 
        this.height / 4, Color.black));
  }
}
// a class to add some trees to the ticky-tack world
class Tree{
  Posn loc;         // the left base of the trunk
  int trunkHeight;  // the height of the trunk
  int width;        // the width of the crown of the tree
  int height;       // the height of the crown of the tree
  
  Tree(Posn loc, int trunkHeight, int width, int height){
    this.loc = loc;
    this.trunkHeight = trunkHeight;
    this.width = width;
    this.height = height;
  }
  
//make the image of this tree
  WorldImage treeImage(){
    return
    // the trunk
    new RectangleImage(new Posn(this.loc.x + 5, 
                                this.loc.y - this.trunkHeight / 2),
                       10, this.trunkHeight, 
                       new Color(0x84, 0x3c, 0x24)).overlayImages(
    new OvalImage(new Posn(this.loc.x + 5, 
                           this.loc.y - this.trunkHeight - this.height / 2 + 3),
                  this.width, this.height, Color.green));
  }
}

// a white cloud floating over the ticky-tack houses
class Cloud{
  Posn loc;
  int width;
  int height;
  
  Cloud(Posn loc, int width, int height){
    this.loc = loc;
    this.width = width;
    this.height = height;
  }
  
  // move this cloud east with the wind
  void move(int dx){
    this.loc.x = this.loc.x + dx;
  }

  // when cloud moves off the canvas, move it back to the west
  void moveInBounds(int dx, int hbound){
    if (this.loc.x + dx > hbound)
      this.loc.x = dx;
    else
      this.loc.x = this.loc.x + dx;
  }
  
  //make the image of this cloud
  WorldImage cloudImage(){
    return
    new OvalImage(this.loc, this.width, this.height, Color.white).overlayImages(
    new OvalImage(new Posn(this.loc.x - this.width / 4, 
                      this.loc.y - this.height / 4),
                  this.width / 2, this.width / 2, Color.white),
    new OvalImage(new Posn(this.loc.x + this.width / 4, 
                      this.loc.y - this.height / 2),
                  this.width / 2, this.height / 2, Color.white));       
  }
}

// the sun above this pretty world
class Sun{
  int size; // limited to less than 30
  
  Sun(int size){
    this.size = size % 30;
  }
  
  //make the image of this sun - somewhat transparent
  WorldImage sunImage(){
    return 
    new DiskImage(new Posn(50, 50), this.size, new Color(255, 255, 0, 230));
  }
}

/** Class that represents little houses with clouds above */
public class TickyTackImp extends World{
  House h1 = new House(new Posn(0, 300), 60, 80, Color.red);
  House h2 = new House(new Posn(60, 300), 60, 40, Color.green);
  House h3 = new House(new Posn(120, 300), 80, 60, Color.pink);
  House h4 = new House(new Posn(200, 300), 70, 50, Color.cyan);
  House h5 = new House(new Posn(270, 300), 90, 70, Color.yellow);
  House h6 = new House(new Posn(360, 300), 80, 60, Color.magenta);
  House h7 = new House(new Posn(440, 300), 90, 70, Color.orange);
  
  WorldImage familyImage = new FromFileImage(new Posn(30,272), "Images/family.png");
  
  Tree t1 = new Tree(new Posn(550, 300), 40, 50, 80);
  Tree t2 = new Tree(new Posn(580, 300), 20, 30, 30);
  
  Cloud cloud;
  
  Sun sun = new Sun(20);
  
  TickyTackImp(Cloud cloud, Sun sun){
    this.cloud = cloud;
    this.sun = sun;
  }
  
  // move the cloud, making sure there is always a cloud in the sky
  public void onTick(){
    this.cloud.moveInBounds(4, 600);
  }

  // the space bar makes the sun grow till max, then shrink and grow again
  public void onKeyEvent(String ke){
    if (ke.equals(" "))
      this.sun = new Sun(this.sun.size + 3);
    
    else if (ke.equals("x")){
        this.endOfWorld("Have a nice Day!");
    }
  }
  
  // move the cloud to the location of the mouse click - if it is high enough
  public void onMouseClicked(Posn p){
    if (p.y < 100)
      this.cloud.loc = p;
  }
  
  //say goodbye to the sun, when it gets to be big enough again
  public WorldEnd worldEnds(){
    if (10 < sun.size && sun.size < 20)
      return new WorldEnd(true, 
    this.makeImage().overlayImages(
        new TextImage(new Posn(150, 80), "Goodbye sun!", 
            15, 3, new Red())));
    else 
      return new WorldEnd(false, this.makeImage());
  }
  
  // produce the image of the whole world - 
  // with all houses, both trees, the cloud and the sun
  public WorldImage makeImage(){
    return 
    new RectangleImage(new Posn(300, 150), 600, 300, Color.blue).overlayImages(
        this.h1.houseImage(),
        this.h2.houseImage(),
        this.h3.houseImage(),
        this.h4.houseImage(),
        this.h5.houseImage(),
        this.h6.houseImage(),
        this.h7.houseImage(),
        this.t1.treeImage(),
        this.t2.treeImage(),
        this.cloud.cloudImage(),
        this.familyImage,
        this.sun.sunImage());
  }
  
  public WorldImage lastImage(String s){
    return
    this.makeImage().overlayImages(
        new TextImage(new Posn(150, 80), s, 
            15, 3, new Red()));
  }
  
  // support for the regression tests
  public static ExamplesTickyTack examplesInstance = new ExamplesTickyTack();
}

class ExamplesTickyTack{
  ExamplesTickyTack(){}
  
  House h1 = new House(new Posn(0, 300), 60, 80, Color.red);
  House h2 = new House(new Posn(60, 300), 60, 40, Color.green);
  House h3 = new House(new Posn(120, 300), 80, 60, Color.pink);
  House h4 = new House(new Posn(200, 300), 70, 50, Color.cyan);
  House h5 = new House(new Posn(270, 300), 90, 70, Color.yellow);
  House h6 = new House(new Posn(360, 300), 80, 60, Color.magenta);
  House h7 = new House(new Posn(440, 300), 90, 70, Color.orange);
  
  WorldImage familyImage = new FromFileImage(new Posn(30,272), "Images/family.png");
  
  Tree t1 = new Tree(new Posn(550, 300), 40, 50, 80);
  Tree t2 = new Tree(new Posn(580, 300), 20, 30, 30);
  
  Cloud cloud = new Cloud(new Posn(200, 100), 90, 60);
  
  Sun sun = new Sun(25);
  
  WorldImage wholeworld = 
      new RectangleImage(new Posn(300, 150), 600, 300, Color.blue).overlayImages(
          this.h1.houseImage(),
          this.h2.houseImage(),
          this.h3.houseImage(),
          this.h4.houseImage(),
          this.h5.houseImage(),
          this.h6.houseImage(),
          this.h7.houseImage(),
          this.t1.treeImage(),
          this.t2.treeImage(),
          this.cloud.cloudImage(),
          this.familyImage,
          this.sun.sunImage());

  TickyTackImp tworld = new TickyTackImp(this.cloud, new Sun(25));
  
  void reset(){
    this.sun = new Sun(25);
    this.cloud = new Cloud(new Posn(200, 100), 90, 60);
    this.tworld = new TickyTackImp(this.cloud, new Sun(25));
  }
  
  // test the method move in the class Cloud
  void testMove(Tester t){
    this.reset();
    this.cloud.move(5);
    t.checkExpect(this.cloud, new Cloud(new Posn(205, 100), 90, 60));
  }
  
  // test the method moveInBounds in the class Cloud
  void testMoveInBounds(Tester t){
    this.reset();
    this.cloud.moveInBounds(5, 300);
    t.checkExpect(this.cloud, 
        new Cloud(new Posn(205, 100), 90, 60));
    this.cloud.moveInBounds(5, 200);
    t.checkExpect(this.cloud, 
            new Cloud(new Posn(5, 100), 90, 60));
  }

  // test the constructor for the sun
  void testSunConstructor(Tester t){
    t.checkExpect(new Sun(35), new Sun(5));
  }

  // test the method onTick for the ticky-tack world
  void testOnTick(Tester t){
    this.reset();
    this.tworld.onTick();
    t.checkExpect(this.tworld, 
        new TickyTackImp(new Cloud(new Posn(204, 100), 90, 60), this.sun));
  }

  // test the method onKeyEvent for the ticky-tack world
  void testOnKeyEvent(Tester t){
    this.reset();
    this.tworld.onKeyEvent(" ");
    t.checkExpect(this.tworld, 
        new TickyTackImp(this.cloud, new Sun(28)));
    TickyTackImp ttw = new TickyTackImp(this.cloud, new Sun(28));
    ttw.onKeyEvent(" ");
    t.checkExpect(ttw, new TickyTackImp(this.cloud, new Sun(1)));
    this.tworld.onKeyEvent("x");
    t.checkExpect(this.tworld, this.tworld);
  }
  
  // test the method onMouseClicked in the class Cloud
  void testOnMouseClicked(Tester t){
    this.reset();
    this.tworld.onMouseClicked(new Posn(150, 80)); 
    t.checkExpect(this.tworld, 
        new TickyTackImp(new Cloud(new Posn(150, 80), 90, 60), this.sun));
    this.tworld.onMouseClicked(new Posn(150, 180));
    t.checkExpect(this.tworld,
                         this.tworld);
  }
  
  // run the ticky-tack game
  void testWholeWorld(Tester t){
    this.tworld.bigBang(600, 300, 0.1);
  }
  
  
  public static void main(String[] argv){
    ExamplesTickyTack ett = new ExamplesTickyTack();

    Tester.runReport(ett, false, false);
  }
  
}