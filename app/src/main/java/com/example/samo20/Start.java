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
    ConstraintLayout layout;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // Igangsætter baggrundsanimation
        setContentView(R.layout.activity_start);
        layout = (ConstraintLayout) findViewById(R.id.layout);
        layout.setBackgroundResource(R.drawable.animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) layout.getBackground();
        frameAnimation.start();

        handler = new Handler(Looper.getMainLooper());  // Handler til delay af åben

        bluetooth = BluetoothController.getInstance();  // Singleton invokering af Bluetooth interface
        connect();      // Lokal metode til igangsættelse af bluetooth forbindelse
    }

    public void connect()
    {
        bluetooth.connectToBluetooth();     // Iganngsætter bluetooth forbindelse gennem interface
        if(bluetooth.getStatus() == true){  // Tjekker med bluetooth interfacet, om forbindelse er oprettet
            pause();    // Hvis det er, igangsæt delay for opdatering af animation
        }
    }

    public void pause()
    {
        handler.postDelayed(new Runnable() { // Delay metode, kald til afspil af forbundet lyd og åben derefter næste brugergrænseflade/opdatering af animationen
            @Override
            public void run() {
                bluetooth.sendCommand("b-1");
                openDone();
            }
        }, 4000);
    }

    public void openDone() // Åben næste brugergrænseflade
    {
        Intent intent = new Intent(this,Done.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);     // Udfør arbejde uden nogen transition
        finish();         // Afslut denne brugergrænseflade
    }



}