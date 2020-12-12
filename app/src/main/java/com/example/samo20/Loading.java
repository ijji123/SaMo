package com.example.samo20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

public class Loading extends AppCompatActivity {

    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    Button test;
    Button mainmenu;

    OutputStream mmOutputStream;

    public void sendBtMsg(String msg2send){
        //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {

            String msg = msg2send;
            //msg += "\n";
            mmOutputStream = mmSocket.getOutputStream();
            mmOutputStream.write(msg.getBytes());

            mmOutputStream.close();
            mmSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Handler handler = new Handler();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mmOutputStream = new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        };

        test = (Button) findViewById(R.id.test);
        mainmenu = (Button) findViewById(R.id.mainmenu);

        final class workerThread implements  Runnable{
            private String btMsg;
            UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

            public workerThread(String msg){
                btMsg = msg;
            }

            public void sendBtMsg(String command)
            {
                (new Thread(new workerThread(command))).start();
            }

            public void run()
            {
                try {
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.

                    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                    mmSocket.connect();
                } catch (IOException connectException) {
                    // Unable to connect; close the socket and return.
                    try {
                        mmSocket.close();
                    } catch (IOException closeException) {

                    }
                    return;
                }

               sendBtMsg(btMsg);
            }
        };

        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                (new Thread(new workerThread("1"))).start();

            }
        });

        mainmenu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDoneScreen();
            }
        });


        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth,0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if (device.getName().equals("raspberrypi")) // Skal rettes til vores navn
                {
                    Log.e("Forbundet Pi", device.getName());
                    mmDevice = device;
                    // openDoneScreen();
                    break;
                }
            }
        }
    }

    public void openDoneScreen()
    {
        Intent intent = new Intent(this,Done.class);
        startActivity(intent);
    }

}