package javalib.appletsoundworld;

import javalib.worldcanvas.*;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;


import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagLayout;

import javax.swing.*;


/**
 * Copyright 2008 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * The applet that runs the world
 * 
 * @author Viera K. Proulx 
 * @since April 30, 2008
 */
abstract public class WorldApplet extends JApplet{

  /** a slider to control the world speed */
  protected JSlider speedSlider= new JSlider();

  /** the main applet panel */
  protected JPanel appletPanel = new JPanel();

  /** the state of the world */
  protected static String CLEAR =   "CLEAR";
  protected static String RUNNING = "STOP ";
  protected static String READY =   "START";
  protected String currentState = READY;
  protected static double SPEED_FACTOR = 1.0;

  /** the button to START, STOP, and CLEAR the world */
  protected JButton bigButton = new JButton(READY);

  /** the responses to the control button: START, STOP, CLEAR */
  protected  ActionListener bba = new BigBangAction();

  /** the panel for the control button and the speed control slider */
  protected JPanel controls = new JPanel();

  /** the width of the Canvas for the world */
  protected  int WIDTH = 200;
  
  /** the height of the Canvas for the world */
  protected  int HEIGHT = 200;
  
  /** the speed at which the clock ticks */
  protected double SPEED = 0.1;

  /** the one and only Canvas */
  protected AppletCanvas theCanvas;
  
  /** the Painter in the Canvas */
  protected AppletCanvas.Painter painter;

  /** enforce notifying FromFileImage that we are running an applet */
  protected boolean setApplet = this.isApplet();

  /** the world running in this applet */
  protected World currentworld = this.getNewWorld();
  
  /** is this applet stopped? (e.g. hidden) */
  protected boolean stopped = false;
   
  /////////////////////////////////////////////////////////////////////////
  // Initializer for the images from files                               //
  /////////////////////////////////////////////////////////////////////////
  /**
   *  make sure FromFileImage knows we are working on an applet 
   *  before any files are read 
   *  
   * @return always true
   */
  protected boolean isApplet(){
    FromFileImage.isApplet = true;
    return FromFileImage.isApplet;
  }
  
  /////////////////////////////////////////////////////////////////////////
  // Methods used to customize user's World                              //
  /////////////////////////////////////////////////////////////////////////
  /** 
   * implement this method to create user's initial world 
   *
   * @return the initial user's world
   */
  abstract public World getNewWorld();
  
  /**
   * implement this method to set the size of the user's world 
   */
  abstract public void setWorldSize();
  
  /**
   * implement this method if you want a different upper limit 
   * for your speed control
   */
  public double setSpeedFactor(){
    return 1.0;
  }
  
  /////////////////////////////////////////////////////////////////////////
  // Methods that run the applet World                                   //
  /////////////////////////////////////////////////////////////////////////
  /**
   * Initialize the width and height of the Canvas 
   * making sure the minimum size is 200 x 200
   */
  protected void initWH(){
    this.setWorldSize();
    if (this.WIDTH < 200)
      this.WIDTH = 200;
    if (this.HEIGHT < 200)
      this.HEIGHT = 200;
  }
  
  /**
   * Initialize the speed range of the slider
   */
  protected void initSpeed(){
    SPEED_FACTOR = this.setSpeedFactor();
  }
  
  /**
   * Get the speed for the world from the slider.
   * 
   * @return the speed for the world
   */
  protected double getSpeed(){
    int n = speedSlider.getValue();
    return Double.valueOf("" + (101 - n))/100 / SPEED_FACTOR;
  }
  
  /**
   * Display the speed of the world in the slider.
   */
  protected void setSpeed(double d){
    int n = ((Long)(Math.round(101 - d * SPEED_FACTOR * 100.0))).intValue();
    speedSlider.setValue(n);
  }

  //////////////////////////////////////////////////////////////////////
  // Methods needed to manage the applet                              //
  //////////////////////////////////////////////////////////////////////
  /**
   * We implement this method so that mouse and key events will not be
   * recorded outside of the canvas panel.
   */
  public boolean isFocusable(){
    return true;
  }

