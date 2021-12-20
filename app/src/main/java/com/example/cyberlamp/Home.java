package com.example.cyberlamp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.cyberlamp.lampInteraction.LampManager;
import com.example.cyberlamp.lampInteraction.simpleInteraction.SimpleLampCommandManager;
import com.example.cyberlamp.lampInteraction.simpleInteraction.SimpleLampExceptionManager;
import com.example.cyberlamp.lampInteraction.simpleInteraction.SimpleLampSetupManager;

/*  Website as reference:
*       - https://create.arduino.cc/projecthub/azoreanduino/simple-bluetooth-lamp-controller-using-android-and-arduino-aa2253
*       - https://developer.android.com/guide/topics/connectivity/bluetooth/transfer-data
*       - https://medium.com/swlh/create-custom-android-app-to-control-arduino-board-using-bluetooth-ff878e998aa8*/

public class Home extends AppCompatActivity {
    TextView statusTextView;
    SeekBar seekBar;
    LampManager lampManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        statusTextView = (TextView) findViewById(R.id.statusTextView);
        seekBar = (SeekBar) findViewById(R.id.seekBar_luminosite);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

                if(lampManager != null){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.valueOf(seekBar.getProgress()));
                    stringBuilder.append("-255-192-203");
                    // Intensity-Red-Green-Blue
                    lampManager.writeCommand(stringBuilder.toString());
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
            }
        });

        /*Thread thread = new Thread(new RunCommand(this));
        thread.start();*/

        startCommunicationThread();
    }

    public void startCommunicationThread(){
        lampManager = new LampManager(new SimpleLampSetupManager(this), new SimpleLampCommandManager(), new SimpleLampExceptionManager(this));
        Thread thread = new Thread(lampManager);
        thread.start();
    }

    public void updateStatus(String value) { statusTextView.setText(value); }

    public void updateText(String data){
        statusTextView.setText(data);
    }
}