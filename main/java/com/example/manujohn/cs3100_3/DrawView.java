package com.example.manujohn.cs3100_3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by manujohn on 04/10/17.
 */

class DrawView extends View {
    Paint paint = new Paint();
    int size=50;
    int c=500;
    int x_offset=0;
    int level;
    Random r= new Random();
    int x= (r.nextInt(7))+1;
    GameState game=new GameState(24,20,x);
    int y_offset=200;

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.BLUE);
        loop.run();
    }

    private void DrawMatrix(BasicBlock[][] matrix,Canvas canvas)
    {
        for(int i=0;i<24;i++)
        {
            for (int j=0;j<20;j++)
            {
                if(matrix[i][j].active==0)
                    continue;

                int color=matrix[i][j].getColor(matrix[i][j].colour);
                Paint p=new Paint();
                p.setColor(color);
                canvas.drawRect(42+j*50,y_offset+i*50+2,88+j*50,y_offset+(i+1)*50-2,p);
            }
        }
    }
    private void Clear(BasicBlock[][] matrix,Canvas canvas)
    {
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        for(int i=0;i<24;i++)
        {
            for (int j=0;j<20;j++)
            {
                canvas.drawRect(42+j*50,y_offset+i*50+2,88+j*50,y_offset+(i+1)*50-2,p);
            }
        }
    }
    private void DrawTetramino(tetramino piece,Canvas canvas)
    {
        for (int j=0;j<4;j++) {
            int color = piece.curr[j].getColor(piece.curr[j].colour);
            Paint p = new Paint();
            p.setColor(color);
            canvas.drawRect(42 +  piece.curr[j].C.c* 50, y_offset + piece.curr[j].C.r * 50 + 2, 88 + piece.curr[j].C.c * 50, y_offset + (piece.curr[j].C.r + 1) * 50 - 2, p);
        }
    }
    private void Boundary(Canvas canvas)
    {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        canvas.drawLine(40,y_offset+0,40,y_offset+1200,paint);
        canvas.drawLine(40,y_offset+0,1040,y_offset+0,paint);
        canvas.drawLine(1040,y_offset+0,1040,y_offset+1200,paint);
        canvas.drawLine(1040,y_offset+1200,40,y_offset+1200,paint);
    }
    private void grid(Canvas canvas)
    {
        paint.setStrokeWidth(2f);
        for(int i=90;i<1040;i=i+50)
        {
            canvas.drawLine(i,y_offset+0,i,y_offset+1200,paint);
        }
        for(int j=50;j<1200;j=j+50)
        {
            canvas.drawLine(40,y_offset+j,1040,y_offset+j,paint);
        }
    }
    private void PrintScore(int score,Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0,100,200,200,paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(Integer.toString(score),80,170, paint);

    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw(canvas,s,paint);
        //canvas.drawRect(600,600,3  00,300,paint);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
//        canvas.drawLine(0,200,0,1600,paint);
//        canvas.drawLine(0,200,1080,200,paint);
//        canvas.drawLine(1080,200,1080,1600,paint);
//        canvas.drawLine(1080,1600,0,1600,paint);
        Boundary(canvas);
        grid(canvas);
        //Draw(canvas,s,paint);
        //Initilize();
        //b[0][0].color=1;
        //Overwrite();
        if(game.status)
        {
            Clear(game.board,canvas);
            DrawMatrix(game.board,canvas);
            DrawTetramino(game.falling,canvas);
            PrintScore(game.score,canvas);
        }
        else
        {
            Paint paint = new Paint();
//            paint.setColor(Color.WHITE);
//            paint.setStyle(Paint.Style.FILL);
//            canvas.drawPaint(paint);
            DrawMatrix(game.board,canvas);
            DrawTetramino(game.falling,canvas);
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            canvas.drawText("Game Over",60,800, paint);
            PrintScore(game.score,canvas);
        }

   }
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable loop = new Runnable(){
        public void run(){
            if(game.status && !game.pause)
            {
                if(!game.shiftDown()) {
                    Random q = new Random();
                    int y = (q.nextInt(7)) + 1;
                    game.freeze_update(y);

                    if(game.score%10==9 && level==1)
                    {
                        level=0;
                        c=c-50;
                    }
                    else if(game.score%10!=9)
                    {
                        level=1;
                    }
                }
                invalidate();
                handler.postDelayed(this,c);
            }
            else if(game.pause)
            {
                handler.postDelayed(this,c);
            }
        }

    };

}
