package com.tetris.tetris;

class Coordinate {
    int r, c;

    Coordinate(int r, int c) {
        this.r = r;
        this.c = c;
    }

    static Coordinate add(Coordinate A, Coordinate B) {
        return new Coordinate(A.r + B.r, A.c + B.c);
    }

    static Coordinate sub(Coordinate A, Coordinate B) {
        return new Coordinate(A.r - B.r, A.c - B.c);
    }

    static Coordinate rotateAntiClock(Coordinate A) {

        return new Coordinate(-A.c, A.r);
    }

    static boolean isEqual(Coordinate A, Coordinate B) {
        return A.r == B.r && A.c == B.c;
    }

}
