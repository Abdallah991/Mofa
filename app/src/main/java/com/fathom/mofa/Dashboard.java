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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.mofa.Adapters.VehicleRecordsAdapter;
import com.fathom.mofa.Adapters.VehiclesAdapter;
import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.fathom.mofa.ViewModels.VehicleRecordViewModel;
import com.fathom.mofa.ViewModels.VehicleViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {

    private String TAG = "Dashboard";
    private ArrayList<VehicleRecordDataModel> mVehicleRecords = new ArrayList<>();
    private ArrayList<VehicleRecordDataModel> filteredVehicleRecords = new ArrayList<>();
    private RecyclerView mVehiclesRecycler;
    private VehicleRecordsAdapter mVehicleRecordAdapter;
    private NavController mNavController;
    private VehicleRecordViewModel mVehicleRecordViewModel;
    private SearchView searchVehicleRecords;
    private ImageView searchButton;
    private ImageView filterButton;
    private TextView numberOfRecords;
    private int pages;
    private ProgressDialog progressDialog;
    private int actionToVehicleRecordDetail = R.id.action_dashboard_to_vehicleRecordDetails;
    // Sorting Spinner
    private int positionOfSorter;
    private String[] sortingBys ;
    private ArrayAdapter<String> sortAdapter;
    private Spinner sortSpinner;
    private boolean filter = false;
    private VehicleRecordSorter sorter;


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
        searchVehicleRecords = view.findViewById(R.id.searchVehicleRecord);
        searchButton = view.findViewById(R.id.searchButton);
        filterButton = view.findViewById(R.id.filterButton);
        numberOfRecords = view.findViewById(R.id.numberOfRecords);
        sortSpinner = view.findViewById(R.id.sortName);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        sortingBys = getResources().getStringArray(R.array.sort);

        sortAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, sortingBys);

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

        searchVehicleRecords.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                String searchText = searchVehicleRecords.getQuery().toString();
                filter(searchText);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (s.isEmpty())
                {
                    mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
                }


                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchVehicleRecords.getQuery().toString();
                filter(searchText);

            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!filter) {
////                    filterLayout.setVisibility(View.VISIBLE);
//                    filter = true;
//                }else {
////                    filterLayout.setVisibility(View.GONE);
//                    filter = false;
//                }

            }
        });

        initRecycler();
        initSorting();

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
        mVehicleRecordAdapter = new VehicleRecordsAdapter(mVehicleRecords, getContext(), mNavController, actionToVehicleRecordDetail, mVehicleRecordViewModel);
        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVehiclesRecycler.setAdapter(mVehicleRecordAdapter);
                mVehiclesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                numberOfRecords.setText(mVehicleRecords.size()+ " Records");
                progressDialog.dismiss();
            }


        },SPLASH_TIME_OUT);




    }

    private void filter(String searchText) {
        filteredVehicleRecords = new ArrayList<>();

        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
            if (vehicleRecord.getDriverName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }
        }
        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
            if (vehicleRecord.getModel().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }

        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleRecordDataModel vehicleRecord  : mVehicleRecords) {
            if (vehicleRecord.getMake().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }

        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleRecordDataModel vehicleRecord  : mVehicleRecords) {
            if (vehicleRecord.getRentalInfo().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }

        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }


        for (VehicleRecordDataModel vehicleRecord  : mVehicleRecords) {
            if (vehicleRecord.getStatus().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }

        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();

        } else {
            mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
        }


    }

    private void checkIfSearchIsValid() {
        if (!filteredVehicleRecords.isEmpty()) {
            mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
        }
        else {
            mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSorting() {





                sortAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, sortingBys) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            return true;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
//                        if (position == 0) {
//                            // Set the hint text color gray
//                            tv.setTextColor(getResources().getColor(R.color.appGrey));
//                        } else {
//                            tv.setTextColor(getResources().getColor(R.color.black));
//                        }
                        return view;
                    }
                };
                sortAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                sortSpinner.setAdapter(sortAdapter);
                sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);

                        filteredVehicleRecords = new ArrayList<>();
                        sorter = new VehicleRecordSorter(mVehicleRecords);
                        switch (position) {
                            case 0:
                                filteredVehicleRecords = sorter.getSortedJobCandidateByModel();
                                mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
                                break;
                            case 1:
                                filteredVehicleRecords = sorter.getSortedJobCandidateByDriver();
                                mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
                                break;
                            case 2:
                                filteredVehicleRecords = sorter.getSortedJobCandidateByStatus();
                                mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
                                break;
                            case 3:
                                filteredVehicleRecords = sorter.getSortedJobCandidateByRental();
                                mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
                               break;
                            case 4:
                                filteredVehicleRecords = sorter.getSortedJobCandidateByMake();
                                mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
//                progressDialog.dismiss();
            }


}
