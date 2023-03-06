package com.example.a2048;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivityLightsOut extends AppCompatActivity {
    ImageButton[][] buttons;
    ControllerLightsOut controller;
    TextView textTimer;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lights_out);
        db = new DataBase(this);
        textTimer = (TextView) findViewById(R.id.timer);
        timer = new Timer();
        buttons = new ImageButton[5][5];
        buttons[0][0] = (ImageButton)findViewById(R.id.imgBttn00);
        buttons[0][1] = (ImageButton)findViewById(R.id.imgBttn01);
        buttons[0][2] = (ImageButton)findViewById(R.id.imgBttn02);
        buttons[0][3] = (ImageButton)findViewById(R.id.imgBttn03);
        buttons[0][4] = (ImageButton)findViewById(R.id.imgBttn04);
        buttons[1][0] = (ImageButton)findViewById(R.id.imgBttn10);
        buttons[1][1] = (ImageButton)findViewById(R.id.imgBttn11);
        buttons[1][2] = (ImageButton)findViewById(R.id.imgBttn12);
        buttons[1][3] = (ImageButton)findViewById(R.id.imgBttn13);
        buttons[1][4] = (ImageButton)findViewById(R.id.imgBttn14);
        buttons[2][0] = (ImageButton)findViewById(R.id.imgBttn20);
        buttons[2][1] = (ImageButton)findViewById(R.id.imgBttn21);
        buttons[2][2] = (ImageButton)findViewById(R.id.imgBttn22);
        buttons[2][3] = (ImageButton)findViewById(R.id.imgBttn23);
        buttons[2][4] = (ImageButton)findViewById(R.id.imgBttn24);
        buttons[3][0] = (ImageButton)findViewById(R.id.imgBttn30);
        buttons[3][1] = (ImageButton)findViewById(R.id.imgBttn31);
        buttons[3][2] = (ImageButton)findViewById(R.id.imgBttn32);
        buttons[3][3] = (ImageButton)findViewById(R.id.imgBttn33);
        buttons[3][4] = (ImageButton)findViewById(R.id.imgBttn34);
        buttons[4][0] = (ImageButton)findViewById(R.id.imgBttn40);
        buttons[4][1] = (ImageButton)findViewById(R.id.imgBttn41);
        buttons[4][2] = (ImageButton)findViewById(R.id.imgBttn42);
        buttons[4][3] = (ImageButton)findViewById(R.id.imgBttn43);
        buttons[4][4] = (ImageButton)findViewById(R.id.imgBttn44);
        controller = new ControllerLightsOut(buttons);
        startTimer();
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imgBttn00:
                controller.click(0,0);
                break;
            case R.id.imgBttn01:
                controller.click(0,1);
                break;
            case R.id.imgBttn02:
                controller.click(0,2);
                break;
            case R.id.imgBttn03:
                controller.click(0,3);
                break;
            case R.id.imgBttn04:
                controller.click(0,4);
                break;
            case R.id.imgBttn10:
                controller.click(1,0);
                break;
            case R.id.imgBttn11:
                controller.click(1,1);
                break;
            case R.id.imgBttn12:
                controller.click(1,2);
                break;
            case R.id.imgBttn13:
                controller.click(1,3);
                break;
            case R.id.imgBttn14:
                controller.click(1,4);
                break;
            case R.id.imgBttn20:
                controller.click(2,0);
                break;
            case R.id.imgBttn21:
                controller.click(2,1);
                break;
            case R.id.imgBttn22:
                controller.click(2,2);
                break;
            case R.id.imgBttn23:
                controller.click(2,3);
                break;
            case R.id.imgBttn24:
                controller.click(2,4);
                break;
            case R.id.imgBttn30:
                controller.click(3,0);
                break;
            case R.id.imgBttn31:
                controller.click(3,1);
                break;
            case R.id.imgBttn32:
                controller.click(3,2);
                break;
            case R.id.imgBttn33:
                controller.click(3,3);
                break;
            case R.id.imgBttn34:
                controller.click(3,4);
                break;
            case R.id.imgBttn40:
                controller.click(4,0);
                break;
            case R.id.imgBttn41:
                controller.click(4,1);
                break;
            case R.id.imgBttn42:
                controller.click(4,2);
                break;
            case R.id.imgBttn43:
                controller.click(4,3);
                break;
            case R.id.imgBttn44:
                controller.click(4,4);
                break;
            case R.id.newButton:
                controller = new ControllerLightsOut(buttons);
                while (controller.win()){
                    controller = new ControllerLightsOut(buttons);
                }
                resetTimer();
                startTimer();
                break;
            case R.id.retryButton:
                controller.retryBoard();
                resetTimer();
                startTimer();
                break;
            case R.id.helpButton:
                controller.help();
                break;
        }
        if (controller.win()) {
            timerTask.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Congratulations! You win!");
            builder.setMessage("Introduce your name to save your score");

// Crea un EditText para que el usuario ingrese su nombre
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Agrega un botón para confirmar el ingreso de nombre
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = input.getText().toString();

                    // Creamos un objeto ContentValues y le agregamos los valores que queremos insertar
                    ContentValues values = new ContentValues();
                    values.put(DataBase.COLUMN_NAME_LIGHTS_OUT, name);
                    values.put(DataBase.COLUMN_SCORE_LIGHTS_OUT, time);

                    db.insert("ScoreLightsOut", values);
                    finish();

                }
            });

// Agrega un botón para cancelar el ingreso de nombre


            builder.show();

        }
        controller.updateView();
    }

    private void startTimer() {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        textTimer.setText(getTimerText());
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private void resetTimer() {
        if(timerTask != null) {
            timerTask.cancel();
            time = 0.0;
            textTimer.setText(formatTime(0,0,0));
        }
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }

}