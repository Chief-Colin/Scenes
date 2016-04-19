package com.example.chiefcorlyns.scenes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SecondFragment extends Fragment {

    View view;





    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_second, container, false);
        view = inflater.inflate(R.layout.fragment_second, container, false);


        Button Foward = (Button) view.findViewById(R.id.btForward);
        Button Reverse = (Button) view.findViewById(R.id.btReverse);
        Button Right = (Button) view.findViewById(R.id.btRight);
        Button Left = (Button) view.findViewById(R.id.btLeft);
        Button Stop = (Button) view.findViewById(R.id.btStop);

        Foward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendForward(v);


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

                } catch (IOException ex) {
                }
            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendLeft(v);

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
                FirstFragment.mmOutputStream.write(msg.getBytes());
                //myLabel.setText("Data Sent");
                break;
        }
    }

    void sendReverse(View view)throws IOException {
        switch (view.getId()) {
            case R.id.btReverse:
                String msg = setReverse();
                msg += "\n";
                FirstFragment.mmOutputStream.write(msg.getBytes());
                //  myLabel.setText("Data Sent");

                break;
        }
    }

    void sendLeft(View view)throws IOException  {
        switch (view.getId()) {
            case R.id.btLeft:
                String msg = setLeft();
                msg += "\n";
                FirstFragment.mmOutputStream.write(msg.getBytes());
                // myLabel.setText("left turn");

                break;
        }

    }

    void sendRight(View view)throws IOException {

        switch(view.getId()){
            case R.id.btRight:
                String msg = setRight();
                msg += "\n";
                FirstFragment.mmOutputStream.write(msg.getBytes());
                //  myLabel.setText("Data Sent");

                break;

        }
    }

    void sendStop(View view)throws IOException {

        switch(view.getId()){
            case R.id.btStop:
                String msg = setStop();
                msg += "\n";
                FirstFragment.mmOutputStream.write(msg.getBytes());
                //  myLabel.setText("Data Sent");

                break;

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





