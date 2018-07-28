package com.example.manujohn.cs3100_3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.*;

public class GameState {

    BasicBlock board[][];
    BasicBlock curr[];
    int r;
    int c;
    boolean status;
    tetramino falling;
    HashMap <Integer,tetramino> tetra_map;
    Integer ctr;
    int score;
    boolean pause;

    GameState(int r,int c,int x)
    {
        this.r = r;
        this.c = c;
        this.pause = false;

        this.status = true;
        System.out.println(Integer.toString(r)+" "+Integer.toString(c));

        curr = new BasicBlock[4];

        board = new BasicBlock[r][c];

        for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                board[i][j] = new BasicBlock(-1,-1,-1,new Coordinate(i,j),0);

//				int type;
//				int colour;
//				int tetra_id;
//				Coordinate C;
//				int active;
            }
        }
        tetra_map = new HashMap<Integer,tetramino>();
        ctr = 0;
        score=0;

        falling = new tetramino(x,this);


    }

    public boolean shiftDown() {
        return falling.shiftDown(this);
    }

    public boolean userPressedLeft() {
        return falling.Left(this);
    }

    public boolean userPressedRight() {
        return falling.Right(this);
    }

    public void freeze_update(int x)
    {
        falling.update(this);
        falling.line_remove(this);
        falling = new tetramino(x,this);
    }


    public boolean userPressedRotate()
    {
        return falling.Rotate(this);
    }
}
