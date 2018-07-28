package com.example.manujohn.cs3100_3;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity implements View.OnClickListener {

    DrawView drawView;
    RelativeLayout GameButtons;
    Button left;
    Button right;
    Button down;
    FrameLayout game;
    Button pause;
    TextView score;
    Button speed;
    boolean flag=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);

        game = new FrameLayout(this);
        GameButtons = new RelativeLayout(this);

        left = new Button(this);
        left.setText("Left");
        left.setId(111);

        right = new Button(this);
        right.setText("Right");
        right.setId(222);

        down = new Button(this);
        down.setText("Rotate");
        down.setId(333);

        pause = new Button(this);
        pause.setText("Pause");
        pause.setId(444);

        score=new TextView(this);
        score.setText("Score");
        score.setId(555);
        score.setTextSize(30);

        speed=new Button(this);
        speed.setText("Fast");
        speed.setId(666);

        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams leftButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams rightButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams downButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams pausebutton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams scoretext = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams speedbutton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        GameButtons.setLayoutParams(rl);
        GameButtons.addView(left);
        GameButtons.addView(right);
        GameButtons.addView(down);
        GameButtons.addView(pause);
        GameButtons.addView(score);
        GameButtons.addView(speed);

        leftButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        rightButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        downButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        downButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        pausebutton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        pausebutton.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);

        scoretext.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        scoretext.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);

        speedbutton.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        speedbutton.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);

        left.setLayoutParams(leftButton);
        right.setLayoutParams(rightButton);
        down.setLayoutParams(downButton);
        pause.setLayoutParams(pausebutton);
        score.setLayoutParams(scoretext);
        speed.setLayoutParams(speedbutton);

        game.addView(drawView);
        game.addView(GameButtons);
        setContentView(game);

        View leftButtonListener = findViewById(111);
        leftButtonListener.setOnClickListener(this);

        View rightButtonListener = findViewById(222);
        rightButtonListener.setOnClickListener(this);

        View downButtonListener = findViewById(333);
        downButtonListener.setOnClickListener(this);

        View pauseButtonListener = findViewById(444);
        pauseButtonListener.setOnClickListener(this);

        View speedButtonListener = findViewById(666);
        speedButtonListener.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == left) {
            drawView.game.userPressedLeft();
        } else if (v == right) {
            drawView.game.userPressedRight();
        } else if (v == down) {
            drawView.game.userPressedRotate();
        }
        else if(v==pause)
        {
            if(drawView.game.status) {
                if (drawView.game.pause) {
                    drawView.game.pause = false;
                    pause.setText("Pause");
                } else {
                    //drawView.c = drawView.c * 2;
                    pause.setText("Play");
                    drawView.game.pause = true;
                }
            }
            else
            {
                pause.setText("Start New Game");
                Intent i= new Intent(Game.this,MainActivity.class);
                startActivity(i);
            }
        }

        else if(v==speed)
        {
            if(flag==true) {
                drawView.c = drawView.c / 2;
                flag=false;
                speed.setText("Slow");
            }
            else
            {
                drawView.c = drawView.c * 2;
                speed.setText("fast");
                flag=true;
            }
        }


    }
}
