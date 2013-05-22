package funworldtests;

import javalib.worldimages.*;
import javalib.funworld.*;

public class Ex611 extends Animation<WorldImage>
{
    Ex611 (WorldImage initial)
    {
        super(initial);
    }
    
    public WorldImage makeImage (WorldImage current)
    {
        return current;
    }
}
