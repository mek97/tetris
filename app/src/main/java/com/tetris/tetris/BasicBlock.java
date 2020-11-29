package com.tetris.tetris;

import android.graphics.Color;

class BasicBlock {
    int type;
    int colour;
    int tetraId;
    Coordinate coordinate;
    int active;

    BasicBlock(int type, int colour, int tetraId, Coordinate coordinate, int active) {
        this.type = type;
        this.colour = colour;
        this.tetraId = tetraId;
        this.coordinate = coordinate;
        this.active = active;

    }

    static BasicBlock position(BasicBlock[][] B, Coordinate A) {

        return B[A.r][A.c];
    }

    int getColor(int color) {
        switch (color) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.RED;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.CYAN;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.DKGRAY;
            default:
                return Color.TRANSPARENT;
        }

    }

    BasicBlock copy() {

        return new BasicBlock(type, colour, tetraId, coordinate, active);
    }

    void set(int type, int colour, int tetraId, Coordinate coordinate, int active) {
        this.type = type;
        this.colour = colour;
        this.tetraId = tetraId;
        this.coordinate.c = coordinate.c;
        this.coordinate.r = coordinate.r;
        this.active = active;

    }

    void set(BasicBlock B) {
        this.type = B.type;
        this.colour = B.colour;
        this.tetraId = B.tetraId;
        this.coordinate.r = B.coordinate.r;
        this.coordinate.c = B.coordinate.c;
        this.active = B.active;

    }
}

