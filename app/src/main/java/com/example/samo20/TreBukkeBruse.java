package com.example.samo20;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class TreBukkeBruse extends AppCompatActivity {

    IBluetooth bluetooth;
    Handler handler;
    int time;
    boolean timedOut =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_b_b);

        bluetooth = BluetoothController.getInstance();
        handler = new Handler(Looper.getMainLooper());

        playAudioBook();
        timedOut = true;

        new CountDownTimer(time*1000, 1000) {   // Delay vedholder tilstanden timedOut = true, så længe sekvensen varer
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                timedOut = false;       // Den sættes igen til timedOut = false efter sekvenstiden
            }
        }.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();

            }
        }, 203000);

    }

    @Override
    public void onBackPressed()
    {
        if (timedOut = true)
        {
            finish();
            bluetooth.sendCommand("quit");
        }
        else
            finish();
    }

    public void playAudioBook()
    {
        timedOut = true;
        bluetooth.sendCommand("a-0");
    }
}