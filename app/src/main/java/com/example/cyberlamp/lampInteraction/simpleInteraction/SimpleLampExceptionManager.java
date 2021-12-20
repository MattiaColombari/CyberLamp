package com.example.cyberlamp.lampInteraction.simpleInteraction;

import static java.lang.Thread.sleep;

import android.app.Activity;

import com.example.cyberlamp.Home;
import com.example.cyberlamp.lampInteraction.LampExceptionManager;

public class SimpleLampExceptionManager implements LampExceptionManager {
    Activity mainActivity;

    public SimpleLampExceptionManager(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void manageSetupException(Exception e) {
        if(mainActivity instanceof Home){
            try {
                ((Home) mainActivity).updateStatus("Error connection");
                sleep(2500);
                for(int i = 10; i >= 1; i--) {
                    ((Home) mainActivity).updateStatus("next connection in: " + i);
                    sleep(1000);
                }
                ((Home) mainActivity).startCommunicationThread();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        e.printStackTrace();

    }

    @Override
    public void manageCommandException(Exception e) {
        if(mainActivity instanceof Home){
            ((Home) mainActivity).updateStatus("Error message");
        }
        e.printStackTrace();
    }
}
