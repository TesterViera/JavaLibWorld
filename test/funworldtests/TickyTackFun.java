package funworldtests;

import java.awt.Color;
import java.util.Random;

import tester.*;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;

/**
 * Copyright 2012 Viera K. Proulx, tweaked by Stephen Bloch
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * Not completely ported to new API yet as of Dec 26, 2012.
 * 
 * @author Viera K. Proulx and Stephen Bloch
 * @since 5 February 2012
 */

//A class to display one of the ticky-tacky houses on the hillside.
class House{
  // Posn loc;    // of the SW corner of the house base
  int width;   // the width of the house
  int height;  // the height of the house
  Color color; // the color of the house
  Person person;
  
  House(/* Posn loc, */ int width, int height, Color color){
    // this.loc = loc;
    this.width = width;
    this.height = height;
    this.color = color;
    this.person = 
        new Person(/* new Posn(this.loc.x + this.width / 2,
            this.loc.y - this.height / 4), */
                    this.width / 2, this.height / 2,
                    this.color);
  }
  
  // make the image of this house
  WorldImage houseImage(){
    return 
    AImage.makeRectangle(
        this.width, this.height, this.color, Mode.FILLED).
    overlay(
        // the roof - the height is half the height of the house
        AImage.makeTriangle(new Posn(this.loc.x, this.loc.y - this.height), 
                          new Posn(this.loc.x + this.width, this.loc.y - this.height),
                          new Posn(this.loc.x + this.width / 2, 
                                   this.loc.y - 3 * this.height / 2),
                          Color.red,
                          Mode.FILLED),
        // the door - black in the middle
        AImage.makeRectangle(this.width / 2, this.height / 2, Color.gray, Mode.FILLED)
            .moved(loc.x, loc.y)
            .overlayXY (this.person.personImage(), this.width/2, 3*this.height/4));
  }
}

class Person{
  // Posn pinhole;
  int width;
  int height;
  Color color;
  
  Person(/* Posn pinhole, */ int width, int height, Color color){
    // this.pinhole = pinhole;
    this.width = width;
    this.height = height;
    this.color = color;
  }
  
  // make the image of this house
  WorldImage personImage(){
      WorldImage head = AImage.makeCircle (this.height/4);
      WorldImage torso = AImage.makeRectangle (this.width/3, 2*this.width/3, this.color, Mode.FILLED);
      WorldImage legs = AImage.makeLine (new Posn(this.width/2, 0),
                                         new Posn(0, this.height/2))
               .beside (AImage.makeLine (new Posn(0, 0),
                                         new Posn(this.width/2, this.height/2)));
      WorldImage arms = AImage.makeLine (new Posn(0, 0),
                                         new Posn(this.width));
      return head.aboveCentered (torso).overlay(legs, arms);
    /*
                                     
    return AImage.makeCircle (this.height/4) // head
            .aboveCentered(AImage.makeRectangle (this.width/3, 2*this.width/3, this.color, Mode.FILLED))
            .overlay(AImage.makeLine (new Posn(this.width/3, 
                
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
        */
  }
}
// a class to add some trees to the ticky-tack world
class Tree{
  // Posn loc;         // the left base of the trunk
  int trunkHeight;  // the height of the trunk
  int width;        // the width of the crown of the tree
  int height;       // the height of the crown of the tree
  
