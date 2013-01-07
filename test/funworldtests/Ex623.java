package funworldtests;

import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

public class Ex623 extends Animation<WorldImage>
{
    WorldImage background = AImage.makeRectangle (100, 100, Color.white, Mode.FILLED);
    
    Ex623 (WorldImage initial)
    {
        super(initial);
    }
    
    public WorldImage makeImage (WorldImage current)
    {
        return this.background.place (current, 50, 50);
    }
    
    public WorldImage gotTick (WorldImage current)
    {
        return current.yReflected();
    }
}

