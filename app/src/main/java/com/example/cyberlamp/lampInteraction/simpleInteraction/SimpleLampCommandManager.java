package com.example.cyberlamp.lampInteraction.simpleInteraction;

import static java.lang.Thread.sleep;

import com.example.cyberlamp.lampInteraction.LampCommandManager;
import com.example.cyberlamp.lampInteraction.LampDataStructure;

import java.io.OutputStream;

public class SimpleLampCommandManager implements LampCommandManager {

    public SimpleLampCommandManager() {
    }

    @Override
    public void write(LampDataStructure lampDataStructure, String data) throws Exception {
        if(!(lampDataStructure instanceof SimpleLampDataStructure)){
            throw new IllegalArgumentException("lampDataStructure is not istance of SimpleLampDataStructure");
        }
        SimpleLampDataStructure simpleLampDataStructure = (SimpleLampDataStructure) lampDataStructure;
        OutputStream mmOutStream = simpleLampDataStructure.getMmOutStream();
        if(mmOutStream != null) {
            mmOutStream.write(data.getBytes());
            try {
                sleep(10);
            } catch(InterruptedException e) {}
        }
    }
}
