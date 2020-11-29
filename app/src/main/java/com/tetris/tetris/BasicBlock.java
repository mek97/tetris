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
        this.coordinate.x = coordinate.x;
        this.coordinate.y = coordinate.y;
        this.active = active;

    }

    boolean isActive() {
        return this.active == -1;
    }

    void set(BasicBlock B) {
        this.type = B.type;
        this.colour = B.colour;
        this.tetraId = B.tetraId;
        this.coordinate.y = B.coordinate.y;
        this.coordinate.x = B.coordinate.x;
        this.active = B.active;

    }
}

