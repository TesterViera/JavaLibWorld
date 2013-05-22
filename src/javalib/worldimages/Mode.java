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
    
    /**
     * A synonym for FILLED.
     */
    public static final Mode SOLID = FILLED;
    /**
     * A synonym for FILLED.
     */
    public static final Mode filled = FILLED;
    /**
     * A synonym for FILLED.
     */
    public static final Mode solid = FILLED;
    /**
     * A synonym for OUTLINED.
     */
    public static final Mode outlined = OUTLINED;
}
