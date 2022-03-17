package com.example.cyberlamp.lampInteraction;

public class LampManager implements Runnable {
    private LampSetupManager lampSetupManager;
    private LampCommandManager lampCommandManager;
    private LampDataStructure lampDataStructure = null;
    private LampExceptionManager lampExceptionManager;

    public LampManager(LampSetupManager lampSetupManager, LampCommandManager lampCommandManager, LampExceptionManager lampExceptionManager) {
        this.lampSetupManager = lampSetupManager;
        this.lampCommandManager = lampCommandManager;
        this.lampExceptionManager = lampExceptionManager;
    }

    public void setLampSetupManager(LampSetupManager lampSetupManager) { this.lampSetupManager = lampSetupManager; }
    public void setLampCommandManager(LampCommandManager lampCommandManager) { this.lampCommandManager = lampCommandManager; }
    public void setLampExceptionManager(LampExceptionManager lampExceptionManager) { this.lampExceptionManager = lampExceptionManager; }

    @Override
    public void run() {
        this.setup();

        while(!Thread.interrupted()){}
        //yourThread.interrupt()
    }

    private void setup(){
        try {
            lampDataStructure = lampSetupManager.startSetup();
        } catch (Exception e) {
            lampExceptionManager.manageSetupException(e);
            return;
        }
    }

    public void writeCommand(String data){
        try {
            if((lampDataStructure != null) && (lampDataStructure.isActive())) {
                lampCommandManager.write(lampDataStructure, data);
            }
        } catch (Exception e) {
            lampExceptionManager.manageCommandException(e);
            if(lampDataStructure != null) { lampDataStructure.freeSpace(); }
            this.setup(); //If something goes wrong i'll get again the data of the object.
        }
    }

}
