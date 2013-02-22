package javalib.funworld;

import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Copyright 2007, 2008 2009, 2012 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * World for programming interactive games - with graphics, key events,
 * mouse events and a timer. 
 * 
 * @author Viera K. Proulx
 * @since November 15 2007, March 17 2008, October 19 2009, February 4 2012
 */
abstract public class World implements UserWorld, Drawable{
	
	/** the canvas that displays the current world */
	public WorldCanvas theCanvas;
	
	/** true if 'bigBang' started the world and it did not end, did not stop */
	private transient boolean worldExists = false;
	
	/** the timer for this world */
	protected transient MyTimer mytime;
	
	/** timer events not processed when the mouse event is processed */
	protected transient boolean stopTimer = false;
	
	/** the key adapter for this world*/
	private transient MyKeyAdapter ka;
	
	/** the mouse adapter for this world */
	private transient MyMouseAdapter ma;
	
	/** the window closing listener for this world */
	private transient WindowListener windowClosing;
  
	/** a blank image, to avoid <code>null</code> in the <code>lastWorld</code> */
  private transient WorldImage blankImage = 
      AImage.makeCircle(1, Color.white);
  
  /** the last world - if needed */
  public WorldEnd lastWorld = new WorldEnd(false, this.blankImage);
	
	/**
	 * The default constructor. To start the world one must invoke the
	 * <code>bigBang</code> method.
	 */
	public World(){ }
	
	/////////////////////////////////////////////////////////////////////////
	// Methods for interacting with the World                              //
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * Start the world by creating a canvas of the given size, creating
	 * and adding the key and mouse adapters, and starting the timer at the
	 * given speed.
	 * 
	 * @param w the width of the <code>{@link WorldCanvas Canvas}</code>
	 * @param h the height of the <code>{@link WorldCanvas Canvas}</code>
	 * @param speed the speed at which the clock runs
	 * @return <code>true</code>
	 */
	public boolean bigBang(int w, int h, double speed){
    if (this.worldExists){
      System.out.println("Only one world can run at a time");
      return true;
    }
		// throw runtime exceptions if w, h <= 0	  
		this.theCanvas = new WorldCanvas(w, h);
		
		// if the user closes the Canvas window
		// it will only hide and can be reopened by invoking 'show'
		this.theCanvas.f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		this.windowClosing = new MyWindowClosingListener(this);
		this.theCanvas.f.addWindowListener(this.windowClosing);
		
		//this.imageList = new HashMap<String, PictureImage>();
		
		// pause a bit so that two canvases do not compete when being opened
		// almost at the same time
		long start = System.currentTimeMillis();
		long tmp = System.currentTimeMillis();
		// System.out.println("Going to sleep.");
		
		while(tmp - start < 1000){
			tmp = System.currentTimeMillis();
		}
		
		// add the key listener to the frame for our canvas
		this.ka = new MyKeyAdapter(this);
		this.theCanvas.f.addKeyListener(this.ka);
		
		// add the mouse listener to the frame for our canvas
		this.ma = new MyMouseAdapter(this);
		this.theCanvas.f.addMouseListener(this.ma);
		
		// make sure the canvas responds to events
		this.theCanvas.f.setFocusable(true);
		
		// finally, show the canvas and draw the initial world
		this.theCanvas.show();
   
		
		// draw the initial world
		this.drawWorld(" ");
		
		// pause again after the Canvas is shown to make sure 
		// all listeners and the timer are installed for theCanvas
		start = System.currentTimeMillis();
		tmp = System.currentTimeMillis();
		// System.out.println("Going to sleep again.");
		
		while(tmp - start < 1000){
			tmp = System.currentTimeMillis();
		}
		
		// and add the timer -- start it if speed is greater than 0
		this.mytime = new MyTimer(this, speed);
		if (speed > 0.0)
			this.mytime.timer.start();
		
		this.worldExists = true;
    
    // print a header that specifies the current version of the World
    System.out.println(Versions.CURRENT_VERSION);
		
		return this.drawWorld("");
	}
	
