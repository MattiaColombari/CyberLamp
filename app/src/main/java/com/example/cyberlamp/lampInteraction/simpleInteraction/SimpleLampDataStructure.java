package com.example.cyberlamp.lampInteraction.simpleInteraction;

import android.bluetooth.BluetoothSocket;

import com.example.cyberlamp.lampInteraction.LampDataStructure;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleLampDataStructure implements LampDataStructure {
    private BluetoothSocket mmSocket;
    private OutputStream mmOutStream;
    private boolean active;

    public SimpleLampDataStructure(BluetoothSocket mmSocket, OutputStream mmOutStream) {
        this.mmSocket = mmSocket;
        this.mmOutStream = mmOutStream;
        active = true;
    }

    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }

    public OutputStream getMmOutStream() {
        return mmOutStream;
    }

    @Override
    public void freeSpace() {
        try {
            mmSocket.close();
            mmOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            active = false;
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
