package com.fathom.mofa;

import android.app.ProgressDialog;
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

import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRepository;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.fathom.mofa.ViewModels.UserViewModel;
import com.fathom.mofa.ViewModels.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRegistration.carPhotos;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRecord extends Fragment {
    private static final String TAG = "VEHICLES";
    private static final String TAG2 = "DRIVERS";
    private static final String TAG3 = "USERS";
    public static VehicleRecordDataModel vehicleRecord = new VehicleRecordDataModel();
    private NavController mNavController;
    private Spinner vehicleName;
    private Spinner userName;
    private Spinner driverName;
    private ImageView handover;
    private ImageView retrieval;
    private ImageView release;
    private ImageView carImages;
    private ImageView firstDot;
    private ImageView secondDot;
    private ImageView thirdDot;
    private ImageView fourthDot;
    private Button next;
    private ProgressDialog progressDialog;
    private int actionToVehicleUtilities = R.id.action_vehicleRecord_to_vehicleUtilities;
    // Vehicle Name
    private ArrayList<VehicleDataModel> mVehicles = new ArrayList<>();
    private VehicleViewModel mVehicleViewModel;
    private ArrayList<String> vehicleNames = new ArrayList<>();
    private ArrayAdapter<String> vehicleAdapter;

    // Driver Names
    private ArrayList<DriverDataModel> mDrivers = new ArrayList<>();
    private DriverViewModel mDriverViewModel;
    private ArrayList<String> driverNames = new ArrayList<>();
    private ArrayAdapter<String> driverAdapter;

    // User Names
    private ArrayList<UserDataModel> mUsers = new ArrayList<>();
    private UserViewModel mUserViewModel;
    private ArrayList<String> userNames = new ArrayList<>();
    private ArrayAdapter<String> userAdapter;

    // Vehicle Images
    private CarPhotosDataModel carPhotos = new CarPhotosDataModel();
    private int index = 0;

    // Vehicle Transaction
    private boolean handoverStatus =false;
    private boolean retrievalStatus =false;
    private boolean releaseStatus =false;

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
        handover = view.findViewById(R.id.handoverImage);
        retrieval = view.findViewById(R.id.retrievalImage);
        release = view.findViewById(R.id.releaseImage);
        carImages = view.findViewById(R.id.vehicleImagesHandover);
        firstDot = view.findViewById(R.id.firstImageHandover);
        secondDot = view.findViewById(R.id.secondImageHandover);
        thirdDot = view.findViewById(R.id.thirdImageHandover);
        fourthDot = view.findViewById(R.id.fourthImageHandover);
        next = view.findViewById(R.id.nextVehicleRecord);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        vehicleAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, vehicleNames);
        driverAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, driverNames);
        userAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, driverNames);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mVehicleViewModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
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
                if (index == 3) {
                    index = 0;
                } else {
                    index++;
                }
                switch (index) {
                    case 0:
                        fourthDot.setImageResource(R.drawable.grey_dot);
                        firstDot.setImageResource(R.drawable.red_dot);
                        carImages.setImageBitmap(carPhotos.getPhotoLeftSide());
                        break;
                    case 1:
                        firstDot.setImageResource(R.drawable.grey_dot);
                        secondDot.setImageResource(R.drawable.red_dot);
                        carImages.setImageBitmap(carPhotos.getPhotoRightSide());
                        break;
                    case 2:
                        secondDot.setImageResource(R.drawable.grey_dot);
                        thirdDot.setImageResource(R.drawable.red_dot);
                        carImages.setImageBitmap(carPhotos.getPhotoFrontSide());
                        break;
                    case 3:
                        thirdDot.setImageResource(R.drawable.grey_dot);
                        fourthDot.setImageResource(R.drawable.red_dot);
                        carImages.setImageBitmap(carPhotos.getPhotoBackSide());
                        break;

                }
            }
        });

        handover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handoverStatus) {
                    handover.setImageResource(R.drawable.empty_check_box);
                    handoverStatus = false;
                } else {
                    handoverStatus = true;
                    retrievalStatus = false;
                    releaseStatus = false;
                    handover.setImageResource(R.drawable.checked_check_box);
                    retrieval.setImageResource(R.drawable.empty_check_box);
                    release.setImageResource(R.drawable.empty_check_box);
                    vehicleRecord.setCarTransaction("MTD");
                }
            }
        });

        retrieval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retrievalStatus) {
                    retrieval.setImageResource(R.drawable.empty_check_box);
                    retrievalStatus = false;
                } else {
                    retrievalStatus = true;
                    handoverStatus = false;
                    releaseStatus = false;
                    retrieval.setImageResource(R.drawable.checked_check_box);
                    handover.setImageResource(R.drawable.empty_check_box);
                    release.setImageResource(R.drawable.empty_check_box);
                    vehicleRecord.setCarTransaction("DTM");
                }
            }
        });

        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (releaseStatus) {
                    release.setImageResource(R.drawable.empty_check_box);
                    releaseStatus = false;
                } else {
                    releaseStatus = true;
                    handoverStatus = false;
                    retrievalStatus = false;
                    release.setImageResource(R.drawable.checked_check_box);
                    handover.setImageResource(R.drawable.empty_check_box);
                    retrieval.setImageResource(R.drawable.empty_check_box);
                    vehicleRecord.setCarTransaction("MTR");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (getVehicleRecordInfo()) {
                    mNavController.navigate(actionToVehicleUtilities);
//                }
            }
        });

    }


    private void initVehicles() {
        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();

        Log.d(TAG, "loading Recycler been called");
        progressDialog.show();
        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mVehicles.isEmpty()) {
                    mVehicles = (ArrayList<VehicleDataModel>) mVehicleViewModel.getVehicles().getValue();
                    vehicleNames.add("Vehicle Name");
                    for (VehicleDataModel vehicle : mVehicles) {
                        vehicleNames.add(vehicle.getCarName());
                    }
                }
                vehicleAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, vehicleNames){
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(position == 0){
                            // Set the hint text color gray
                            tv.setTextColor(getResources().getColor(R.color.appGrey));
                        }
                        else {
                            tv.setTextColor(getResources().getColor(R.color.black));
                        }
                        return view;
                    }
                };
                vehicleAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                vehicleName.setAdapter(vehicleAdapter);
                vehicleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);

                        if(position > 0){
                            // Notify the selected item text
                            vehicleRecord.setVehicleName(selectedItemText);
                            final VehicleRepository repo = new VehicleRepository();
                            Handler myHandler;
                            int SPLASH_TIME_OUT = 5000;
                            myHandler = new Handler();
                            progressDialog.show();
                            carPhotos = repo.getImage(mVehicles.get(position-1).getPlateNumber());
                            myHandler.postDelayed(new Runnable() {
                                public void run() {
//                                    Toast.makeText(getContext(), "Name of left Vehicle Image " + carPhotos.getPhotoLeftSide(), Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, carPhotos.getPhotoLeftSide().toString());
                                    progressDialog.dismiss();
                                    setVehicleImages();
                                }

                                                  },SPLASH_TIME_OUT);



                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                progressDialog.dismiss();
            }
        }, SPLASH_TIME_OUT);

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
                    mDrivers = (ArrayList<DriverDataModel>) mDriverViewModel.getDrivers().getValue();
                    driverNames.add("Driver Name");
                    for (DriverDataModel driver : mDrivers) {
                        driverNames.add(driver.getDriverName());
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
//                            Toast.makeText(getContext(), selectedItemText, Toast.LENGTH_SHORT).show();
                            vehicleRecord.setDriverName(selectedItemText);
                            // Notify the selected item text
//                            final VehicleRepository repo = new VehicleRepository();
//                            Handler myHandler;
//                            int SPLASH_TIME_OUT = 4000;
//                            myHandler = new Handler();
//                            progressDialog.show();
//                            carPhotos = repo.getImage(mVehicles.get(position-1).getPlateNumber());
//                            myHandler.postDelayed(new Runnable() {
//                                public void run() {
//                                    Toast.makeText(getContext(), "Name of left Vehicle Image " + carPhotos.getPhotoLeftSide(), Toast.LENGTH_SHORT).show();
////                                    Log.d(TAG, carPhotos.getPhotoLeftSide().toString());
//                                    progressDialog.dismiss();
//                                    setVehicleImages();
//                                }
//
//                            },SPLASH_TIME_OUT);


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

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();

        Log.d(TAG3, "loading Recycler been called");
        progressDialog.show();
        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mUsers.isEmpty()) {
                    mUsers = (ArrayList<UserDataModel>) mUserViewModel.getUsers().getValue();
                    userNames.add("Managed By");
                    for (UserDataModel user : mUsers) {
                        userNames.add(user.getFirstName()+" "+user.getLastName());
                    }
                }
                userAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, userNames) {
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
                userAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                userName.setAdapter(userAdapter);
                userName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);

                        if (position > 0) {
//                            Toast.makeText(getContext(), selectedItemText, Toast.LENGTH_SHORT).show();
                            vehicleRecord.setReleasePersonName(selectedItemText);
                            // Notify the selected item text
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


    private void setVehicleImages(){
        carImages.setImageBitmap(carPhotos.getPhotoLeftSide());
    }

    private boolean getVehicleRecordInfo() {
        String vehicle = vehicleRecord.getVehicleName();
        String user = vehicleRecord.getReleasePersonName();
        String driver = vehicleRecord.getDriverName();
        String transaction = vehicleRecord.getCarTransaction();

        if ((vehicle != null ) && (user != null ) &&
                (driver != null ) && (transaction != null )) {
            return true;
        } else {

                Toast.makeText(getContext(), "Please fill the missing fields", Toast.LENGTH_SHORT).show();
            return false;
            }


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleRecord";
    }
}
