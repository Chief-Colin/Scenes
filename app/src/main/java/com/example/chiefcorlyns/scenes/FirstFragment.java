package com.example.chiefcorlyns.scenes;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.support.v7.app.AlertDialog.Builder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    BluetoothDevice mmDevice;
    public static OutputStream mmOutputStream;
    public static InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;


    NavigationView navigationView = null;
    Toolbar toolbar = null;
    public Set<BluetoothDevice> pairedDevices;


    // static public String address = null;
    private ProgressDialog progress;
    public static BluetoothAdapter myBluetooth = null;
    public static BluetoothSocket btSocket = null;
    public static boolean isBtConnected = false;
    public static String ipAddress;
    //SPP UUID. Look for it


    //widgets
    Button btnPaired;
    ListView devicelist;
    //Bluetooth


    volatile boolean stopWorker;
    Button openButton;
    Button ipButton;
    EditText ipText;
    View view;
    ImageView imageview;



     public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        view = inflater.inflate(R.layout.fragment_first, container, false);

        //startAnimation();
        openButton = (Button) view.findViewById(R.id.btBluetooth);
        imageview = (ImageView) view.findViewById(R.id.car1);
        devicelist = (ListView) view.findViewById(R.id.listView);

        btnPaired = (Button) view.findViewById(R.id.button);
        devicelist = (ListView) view.findViewById(R.id.listView);
        ipText = (EditText) view.findViewById(R.id.txtIP);
        ipButton = (Button) view.findViewById(R.id.enterIP);

        //if the device has bluetooth

        final Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.animate);
        imageview.startAnimation(animate);

        animate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pairedDevicesList();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //Open Button

        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getActivity(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            //finish();
        } else if (!myBluetooth.isEnabled()) {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });

        return view;
    }
   // @Override
    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getActivity(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }


    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);



            // Make an intent to start next activity.
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID


            try {
                myBluetooth = BluetoothAdapter.getDefaultAdapter();//
                mmDevice = myBluetooth.getRemoteDevice(address);

                    btSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);


                    btSocket.connect();
                    mmOutputStream = btSocket.getOutputStream();
                    mmInputStream = btSocket.getInputStream();



            } catch (IOException e) {
                e.printStackTrace();
            }
                if (btSocket.isConnected()==false){


                        Toast.makeText(getActivity(), " Devices Not Found.", Toast.LENGTH_LONG).show();
                    }
                    else {

                        beginListenForData();


                    }  //myLabel.setText("Bluetooth Opened");
        }

        void beginListenForData() {
            final Handler handler = new Handler();
            final byte delimiter = 10; //This is the ASCII code for a newline character

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];
            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                        try {
                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                //myLabel.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            stopWorker = true;
                        }
                    }
                }
            });
             //getMenuInflater().inflate(R.menu.setting, menu);
            openDialog();

        }
    };

    public void openDialog() {
        Builder builder = new Builder(getContext());
        builder.setTitle("Cobra Mc");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.ip_popup, (ViewGroup) getView(), false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.txtIP);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int num) {
                dialog.dismiss();
                ipAddress = input.getText().toString();

                }

        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int num) {
                dialog.cancel();
            }
        });

        builder.show();


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


}




















































