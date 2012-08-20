package tunesdemoapplet;

import javalib.appletsoundworld.*;

/**
 * A comprehensive demo of the appletsoundworld and tunes package.
 * 
 * @author Viera K. Proulx
 * @since 14 August 2010
 */

public class TunesDemoApplet extends WorldApplet{

  /** Produce a new TunesWorld */
  public World getNewWorld(){    
    return new TunesWorld();
  }

  /** Set the size of this world */
  public void setWorldSize(){
    this.WIDTH = 600;
    this.HEIGHT = 400;
  }
  
  /** Set the speed to be twice the normal, so the music plays better */
  public double setSpeedFactor(){
    return 2.0;
  }
}
