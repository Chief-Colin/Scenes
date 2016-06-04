package com.example.chiefcorlyns.scenes;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import lecho.lib.hellocharts.model.PointValue;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ButtonFragment extends Fragment {

    View view;
    TextView leftRange;
    Vibrator vibrator;
    MediaPlayer dangerWarning;

    TextView displayTimer;

    static ArrayList<PointValue> entries;
    static ArrayList<PointValue> entries1;
    Button Forward, Reverse, Right, Left, Stop, SaveRoute;
    double finalTime;
    long endTime;
    double gradient = 1;
    double distance = 0;
    int speed = 10;
    double lastX = 0;
    double lastY = 0;
    double lastX2 = 0;
    double lastY2 = 0;
    double angle = 90;
    double angleTurned = 0;
    static double maxY = 0;
    static double maxX = 0;
    static double minY = 0;
    static double minX = 0;
    boolean isPressed = false;
    static double secAngle = 0;



    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    long startTime;

    public ButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity a = getActivity();
            if(a!=null)a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        view = inflater.inflate(R.layout.fragment_button, container, false);

        View nonVideoLayout = view.findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) view.findViewById(R.id.videoLayout); // Your own view, read class comments noinspection all
        View loadingView = getActivity().getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webView = (VideoEnabledWebView) view.findViewById(R.id.webView);// Save the web view


        //Button btnOn = (Button) view.findViewById(R.id.btnOn);
        Forward = (Button) view.findViewById(R.id.btForward);
        Right = (Button) view.findViewById(R.id.btRight);
        Left = (Button) view.findViewById(R.id.btLeft);
        Stop = (Button) view.findViewById(R.id.btStop);
        SaveRoute = (Button) view.findViewById(R.id.btSaveRoute);
        Reverse = (Button) view.findViewById(R.id.btReverse);
        entries = new ArrayList<>();

        displayTimer = (TextView)view.findViewById(R.id.tvTimer);



        leftRange = (TextView) view.findViewById(R.id.tvRange1);
        dangerWarning = MediaPlayer.create(getContext(), R.raw.warning);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);



