package com.example.cyberlamp.lampInteraction.simpleInteraction;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import com.example.cyberlamp.Home;
import com.example.cyberlamp.lampInteraction.LampDataStructure;
import com.example.cyberlamp.lampInteraction.LampSetupManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class SimpleLampSetupManager implements LampSetupManager {
    public final static String MODULE_MAC = "98:D3:C1:FD:78:D9";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Mac address of the device.

    Activity mainActivity;
    private BluetoothSocket mmSocket;
    private OutputStream mmOutStream;

    public SimpleLampSetupManager(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public LampDataStructure startSetup() throws IOException {
        if(mainActivity instanceof Home){
            ((Home) mainActivity).updateStatus("Connecting...");
        }

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Check for bluetooth and enable it if necessary.
        /*if (mainActivity != null) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        mainActivity.startActivityForResult(enableBtIntent, 5);
                    }
                }
            });
        }*/

        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(MODULE_MAC);

        mmSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);

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
            }
            finally {
                throw new IOException(connectException);
            }
        }

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            mmOutStream = mmSocket.getOutputStream();
        } catch (IOException e) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            finally {
                throw new IOException(e);
            }
        }

        if(mainActivity instanceof Home){
            ((Home) mainActivity).updateStatus("Connected");
        }

        return new SimpleLampDataStructure(mmSocket, mmOutStream);
    }
}
