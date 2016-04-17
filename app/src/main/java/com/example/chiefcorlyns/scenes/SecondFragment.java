package com.example.chiefcorlyns.scenes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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

        Button button1 = (Button) view.findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onButtonClicked(v);
            }
        });
        return view;
    }


public void onButtonClicked(View view){

    switch (view.getId()){
        case R.id.button:


            FirstFragment fragment = new FirstFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

          // fr.addToBackStack(null);


    }
}

}


