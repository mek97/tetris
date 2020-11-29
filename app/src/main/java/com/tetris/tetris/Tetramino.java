package com.tetris.tetris;

class Tetramino {
    BasicBlock[] blocks;

    Tetramino() {
        blocks = new BasicBlock[4];
    }

    Tetramino(int type, int tetraId) {
        blocks = new BasicBlock[4];

        if (type == 1) {
            blocks[0] = new BasicBlock(1, 1, tetraId, new Coordinate(0, 10), -1);
            blocks[1] = new BasicBlock(1, 1, tetraId, new Coordinate(1, 10), -1);
            blocks[2] = new BasicBlock(1, 1, tetraId, new Coordinate(1, 11), -1);
            blocks[3] = new BasicBlock(1, 1, tetraId, new Coordinate(0, 11), -1);
        } else if (type == 2) {
            blocks[0] = new BasicBlock(2, 2, tetraId, new Coordinate(0, 10), -1);
            blocks[1] = new BasicBlock(2, 2, tetraId, new Coordinate(0, 11), -1);
            blocks[2] = new BasicBlock(2, 2, tetraId, new Coordinate(1, 10), -1);
            blocks[3] = new BasicBlock(2, 2, tetraId, new Coordinate(2, 10), -1);

        } else if (type == 3) {
            blocks[0] = new BasicBlock(3, 3, tetraId, new Coordinate(0, 11), -1);
            blocks[1] = new BasicBlock(3, 3, tetraId, new Coordinate(0, 10), -1);
            blocks[2] = new BasicBlock(3, 3, tetraId, new Coordinate(1, 11), -1);
            blocks[3] = new BasicBlock(3, 3, tetraId, new Coordinate(2, 11), -1);

        } else if (type == 4) {
            blocks[0] = new BasicBlock(4, 4, tetraId, new Coordinate(1, 10), -1);
            blocks[1] = new BasicBlock(4, 4, tetraId, new Coordinate(0, 10), -1);
            blocks[2] = new BasicBlock(4, 4, tetraId, new Coordinate(1, 11), -1);
            blocks[3] = new BasicBlock(4, 4, tetraId, new Coordinate(2, 10), -1);

        } else if (type == 5) {
            blocks[0] = new BasicBlock(5, 5, tetraId, new Coordinate(1, 11), -1);
            blocks[1] = new BasicBlock(5, 5, tetraId, new Coordinate(1, 10), -1);
            blocks[2] = new BasicBlock(5, 5, tetraId, new Coordinate(0, 10), -1);
            blocks[3] = new BasicBlock(5, 5, tetraId, new Coordinate(2, 11), -1);
        } else if (type == 6) {
            blocks[0] = new BasicBlock(6, 6, tetraId, new Coordinate(1, 11), -1);
            blocks[1] = new BasicBlock(6, 6, tetraId, new Coordinate(0, 11), -1);
            blocks[2] = new BasicBlock(6, 6, tetraId, new Coordinate(1, 10), -1);
            blocks[3] = new BasicBlock(6, 6, tetraId, new Coordinate(2, 10), -1);

        } else if (type == 7) {
            blocks[0] = new BasicBlock(7, 7, tetraId, new Coordinate(0, 10), -1);
            blocks[1] = new BasicBlock(7, 7, tetraId, new Coordinate(1, 10), -1);
            blocks[2] = new BasicBlock(7, 7, tetraId, new Coordinate(2, 10), -1);
            blocks[3] = new BasicBlock(7, 7, tetraId, new Coordinate(3, 10), -1);
        }
    }

    BasicBlock getBlock(int pos) {
        return this.blocks[pos];
    }

    Tetramino copy(int tetraId) {
        BasicBlock[] blocks = new BasicBlock[4];
        for (int i = 0; i < 4; i++) {
            blocks[i] = this.blocks[i].copy();
            blocks[i].tetraId = tetraId;
        }
        Tetramino tetramino = new Tetramino();
        tetramino.blocks = blocks;
        return tetramino;
    }

    void moveDown() {

        for (int i = 0; i < 4; i++) {
            blocks[i].coordinate.y++;
        }
    }

    void moveLeft() {

        for (int i = 0; i < 4; i++) {
            blocks[i].coordinate.x--;
        }
    }

    void moveRight() {

        for (int i = 0; i < 4; i++) {
            blocks[i].coordinate.x++;
        }
    }

    void performClockWiseRotation() {

        for (int i = 0; i < 4; i++) {
            Coordinate baseCoordinate = Coordinate.sub(blocks[i].coordinate, blocks[0].coordinate);
            blocks[i].coordinate = Coordinate.add(Coordinate.rotateAntiClock(baseCoordinate), blocks[0].coordinate);
        }
    }
}
