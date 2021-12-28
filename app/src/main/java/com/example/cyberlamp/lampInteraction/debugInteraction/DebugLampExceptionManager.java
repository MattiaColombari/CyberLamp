package com.example.cyberlamp.lampInteraction.debugInteraction;

import com.example.cyberlamp.lampInteraction.LampExceptionManager;

public class DebugLampExceptionManager implements LampExceptionManager {
    public DebugLampExceptionManager() {
    }

    @Override
    public void manageCommandException(Exception e) {
        System.out.println("" +
                "[Debug]:\t\"manageCommandException\" method called on \"DebugLampExceptionManager\"\n" +
                "\t\t\tData passed: \"" + e.toString() + "\"");
    }

    @Override
    public void manageSetupException(Exception e) {
        System.out.println("" +
                "[Debug]:\t\"manageSetupException\" method called on \"DebugLampExceptionManager\"\n" +
                "\t\t\tData passed: \"" + e.toString() + "\"");
    }
}
