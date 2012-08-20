package javalib.worldimages;


/**
 * <p>Copyright 2012 Viera K. Proulx</p>
 * <p>This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)</p>
 */

 /**
 * <p>The class to represent a pair of values <code>boolean</code> and 
 * <code>{@link WorldImage WorldImage}</code>.</p>
 * <p>This is used to indicate when the world should end and allow for 
 * creating one last image to display at the end.</p>
 *
 * @author Viera K. Proulx
 * @since February 4 2012
 */
public class WorldEnd{
  /** the indicator whether the world should end:
   * true if the world should end, false if the world goes on */
  public boolean worldEnds;
  
  /** the last image to display when the world ends */
  public WorldImage lastImage;
  
  /**
   * The standard full constructor.
   * @param worldEnds true if the world should end, false if the world goes on
   * @param lastImage the last image to display when the world ends
   * (ignored if the world goes on)
   */
  public WorldEnd(boolean worldEnds, WorldImage lastImage){
    this.worldEnds = worldEnds;
    this.lastImage = lastImage;
  }
}