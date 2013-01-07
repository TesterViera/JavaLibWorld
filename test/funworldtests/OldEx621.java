package funworldtests;

import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

public class OldEx621 extends World
{
    final int xPos = 50, yPos = 50;
    final int degrees = 90;
    WorldImage current;
    
    public OldEx621 (WorldImage initial)
    {
        this.current = initial;
    }
    
    public WorldImage makeImage ()
    {
        return this.current.moved (this.xPos, this.yPos);
    }
    
    public World onTick ()
    {
        return new OldEx621 (this.current.rotatedInPlace (this.degrees));
    }
}