package funworldtests;

import java.awt.Color;
import java.util.Random;

import tester.*;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;

/**
 * Test cases for the blob animation.
 * 
 * 
 * Not completely ported to new API yet as of Dec 26, 2012 -- sbloch
 * 
 */
class BlobExamples{
    
    // examples of data for the Blob class:
    Blob b1 = new Blob(new Posn(100, 100), 50, new Red());
    Blob b1left = new Blob(new Posn(95, 100), 50, new Red());
    Blob b1right = new Blob(new Posn(105, 100), 50, new Red());
    Blob b1up = new Blob(new Posn(100, 95), 50, new Red());
    Blob b1down = new Blob(new Posn(100, 105), 50, new Red());
    Blob b1G = new Blob(new Posn(100, 100), 50, new Green());
    Blob b1Y = new Blob(new Posn(100, 100), 50, new Yellow());
    
    
    // examples of data for the BlobWorldFun class:
    BlobWorldFun b1w = new BlobWorldFun(this.b1);
    BlobWorldFun b1leftw = new BlobWorldFun(this.b1left);
    BlobWorldFun b1rightw = new BlobWorldFun(this.b1right);
    BlobWorldFun b1upw = new BlobWorldFun(this.b1up);
    BlobWorldFun b1downw = new BlobWorldFun(this.b1down);
    BlobWorldFun b1Gw = new BlobWorldFun(this.b1G);
    BlobWorldFun b1Yw = new BlobWorldFun(this.b1Y);
    BlobWorldFun b1mouse50x50w = 
    new BlobWorldFun(new Blob(new Posn(50, 50), 20, new Red()));
    
    BlobWorldFun bwOutOfBounds = 
        new BlobWorldFun(new Blob(new Posn(100, 350), 50, new Red()));
  
  BlobWorldFun bwInTheCenter = 
      new BlobWorldFun(new Blob(new Posn(100, 150), 50, new Red()));
    
    /** test the method moveBlob in the Blob class */
    boolean testMoveBlob(Tester t){
        return
        t.checkExpect(this.b1.moveBlob("left"), 
                      this.b1left, "test moveBolb - left " + "\n") &&
        t.checkExpect(this.b1.moveBlob("right"), 
                      this.b1right, "test movelob - right " + "\n") &&
        t.checkExpect(this.b1.moveBlob("up"), 
                      this.b1up, "test moveBlob - up " + "\n") &&
        t.checkExpect(this.b1.moveBlob("down"), 
                      this.b1down, "test moveBlob - down " + "\n") &&
        t.checkExpect(this.b1.moveBlob("G"), 
                      this.b1G, "test moveBlob - G " + "\n") &&  
        t.checkExpect(this.b1.moveBlob("Y"), 
                      this.b1Y, "test moveBlob - Y " + "\n") && 
        t.checkExpect(this.b1G.moveBlob("R"), 
                      this.b1, "test moveBlob - R " + "\n");  
    }
    
    /** test the method onKeyEvent in the BlobWorldFun class */
    boolean testOnKeyEvent(Tester t){
        return
        t.checkExpect(this.b1w.onKeyEvent("left"), 
                      this.b1leftw, "test moveBolb - left " + "\n") &&
        t.checkExpect(this.b1w.onKeyEvent("right"), 
                      this.b1rightw, "test movelob - right " + "\n") &&
        t.checkExpect(this.b1w.onKeyEvent("up"), 
                      this.b1upw, "test moveBlob - up " + "\n") &&
        t.checkExpect(this.b1w.onKeyEvent("down"), 
                      this.b1downw, "test moveBlob - down " + "\n") &&
        t.checkExpect(this.b1w.onKeyEvent("G"), 
                      this.b1Gw, "test moveBlob - G " + "\n") &&  
        t.checkExpect(this.b1w.onKeyEvent("Y"), 
                      this.b1Yw, "test moveBlob - Y " + "\n") && 
        t.checkExpect(this.b1Gw.onKeyEvent("R"), 
                      this.b1w, "test moveBlob - R " + "\n") && 
                      
        // to test the world ending, verify the value of the lastWorld            
        t.checkExpect(this.b1Gw.onKeyEvent("x").lastWorld,
        new WorldEnd(true,
            this.b1Gw.makeImage().overlayXY (AImage.makeText ("Goodbye", Color.red), 100, 40)));
    }
    
    /** test the method outsideBounds in the Blob class */
    boolean testOutsideBounds(Tester t){
        return
        t.checkExpect(this.b1.outsideBounds(60, 200), true,
            "test outsideBounds on the right") &&
        
        t.checkExpect(this.b1.outsideBounds(100, 90), true,
                      "test outsideBounds below") &&
        
        t.checkExpect(
                      new Blob(new Posn(-5, 100), 50, new Red()).outsideBounds(100, 110), 
                      true,
                      "test outsideBounds above") &&
        
        t.checkExpect(
                      new Blob(new Posn(80, -5), 50, new Blue()).outsideBounds(100, 90), 
                      true,
                      "test outsideBounds on the left") &&
        
        t.checkExpect(this.b1.outsideBounds(200, 400), false,
                      "test outsideBounds - within bounds");
    }
    
    /** test the method onMOuseClicked in the BlobWorldFun class */
    boolean testOnMouseClicked(Tester t){
        return
        t.checkExpect(this.b1w.onMouseClicked(new Posn(50, 50)), 
                      this.b1mouse50x50w);
    }
    
