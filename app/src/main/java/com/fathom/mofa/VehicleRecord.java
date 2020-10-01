package com.fathom.mofa;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.RentalInfoDataModel;
import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.fathom.mofa.ViewModels.UserViewModel;
import com.fathom.mofa.ViewModels.VehicleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import static com.fathom.mofa.Adapters.VehiclesAdapter.vehicleDashboard;
import static com.fathom.mofa.VehicleDetails.vehicleRecord;
import static com.fathom.mofa.LoginActivity.USER;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleDetails.carPhotosRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRecord extends Fragment {
    private static final String TAG = "VEHICLES";
    private static final String TAG2 = "DRIVERS";
    private static final String TAG3 = "USERS";
    public static VehicleDataModel vehicleInRecord = new VehicleDataModel();
//    public static DriverDataModel driverInRecord = new DriverDataModel();
    private ArrayList<RentalInfoDataModel> rentalInformation = new ArrayList<>();
    private NavController mNavController;
    private TextView vehicleName;
    private TextView userName;
    private Spinner driverName;
    private ImageView carImages;
    private ImageView firstDot;
    private ImageView secondDot;
    private ImageView thirdDot;
    private ImageView fourthDot;
    private ImageView fifthDot;
    private ImageView sixthDot;
    private ImageView seventhDot;
    private ProgressDialog progressDialog;
    private int actionToVehicleUtilities = R.id.action_vehicleRecord_to_vehicleUtilities;
    // Vehicle Name
    private ArrayList<VehicleDataModel> mVehicles = new ArrayList<>();
    private int positionOfVehicle;
    private ArrayList<String> vehicleNames = new ArrayList<>();
    private ArrayAdapter<String> vehicleAdapter;

    // Driver Names
    private ArrayList<DriverDataModel> mDrivers = new ArrayList<>();
    private int positionOfDriver;
    private DriverViewModel mDriverViewModel;
    private ArrayList<String> driverNames = new ArrayList<>();
    private ArrayAdapter<String> driverAdapter;

    // User Names
    private ArrayList<UserDataModel> mUsers = new ArrayList<>();
    private UserViewModel mUserViewModel;
    private ArrayList<String> userNames = new ArrayList<>();
    private ArrayAdapter<String> userAdapter;

    // Vehicle Images
    private int index = 0;

    // Vehicle Transaction
    private boolean handoverStatus =false;
    private boolean retrievalStatus =false;
    private boolean releaseStatus =false;

    // getting Rental Info
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public VehicleRecord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleName = view.findViewById(R.id.vehicleName);
        userName = view.findViewById(R.id.managedBy);
        driverName = view.findViewById(R.id.driverName);
        carImages = view.findViewById(R.id.vehicleImagesHandover);
        firstDot = view.findViewById(R.id.firstImageHandover);
        secondDot = view.findViewById(R.id.secondImageHandover);
        thirdDot = view.findViewById(R.id.thirdImageHandover);
        fourthDot = view.findViewById(R.id.fourthImageHandover);
        fifthDot = view.findViewById(R.id.fifthImageHandover);
        sixthDot= view.findViewById(R.id.sixthImageHandover);
        seventhDot = view.findViewById(R.id.seventhImageHandover);
        Button next = view.findViewById(R.id.nextVehicleRecord);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");
        vehicleAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, vehicleNames);
        driverAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, driverNames);
        userAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, driverNames);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        VehicleViewModel mVehicleViewModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        mVehicleViewModel.initVehicles();
        mVehicleViewModel.getVehicles().observe(getViewLifecycleOwner(), new Observer<List<VehicleDataModel>>() {
            @Override
            public void onChanged(List<VehicleDataModel> vehicleDataModels) {
                Log.d(TAG, vehicleDataModels.size()+" ");
                vehicleAdapter.notifyDataSetChanged();
            }
        });

        mDriverViewModel = new ViewModelProvider(requireActivity()).get(DriverViewModel.class);
        mDriverViewModel.initDrivers();
        mDriverViewModel.getDrivers().observe(getViewLifecycleOwner(), new Observer<List<DriverDataModel>>() {
            @Override
            public void onChanged(List<DriverDataModel> driverDataModels) {
                Log.d(TAG2, driverDataModels.size()+" ");
                driverAdapter.notifyDataSetChanged();
            }
        });

        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mUserViewModel.initUsers();
        mUserViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<UserDataModel>>() {
            @Override
            public void onChanged(List<UserDataModel> userDataModels) {
                Log.d(TAG3, userDataModels.size()+" ");
                userAdapter.notifyDataSetChanged();
            }
        });



