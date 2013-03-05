package javalib.funworld;

import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;
import javalib.worldimages.Drawable;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.AImage;


/**
 * An easy-to-use class for model/view GUI programs.
 * 
 * The Animation class maintains the model as an instance variable and updates it at each event.
 * To use it,
 * <ol>
 *  <li>Decide what type the "model" (<em>i.e.</em> the internal information that changes
 *  while the program runs) should be.  Some common choices:
 *   <ul>
 *    <li><tt>WorldImage</tt> (for an animation that does purely graphical operations at each event)</li>
 *    <li><tt>Integer</tt> (for an animation whose current state can be described by a single integer,
 *    and whose changes are arithmetic operations)</li>
 *    <li><tt>String</tt> (for an animation whose current state can be described by a string, and
 *    whose changes are string operations)</li>
 *    <li><tt>Posn</tt> (for an animation whose current state consists of an (x,y) coordinate pair, and
 *    which might change either or both coordinates in response to events)</li>
 *    <li>A user-defined class (if you need to keep track of more information than the above choices give you)</li>
 *   </ul>
 *  </li>
 *  <li>Define a subclass of Animation&lt;<em>your model type</em>&gt;.  For example,
 *  if you were using an <tt>Integer</tt> model, you could write
 *  <pre>import javalib.funworld.*;
 *  class MyAnimation extends Animation&lt;Integer&gt;
 *  {
 *      // constructors and methods will go here
 *  }</pre></li>
 *  <li>Add a constructor that takes in the specified type and calls <tt>super</tt> on it, <em>e.g.</em>
 *  <pre>    public MyAnimation (Integer initial)
 *      {
 *          super(initial);
 *          // maybe do other stuff here
 *      }</pre>
 *  Alternatively, if your animation should always start with the same initial value, you can write a
 *  constructor that takes in nothing, <em>e.g.</em>
 *  <pre>    public MyAnimation ()
 *      {
 *          super(0);
 *          // maybe do other stuff here
 *      }</pre>
 *  </li>
 *  <li>Add event-handlers as necessary.  For example, if you wanted to add 1 to the integer model at
 *  every clock tick, you could write
 *  <pre>    public Integer gotTick (Integer old)
 *      {
 *          return old + 1;
 *      }</pre>
 *  Of course, these methods should have test cases in an accompanying <tt>TestMyAnimation</tt> class.
 *  </li>
 *  <li>One event handler you'll <em>always</em> need is <code>makeImage</code>, which is how the
 *  animation library decides what to show in the animation window.  For example, if you wanted to
 *  display a blue disk whose radius was the integer model, you could write
 *  <pre>    public WorldImage makeImage (Integer current)
 *      {
 *          return AImage.makeCircle(current, new Blue(), Mode.FILLED);
 *      }<pre>
 *  </li>
 *  <li>Once all the event handlers pass all their test cases, you can try running the whole animation
 *  with code like
 *  <pre>new MyAnimation(5).bigBang (300, 200, 0.5);</pre>
 *  which creates a <tt>MyAnimation</tt> object with initial model 5 and runs it in a 300x200
 *  window, with the clock ticking every 0.5 second.
 *  </li>
 * </ol>
 * 
 * @author Stephen Bloch
 * @version March 5, 2013
 */
public abstract class Animation<Model> extends World
{
    // instance variables - replace the example below with your own
    private Model model;

    /**
     * Get the current model.
     * 
     * You shouldn't need to override this.  You shouldn't need to call it often either, since
     * all the <tt>gotWhatever</tt> methods are given the current model as an argument
     * anyway, but you can call it if you want.
     * 
     * @return the current Model.
     */
    public Model getModel () { return this.model; }
    
