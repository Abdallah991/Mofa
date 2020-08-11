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

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");

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
       if (vehicleRecordDashboard.isCarHasDamage()) {
            db.collection("Damage Reports").whereEqualTo("damageReportName",vehicleRecordDashboard.getDamageReport())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    DamageReportDataModel damageReport = document.toObject(DamageReportDataModel.class);
                                    Log.d("WEIRD", damageReport.getCarId());
                                    if (damageReport.isBack()) {
                                        back.setImageResource(R.drawable.back_red);
                                    }
                                    if (damageReport.isBackRight()) {
                                        backRight.setImageResource(R.drawable.back_right_red);
                                    }
                                    if (damageReport.isBackLeft()) {
                                        backLeft.setImageResource(R.drawable.back_left_red);
                                    }
                                    if (damageReport.isBackWindShield()) {
                                        backWindShield.setImageResource(R.drawable.back_wind_shield_red);
                                    }
                                    if (damageReport.isBackLeftDoor()) {
                                        backLeftDoor.setImageResource(R.drawable.back_door_left_red);
                                    }
                                    if (damageReport.isBackRightDoor()) {
                                        backRightDoor.setImageResource(R.drawable.back_door_right_red);
                                    }
                                    if (damageReport.isPassengerDoor()) {
                                        frontRightDoor.setImageResource(R.drawable.front_right_door_red);
                                    }
                                    if (damageReport.isDriverDoor()) {
                                        frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
                                    }
                                    if (damageReport.isBackCeiling()) {
                                        backCeiling.setImageResource(R.drawable.ceiling_back_red);
                                    }
                                    if (damageReport.isCeiling()) {
                                        frontCeiling.setImageResource(R.drawable.ceiling_red);
                                    }
                                    if (damageReport.isFrontWindShield()) {
                                        frontWindShield.setImageResource(R.drawable.wind_sheild_red);

                                    }
                                    if (damageReport.isFrontRight()) {
                                        frontRight.setImageResource(R.drawable.front_right_red);
                                    }
                                    if (damageReport.isFrontLeft()) {
                                        frontLeft.setImageResource(R.drawable.front_left_red);
                                    }
                                    if (damageReport.isFront()) {
                                        front.setImageResource(R.drawable.front_red);

                                    }



                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }


                    });

            Log.d(TAG, " Loading the data is DONE");
        }
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
}
