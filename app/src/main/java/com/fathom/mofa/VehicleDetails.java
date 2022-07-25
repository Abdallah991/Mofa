package com.fathom.mofa;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.DamageReportDataModel;
import com.fathom.mofa.DataModels.RentalInfoDataModel;
import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;

import static com.fathom.mofa.Adapters.VehiclesAdapter.vehicleDashboard;
import static com.fathom.mofa.LoginActivity.USER;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRecord.vehicleInRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleDetails extends Fragment {

//     declare variables
    public static CarPhotosDataModel carPhotosRecord = new CarPhotosDataModel();
    private String TAG = "VEHICLE DETAIL";
    private NavController mNavController;
    private ImageView vehicleInfoCollapse;
    private LinearLayout vehicleInfoLayout;
    private ImageView rentalInfoCollapse;
    private LinearLayout rentalInfoLayout;
    private TextView registrationCompany;
    private Button editVehicleInfo;
    private Button editRentalInfo;
    private ImageView vehicleDetailImage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean toggleVehicleInfo = true;
    private boolean toggleRentalInfo = false;
    private ProgressDialog progressDialog;
    public static VehicleRecordDataModel vehicleRecord = new VehicleRecordDataModel();
    public static DamageReportDataModel damageReportRecord = new DamageReportDataModel();
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

//         link UI variables
        TextView vehicleName = view.findViewById(R.id.carNameTitle);
        vehicleInfoCollapse = view.findViewById(R.id.vehicleInformationToggle);
        vehicleInfoLayout = view.findViewById(R.id.vehicleInformationCollapse);
        LinearLayout registrationStartLayout = view.findViewById(R.id.registrationStartLayout);
        rentalInfoCollapse = view.findViewById(R.id.rentalInfoToggle);
        rentalInfoLayout = view.findViewById(R.id.rentalInfoCollapse);
        TextView plateNumber = view.findViewById(R.id.plateNumberDetail);
        TextView manufacturer = view.findViewById(R.id.manufacturerDetail);
        TextView model = view.findViewById(R.id.modelDetail);
        TextView color = view.findViewById(R.id.colorDetail);
        TextView make = view.findViewById(R.id.makeDetail);
        TextView carType = view.findViewById(R.id.carTypeDetail);
        registrationCompany = view.findViewById(R.id.rentalNameDetail);
        TextView registrationType = view.findViewById(R.id.registrationTypeDetail);
        TextView registrationStart = view.findViewById(R.id.registrationStartDetail);
        TextView registrationEnd = view.findViewById(R.id.registrationEndDetail);
        Button handoverOrRetrieveOrRelease = view.findViewById(R.id.handoverOrRetrieval);
        Button vehicleHistory = view.findViewById(R.id.vehicleHistory);
        vehicleDetailImage = view.findViewById(R.id.vehicleDetailsImage);

//         initialise progress dialog and controller
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");
        progressDialog.setCanceledOnTouchOutside(false);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

//         set values
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


        final SharedPreferences pref = getActivity().getSharedPreferences(USER, 0); // 0 - for private mode
        String userStatus = pref.getString("userStatus", "");

//         car status
        switch (vehicleDashboard.getStatus()) {
            case "Busy":
                handoverOrRetrieveOrRelease.setText(R.string.retrieve);
                if (userStatus.equals("Admin") || userStatus.equals("مشرف")) {
                    vehicleHistory.setText(R.string.release);
                }else {
                    vehicleHistory.setVisibility(View.GONE);
                }
                vehicleRecord.setCarTransaction("DTM");
                damageReportRecord.setCarTransaction("DTM");
                break;
            case "Returned":
                handoverOrRetrieveOrRelease.setText(R.string.handover);
                if (userStatus.equals("Admin") || userStatus.equals("مشرف")) {
                    vehicleHistory.setText(R.string.release);
                }else {
                    vehicleHistory.setVisibility(View.GONE);
                }
                vehicleRecord.setCarTransaction("MTD");
                damageReportRecord.setCarTransaction("MTD");
                break;
            case "Released":
                handoverOrRetrieveOrRelease.setText(R.string.retrieve);
                vehicleHistory.setVisibility(View.GONE);
                vehicleRecord.setCarTransaction("RTM");
                damageReportRecord.setCarTransaction("RTM");

                break;
        }

//        click implementation
        handoverOrRetrieveOrRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionToVehicleRecord);

            }
        });

        vehicleHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleRecord.setCarTransaction("MTR");
                damageReportRecord.setCarTransaction("MTR");
                mNavController.navigate(actionToVehicleRecord);
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

        SharedPreferences userPrefs = getActivity().getSharedPreferences(USER, 0);
        String lang = userPrefs.getString("Lang","");
//        Toast.makeText(getContext(), lang, Toast.LENGTH_SHORT).show();

        getRentalInfo();
        getVehicleImages();



    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleDetails";

    }

//     get rental company info
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

//    get vehicle images
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

//    set vehicle images
    private void setVehicleImages(){
        Glide.with(getContext())
                .load(carPhotosRecord.getPhotoLeftSide())
                .centerCrop()
                .into(vehicleDetailImage);//
    }

    @Override
    public void onStop() {
        super.onStop();
        Glide.with(getContext()).clear(vehicleDetailImage);
        vehicleDetailImage = null;
        vehicleInfoCollapse = null;
        vehicleInfoLayout = null;
        rentalInfoCollapse = null;
        rentalInfoLayout = null;
        registrationCompany = null;
        editVehicleInfo = null;
        editRentalInfo = null;


    }
}