    /**
     * Constructor for Animation<Model>.
     * 
     * If you're subclassing <tt>Animation</tt>, you need to write a constructor that looks like 
     * <pre>public MyKindOfAnimation (Model newModel)
     * {
     *     super(newModel);
     *     // other initialization can go here
     * }</pre>
     * 
     * @param newModel the initial model for the Animation.
     */
    public Animation (Model newModel)
    {
        super();
        this.model = newModel;
    }
    
    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onTick()
     */
    public World onTick()
    {
        this.model = this.gotTick(this.model);
        return this;
    }

    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onKeyEvent()
     */
    public World onKeyEvent(String s)
    {   
        this.model = this.gotKeyEvent(this.model, s);
        return this;
    }

    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onMouseClicked()
     */
    public World onMouseClicked(Posn mouse)
    {
        this.model = this.gotMouseClicked(this.model, mouse);
        return this;
    }

    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onMouseDragged()
     */
    public World onMouseDragged(Posn mouse)
    {
        this.model = this.gotMouseDragged(this.model, mouse);
        return this;
    }
    
    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onMouseMoved()
     */
    public World onMouseMoved(Posn mouse)
    {
        this.model = this.gotMouseMoved(this.model, mouse);
        return this;
    }

    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onMouseEntered()
     */
    public World onMouseEntered(Posn mouse)
    {
        this.model = this.gotMouseEntered(this.model, mouse);
        return this;
    }
    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @override World.onMouseExited()
     */
    public World onMouseExited(Posn mouse)
    {
        this.model = this.gotMouseExited(this.model, mouse);
        return this;
    }
    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @see World.onMousePressed()
     */
    public World onMousePressed(Posn mouse)
    {
        this.model = this.gotMousePressed(this.model, mouse);
        return this;
    }
    /**
     * User shouldn't need to use or override this method,
     * unless you're doing something with the World more complicated
     * than just replacing the Model.
     * 
     * @see World.onMouseReleased()
     */
    public World onMouseReleased(Posn mouse) 
    {
        this.model = this.gotMouseReleased(this.model, mouse);
        return this;
    }
    
    /**
     * User shouldn't need to use or override this method.
     * 
     * @see World.worldEnds()
     */
    public WorldEnd worldEnds ()
    {
        return this.worldEnds (this.model);
    }
    
    /**
     * A version of makeImage(Model) that defaults to using the current Model.
     * 
     * You probably don't need to override this, but you can call it if you want.
     * 
     * @see World.makeImage()
     */
    public WorldImage makeImage ()
    {
        return this.makeImage (this.model);
    }
    
    /**
     * Produce a WorldImage representation of the model; users subclassing Animation <em>must</em> override this.
     * 
     * @param oldModel   the current model
     * @return a WorldImage based on the current model
     * @since Jan. 2, 2012
     */
    public abstract WorldImage makeImage (Model oldModel);
    
    /**
     * Produce the new model after a tick event.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @return           the new model
     */
    public Model gotTick(Model oldModel) { return oldModel; }

    /**
     * Produce the new model after a keyboard event.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param s          the key that was pressed <br>
     *  This will be either a single character (for letters, numbers, punctuation) or
     *  one of the following:
     *  "backspace", "tab", "newline", "escape", "page up", "page down", "end", "home",
     *  "left", "up", "right", "down", "delete", or "f1" through "f12"
     * @return           the new model
     */
    public Model gotKeyEvent(Model oldModel, String s) { return oldModel; }

    /**
     * Produce the new model after a mouse-click event.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMouseClicked(Model oldModel, Posn mouse) { return oldModel; }

    /**
     * Produce the new model after a mouse-move event.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMouseMoved(Model oldModel, Posn mouse) { return oldModel; }

    /**
     * Produce the new model after a mouse-drag event.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMouseDragged(Model oldModel, Posn mouse) { return oldModel; }

    /**
     * Produce the new model after the mouse enters the window.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMouseEntered(Model oldModel, Posn mouse) { return oldModel; }

    /**
     * Produce the new model after the mouse leaves the window.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMouseExited(Model oldModel, Posn mouse) { return oldModel; }

    /**
     * Produce the new model after the mouse is pressed (but not yet released).
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMousePressed(Model oldModel, Posn mouse) { return oldModel; }

    /**
     * Produce the new model after the mouse is released.
     * Users subclassing Animation may override this;
     * if they don't, the default handler makes no change.
     * 
     * @param oldModel   the current model
     * @param mouse      the location of the mouse
     * @return           the new model
     */
    public Model gotMouseReleased(Model oldModel, Posn mouse) { return oldModel; }
    
    /**
     * Decide whether the animation should end yet, and if so, what the window
     * should look like.  Users subclassing Animation may override this;
     * if they don't, the default handler says "no, don't end the world."
     * 
     * @param oldModel   the current model
     * @return           a WorldEnd object, which comprises
     *      a boolean, telling whether the animation should end, and
     *      a WorldImage, the final contents of the animation window.
     */
    public WorldEnd worldEnds (Model oldModel)
    {
        final WorldEnd defaultAnswer = new WorldEnd (false, AImage.makeRectangle(0,0));
        return defaultAnswer;
    }
    
}
