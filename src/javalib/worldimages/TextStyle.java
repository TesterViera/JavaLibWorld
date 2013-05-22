package javalib.worldimages;


/**
 * The available TextStyles are NORMAL (aka REGULAR), ITALIC, BOLD, and BOLD_ITALIC (aka ITALIC_BOLD).
 * 
 * I really didn't like the idea of users passing in the numbers 0, 1, 2, or 3
 * to specify text styles, even if that's the way Graphics2D.drawText wants them.
 * And the ints actually bit me: since both the text style and the font size are
 * optional, and they're right next to one another in the constructor parameter list,
 * I created some text that I thought was NORMAL and was instead font-size 0, or
 * what I thought was ITALIC and was instead font-size 1.  This version is safer
 * against that kind of mistake.
 * 
 * @author Stephen Bloch
 * @version Dec. 5, 2012
 */
public enum TextStyle
{
    NORMAL, ITALIC, BOLD, BOLD_ITALIC;
    
    /**
     * A synonym for NORMAL.
     */
    public static final TextStyle REGULAR = NORMAL;
    /**
     * A synonym for BOLD_ITALIC.
     */
    public static final TextStyle ITALIC_BOLD = BOLD_ITALIC;
    /**
     * Lower-case synonyms.
     */
    public static final TextStyle normal = NORMAL;
    public static final TextStyle italic = ITALIC;
    public static final TextStyle bold = BOLD;
    public static final TextStyle bold_italic = BOLD_ITALIC;
    public static final TextStyle regular = NORMAL;
    public static final TextStyle italic_bold = BOLD_ITALIC;
    
    public int toInt ()
    {
        switch (this) {
            case NORMAL : return 0;
            case BOLD : return 1;
            case ITALIC : return 2;
            case BOLD_ITALIC : return 3;
            default: return -1; // should never happen
        }
    }
}
