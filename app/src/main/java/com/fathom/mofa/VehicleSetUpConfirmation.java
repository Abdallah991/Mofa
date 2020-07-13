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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleSetUp.vehicle;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleSetUpConfirmation extends Fragment {

    private ImageView vehicleImage;
    private ImageView firstCircle;
    private ImageView secondCircle;
    private ImageView thirdCircle;
    private ImageView fourthCircle;
    private TextView plateNumber;
    private TextView manufacturer;
    private TextView model;
    private TextView make;
    private TextView color;
    private TextView type;
    private TextView registrationType;
    private TextView registrationEnd;
    private TextView registrationStart;
    private TextView provider;
    private TextView leaseFrom;
    private TextView leaseTo;
    private TextView providerPhoneNumber;
    private AutoCompleteTextView additionalNotes;
    private Button next;
    private Button back;
    private NavController mNavController;
    private int index = 0;
    private int actionToSignature = R.id.action_vehicleSetUpConfirmation_to_vehicleSetUpSignature;

    public VehicleSetUpConfirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_set_up_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleImage = view.findViewById(R.id.vehicleImages);
        firstCircle = view.findViewById(R.id.firstImage);
        secondCircle = view.findViewById(R.id.secondImage);
        thirdCircle = view.findViewById(R.id.thirdImage);
        fourthCircle = view.findViewById(R.id.fourthImage);
        plateNumber = view.findViewById(R.id.plateNumberValue);
        manufacturer = view.findViewById(R.id.manufacturerValue);
        color = view.findViewById(R.id.colorValue);
        type = view.findViewById(R.id.typeValue);
        registrationType = view.findViewById(R.id.registrationTypeValue);
        registrationStart = view.findViewById(R.id.registrationStartValue);
        registrationEnd = view.findViewById(R.id.registrationEndValue);
        provider = view.findViewById(R.id.providerValue);
        leaseFrom = view.findViewById(R.id.leaseFromValue);
        leaseTo= view.findViewById(R.id.leaseToValue);
        providerPhoneNumber= view.findViewById(R.id.providerPhoneValue);
        additionalNotes= view.findViewById(R.id.additionalNotes);
        next = view.findViewById(R.id.nextVehicleConfirmation);
        back = view.findViewById(R.id.backVehicleConfirmation);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        vehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 3) {
                    index =0;
                } else {
                    index++;
                }
                switch (index) {
                    case 0:
                        fourthCircle.setImageResource(R.drawable.grey_dot);
                        firstCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageResource(R.drawable.vehicle_left_side);
                        break;
                    case 1:
                        firstCircle.setImageResource(R.drawable.grey_dot);
                        secondCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageResource(R.drawable.vehicle_right_side);
                        break;
                    case 2:
                        secondCircle.setImageResource(R.drawable.grey_dot);
                        thirdCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageResource(R.drawable.vehicle_front_side);
                        break;
                    case 3:
                        thirdCircle.setImageResource(R.drawable.grey_dot);
                        fourthCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageResource(R.drawable.vehicle_back_side);
                        break;

                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToSignature);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpConfirmation";
    }
}
