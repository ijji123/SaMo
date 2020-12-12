package com.example.samo20;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TreBukkeBruse extends AppCompatActivity {

    IBluetooth bluetooth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_b_b);

        bluetooth = BluetoothController.getInstance();
        playAudioBook();
    }
    //@Override
    //public void onDestroy() {
    //    super.onDestroy();
    //    bluetooth.sendCommand("quit");
    //    int filler;
    //}

    @Override
    public void onBackPressed()
    {
        finish();
        bluetooth.sendCommand("quit");
    }

    public void playAudioBook()
    {
        bluetooth.sendCommand("a-0");
    }
}