package javalib.impworld;


import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;
import javalib.worldimages.Drawable;


/**
 * <p>The Animation class hides the common coding pattern of storing the model
 * as an instance variable and updating it at each event.
 * Not as much as I would like: if you write a subclass of Animation, you have to
 * write a constructor that starts with super(newModel).</p>
 * <p>This is almost identical to the Animation class in funworld,
 * except that the event handlers return <code>void</code> rather than
 * <code>World</code> or <code>Model</code>.  They are given a <code>Model</code>
 * parameter, as before, but now they're expected to mutate it.
 * 
 * @author Stephen Bloch
 * @version Jan. 1, 2013
 */
public abstract class Animation<Model> extends World
{
    // instance variables - replace the example below with your own
    private Model model;

    public Model getModel () { return this.model; }
    
    public Animation (Model newModel)
    {
        super();
        this.model = newModel;
    }
    
    public void onTick()
    {
        this.gotTick(this.model);
    }
    public void onKeyEvent(String s)
    {   
        this.gotKeyEvent(this.model, s);
    }
    public void onMouseClicked(Posn mouse)
    {
        this.gotMouseClicked(this.model, mouse);
    }
    public void onMouseEntered(Posn mouse)
    {
        this.gotMouseEntered(this.model, mouse);
    }
    public void onMouseExited(Posn mouse)
    {
        this.gotMouseExited(this.model, mouse);
    }
    public void onMousePressed(Posn mouse)
    {
        this.gotMousePressed(this.model, mouse);
    }
    public void onMouseReleased(Posn mouse) 
    {
        this.gotMouseReleased(this.model, mouse);
    }
    public WorldImage makeImage ()
    {
        return this.makeImage (this.model);
    }
    
    /**
     * User must override the following.
     */
    public abstract WorldImage makeImage (Model oldModel);
    
    /**
     * User can override the following.
     */
    public void gotTick(Model oldModel) { }
    public void gotKeyEvent(Model oldModel, String s) { }
    public void gotMouseClicked(Model oldModel, Posn mouse) { }
    public void gotMouseEntered(Model oldModel, Posn mouse) { }
    public void gotMouseExited(Model oldModel, Posn mouse) { }
    public void gotMousePressed(Model oldModel, Posn mouse) { }
    public void gotMouseReleased(Model oldModel, Posn mouse) { }
}