    /** test the method nearCenter in the Blob class */
    boolean testNearCenter(Tester t){
        return
        t.checkExpect(this.b1.nearCenter(200, 200), true,
                      "test nearCenter - true") &&
        t.checkExpect(this.b1.nearCenter(200, 100), false,
                      "test nearCenter - false");
    }
    
    /** the method randomInt in the Blob class */
    boolean testRandomInt(Tester t){
        return
        t.checkOneOf("test randomInt",
                     this.b1.randomInt(3), -3, -2, -1, 0, 1, 2, 3) &&
        t.checkNoneOf("test randomInt", 
                      this.b1.randomInt(3), -5, -4, 4, 5);
    }
    
    /** test the method randomMove in the Blob class */
    boolean testRandomMove(Tester t){
        return 
        t.checkOneOf("test randomMove", this.b1.randomMove(1),
                     new Blob(new Posn( 99,  99), 50, new Red()),
                     new Blob(new Posn( 99, 100), 50, new Red()),
                     new Blob(new Posn( 99, 101), 50, new Red()),
                     new Blob(new Posn(100,  99), 50, new Red()),
                     new Blob(new Posn(100, 100), 50, new Red()),
                     new Blob(new Posn(100, 101), 50, new Red()),
                     new Blob(new Posn(101,  99), 50, new Red()),
                     new Blob(new Posn(101, 100), 50, new Red()),
                     new Blob(new Posn(101, 101), 50, new Red()));
    }  
    
    /** test the method onTick in the BlobWorldFun class */
    boolean testOnTick1(Tester t){
        boolean result = true;
        for (int i = 0; i < 20; i++){
            BlobWorldFun bwf = (BlobWorldFun)this.b1w.onTick();
            result = result &&
            t.checkRange(bwf.blob.center.x, 95, 106) &&
            t.checkRange(bwf.blob.center.y, 95, 106);
        }
        return result;
    }
    

  /** test the method onTick when the world should end 
   * in the BlobWorldFun class */
    boolean testOnTick2(Tester t){
      return
        // to test the world ending, verify the value of the lastWorld        
        t.checkExpect(this.bwOutOfBounds.testOnTick().lastWorld,
          new WorldEnd(true,
            this.bwOutOfBounds.lastImage("Blob is outside the bounds"))) &&  
            
        t.checkExpect(this.bwInTheCenter.testOnTick().lastWorld,
          new WorldEnd(true,
            this.bwInTheCenter.makeImage().overlayXY (AImage.makeText ("Black hole at the blob", 13, TextStyle.BOLD_ITALIC, Color.red),
                                                      100, 40)));
    }
    
    /** test the method onTick in the BlobWorldFun class */
    /*
    boolean testOnTick2(Tester t){
        return
        
        // insufficient number of options ...
        t.checkOneOf("test onTick2: randomMove", this.b1w.onTick(),
                     new BlobWorldFun(new Blob(new Posn( 99,  99), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn( 99, 100), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn( 99, 101), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn(100,  99), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn(100, 100), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn(100, 101), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn(101,  99), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn(101, 100), 50, new Red())),
                     new BlobWorldFun(new Blob(new Posn(101, 101), 50, new Red()))
                     ); 
    }
    */
    
    // test the method worldEnds for the class BlobWorld
    boolean testWorldEnds(Tester t){
        return
        t.checkExpect(this.bwOutOfBounds.worldEnds(),
            new WorldEnd(true, 
                this.bwOutOfBounds.makeImage().overlayXY (AImage.makeText("Blob is outside the bounds", Color.red),
                                                          100, 40)))
        &&
         
    t.checkExpect(this.bwInTheCenter.worldEnds(),
        new WorldEnd(true, 
            this.bwInTheCenter.makeImage().overlayXY (AImage.makeText ("Black hole ate the blob", 13, TextStyle.BOLD_ITALIC, Color.red),
                                                      100, 40))) &&
        
   t.checkExpect(this.b1w.worldEnds(),
       new WorldEnd(false, this.b1w.makeImage()));
    }
    
    public static void runIt ()
    {
    /** run the animation */
    BlobWorldFun w1 = 
    new BlobWorldFun(new Blob(new Posn(100, 200), 20, new Red()));
    BlobWorldFun w2 = 
    new BlobWorldFun(new Blob(new Posn(100, 200), 20, new Red()));
    BlobWorldFun w3 = 
    new BlobWorldFun(new Blob(new Posn(100, 200), 20, new Red()));
    
    // test that we can run three different animations concurrently
    // with the events directed to the correct version of the world
    
    boolean runAnimation = w1.bigBang(200, 300, 0.3); 
    boolean runAnimation2 = w2.bigBang(200, 300, 0.3); 
    boolean runAnimation3 = w3.bigBang(200, 300, 0.3); 
    }
    
    /** main: an alternative way of starting the world and running the tests */
    public static void main(String[] argv){

      // run the tests - showing only the failed test results
        BlobExamples be = new BlobExamples();
        Tester.runReport(be, false, false);
        
        // run the game
        BlobWorldFun w = 
        new BlobWorldFun(new Blob(new Posn(150, 100), 20, new Red()));
    w.bigBang(200, 300, 0.3);
    
        /* 
         Canvas c = new Canvas(200, 300);
         c.show();
         System.out.println(" let's see: \n\n" + 
         Printer.produceString(w.makeImage()));
         c.drawImage(new OverlayImages(new DiskImage(new Posn(50, 50), 20, new Red()),
         new RectangleImage(new Posn(20, 30), 40, 20, new Blue())));
         */
    }
    
}