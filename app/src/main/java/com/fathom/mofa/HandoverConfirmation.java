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

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleAccidentReport.carPhotosRecord;
import static com.fathom.mofa.VehicleRecord.damageReportRecord;
import static com.fathom.mofa.VehicleRecord.vehicleRecord;
import static com.fathom.mofa.VehicleRegistration.carPhotos;
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
            back.setImageResource(R.drawable.back_red);
        }
        if (damageReportRecord.isBackRight()) {
            backRight.setImageResource(R.drawable.back_right_red);
        }
        if (damageReportRecord.isBackLeft()) {
            backLeft.setImageResource(R.drawable.back_left_red);
        }
        if (damageReportRecord.isBackWindShield()) {
            backWindShield.setImageResource(R.drawable.back_wind_shield_red);
        }
        if (damageReportRecord.isBackLeftDoor()) {
            backLeftDoor.setImageResource(R.drawable.back_door_left_red);
        }
        if (damageReportRecord.isBackRightDoor()) {
            backRightDoor.setImageResource(R.drawable.back_door_right_red);
        }
        if (damageReportRecord.isPassengerDoor()) {
            frontRightDoor.setImageResource(R.drawable.front_right_door_red);
        }
        if (damageReportRecord.isDriverDoor()) {
            frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
        }
        if (damageReportRecord.isBackCeiling()) {
            backCeiling.setImageResource(R.drawable.ceiling_back_red);
        }
        if (damageReportRecord.isCeiling()) {
            frontCeiling.setImageResource(R.drawable.ceiling_red);
        }
        if (damageReportRecord.isFrontWindShield()) {
            frontWindShield.setImageResource(R.drawable.wind_sheild_red);

        }
        if (damageReportRecord.isFrontRight()) {
            frontRight.setImageResource(R.drawable.front_right_red);
        }
        if (damageReportRecord.isFrontLeft()) {
            frontLeft.setImageResource(R.drawable.front_left_red);
        }
        if (damageReportRecord.isFront()) {
            front.setImageResource(R.drawable.front_red);

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
