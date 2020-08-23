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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleAccidentReport.carPhotosRecord;
import static com.fathom.mofa.VehicleRecord.damageReportRecord;
import static com.fathom.mofa.VehicleRecord.vehicleInRecord;
import static com.fathom.mofa.VehicleRecord.vehicleRecord;
import static com.fathom.mofa.VehicleRegistration.carPhotos;
import static com.fathom.mofa.VehicleSetUp.vehicle;
import static com.fathom.mofa.VehicleSetUpDamageReport.damageReport;


/**
 * A simple {@link Fragment} subclass.
 */
public class HandoverConfirmation extends Fragment {

    private NavController mNavController;
    private TextView vehicleName;
    private TextView managedBy;
    private TextView driverName;
    private TextView operation;
    private TextView milage;
    private SeekBar mSeekBar;
    private ImageView jack;
    private ImageView tools;
    private ImageView spareTire;
    private ImageView CLighter;
    private ImageView wheelCap;
    private ImageView floorMat;
    // Damage report
    private ViewFlipper mViewFlipper;
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

    private TextView carHasDamage;
    private TextView carIsUseable;
    private Button editVehicleRecord;
    private Button editVehicleUtilities;
    private Button editVehicleAccidentReport;
    private Button next;
    private Button backButton;
    private ImageView vehicleRecordImages;
    private ImageView firstDot;
    private ImageView secondDot;
    private ImageView thirdDot;
    private ImageView fourthDot;
    private AutoCompleteTextView additionalNotes;
    private int index = 0;
    private int actionNavigateToVehicleRecord = R.id.action_handoverConfirmation_to_vehicleRecord;
    private int actionNavigateToAccidentReport = R.id.action_handoverConfirmation_to_vehicleAccidentReport;
    private int actionNavigateToVehicleUtilities = R.id.action_handoverConfirmation_to_vehicleUtilities;
    private int actionNavigateToVehicleRecordSignature = R.id.action_handoverConfirmation_to_vehicleRecordSignature;
    private int actionNavigateBack = R.id.action_handoverConfirmation_to_vehicleAccidentReport;


