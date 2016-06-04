package com.example.chiefcorlyns.scenes;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static java.lang.String.valueOf;

public class JoystickFragment extends Fragment  {
    private TextView angleTextView;
    private TextView powerTextView;
    public TextView directionTextView;
    TextView movement;
    View view;
    String speed;
    String power2;
    String upServo;
    String downServo;
    float x, y, z = 0;

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    // Importing also other views
    private com.example.chiefcorlyns.scenes.JoystickView joystick;
    private SensorManager mSensorManager;
    public Sensor mAccelerometer;

    public JoystickFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_joystick, container, false);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //get the accelerometer sensor
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        angleTextView = (TextView) view.findViewById(R.id.angleTextView);
        powerTextView = (TextView) view.findViewById(R.id.tvRange1);
        directionTextView = (TextView) view.findViewById(R.id.directionTextView);
         movement = (TextView) view.findViewById(R.id.moveLabel);
        //Referencing also other views
        joystick = (com.example.chiefcorlyns.scenes.JoystickView) view.findViewById(R.id.joystickView);

        View nonVideoLayout = view.findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) view.findViewById(R.id.videoLayout); // Your own view, read class comments noinspection all
        View loadingView = getActivity().getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webView = (VideoEnabledWebView) view.findViewById(R.id.webView);// Save the web view

        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };

        webView.setWebChromeClient(webChromeClient);
        // Call private class InsideWebViewClient
        webView.setWebViewClient(new InsideWebViewClient());

        // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
        webView.loadUrl(FirstFragment.ipAddress);

        //Event listener that always returns the variation of the angle in degrees, motion power in percentage and direction of movement
        joystick.setOnJoystickMoveListener(new com.example.chiefcorlyns.scenes.JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub




                angleTextView.setText(" " + valueOf(angle) + "°");
                powerTextView.setText(" " + valueOf(power) + "%");
                directionTextView.setText("" + valueOf(direction));


                if(power >-1 && power<11){
                    power2="!"+"L" +"W";

                }
                else if(power >10  && power <21){
                    power2="#"+"L" +"W";

                }
                else if(power>20  && power <31){
                    power2="-"+"L" +"W";

                }
                else if(power>30  && power <41){
                    power2="+"+"L" +"W";

                }
                else if(power>40  && power <51){
                    power2="?"+"L" +"W";

                }
                else if(power>50  && power <61){
                    power2="|"+"L" +"W";

                }
                else if(power>60  && power <71){
                    power2="&"+"L" +"W";

                }
                else if(power>70  && power <81){
                    power2="@"+"L" +"W";

                }
                else if(power>80  && power <91){
                    power2=")"+"L" +"W";

                }
                else if(power>90  && power <101){
                    power2="("+"L" +"W";

                }

              if(angle > -1 && angle < 16 ){

                    speed = "a"+"L" +"W";
                }
                else if(angle>15 && angle <31){
                  speed ="b"+"L" +"W";
              }
                else if(angle>30 && angle <46){
                  speed = "c"+"L" +"W";
              }
                else if(angle >45 && angle < 61){
                  speed ="d"+"L" +"W";
              }
              else if(angle >60 && angle <76){
                  speed ="e"+"L" +"W";
              }
              else if(angle >75 && angle < 91){
                  speed ="f"+"L" +"W";
              }
              else if(angle >90 && angle < 106){
                  speed ="g"+"L" +"W";
              }
              else if(angle >105 && angle < 121){
                  speed ="h"+"L" +"W";
              }
              else if(angle >120 && angle < 136){
                  speed ="i"+"L" +"W";
              }
              else if(angle >135 && angle < 151){
                  speed ="j"+"L" +"W";
              }
              else if(angle >150 && angle < 166){
                  speed ="k"+"L" +"W";
              }
              else if(angle >165 && angle < 181){
                  speed ="l"+"L" +"W";
              }
                if(angle <0 && angle> -16 ){

                    speed = "m"+"L" +"W";
                }
                else if(angle< -15 && angle> -31){
                    speed ="n"+"L" +"W";
                }
                else if(angle< -30 && angle> -46){
                    speed = "o"+"L" +"W";
                }
                else if(angle< -45 && angle> -61){
                    speed ="p"+"L" +"W";
                }
                else if(angle< -60 && angle> -76){
                    speed ="q"+"L" +"W";
                }
                else if(angle< -75 && angle> -91){
                    speed ="r"+"L" +"W";
                }
                else if(angle< -90 && angle> -106){
                    speed ="s"+"L" +"W";
                }
                else if(angle< -105 && angle> -121){
                    speed ="t"+"L" +"W";
                }
                else if(angle< -120 && angle> -136){
                    speed ="u"+"L" +"W";
                }
                else if(angle< -135 && angle> -151){
                    speed ="v"+"L" +"W";
                }
                else if(angle< -150 && angle> -166){
                    speed ="w"+"L" +"W";
                }
                else if(angle< -165 && angle> -181){
                    speed ="x"+"L" +"W";
                }

                if (FirstFragment.btSocket != null) {
                    try {
                       //int move = Integer.parseInt();


                        FirstFragment.mmOutputStream.write((power2 ).getBytes());
                        FirstFragment.mmOutputStream.write((speed ).getBytes());

                       // FirstFragment.mmOutputStream.write(("t"+angle).getBytes());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
                }
            }
        }, com.example.chiefcorlyns.scenes.JoystickView.DEFAULT_LOOP_INTERVAL);
return view;

    }

    @Override
    public void onResume() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // set the activity back to //whatever it needs to be when going back.
        super.onPause();
    }









    void ultrasonicSensor() {


        String m = null;
        if(FirstFragment.btSocket!=null) {
            try {
                m = String.valueOf(FirstFragment.mmInputStream.read());

            } catch (IOException e) {
                e.printStackTrace();
            }

            movement.setText(" Ultrasonic " + m);
            int range = Integer.parseInt(m);



        }else
        {
            Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

    }





    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    }