  Tree(/* Posn loc, */ int trunkHeight, int width, int height){
    // this.loc = loc;
    this.trunkHeight = trunkHeight;
    this.width = width;
    this.height = height;
  }
  
//make the image of this tree
  WorldImage treeImage(){
    WorldImage trunk = AImage.makeRectangle (10, this.trunkHeight, new Color(0x84, 0x3c, 0x24), Mode.FILLED);
    WorldImage leaves = AImage.makeEllipse (this.width, this.height, Color.green, Mode.FILLED);
    return leaves.aboveCentered (trunk);

    /*
    return
    new RectangleImage(new Posn(this.loc.x + 5, 
                                this.loc.y - this.trunkHeight / 2),
                       10, this.trunkHeight, 
                       new Color(0x84, 0x3c, 0x24)).overlayImages(
    new OvalImage(new Posn(this.loc.x + 5, 
                           this.loc.y - this.trunkHeight - this.height / 2 + 3),
                  this.width, this.height, Color.green));
    */
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
  Cloud move(int dx){
    return new Cloud(new Posn(this.loc.x + dx, this.loc.y), 
        this.width, this.height);
  }

  // when cloud moves off the canvas, move it back to the west
  Cloud moveInBounds(int dx, int hbound){
    if (this.loc.x + dx > hbound)
      return new Cloud(new Posn(dx, this.loc.y), 
          this.width, this.height);
    else
      return this.move(dx);
  }
  
  //make the image of this cloud
  WorldImage cloudImage(){
      WorldImage cloud1 = AImage.makeEllipse (this.width, this.height, Color.white, Mode.FILLED);
      WorldImage cloud2 = AImage.makeEllipse (this.width/2, this.height/2, Color.white, Mode.FILLED);
      WorldImage result = cloud1.overlayXY(cloud2, this.width/4, this.height/4)
                                .overlayXY(cloud2, 3*this.width/4, this.height/4);
      return result.moved(this.loc);
      /*
    return
    new OvalImage(this.loc, this.width, this.height, Color.white).overlayImages(
    new OvalImage(new Posn(this.loc.x - this.width / 4, 
                      this.loc.y - this.height / 4),
                  this.width / 2, this.width / 2, Color.white),
    new OvalImage(new Posn(this.loc.x + this.width / 4, 
                      this.loc.y - this.height / 2),
                  this.width / 2, this.height / 2, Color.white));
    */
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
    AImage.makeCircle (/* new Posn(50, 50), */ this.size, new Color(255, 255, 0, 230), Mode.FILLED);
  }
}

/** Class that represents little houses with clouds above */
public class TickyTackFun extends World{
  House h1 = new House(new Posn(0, 300), 60, 80, Color.red).moved(0,300);
  House h2 = new House(new Posn(60, 300), 60, 40, Color.green).moved(60,300);
  House h3 = new House(new Posn(120, 300), 80, 60, Color.pink).moved(120,300);
  House h4 = new House(new Posn(200, 300), 70, 50, Color.cyan).moved(200,300);
  House h5 = new House(new Posn(270, 300), 90, 70, Color.yellow).moved(270,300);
  House h6 = new House(new Posn(360, 300), 80, 60, Color.magenta).moved(360,300);
  House h7 = new House(new Posn(440, 300), 90, 70, Color.orange).moved(440,300);

  WorldImage familyImage = new FromFileImage(/* new Posn(30,272), */"Images/family.png").moved(30,272);
    
  Tree t1 = new Tree(40, 50, 80).moved(550,300);
  Tree t2 = new Tree(20, 30, 30).moved(580,300);
  
  Cloud cloud;
  
  Sun sun = new Sun(20).moved(50,50);
  
  TickyTackFun(Cloud cloud, Sun sun){
    this.cloud = cloud;
    this.sun = sun;
  }
  
  // move the cloud, making sure there is always a cloud in the sky
  public World onTick(){
    return new TickyTackFun(this.cloud.moveInBounds(4, 600), this.sun);
  }

  // the space bar makes the sun grow till max, then shrink and grow again
  public World onKeyEvent(String ke){
    if (ke.equals(" "))
      return new TickyTackFun(this.cloud, new Sun(this.sun.size + 3));
    
    else if (ke.equals("x")){
      return 
        this.endOfWorld("Have a nice Day!");
    }
    else
      return this;
  }
  
  // move the cloud to the location of the mouse click - if it is high enough
  public World onMouseClicked(Posn p){
    if (p.y < 100)
      return new TickyTackFun(new Cloud(p, this.cloud.width, this.cloud.height), 
          this.sun);
    else
      return this;
  }

  //say goodbye to the sun, when it gets to be big enough again
  public WorldEnd worldEnds(){
    if (10 < sun.size && sun.size < 20)
      return new WorldEnd(true, 
    this.makeImage().overlayXY(
        AImage.makeText("Goodbye sun!", 15, TextStyle.BOLD_ITALIC, new Red()), 150, 80));
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
    this.makeImage().overlayXY(
        new TextImage(s, 15, TextStyle.BOLD_ITALIC, new Red()), 150, 80);
  }

