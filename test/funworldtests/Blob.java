package funworldtests;

import java.awt.Color;
import java.util.Random;

import tester.*;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;

/** Class that represents a colored disk that moves around the Canvas 
 * 
 * Not completely ported to new API yet as of Dec 26, 2012. -- sbloch
 * 
 */
class Blob{
    
    Posn center;
    int radius;
    IColor col;
    //ImageMaker image = new ImageMaker("shark.png");
    
    /** The constructor */
    Blob(Posn center, int radius, IColor col) {
        this.center = center;
        this.radius = radius;
        this.col = col;
    }
    
    /** produce the image of this blob at its current location and color */
    WorldImage blobImage(){
        //return new DiskImage(this.center, this.radius, this.col);
        return AImage.makeFromFile("Images/small-shark.png")
            .overlayCentered(AImage.makeCircle(this.radius, this.col, Mode.OUTLINED));
    }
    
    /** move this blob 20 pixels in the direction given by the ke
     or change its color to Green, Red or Yellow */
    public Blob moveBlob(String ke){
        if (ke.equals("right")){
            return new Blob(new Posn(this.center.x + 5, this.center.y),
                            this.radius, this.col);
        }
        else if (ke.equals("left")){
            return new Blob(new Posn(this.center.x - 5, this.center.y),
                            this.radius, this.col);
        }
        else if (ke.equals("up")){
            return new Blob(new Posn(this.center.x, this.center.y - 5),
                            this.radius, this.col);
        }
        else if (ke.equals("down")){
            return new Blob(new Posn(this.center.x, this.center.y + 5),
                            this.radius, this.col);
        }
        // change the color to Y, G, R
        else if (ke.equals("Y")){
            return new Blob(this.center, this.radius, new Yellow());
        }    
        else if (ke.equals("G")){
            return new Blob(this.center, this.radius, new Green());
        }    
        else if (ke.equals("R")){
            return new Blob(this.center, this.radius, new Red());
        }
        else
            return this;
    }
    
    /** produce a new blob moved by a random distance < n pixels */
    Blob randomMove(int n){
        return new Blob(new Posn(this.center.x + this.randomInt(n),
                                 this.center.y + this.randomInt(n)),
                        this.radius, this.col);
    }
    
    /** helper method to generate a random number in the range -n to n */
    int randomInt(int n){
        return -n + (new Random().nextInt(2 * n + 1));
    }
    
    /** is the blob outside the bounds given by the width and height */
    boolean outsideBounds(int width, int height) {
        return this.center.x < 0
        || this.center.x > width
        || this.center.y < 0 
        || this.center.y > height;
    }
    
    /** is the blob near the center of area given by the width and height */
    boolean nearCenter(int width, int height) {
        return this.center.x > width / 2 - 10
        && this.center.x < width / 2 + 10
        && this.center.y > height / 2 - 10
        && this.center.y < height / 2 + 10;
    }
}
