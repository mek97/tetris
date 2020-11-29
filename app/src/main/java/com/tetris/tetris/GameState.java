package com.tetris.tetris;

import android.util.SparseArray;

class GameState {

    int rows;
    int columns;
    boolean status;
    Integer ctr;
    int score;
    boolean pause;
    BasicBlock[][] board;
    Tetramino falling;
    SparseArray<Tetramino> tetraminos;
    int difficulty;

    GameState(int rows, int columns, int FallingTetraminoType) {

        this.rows = rows;
        this.columns = columns;
        this.pause = false;
        ctr = 0;
        score = 0;
        this.status = true;

        board = new BasicBlock[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                board[row][column] = new BasicBlock(-1, -1, -1, new Coordinate(row, column), 0);
            }
        }

        tetraminos = new SparseArray<>();

        falling = new Tetramino(FallingTetraminoType, this.ctr);

        tetraminos.put(this.ctr, falling);

        difficulty = 1;

    }

    BasicBlock getCoordinateBlock(Coordinate A) {

        return this.board[A.y][A.x];
    }

    boolean isConflicting(Coordinate X) {

        if (X.x < 0 || X.x >= this.columns || X.y < 0 || X.y >= this.rows)
            return true;

        return this.getCoordinateBlock(X).active == -1;

    }

    boolean canTetraminoDisplace(Tetramino tetramino, Coordinate displacement) {

        for (int pos = 0; pos < 4; pos++) {
            if (tetramino.getBlock(pos).isActive()) {
                Coordinate shifted = Coordinate.add(tetramino.blocks[pos].coordinate, displacement);
                if (isConflicting(shifted)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean moveFallingTetraminoDown() {

        if (canTetraminoDisplace(falling, new Coordinate(1, 0))) {
            falling.moveDown();
            return true;
        } else {
            return false;
        }

    }

    boolean moveFallingTetraminoLeft() {

        if (canTetraminoDisplace(falling, new Coordinate(0, -1))) {
            falling.moveLeft();
            return true;
        } else {
            return false;
        }
    }

    boolean moveFallingTetraminoRight() {

        if (canTetraminoDisplace(falling, new Coordinate(0, 1))) {
            falling.moveRight();
            return true;
        } else {
            return false;
        }
    }

    boolean rotateFallingTetraminoAntiClock() {
        if (falling.blocks[0].type == 1) {
            return true;
        } else {
            for (int pos = 0; pos < 4; pos++) {
                if (!falling.getBlock(pos).isActive())
                    continue;

                Coordinate baseCoordinate = Coordinate.sub(falling.getBlock(pos).coordinate, falling.getBlock(0).coordinate);
                if (isConflicting(Coordinate.add(Coordinate.rotateAntiClock(baseCoordinate), falling.getBlock(0).coordinate))) {
                    return false;
                }
            }
            falling.performClockWiseRotation();
            return true;
        }
    }

    void paintTetramino(Tetramino tetramino) {
        for (int pos = 0; pos < 4; pos++) {
            if (!tetramino.getBlock(pos).isActive())
                continue;
            BasicBlock basicBlock = tetramino.getBlock(pos);
            this.getCoordinateBlock(basicBlock.coordinate).set(basicBlock);
        }
    }

    void pushNewTetramino(int tetraminoType) {
        this.ctr++;

        falling = new Tetramino(tetraminoType, this.ctr);
        this.tetraminos.put(this.ctr, falling);
        for (int pos = 0; pos < 4; pos++) {
            if (this.getCoordinateBlock(this.falling.blocks[pos].coordinate).active == -1)
                this.status = false;
        }
    }

    void incrementScore() {
        this.score++;
    }

    void lineRemove() {
        boolean removeLines = false;
        do {
            for (int row = this.rows - 1; row >= 0; row--) {
                boolean rowIsALine = true;
                for (int column = 0; column < this.columns; column++) {
                    if (this.board[row][column].active == 0) {
                        rowIsALine = false;
                        break;
                    }
                }
                if (!rowIsALine) {
                    continue;
                }

                for (int column = 0; column < this.columns; column++) {
                    this.board[row][column].active = 0;

                    Tetramino tetramino = this.tetraminos.get((this.board[row][column].tetraId));

                    if (tetramino == null)
                        continue;

                    for (int pos = 0; pos < 4; pos++) {
                        if (tetramino.blocks[pos].active == 0) {
                            continue;
                        }

                        if (tetramino.blocks[pos].coordinate.y == row && tetramino.blocks[pos].coordinate.x == column) {
                            tetramino.blocks[pos].active = 0;

                            this.ctr++;
                            Tetramino upperTetramino = tetramino.copy(this.ctr);
                            this.tetraminos.put(this.ctr, upperTetramino);
                            for (int s = 0; s < 4; s++) {
                                if (upperTetramino.blocks[s].coordinate.y >= tetramino.blocks[pos].coordinate.y) {
                                    upperTetramino.blocks[s].active = 0;
                                } else {
                                    this.getCoordinateBlock(upperTetramino.blocks[s].coordinate).tetraId = upperTetramino.blocks[s].tetraId;
                                }
                            }

                            this.ctr++;
                            Tetramino lowerTetramino = tetramino.copy(this.ctr);
                            this.tetraminos.put(this.ctr, lowerTetramino);
                            for (int s = 0; s < 4; s++) {
                                if (lowerTetramino.blocks[s].coordinate.y <= tetramino.blocks[pos].coordinate.y) {
                                    lowerTetramino.blocks[s].active = 0;
                                } else {
                                    this.getCoordinateBlock(lowerTetramino.blocks[s].coordinate).tetraId = lowerTetramino.blocks[s].tetraId;
                                }
                            }

                            this.tetraminos.remove(tetramino.blocks[pos].tetraId);
                            break;
                        }

                    }
                }
                this.adjustTheMatrix();
                this.incrementScore();
                removeLines = true;
                break;
            }
        } while (removeLines);
    }

    private void adjustTheMatrix() {
        for (int row = this.rows - 1; row >= 0; row--) {
            for (int column = 0; column < this.columns; column++) {
                Tetramino T = (this.tetraminos).get((this.board[row][column].tetraId));

                if (T != null)
                    this.shiftTillBottom(T);
            }
        }
    }

    private void shiftTillBottom(Tetramino tetramino) {
        boolean shiftTillBottom = false;
        do {
            boolean shouldShiftDown = true;

            for (int i = 0; i < 4; i++) {
                if (tetramino.blocks[i].active == 0)
                    continue;

                Coordinate newCoordinate = Coordinate.add(tetramino.blocks[i].coordinate, new Coordinate(1, 0));

                if (isTetraPresent(newCoordinate, tetramino))
                    continue;

                if (isConflicting(newCoordinate))
                    shouldShiftDown = false;
            }

            if (shouldShiftDown) {
                for (int i = 0; i < 4; i++) {
                    if (tetramino.blocks[i].active == 0)
                        continue;

                    this.getCoordinateBlock(tetramino.blocks[i].coordinate).set(-1, -1, -1, tetramino.blocks[i].coordinate, 0);


                    tetramino.blocks[i].coordinate.y++;
                }

                for (int i = 0; i < 4; i++) {
                    if (tetramino.blocks[i].active == 0)
                        continue;

                    this.getCoordinateBlock(tetramino.blocks[i].coordinate).set(tetramino.blocks[i]);

                }
                shiftTillBottom = true;
            }
        } while (shiftTillBottom);
    }

    private boolean isTetraPresent(Coordinate D, Tetramino T) {
        for (int pos = 0; pos < 4; pos++) {
            if (T.blocks[pos].active == 0)
                continue;

            if (Coordinate.isEqual(T.blocks[pos].coordinate, D))
                return true;

        }

        return false;
    }
}
