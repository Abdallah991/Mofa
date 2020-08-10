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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

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
    private RecyclerView mVehiclesRecycler;
    private VehiclesAdapter mVehiclesAdapter;
    private NavController mNavController;
    private VehicleViewModel mVehicleViewModel;
    private SearchView searchVehicles;
    private ImageView searchButton;
    private ImageView filterButton;
    private TextView numberOfRecords;
    private TextView numberOfPages;
    private Button backButton;
    private Button nextButton;
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

        mVehiclesRecycler = view.findViewById(R.id.vehiclesInDashboard);
        searchVehicles = view.findViewById(R.id.searchVehicle);
        searchButton = view.findViewById(R.id.searchVehicles);
        filterButton = view.findViewById(R.id.filterVehicles);
        numberOfRecords = view.findViewById(R.id.numberOfRecordsVehicles);
        numberOfPages = view.findViewById(R.id.numberOfPagesVehicles);
        backButton = view.findViewById(R.id.backButtonVehicles);
        nextButton = view.findViewById(R.id.nextButtonVehicles);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mVehicleViewModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        mVehicleViewModel.initVehicles();
        mVehicleViewModel.getVehicles().observe(getViewLifecycleOwner(), new Observer<List<VehicleDataModel>>() {
            @Override
            public void onChanged(List<VehicleDataModel> vehicleDataModels) {
                Log.d(TAG, vehicleDataModels.size()+" ");
                mVehiclesAdapter.notifyDataSetChanged();
                initRecycler();            }
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
        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVehiclesRecycler.setAdapter(mVehiclesAdapter);
                mVehiclesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                progressDialog.dismiss();
            }


        },SPLASH_TIME_OUT);



    }
}
