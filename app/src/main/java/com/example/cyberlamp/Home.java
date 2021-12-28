package com.example.cyberlamp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.cyberlamp.lampInteraction.LampManager;
import com.example.cyberlamp.lampInteraction.debugInteraction.DebugLampCommandManager;
import com.example.cyberlamp.lampInteraction.debugInteraction.DebugLampExceptionManager;
import com.example.cyberlamp.lampInteraction.debugInteraction.DebugLampSetupManager;
import com.example.cyberlamp.lampInteraction.simpleInteraction.SimpleLampCommandManager;
import com.example.cyberlamp.lampInteraction.simpleInteraction.SimpleLampExceptionManager;
import com.example.cyberlamp.lampInteraction.simpleInteraction.SimpleLampSetupManager;

/*  Website as reference:
*       - https://create.arduino.cc/projecthub/azoreanduino/simple-bluetooth-lamp-controller-using-android-and-arduino-aa2253
*       - https://developer.android.com/guide/topics/connectivity/bluetooth/transfer-data
*       - https://medium.com/swlh/create-custom-android-app-to-control-arduino-board-using-bluetooth-ff878e998aa8*/

public class Home extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    TextView statusTextView;
    TextView percentageRed;
    TextView percentageGreen;
    TextView percentageBlue;
    TextView percentageIntensity;

    SeekBar redSeekBar;
    SeekBar greenSeekBar;
    SeekBar blueSeekBar;
    SeekBar intensitySeekBar;

    LampManager lampManager = null;

    int redValue = 0;
    int greenValue = 0;
    int blueValue = 0;
    int intensityValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        statusTextView = (TextView) findViewById(R.id.statusTextView);
        percentageRed = (TextView) findViewById(R.id.red_percentage_TextView);
        percentageGreen = (TextView) findViewById(R.id.green_percentage_TextView);
        percentageBlue = (TextView) findViewById(R.id.blue_percentage_TextView);
        percentageIntensity = (TextView) findViewById(R.id.intensity_percentage_TextView);

        redSeekBar = (SeekBar) findViewById(R.id.seekBar_Red);
        greenSeekBar = (SeekBar) findViewById(R.id.seekBar_Green);
        blueSeekBar = (SeekBar) findViewById(R.id.seekBar_Blue);
        intensitySeekBar = (SeekBar) findViewById(R.id.seekBar_Intensity);

        redSeekBar.setMax(255);
        greenSeekBar.setMax(255);
        blueSeekBar.setMax(255);
        intensitySeekBar.setMax(255);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        intensitySeekBar.setOnSeekBarChangeListener(this);

        /*Thread thread = new Thread(new RunCommand(this));
        thread.start();*/

        startCommunicationThread();
    }

    public void startCommunicationThread(){
        //lampManager = new LampManager(new SimpleLampSetupManager(this), new SimpleLampCommandManager(), new SimpleLampExceptionManager(this));
        lampManager = new LampManager(new DebugLampSetupManager(), new DebugLampCommandManager(), new DebugLampExceptionManager());
        Thread thread = new Thread(lampManager);
        thread.start();
    }

    public void updateStatus(String value) { statusTextView.setText(value); }

    public void updateText(String data){
        statusTextView.setText(data);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId() == R.id.seekBar_Red){
            redValue = progress;
            percentageRed.setText(String.valueOf(progress));
        }
        else if(seekBar.getId() == R.id.seekBar_Green){
            greenValue = progress;
            percentageGreen.setText(String.valueOf(progress));
        }
        else if(seekBar.getId() == R.id.seekBar_Blue){
            blueValue = progress;
            percentageBlue.setText(String.valueOf(progress));
        }
        else if(seekBar.getId() == R.id.seekBar_Intensity){
            intensityValue = progress;
            percentageIntensity.setText(String.valueOf(progress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(lampManager != null){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(intensityValue);
            stringBuilder.append("-");
            stringBuilder.append(redValue);
            stringBuilder.append("-");
            stringBuilder.append(greenValue);
            stringBuilder.append("-");
            stringBuilder.append(blueValue);
            // Intensity-Red-Green-Blue

            lampManager.writeCommand(stringBuilder.toString());
        }
    }
}