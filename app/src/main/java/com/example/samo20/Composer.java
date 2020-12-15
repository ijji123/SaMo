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
    int[] myImageList;              // Liste over ikoner anvendt til buttons, bruges til at udfylde afpsillingsfeltet

    private String sequence = "s";   // String til afsendelse af musiksekvens
    String preview;                  // String værdi for Tag for den button, som brugeren har trykket på
    int current;                     // Int værdi for tag for den button, som brugeren har trykket på
    int counter = 0;                 // Tæller til musiksekvensen

    boolean timedOut = false;        // Boolean værdi til angivelse af, hvorvidt returknappen er blokeret eller ej. Afhængig af, om der afspilles en musiksekvens
    int time;                        // Int værdi for hvor lang tid returknappen skal blokeres i


    IBluetooth bluetooth;            // Bluetooth interface
    Handler handler;                 // Handler til timer delay, til blokering af returknappen

    public Composer() {              // Tom constructor
    }

    protected Composer(IBluetooth bluetooth) {  // Constructor med interface injection, anvendes under unit tests
        this.bluetooth = bluetooth;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer);

        bluetooth = BluetoothController.getInstance();       // Singleton invokering af Bluetooth interface
        handler = new Handler(Looper.getMainLooper());       // Oprettelse af delay handler til blokering af returknap

        heart = (Button) findViewById(R.id.heart);           // Lokal initiering af alle buttons samt angivelse af deres numeriske tag
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

        dropzone = (TextView) findViewById(R.id.dropzone);      // Lokal initiering af dropzonen

        sequenceField1 = (ImageView) findViewById(R.id.sequenceField1);     // Lokal initiering af det sammensatte afspillingsfelt
        sequenceField2 = (ImageView) findViewById(R.id.sequenceField2);
        sequenceField3 = (ImageView) findViewById(R.id.sequenceField3);
        sequenceField4 = (ImageView) findViewById(R.id.sequenceField4);
        sequenceField5 = (ImageView) findViewById(R.id.sequenceField5);
        sequenceField6 = (ImageView) findViewById(R.id.sequenceField6);
        sequenceField7 = (ImageView) findViewById(R.id.sequenceField7);
        sequenceField8 = (ImageView) findViewById(R.id.sequenceField8);

        // Alle ikon-illustrationer gemmes i ImageList, således at deres placering korrelerer med det numeriske tag givet til button, med det korrelerende ikon
        myImageList = new int[]{R.drawable.hjerte, R.drawable.gave,R.drawable.maane,R.drawable.regn,R.drawable.marie,R.drawable.sol,R.drawable.regnbue,R.drawable.blomst,R.drawable.stjerne};

        play.setEnabled(false);     // Play knap initieres som disabled, eftersom afspillingsfeltet initieres tomt

        heart.setOnLongClickListener(longClickListener);        // Der knyttes en longClick listener til alle buttons, og en onClickListener
        heart.setOnClickListener(previewClickListener);         // LongClick anvendes til drag-and-drop, onClick til afspilling af preview
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

        dropzone.setOnDragListener(dragListener);               // Der sættes en draglistener på dropzonen, sålede at den kan registrere at noget er sluppet henover det

        play.setOnClickListener(playClickListener);             // Der sættes en specifik onClick listener til playknappen, som skal igangsætte en anden process end previewClick listener
    }


    // Long click listener, sat på Buttons
    // Opretter "skyggen", som følger brugerens finger
    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);

            current = (int) v.getTag();     // Tag-værdien for den givne button gemmes lokalt

            v.startDrag(data,myShadowBuilder,null,0 );
            v.invalidate();

            return true;
        }
    };

    // OnClickListener, som specifikt anvendes til afspillingsknappen
    View.OnClickListener playClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            sendCommand(getSequence());     // Kalder lokal metode send command. Værdien der sendes med er sekvensen

            time = (counter * 4) + 1;       // Estimerer tiden, musiksekvensen varer. Antal af lydsekvenser, deres længde (4 sek) plus 1 sekunds buffer
            timedOut = true;                // Returknappen skal blokeres

            new CountDownTimer(time*1000, 1000) {   // Delay vedholder tilstanden timedOut = true, så længe sekvensen varer
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    timedOut = false;       // Den sættes igen til timedOut = false efter sekvenstiden
                    prepareIcons();         // Kalder lokal metode for at nulstille afspillingsfeltet
                    resetSequence();        // Kalder lokal metode for at nulstille sekvensen
                }
            }.start();
        }
    };

    // onClick listener sat på buttons med ikoner
    // Opstillet med det formål at afspille et enkel lydstykke
    View.OnClickListener previewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current = (int) v.getTag();         // Tag-værdien for den specifikke knap, der trykkes, gemmes lokalt
            preview = String.valueOf(current);  // Int-værdien konverteres til en string
            sendCommand(preview);               // Kalder lokal metode send command
        }
    };


    // Drag listener til events når knapper bliver trukket, og sluppet henover afspillingsfeltet
    View.OnDragListener dragListener = new View.OnDragListener()
    {
        @Override
        public boolean onDrag(View v, DragEvent event){
            int dragEvent = event.getAction();

            switch(dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:         // Kaldes når vores views kommer ind på vores targetfelt
                    break;
                case DragEvent.ACTION_DRAG_EXITED:          // Når viewet slippes andre steder, end over vores targetfelt
                    break;
                case DragEvent.ACTION_DROP:                 // Når viewet slippes henover vores targetfelt
                    updateSequenceField(current);           // Lokal metode kaldes til opdatering af brugergrænsefladen. Current-værdien udfyldes under longClick
            }
            return true;
        }
    };

    public void updateSequenceField(int current) // Metode for behandling af nyt drop-event i
    {
        counter++;          // Tælleren stiger med ém
                            // Afhængig af tælleren opdateres et forskelligt subfelt i afspillingsfeltet

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
        if (counter == 8) {         // Når tælleren er 8 blokeres alle interfaces, alle felter er udfyldt
            sequenceField8.setImageResource(myImageList[current]);
            addToSequence(current);
            disableIcons();
        }
    }

    public void sendCommand(String command)
    {
        bluetooth.sendCommand(command);         // Sender en given stringværdi til BluetoothController klassen, via Bluetooth interface
    }

    public void addToSequence(int tag){
        sequence = sequence + "-" + tag;        // Tilføjer den givne værdi til den eksisterende string. Der tilføjes en bindestreg mellem værdierne
    }

    public String getSequence(){
        return sequence;                        // Returnerer sekvensen
    }

    public void resetSequence()                 // Nulstiller sekvensen
    {
        sequence = "s";
        counter = 0;
    }

    public void disableIcons(){                 // Blokerer alle buttons
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

    public void prepareIcons(){                 // Nulstiller interface
        sequenceField1.setImageResource(R.drawable.hvid);   // Hele afspillingsfelt gøres hvidt
        sequenceField2.setImageResource(R.drawable.hvid);
        sequenceField3.setImageResource(R.drawable.hvid);
        sequenceField4.setImageResource(R.drawable.hvid);
        sequenceField5.setImageResource(R.drawable.hvid);
        sequenceField6.setImageResource(R.drawable.hvid);
        sequenceField7.setImageResource(R.drawable.hvid);
        sequenceField8.setImageResource(R.drawable.hvid);

        heart.setEnabled(true);     // Alle buttons gøres aktive
        gift.setEnabled(true);
        moon.setEnabled(true);
        rain.setEnabled(true);
        ladybug.setEnabled(true);
        sun.setEnabled(true);
        rainbow.setEnabled(true);
        flower.setEnabled(true);
        star.setEnabled(true);

        play.setEnabled(false);     // Play knappen gøres inaktiv, eftersom afspillingsfeltet er tomt
    }



    @Override
    public void onBackPressed()     // Overriden metode til at definere hvordan retur-tasten skal opføre sig
    {
        if (timedOut == true){      // Check hvorvidt der afspilles en lydfil

        }
        else                        // Hvis ikke, kan brugergrænsefladen afsluttes
            finish();
    }

}