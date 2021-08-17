package com.fathom.mofa;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.mofa.Adapters.DriverRecordsAdaptor;
import com.fathom.mofa.Adapters.VehicleRecordsAdapter;
import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.fathom.mofa.ViewModels.VehicleRecordViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.MainActivity.FRAGMENT;


public class DriverRecord extends Fragment {
    private RecyclerView mDriverRecycler;
    private SearchView searchDriverRecords;
    private TextView numberOfRecords;
    private ArrayList<DriverDataModel> mDriverRecords = new ArrayList<>();
    private DriverViewModel mDriverRecordViewModel;
    private DriverRecordsAdaptor mDriverRecordAdaptor;
    private ProgressDialog progressDialog;
    private NavController mNavController;
    private ArrayList<DriverDataModel> filteredDriverRecords = new ArrayList<>();

    public DriverRecord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_lists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDriverRecycler = view.findViewById(R.id.driverList);
        searchDriverRecords = view.findViewById(R.id.searchDriverRecord);
        ImageView searchButton = view.findViewById(R.id.searchDriver);
        numberOfRecords = view.findViewById(R.id.numberOfRecordsDrivers);

        // Filter dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_driver_lists);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");
        // loading spinner Arrays
//Progress indicator


        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        mDriverRecordViewModel = new ViewModelProvider(requireActivity()).get(DriverViewModel.class);
        mDriverRecordViewModel.initDrivers();
        mDriverRecordViewModel.getDrivers().observe(getViewLifecycleOwner(), new Observer<List<DriverDataModel>>() {
            @Override
            public void onChanged(List<DriverDataModel> vehicleDataModels) {
                Log.d("T", vehicleDataModels.size() + " ");
                mDriverRecordAdaptor.notifyDataSetChanged();
                initRecycler();
            }
        });
        searchDriverRecords.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                String searchText = searchDriverRecords.getQuery().toString();
                filter(searchText);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (s.isEmpty()) {
                    mDriverRecordAdaptor.filterDrivers(mDriverRecords);
                }


                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchDriverRecords.getQuery().toString();
                filter(searchText);

            }
        });


//        initRecycler();
    }
    public void onResume() {
        super.onResume();
        FRAGMENT = "DriverRecord";

    }
    private void initRecycler() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();
        mDriverRecords = (ArrayList<DriverDataModel>) mDriverRecordViewModel.getDrivers().getValue();

        int actionToVehicleRecordDetail = R.id.action_dashboard_to_vehicleRecordDetails;
        mDriverRecordAdaptor = new DriverRecordsAdaptor(mDriverRecords, getContext(), mNavController, actionToVehicleRecordDetail, mDriverRecordViewModel);
        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDriverRecycler.setAdapter(mDriverRecordAdaptor);
                mDriverRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                String record = getResources().getString(R.string.records);
                numberOfRecords.setText(mDriverRecords.size() +" "+ record);
                progressDialog.dismiss();
            }


        }, SPLASH_TIME_OUT);


    }



    private void checkIfSearchIsValid() {
        {
            mDriverRecordAdaptor.filterDrivers(mDriverRecords);
//            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
        }
    }
    private void filter(String searchText) {
        filteredDriverRecords = new ArrayList<>();

        for (DriverDataModel driverRecord : mDriverRecords) {
            if (driverRecord.getDriverName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredDriverRecords.add(driverRecord);
            }
        }
        if (!filteredDriverRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (DriverDataModel driverRecord : mDriverRecords) {
            if (driverRecord.getDriverID().toLowerCase().contains(searchText.toLowerCase())) {
                filteredDriverRecords.add(driverRecord);
            }

        }

        if (!filteredDriverRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (DriverDataModel driverRecord : mDriverRecords) {
            if (driverRecord.getNationality().toLowerCase().contains(searchText.toLowerCase())) {
                filteredDriverRecords.add(driverRecord);
            }

        }

        if (!filteredDriverRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        } else {
            mDriverRecordAdaptor.filterDrivers(mDriverRecords);
            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onStop() {
        super.onStop();

        mDriverRecords = null;
        mDriverRecycler = null;
        mDriverRecordAdaptor = null;
        mDriverRecordViewModel = null;
        numberOfRecords = null;


    }}
