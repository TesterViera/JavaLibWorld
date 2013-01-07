package funworldtests;

import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

public class Ex621 extends Animation<WorldImage>
{
    final int xPos = 50, yPos = 50;
    final int degrees = 90;
    
    Ex621 (WorldImage initial)
    {
        super(initial);
    }
    
    public WorldImage makeImage (WorldImage current)
    {
        return current.moved(this.xPos, this.yPos);
    }
    
    public WorldImage gotTick (WorldImage current)
    {
        return current.rotatedInPlace (this.degrees);
    }
}