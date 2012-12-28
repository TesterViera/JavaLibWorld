package javalib.worldimages;

import java.awt.Color;

interface ImageMap
{
   public Color pixelColor (int x, int y, Color oldColor, Object other);
}
