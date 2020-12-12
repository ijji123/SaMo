package com.example.samo20;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothController implements IBluetooth {
    public static BluetoothController singleBluetooth = null;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;
    private boolean connected = false;

    public static BluetoothController getInstance(){
        if(singleBluetooth == null){
            singleBluetooth = new BluetoothController();
        }
        return singleBluetooth;
    }

    public void send(String msg2send){
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
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

    public void sendCommand(String command)
    {
        (new Thread(new workerThread(command))).start();
    }


    public boolean getStatus(){
        return connected;
    }

    public void connectToBluetooth() {
        final Handler handler = new Handler();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mmOutputStream = new OutputStream() {
            @Override
            public void write(int i) throws IOException {
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
                if (device.getAddress().equals("B8:27:EB:8D:47:68")) // fundet via consol på raspberry pi, "bluetoothctl", og derefter "show"
                {
                    mmDevice = device;
                    connected = true;
                    break;
                }
            }
        }
    }

    final class workerThread implements  Runnable{
        private String btMsg;
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

        public workerThread(String msg){
            btMsg = msg;
        }

        public void run()
        {
            try {
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

            send(btMsg);
        }

    };

}
