package com.kanghyo.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Process m_Process;
    private Button btnRecord, btnCapture;
    boolean rooted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rooted = isRooted();
        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            public Process m_Process;


            @Override
            public void onClick(View v) {
                Process su;
                try {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat curDateFormat = new SimpleDateFormat("yyMMdd_HHmmss");

                    String strCurDate = curDateFormat.format(date);
                    Log.e("isRooted()", "[*] Start Recording...");

                    su = Runtime.getRuntime().exec("su");
                    DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                    outputStream.writeBytes("screenrecord --time-limit 10 /sdcard/MyVideo123_" + strCurDate + ".mp4\n");
                    outputStream.flush();
                    outputStream.writeBytes("exit\n");
                    outputStream.flush();
                    su.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            public Process m_Process;

            @Override
            public void onClick(View v) {
                if (rooted) {
                    try {
                        Log.e("isRooted()", "[*] Start Capture...");
                        this.m_Process = Runtime.getRuntime().exec("screencap -p /sdcard/screen.png");
                        Log.e("isRooted()", "[*] End Capture...");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public boolean isRooted() {
        boolean flag = false;
        try {
            Runtime.getRuntime().exec("su");
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}
