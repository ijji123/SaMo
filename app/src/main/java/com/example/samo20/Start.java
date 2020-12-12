package com.example.samo20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Start extends AppCompatActivity {

    IBluetooth bluetooth;
    Button test;
    ConstraintLayout layout;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_start);

        layout = (ConstraintLayout) findViewById(R.id.layout);
        layout.setBackgroundResource(R.drawable.animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) layout.getBackground();
        frameAnimation.start();
        handler = new Handler(Looper.getMainLooper());

        bluetooth = BluetoothController.getInstance();
        connect();
    }

    public void openDone()
    {
        Intent intent = new Intent(this,Done.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }



    public void connect()
    {
        bluetooth.connectToBluetooth();
        if(bluetooth.getStatus() == true){
            pause();
        }
    }

    public void pause()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetooth.sendCommand("b-1");
                openDone();
            }
        }, 4000);
    }

}