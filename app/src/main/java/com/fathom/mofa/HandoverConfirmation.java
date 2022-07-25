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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;

import static com.fathom.mofa.Adapters.VehicleRecordsAdapter.vehicleRecordDashboard;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleDetails.carPhotosRecord;
import static com.fathom.mofa.VehicleDetails.damageReportRecord;
import static com.fathom.mofa.VehicleRecord.vehicleInRecord;
import static com.fathom.mofa.VehicleDetails.vehicleRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class HandoverConfirmation extends Fragment {

//    variable declaration
    private NavController mNavController;
    private TextView operation;
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
    private ImageView frontRightTire;
    private ImageView frontLeftTire;
    private ImageView backRightTire;
    private ImageView backLeftTire;
    // Adding the ring
    private ImageView frontRightRing;
    private ImageView frontLeftRing;
    private ImageView backRightRing;
    private ImageView backLeftRing;

    private TextView carHasDamage;
    private TextView carIsUseable;
    private TextView carIsClean;
    private ImageView vehicleRecordImages;
    private ImageView firstDot;
    private ImageView secondDot;
    private ImageView thirdDot;
    private ImageView fourthDot;
    private ImageView fifthDot;
    private ImageView sixthDot;
    private ImageView seventhDot;
    private AutoCompleteTextView additionalNotes;
    private int index = 0;
    private int actionNavigateToVehicleRecord = R.id.action_handoverConfirmation_to_vehicleRecord;
    private int actionNavigateToAccidentReport = R.id.action_handoverConfirmation_to_vehicleAccidentReport;
    private int actionNavigateToVehicleUtilities = R.id.action_handoverConfirmation_to_vehicleUtilities;
    private int actionNavigateToVehicleRecordSignature = R.id.action_handoverConfirmation_to_vehicleRecordSignature;
    private int actionNavigateBack = R.id.action_handoverConfirmation_to_vehicleAccidentReport;
    private TextView vehicleDestinationConfirmation;
    private TextView destinationValueConfirmation;



    // MultiLanguage
    public static String SALOON;
    public static String JEEP;
    public static String FAMILY;
    public static String VAN;

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

//         link variables and images
        TextView vehicleName = view.findViewById(R.id.vehicleNameValue);
        TextView managedBy = view.findViewById(R.id.managedByValue);
        TextView driverName = view.findViewById(R.id.driverValue);
        TextView driverTitle = view.findViewById(R.id.driverTitle);
        operation = view.findViewById(R.id.operationValue);
        TextView milage = view.findViewById(R.id.milageValue);
        SeekBar mSeekBar = view.findViewById(R.id.fuelLevelConfirmation);
        jack = view.findViewById(R.id.jackConfirmation);
        tools = view.findViewById(R.id.toolsConfirmation);
        spareTire = view.findViewById(R.id.spareTireConfirmation);
        CLighter = view.findViewById(R.id.CLighterConfirmation);
        wheelCap = view.findViewById(R.id.wheelCapConfirmation);
        floorMat = view.findViewById(R.id.floorMatConfirmation);
        vehicleDestinationConfirmation = view.findViewById(R.id.vehicleDestinationConfirmation);
        destinationValueConfirmation = view.findViewById(R.id.destinationValueConfirmation);

        // Damage report UI linking
        ViewFlipper mViewFlipper = view.findViewById(R.id.vehicleRecordConfirmationViewFlipper);
        switch (vehicleInRecord.getCarType()) {
            case "Saloon":
            case "صالون":
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
                backRightTire = view.findViewById(R.id.backRightTire);
                backLeftTire = view.findViewById(R.id.backLeftTire);
                frontRightTire = view.findViewById(R.id.frontRightTire);
                frontLeftTire = view.findViewById(R.id.frontLeftTire);
                // TODO:add the ring resource
                backRightRing = view.findViewById(R.id.backRightRing);
                backLeftRing = view.findViewById(R.id.backLeftRing);
                frontRightRing = view.findViewById(R.id.frontRightRing);
                frontLeftRing = view.findViewById(R.id.frontLeftRing);
                break;
            case "Jeep":
            case "جيب":
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
                backRightTire = view.findViewById(R.id.jeepBackRightTire);
                backLeftTire = view.findViewById(R.id.jeepBackLeftTire);
                frontRightTire = view.findViewById(R.id.jeepFrontRightTire);
                frontLeftTire = view.findViewById(R.id.jeepFrontLeftTire);
                // TODO:add the ring resource
                backRightRing = view.findViewById(R.id.jeepBackRightRing);
                backLeftRing = view.findViewById(R.id.jeepBackLeftRing);
                frontRightRing = view.findViewById(R.id.jeepFrontRightRing);
                frontLeftRing = view.findViewById(R.id.jeepFrontLeftRing);
                break;
            case "Family":
            case "مركبة عائلية":
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
                backRightTire = view.findViewById(R.id.familyBackRightTire);
                backLeftTire = view.findViewById(R.id.familyBackLeftTire);
                frontRightTire = view.findViewById(R.id.familyFrontRightTire);
                frontLeftTire = view.findViewById(R.id.familyFrontLeftTire);
                // TODO:add the ring resource
                backRightRing = view.findViewById(R.id.familyBackRightRing);
                backLeftRing = view.findViewById(R.id.familyBackLeftRing);
                frontRightRing = view.findViewById(R.id.familyFrontRightRing);
                frontLeftRing = view.findViewById(R.id.familyFrontLeftRing);

                break;
            case "Van":
            case "شاحنة صغيرة":
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
                backRightTire = view.findViewById(R.id.vanBackRightTire);
                backLeftTire = view.findViewById(R.id.vanBackLeftTire);
                frontRightTire = view.findViewById(R.id.vanFrontRightTire);
                frontLeftTire = view.findViewById(R.id.vanFrontLeftTire);
                // TODO:add the ring resource
                backRightRing = view.findViewById(R.id.vanBackRightRing);
                backLeftRing = view.findViewById(R.id.vanBackLeftRing);
                frontRightRing = view.findViewById(R.id.vanFrontRightRing);
                frontLeftRing = view.findViewById(R.id.vanFrontLeftRing);

                break;
        }

        // Language handling
        mViewFlipper.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        String[] carTypes = getResources().getStringArray(R.array.types);
        SALOON = carTypes[0];
        JEEP = carTypes[1];
        FAMILY = carTypes[2];
        VAN = carTypes[3];

        carHasDamage = view.findViewById(R.id.carHasDamageValue);
        carIsUseable = view.findViewById(R.id.carIsUseableValue);
        carIsClean = view.findViewById(R.id.carIsCleanValue);
        Button editVehicleRecord = view.findViewById(R.id.vehicleRecordEdit);
        Button editVehicleUtilities = view.findViewById(R.id.vehicleUtilitiesEdit);
        Button editVehicleAccidentReport = view.findViewById(R.id.accidentReportEdit);
        Button next = view.findViewById(R.id.nextConfirmationRecord);
        Button backButton = view.findViewById(R.id.backConfirmationRecord);
        vehicleRecordImages = view.findViewById(R.id.vehicleImagesHandover);
        firstDot = view.findViewById(R.id.firstImageHandover);
        secondDot = view.findViewById(R.id.secondImageHandover);
        thirdDot = view.findViewById(R.id.thirdImageHandover);
        fourthDot = view.findViewById(R.id.fourthImageHandover);
        fifthDot = view.findViewById(R.id.fifthImageHandover);
        sixthDot = view.findViewById(R.id.sixthImageHandover);
        seventhDot = view.findViewById(R.id.seventhImageHandover);
        additionalNotes = view.findViewById(R.id.additionalNotesRecord);

        // Setting Confirmation Values
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        vehicleName.setText(vehicleRecord.getVehicleName());
        managedBy.setText(vehicleRecord.getReleasePersonName());
        driverName.setText(vehicleRecord.getDriverName());

//       remove visibility if there is no destination
        if(vehicleInRecord.getDestination() == null ||  vehicleInRecord.getDestination().equals("")) {

            vehicleDestinationConfirmation.setVisibility(View.GONE);
            destinationValueConfirmation.setVisibility(View.GONE);
        } else {
            destinationValueConfirmation.setText(vehicleInRecord.getDestination());
        }
        if(vehicleRecord.getCarTransaction().equals("MTR")) {
            driverTitle.setText(R.string.renalCompany);
        }else {
            driverTitle.setText(R.string.driver);
        }
        milage.setText(vehicleRecord.getMilage());

        mSeekBar.setProgress(vehicleRecord.getFuelLevel());
        vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoLeftSide());

        vehicleStatus();
        vehicleOperationReview();
        toolsStatusReview();
        damageReportReview();