    public HandoverConfirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handover_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleName = view.findViewById(R.id.vehicleNameValue);
        managedBy = view.findViewById(R.id.managedByValue);
        driverName = view.findViewById(R.id.driverValue);
        operation = view.findViewById(R.id.operationValue);
        milage = view.findViewById(R.id.milageValue);
        mSeekBar = view.findViewById(R.id.fuelLevelConfirmation);
        jack = view.findViewById(R.id.jackConfirmation);
        tools = view.findViewById(R.id.toolsConfirmation);
        spareTire = view.findViewById(R.id.spareTireConfirmation);
        CLighter = view.findViewById(R.id.CLighterConfirmation);
        wheelCap = view.findViewById(R.id.wheelCapConfirmation);
        floorMat = view.findViewById(R.id.floorMatConfirmation);
        mViewFlipper = view.findViewById(R.id.vehicleRecordConfirmationViewFlipper);
        switch (vehicleInRecord.getCarType()) {
            case "Saloon":
                mViewFlipper.setDisplayedChild(0);
                front = view.findViewById(R.id.frontConfirmation);
                frontRight = view.findViewById(R.id.frontRightConfirmation);
                frontLeft = view.findViewById(R.id.frontLeftConfirmation);
                frontWindShield = view.findViewById(R.id.windShieldConfirmation);
                frontRightDoor = view.findViewById(R.id.frontRightDoorConfirmation);
                frontLeftDoor = view.findViewById(R.id.frontLeftDoorConfirmation);
                frontCeiling = view.findViewById(R.id.ceilingConfirmation);
                backCeiling = view.findViewById(R.id.backCeilingConfirmation);
                backLeftDoor = view.findViewById(R.id.backLeftDoorConfirmation);
                backRightDoor = view.findViewById(R.id.backRightDoorConfirmation);
                backWindShield = view.findViewById(R.id.windShieldBackConfirmation);
                back = view.findViewById(R.id.backConfirmation);
                backRight = view.findViewById(R.id.backRightConfirmation);
                backLeft = view.findViewById(R.id.backLeftConfirmation);
                break;
            case "Jeep":
                mViewFlipper.setDisplayedChild(1);
                front = view.findViewById(R.id.jeepFront);
                frontLeft = view.findViewById(R.id.jeepFrontLeft);
                frontRight= view.findViewById(R.id.jeepFrontRight);
                frontLeftDoor = view.findViewById(R.id.jeepFrontLeftDoor);
                frontRightDoor = view.findViewById(R.id.jeepFrontRightDoor);
                frontWindShield = view.findViewById(R.id.jeepWindShield);
                frontCeiling = view.findViewById(R.id.jeepCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoor = view.findViewById(R.id.jeepBackRightDoor);
                backLeftDoor = view.findViewById(R.id.jeepBackLeftDoor);
                backWindShield = view.findViewById(R.id.jeepBackWindShield);
                backLeft = view.findViewById(R.id.jeepBackLeft);
                backRight = view.findViewById(R.id.jeepBackRight);
                back= view.findViewById(R.id.jeepBack);
                break;
            case "Family":
                mViewFlipper.setDisplayedChild(2);
                front = view.findViewById(R.id.familyFront);
                frontLeft = view.findViewById(R.id.familyFrontLeft);
                frontRight = view.findViewById(R.id.familyFrontRight);
                frontLeftDoor = view.findViewById(R.id.familyFrontLeftDoor);
                frontRightDoor = view.findViewById(R.id.familyFrontRightDoor);
                frontWindShield = view.findViewById(R.id.familyWindShield);
                frontCeiling = view.findViewById(R.id.familyCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoor = view.findViewById(R.id.familyBackRightDoor);
                backLeftDoor = view.findViewById(R.id.familyBackLeftDoor);
                backWindShield = view.findViewById(R.id.jeepBackWindShield);
                backLeft = view.findViewById(R.id.familyBackLeft);
                backRight = view.findViewById(R.id.familyBackRight);
                back = view.findViewById(R.id.familyBack);
                break;
            case "Van":
                mViewFlipper.setDisplayedChild(3);
                front = view.findViewById(R.id.vanFront);
                frontLeft = view.findViewById(R.id.vanFrontLeft);
                frontRight = view.findViewById(R.id.vanFrontRight);
                frontLeftDoor = view.findViewById(R.id.vanFrontLeftDoor);
                frontRightDoor = view.findViewById(R.id.vanFrontRightDoor);
                frontWindShield = view.findViewById(R.id.vanWindShield);
                frontCeiling = view.findViewById(R.id.vanCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoor = view.findViewById(R.id.vanBackRightDoor);
                backLeftDoor = view.findViewById(R.id.vanBackLeftDoor);
//                backWindShield = view.findViewById(R.id.vanBackWindShield);
                backLeft = view.findViewById(R.id.vanBackLeft);
                backRight = view.findViewById(R.id.vanBackRight);
                back = view.findViewById(R.id.vanBack);
                break;
        }

        carHasDamage = view.findViewById(R.id.carHasDamageValue);
        carIsUseable = view.findViewById(R.id.carIsUseableValue);
        editVehicleRecord = view.findViewById(R.id.vehicleRecordEdit);
        editVehicleUtilities = view.findViewById(R.id.vehicleUtilitiesEdit);
        editVehicleAccidentReport = view.findViewById(R.id.accidentReportEdit);
        next = view.findViewById(R.id.nextConfirmationRecord);
        backButton = view.findViewById(R.id.backConfirmationRecord);
        vehicleRecordImages = view.findViewById(R.id.vehicleImagesHandover);
        firstDot = view.findViewById(R.id.firstImageHandover);
        secondDot = view.findViewById(R.id.secondImageHandover);
        thirdDot = view.findViewById(R.id.thirdImageHandover);
        fourthDot = view.findViewById(R.id.fourthImageHandover);
        additionalNotes = view.findViewById(R.id.additionalNotesRecord);

        // Setting Confirmation Values
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        vehicleName.setText(vehicleRecord.getVehicleName());
        managedBy.setText(vehicleRecord.getReleasePersonName());
        driverName.setText(vehicleRecord.getDriverName());
        milage.setText(vehicleRecord.getMilage());
        mSeekBar.setProgress(vehicleRecord.getFuelLevel());
        vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoLeftSide());
        vehicleStatus();
        vehicleOperationReview();
        toolsStatusReview();
        damageReportReview();

        vehicleRecordImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleGallery();

            }
        });

        editVehicleAccidentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionNavigateToAccidentReport);

            }
        });

        editVehicleUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionNavigateToVehicleUtilities);

            }
        });

        editVehicleRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionNavigateToVehicleRecord);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleRecord.setNotes(additionalNotes.getText().toString());
                mNavController.navigate(actionNavigateToVehicleRecordSignature);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionNavigateBack);

            }
        });


    }


    @Override
    public void onResume() {
        FRAGMENT = "RecordConfirmation";
        super.onResume();
    }

    private void damageReportReview() {

        if (damageReportRecord.isBack()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    back.setImageResource(R.drawable.back_red);
                    break;
                case "Jeep":
                    back.setImageResource(R.drawable.jeep_back_red);
                    break;
                case "Family":
                case "Van":
                    back.setImageResource(R.drawable.family_back_red);
                    break;
            }
        }
        if (damageReportRecord.isBackRight()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    backRight.setImageResource(R.drawable.back_right_red);
                    break;
                case "Jeep":
                    backRight.setImageResource(R.drawable.jeep_back_right_red);
                    break;
                case "Family":
                case "Van":
                    backRight.setImageResource(R.drawable.family_back_right_red);
                    break;
            }
        }
        if (damageReportRecord.isBackLeft()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    backLeft.setImageResource(R.drawable.back_left_red);
                    break;
                case "Jeep":
                    backLeft.setImageResource(R.drawable.jeep_back_left_red);
                    break;
                case "Family":
                case "Van":
                    backLeft.setImageResource(R.drawable.family_back_left_red);
                    break;
            }
        }

        if (damageReportRecord.isBackWindShield()) {
            if (vehicleInRecord.getCarType().equals("Saloon") ||
                    vehicleInRecord.getCarType().equals("Jeep") ||
                    vehicleInRecord.getCarType().equals("Family")) {
                switch (vehicleInRecord.getCarType()) {
                    case "Saloon":
                        backWindShield.setImageResource(R.drawable.back_wind_shield_red);
                        break;
                    case "Jeep":
                        backWindShield.setImageResource(R.drawable.jeep_back_red);
                        break;
                    case "Family":
                        backWindShield.setImageResource(R.drawable.family_back_red);
                        break;
                }
            }
        }

        if (damageReportRecord.isBackLeftDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    backLeftDoor.setImageResource(R.drawable.back_door_left_red);
                    break;
                case "Jeep":
                    backLeftDoor.setImageResource(R.drawable.jeep_back_left_door_red);
                    break;
                case "Family":
                case "Van":
                    backLeftDoor.setImageResource(R.drawable.family_back_left_door_red);
                    break;
            }
        }
        if (damageReportRecord.isBackRightDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    backRightDoor.setImageResource(R.drawable.back_door_right_red);
                    break;
                case "Jeep":
                    backRightDoor.setImageResource(R.drawable.jeep_back_right_door_red);
                    break;
                case "Family":
                case "Van":
                    backRightDoor.setImageResource(R.drawable.family_back_right_door_red);
                    break;
            }
        }
        if (damageReportRecord.isPassengerDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    frontRightDoor.setImageResource(R.drawable.front_right_door_red);
                    break;
                case "Jeep":
                    frontRightDoor.setImageResource(R.drawable.jeep_front_right_door_red);
                    break;
                case "Family":
                case "Van":
                    frontRightDoor.setImageResource(R.drawable.family_front_right_door_red);
                    break;
            }
        }
        if (damageReportRecord.isDriverDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
                    break;
                case "Jeep":
                    frontLeftDoor.setImageResource(R.drawable.jeep_front_left_door_red);
                    break;
                case "Family":
                case "Van":
                    frontLeftDoor.setImageResource(R.drawable.family_front_left_door_red);
                    break;
            }
        }
        if (vehicleInRecord.getCarType().equals("Saloon")) {
            if (damageReportRecord.isBackCeiling()) {
                backCeiling.setImageResource(R.drawable.ceiling_back_red);
            }
        }
        if (damageReportRecord.isCeiling()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    frontCeiling.setImageResource(R.drawable.ceiling_red);
                    break;
                case "Jeep":
                    frontCeiling.setImageResource(R.drawable.jeep_ceiling_red);
                    break;
                case "Family":
                case "Van":
                    frontCeiling.setImageResource(R.drawable.family_ceiling_red);
                    break;
            }
        }
        if (damageReportRecord.isFrontWindShield()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    frontWindShield.setImageResource(R.drawable.wind_sheild_red);
                    break;
                case "Jeep":
                    frontWindShield.setImageResource(R.drawable.jeep_wind_sheild_red);
                    break;
                case "Family":
                case "Van":
                    frontWindShield.setImageResource(R.drawable.family_wind_sheild_red);
                    break;
            }

        }
        if (damageReportRecord.isFrontRight()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    frontRight.setImageResource(R.drawable.front_right_red);
                    break;
                case "Jeep":
                    frontRight.setImageResource(R.drawable.jeep_front_right_red);
                    break;
                case "Family":
                case "Van":
                    frontRight.setImageResource(R.drawable.family_front_right_red);
                    break;
            }
        }
        if (damageReportRecord.isFrontLeft()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    frontLeftDoor.setImageResource(R.drawable.front_left_red);
                    break;
                case "Jeep":
                    frontLeftDoor.setImageResource(R.drawable.jeep_front_left_red);
                    break;
                case "Family":
                case "Van":
                    frontLeftDoor.setImageResource(R.drawable.family_front_left_red);
                    break;
            }
        }
        if (damageReportRecord.isFront()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                    front.setImageResource(R.drawable.front_red);
                    break;
                case "Jeep":
                    front.setImageResource(R.drawable.jeep_front_red);
                    break;
                case "Family":
                case "Van":
                    front.setImageResource(R.drawable.family_front_red);
                    break;
            }

        }







    }

    private void toolsStatusReview() {
        if (vehicleRecord.isJackStatus()) {
            jack.setImageResource(R.drawable.checked_check_box);
        }
        if (vehicleRecord.isToolsStatus()) {
            tools.setImageResource(R.drawable.checked_check_box);
        }
        if (vehicleRecord.isCLighterStatus()) {
            CLighter.setImageResource(R.drawable.checked_check_box);
        }
        if (vehicleRecord.isSpareTireStatus()) {
            spareTire.setImageResource(R.drawable.checked_check_box);
        }
        if (vehicleRecord.isWheelCapStatus()) {
            wheelCap.setImageResource(R.drawable.checked_check_box);
        }
        if (vehicleRecord.isFloorMatStatus()) {
            floorMat.setImageResource(R.drawable.checked_check_box);
        }

    }

    private void vehicleOperationReview() {
        switch (vehicleRecord.getCarTransaction()) {
            case "MTD":
                operation.setText("Handover to Driver");
                break;
            case "DTM":
                operation.setText("Retrieval from Driver");
                break;
            case "MTR":
                operation.setText("Handover to Rental Company");
                break;
        }
    }

    private void vehicleStatus() {
        if(vehicleRecord.isCarHasDamage()) {
            carHasDamage.setText("Yes");
        }else {
            carHasDamage.setText("No");

        }
        if(vehicleRecord.isCarIsUseable()) {
            carIsUseable.setText("Yes");
        }else {
            carIsUseable.setText("No");

        }
    }

    private void vehicleGallery() {
        if (index == 3) {
            index =0;
        } else {
            index++;
        }
        switch (index) {
            case 0:
                fourthDot.setImageResource(R.drawable.grey_dot);
                firstDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoLeftSide());
                break;
            case 1:
                firstDot.setImageResource(R.drawable.grey_dot);
                secondDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoRightSide());
                break;
            case 2:
                secondDot.setImageResource(R.drawable.grey_dot);
                thirdDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoFrontSide());
                break;
            case 3:
                thirdDot.setImageResource(R.drawable.grey_dot);
                fourthDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoBackSide());
                break;

        }

    }
}
