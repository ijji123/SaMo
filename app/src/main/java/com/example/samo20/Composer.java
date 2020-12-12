package com.example.samo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class Composer extends AppCompatActivity {

    Button heart, moon, rain, ladybug, sun, rainbow, flower, star ,gift, play;
    ImageView sequenceField1, sequenceField2, sequenceField3, sequenceField4, sequenceField5, sequenceField6, sequenceField7, sequenceField8;
    TextView dropzone;
    private String sequence = "s";
    String preview;
    String message;
    boolean timedOut = false;
    int[] myImageList;
    int current;
    int time;

    int counter = 0;
    IBluetooth bluetooth;
    Handler handler;

    public Composer() {
    }

    protected Composer(IBluetooth bluetooth) {
        this.bluetooth = bluetooth;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer);

        bluetooth = BluetoothController.getInstance();
        handler = new Handler(Looper.getMainLooper());

        // Lokal inititiering af alle vores views ("typecasting")
        // Fungerer også som eventgenerator
        heart = (Button) findViewById(R.id.heart);
        heart.setTag(0);
        gift = (Button) findViewById(R.id.gift);
        gift.setTag(1);
        moon = (Button) findViewById(R.id.moon);
        moon.setTag(2);
        rain = (Button) findViewById(R.id.rain);
        rain.setTag(3);
        ladybug = (Button) findViewById(R.id.bug);
        ladybug.setTag(4);
        sun = (Button) findViewById(R.id.sun);
        sun.setTag(5);
        rainbow = (Button) findViewById(R.id.rainbow);
        rainbow.setTag(6);
        flower = (Button) findViewById(R.id.flower);
        flower.setTag(7);
        star = (Button) findViewById(R.id.star);
        star.setTag(8);
        play = (Button) findViewById(R.id.play);

        dropzone = (TextView) findViewById(R.id.dropzone);

        sequenceField1 = (ImageView) findViewById(R.id.sequenceField1);
        sequenceField2 = (ImageView) findViewById(R.id.sequenceField2);
        sequenceField3 = (ImageView) findViewById(R.id.sequenceField3);
        sequenceField4 = (ImageView) findViewById(R.id.sequenceField4);
        sequenceField5 = (ImageView) findViewById(R.id.sequenceField5);
        sequenceField6 = (ImageView) findViewById(R.id.sequenceField6);
        sequenceField7 = (ImageView) findViewById(R.id.sequenceField7);
        sequenceField8 = (ImageView) findViewById(R.id.sequenceField8);

        myImageList = new int[]{R.drawable.hjerte, R.drawable.gave,R.drawable.maane,R.drawable.regn,R.drawable.marie,R.drawable.sol,R.drawable.regnbue,R.drawable.blomst,R.drawable.stjerne};
        play.setEnabled(false);
        // Forbinder eventet til listeneren. Fortæller, at det her er de clickable objekter
        // Her indgår ikke de objekter, hvor der skal ske et andet event til / en anden listener, som play og sequenceView
        // heart.setOnLongClickListener(longClickListener);
        // boat.setOnLongClickListener(longClickListener);

        heart.setOnLongClickListener(longClickListener);
        heart.setOnClickListener(previewClickListener);
        gift.setOnLongClickListener(longClickListener);
        gift.setOnClickListener(previewClickListener);
        moon.setOnLongClickListener(longClickListener);
        moon.setOnClickListener(previewClickListener);
        rain.setOnLongClickListener(longClickListener);
        rain.setOnClickListener(previewClickListener);
        ladybug.setOnLongClickListener(longClickListener);
        ladybug.setOnClickListener(previewClickListener);
        sun.setOnLongClickListener(longClickListener);
        sun.setOnClickListener(previewClickListener);
        rainbow.setOnLongClickListener(longClickListener);
        rain.setOnClickListener(previewClickListener);
        flower.setOnLongClickListener(longClickListener);
        flower.setOnClickListener(previewClickListener);
        star.setOnLongClickListener(longClickListener);
        star.setOnClickListener(previewClickListener);

        dropzone.setOnDragListener(dragListener);

        play.setOnClickListener(playClickListener);
    }

    // Listener til event hvor ting bliver holdt inde.
    // "Eventlistener"
    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            current = (int) v.getTag();
            v.startDrag(data,myShadowBuilder,null,0 );
            v.invalidate();

            return true;
        }
    };

    View.OnClickListener playClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            time = (counter * 4) + 1;
            timedOut = true;
            message = getSequence();
            new CountDownTimer(time*1000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    timedOut = false;
                }
            }.start();


            sendCommand(message);

            prepareIcons();
            resetSequence();

        }
    };

    View.OnClickListener previewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current = (int) v.getTag();
            preview = String.valueOf(current);
            sendCommand(preview);
        }
    };

    View.OnClickListener closeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendCommand("stop");
            Composer.this.finish();
        }
    };

    public void sendCommand(String command)
    {
        bluetooth.sendCommand(command);
    }

    public void addToSequence(int tag){
        sequence = sequence + "-" + tag;
    }

    public String getSequence(){
        return sequence;
    }

    public void resetSequence()
    {
        sequence = "s";
        counter = 0;
    }

    public void disableIcons(){
        heart.setEnabled(false);
        gift.setEnabled(false);
        moon.setEnabled(false);
        rain.setEnabled(false);
        ladybug.setEnabled(false);
        sun.setEnabled(false);
        rainbow.setEnabled(false);
        flower.setEnabled(false);
        star.setEnabled(false);
    }

    public void prepareIcons(){
        sequenceField1.setImageResource(R.drawable.hvid);
        sequenceField2.setImageResource(R.drawable.hvid);
        sequenceField3.setImageResource(R.drawable.hvid);
        sequenceField4.setImageResource(R.drawable.hvid);
        sequenceField5.setImageResource(R.drawable.hvid);
        sequenceField6.setImageResource(R.drawable.hvid);
        sequenceField7.setImageResource(R.drawable.hvid);
        sequenceField8.setImageResource(R.drawable.hvid);

        heart.setEnabled(true);
        gift.setEnabled(true);
        moon.setEnabled(true);
        rain.setEnabled(true);
        ladybug.setEnabled(true);
        sun.setEnabled(true);
        rainbow.setEnabled(true);
        flower.setEnabled(true);
        star.setEnabled(true);

        play.setEnabled(false);
    }

    public void updateSequenceField(int current)
    {
        counter++;

        if (counter == 1) {
            sequenceField1.setImageResource(myImageList[current]);
            addToSequence(current);
            play.setEnabled(true);
        }
        if (counter == 2) {
            sequenceField2.setImageResource(myImageList[current]);
            addToSequence(current);
        }
        if (counter == 3) {
            sequenceField3.setImageResource(myImageList[current]);
            addToSequence(current);
        }
        if (counter == 4) {
            sequenceField4.setImageResource(myImageList[current]);
            addToSequence(current);
        }
        if (counter == 5) {
            sequenceField5.setImageResource(myImageList[current]);
            addToSequence(current);
        }
        if (counter == 6) {
            sequenceField6.setImageResource(myImageList[current]);
            addToSequence(current);
        }
        if (counter == 7) {
            sequenceField7.setImageResource(myImageList[current]);
            addToSequence(current);
        }
        if (counter == 8) {
            sequenceField8.setImageResource(myImageList[current]);
            addToSequence(current);
            disableIcons();
        }
    }


    // Drag listener til events hvor textviews bliver trykket
    View.OnDragListener dragListener = new View.OnDragListener()
    {
        @Override
        public boolean onDrag(View v, DragEvent event){
            int dragEvent = event.getAction();

            switch(dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED: // Kaldes når vores views kommer ind på vores targetfelt
                    // Starter med at check hvilket view, der er blevet flyttet henover target
                    break;
                case DragEvent.ACTION_DRAG_EXITED: // Når viewet slippes andre steder, end over vores targetfelt
                    break;
                case DragEvent.ACTION_DROP: // Når viewet slippes henover vores targetfelt
                updateSequenceField(current);
            }
            return true;
        }
    };

    @Override
    public void onBackPressed()
    {
        if (timedOut == true){

        }
        else
            finish();
    }

}