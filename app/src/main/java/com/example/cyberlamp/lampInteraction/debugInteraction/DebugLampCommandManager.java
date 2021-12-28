package com.example.cyberlamp.lampInteraction.debugInteraction;

import com.example.cyberlamp.lampInteraction.LampCommandManager;
import com.example.cyberlamp.lampInteraction.LampDataStructure;

public class DebugLampCommandManager implements LampCommandManager {
    public DebugLampCommandManager() {
    }

    @Override
    public void write(LampDataStructure lampDataStructure, String data) throws Exception {
        System.out.println("" +
                "[Debug]:\t\"write\" method called on \"DebugLampCommandManager\"\n" +
                "\t\t\tData passed: \"" + data + "\"");
    }
}
