package com.hand.shockersbluff;

import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Formatter;
import java.util.Random;
import java.util.UUID;
import java.lang.Thread;

public class MainActivity extends Activity {
    Button start,connect;
    TextView playerNumber;
    TextView bluffer;
    int theShocked;
    private BluetoothAdapter BA;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private static final String TAG = "MyActivity";

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "30:14:06:19:02:32";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect=(Button)findViewById(R.id.connect);
        start=(Button)findViewById(R.id.startButton);
        playerNumber=(TextView)findViewById(R.id.playerNumber);
        bluffer=(TextView)findViewById(R.id.bluffer);

        BA = BluetoothAdapter.getDefaultAdapter();


    }
    @Override
    public void onResume() {
        super.onResume();
        connectToArduino();
    }


    public void connect(View v) {
        //This is called from the connect button
        connectToArduino();
    }

    public void start(View v) {
        letsPlay();
    }

    public void sendShock(int sleepTime, String msg){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (btSocket.isConnected()) {
            try {
                outStream.write(msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void letsPlay(){
        if (playerNumber.getText().toString().trim().length() == 0){
            Toast.makeText(MainActivity.this, "Enter the number of players.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (start.getText() != "Reveal Bluffer") {
            int players = Integer.parseInt(playerNumber.getText().toString());

            Random r = new Random();
            theShocked = r.nextInt(players) + 1;
            final String msg = Integer.toString(theShocked);

            final int sleepTime = (r.nextInt((4-1) + 1)) * 1000;

            Thread shockThread = new Thread(new Runnable() {
                public void run() {
                    sendShock(sleepTime, msg);
                }
            });
            shockThread.start();
            bluffer.setText("");

            start.setText("Reveal Bluffer");
        }
        else{
            start.setText("Again");
            Formatter f = new Formatter();
            String text = f.format("Player %d is a big fat liar!", theShocked).toString();
            bluffer.setText(text);
        }
    }

    public void connectToArduino(){
        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = BA.getRemoteDevice(address);

        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Fatal Error In onResume() and socket create failed:" + e.getMessage() + ".", Toast.LENGTH_SHORT).show();
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        BA.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        try {
            btSocket.connect();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            try {
                btSocket.close();
            } catch (IOException e2) {
                Toast.makeText(MainActivity.this, "Fatal Error In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".", Toast.LENGTH_SHORT).show();
            }
        }
        if (btSocket.isConnected()){
            Toast.makeText(MainActivity.this, "Connected!", Toast.LENGTH_SHORT).show();
        }

        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Output stream creation failed:" + e.getMessage() + ".", Toast.LENGTH_SHORT).show();
        }
    }

}

