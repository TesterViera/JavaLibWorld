package javalib.worldimages;


/**
 * The available Modes are FILLED (aka SOLID) and OUTLINED.
 * 
 * @author Stephen Bloch
 * @version Feb 10, 2013
 */
public enum Mode
{
    FILLED, OUTLINED;
    
    public static final Mode SOLID = FILLED;
    public static final Mode filled = FILLED;
    public static final Mode solid = FILLED;
    public static final Mode outlined = OUTLINED;
}