	/**
	 * Start the world by creating a canvas of the given size, creating
	 * and adding the key and mouse adapters, without running the the timer.
	 * 
	 * @param w the width of the <code>{@link WorldCanvas Canvas}</code>
	 * @param h the height of the <code>{@link WorldCanvas Canvas}</code>
	 * @return <code>true</code>
	 */
	public boolean bigBang(int w, int h){
		return this.bigBang(w, h, 0.0);
	}
	
	/**
	 * Start the world by creating a canvas whose size is that of the initial image,
	 * and starting the timer at a given speed.
	 * 
	 * @param speed    the interval between clock ticks, in seconds
	 * @return <code>true</code>
	 * @since Feb. 10, 2013
	 * @author Stephen Bloch
	 */
	public boolean bigBang (double speed)
	{
	    WorldImage firstImage = this.makeImage();
	    return this.bigBang (firstImage.getWidth(), firstImage.getHeight(), speed);
	}
	
	/**
	 * Start the world by creating a canvas whose size is that of the initial image,
	 * with no timer.
	 * 
	 * @return <code>true</code>
	 * @since Feb. 10, 2013
	 * @author Stephen Bloch
	 */
	public boolean bigBang ()
	{
	    return this.bigBang (0.0);
	}
	   
	/**
	 * Stop the world, close all listeners and the timer, draw the last 
	 * <code>Scene</code>.
	 */
	protected void stopWorld(){
	  if (worldExists){
		// remove listeners and set worldExists to false
		this.mytime.timer.stop();
		this.worldExists = false;
		this.mytime.stopTimer();
		this.theCanvas.f.removeKeyListener(this.ka);
		this.theCanvas.f.removeMouseListener(this.ma);
		System.out.println("The world stopped.");
		
		// draw the final scene of the world with the end of time message
		this.theCanvas.clear();
		this.theCanvas.drawImage(this.lastWorld.lastImage);
	  }
	}
	
	/**
	 * <p>This method is invoked at each tick. It checks if the world 
	 * should end now.</p>
	 * <p>The saved image will be shown when the world ends,
	 * otherwise it is ignored.</p>
	 * 
	 * @return pair (true, last image) or (false, any image)
	 */
	public WorldEnd worldEnds(){
		return new WorldEnd(false, this.makeImage());
	}
	
  /**
   * End the world interactions - leave the canvas open, 
   * show the image of the last world with the given message
   * 
   * @param s the message to display
   * @return <code>this</code> world
   */
  public World endOfWorld(String s){
    // set up the last world pair and finish as usual
    this.lastWorld = new WorldEnd(true, this.lastImage(s));
    this.stopWorld();

    return this;
  }

  /**
   * The <code>onTick</code> method is invoked only if the world exists. 
   * To test the method <code>onTick</code> we provide this method that
   * will invoke the <code>onTick</code> method for the testing purposes.
   */
  public World testOnTick(){
    // the world does not exist, but run the test for the end of the world
    // and handle the last sound effect as needed
    this.lastWorld = this.worldEnds();
    if (this.lastWorld.worldEnds){
      this.stopWorld();
    }
    
    // now invoke the onTick method - 
    // it will ignore the code similar to that above
    // but will handle the tickTunes manipulation
    return this.processTick();
  }
  
	/**
	 * The method invoked by the timer on each tick.
	 * Delegates to the user to define a new state of the world,
	 * then resets the canvas and event handlers for the new world
	 * to those currently used.
	 * 
	 * @return <code>{@link World World}</code> after the tick event
	 */
	protected synchronized World processTick(){
		try{
			if (this.worldExists && !this.stopTimer){
				this.lastWorld = this.worldEnds();
				if (this.lastWorld.worldEnds){
					this.stopWorld();
				}
				else{
					World bw = this.onTick();  
					return resetWorld(bw);
				}
			}
			else 
				return this;
		} 
		catch(RuntimeException re){
			re.printStackTrace();
			this.drawWorld("");
			//throw re;
			Runtime.getRuntime().halt(1);
		}
		return this;
	}  
	
