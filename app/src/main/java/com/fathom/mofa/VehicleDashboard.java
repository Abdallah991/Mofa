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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.mofa.Adapters.VehiclesAdapter;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.ViewModels.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleDashboard extends Fragment {

    private String TAG = "Vehicle Dashboard";
    private ArrayList<VehicleDataModel> mVehicles = new ArrayList<>();
    private ArrayList<VehicleDataModel> filteredVehicles = new ArrayList<>();
    private RecyclerView mVehiclesRecycler;
    private VehiclesAdapter mVehiclesAdapter;
    private NavController mNavController;
    private VehicleViewModel mVehicleViewModel;
    private SearchView searchVehicles;
    private ImageView searchButton;
    private TextView numberOfVehicles;
    private ProgressDialog progressDialog;
    private int actionToVehicleDetail = R.id.action_vehicleDashboard_to_vehicleDetails;

    public VehicleDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVehiclesRecycler = view.findViewById(R.id.driverList);
        searchVehicles = view.findViewById(R.id.searchDriverRecord);
        searchButton = view.findViewById(R.id.searchDriver);
        numberOfVehicles = view.findViewById(R.id.numberOfRecordsDrivers);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mVehicleViewModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        mVehicleViewModel.initVehicles();
        mVehicleViewModel.getVehicles().observe(getViewLifecycleOwner(), new Observer<List<VehicleDataModel>>() {
            @Override
            public void onChanged(List<VehicleDataModel> vehicleDataModels) {
                Log.d(TAG, vehicleDataModels.size()+" ");
                mVehiclesAdapter.notifyDataSetChanged();
                initRecycler();
            }
        });

        searchVehicles.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                String searchText = searchVehicles.getQuery().toString();
                filter(searchText);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (s.isEmpty())
                {
                    mVehiclesAdapter.filterRecycler(mVehicles);
                }


                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchVehicles.getQuery().toString();
                filter(searchText);

            }
        });

        initRecycler();

    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleDashboard";

    }

    private void initRecycler() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();
        mVehicles = (ArrayList<VehicleDataModel>) mVehicleViewModel.getVehicles().getValue();

        mVehiclesAdapter = new VehiclesAdapter(mVehicles, getContext(), mNavController, actionToVehicleDetail, mVehicleViewModel);
        Log.d("help", String.valueOf(mVehicles));
        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVehiclesRecycler.setAdapter(mVehiclesAdapter);
                mVehiclesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                String vehicle = mVehicles.size()+" "+getResources().getString(R.string.vehicle);
                numberOfVehicles.setText(vehicle);
                progressDialog.dismiss();
            }


        },SPLASH_TIME_OUT);



    }

    private void filter(String searchText) {
        filteredVehicles = new ArrayList<>();

        for (VehicleDataModel vehicle : mVehicles) {
            if (vehicle.getPlateNumber().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicles.add(vehicle);
            }
        }
        if (!filteredVehicles.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleDataModel vehicle : mVehicles) {
            if (vehicle.getModel().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicles.add(vehicle);
            }

        }

        if (!filteredVehicles.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleDataModel vehicle : mVehicles) {
            if (vehicle.getMake().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicles.add(vehicle);
            }

        }

        if (!filteredVehicles.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleDataModel vehicle : mVehicles) {
            if (vehicle.getRentalInfoContent().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicles.add(vehicle);
            }

        }

        if (!filteredVehicles.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleDataModel vehicle : mVehicles) {
            if (vehicle.getColorOfCar().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicles.add(vehicle);
            }

        }

        if (!filteredVehicles.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleDataModel vehicle : mVehicles) {
            if (vehicle.getStatus().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicles.add(vehicle);
            }

        }

        if (!filteredVehicles.isEmpty()) {
            checkIfSearchIsValid();

        } else {
            mVehiclesAdapter.filterRecycler(mVehicles);
            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
        }


    }

    private void checkIfSearchIsValid() {
        if (!filteredVehicles.isEmpty()) {
            mVehiclesAdapter.filterRecycler(filteredVehicles);
        }
        else {
            mVehiclesAdapter.filterRecycler(mVehicles);
            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        numberOfVehicles = null;
        searchButton = null;
        searchVehicles = null;
        mVehiclesRecycler = null;
        mVehiclesAdapter = null;
        mVehicles = null;
        filteredVehicles = null;
        mVehicleViewModel = null;

    }
}
