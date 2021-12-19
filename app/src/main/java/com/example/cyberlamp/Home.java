package com.example.cyberlamp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/*  Website as reference:
*       - https://create.arduino.cc/projecthub/azoreanduino/simple-bluetooth-lamp-controller-using-android-and-arduino-aa2253
*       - https://developer.android.com/guide/topics/connectivity/bluetooth/transfer-data
*       - https://medium.com/swlh/create-custom-android-app-to-control-arduino-board-using-bluetooth-ff878e998aa8*/

public class Home extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Prendo l'adapter.
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        text = (TextView) findViewById(R.id.textView2);

        Thread thread = new Thread(new RunCommand(this));
        thread.start();
    }

    public void updateText(String data){
        text.setText(data);
    }
}