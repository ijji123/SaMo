package com.example.samo20;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class Done extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        handler = new Handler(Looper.getMainLooper());
        pause(); // Automatisk start
    }

    public void pause() // Udskyd åbning af hovedmenu i 3 sekunder. Tilføjet således at animationen er bevaret i den ønskede periode
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openMainMenu();

            }
        }, 3000);
    }

    public void openMainMenu() // Åben næste brugergrænseflade
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0); // Udfør arbejde uden nogen transition
        finish();              // Afslut denne brugergrænseflade

    }

}