//        if(mVehicles.isEmpty()) {
        initVehicles();
        initDrivers();
        initUsers();
//        }

        carImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 6) {
                    index = 0;
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
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getPhotoLeftSide());
                        break;
                    case 1:
                        firstDot.setImageResource(R.drawable.grey_dot);
                        secondDot.setImageResource(R.drawable.red_dot);
                        Glide.with(getContext())
                                .load(carPhotosRecord.getPhotoRightSide())
                                .centerCrop()
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getPhotoRightSide());
                        break;
                    case 2:
                        secondDot.setImageResource(R.drawable.grey_dot);
                        thirdDot.setImageResource(R.drawable.red_dot);
                        Glide.with(getContext())
                                .load(carPhotosRecord.getPhotoFrontSide())
                                .centerCrop()
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getPhotoFrontSide());
                        break;
                    case 3:
                        thirdDot.setImageResource(R.drawable.grey_dot);
                        fourthDot.setImageResource(R.drawable.red_dot);
                        Glide.with(getContext())
                                .load(carPhotosRecord.getPhotoBackSide())
                                .centerCrop()
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getPhotoBackSide());
                        break;
                    case 4:
                        fourthDot.setImageResource(R.drawable.grey_dot);
                        fifthDot.setImageResource(R.drawable.red_dot);
                        Glide.with(getContext())
                                .load(carPhotosRecord.getVehicleFrontInterior())
                                .centerCrop()
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getVehicleFrontInterior());
                        break;
                    case 5:
                        fifthDot.setImageResource(R.drawable.grey_dot);
                        sixthDot.setImageResource(R.drawable.red_dot);
                        Glide.with(getContext())
                                .load(carPhotosRecord.getVehicleBackInterior())
                                .centerCrop()
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getVehicleBackInterior());
                        break;
                    case 6:
                        sixthDot.setImageResource(R.drawable.grey_dot);
                        seventhDot.setImageResource(R.drawable.red_dot);
                        Glide.with(getContext())
                                .load(carPhotosRecord.getVehicleTrunk())
                                .centerCrop()
                                .into(carImages);//
//                        carImages.setImageBitmap(carPhotosRecord.getVehicleTrunk());
                        break;

                }
            }
        });

//        handover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (handoverStatus) {
//                    handover.setImageResource(R.drawable.empty_check_box);
//                    handoverStatus = false;
//                } else {
//                    handoverStatus = true;
//                    retrievalStatus = false;
//                    releaseStatus = false;
//                    handover.setImageResource(R.drawable.checked_check_box);
//                    retrieval.setImageResource(R.drawable.empty_check_box);
//                    release.setImageResource(R.drawable.empty_check_box);
//                    vehicleRecord.setCarTransaction("MTD");
//                    damageReportRecord.setCarTransaction("MTD");
//                }
//            }
//        });

//        retrieval.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (retrievalStatus) {
//                    retrieval.setImageResource(R.drawable.empty_check_box);
//                    retrievalStatus = false;
//                } else {
//                    retrievalStatus = true;
//                    handoverStatus = false;
//                    releaseStatus = false;
//                    retrieval.setImageResource(R.drawable.checked_check_box);
//                    handover.setImageResource(R.drawable.empty_check_box);
//                    release.setImageResource(R.drawable.empty_check_box);
//                    vehicleRecord.setCarTransaction("DTM");
//                    damageReportRecord.setCarTransaction("DTM");
//                }
//            }
//        });

