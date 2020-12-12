package com.example.samo20;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import dagger.hilt.android.HiltAndroidApp;

public class ThreadController {
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    String msg2send;

    OutputStream mmOutputStream;

    public void sendCommand(String command)
    {
        (new Thread(new workerThread(command))).start();
    }
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

    final class workerThread implements  Runnable{
        private String btMsg;
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

        public workerThread(String msg){
            btMsg = msg;
        }

        public void runThread(String command)
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

    protected void onCreate() {
        final Handler handler = new Handler();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mmOutputStream = new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        };

        final class workerThread implements  Runnable{
            private String btMsg;
            UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

            public workerThread(String msg){
                btMsg = msg;
            }

            public void runThread(String command)
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


        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // startActivityForResult(enableBluetooth,0);
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

    public static interface IController {
        void sendCommand(String msg2send);
        void connectToBluetooth();
    }
}
