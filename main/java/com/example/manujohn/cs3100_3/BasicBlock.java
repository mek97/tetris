package com.example.manujohn.cs3100_3;

import android.graphics.Color;
import android.media.MediaExtractor;

/**
 * Created by manujohn on 05/10/17.
 */

public class BasicBlock {
    int type;
    int colour;
    int tetra_id;
    Coordinate C;
    int active;

    BasicBlock(int type, int colour, int tetra_id, Coordinate C, int active)
    {
        this.type = type;
        this.colour = colour;
        this.tetra_id = tetra_id;
        this.C = C;
        this.active = active;

    }
    public void init()
    {
        colour=0;
        type=0;
    }
    public int getColor(int color)
    {
        switch(color)
        {
            case 1: return Color.BLUE;
            case 2: return Color.YELLOW;
            case 3: return Color.RED;
            case 4: return Color.GREEN;
            case 5: return Color.CYAN;
            case 6: return Color.MAGENTA;
            case 7: return Color.DKGRAY;
            default: return Color.TRANSPARENT;
        }

    }
    public BasicBlock copy()
    {
        return new BasicBlock(type, colour, tetra_id, C, active);
    }

    public void Assign_V(int type, int colour, int tetra_id, Coordinate C, int active)
    {
        this.type = type;
        this.colour = colour;
        this.tetra_id = tetra_id;
        this.C.c = C.c;
        this.C.r = C.r;
        this.active = active;

    }

    public void Assign_V(BasicBlock B)
    {
        this.type = B.type;
        this.colour = B.colour;
        this.tetra_id = B.tetra_id;
        this.C.r = B.C.r;
        this.C.c = B.C.c;
        this.active = B.active;

    }

    public static BasicBlock position(BasicBlock[][] B,Coordinate A)
    {
        return B[A.r][A.c];
    }
}

