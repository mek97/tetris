package com.tetris.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.util.Random;

class DrawView extends View {
    int delay;
    int tetraminoType;
    int yOffset;
    Paint paint;
    Random randomGenerator;
    GameState game;
    Handler handler;
    Runnable loop;

    public DrawView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        delay = 500;
        randomGenerator = new Random();
        tetraminoType = randomGenerator.nextInt(7) + 1;
        game = new GameState(24, 20, tetraminoType);
        yOffset = 200;
        handler = new Handler(Looper.getMainLooper());
        loop = new Runnable() {
            public void run() {
                if (game.status && !game.pause) {
                    if (!game.shiftDown()) {
                        Random q = new Random();
                        int y = (q.nextInt(7)) + 1;
                        game.freezeUpdate(y);

                        if (game.score % 10 == 9 && delay >= 200) {
                            delay = delay / 2 + 1;
                        }
                        game.score += 1;
                    }
                    invalidate();
                    handler.postDelayed(this, delay);
                } else if (game.pause) {
                    handler.postDelayed(this, delay);
                }
            }

        };
        loop.run();
    }

    private void DrawMatrix(BasicBlock[][] matrix, Canvas canvas) {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 20; j++) {
                if (matrix[i][j].active == 0)
                    continue;

                int color = matrix[i][j].getColor(matrix[i][j].colour);
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(42 + j * 50, yOffset + i * 50 + 2, 88 + j * 50, yOffset + (i + 1) * 50 - 2, p);
            }
        }
    }

    private void Clear(BasicBlock[][] matrix, Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 20; j++) {
                canvas.drawRect(42 + j * 50, yOffset + i * 50 + 2, 88 + j * 50, yOffset + (i + 1) * 50 - 2, p);
            }
        }
    }

    private void DrawTetramino(Tetramino piece, Canvas canvas) {
        for (int j = 0; j < 4; j++) {
            int color = piece.curr[j].getColor(piece.curr[j].colour);
            Paint p = new Paint();
            p.setColor(color);
            canvas.drawRect(42 + piece.curr[j].coordinate.c * 50, yOffset + piece.curr[j].coordinate.r * 50 + 2, 88 + piece.curr[j].coordinate.c * 50, yOffset + (piece.curr[j].coordinate.r + 1) * 50 - 2, p);
        }
    }

    private void Boundary(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        canvas.drawLine(40, yOffset, 40, yOffset + 1200, paint);
        canvas.drawLine(40, yOffset, 1040, yOffset, paint);
        canvas.drawLine(1040, yOffset, 1040, yOffset + 1200, paint);
        canvas.drawLine(1040, yOffset + 1200, 40, yOffset + 1200, paint);
    }

    private void grid(Canvas canvas) {
        paint.setStrokeWidth(2f);
        for (int i = 90; i < 1040; i = i + 50) {
            canvas.drawLine(i, yOffset, i, yOffset + 1200, paint);
        }
        for (int j = 50; j < 1200; j = j + 50) {
            canvas.drawLine(40, yOffset + j, 1040, yOffset + j, paint);
        }
    }

    private void PrintScore(int score, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 100, 200, 200, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(Integer.toString(score), 80, 170, paint);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        Boundary(canvas);
        grid(canvas);
        if (game.status) {
            Clear(game.board, canvas);
            DrawMatrix(game.board, canvas);
            DrawTetramino(game.falling, canvas);
            PrintScore(game.score, canvas);
        } else {
            Paint paint = new Paint();
            DrawMatrix(game.board, canvas);
            DrawTetramino(game.falling, canvas);
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            canvas.drawText("Game Over", 60, 800, paint);
            PrintScore(game.score, canvas);
        }

    }

}
