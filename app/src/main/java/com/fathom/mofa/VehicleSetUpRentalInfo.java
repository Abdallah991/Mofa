package com.fathom.mofa;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.fathom.mofa.DataModels.RentalInfoDataModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleSetUp.vehicle;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleSetUpRentalInfo extends Fragment {

    private NavController mNavController;
    private TextInputEditText provider;
    private TextInputEditText providerPhoneNumber;
    private AutoCompleteTextView leaseStart;
    private AutoCompleteTextView leaseEnd;
    private TextInputLayout leaseStartTextInput;
    private TextInputLayout leaseEndTextInput;
    private Button next;
    private Button back;
    public static RentalInfoDataModel rentalInfo;
    private int actionNavigateToConfirmation = R.id.action_vehicleSetUpRentalInfo_to_vehicleSetUpConfirmation;

    public VehicleSetUpRentalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_set_up_rental_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        provider = view.findViewById(R.id.provider);
        providerPhoneNumber = view.findViewById(R.id.providerPhoneNumber);
        leaseStartTextInput = view.findViewById(R.id.leaseStartTextInput);
        leaseEndTextInput = view.findViewById(R.id.leaseEndTextInput);
        leaseStart = view.findViewById(R.id.leaseStart);
        leaseEnd = view.findViewById(R.id.leaseEnd);
        next = view.findViewById(R.id.nextVehicleRental);
        back = view.findViewById(R.id.backVehicleRental);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        final DatePickerDialog[] picker = new DatePickerDialog[1];

        leaseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                leaseStart.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

        leaseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                leaseEnd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNavController.navigate(actionNavigateToConfirmation);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpRentalInfo";
    }
}