	/**
	 * <P>User defined method to be invoked by the timer on each tick.
	 * Produces a new <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @return <code>{@link World World}</code> that needs to have 
	 * the canvas and the event handlers initialized
	 */
	public World onTick(){
		return this;
	}
    
	
	/**
	 * The method invoked by the key adapter on selected key events.
	 * Delegates to the user to define a new state of the world,
	 * then resets the canvas and event handlers for the new world
	 * to those currently used.
	 * 
	 * @return <code>{@link World World}</code> after the key event
	 */
	protected synchronized World processKeyEvent(String ke){
		try{
			if (this.worldExists){
					World bw = this.onKeyEvent(ke); 
					if (!this.lastWorld.worldEnds)
					  return resetWorld(bw);
					else
					  return this;
			}
			else 
				return this;
			
		}
		catch(RuntimeException re){
			re.printStackTrace();
			this.drawWorld("");
			//throw re;
			Runtime.getRuntime().halt(1);
		}
		return this;
	}
	
	/**
	 * <P>User defined method to be invoked by the key adapter 
	 * on selected key events.
	 * Produces a new <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @return <code>{@link World World}</code> that needs to have 
	 * the canvas and the event handlers initialized
	 */
	public World onKeyEvent(String s){
		return this;
	}
	
	/**
	 * The method invoked by the mouse adapter on mouse clicked event.
	 * Delegates to the user to define a new state of the world.
	 * 
	 * @param mouse the location of the mouse when clicked
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	protected World processMouseClicked(Posn mouse){
		try{
		  if (this.worldExists){
		    World bw = this.onMouseClicked(mouse);
        if (!this.lastWorld.worldEnds)
          return resetWorld(bw);
        else
          return this;
		  }
			else 
				return this;
		} 
		catch(RuntimeException re){
			re.printStackTrace();
			this.drawWorld("");
			//throw re;
			Runtime.getRuntime().halt(1);
		}
		return this;
	}
	
	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is clicked.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @param mouse the location of the mouse when clicked
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseClicked(Posn mouse){
		return this;
	}
	
	/**
	 * The method invoked by the mouse adapter on mouse entered event.
	 * Delegates to the user to define a new state of the world.
	 * 
	 * @param mouse the location of the mouse when entered
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	protected World processMouseEntered(Posn mouse){
		
	  try{
	    if (this.worldExists){
	      World bw = this.onMouseEntered(mouse); 
	      if (!this.lastWorld.worldEnds)
	        return resetWorld(bw);
	      else
	        return this;
	    }
	    else 
	      return this;
	  } 
	  catch(RuntimeException re){
	    re.printStackTrace();
	    this.drawWorld("");
	    //throw re;
	    Runtime.getRuntime().halt(1);
	  }

	  return this;
	}
	
	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is entered.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @param mouse the location of the mouse when entered
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseEntered(Posn mouse){
		return this;
	}
	
	
	/**
	 * The method invoked by the mouse adapter on mouse exited event.
	 * Delegates to the user to define a new state of the world.
	 * 
	 * @param mouse the location of the mouse when exited
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	protected World processMouseExited(Posn mouse){
		
	  try{
	    if (this.worldExists){
	      World bw = this.onMouseExited(mouse);   
	      if (!this.lastWorld.worldEnds)
	        return resetWorld(bw);
	      else
	        return this;
	    }
	    else 
	      return this;
	  } 
	  catch(RuntimeException re){
	    re.printStackTrace();
	    this.drawWorld("");
	    //throw re;
	    Runtime.getRuntime().halt(1);
	  }

	  return this;
	}
	
	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is exited.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @param mouse the location of the mouse when exited
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseExited(Posn mouse){
		return this;
	}
	
	
	/**
	 * The method invoked by the mouse adapter on mouse pressed event.
	 * Delegates to the user to define a new state of the world.
	 * 
	 * @param mouse the location of the mouse when pressed
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	protected World processMousePressed(Posn mouse){
		
	  try{
	    if (this.worldExists){
	      World bw = this.onMousePressed(mouse);  
	      if (!this.lastWorld.worldEnds)
	        return resetWorld(bw);
	      else
	        return this;
	    }
	    else 
	      return this;
	  } 
	  catch(RuntimeException re){
	    re.printStackTrace();
	    this.drawWorld("");
	    //throw re;
	    Runtime.getRuntime().halt(1);
	  }

	  return this;
	}
	
	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is pressed.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @param mouse the location of the mouse when pressed
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMousePressed(Posn mouse){
		return this;
	}
	
	/**
	 * The method invoked by the mouse adapter on mouse released event.
	 * Delegates to the user to define a new state of the world.
	 * 
	 * @param mouse the location of the mouse when released
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	protected World processMouseReleased(Posn mouse){
		
	  try{
	    if (this.worldExists){
	      World bw = this.onMouseReleased(mouse); 
	      if (!this.lastWorld.worldEnds)
	        return resetWorld(bw);
	      else
	        return this;
	    }
	    else 
	      return this;
	  } 
	  catch(RuntimeException re){
	    re.printStackTrace();
	    this.drawWorld("");
	    //throw re;
	    Runtime.getRuntime().halt(1);
	  }

	  return this;
	}

	/**
	 * <P>User defined method to be invoked by the mouse adapter 
	 * when a mouse is released.
	 * Update the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @param mouse the location of the mouse when released
	 * @return <code>{@link World World}</code> after the mouse event
	 */
	public World onMouseReleased(Posn mouse){
		return this;
	}
	
	
	/**
	 * Initialize the canvas, the event handlers, and the timer for the
	 * given <code>{@link World World}</code> to the current settings.
	 * 
	 * @param bw the new <code>{@link World World}</code> created by the user
	 * @return the same <code>{@link World World}</code> with the canvas, 
	 * the event handlers, and the timer initialized to the current settings.
	 */
	private synchronized World resetWorld(World bw){
		if (this.worldExists){
			bw.theCanvas = this.theCanvas;
			bw.worldExists = true;
			
			// with all the listeners
			bw.ka = this.ka;
			bw.ma = this.ma;
			bw.windowClosing = this.windowClosing;
			bw.ka.currentWorld = bw;
			bw.ma.currentWorld = bw;
			
			// and the timer
			bw.mytime = this.mytime;
			bw.mytime.setSpeed();
			bw.mytime.currentWorld = bw;
			
			// draw the new world
			bw.drawWorld("");
			return bw;
		}
		else{
			this.theCanvas.clear();
			this.drawWorld("");
			return this;
		}
	}
	
	/**
	 * Invoke the user defined <code>makeImage</code> method, if this 
	 * <code>{@link World World}</code> has been initialized 
	 * via <code>bigBang</code> and did not stop or end, otherwise 
	 * invoke the user defined <code>lastImage</code> method,
	 * 
	 * @return <code>true</code>
	 */
	protected synchronized boolean drawWorld(String s){
		if (this.worldExists){
			this.theCanvas.clear();
			this.theCanvas.drawImage(this.makeImage());
			return true;
		}
		else{
            this.theCanvas.clear();
            this.theCanvas.drawImage(this.lastImage(s));
			return true;
		}
	}
	
	/**
	 * <P>User defined method to draw the <code>{@link World World}</code>.</P>
	 * <P>Override this method in the game world class</P>
	 * 
	 * @return the image that represents this world at this moment
	 */
	abstract public WorldImage makeImage();

  /**
   * <P>User defined method to draw the <code>{@link World World}</code>.</P>
   * <P>Override this method in the game world class</P>
   * 
   * @return the image that represents the last world to be drawn
   */
	public WorldImage lastImage(String s){
	  return this.makeImage();
	}
}