  // support for the regression tests
  public static ExamplesTickyTack examplesInstance = new ExamplesTickyTack();
  
}

class ExamplesTickyTack{
  ExamplesTickyTack(){}
  
  House h1 = new House(/* new Posn(0, 300), */ 60, 80, Color.red);
  House h2 = new House(/* new Posn(60, 300), */ 60, 40, Color.green);
  House h3 = new House(/* new Posn(120, 300), */ 80, 60, Color.pink);
  House h4 = new House(/* new Posn(200, 300), */ 70, 50, Color.cyan);
  House h5 = new House(/* new Posn(270, 300), */ 90, 70, Color.yellow);
  House h6 = new House(/* new Posn(360, 300), */ 80, 60, Color.magenta);
  House h7 = new House(/* new Posn(440, 300), */ 90, 70, Color.orange);
  
  Tree t1 = new Tree(/* new Posn(550, 300), */ 40, 50, 80);
  Tree t2 = new Tree(/* new Posn(580, 300), */ 20, 30, 30);
  
  Cloud cloud = new Cloud(new Posn(200, 100), 90, 60);
  
  Sun sun = new Sun(25);
  
  WorldImage wholeworld = 
      AImage.makeRectangle (/* new Posn(300, 150), */ 600, 300, Color.blue, Mode.FILLED)
        .overlay(
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
          this.sun.sunImage());
  
  /*
  WorldCanvas c = new WorldCanvas(600, 300);
  
  boolean showWorld = 
      c.show() && c.drawImage(this.wholeworld);
  */
  TickyTackFun tworld = new TickyTackFun(this.cloud, new Sun(25));
  
  
  // test the method move in the class Cloud
  boolean testMove(Tester t){
    return 
    t.checkExpect(this.cloud.move(5), 
                     new Cloud(new Posn(205, 100), 90, 60)) &&
    t.checkExpect(this.cloud.move(5), 
                         new Cloud(new Posn(205, 100), 90, 60));
  }
  
  // test the method moveInBounds in the class Cloud
  boolean testMoveInBounds(Tester t){
    return
    t.checkExpect(this.cloud.moveInBounds(5, 300), 
        new Cloud(new Posn(205, 100), 90, 60)) &&
    t.checkExpect(this.cloud.moveInBounds(5, 200), 
            new Cloud(new Posn(5, 100), 90, 60));
  }

  // test the constructor for the sun
  boolean testSunConstructor(Tester t){
    return
    t.checkExpect(new Sun(35), new Sun(5));
  }

  // test the method onTick for the ticky-tack world
  boolean testOnTick(Tester t){
    return
    t.checkExpect(this.tworld.onTick(), 
        new TickyTackFun(new Cloud(new Posn(204, 100), 90, 60), this.sun));
  }

  // test the method onKeyEvent for the ticky-tack world
  boolean testOnKeyEvent(Tester t){
    return
    t.checkExpect(this.tworld.onKeyEvent(" "), 
        new TickyTackFun(this.cloud, new Sun(28))) &&
    t.checkExpect(new TickyTackFun(this.cloud, new Sun(28)).onKeyEvent(" "), 
        new TickyTackFun(this.cloud, new Sun(1))) &&
    t.checkExpect(this.tworld.onKeyEvent("x"), 
        this.tworld);
  }
  
  // test the method onMouseClicked in the class Cloud
  boolean testOnMouseClicked(Tester t){
    return 
    t.checkExpect(this.tworld.onMouseClicked(new Posn(150, 80)), 
        new TickyTackFun(new Cloud(new Posn(150, 80), 90, 60), this.sun))&&
    t.checkExpect(this.tworld.onMouseClicked(new Posn(150, 180)),
                         this.tworld);
  }
  
  public static void main(String[] argv){
    ExamplesTickyTack ett = new ExamplesTickyTack();

    Tester.runReport(ett, false, false);
    
    TickyTackFun tworld = new TickyTackFun(ett.cloud, ett.sun);
    tworld.bigBang(600, 300, 0.1);
  }
  
}