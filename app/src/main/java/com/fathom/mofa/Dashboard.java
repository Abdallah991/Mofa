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

import com.fathom.mofa.Adapters.VehicleRecordsAdapter;
import com.fathom.mofa.Adapters.VehiclesAdapter;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.ViewModels.VehicleRecordViewModel;
import com.fathom.mofa.ViewModels.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {

    private String TAG = "Dashboard";
    private ArrayList<VehicleRecordDataModel> mVehicleRecords = new ArrayList<>();
    private RecyclerView mVehiclesRecycler;
    private VehicleRecordsAdapter mVehicleRecordAdapter;
    private NavController mNavController;
    private VehicleRecordViewModel mVehicleRecordViewModel;
    private SearchView searchVehicles;
    private ImageView searchButton;
    private ImageView filterButton;
    private TextView numberOfRecords;
    private TextView numberOfPages;
    private Button backButton;
    private Button nextButton;
    private ProgressDialog progressDialog;




    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVehiclesRecycler = view.findViewById(R.id.vehicleRecordsInDashboard);
        searchVehicles = view.findViewById(R.id.searchVehicleRecord);
        searchButton = view.findViewById(R.id.searchButton);
        filterButton = view.findViewById(R.id.filterButton);
        numberOfPages = view.findViewById(R.id.numberOfPages);
        numberOfRecords = view.findViewById(R.id.numberOfRecords);
        backButton = view.findViewById(R.id.backButton);
        nextButton = view.findViewById(R.id.nextButton);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mVehicleRecordViewModel = new ViewModelProvider(requireActivity()).get(VehicleRecordViewModel.class);
        mVehicleRecordViewModel.initVehicleRecords();
        mVehicleRecordViewModel.getVehicleRecords().observe(getViewLifecycleOwner(), new Observer<List<VehicleRecordDataModel>>() {
            @Override
            public void onChanged(List<VehicleRecordDataModel> vehicleDataModels) {
                Log.d(TAG, vehicleDataModels.size()+" ");
                mVehicleRecordAdapter.notifyDataSetChanged();
                initRecycler();            }
        });

        initRecycler();

    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "dashboard";

    }

    private void initRecycler() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();
        mVehicleRecords = (ArrayList<VehicleRecordDataModel>) mVehicleRecordViewModel.getVehicleRecords().getValue();
        mVehicleRecordAdapter = new VehicleRecordsAdapter(mVehicleRecords, getContext(), mNavController, 0, mVehicleRecordViewModel);
        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVehiclesRecycler.setAdapter(mVehicleRecordAdapter);
                mVehiclesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                progressDialog.dismiss();
            }


        },SPLASH_TIME_OUT);




    }
}
