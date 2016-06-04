package com.example.chiefcorlyns.scenes;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
public class TiltControl extends Activity implements SensorEventListener {
    Button btnDis;
    //TextView lumn;
    private ProgressDialog progress;
    //MOTION STARTS
    //private static final String TAG = ledControl.class.getSimpleName();
    public static SensorManager mSensorManager;
    public static Sensor mAccelerometer;
    TextView title,tv,tv1,tv2, tv3,tv4;
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    String sendSpeed;
    String direction;
    //MOTION ENDS
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Intent newint = getIntent();
        // PairedDevices.address = newint.getStringExtra(PairedDevices.EXTRA_ADDRESS); //receive the address of the bluetooth device
        //view of the ledControl
        setContentView(R.layout.activity_tilt_control);
        //call the widgtes
        btnDis = (Button)findViewById(R.id.button4);
        //MOTION START
        //get the sensor service
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //get the accelerometer sensor
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //get layout
        tv=(TextView)findViewById(R.id.xval);
        tv1=(TextView)findViewById(R.id.yval);
        tv2=(TextView)findViewById(R.id.zval);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        Log.d("TAG", "My debug-message4");
        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments noinspection all
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webView = (VideoEnabledWebView) findViewById(R.id.webView);// Save the web view
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
    }
    private void Disconnect()
    {
        if (FirstFragment.btSocket!=null) //If the btSocket is busy
        {
            try
            {
                // FirstFragment.btSocket.close(); //close connection
                FirstFragment.mmOutputStream.write("(".getBytes());
            }
            catch (IOException e)
            { msg("Error");}
        }
        //finish(); //return to the first layout
    }
    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //MOTION STARTS
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Do something here if sensor accuracy changes.
    }
    @Override
    public final void onSensorChanged(SensorEvent event) {
        float x, y, z = 0;
        WindowManager windowMgr = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        int rotationIndex = windowMgr.getDefaultDisplay().getRotation();
        if (rotationIndex == 1 || rotationIndex == 3) {            // detect 90 or 270 degree rotation (îïðåäåëÿåì ïîâîðîò óñòðîéñòâà íà 90 èëè 270 ãðàäóñîâ)
            x = -event.values[1];
            y = event.values[0];
        } else {
            x = event.values[0];            //Force x axis in m s-2
            y = event.values[1];           //Force y axis in m s-2
        }
        // Many sensors return 3 values, one for each axis.
        tv.setText("X axisz" + "\t\t" + x);
        tv1.setText("Y axis" + "\t\t" + y);
        tv2.setText("Z axis" + "\t\t" + z);
        if (x <= 1 && x > 0) {
            sendSpeed = "W" + "a";
        } else if(x>1 && x<=2) {
            sendSpeed = "J" +"n";
        }else if(x>2 && x<=3)
        {
            sendSpeed = "F" +"o";
        }
        if (x> 3 && x <= 4) {
            sendSpeed = "[" +"p";
        } else if(x>4 && x<=5) {
            sendSpeed ="D" +"q" ;
        }else if(x>5 && x<=6)
        {
            sendSpeed ="O" +"r" ;
        }
        if (x > 6 && x<= 7) {
            sendSpeed = "C" +"s";
        } else if(x>7 && x<=8) {
            sendSpeed = "B" +"t";
        }else if(x>8 && x<=9)
        {
            sendSpeed ="}" +"u";
        }
        else if(x>9 )
        {
            sendSpeed ="A" +"v" ;
        }
        else if (x <= -9 ) {
            sendSpeed = "H" +"j";
        } else if(x<=-8 && x>-9) {
            sendSpeed =  "V" + "i";
        } else if(x<=-7 && x>-8) {
            sendSpeed = "Z"+ "h";
        }
        else if(x<=-6 && x>-7) {
            sendSpeed = "X" +"g";
        } else if(x<=-5 && x>-6) {
            sendSpeed = "S"+ "f";
        }
        else if(x<=-4 && x>-5) {
            sendSpeed = "U"+"e";
        } else if(x<=-3 && x>-4) {
            sendSpeed = "Y" +"d";
        }
        else if(x<=-2 && x>-3) {
            sendSpeed = "R" +"c";
        }
        else if(x<=-1 && x>-2) {
            sendSpeed = "E" + "b";
        }
        else if(x<0 && x>-1) {
            sendSpeed = "Q" +"m";
        }
        if(y>1 && y<4){
            direction="P" +"2";
        }
        else if(y>=4 && y<7){
            direction="K" +"4";
        }
        else if(y>=7 ){
            direction="M" + "7";
        }
        else if(y<1 && y>-1){
            direction="L" + "#";
        }
        else if(y<-1 && y>= -4){
            direction ="G"+ "+";
        }
        else if(y>=-7 && y<-4){
            direction ="*"+"|";
        }
        else if(y<-7){
            direction ="I"+"@";
        }
        if(FirstFragment.btSocket!=null) {
            try {
                FirstFragment.mmOutputStream.write(sendSpeed.getBytes());
                FirstFragment.mmOutputStream.write(direction.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            msg("No Device Paired");
        }
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    //MOTION ENDS
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