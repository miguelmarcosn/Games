package com.example.a2048;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a2048.*;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);




        Button game2048 = findViewById(R.id.game2048);
        Button gameLightsOut = findViewById(R.id.LightsOut);
        Button ranking = findViewById(R.id.Ranking);
        game2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity2048.class);
                startActivityForResult(intent, 0);
            }
        });
        gameLightsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivityLightsOut.class);
                startActivityForResult(intent, 0);
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScoreActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}