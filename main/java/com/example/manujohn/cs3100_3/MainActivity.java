package com.example.manujohn.cs3100_3;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //left.setText("as");
        setContentView(R.layout.activity_main);
        play=(Button)findViewById(R.id.play);
        //play.setText("asd");
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,Game.class);
                startActivity(i);
            }
        });
    }

    /**
     * Created by manujohn on 05/10/17.
     */
}