  /** prepare the applet for launch */
  /**
   * Build the applet GUI: The Canvas panel on the top, the controls
   * on the bottom --- a buttom to start, stop, and clear; 
   * a slider to set the speed.
   */
  public void init(){
    // make sure from the start the image files will be read via url
    if (isApplet())
    // set the Canvas size
    this.initWH();

    
    // the applet has two parts - canvas on the top, controls below
    setLayout(new BorderLayout(1, 2));
    setBackground(Color.white);
    setSize(this.WIDTH, this.HEIGHT + 30);   

    // set the size of the slider and the control button
    this.initSpeed();
    speedSlider.setPreferredSize(new Dimension(100, 30));
    bigButton.setPreferredSize(new Dimension(this.WIDTH - 100, 30));

    // the controls on the bottom
    controls.setLayout(new BorderLayout());
    controls.setBackground(Color.darkGray);

    // action control button on the left (START, STOP, CLEAR)
    // speed control slider on the right
    controls.add(BorderLayout.WEST, bigButton);
    controls.add(BorderLayout.EAST, speedSlider);
    controls.validate();

    // the main applet panel
    appletPanel.setLayout(new BorderLayout());
    appletPanel.setBackground(Color.lightGray);

    // construct a new AppletCanvas of the fixed size
    theCanvas = new AppletCanvas(WIDTH, HEIGHT);
    
    // install the AppletCanvas
    theCanvas.painter.setSize(WIDTH, HEIGHT);
    theCanvas.painter.setMaximumSize(new Dimension(WIDTH, HEIGHT));
    theCanvas.clearPanel();

    // do not resize the canvas
    theCanvas.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE; 
    
    // Add the Canvas and the controls to the applet panel
    // place the canvas on the top
    appletPanel.add(BorderLayout.CENTER, theCanvas);

    // user interactions below the canvas
    appletPanel.add(BorderLayout.SOUTH, controls);

    // set the size of the whole applet 30 pixels taller 
    // 30 is the expected size of the control bar on the bottom
    appletPanel.setSize(this.WIDTH, this.HEIGHT + 30);
    appletPanel.addNotify(); 
    
    add(appletPanel);
    
    // BigBang button actions: START the world, STOP the world, CLEAR the world
    bigButton.addActionListener(bba);
    
    stopped = false;
    
    // initialize the world as defined by the user
    currentworld = getNewWorld();
    
    // give the world a Canvas to draw on and a button for the end of the world
    currentworld.initWorld(theCanvas, bigButton);
    
    // draw the background for the Canvas
    redraw();
  }

  /**
   * <p>The action for the world action control button. The button has three
   * states: CLEAR, RUNNING, and READY.</p>
   * <p>In READY state, the button is labeled "Start the world". 
   * The button press starts the world and changes 
   * the state to RUNNING</p>
   * <p>In RUNNING state, the button is labeled "Stop the world". 
   * The world stops either when the program stops the timer, 
   * or when the program ends the world, or when the user hits the button.
   * The button action changes the state to CLEAR</p>
   * <p>In CLEAR state, the button is labeled "Clear the world". 
   * The final state of the world with the appropriate
   * message is still visible. The button press clears the display and
   * changes the state to READY.</p>
   * 
   * @author Viera K. Proulx
   * @since November 29, 2007 2007
   *
   */
  public class BigBangAction implements ActionListener{

