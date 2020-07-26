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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRecord.vehicleRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleUtilities extends Fragment {

    private NavController mNavController;
    private TextInputEditText milage;
    private SeekBar fuelLevel;
    private ImageView jack;
    private ImageView tools;
    private ImageView spareTire;
    private ImageView CLighter;
    private ImageView wheelCap;
    private ImageView floorMat;
    private Button next;
    private Button back;
    private boolean jackStatus = false;
    private boolean toolsStatus = false;
    private boolean spareTireStatus = false;
    private boolean CLighterStatus = false;
    private boolean wheelCapStatus = false;
    private boolean floorMatStatus = false;
    private int actionToAccidentReport = R.id.action_vehicleUtilities_to_vehicleAccidentReport;
    public VehicleUtilities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_utilities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fuelLevel = view.findViewById(R.id.fuelLevel);
        milage = view.findViewById(R.id.milage);
        jack = view.findViewById(R.id.jackImage);
        tools = view.findViewById(R.id.toolsImage);
        spareTire = view.findViewById(R.id.spareImage);
        CLighter = view.findViewById(R.id.lighterImage);
        wheelCap = view.findViewById(R.id.wheelCapImage);
        floorMat = view.findViewById(R.id.floorMatImage);
        next = view.findViewById(R.id.nextVehicleUtilities);
        back = view.findViewById(R.id.backVehicleUtilities);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        jack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jackStatus) {
                    jack.setImageResource(R.drawable.empty_check_box);
                    jackStatus = false;
                }
                else {
                    jack.setImageResource(R.drawable.checked_check_box);
                    jackStatus = true;
                }
                vehicleRecord.setJackStatus(jackStatus);
            }
        });

        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolsStatus) {
                    tools.setImageResource(R.drawable.empty_check_box);
                    toolsStatus = false;
                }
                else {
                    tools.setImageResource(R.drawable.checked_check_box);
                    toolsStatus = true;
                }
                vehicleRecord.setToolsStatus(toolsStatus);
            }
        });

        spareTire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spareTireStatus) {
                    spareTire.setImageResource(R.drawable.empty_check_box);
                    spareTireStatus = false;
                }
                else {
                    spareTire.setImageResource(R.drawable.checked_check_box);
                    spareTireStatus = true;
                }
                vehicleRecord.setSpareTireStatus(spareTireStatus);

            }
        });

        CLighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CLighterStatus) {
                    CLighter.setImageResource(R.drawable.empty_check_box);
                    CLighterStatus = false;
                }
                else {
                    CLighter.setImageResource(R.drawable.checked_check_box);
                    CLighterStatus = true;
                }
                vehicleRecord.setCLighterStatus(CLighterStatus);
            }
        });

        wheelCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wheelCapStatus) {
                    wheelCap.setImageResource(R.drawable.empty_check_box);
                    wheelCapStatus = false;
                }
                else {
                    wheelCap.setImageResource(R.drawable.checked_check_box);
                    wheelCapStatus = true;
                }
                vehicleRecord.setWheelCapStatus(wheelCapStatus);
            }
        });

        floorMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floorMatStatus) {
                    floorMat.setImageResource(R.drawable.empty_check_box);
                    floorMatStatus = false;
                }
                else {
                    floorMat.setImageResource(R.drawable.checked_check_box);
                    floorMatStatus = true;
                }
                vehicleRecord.setFloorMatStatus(floorMatStatus);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (getVehicleRecord()) {
                    mNavController.navigate(actionToAccidentReport);
//                }
            }
        });
    }

    private boolean getVehicleRecord() {
        String vehicleMilage = milage.getText().toString();
        int fuel = fuelLevel.getProgress();

        if (!vehicleMilage.isEmpty()) {
            vehicleRecord.setMilage(vehicleMilage);
            vehicleRecord.setFuelLevel(fuel);
            return true;
        } else {

            Toast.makeText(getContext(), "Please fill the missing fields", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleUtilities";
    }
}
