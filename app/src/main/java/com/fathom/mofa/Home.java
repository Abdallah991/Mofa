package com.fathom.mofa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    ImageView dashboard;
    ImageView vehicleSetUp;
    ImageView driverSetUp;
    ImageView vehicleRecord;
    private NavController mNavController;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboard = view.findViewById(R.id.dashboardButton);
        vehicleSetUp = view.findViewById(R.id.vehicleSetUpButton);
        vehicleRecord = view.findViewById(R.id.vehicleRecordButton);
        driverSetUp = view.findViewById(R.id.driverSetUpButton);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.dashboard);
            }
        });

        vehicleSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.vehicleSetUp);
            }
        });

        vehicleRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.vehicleRecord);
            }
        });

        driverSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.driverSetUp);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "home";
    }
}
