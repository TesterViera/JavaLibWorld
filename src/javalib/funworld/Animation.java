package javalib.funworld;

import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;
import javalib.worldimages.Drawable;


/**
 * The Animation class hides the common coding pattern of storing the model
 * as an instance variable and updating it at each event.
 * Not as much as I would like: if you write a subclass of Animation, you have to
 * write a constructor that starts with super(newModel).
 * 
 * @author Stephen Bloch
 * @version Jan. 1, 2013
 */
public abstract class Animation<Model> extends World
{
    // instance variables - replace the example below with your own
    private Model model;

    /**
     * Get the current model.
     * 
     * @return the current Model.
     */
    public Model getModel () { return this.model; }
    
    /**
     * Constructor for Animation<Model>.
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
     * A version of makeImage(Model) that defaults to using the current Model.
     * 
     * @see World.makeImage()
     */
    public WorldImage makeImage ()
    {
        return this.makeImage (this.model);
    }
    
    /**
     * Produce a WorldImage representation of the model.
     * Users subclassing Animation <em>must</em> override this.
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
     * @param s          the key that was pressed
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
}
