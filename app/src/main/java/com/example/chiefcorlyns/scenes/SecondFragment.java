package com.example.chiefcorlyns.scenes;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SecondFragment extends Fragment {

    View view;
    TextView powerTextView;
    Vibrator vibrator;
    MediaPlayer dangerWarning;





    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_second, container, false);
        view = inflater.inflate(R.layout.fragment_second, container, false);


        WebView wv = (WebView) view.findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();

        // myWebView.setWebViewClient(new webView());
        webSettings.setJavaScriptEnabled(true);
        wv.loadUrl("http://www.onedirectionmusic.com/ie/home/");

        Button Foward = (Button) view.findViewById(R.id.btForward);
        Button Reverse = (Button) view.findViewById(R.id.btReverse);
        Button Right = (Button) view.findViewById(R.id.btRight);
        Button Left = (Button) view.findViewById(R.id.btLeft);
        Button Stop = (Button) view.findViewById(R.id.btStop);

        powerTextView = (TextView) view.findViewById(R.id.powerTextView);
        dangerWarning = MediaPlayer.create(getContext(), R.raw.warning);
        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //utrasonicSensor();


        Foward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendForward(v);
                    utrasonicSensor();




                } catch (IOException ex) {
                }
            }
        });

        Reverse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    sendReverse(v);

                } catch (IOException ex) {
                }
            }
        });

        Right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendRight(v);
                    utrasonicSensor();

                } catch (IOException ex) {
                }
            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendLeft(v);
                    utrasonicSensor();

                } catch (IOException ex) {
                }
            }
        });


        Stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendStop(v);

                } catch (IOException ex) {
                }
            }
        });

    return view;
    }


    void sendForward(View view) throws IOException {
        //String msg = myTextbox.getText().toString();
        switch (view.getId()) {
            case R.id.btForward:

                String msg = setForward();
                msg += "\n";
                PairedDevices.mmOutputStream.write(msg.getBytes());
                //myLabel.setText("Data Sent");
                break;
        }
    }

    void sendReverse(View view)throws IOException {
        switch (view.getId()) {
            case R.id.btReverse:
                String msg = setReverse();
                msg += "\n";
                PairedDevices.mmOutputStream.write(msg.getBytes());
                //  myLabel.setText("Data Sent");

                break;
        }
    }

    void sendLeft(View view)throws IOException  {
        switch (view.getId()) {
            case R.id.btLeft:
                String msg = setLeft();
                msg += "\n";
                PairedDevices.mmOutputStream.write(msg.getBytes());
                // myLabel.setText("left turn");

                break;
        }

    }

    void sendRight(View view)throws IOException {

        switch(view.getId()){
            case R.id.btRight:
                String msg = setRight();
                msg += "\n";
                PairedDevices.mmOutputStream.write(msg.getBytes());
                //  myLabel.setText("Data Sent");

                break;

        }
    }

    void sendStop(View view)throws IOException {

        switch(view.getId()){
            case R.id.btStop:
                String msg = setStop();
                msg += "\n";
                PairedDevices.mmOutputStream.write(msg.getBytes());
                //  myLabel.setText("Data Sent");

                break;

        }
    }

    void utrasonicSensor() {


        String m = null;
        try {
            m = String.valueOf(PairedDevices.mmInputStream.read());

        } catch (IOException e) {
            e.printStackTrace();
        }
        powerTextView.setText(" Ultrasonic "+m);
        int range = Integer.parseInt(m);

               if (range<40){

                   dangerWarning.start();
                   //long [] pattern ={250, 500};
                   vibrator.vibrate(1000);
               }


    }






    String setForward(){
        return "f";
    }

    String setRight(){
        return "r";
    }

    String setLeft(){
        return "l";
    }

    String setReverse(){
        return "b";
    }
    String setStop(){
        return "g";
    }

}





