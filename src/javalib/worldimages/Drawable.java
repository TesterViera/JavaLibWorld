package javalib.worldimages;


/**
 * Drawable: any class that has a makeImage() method.
 * 
 * @author Stephen Bloch
 * @version Jan. 1, 2013
 */
public interface Drawable
{
	/**
	 * Produce a WorldImage representation of this object.
	 * You must provide a definition of this method in any class to be used as a model.
	 * 
	 * @return a WorldImage
	 */
    public abstract WorldImage makeImage();
}
