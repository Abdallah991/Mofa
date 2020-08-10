package com.fathom.mofa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRecordDetails extends Fragment {

    private NavController mNavController;
    private TextView vehiclePlateNumber;
    private TextView model;
    private TextView manufacturer;
    private TextView make;
    private TextView color;
    private TextView company;
    private TextView registrationType;
    private TextView registrationStart;
    private TextView registrationEnd;
    private ImageView front;
    private ImageView frontRight;
    private ImageView frontLeft;
    private ImageView frontWindShield;
    private ImageView frontRightDoor;
    private ImageView frontLeftDoor;
    private ImageView frontCeiling;
    private ImageView backCeiling;
    private ImageView backRightDoor;
    private ImageView backLeftDoor;
    private ImageView backWindShield;
    private ImageView backLeft;
    private ImageView backRight;
    private ImageView back;
    private TextView provider;
    private TextView providerPhoneNumber;
    private TextView leaseFrom;
    private TextView leaseTo;
    private TextView assignedDriver;
    private TextView status;
    private TextView notes;
    private Button backButton;
    private ImageView vehicleRecordImages;
    private ImageView firstDot;
    private ImageView secondDot;
    private ImageView thirdDot;
    private ImageView fourthDot;

    public VehicleRecordDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_record_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
