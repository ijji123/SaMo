package com.example.samo20;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
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

    public static BluetoothController getInstance(){    // Singleton metode til oprettelse af instansen
        if(singleBluetooth == null){                    // Opretter kun en ny, hvis der ikke allerede findes en
            singleBluetooth = new BluetoothController();
        }
        return singleBluetooth;                         // Returnerer den eksisterende instans, bruges til at oprette den i øvrige klasser
    }

    public void connectToBluetooth() {  // Første metodekald ved forbindelse af bluetooth (efter kald "connect" i Loading)
        final Handler handler = new Handler();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mmOutputStream = new OutputStream() {
            @Override
            public void write(int i) throws IOException {
            }
        };

        if(!mBluetoothAdapter.isEnabled()) // Tjekker hvorvidt telefonens bluetooth er igangsat. Hvis ikke, udfør dette arbejde
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // startActivityForResult(enableBluetooth,0);   // Ville her udskrive en melding til skærm om, at Bluetooth ikke er aktiveret. Er ikke lykkedes implementeret
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices(); // Gemmer lokalt ned alle de parrede eneheder, telefonen har

        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices) // Finder den rette bluetooth enhed, blandt dem telefonen i forvejen er forbundet til
            {
                if (device.getAddress().equals("B8:27:EB:8D:47:68")) // fundet via consol på raspberry pi, "bluetoothctl", og derefter "show"
                {
                    mmDevice = device;  // Gemmer dene enhed som vores device
                    connected = true;   // Opdaterer tilstand
                    break;
                }
            }
        }
    }

    public boolean getStatus(){                 // Returnerer status. Kaldes kun som del af forbindelsesprocessen
        return connected;
    }

    public void sendCommand(String command)     // Første metodekald, opretter en ny tråd som får metodeparameteren med
    {
        (new Thread(new workerThread(command))).start();
    }


    final class workerThread implements  Runnable{
        private String btMsg;
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

        public workerThread(String msg){        // Opretter en ny tråd
            btMsg = msg;
        }

        public void run()                       // Trådens arbejdsmetode, den som starter når der kaldes "start".
        {
            try {
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);    // Opret ny bluetooth forbindelse til enhed med den givne uuid
                mmSocket.connect();                                             // Forbind til den
            } catch (IOException connectException) {                            // Hvis der kastes en exception....
                // Unable to connect; close the socket and return.
                try {                                                           //.. prøv at luk Socket først, i tilfælde af at der lå en åben
                    mmSocket.close();
                } catch (IOException closeException) {

                }
                return;
            }

            send(btMsg);       // Igangsæt udskrift af besked
        }

    };

    public void send(String msg2send){
        try {

            String msg = msg2send;                              // Gemmer beskeden lokalt
            //msg += "\n";
            mmOutputStream = mmSocket.getOutputStream();        // Opretter et output stream
            mmOutputStream.write(msg.getBytes());               // Skriver i denne, beskeden sendes som bytes.

            mmOutputStream.close();                             // Luk stream
            mmSocket.close();                                   // Luk socket

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
