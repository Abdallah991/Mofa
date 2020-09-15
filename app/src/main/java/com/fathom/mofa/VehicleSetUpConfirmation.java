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
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRegistration.carPhotos;
import static com.fathom.mofa.VehicleSetUp.vehicle;
import static com.fathom.mofa.VehicleSetUpDamageReport.FAMILY;
import static com.fathom.mofa.VehicleSetUpDamageReport.JEEP;
import static com.fathom.mofa.VehicleSetUpDamageReport.SALOON;
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
    private TextView vin;
    private TextView motorSize;
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
    private ImageView frontRightTire;
    private ImageView frontLeftTire;
    private ImageView backRightTire;
    private ImageView backLeftTire;
    // view Flipper
    private ViewFlipper mViewFlipper;
    // Navigation Actions
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

        vehicleImage = view.findViewById(R.id.vehicleImagesHandover);
        firstCircle = view.findViewById(R.id.firstImageHandover);
        secondCircle = view.findViewById(R.id.secondImageHandover);
        thirdCircle = view.findViewById(R.id.thirdImageHandover);
        fourthCircle = view.findViewById(R.id.fourthImageHandover);
        plateNumber = view.findViewById(R.id.plateNumberValue);
        vin = view.findViewById(R.id.vinValue);
        motorSize = view.findViewById(R.id.motorSizeValue);
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
        leaseTo = view.findViewById(R.id.leaseToValue);
        providerPhoneNumber = view.findViewById(R.id.providerPhoneValue);
        additionalNotes = view.findViewById(R.id.additionalNotes);
        next = view.findViewById(R.id.nextVehicleConfirmation);
        back = view.findViewById(R.id.backVehicleConfirmation);
        editVehicleInformation = view.findViewById(R.id.vehicleInformationEdit);
        editRegistration = view.findViewById(R.id.registrationEdit);
        editRentalInformation = view.findViewById(R.id.rentalInfoEdit);
        editDamageReport = view.findViewById(R.id.damageReportEdit);

        // Date Formatter
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String registrationStartDate = formatter.format(vehicle.getRegistrationStart());
        String registrationEndDate = formatter.format(vehicle.getRegistrationEnd());
        String leaseFromDate = formatter.format(rentalInfo.getLeaseFrom());
        String leaseToDate = formatter.format(rentalInfo.getLeaseTo());

        // Damage Report Review Elements
        mViewFlipper = view.findViewById(R.id.vehicleConfirmationViewFlipper);
        switch (vehicle.getCarType()) {
            case "Saloon":
            case "صالون":
                mViewFlipper.setDisplayedChild(0);
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
                backRightTire = view.findViewById(R.id.backRightTire);
                backLeftTire = view.findViewById(R.id.backLeftTire);
                frontRightTire = view.findViewById(R.id.frontRightTire);
                frontLeftTire = view.findViewById(R.id.frontLeftTire);
                break;
            case "Jeep":
            case "جيب":
                mViewFlipper.setDisplayedChild(1);
                frontConfirmation = view.findViewById(R.id.jeepFront);
                frontLeftConfirmation = view.findViewById(R.id.jeepFrontLeft);
                frontRightConfirmation = view.findViewById(R.id.jeepFrontRight);
                frontLeftDoorConfirmation = view.findViewById(R.id.jeepFrontLeftDoor);
                frontRightDoorConfirmation = view.findViewById(R.id.jeepFrontRightDoor);
                frontWindshieldConfirmation = view.findViewById(R.id.jeepWindShield);
                frontCeilingConfirmation = view.findViewById(R.id.jeepCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoorConfirmation = view.findViewById(R.id.jeepBackRightDoor);
                backLeftDoorConfirmation = view.findViewById(R.id.jeepBackLeftDoor);
                backWindShieldConfirmation = view.findViewById(R.id.jeepBackWindShield);
                backLeftConfirmation = view.findViewById(R.id.jeepBackLeft);
                backRightConfirmation = view.findViewById(R.id.jeepBackRight);
                backConfirmation = view.findViewById(R.id.jeepBack);
                backRightTire = view.findViewById(R.id.jeepBackRightTire);
                backLeftTire = view.findViewById(R.id.jeepBackLeftTire);
                frontRightTire = view.findViewById(R.id.jeepFrontRightTire);
                frontLeftTire = view.findViewById(R.id.jeepFrontLeftTire);
                break;
            case "Family":
            case "مركبة عائلية":
                mViewFlipper.setDisplayedChild(2);
                frontConfirmation = view.findViewById(R.id.familyFront);
                frontLeftConfirmation = view.findViewById(R.id.familyFrontLeft);
                frontRightConfirmation = view.findViewById(R.id.familyFrontRight);
                frontLeftDoorConfirmation = view.findViewById(R.id.familyFrontLeftDoor);
                frontRightDoorConfirmation = view.findViewById(R.id.familyFrontRightDoor);
                frontWindshieldConfirmation = view.findViewById(R.id.familyWindShield);
                frontCeilingConfirmation = view.findViewById(R.id.familyCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoorConfirmation = view.findViewById(R.id.familyBackRightDoor);
                backLeftDoorConfirmation = view.findViewById(R.id.familyBackLeftDoor);
                backWindShieldConfirmation = view.findViewById(R.id.jeepBackWindShield);
                backLeftConfirmation = view.findViewById(R.id.familyBackLeft);
                backRightConfirmation = view.findViewById(R.id.familyBackRight);
                backConfirmation = view.findViewById(R.id.familyBack);
                backRightTire = view.findViewById(R.id.familyBackRightTire);
                backLeftTire = view.findViewById(R.id.familyBackLeftTire);
                frontRightTire = view.findViewById(R.id.familyFrontRightTire);
                frontLeftTire = view.findViewById(R.id.familyFrontLeftTire);
                break;
            case "Van":
            case "شاحنة صغيرة":
                mViewFlipper.setDisplayedChild(3);
                frontConfirmation = view.findViewById(R.id.vanFront);
                frontLeftConfirmation = view.findViewById(R.id.vanFrontLeft);
                frontRightConfirmation = view.findViewById(R.id.vanFrontRight);
                frontLeftDoorConfirmation = view.findViewById(R.id.vanFrontLeftDoor);
                frontRightDoorConfirmation = view.findViewById(R.id.vanFrontRightDoor);
                frontWindshieldConfirmation = view.findViewById(R.id.vanWindShield);
                frontCeilingConfirmation = view.findViewById(R.id.vanCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoorConfirmation = view.findViewById(R.id.vanBackRightDoor);
                backLeftDoorConfirmation = view.findViewById(R.id.vanBackLeftDoor);
//                backWindShield = view.findViewById(R.id.vanBackWindShield);
                backLeftConfirmation = view.findViewById(R.id.vanBackLeft);
                backRightConfirmation = view.findViewById(R.id.vanBackRight);
                backConfirmation = view.findViewById(R.id.vanBack);
                backRightTire = view.findViewById(R.id.vanBackRightTire);
                backLeftTire = view.findViewById(R.id.vanBackLeftTire);
                frontRightTire = view.findViewById(R.id.vanFrontRightTire);
                frontLeftTire = view.findViewById(R.id.vanFrontLeftTire);
                break;
        }

        // Language handling
        mViewFlipper.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        // setting the values of the car
        plateNumber.setText(vehicle.getPlateNumber());
        vin.setText(vehicle.getChassisNumber());
        motorSize.setText(vehicle.getMotorSize());
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
                    index = 0;
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
        if (damageReport.isBack()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    backConfirmation.setImageResource(R.drawable.back_red);
                    break;
                case "Jeep":
                case "جيب":
                    backConfirmation.setImageResource(R.drawable.jeep_back_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backConfirmation.setImageResource(R.drawable.family_back_red);
                    break;
            }
        }
        if (damageReport.isBackRight()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    backRightConfirmation.setImageResource(R.drawable.back_right_red);
                    break;
                case "Jeep":
                case "جيب":
                    backRightConfirmation.setImageResource(R.drawable.jeep_back_right_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backRightConfirmation.setImageResource(R.drawable.family_back_right_red);
                    break;
            }
        }
        if (damageReport.isBackLeft()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    backLeftConfirmation.setImageResource(R.drawable.back_left_red);
                    break;
                case "Jeep":
                case "جيب":
                    backLeftConfirmation.setImageResource(R.drawable.jeep_back_left_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backLeftConfirmation.setImageResource(R.drawable.family_back_left_red);
                    break;
            }
        }

        if (damageReport.isBackWindShield()) {
            if (vehicle.getCarType().equals(SALOON) ||
                    vehicle.getCarType().equals(JEEP) ||
                    vehicle.getCarType().equals(FAMILY)) {
                switch (vehicle.getCarType()) {
                    case "Saloon":
                    case "صالون":
                        backWindShieldConfirmation.setImageResource(R.drawable.back_wind_shield_red);
                        break;
                    case "Jeep":
                    case "جيب":
                        backWindShieldConfirmation.setImageResource(R.drawable.jeep_back_red);
                        break;
                    case "Family":
                    case "مركبة عائلية":
                    case "شاحنة صغيرة":
                        backWindShieldConfirmation.setImageResource(R.drawable.family_back_red);
                        break;
                }
            }
        }

        if (damageReport.isBackLeftDoor()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    backLeftDoorConfirmation.setImageResource(R.drawable.back_door_left_red);
                    break;
                case "Jeep":
                case "جيب":
                    backLeftDoorConfirmation.setImageResource(R.drawable.jeep_back_left_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backLeftDoorConfirmation.setImageResource(R.drawable.family_back_left_door_red);
                    break;
            }
        }
        if (damageReport.isBackRightDoor()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    backRightDoorConfirmation.setImageResource(R.drawable.back_door_right_red);
                    break;
                case "Jeep":
                case "جيب":
                    backRightDoorConfirmation.setImageResource(R.drawable.jeep_back_right_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backRightDoorConfirmation.setImageResource(R.drawable.family_back_right_door_red);
                    break;
            }
        }
        if (damageReport.isPassengerDoor()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontRightDoorConfirmation.setImageResource(R.drawable.front_right_door_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontRightDoorConfirmation.setImageResource(R.drawable.jeep_front_right_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontRightDoorConfirmation.setImageResource(R.drawable.family_front_right_door_red);
                    break;
            }
        }
        if (damageReport.isDriverDoor()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontLeftDoorConfirmation.setImageResource(R.drawable.front_left_door_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontLeftDoorConfirmation.setImageResource(R.drawable.jeep_front_left_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontLeftDoorConfirmation.setImageResource(R.drawable.family_front_left_door_red);
                    break;
            }
        }
        if (vehicle.getCarType().equals(SALOON)) {
            if (damageReport.isBackCeiling()) {
                backCeilingConfirmation.setImageResource(R.drawable.back_red);
            }
        }
        if (damageReport.isCeiling()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontCeilingConfirmation.setImageResource(R.drawable.ceiling_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontCeilingConfirmation.setImageResource(R.drawable.jeep_ceiling_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontCeilingConfirmation.setImageResource(R.drawable.family_ceiling_red);
                    break;
            }
        }
        if (damageReport.isFrontWindShield()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontWindshieldConfirmation.setImageResource(R.drawable.wind_sheild_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontWindshieldConfirmation.setImageResource(R.drawable.jeep_wind_sheild_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontWindshieldConfirmation.setImageResource(R.drawable.family_wind_sheild_red);
                    break;
            }

        }
        if (damageReport.isFrontRight()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontRightConfirmation.setImageResource(R.drawable.front_right_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontRightConfirmation.setImageResource(R.drawable.jeep_front_right_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontRightConfirmation.setImageResource(R.drawable.family_front_right_red);
                    break;
            }
        }
        if (damageReport.isFrontLeft()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontLeftConfirmation.setImageResource(R.drawable.front_left_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontLeftConfirmation.setImageResource(R.drawable.jeep_front_left_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontLeftConfirmation.setImageResource(R.drawable.family_front_left_red);
                    break;
            }
        }
        if (damageReport.isFront()) {
            switch (vehicle.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontConfirmation.setImageResource(R.drawable.front_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontConfirmation.setImageResource(R.drawable.jeep_front_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontConfirmation.setImageResource(R.drawable.family_front_red);
                    break;
            }

        }


        if (damageReport.isFrontRightTire()) {
            frontRightTire.setImageResource(R.drawable.tire_red);

        }
        if (damageReport.isFrontLeftTire()) {
            frontLeftTire.setImageResource(R.drawable.tire_red);
        }
        if (damageReport.isBackLeftTire()) {
            backLeftTire.setImageResource(R.drawable.tire_red);
        }

        if (damageReport.isBackRightTire()) {
            backRightTire.setImageResource(R.drawable.tire_red);
        }


    }
}