//ultrasonicSensor();



                Forward.findViewById(R.id.btForward).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            startTime = System.currentTimeMillis();
                            try {
                                sendForward(v);
                               // ultrasonicSensor();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // start your timer
                            start();

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            endTime = System.currentTimeMillis();
                            finalTime = (endTime - startTime) / 1000;
                            distance = (finalTime * speed);

                            if (isEmpty()) {
                                entries.add(new PointValue(1, (float) (distance / 10)));
                                lastX = 1;
                                lastY = distance / 10;
                                setMinMax(distance / 10, 1);
                                Log.d("I am here", "inside the if loop");

                            } else {
                                Log.d("in", "first if");
                                double newX1 = 0;
                                double newX2 = 0;
                                double newY1 = 0;
                                double newY2 = 0;

                                newX1 = lastX + Math.sqrt(((distance / 10) * (distance / 10)) / (1 + (gradient * gradient)));
                                newX2 = lastX - Math.sqrt(((distance / 10) * (distance / 10)) / (1 + (gradient * gradient)));

                                newY1 = (gradient * (newX1 - lastX)) + lastY;
                                newY2 = (gradient * (newX2 - lastX)) + lastY;

                                if (angle < 90) {
                                    Log.d("in", "90");
                                    if (secAngle > 0 && secAngle < 90){
                                        if (newY1 > lastY && newX1 > lastX) {
                                            Log.d("in", "else1");
                                            entries.add(new PointValue((float) newX1, (float) newY1));
                                            shiftLast(newX1, newY1);
                                            setMinMax(newX1, newY1);
                                        } else {
                                            Log.d("in", "else2");
                                            entries.add(new PointValue((float) newX2, (float) newY2));
                                            shiftLast(newX2, newY2);
                                            setMinMax(newX2, newY2);
                                        }
                                    }
                                    else {
                                        if (newY1 < lastY && newX1 < lastX){
                                            entries.add(new PointValue((float) newX1, (float) newY1));
                                            shiftLast(newX1, newY1);
                                            setMinMax(newX1, newY1);
                                        }
                                        else {
                                            entries.add(new PointValue((float) newX2, (float) newY2));
                                            shiftLast(newX2, newY2);
                                            setMinMax(newX2, newY2);
                                        }
                                    }

                                }

                                else if (angle > 90 && angle <= 180) {
                                    Log.d("in", "angle 180");
                                    if (secAngle > 90 && secAngle < 180){
                                        if (newY1 < lastY && newX1 > lastX){
                                            entries.add(new PointValue((float) newX1, (float) newY1));
                                            shiftLast(newX1, newY1);
                                            setMinMax(newX1, newY1);
                                        }
                                        else {
                                            entries.add(new PointValue((float) newX2, (float) newY2));
                                            shiftLast(newX2, newY2);
                                            setMinMax(newX2, newY2);
                                        }
                                    }
                                    else {
                                        if (newY1 > lastY && newX1 < lastX){
                                            entries.add(new PointValue((float) newX1, (float) newY1));
                                            shiftLast(newX1, newY1);
                                            setMinMax(newX1, newY1);
                                        }
                                        else {
                                            entries.add(new PointValue((float) newX2, (float) newY2));
                                            shiftLast(newX2, newY2);
                                            setMinMax(newX2, newY2);
                                        }
                            // stop your timer.
                            stop();

                        }

                    }
                            }
                            try {
                                sendStop(v);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }

                });

        Reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                    try {
                        sendReverse(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // start your timer
                    start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime) / 1000;
                    distance = (finalTime * speed);

                    if (isEmpty()) {
                        entries.add(new PointValue(1, (float) (-1 * (distance / 10))));
                        shiftLast(1, (-1 * (distance / 10)));
                        setMinMax(1, (-1 * (distance / 10)));
                    } else {
                        double newX1 = 0;
                        double newX2 = 0;
                        double newY1 = 0;
                        double newY2 = 0;

                        newX1 = lastX + Math.sqrt((distance / 10 * distance / 10) / (1 + (gradient * gradient)));
                        newX2 = lastX - Math.sqrt((distance / 10 * distance / 10) / (1 + (gradient * gradient)));

                        newY1 = (gradient * (newX1 - lastX)) + lastY;
                        newY2 = (gradient * (newX2 - lastX)) + lastY;

                        if (angle < 90) {
                            Log.d("in", "90");
                            if (secAngle > 0 && secAngle < 90) {
                                if (newY1 < lastY && newX1 < lastX) {
                                    Log.d("in", "else1");
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                } else {
                                    Log.d("in", "else2");
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            } else {
                                if (newY1 > lastY && newX1 > lastX) {
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                } else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }

                        } else if (angle > 90 && angle <= 180) {
                            Log.d("in", "angle 180");
                            if (secAngle > 90 && secAngle < 180) {
                                if (newY1 > lastY && newX1 < lastX) {
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                } else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            } else {
                                if (newY1 < lastY && newX1 > lastX) {
                                    entries.add(new PointValue((float) newX1, (float) newY1));
                                    shiftLast(newX1, newY1);
                                    setMinMax(newX1, newY1);
                                } else {
                                    entries.add(new PointValue((float) newX2, (float) newY2));
                                    shiftLast(newX2, newY2);
                                    setMinMax(newX2, newY2);
                                }
                            }
                            // stop your timer.
                            stop();

                        }
                    }
                    try {
                        sendStop(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

        Right.findViewById(R.id.btRight).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                    try {
                        sendRight(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // start your timer
                    start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime);
                    finalTime = 3 % (finalTime / 1000);
                    angleTurned = (360 / 3) * finalTime;

                    if (secAngle + angleTurned < 360){
                        secAngle += angleTurned;
                    }
                    else if ((secAngle + angleTurned) > 360){
                        secAngle = (secAngle + angleTurned) - 360;
                    }
                    if (angle - angleTurned < 0) {
                        angleTurned -= angle;
                        angle = 180;
                    }
                    angle -= angleTurned;
                    double radAngle = Math.toRadians(angle);

                    gradient = Math.tan(radAngle);

                    try {
                        sendStop(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("start", "time " + startTime);
                    Log.d("end", "time " + endTime);
                    Log.d("final", "time " + finalTime);
                    Log.d("angle ", "turned " + angleTurned);
                    Log.d("angle", "angle is " + angle);
                    Log.d("gradient", "gradient " + gradient);
                    Log.d("inside", "right loop");
                    Log.d("this is", "int here " + finalTime);
                    // stop your timer.
                    stop();

                }
                return false;
            }
        });

        Left.findViewById(R.id.btLeft).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                    try {
                        sendLeft(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // start your timer
                    start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endTime = System.currentTimeMillis();
                    finalTime = (endTime - startTime);
                    finalTime = 3 % (finalTime / 1000);
                    angleTurned = (360 / 3) * finalTime;

                    if (secAngle - angleTurned < 0){
                        secAngle = (angleTurned - secAngle);
                    }
                    else if (secAngle - angleTurned > 0){
                        secAngle -= angleTurned;
                    }

                    if (angle + angleTurned > 180){
                        angleTurned += angle - 180;
                        angle = angleTurned;
                    }
                    else {
                        angle += angleTurned;
                    }

                    double radAngle = Math.toRadians(angle);
                    gradient = Math.tan(radAngle);

                    try {
                        sendStop(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("start", "time " + startTime);
                    Log.d("end", "time " + endTime);
                    Log.d("final", "time " + finalTime);
                    Log.d("angle ", "turned " + angleTurned);
                    Log.d("angle", "angle is " + angle);
                    Log.d("gradient", "gradient " + gradient);
                    Log.d("inside", "right loop");
                    Log.d("this is", "int here " + finalTime);
                }
                    // stop your timer.
                    stop();

                return false;
            }
        });

        Stop.findViewById(R.id.btStop).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    try {
                        sendStop(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // start your timer
                    start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // stop your timer.
                    stop();
                }
                return false;
            }
        });

        SaveRoute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int listSize = entries.size();

                for (int i = 0; i<listSize; i++){
                    Log.i("Data: ", entries.get(i).toString());
                }
                if (isPressed == false){
                    SaveRoute.setBackgroundColor(Color.GREEN);
                    SaveRoute.setText("Stop Save Route");
                    isPressed = true;
                }
                else if (isPressed == true){
                    SaveRoute.setBackgroundColor(Color.BLACK);
                    SaveRoute.setText("Start Save Route");
                    isPressed = false;

                    try {
                        saveRoute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        // Initialize the VideoEnabledWebChromeClient and set event handlers


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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getContext(), "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getContext(), "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    void saveRoute()throws IOException{
        Intent i = new Intent(getContext(), SaveRouteGraphView.class);
        startActivity(i);

    }

    public long start() {
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;

        displayTimer.setText(String.format("%d:%02d", seconds, millis));
        return millis;
}


public long stop() {
    long millis1 = System.currentTimeMillis() - startTime;
    int seconds1 = (int) (millis1 / 1000);
    int minutes = seconds1 / 60;
    seconds1 = seconds1 % 60;
    long end = start()-millis1;

   return end;
}
    void sendForward(View view) throws IOException {
        //String msg = myTextbox.getText().toString();
        switch (view.getId()) {
            case R.id.btForward:

                if(FirstFragment.btSocket!=null) {
                    String msg = setForward();
                    msg += "\n";
                    FirstFragment.mmOutputStream.write(msg.getBytes());

                    //myLabel.setText("Data Sent");
                }else
            {
                Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
            }
                break;
        }
    }

    void sendReverse(View view)throws IOException {
        switch (view.getId()) {
            case R.id.btReverse:
                if(FirstFragment.btSocket!=null) {
                    String msg = setReverse();
                    msg += "\n";
                    FirstFragment.mmOutputStream.write(msg.getBytes());
                    //  myLabel.setText("Data Sent");
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    void sendLeft(View view)throws IOException  {
        switch (view.getId()) {
            case R.id.btLeft:

                if (FirstFragment.btSocket != null){
                    String msg = setLeft();
                msg += "\n";
                FirstFragment.mmOutputStream.write(msg.getBytes());
                    // myLabel.setText("left turn");
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

                break;
        }

    }

    void sendRight(View view)throws IOException {

        switch(view.getId()){
            case R.id.btRight:
                if(FirstFragment.btSocket!=null) {
                    String msg = setRight();
                    msg += "\n";
                    FirstFragment.mmOutputStream.write(msg.getBytes());
                    //  myLabel.setText("Data Sent");
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    void sendStop(View view)throws IOException {


                if(FirstFragment.btSocket!=null) {
                    String msg = setStop();
                    msg += "=";
                    FirstFragment.mmOutputStream.write(msg.getBytes());
                    //  myLabel.setText("Data Sent");
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
                }

        }


    private void turnOnLed()
    {
        if (FirstFragment.btSocket!=null)
        {
            try
            {
                FirstFragment.btSocket.getOutputStream().write("on:".toString().getBytes());
            }
            catch (IOException e)
            {
                Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
            }
        }
    }

    void ultrasonicSensor() {


        String m = null;
        if(FirstFragment.btSocket!=null) {
            try {
                m = String.valueOf(FirstFragment.mmInputStream.read());

            } catch (IOException e) {
                e.printStackTrace();
            }

            leftRange.setText(" " + m);
            int range = Integer.parseInt(m);

            if (range < 40) {

                dangerWarning.start();
                long[] pattern = {250, 500};
                vibrator.vibrate(1000);
            }

        }else
        {
            Toast.makeText(getActivity().getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

    }


    String setForward(){
        return "a" +"?" +"L" +"W";
    }

    String setRight(){
        return "j" +"?"+"L" +"W";
    }

    String setLeft(){
        return "v"+ "?"+"L" +"W";
    }

    String setReverse(){
        return "a" +"6"+"L" +"W";
    }
    String setStop(){
        return "=";
    }

    public static void setMinMax(double x, double y){
        if (y > maxY){
            maxY = y;
        }
        else if (y < minY){
            minY = y;
        }
        if (x > maxX){
            maxX = x;
        }
        else if (x < minX){
            minX = x;
        }
    }

    public static double getMaxY(){
        return maxY;
    }
    public static double getMaxX(){
        return maxX;
    }
    public static double getMinY(){
        return minY;
    }
    public static double getMinX(){
        return minX;
    }

    public void shiftLast(double newX, double newY){
        lastX2 = lastX;
        lastY2 = lastY;

        lastX = newX;
        lastY = newY;
    }

    public boolean decreasingX(){
        if(lastX < lastX2){
            return true;
        }
        else return false;
    }



    public static ArrayList<PointValue> getArray() {
        return entries;
    }
    public static ArrayList<PointValue> getArray1() {
        return entries1;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
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