//        release.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (releaseStatus) {
//                    release.setImageResource(R.drawable.empty_check_box);
//                    releaseStatus = false;
//                } else {
//                    releaseStatus = true;
//                    handoverStatus = false;
//                    retrievalStatus = false;
//                    release.setImageResource(R.drawable.checked_check_box);
//                    handover.setImageResource(R.drawable.empty_check_box);
//                    retrieval.setImageResource(R.drawable.empty_check_box);
//                    vehicleRecord.setCarTransaction("MTR");
//                    damageReportRecord.setCarTransaction("MTR");
//                }
//            }
//        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getVehicleRecordInfo()) {
                    mNavController.navigate(actionToVehicleUtilities);
                }
            }
        });

        setVehicleInfo();

//        switch (vehicleDashboard.getStatus()) {
//            case "Busy":
//                retrieval.setImageResource(R.drawable.checked_check_box);
//                vehicleRecord.setCarTransaction("DTM");
//                damageReportRecord.setCarTransaction("DTM");
//                break;
//            case "Returned":
//                handover.setImageResource(R.drawable.checked_check_box);
//                vehicleRecord.setCarTransaction("MTD");
//                damageReportRecord.setCarTransaction("MTD");
//                break;
//            case "Released":
//                release.setImageResource(R.drawable.checked_check_box);
//                vehicleRecord.setCarTransaction("MTR");
//                damageReportRecord.setCarTransaction("MTR");
//
//                break;
//        }

    }


    private void initVehicles() {

        vehicleName.setText(vehicleDashboard.getCarName());
        setVehicleImages();


    }

    private void initDrivers() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();



            Log.d(TAG2, "loading Recycler been called");
            progressDialog.show();
            // showing the Splash screen for two seconds then going to on boarding activity
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mDrivers.isEmpty()) {
                        if (!vehicleRecord.getCarTransaction().equals("MTR")) {
                            mDrivers = (ArrayList<DriverDataModel>) mDriverViewModel.getDrivers().getValue();
                            String spinnerManagerBy = getResources().getString(R.string.driver);
                            driverNames.add(spinnerManagerBy);

                            for (DriverDataModel driver : mDrivers) {
                                driverNames.add(driver.getDriverName());
                            }
                        }else {
                            String spinnerRentalCompany = getResources().getString(R.string.renalCompany);
                            driverNames.add(spinnerRentalCompany);
                            getRentalInfo();
                        }
                    }
                    driverAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, driverNames) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(getResources().getColor(R.color.appGrey));
                            } else {
                                tv.setTextColor(getResources().getColor(R.color.black));
                            }
                            return view;
                        }
                    };
                    driverAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    driverName.setAdapter(driverAdapter);
                    driverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                            String selectedItemText = (String) parent.getItemAtPosition(position);

                            if (position > 0) {
                                // This code is to get the object of the selected Vehicle
                                positionOfDriver = position - 1;
//                                driverInRecord = mDrivers.get(positionOfDriver);
                                vehicleRecord.setDriverName(selectedItemText);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
//                progressDialog.dismiss();
                }
            }, SPLASH_TIME_OUT);

    }

    private void initUsers() {

        SharedPreferences pref = getActivity().getSharedPreferences(USER, 0); // 0 - for private mode
        final String email = pref.getString("Email", "");
//        final String Name = new String[1];

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();

        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mUsers.isEmpty()) {
                    mUsers = (ArrayList<UserDataModel>) mUserViewModel.getUsers().getValue();
                    userNames.add("Managed By");
                    for (UserDataModel user : mUsers) {
                        userNames.add(user.getFirstName()+" "+user.getLastName());
                        if (user.getEmail().equals(email)) {
                            userName.setText(user.getFirstName()+" "+user.getLastName());
                            vehicleRecord.setReleasePersonName(user.getFirstName()+" "+user.getLastName());
                        }
                    }
                }

                progressDialog.dismiss();
            }
        } ,SPLASH_TIME_OUT);

    }


    private void setVehicleImages(){
        Glide.with(getContext())
                .load(carPhotosRecord.getPhotoLeftSide())
                .centerCrop()
                .into(carImages);//
//        carImages.setImageBitmap(carPhotosRecord.getPhotoLeftSide());
    }

    private boolean getVehicleRecordInfo() {

        String user = vehicleRecord.getReleasePersonName();
        String driver = vehicleRecord.getDriverName();
        String transaction = vehicleRecord.getCarTransaction();
        vehicleRecord.setPlateNumber(vehicleInRecord.getPlateNumber());

        if ((user!=null) &&
                (driver != null ) && (transaction != null )) {
            return true;
        } else {

                Toast.makeText(getContext(), "Please fill the missing fields", Toast.LENGTH_SHORT).show();
            return false;
            }


    }

    private void setVehicleInfo() {

        vehicleInRecord.setStatus(vehicleDashboard.getStatus());
        vehicleInRecord.setRentalInfoContent(vehicleDashboard.getRentalInfoContent());
        vehicleInRecord.setRentalInfo(vehicleDashboard.getRentalInfo());
        vehicleInRecord.setPhotoBackSide(vehicleDashboard.getPhotoBackSide());
        vehicleInRecord.setPhotoFrontSide(vehicleDashboard.getPhotoFrontSide());
        vehicleInRecord.setPhotoRightSide(vehicleDashboard.getPhotoRightSide());
        vehicleInRecord.setPhotoLeftSide(vehicleDashboard.getPhotoLeftSide());
        vehicleInRecord.setVehicleFrontInterior(vehicleDashboard.getVehicleFrontInterior());
        vehicleInRecord.setVehicleBackInterior(vehicleDashboard.getVehicleBackInterior());
        vehicleInRecord.setVehicleTrunk(vehicleDashboard.getVehicleTrunk());
        vehicleInRecord.setCarName(vehicleDashboard.getCarName());
        vehicleInRecord.setRegistrationEnd(vehicleDashboard.getRegistrationEnd());
        vehicleInRecord.setRegistrationStart(vehicleDashboard.getRegistrationStart());
        vehicleInRecord.setRegistrationType(vehicleDashboard.getRegistrationType());
        vehicleInRecord.setMake(vehicleDashboard.getMake());
        vehicleInRecord.setManufacturer(vehicleDashboard.getManufacturer());
        vehicleInRecord.setModel(vehicleDashboard.getModel());
        vehicleInRecord.setPlateNumber(vehicleDashboard.getPlateNumber());
        vehicleInRecord.setColorOfCar(vehicleDashboard.getColorOfCar());
        vehicleInRecord.setCarType(vehicleDashboard.getCarType());
        vehicleInRecord.setChassisNumber(vehicleDashboard.getChassisNumber());
        vehicleInRecord.setMotorSize(vehicleDashboard.getMotorSize());
        vehicleRecord.setVehicleName(vehicleDashboard.getCarName());
        vehicleRecord.setCarType(vehicleDashboard.getCarType());
        vehicleRecord.setChassisNumber(vehicleDashboard.getChassisNumber());
        vehicleRecord.setMotorSize(vehicleDashboard.getMotorSize());


    }

    private void getRentalInfo() {
        db.collection("Rental Information")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("RENTAL INFORMATION", document.getId() + " => " + document.getData());
                                if (rentalInformation.size() <= task.getResult().size()) {
                                    RentalInfoDataModel rental = document.toObject(RentalInfoDataModel.class);
                                    rentalInformation.add(rental);
                                    driverNames.add(rental.getName());


                                }
                            }
                        } else {
                            Log.d("RENTAL INFORMATION", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleRecord";
    }

    @Override
    public void onStop() {
        super.onStop();
        driverName = null;
        userName = null;
        vehicleName = null;
        Glide.with(getContext()).clear(carImages);
        carImages = null;
        firstDot = null;
        secondDot = null;
        thirdDot = null;
        fourthDot = null;
        fifthDot = null;
        sixthDot = null;
        seventhDot = null;
        mVehicles = null;
        vehicleAdapter = null;
        vehicleNames = null;
        driverAdapter = null;
        mUserViewModel = null;
        userNames = null;
        userAdapter = null;

    }
}
