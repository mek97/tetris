package com.tetris.tetris;

class Tetramino {
    BasicBlock[] curr;

    private Tetramino(Tetramino T, GameState G) {
        G.ctr = G.ctr + 1;
        curr = new BasicBlock[4];
        for (int i = 0; i < 4; i++) {
            curr[i] = T.curr[i].copy();
            curr[i].tetraId = G.ctr;
        }
        G.tetraminoMap.put(G.ctr, this);
    }

    Tetramino(int type, GameState G) {
        G.ctr = G.ctr + 1;
        curr = new BasicBlock[4];

        if (type == 1) {
            curr[0] = new BasicBlock(1, 1, G.ctr, new Coordinate(0, 10), -1);
            curr[1] = new BasicBlock(1, 1, G.ctr, new Coordinate(1, 10), -1);
            curr[2] = new BasicBlock(1, 1, G.ctr, new Coordinate(1, 11), -1);
            curr[3] = new BasicBlock(1, 1, G.ctr, new Coordinate(0, 11), -1);
        } else if (type == 2) {
            curr[0] = new BasicBlock(2, 2, G.ctr, new Coordinate(0, 10), -1);
            curr[1] = new BasicBlock(2, 2, G.ctr, new Coordinate(0, 11), -1);
            curr[2] = new BasicBlock(2, 2, G.ctr, new Coordinate(1, 10), -1);
            curr[3] = new BasicBlock(2, 2, G.ctr, new Coordinate(2, 10), -1);

        } else if (type == 3) {
            curr[0] = new BasicBlock(3, 3, G.ctr, new Coordinate(0, 11), -1);
            curr[1] = new BasicBlock(3, 3, G.ctr, new Coordinate(0, 10), -1);
            curr[2] = new BasicBlock(3, 3, G.ctr, new Coordinate(1, 11), -1);
            curr[3] = new BasicBlock(3, 3, G.ctr, new Coordinate(2, 11), -1);

        } else if (type == 4) {
            curr[0] = new BasicBlock(4, 4, G.ctr, new Coordinate(1, 10), -1);
            curr[1] = new BasicBlock(4, 4, G.ctr, new Coordinate(0, 10), -1);
            curr[2] = new BasicBlock(4, 4, G.ctr, new Coordinate(1, 11), -1);
            curr[3] = new BasicBlock(4, 4, G.ctr, new Coordinate(2, 10), -1);

        } else if (type == 5) {
            curr[0] = new BasicBlock(5, 5, G.ctr, new Coordinate(1, 11), -1);
            curr[1] = new BasicBlock(5, 5, G.ctr, new Coordinate(1, 10), -1);
            curr[2] = new BasicBlock(5, 5, G.ctr, new Coordinate(0, 10), -1);
            curr[3] = new BasicBlock(5, 5, G.ctr, new Coordinate(2, 11), -1);
        } else if (type == 6) {
            curr[0] = new BasicBlock(6, 6, G.ctr, new Coordinate(1, 11), -1);
            curr[1] = new BasicBlock(6, 6, G.ctr, new Coordinate(0, 11), -1);
            curr[2] = new BasicBlock(6, 6, G.ctr, new Coordinate(1, 10), -1);
            curr[3] = new BasicBlock(6, 6, G.ctr, new Coordinate(2, 10), -1);

        } else if (type == 7) {
            curr[0] = new BasicBlock(7, 7, G.ctr, new Coordinate(0, 10), -1);
            curr[1] = new BasicBlock(7, 7, G.ctr, new Coordinate(1, 10), -1);
            curr[2] = new BasicBlock(7, 7, G.ctr, new Coordinate(2, 10), -1);
            curr[3] = new BasicBlock(7, 7, G.ctr, new Coordinate(3, 10), -1);
        }


        G.tetraminoMap.put(G.ctr, this);

        for (int i = 0; i < 4; i++) {
            if (BasicBlock.position(G.board, curr[i].coordinate).active == -1)
                G.status = false;
        }

    }


    private boolean isConflict(Coordinate P, GameState G) {

        if (P.c < 0 || P.c >= G.c || P.r < 0 || P.r >= G.r)
            return true;

        return BasicBlock.position(G.board, P).active == -1;

    }

    boolean shiftDown(GameState G) {


        for (int i = 0; i < 4; i++) {
            if (curr[i].active == 0)
                continue;

            if (isConflict(Coordinate.add(curr[i].coordinate, new Coordinate(1, 0)), G))
                return false;
        }

        for (int i = 0; i < 4; i++)
            curr[i].coordinate.r++;

        return true;
    }

    boolean moveLeft(GameState G) {
        for (int i = 0; i < 4; i++) {
            if (curr[i].active == 0)
                continue;

            if (isConflict(Coordinate.sub(curr[i].coordinate, new Coordinate(0, 1)), G))
                return false;
        }

        for (int i = 0; i < 4; i++)
            curr[i].coordinate.c--;

        return true;
    }

