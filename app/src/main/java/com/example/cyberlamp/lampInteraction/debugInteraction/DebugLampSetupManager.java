package com.example.cyberlamp.lampInteraction.debugInteraction;

import com.example.cyberlamp.lampInteraction.LampDataStructure;
import com.example.cyberlamp.lampInteraction.LampSetupManager;

public class DebugLampSetupManager implements LampSetupManager {
    public DebugLampSetupManager() {
    }

    @Override
    public LampDataStructure startSetup() throws Exception {
        System.out.println("" +
                "[Debug]:\t\"startSetup\" method called on \"DebugLampSetupManager\"\n");
        return new DebugLampDataStructure();
    }
}
