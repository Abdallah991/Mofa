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

import java.text.SimpleDateFormat;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRegistration.carPhotos;
import static com.fathom.mofa.VehicleSetUp.vehicle;
import static com.fathom.mofa.VehicleSetUpDamageReport.damageReport;
import static com.fathom.mofa.VehicleSetUpRentalInfo.rentalInfo;


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
    private Button editVehicleInformation;
    private Button editRegistration;
    private Button editDamageReport;
    private Button editRentalInformation;
    private NavController mNavController;
    private int index = 0;
    private int actionToSignature = R.id.action_vehicleSetUpConfirmation_to_vehicleSetUpSignature;
    private View rootView;
    private ImageView backConfirmation;
    private ImageView frontConfirmation;
    private ImageView frontRightConfirmation;
    private ImageView frontLeftConfirmation;
    private ImageView frontWindshieldConfirmation;
    private ImageView frontRightDoorConfirmation;
    private ImageView frontLeftDoorConfirmation;
    private ImageView frontCeilingConfirmation;
    private ImageView backCeilingConfirmation;
    private ImageView backRightDoorConfirmation;
    private ImageView backLeftDoorConfirmation;
    private ImageView backWindShieldConfirmation;
    private ImageView backRightConfirmation;
    private ImageView backLeftConfirmation;
    private int actionToRentalInfo = R.id.action_vehicleSetUpConfirmation_to_vehicleSetUpRentalInfo;
    private int actionToRegistration = R.id.action_vehicleSetUpConfirmation_to_vehicleRegistration;
    private int actionToDamageReport = R.id.action_vehicleSetUpConfirmation_to_vehicleSetUpDamageReport;
    private int actionToVehicleInformation = R.id.action_vehicleSetUpConfirmation_to_vehicleSetUp;

    public VehicleSetUpConfirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.damage_report, container, false);
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
        make = view.findViewById(R.id.makeValue);
        model = view.findViewById(R.id.modelValue);
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
        editVehicleInformation = view.findViewById(R.id.vehicleInformationEdit);
        editRegistration = view.findViewById(R.id.registrationEdit);
        editRentalInformation = view.findViewById(R.id.rentalInfoEdit);
        editDamageReport = view.findViewById(R.id.damageReportEdit);

        // Date Formatter
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String registrationStartDate = formatter.format(vehicle.getRegistrationStart());
        String registrationEndDate = formatter.format(vehicle.getRegistrationEnd());
        String leaseFromDate = formatter.format(rentalInfo.getLeaseFrom());
        String leaseToDate = formatter.format(rentalInfo.getLeaseTo());

        // Damage Report Review Elements
        backConfirmation = view.findViewById(R.id.backConfirmation);
        backRightConfirmation = view.findViewById(R.id.backRightConfirmation);
        backLeftConfirmation = view.findViewById(R.id.backLeftConfirmation);
        backWindShieldConfirmation = view.findViewById(R.id.windShieldBackConfirmation);
        backRightDoorConfirmation = view.findViewById(R.id.backRightDoorConfirmation);
        backLeftDoorConfirmation = view.findViewById(R.id.backLeftDoorConfirmation);
        backCeilingConfirmation = view.findViewById(R.id.backCeilingConfirmation);
        frontCeilingConfirmation = view.findViewById(R.id.ceilingConfirmation);
        frontLeftDoorConfirmation = view.findViewById(R.id.frontLeftDoorConfirmation);
        frontRightDoorConfirmation = view.findViewById(R.id.frontRightDoorConfirmation);
        frontWindshieldConfirmation = view.findViewById(R.id.windShieldConfirmation);
        frontRightConfirmation = view.findViewById(R.id.frontRightConfirmation);
        frontLeftConfirmation = view.findViewById(R.id.frontLeftConfirmation);
        frontConfirmation = view.findViewById(R.id.frontConfirmation);

        // setting the values of the car
        plateNumber.setText(vehicle.getPlateNumber());
        manufacturer.setText(vehicle.getManufacturer());
        model.setText(vehicle.getModel());
        color.setText(vehicle.getColorOfCar());
        make.setText(vehicle.getMake());
        type.setText(vehicle.getCarType());
        registrationType.setText(vehicle.getRegistrationType());
        registrationStart.setText(registrationStartDate);
        registrationEnd.setText(registrationEndDate);
        provider.setText(rentalInfo.getName());
        leaseFrom.setText(leaseFromDate);
        leaseTo.setText(leaseToDate);
        providerPhoneNumber.setText(rentalInfo.getPhoneNumber());
        vehicleImage.setImageBitmap(carPhotos.getPhotoLeftSide());

        // Damage Report
        damageReportReview();

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
                        vehicleImage.setImageBitmap(carPhotos.getPhotoLeftSide());
                        break;
                    case 1:
                        firstCircle.setImageResource(R.drawable.grey_dot);
                        secondCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageBitmap(carPhotos.getPhotoRightSide());
                        break;
                    case 2:
                        secondCircle.setImageResource(R.drawable.grey_dot);
                        thirdCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageBitmap(carPhotos.getPhotoFrontSide());
                        break;
                    case 3:
                        thirdCircle.setImageResource(R.drawable.grey_dot);
                        fourthCircle.setImageResource(R.drawable.red_dot);
                        vehicleImage.setImageBitmap(carPhotos.getPhotoBackSide());
                        break;

                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = additionalNotes.getText().toString();
                vehicle.setNotes(notes);
                mNavController.navigate(actionToSignature);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();
            }
        });

        editVehicleInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToVehicleInformation);
            }
        });

        editRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToRegistration);
            }
        });

        editRentalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToRentalInfo);
            }
        });

        editDamageReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToDamageReport);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpConfirmation";
    }

    private void damageReportReview() {
        if(damageReport.isBack()){
            backConfirmation.setImageResource(R.drawable.back_red);
        }
        if(damageReport.isBackRight()){
            backRightConfirmation.setImageResource(R.drawable.back_right_red);
        }
        if(damageReport.isBackLeft()){
            backLeftConfirmation.setImageResource(R.drawable.back_left_red);
        }
        if(damageReport.isBackWindShield()){
            backWindShieldConfirmation.setImageResource(R.drawable.back_wind_shield_red);
        }
        if(damageReport.isBackLeftDoor()){
            backLeftDoorConfirmation.setImageResource(R.drawable.back_door_left_red);
        }
        if(damageReport.isBackRightDoor()){
            backRightDoorConfirmation.setImageResource(R.drawable.back_door_right_red);
        }
        if(damageReport.isPassengerDoor()){
            frontRightDoorConfirmation.setImageResource(R.drawable.front_right_door_red);
        }
        if(damageReport.isDriverDoor()){
            frontLeftDoorConfirmation.setImageResource(R.drawable.front_left_door_red);
        }
        if(damageReport.isBackCeiling()){
            backCeilingConfirmation.setImageResource(R.drawable.ceiling_back_red);
        }
        if(damageReport.isCeiling()){
            frontCeilingConfirmation.setImageResource(R.drawable.ceiling_red);
        }
        if(damageReport.isFrontWindShield()){
            frontWindshieldConfirmation.setImageResource(R.drawable.wind_sheild_red);

        }
        if(damageReport.isFrontRight()){
            frontRightConfirmation.setImageResource(R.drawable.front_right_red);
        }
        if(damageReport.isFrontLeft()){
            frontLeftConfirmation.setImageResource(R.drawable.front_left_red);
        }
        if(damageReport.isFront()){
            frontConfirmation.setImageResource(R.drawable.front_red);

        }


    }
}
