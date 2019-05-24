package com.demo.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;

    private int seconds = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);
        if(savedInstanceState !=null)// Если есть данные в Bandle, то загружаем их по ключу.
        {
            seconds = savedInstanceState.getInt("second"); // загружаем время
            isRunning = savedInstanceState.getBoolean("isRunning"); //загружаем состаяние таймера.
            wasRunning = savedInstanceState.getBoolean("wasRunning"); //загружаем времменное состояние.

        }
        runTimer();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        wasRunning = isRunning; //Сохраняем состояние таймера во временную переменную
//        isRunning = false;
//    }


    @Override
    protected void onStart() {
        super.onStart();
        isRunning = wasRunning;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning; //Сохраняем состояние таймера во временную переменную
        isRunning = false; //Останавливаем таймер.
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("second",seconds);
        outState.putBoolean("isRunning",isRunning);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStartTimer(View view) {
        isRunning = true;
    }

    public void onClickPauseTimer(View view) {
        isRunning = false;
    }

    public void onClickResetTimer(View view) {
        isRunning = false;
        seconds = 0;
    }

    private void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600)/60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                textViewTimer.setText(time);

                if(isRunning){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
}
