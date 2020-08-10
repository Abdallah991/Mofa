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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.RentalInfoDataModel;
import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;

import static com.fathom.mofa.Adapters.VehiclesAdapter.vehicleDashboard;
import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleDetails extends Fragment {

    public static CarPhotosDataModel carPhotosRecord = new CarPhotosDataModel();
    private String TAG = "VEHICLE DETAIL";
    private NavController mNavController;
    private TextView vehicleName;
    private ImageView vehicleInfoCollapse;
    private LinearLayout vehicleInfoLayout;
    private ImageView rentalInfoCollapse;
    private LinearLayout rentalInfoLayout;
    private TextView plateNumber;
    private TextView manufacturer;
    private TextView model;
    private TextView color;
    private TextView make;
    private TextView carType;
    private TextView registrationCompany;
    private TextView registrationType;
    private TextView registrationStart;
    private TextView registrationEnd;
    private Button editVehicleInfo;
    private Button editRentalInfo;
    private Button handoverOrRetrieveOrRelease;
    private Button vehicleHistory;
    private ImageView vehicleDetailImage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean toggleVehicleInfo = true;
    private boolean toggleRentalInfo = false;
    private ProgressDialog progressDialog;
    private int actionToVehicleRecord = R.id.action_vehicleDetails_to_vehicleRecord2;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");


    public VehicleDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleName = view.findViewById(R.id.carNameTitle);
        vehicleInfoCollapse = view.findViewById(R.id.vehicleInformationToggle);
        vehicleInfoLayout = view.findViewById(R.id.vehicleInformationCollapse);
        rentalInfoCollapse = view.findViewById(R.id.rentalInfoToggle);
        rentalInfoLayout = view.findViewById(R.id.rentalInfoCollapse);
        plateNumber = view.findViewById(R.id.plateNumberDetail);
        manufacturer = view.findViewById(R.id.manufacturerDetail);
        model = view.findViewById(R.id.modelDetail);
        color = view.findViewById(R.id.colorDetail);
        make = view.findViewById(R.id.makeDetail);
        carType = view.findViewById(R.id.carTypeDetail);
        registrationCompany = view.findViewById(R.id.rentalNameDetail);
        registrationType = view.findViewById(R.id.registrationTypeDetail);
        registrationStart = view.findViewById(R.id.registrationStartDetail);
        registrationEnd = view.findViewById(R.id.registrationEndDetail);
        handoverOrRetrieveOrRelease = view.findViewById(R.id.handoverOrRetrieval);
        vehicleHistory = view.findViewById(R.id.vehicleHistory);
        vehicleDetailImage = view.findViewById(R.id.vehicleDetailsImage);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        vehicleName.setText(vehicleDashboard.getManufacturer()+ " "+ vehicleDashboard.getModel());
        plateNumber.setText(vehicleDashboard.getPlateNumber());
        manufacturer.setText(vehicleDashboard.getManufacturer());
        model.setText(vehicleDashboard.getModel());
        color.setText(vehicleDashboard.getColorOfCar());
        make.setText(vehicleDashboard.getMake());
        carType.setText(vehicleDashboard.getCarType());
        registrationType.setText(vehicleDashboard.getRegistrationType());
        String registrationStarts = formatter.format(vehicleDashboard.getRegistrationStart());
        String registrationEnds = formatter.format(vehicleDashboard.getRegistrationEnd());
        registrationStart.setText(registrationStarts);
        registrationEnd.setText(registrationEnds);

        switch (vehicleDashboard.getStatus()) {
            case "Busy":
                handoverOrRetrieveOrRelease.setText(R.string.retrieve);
                vehicleHistory.setText(R.string.release);
                break;
            case "Returned":
                handoverOrRetrieveOrRelease.setText(R.string.handover);
                vehicleHistory.setText(R.string.release);
                break;
            case "Released":
                handoverOrRetrieveOrRelease.setText(R.string.retrieve);
                vehicleHistory.setVisibility(View.GONE);
                break;
        }

        handoverOrRetrieveOrRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToVehicleRecord);

            }
        });

        vehicleHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vehicleInfoCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleVehicleInfo) {
                    vehicleInfoCollapse.setImageResource(R.drawable.arrow_down);
                    vehicleInfoLayout.setVisibility(View.GONE);
                    toggleVehicleInfo = false;
                } else {
                    vehicleInfoCollapse.setImageResource(R.drawable.arrow_up);
                    vehicleInfoLayout.setVisibility(View.VISIBLE);
                    toggleVehicleInfo = true;
                    rentalInfoCollapse.setImageResource(R.drawable.arrow_down);
                    rentalInfoLayout.setVisibility(View.GONE);
                    toggleRentalInfo = false;
                }
            }
        });

        rentalInfoCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleRentalInfo) {
                    rentalInfoCollapse.setImageResource(R.drawable.arrow_down);
                    rentalInfoLayout.setVisibility(View.GONE);
                    toggleRentalInfo = false;
                } else {
                    rentalInfoCollapse.setImageResource(R.drawable.arrow_up);
                    rentalInfoLayout.setVisibility(View.VISIBLE);
                    toggleRentalInfo = true;
                    vehicleInfoCollapse.setImageResource(R.drawable.arrow_down);
                    vehicleInfoLayout.setVisibility(View.GONE);
                    toggleVehicleInfo = false;
                }
            }
        });

        getRentalInfo();
        getVehicleImages();



    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleDetails";

    }

    private void getRentalInfo() {

        db.collection("Rental Information").document(vehicleDashboard.getPlateNumber())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                         if (task.isSuccessful()) {
                             DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                RentalInfoDataModel rentalInfo = document.toObject(RentalInfoDataModel.class);
                                registrationCompany.setText(rentalInfo.getName());
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
        carPhotosRecord = repo.getImage(vehicleDashboard.getPlateNumber());
        myHandler.postDelayed(new Runnable() {
            public void run() {
//                                    Toast.makeText(getContext(), "Name of left Vehicle Image " + carPhotos.getPhotoLeftSide(), Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, carPhotos.getPhotoLeftSide().toString());
                progressDialog.dismiss();
                setVehicleImages();
            }

        },SPLASH_TIME_OUT);

    }

    private void setVehicleImages(){
        vehicleDetailImage.setImageBitmap(carPhotosRecord.getPhotoLeftSide());
    }
}
