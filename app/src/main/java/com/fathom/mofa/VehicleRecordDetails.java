package com.fathom.mofa;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.DamageReportDataModel;
import com.fathom.mofa.DataModels.RentalInfoDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.fathom.mofa.Adapters.VehicleRecordsAdapter.vehicleRecordDashboard;
import static com.fathom.mofa.Adapters.VehiclesAdapter.vehicleDashboard;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRecord.damageReportRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRecordDetails extends Fragment {

    private String TAG = "VEHICLE RECORD DETAIL";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
    // view flipper
    private ViewFlipper mViewFlipper;
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
    private ProgressDialog progressDialog;
    private static CarPhotosDataModel carPhotosRecordDetail = new CarPhotosDataModel();
    private static DamageReportDataModel damageReport = new DamageReportDataModel();
    private int index = 0;


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

        vehiclePlateNumber = view.findViewById(R.id.plateNumberRecordValue);
        model = view.findViewById(R.id.modelRecordValue);
        manufacturer = view.findViewById(R.id.manufacturerRecordValue);
        make = view.findViewById(R.id.makeRecordValue);
        color = view.findViewById(R.id.colorRecordValue);
        company = view.findViewById(R.id.companyRecordValue);
        registrationType = view.findViewById(R.id.registrationTypeRecordValue);
        registrationStart = view.findViewById(R.id.registrationStartRecordValue);
        registrationEnd = view.findViewById(R.id.registrationEndRecordValue);
        provider = view.findViewById(R.id.providerRecordValue);
        providerPhoneNumber = view.findViewById(R.id.providerPhoneNumberValue);
        leaseFrom = view.findViewById(R.id.leaseFromRecordValue);
        leaseTo = view.findViewById(R.id.leaseToRecordValue);
        assignedDriver = view.findViewById(R.id.driverRecordValue);
        status = view.findViewById(R.id.statusRecordValue);
        notes = view.findViewById(R.id.notesRecordValue);
        backButton = view.findViewById(R.id.backConfirmationRecord);
        vehicleRecordImages = view.findViewById(R.id.vehicleImagesRecord);
        firstDot = view.findViewById(R.id.firstImageRecord);
        secondDot = view.findViewById(R.id.secondImageRecord);
        thirdDot = view.findViewById(R.id.thirdImageRecord);
        fourthDot = view.findViewById(R.id.fourthImageRecord);
        // Vehicle Damage report
        mViewFlipper = view.findViewById(R.id.vehicleRecordDetailsViewFlipper);
        switch (vehicleRecordDashboard.getCarType()) {
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


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        vehiclePlateNumber.setText(vehicleRecordDashboard.getPlateNumber());
        model.setText(vehicleRecordDashboard.getModel());
        make.setText(vehicleRecordDashboard.getMake());
        assignedDriver.setText(vehicleRecordDashboard.getDriverName());
        status.setText(vehicleRecordDashboard.getStatus());
        notes.setText(vehicleRecordDashboard.getNotes());
        getRentalInfo();
        getVehicleInfo();
        getDamageReport();
        getVehicleImages();

        vehicleRecordImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleGallery();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();
            }
        });

    }

    @Override
    public void onResume() {
        FRAGMENT = "RecordDetails";
        super.onResume();
    }


    private void getDamageReport() {

            db.collection("Damage Reports").whereEqualTo("damageReportName",vehicleRecordDashboard.getDamageReport())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    DamageReportDataModel damageReport = document.toObject(DamageReportDataModel.class);
                                    Log.d("WEIRD", damageReport.getCarId());
                                    Toast.makeText(getContext(), damageReport.getCarId(), Toast.LENGTH_SHORT).show();
                                    if (damageReport.isBack()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isBackRight()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isBackLeft()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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

                                    if (damageReport.isBackWindShield()) {
                                        if (vehicleRecordDashboard.getCarType().equals("Saloon") ||
                                                vehicleRecordDashboard.getCarType().equals("Jeep") ||
                                                vehicleRecordDashboard.getCarType().equals("Family")) {
                                            switch (vehicleRecordDashboard.getCarType()) {
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

                                    if (damageReport.isBackLeftDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isBackRightDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isPassengerDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isDriverDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (vehicleRecordDashboard.getCarType().equals("Saloon")) {
                                        if (damageReport.isBackCeiling()) {
                                            backCeiling.setImageResource(R.drawable.ceiling_back_red);
                                        }
                                    }
                                    if (damageReport.isCeiling()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFrontWindShield()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFrontRight()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFrontLeft()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFront()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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

//                                    damageReportReview(damageReport);

                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }


                    });

            Log.d(TAG, " Loading the data is DONE");
        }
    

    private void getRentalInfo() {
        db.collection("Rental Information").document(vehicleRecordDashboard.getPlateNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                RentalInfoDataModel rentalInfo = document.toObject(RentalInfoDataModel.class);
                                provider.setText(rentalInfo.getName());
                                providerPhoneNumber.setText(rentalInfo.getPhoneNumber());
                                leaseFrom.setText(rentalInfo.getLeaseFrom().toString());
                                leaseTo.setText(rentalInfo.getLeaseTo().toString());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }


                });

        Log.d(TAG, " Loading the data is DONE");

    }

    private void getVehicleInfo() {
        db.collection("Vehicles").document(vehicleRecordDashboard.getPlateNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                VehicleDataModel vehicle = document.toObject(VehicleDataModel.class);
                                manufacturer.setText(vehicle.getManufacturer());
                                color.setText(vehicle.getColorOfCar());
                                registrationType.setText(vehicle.getRegistrationType());
                                registrationStart.setText(vehicle.getRegistrationStart().toString());
                                registrationEnd.setText(vehicle.getRegistrationEnd().toString());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }


                });

        Log.d(TAG, " Loading the data is DONE");



    }

    private void getVehicleImages() {

        final VehicleRepository repo = new VehicleRepository();
        Handler myHandler;
        int SPLASH_TIME_OUT = 5000;
        myHandler = new Handler();
        progressDialog.show();
        if (vehicleRecordDashboard.isCarHasDamage()) {

            carPhotosRecordDetail = repo.getImage(vehicleRecordDashboard.getDamageReport());
        }
        else {
            carPhotosRecordDetail = repo.getImage(vehicleRecordDashboard.getPlateNumber());

        }
        myHandler.postDelayed(new Runnable() {
            public void run() {
//                                    Toast.makeText(getContext(), "Name of left Vehicle Image " + carPhotos.getPhotoLeftSide(), Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, carPhotos.getPhotoLeftSide().toString());
                progressDialog.dismiss();
                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoLeftSide());
            }

        },SPLASH_TIME_OUT);



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
                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoLeftSide());
                break;
            case 1:
                firstDot.setImageResource(R.drawable.grey_dot);
                secondDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoRightSide());
                break;
            case 2:
                secondDot.setImageResource(R.drawable.grey_dot);
                thirdDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoFrontSide());
                break;
            case 3:
                thirdDot.setImageResource(R.drawable.grey_dot);
                fourthDot.setImageResource(R.drawable.red_dot);
                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoBackSide());
                break;

        }

    }

    private void damageReportReview(DamageReportDataModel damageReport) {

        if (damageReport.isBack()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isBackRight()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isBackLeft()) {
            switch (vehicleRecordDashboard.getCarType()) {
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

        if (damageReport.isBackWindShield()) {
            if (vehicleRecordDashboard.getCarType().equals("Saloon") ||
                    vehicleRecordDashboard.getCarType().equals("Jeep") ||
                    vehicleRecordDashboard.getCarType().equals("Family")) {
                switch (vehicleRecordDashboard.getCarType()) {
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

        if (damageReport.isBackLeftDoor()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isBackRightDoor()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isPassengerDoor()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isDriverDoor()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (vehicleRecordDashboard.getCarType().equals("Saloon")) {
            if (damageReport.isBackCeiling()) {
                backCeiling.setImageResource(R.drawable.ceiling_back_red);
            }
        }
        if (damageReport.isCeiling()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isFrontWindShield()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isFrontRight()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isFrontLeft()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
        if (damageReport.isFront()) {
            switch (vehicleRecordDashboard.getCarType()) {
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
}
