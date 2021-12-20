package com.example.cyberlamp;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

public class RunCommand implements Runnable{
    private static final String TAG = "Console";
    Home home;

    private BluetoothSocket mmSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;


    public RunCommand(Home home) {
        this.home = home;
    }

    public final static String MODULE_MAC = "98:D3:C1:FD:78:D9";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    @Override
    public void run() {
        writeOut("Go");
        //Prendo l'adapter.
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Check for bluetooth and enable it if necessary.
        if (home != null) {
            Activity mainActivity = home;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        mainActivity.startActivityForResult(enableBtIntent, 1);
                    }
                }
            });
        }

        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(MODULE_MAC);

        writeOut("Got Device");

        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
            return;
        }
        mmSocket = tmp;

        // Cancel discovery because it otherwise slows down the connection.
        bluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        writeOut("Device Connected");

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = mmSocket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
            return;
        }
        try {
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
            return;
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;

        writeOut("Got Stream");

        /* Initialize client-sever "two way" connection. */
        /* Server -> Client, "out". */
        PrintWriter out = new PrintWriter(mmOutStream, true);
        /* Client -> Server, "in". */
        BufferedReader in = new BufferedReader(new InputStreamReader(mmInStream));

        try {
            String sendText;

            while(true) {
                sendText = "10";
                mmOutStream.write(sendText.getBytes());
                sleep(1000);
                Log.e(TAG, "Sent 10");
                writeOut("Sent 10");

                sendText = "0";
                mmOutStream.write(sendText.getBytes());
                sleep(1000);
                Log.e(TAG, "Sent 0");
                writeOut("Sent 0");
            }
        } catch (InterruptedException | IOException /*| IOException*/ e) {
            e.printStackTrace();
        }

        cancel();
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }

    public void writeOut(String data){
        if (home != null) {
            Activity mainActivity = home;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    home.updateText(data);
                }
            });
        }
    }
}
