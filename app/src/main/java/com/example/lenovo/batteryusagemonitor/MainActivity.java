package com.example.lenovo.batteryusagemonitor;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuilder sb = new StringBuilder();

        TextView t = (TextView)findViewById(R.id.textView);

        t.setMovementMethod(new ScrollingMovementMethod());

        t.setText("Hi\n");

        File sdCard = Environment.getExternalStorageDirectory();
        File test = new File(sdCard, "test.txt");

        Log.d("LOG!!!",test.getAbsolutePath());



        Process process1 = null;
        Process process2 = null;

        /*try {
            process1 = Runtime.getRuntime().exec("pm grant \"com.example.lenovo.batteryusagemonitor\" android.permission.DUMP");
            *//*BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));*//*

            *//*while (bufferedReader.readLine() != null) {
                //sb.append(bufferedReader.readLine());
                Log.d("LOG",bufferedReader.readLine());
            }*//*
            //t.append(bufferedReader.readLine());

        } catch (IOException e) {
            sb.append("ERROR1");
            e.printStackTrace();
        }*/

        StringBuilder sb2 = new StringBuilder();
        sb2.append("dumpsys batterystats");
        //sb2.append(" > " + test.getAbsolutePath());

        Log.d("LOG!!!",sb2.toString());

        FileWriter fr = null;
        BufferedWriter bw = null;

        try {
            fr = new FileWriter(test);
            bw = new BufferedWriter(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            process2 = Runtime.getRuntime().exec(sb2.toString());
            process2.waitFor();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process2.getInputStream()));

            while (bufferedReader.readLine() != null) {
                //sb.append(bufferedReader.readLine());
                String line = bufferedReader.readLine();
                sb.append(line);
                bw.write(line);
            }


        } catch (IOException e) {
            sb.append("ERROR2");
            e.printStackTrace();
        } catch (InterruptedException e) {
            sb.append("ERROR3");
            e.printStackTrace();
        }


        t.append(sb);

        t.append("\nENDS!!\n\n");

    }

    private static String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }


}
