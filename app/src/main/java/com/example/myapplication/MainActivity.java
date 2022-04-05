package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    Socket client = null;
    String recv = "koko wawa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
        TextView t = findViewById(R.id.text1);
        TextView a = findViewById(R.id.text2);
        TextView b = findViewById(R.id.text3);
        TextView c = findViewById(R.id.text4);
        Button d = findViewById(R.id.sendb);
        EditText e = findViewById(R.id.message);
        t.setText("Connection Started");
        v.setEnabled(false);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    client = new Socket( "192.168.1.19" ,6789 );
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        t1.start();

    }


    public void send (View v) {

        EditText e = findViewById(R.id.message);
        String msg = e.getText().toString();
        System.out.println(msg);

       Thread t = new Thread ( new Runnable() {
           public void run() {

                   try {
                       System.out.println(msg);
                       PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                       BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                       out.println(msg);
                       out.flush();
                       recv = in.readLine();
                       System.out.println("i am here");
                       TextView b = findViewById(R.id.text3);
                       b.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      b.setText(recv);
                                  }
                              });

                       System.out.println(recv);

                       if ("CLOSE".equals(msg)) {
                           client.close();
                           v.post(new Runnable() {
                               @Override
                               public void run() {
                                   v.setEnabled(false);
                               }
                           });
                           TextView tx = findViewById(R.id.text1);
                           tx.post(new Runnable() {
                               @Override
                               public void run() {
                                   tx.setText("Connection Terminated!");
                               }
                           });
                           out.close();
                           in.close();
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
       });
       t.start();


    }



}
