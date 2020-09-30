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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
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
import java.text.SimpleDateFormat;
import static com.fathom.mofa.Adapters.VehicleRecordsAdapter.vehicleRecordDashboard;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleDetails.damageReportRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRecordDetails extends Fragment {

    private String TAG = "VEHICLE RECORD DETAIL";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private NavController mNavController;
    private TextView vehiclePlateNumber;
    private TextView vin;
    private TextView engineSize;
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
    private ImageView frontRightTire;
    private ImageView frontLeftTire;
    private ImageView backRightTire;
    private ImageView backLeftTire;
    // view flipper
    private ViewFlipper mViewFlipper;
    private TextView provider;
    private TextView providerPhoneNumber;
    private TextView leaseFrom;
    private TextView leaseTo;
    private TextView assignedDriver;
    private TextView driverRecord;
    private TextView status;
    private TextView notes;
    private TextView clean;
    private Button backButton;
    private ImageView vehicleRecordImages;
    private ImageView firstDot;
    private ImageView secondDot;
    private ImageView thirdDot;
    private ImageView fourthDot;
    private ImageView fifthImageRecord;
    private ImageView sixthImageRecord;
    private ImageView seventhImageRecord;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");


    private ProgressDialog progressDialog;
    private static CarPhotosDataModel carPhotosRecordDetail = new CarPhotosDataModel();
    private static DamageReportDataModel damageReport = new DamageReportDataModel();
    private int index = 0;

    // MultiLanguage
    public static String SALOON;
    public static String JEEP;
    public static String FAMILY;
    public static String VAN;

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
        vin = view.findViewById(R.id.chassisNumberRecordValue);
        engineSize = view.findViewById(R.id.motorSizeRecordValue);
        model = view.findViewById(R.id.modelRecordValue);
        manufacturer = view.findViewById(R.id.manufacturerRecordValue);
        make = view.findViewById(R.id.makeRecordValue);
        clean = view.findViewById(R.id.cleanRecordValue);
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
        driverRecord = view.findViewById(R.id.driverRecord);
        status = view.findViewById(R.id.statusRecordValue);
        notes = view.findViewById(R.id.notesRecordValue);
        backButton = view.findViewById(R.id.backConfirmationRecord);
        vehicleRecordImages = view.findViewById(R.id.vehicleImagesRecord);
        firstDot = view.findViewById(R.id.firstImageRecord);
        secondDot = view.findViewById(R.id.secondImageRecord);
        thirdDot = view.findViewById(R.id.thirdImageRecord);
        fourthDot = view.findViewById(R.id.fourthImageRecord);
        fifthImageRecord = view.findViewById(R.id.fifthImageRecord);
        sixthImageRecord = view.findViewById(R.id.sixthImageRecord);
        seventhImageRecord = view.findViewById(R.id.seventhImageRecord);
        // Vehicle Damage report
        mViewFlipper = view.findViewById(R.id.vehicleRecordDetailsViewFlipper);
        switch (vehicleRecordDashboard.getCarType()) {
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
                break;
        }

        // Language handling
        mViewFlipper.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        String[] carTypes = getResources().getStringArray(R.array.types);
        SALOON = carTypes[0];
        JEEP = carTypes[1];
        FAMILY = carTypes[2];
        VAN = carTypes[3];


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");
        progressDialog.setCanceledOnTouchOutside(false);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        vehiclePlateNumber.setText(vehicleRecordDashboard.getPlateNumber());
        vin.setText(vehicleRecordDashboard.getChassisNumber());
        engineSize.setText(vehicleRecordDashboard.getMotorSize());
        vehiclePlateNumber.setText(vehicleRecordDashboard.getPlateNumber());
        model.setText(vehicleRecordDashboard.getModel());
        make.setText(vehicleRecordDashboard.getMake());
        assignedDriver.setText(vehicleRecordDashboard.getDriverName());
        if(vehicleRecordDashboard.getCarTransaction().equals("MTR")) {
            driverRecord.setText(R.string.renalCompany);
        }else {
            driverRecord.setText(R.string.driver);
        }
        status.setText(vehicleRecordDashboard.getStatus());
        notes.setText(vehicleRecordDashboard.getNotes());
        company.setText(vehicleRecordDashboard.getRentalInfo());
        if (vehicleRecordDashboard.isVehicleClean()) {
            clean.setText(R.string.yes);
        }else {
            clean.setText(R.string.no);
        }
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

    @Override
    public void onStop() {
        super.onStop();
        Glide.with(getContext()).clear(vehicleRecordImages);
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
                                    if (damageReport.isBack()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isBackRight()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isBackLeft()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
                                            case "Saloon":
                                            case "صالون":
                                                backLeft.setImageResource(R.drawable.back_left_red);
                                                break;
                                            case "Jeep":
                                            case "جيب":
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

                                    if (damageReport.isBackWindShield()) {
                                        if (vehicleRecordDashboard.getCarType().equals(SALOON) ||
                                                vehicleRecordDashboard.getCarType().equals(JEEP) ||
                                                vehicleRecordDashboard.getCarType().equals(FAMILY)) {
                                            switch (vehicleRecordDashboard.getCarType()) {
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
                                                case "شاحنة صغيرة":
                                                    backWindShield.setImageResource(R.drawable.family_back_red);
                                                    break;
                                            }
                                        }
                                    }

                                    if (damageReport.isBackLeftDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isBackRightDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isPassengerDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isDriverDoor()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (vehicleRecordDashboard.getCarType().equals(SALOON)) {
                                        if (damageReport.isBackCeiling()) {
                                            backCeiling.setImageResource(R.drawable.back_red);
                                        }
                                    }
                                    if (damageReport.isCeiling()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFrontWindShield()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFrontRight()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFrontLeft()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                    if (damageReport.isFront()) {
                                        switch (vehicleRecordDashboard.getCarType()) {
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
                                leaseFrom.setText(formatter.format(rentalInfo.getLeaseFrom()));
                                leaseTo.setText(formatter.format(rentalInfo.getLeaseTo()));
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
                                registrationStart.setText(formatter.format(vehicle.getRegistrationStart()));
                                registrationEnd.setText(formatter.format(vehicle.getRegistrationEnd()));
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

            carPhotosRecordDetail = repo.getImage(vehicleRecordDashboard.getPlateNumber()+vehicleRecordDashboard.getName());
        }
        else {
            carPhotosRecordDetail = repo.getImage(vehicleRecordDashboard.getPlateNumber());

        }
        myHandler.postDelayed(new Runnable() {
            public void run() {
//                                    Toast.makeText(getContext(), "Name of left Vehicle Image " + carPhotos.getPhotoLeftSide(), Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, carPhotos.getPhotoLeftSide().toString());
                progressDialog.dismiss();
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getPhotoLeftSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoLeftSide());
            }

        },SPLASH_TIME_OUT);



    }

    private void vehicleGallery() {
        if (index == 6) {
            index =0;
        } else {
            index++;
        }
        switch (index) {
            case 0:
                seventhImageRecord.setImageResource(R.drawable.grey_dot);
                firstDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getPhotoLeftSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoLeftSide());
                break;
            case 1:
                firstDot.setImageResource(R.drawable.grey_dot);
                secondDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getPhotoRightSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoRightSide());
                break;
            case 2:
                secondDot.setImageResource(R.drawable.grey_dot);
                thirdDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getPhotoFrontSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoFrontSide());
                break;
            case 3:
                thirdDot.setImageResource(R.drawable.grey_dot);
                fourthDot.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getPhotoBackSide())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getPhotoBackSide());
                break;
            case 4:
                fourthDot.setImageResource(R.drawable.grey_dot);
                fifthImageRecord.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getVehicleFrontInterior())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getVehicleFrontInterior());
                break;
            case 5:
                fifthImageRecord.setImageResource(R.drawable.grey_dot);
                sixthImageRecord.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getVehicleBackInterior())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getVehicleBackInterior());
                break;
            case 6:
                sixthImageRecord.setImageResource(R.drawable.grey_dot);
                seventhImageRecord.setImageResource(R.drawable.red_dot);
                Glide.with(getContext())
                        .load(carPhotosRecordDetail.getVehicleTrunk())
                        .centerCrop()
                        .into(vehicleRecordImages);//
//                vehicleRecordImages.setImageBitmap(carPhotosRecordDetail.getVehicleTrunk());
                break;

        }

    }
}