    /**
     * <p>The only method we need.</p>
     * <p>The button switches to state of the applet between three
     * states: CLEAR, RUNNING, and READY.</p>
     * 
     * <p>READY state displays a cleared Canvas. The button label indicates 
     * that the user can now <em>Start the world</em>. On activation
     * it invokes the <code>bigBang</code> method for this world, 
     * with the speed given by the speed slider setting.</p>
     * 
     * <p>RUNNING state displays the running world. The button label
     * indicates that the user can <em>Stop the world</em> 
     * if so desired. On activation of the button the currently running
     * world is terminated the state changes to CLEAR (see below). 
     * It allows the user to interrupt an infinite loop
     * that may have been erroneously encoded in the program.</p>
     * 
     * <p>The change of state from RUNNING to CLEAR can also be triggered
     * by the program actions --- when the world ends.
     * 
     * <p>CLEAR state displays the state of the world when the world ended.
     * It contains any message the program produced in the 
     * <code>endOfWorld</p> or <p>endOfTime</p> method, or it displays the
     * message <em>STOP button ended the world</em>.</p>
     * 
     * <p>The button label indicates that the user can now <em>Clear the 
     * world</em>. On activation the Canvas panel is cleared (painted 
     * in light gray color) and the state is changed to READY.
     * 
     * @param evt ignored, required by the <code>ActionListener</code>
     * interface
     */
    public void actionPerformed(ActionEvent evt){
      /*
        Button labels for each state:
        String CLEAR   = "CLEAR";
        String RUNNING = "STOP ";
        String READY   = "START";
       */   

      // determine the current state of the button
      currentState = bigButton.getText();

      //----------------------------------------------------------------
      // ignore all while the world is suspended (the applet is hidden)
      if (stopped == true){}

      //----------------------------------------------------------------
      // process the READY state - change to RUNNING - if button pressed
      else if (currentState.equals(READY)){
        
        // start a new world, give it the canvas and button to stop the game
        currentworld = getNewWorld();
        currentworld.initWorld(theCanvas, bigButton);

        // note that the world is running
        currentState = RUNNING;
        bigButton.setText(RUNNING); 
        
        // start the world
        bbstart();
      }

      //----------------------------------------------------------------
      // process the RUNNING state - change to CLEAR
      // invoked by button press or endOfWorld or endOfTime
      else if (currentState.equals(RUNNING)){  

        // print a message if world is stopped by the user's button press
        // and inform the world of its demise
        if (currentworld.worldExists == true){

        // stop the timer, remove key listener, mark the world stopped
        currentworld.endOfWorld("STOP button ended the world");
        }

        // now - change the applet so we can see that the world stopped
        currentState = CLEAR;
        bigButton.setText(CLEAR); 
      }

      //---------------------------------------------------------------
      // process the CLEAR state - change to READY -- if button pressed
      else if (currentState.equals(CLEAR)){ 

        // clear the canvas
        redraw();

        // now - change the applet to be ready for the next big bang
        currentState = READY;
        bigButton.setText(READY); 
      }
    }
  }

  protected void signalEnd(){
    currentState = CLEAR;
    bigButton.setText(CLEAR); 
  }
  
  /**
   * Set up the applet for running the world interactions
   */
  protected void bbstart(){
    // set the speed for the timer
    SPEED  = getSpeed();    

    // now - change the applet so we can see that the world runs
    bigButton.setText(currentState); 
 
    // give this world a Canvas and a way to notify the applet when done
    currentworld.initWorld(theCanvas, this.bigButton); 

    // start the world
    currentworld.bigBang(WIDTH, HEIGHT, SPEED);
    
    // make the Canvas visible and responsive to key events
    theCanvas.addNotify();
    theCanvas.requestFocusInWindow(); 
  }

  
  /**
   * The applet action on return from suspension and after init.
   */
  public void start(){
    stopped = false;
    currentworld.worldStopped = false;
  }

  /**
   * Process applet suspension: 
   * mark it stopped. 
   * If the world is currently running, end the world,
   * stop the timer and remove the key listener
   */
  public void stop(){
    //if the world is running, end it, remove listeners and timer
    if (currentworld.worldExists){
      theCanvas.drawImage(new TextImage(new Posn(10, 20), 
          "World stopped when not visible", Color.black));
      
      currentworld.endOfWorld("Stop button stopped the world");
      
      // now - change the applet so we can see that the world stopped
      currentState = CLEAR;
      bigButton.setText(CLEAR); 
    }
    
    // note that the applet is stopped
    stopped = true;
  }
 

  /**
   * Draw the background for the entire applet panel
   * in light gray color
   */
  protected void redraw(){
    Color back = Color.darkGray;
    theCanvas.drawImage(new RectangleImage(new Posn(WIDTH/2, HEIGHT/2), 
        WIDTH, HEIGHT, back));
  }

  /**
   * Do nothing - we draw our own world images
   */
  public void paint(){ }
}



