package org.firstinspires.ftc.teamcode.Universal.BluetoothTelemetryLogger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

//Singleton object so only one connection happens at a time
public class BluetoothTelemetryLogger {
    private static final int REQUEST_ENABLE_BT = 1;
    private final boolean ENABLED = true;
    private final UUID APP_UUID = UUID.fromString("B62C4E8D-62CC-404b-BBBF-BF3E3BBB1374");
    private final String SERVER_BLUETOOTH_MAC_ADDRESS = "";                                     //TODO: Set mac address
    private final long SLEEP_LENGTH = 200;

    private volatile static BluetoothTelemetryLogger instance = null;
    private final static Object mutex = new Object();

    public static BluetoothTelemetryLogger getInstance() {
        BluetoothTelemetryLogger result = instance;

        if(result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new BluetoothTelemetryLogger();
            }
        }

        return result;
    }


    private BluetoothAdapter m_BluetoothAdapter;
    private final BluetoothSocket mmSocket;
    private ConcurrentLinkedQueue<DataPoint> toSend = new ConcurrentLinkedQueue<>();

    Runnable loggerRunnable = new Runnable() {
        public void run() {
            m_BluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            OutputStream sout;


            try {
                sout = mmSocket.getOutputStream();

                while(true) {

                    DataPoint dataPoint = toSend.poll();
                    while(dataPoint != null) {
                        String s = dataPoint.toCSV() + "\n";
                        byte[] bytes = s.getBytes("UTF8");
                        sout.write(bytes);

                        dataPoint = toSend.poll();
                    }

                    try {
                        Thread.sleep(SLEEP_LENGTH);
                    } catch (InterruptedException e) { }
                }
            }catch(IOException e) {}
        }
    };

    Thread loggerThread = new Thread(loggerRunnable);

    private BluetoothTelemetryLogger() {
        final BluetoothDevice mmDevice;

        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!m_BluetoothAdapter.isEnabled()) {
            m_BluetoothAdapter.enable();
        }


        mmDevice = m_BluetoothAdapter.getRemoteDevice(SERVER_BLUETOOTH_MAC_ADDRESS);

        BluetoothSocket tmp = null;

        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(APP_UUID);
        } catch (IOException e) {

        }
        mmSocket = tmp;

        loggerThread.run();
    }


    public void log(DataPoint dataPoint) {
        toSend.offer(dataPoint);
    }

}
