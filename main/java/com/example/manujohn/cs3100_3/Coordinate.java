package com.example.manujohn.cs3100_3;

public class Coordinate {
    public int r, c;

    public Coordinate(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public Coordinate(Coordinate C) {
        this.r = C.r;
        this.c = C.c;
    }

    public static Coordinate add(Coordinate A, Coordinate B)
    {
        return new Coordinate(A.r+B.r,A.c+B.c);
    }

    public static Coordinate sub(Coordinate A, Coordinate B)
    {
        return new Coordinate(A.r-B.r,A.c-B.c);
    }

    public static Coordinate iota(Coordinate A)
    {
        return new Coordinate(-A.c,A.r);
    }

    public static boolean Isequal(Coordinate A, Coordinate B)
    {
        if(A.r==B.r && A.c == B.c)
            return true;
        else
            return false;
    }

}
