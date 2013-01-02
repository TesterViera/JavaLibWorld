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

    public Model getModel () { return this.model; }
    
    public Animation (Model newModel)
    {
        super();
        this.model = newModel;
    }
    
    public World onTick()
    {
        this.model = this.gotTick(this.model);
        return this;
    }
    public World onKeyEvent(String s)
    {   
        this.model = this.gotKeyEvent(this.model, s);
        return this;
    }
    public World onMouseClicked(Posn mouse)
    {
        this.model = this.gotMouseClicked(this.model, mouse);
        return this;
    }
    public World onMouseEntered(Posn mouse)
    {
        this.model = this.gotMouseEntered(this.model, mouse);
        return this;
    }
    public World onMouseExited(Posn mouse)
    {
        this.model = this.gotMouseExited(this.model, mouse);
        return this;
    }
    public World onMousePressed(Posn mouse)
    {
        this.model = this.gotMousePressed(this.model, mouse);
        return this;
    }
    public World onMouseReleased(Posn mouse) 
    {
        this.model = this.gotMouseReleased(this.model, mouse);
        return this;
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
    public Model gotTick(Model oldModel) { return this.model; }
    public Model gotKeyEvent(Model oldModel, String s) { return this.model; }
    public Model gotMouseClicked(Model oldModel, Posn mouse) { return this.model; }
    public Model gotMouseEntered(Model oldModel, Posn mouse) { return this.model; }
    public Model gotMouseExited(Model oldModel, Posn mouse) { return this.model; }
    public Model gotMousePressed(Model oldModel, Posn mouse) { return this.model; }
    public Model gotMouseReleased(Model oldModel, Posn mouse) { return this.model; }
}