//       Click implementation
//       Gallery click implementation
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

//        edit implementation
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

    @Override
    public void onStop() {
        super.onStop();
        Glide.with(getContext()).clear(vehicleRecordImages);
        vehicleRecordImages = null;
        carHasDamage = null;
        carIsClean = null;
        carIsUseable = null;
        front = null;
        frontLeft = null;
        frontRight = null;
        frontWindShield = null;
        backCeiling = null;
        frontCeiling = null;
        frontLeftDoor = null;
        frontRightDoor = null;
        backRightDoor = null;
        backLeftDoor = null;
        backLeft = null;
        backRight = null;
        back = null;
        backWindShield = null;
        frontRightTire = null;
        frontLeftTire = null;
        backRightTire = null;
        backLeftTire = null;
        CLighter = null;
        jack = null;
        floorMat = null;
        tools = null;
        wheelCap= null;
        spareTire = null;
        firstDot = null;
        secondDot = null;
        thirdDot = null;
        fourthDot = null;
        fifthDot = null;
        sixthDot = null;
        seventhDot = null;
        operation = null;
        additionalNotes = null;





    }

//    set the damage report values
    private void damageReportReview() {

        if (damageReportRecord.isBack()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    back.setImageResource(R.drawable.back_red);
                    break;
                case "Jeep":
                case "جيب":
                    back.setImageResource(R.drawable.jeep_back_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    back.setImageResource(R.drawable.family_back_red);
                    break;
            }
        }
        if (damageReportRecord.isBackRight()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    backRight.setImageResource(R.drawable.back_right_red);
                    break;
                case "Jeep":
                case "جيب":
                    backRight.setImageResource(R.drawable.jeep_back_right_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backRight.setImageResource(R.drawable.family_back_right_red);
                    break;
            }
        }
        if (damageReportRecord.isBackLeft()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    backLeft.setImageResource(R.drawable.back_left_red);
                    break;
                case "Jeep":
                    backLeft.setImageResource(R.drawable.jeep_back_left_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backLeft.setImageResource(R.drawable.family_back_left_red);
                    break;
            }
        }

        if (damageReportRecord.isBackWindShield()) {
            if (vehicleInRecord.getCarType().equals(SALOON) ||
                    vehicleInRecord.getCarType().equals(JEEP) ||
                    vehicleInRecord.getCarType().equals(FAMILY)) {
                switch (vehicleInRecord.getCarType()) {
                    case "Saloon":
                    case "صالون":
                        backWindShield.setImageResource(R.drawable.back_wind_shield_red);
                        break;
                    case "Jeep":
                    case "جيب":
                        backWindShield.setImageResource(R.drawable.jeep_back_red);
                        break;
                    case "Family":
                    case "مركبة عائلية":
                        backWindShield.setImageResource(R.drawable.family_back_red);
                        break;
                }
            }
        }

        if (damageReportRecord.isBackLeftDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    backLeftDoor.setImageResource(R.drawable.back_door_left_red);
                    break;
                case "Jeep":
                case "جيب":
                    backLeftDoor.setImageResource(R.drawable.jeep_back_left_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backLeftDoor.setImageResource(R.drawable.family_back_left_door_red);
                    break;
            }
        }
        if (damageReportRecord.isBackRightDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    backRightDoor.setImageResource(R.drawable.back_door_right_red);
                    break;
                case "Jeep":
                case "جيب":
                    backRightDoor.setImageResource(R.drawable.jeep_back_right_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    backRightDoor.setImageResource(R.drawable.family_back_right_door_red);
                    break;
            }
        }
        if (damageReportRecord.isPassengerDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontRightDoor.setImageResource(R.drawable.front_right_door_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontRightDoor.setImageResource(R.drawable.jeep_front_right_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontRightDoor.setImageResource(R.drawable.family_front_right_door_red);
                    break;
            }
        }
        if (damageReportRecord.isDriverDoor()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontLeftDoor.setImageResource(R.drawable.jeep_front_left_door_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontLeftDoor.setImageResource(R.drawable.family_front_left_door_red);
                    break;
            }
        }
        if (vehicleInRecord.getCarType().equals(SALOON)) {
            if (damageReportRecord.isBackCeiling()) {
                backCeiling.setImageResource(R.drawable.back_red);
            }
        }
        if (damageReportRecord.isCeiling()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontCeiling.setImageResource(R.drawable.ceiling_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontCeiling.setImageResource(R.drawable.jeep_ceiling_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontCeiling.setImageResource(R.drawable.family_ceiling_red);
                    break;
            }
        }
        if (damageReportRecord.isFrontWindShield()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontWindShield.setImageResource(R.drawable.wind_sheild_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontWindShield.setImageResource(R.drawable.jeep_wind_sheild_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontWindShield.setImageResource(R.drawable.family_wind_sheild_red);
                    break;
            }

        }
        if (damageReportRecord.isFrontRight()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontRight.setImageResource(R.drawable.front_right_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontRight.setImageResource(R.drawable.jeep_front_right_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontRight.setImageResource(R.drawable.family_front_right_red);
                    break;
            }
        }
        if (damageReportRecord.isFrontLeft()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    frontLeft.setImageResource(R.drawable.front_left_red);
                    break;
                case "Jeep":
                case "جيب":
                    frontLeft.setImageResource(R.drawable.jeep_front_left_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    frontLeft.setImageResource(R.drawable.family_front_left_red);
                    break;
            }
        }
        if (damageReportRecord.isFront()) {
            switch (vehicleInRecord.getCarType()) {
                case "Saloon":
                case "صالون":
                    front.setImageResource(R.drawable.front_red);
                    break;
                case "Jeep":
                case "جيب":
                    front.setImageResource(R.drawable.jeep_front_red);
                    break;
                case "Family":
                case "Van":
                case "مركبة عائلية":
                case "شاحنة صغيرة":
                    front.setImageResource(R.drawable.family_front_red);
                    break;
            }

        }

        if (damageReportRecord.isFrontRightTire()) {
            frontRightTire.setImageResource(R.drawable.tire_red);

        }
        if (damageReportRecord.isFrontLeftTire()) {
            frontLeftTire.setImageResource(R.drawable.tire_red);
        }
        if (damageReportRecord.isBackLeftTire()) {
            backLeftTire.setImageResource(R.drawable.tire_red);
        }

        if (damageReportRecord.isBackRightTire()) {
            backRightTire.setImageResource(R.drawable.tire_red);
        }

        // TODO: fill the resource name
        if (damageReportRecord.isFrontRightRing()) {
            frontRightRing.setImageResource(R.drawable.ring_red);

        }
        if (damageReportRecord.isFrontLeftRing()) {
            frontLeftRing.setImageResource(R.drawable.ring_red);
        }
        if (damageReportRecord.isBackLeftRing()) {
            backLeftRing.setImageResource(R.drawable.ring_red);
        }

        if (damageReportRecord.isBackRightRing()) {
            backRightRing.setImageResource(R.drawable.ring_red);
        }





    }

//    set the value of the tools
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

//    mission value
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

//    set physical status of vehicle
    private void vehicleStatus() {
        if(vehicleRecord.isCarHasDamage()) {
            carHasDamage.setText(R.string.yes);
        }else {
            carHasDamage.setText(R.string.no);

        }
        if(vehicleRecord.isCarIsUseable()) {
            carIsUseable.setText(R.string.yes);
        }else {
            carIsUseable.setText(R.string.no);

        }

        if(vehicleRecord.isVehicleClean()) {
            carIsClean.setText(R.string.yes);
        }else {
            carIsClean.setText(R.string.no);

        }

    }

//    gallery implementation
    private void vehicleGallery() {
        if (index == 6) {
            index =0;
        } else {
            index++;
        }
        switch (index) {
            case 0:
                seventhDot.setImageResource(R.drawable.grey_dot);
                firstDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getPhotoLeftSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoLeftSide());
                break;
            case 1:
                firstDot.setImageResource(R.drawable.grey_dot);
                secondDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getPhotoRightSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoRightSide());
                break;
            case 2:
                secondDot.setImageResource(R.drawable.grey_dot);
                thirdDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getPhotoFrontSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoFrontSide());
                break;
            case 3:
                thirdDot.setImageResource(R.drawable.grey_dot);
                fourthDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getPhotoBackSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getPhotoBackSide());
                break;
            case 4:
                fourthDot.setImageResource(R.drawable.grey_dot);
                fifthDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getVehicleFrontInterior())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getVehicleFrontInterior());
                break;
            case 5:
                fifthDot.setImageResource(R.drawable.grey_dot);
                sixthDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getVehicleBackInterior())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getVehicleBackInterior());
                break;
            case 6:
                sixthDot.setImageResource(R.drawable.grey_dot);
                seventhDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecord.getVehicleTrunk())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecord.getVehicleTrunk());
                break;

        }

    }


}
