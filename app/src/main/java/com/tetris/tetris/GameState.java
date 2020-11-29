package com.tetris.tetris;

import java.util.HashMap;

class GameState {

    BasicBlock[][] board;
    int r;
    int c;
    boolean status;
    Tetramino falling;
    HashMap<Integer, Tetramino> tetraminoMap;
    Integer ctr;
    int score;
    boolean pause;

    GameState(int r, int c, int tetraminoType) {
        this.r = r;
        this.c = c;
        this.pause = false;

        this.status = true;

        board = new BasicBlock[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                board[i][j] = new BasicBlock(-1, -1, -1, new Coordinate(i, j), 0);
            }
        }
        tetraminoMap = new HashMap<Integer, Tetramino>();
        ctr = 0;
        score = 0;

        falling = new Tetramino(tetraminoType, this);


    }

    boolean shiftDown() {

        return falling.shiftDown(this);
    }

    void userPressedLeft() {

        falling.moveLeft(this);
    }

    void userPressedRight() {

        falling.moveRight(this);
    }

    void freezeUpdate(int x) {
        falling.updatePosition(this);
        falling.lineRemove(this);
        falling = new Tetramino(x, this);
    }


    void userPressedRotate() {

        falling.performClockWiseRotation(this);
    }

}
