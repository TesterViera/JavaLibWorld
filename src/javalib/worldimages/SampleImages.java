package javalib.worldimages;


/**
 * A home for sample images students can use without worrying about files and URL's.
 * 
 * @author Stephen Bloch
 * @version Dec. 28, 2012
 */
public abstract class SampleImages 
{
    private static int dummy = initEverything();
    // For some reason the following don't work as initializers, but they do work as assignments inside a method.
    
    public static WorldImage bloch;// = new FromURLImage (find("Images/bloch.jpg"));
    public static WorldImage hieroglyphics;// = new FromURLImage (find("javalib/worldimages/Images/hieroglyphics.png"));
    public static WorldImage hacker;// = new FromURLImage (find("javalib/worldimages/Images/mad_hacker.png"));
    public static WorldImage book;// = new FromURLImage (find("javalib/worldimages/Images/qbook.png"));
    public static WorldImage stickFigure;// = new FromURLImage (find("javalib/worldimages/Images/stick-figure.png"));
    public static WorldImage schemeLogo;// = new FromURLImage (find("javalib/worldimages/Images/schemelogo.png"));
    public static WorldImage calendar;// = new FromURLImage (find("javalib/worldimages/Images/calendar.png"));
    public static WorldImage fish;// = new FromURLImage (find("javalib/worldimages/Images/fish.png"));
    public static WorldImage greenFish;// = new FromURLImage (find("javalib/worldimages/Images/green-fish.png"));
    public static WorldImage pinkFish;// = new FromURLImage (find("javalib/worldimages/Images/pink-fish.png"));
    public static WorldImage shark;// = new FromURLImage (find("javalib/worldimages/Images/shark.png"));
    public static WorldImage family;// = new FromURLImage (find("javalib/worldimages/Images/family.png"));
    

    public static int initEverything ()
    {
        try {
            bloch = new FromURLImage (find("Images/bloch.jpg"));
            hieroglyphics = new FromURLImage (find("Images/hieroglyphics.png"));
            hacker = new FromURLImage (find("Images/mad_hacker.png"));
            book = new FromURLImage (find("Images/qbook.png"));
            stickFigure = new FromURLImage (find("Images/stick-figure.png"));
            schemeLogo = new FromURLImage (find("Images/schemelogo.png"));
            calendar = new FromURLImage (find("Images/calendar.png"));
            fish = new FromURLImage (find("Images/fish.png"));
            greenFish = new FromURLImage (find("Images/green-fish.png"));
            pinkFish = new FromURLImage (find("Images/pink-fish.png"));
            shark = new FromURLImage (find("Images/shark.png"));
            family = new FromURLImage (find("Images/family.png"));
        }
        catch (java.io.IOException e)
        {
            System.err.println ("Unable to initialize SampleImages: " + e);
        }
        return 0;
    }
    
    private static java.net.URL find (String name)
    {
        java.net.URL result = SampleImages.class.getResource(name);
        if (result == null)
            System.err.println ("Couldn't find " + name);
        return result;
    }
}