    boolean moveRight(GameState G) {
        for (int i = 0; i < 4; i++) {
            if (curr[i].active == 0)
                continue;

            if (isConflict(Coordinate.add(curr[i].coordinate, new Coordinate(0, 1)), G))
                return false;
        }

        for (int i = 0; i < 4; i++)
            curr[i].coordinate.c++;


        return true;
    }

    boolean performClockWiseRotation(GameState G) {

        if (curr[0].type == 1)
            return true;
        else {
            for (int i = 0; i < 4; i++) {
                if (curr[i].active == 0)
                    continue;

                if (isConflict(Coordinate.add(Coordinate.rotateAntiClock(Coordinate.sub(curr[i].coordinate, curr[0].coordinate)), curr[0].coordinate), G))
                    return false;
            }

            for (int i = 0; i < 4; i++)
                curr[i].coordinate = Coordinate.add(Coordinate.rotateAntiClock(Coordinate.sub(curr[i].coordinate, curr[0].coordinate)), curr[0].coordinate);
            return true;

        }

    }

    void updatePosition(GameState G) {
        for (int i = 0; i < 4; i++) {
            if (curr[i].active == 0)
                continue;

            BasicBlock.position(G.board, curr[i].coordinate).set(G.falling.curr[i]);
        }
    }

    private boolean isTetraPresent(Coordinate D, Tetramino T) {
        for (int i = 0; i < 4; i++) {
            if (T.curr[i].active == 0)
                continue;

            if (Coordinate.isEqual(T.curr[i].coordinate, D))
                return true;

        }

        return false;
    }

    private void shiftTillBottom(GameState G, Tetramino T) {
        boolean left = true;
        while (left) {
            int flag = 0;
            left = false;

            for (int i = 0; i < 4; i++) {
                if (T.curr[i].active == 0)
                    continue;

                if (isTetraPresent(Coordinate.add(T.curr[i].coordinate, new Coordinate(1, 0)), T))
                    continue;

                if (isConflict(Coordinate.add(T.curr[i].coordinate, new Coordinate(1, 0)), G))
                    flag = 1;
            }

            if (flag == 0) {
                for (int i = 0; i < 4; i++) {
                    if (T.curr[i].active == 0)
                        continue;

                    BasicBlock.position(G.board, T.curr[i].coordinate).set(-1, -1, -1, T.curr[i].coordinate, 0);


                    T.curr[i].coordinate.r++;
                }

                for (int i = 0; i < 4; i++) {
                    if (T.curr[i].active == 0)
                        continue;

                    BasicBlock.position(G.board, T.curr[i].coordinate).set(T.curr[i]);

                }
                left = true;
            }
        }
    }

    private void adjustTheMatrix(GameState G) {
        for (int i = G.r - 1; i >= 0; i--) {
            for (int j = 0; j < G.c; j++) {
                Tetramino T = (G.tetraminoMap).get((G.board[i][j].tetraId));

                if (T != null)
                    shiftTillBottom(G, T);
            }
        }
    }

    void lineRemove(GameState G) {
        boolean left = true;
        while (left) {

            left = false;
            for (int i = G.r - 1; i >= 0; i--) {
                int flag = 0;
                for (int j = 0; j < G.c; j++) {
                    if (G.board[i][j].active == 0) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    left = true;
                    for (int j = 0; j < G.c; j++) {
                        G.board[i][j].active = 0;

                        Tetramino T = (G.tetraminoMap).get((G.board[i][j].tetraId));

                        if (T == null)
                            continue;

                        for (int k = 0; k < 4; k++) {
                            if (T.curr[k].active == 0) {
                                continue;
                            }

                            if (T.curr[k].coordinate.r == i && T.curr[k].coordinate.c == j) {
                                T.curr[k].active = 0;

                                Tetramino T_U = new Tetramino(T, G);
                                Tetramino T_B = new Tetramino(T, G);

                                for (int s = 0; s < 4; s++) {
                                    if (T_U.curr[s].coordinate.r >= T.curr[k].coordinate.r) {
                                        T_U.curr[s].active = 0;
                                    } else {
                                        BasicBlock.position(G.board, T_U.curr[s].coordinate).tetraId = T_U.curr[s].tetraId;
                                    }
                                }
                                for (int s = 0; s < 4; s++) {
                                    if (T_B.curr[s].coordinate.r <= T.curr[k].coordinate.r) {
                                        T_B.curr[s].active = 0;
                                    } else {
                                        BasicBlock.position(G.board, T_B.curr[s].coordinate).tetraId = T_B.curr[s].tetraId;
                                    }
                                }

                                G.tetraminoMap.remove(T.curr[k].tetraId);
                                break;
                            }

                        }
                    }
                    adjustTheMatrix(G);
                    G.score++;
                    break;
                }
            }
        }
    }
}
