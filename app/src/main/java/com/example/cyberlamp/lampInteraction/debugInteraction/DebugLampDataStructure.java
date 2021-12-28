package com.example.cyberlamp.lampInteraction.debugInteraction;

import com.example.cyberlamp.lampInteraction.LampDataStructure;

public class DebugLampDataStructure implements LampDataStructure {
    public DebugLampDataStructure() {
        System.out.println("" +
                "[Debug]:\t\"DebugLampDataStructure\"'s instance has been created\n");
    }

    @Override
    public void freeSpace() {
        System.out.println("" +
                "[Debug]:\t\"freeSpace\" method called on \"DebugLampDataStructure\"\n");
    }

    @Override
    public boolean isActive() {
        System.out.println("" +
                "[Debug]:\t\"isActive\" method called on \"DebugLampDataStructure\"\n");
        return true;
    }
}
