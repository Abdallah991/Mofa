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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
    private String[] sortingBys;
    private ArrayAdapter<String> sortAdapter;
    private Spinner sortSpinner;
    private VehicleRecordSorter sorter;
    // filter dialog
    private ImageView cancelButton;
    private Button confirmFilter;
    private Spinner typeSpinner;
//    private Spinner providerSpinner;
    private Spinner statusSpinner;
    private TextInputEditText dateFrom;
    private TextInputEditText dateTo;
    private Date dateFromValue;
    private Date dateToValue;
    private String vehicleType;
    private String status;
    private String provider;
    // spinner Arrays
    private String[] types;
    private ArrayList<String> providers;
    private String[] statuses;
    private ArrayAdapter<String> typeAdapter;
//    private ArrayAdapter<String> providerAdapter;
    private ArrayAdapter<String> statusAdapter;


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

        // Filter dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.filter_search);
        cancelButton = dialog.findViewById(R.id.cancelFilter);
        confirmFilter = dialog.findViewById(R.id.confirmFilter);
        typeSpinner = dialog.findViewById(R.id.typeSpinner);
//        providerSpinner = dialog.findViewById(R.id.providerSpinner);
        statusSpinner = dialog.findViewById(R.id.statusSpinner);
        dateFrom = dialog.findViewById(R.id.dateFromFilter);
        dateTo = dialog.findViewById(R.id.dateToFilter);
        final DatePickerDialog[] picker = new DatePickerDialog[1];

        dateTo.setInputType(0);
        dateFrom.setInputType(0);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");
        // loading spinner Arrays
        sortingBys = getResources().getStringArray(R.array.sort);
        types = getResources().getStringArray(R.array.type);
        statuses = getResources().getStringArray(R.array.statuses);

        sortAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, sortingBys);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mVehicleRecordViewModel = new ViewModelProvider(requireActivity()).get(VehicleRecordViewModel.class);
        mVehicleRecordViewModel.initVehicleRecords();
        mVehicleRecordViewModel.getVehicleRecords().observe(getViewLifecycleOwner(), new Observer<List<VehicleRecordDataModel>>() {
            @Override
            public void onChanged(List<VehicleRecordDataModel> vehicleDataModels) {
                Log.d(TAG, vehicleDataModels.size() + " ");
                mVehicleRecordAdapter.notifyDataSetChanged();
                initRecycler();
            }
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

                if (s.isEmpty()) {
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
                dialog.show();
                loadSpinnerArrays();

            }
        });
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                dateFromValue = null;
                picker[0] = new DatePickerDialog(getContext(), R.style.DatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateFrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    dateFromValue = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    Toast.makeText(getContext(), issueDateValue.toString() +" ", Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker[0].show();
            }
        });
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                dateToValue = null;
                picker[0] = new DatePickerDialog(getContext(), R.style.DatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateTo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    dateToValue = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    Toast.makeText(getContext(), issueDateValue.toString() +" ", Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                filteredSearch();
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
                String record = getResources().getString(R.string.records);
                numberOfRecords.setText(mVehicleRecords.size() +" "+ record);
                filteredVehicleRecords = new ArrayList<>();
                sorter = new VehicleRecordSorter(mVehicleRecords);
                filteredVehicleRecords = sorter.getSortedJobCandidateByModel();
                mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
                progressDialog.dismiss();
            }


        }, SPLASH_TIME_OUT);


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

        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
            if (vehicleRecord.getMake().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }

        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }

        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
            if (vehicleRecord.getRentalInfo().toLowerCase().contains(searchText.toLowerCase())) {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }

        if (!filteredVehicleRecords.isEmpty()) {
            checkIfSearchIsValid();
            return;
        }


        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
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
        } else {
            mVehicleRecordAdapter.filterDashboard(mVehicleRecords);
//            Toast.makeText(getContext(), "No Searchable match", Toast.LENGTH_SHORT).show();
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
                        mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
                        break;
                    case 1:
                        filteredVehicleRecords = sorter.getSortedJobCandidateByDriver();
                        mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
                        break;
                    case 2:
                        filteredVehicleRecords = sorter.getSortedJobCandidateByStatus();
                        mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
                        break;
                    case 3:
                        filteredVehicleRecords = sorter.getSortedJobCandidateByRental();
                        mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
                        break;
                    case 4:
                        filteredVehicleRecords = sorter.getSortedJobCandidateByMake();
                        mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//                progressDialog.dismiss();
    }

    private void loadSpinnerArrays() {

//        providers = new ArrayList<>();
//        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
//
//            if (providers.isEmpty()) {
//                providers.add(vehicleRecord.getRentalInfo());
//            }
//            else {
//
//
//            for (String value : providers) {
//                if (!value.equals(vehicleRecord.getRentalInfo())) {
//                    providers.add(vehicleRecord.getRentalInfo());
//                }
//
//                }
//            }
//
//        }
//
//        providerAdapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_list_item_1, providers);
        typeAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, types);
        statusAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, statuses);
//        providerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        providerSpinner.setAdapter(providerAdapter);
        typeSpinner.setAdapter(typeAdapter);
        statusSpinner.setAdapter(statusAdapter);

    }

    private void filteredSearch() {

        status = statusSpinner.getSelectedItem().toString();
        vehicleType = typeSpinner.getSelectedItem().toString();
        // Toast message
//        Toast.makeText(getContext(), status+vehicleType, Toast.LENGTH_SHORT).show();
        filteredVehicleRecords = new ArrayList<>();
        for (VehicleRecordDataModel vehicleRecord : mVehicleRecords) {
            if (vehicleRecord.getStatus().equals(status) &&
                    vehicleRecord.getCarType().equals(vehicleType) &&
                    vehicleRecord.getDate().after(dateFromValue) && vehicleRecord.getDate().before(dateToValue))
            {
                filteredVehicleRecords.add(vehicleRecord);
            }

        }


        mVehicleRecordAdapter.filterDashboard(filteredVehicleRecords);
        dateTo.setShowSoftInputOnFocus(false);
        dateFrom.setShowSoftInputOnFocus(false);
        searchVehicleRecords.clearFocus();

    }



}
