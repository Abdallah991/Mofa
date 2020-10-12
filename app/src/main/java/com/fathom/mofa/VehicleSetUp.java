package com.fathom.mofa;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.mofa.DataModels.VehicleDataModel;
import com.google.android.material.textfield.TextInputEditText;

import static com.fathom.mofa.MainActivity.FRAGMENT;



public class VehicleSetUp extends Fragment {
    private NavController mNavController;
    public static VehicleDataModel vehicle = new VehicleDataModel();
    private AutoCompleteTextView manufactureTextView;
    private AutoCompleteTextView colorTextView;
    private Spinner typeTextView;
    private AutoCompleteTextView makeTextView;
    private TextInputEditText plateNumberEditText;
    private TextInputEditText chassisNumber;
    private TextInputEditText modelEditText;
    private int actionNavigateToVehicleSetUpRegistration = R.id.action_vehicleSetUp_to_vehicleRegistration;


    public VehicleSetUp() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_set_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] MANUFACTURERS = getResources().getStringArray(R.array.manufacturers);
        String[] COLORS = getResources().getStringArray(R.array.color);
        String[] TYPES = getResources().getStringArray(R.array.type);
        String[] YEARS = getResources().getStringArray(R.array.make);
        ArrayAdapter<String> manufactureAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, MANUFACTURERS);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, COLORS);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, TYPES) {
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
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, YEARS);
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        manufactureTextView = view.findViewById(R.id.manufacturer);
        colorTextView = view.findViewById(R.id.color);
        typeTextView = view.findViewById(R.id.carType);
        makeTextView = view.findViewById(R.id.make);
        plateNumberEditText = view.findViewById(R.id.plateNumber);
        chassisNumber = view.findViewById(R.id.chassisNumber);
        modelEditText = view.findViewById(R.id.model);
        Button next = view.findViewById(R.id.nextVehicleInformation);

        manufactureTextView.setAdapter(manufactureAdapter);
        colorTextView.setAdapter(colorAdapter);
        typeTextView.setAdapter(typeAdapter);
        makeTextView.setAdapter(yearAdapter);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (getCarInfo()) {
//                String type = typeTextView.getSelectedItem().toString();
//                vehicle.setCarType(type);
                mNavController.navigate(actionNavigateToVehicleSetUpRegistration);
               }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUp";
    }

    private boolean getCarInfo() {
        String plateNumber = plateNumberEditText.getText().toString();
        String chassis = chassisNumber.getText().toString();
        String model = modelEditText.getText().toString();
        String manufacturer = manufactureTextView.getText().toString();
        String color = colorTextView.getText().toString();
        String type = typeTextView.getSelectedItem().toString();
        String make = makeTextView.getText().toString();
        String[] carTypes = getResources().getStringArray(R.array.type);
        String selectCarType = getResources().getString(R.string.you_didnt_select_car_type);
        String fillMissingFields = getResources().getString(R.string.fill_missing_feilds);
        String sixDigitsOrLess = getResources().getString(R.string.six_digits_or_less);

        if (plateNumber.length() <= 6) {


            if ((!plateNumber.isEmpty()) && (!chassis.isEmpty()) &&
                    (!model.isEmpty()) &&
                    (!manufacturer.isEmpty()) && (!color.isEmpty()) &&
                    (!type.equals(carTypes[0]))
                    && (!make.isEmpty())) {

                vehicle.setPlateNumber(plateNumber);
                vehicle.setChassisNumber(chassis);
                vehicle.setModel(model);
                vehicle.setManufacturer(manufacturer);
                vehicle.setColorOfCar(color);
                vehicle.setCarType(type);
                vehicle.setMake(make);
                return true;
            } else {

                if ((!plateNumber.isEmpty()) && (!chassis.isEmpty()) &&
                        (!model.isEmpty()) &&
                        (!manufacturer.isEmpty()) && (!color.isEmpty())
                        && (!make.isEmpty()) && (type.equals(carTypes[0]))) {
                    Toast.makeText(getContext(), selectCarType, Toast.LENGTH_SHORT).show();
                    return false;

                } else {
                    Toast.makeText(getContext(), fillMissingFields, Toast.LENGTH_SHORT).show();
                }
                return false;

            }

        } else {

            Toast.makeText(getContext(), sixDigitsOrLess, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        manufactureTextView = null;
        colorTextView = null;
        typeTextView = null;
        makeTextView = null;
        plateNumberEditText = null;
        chassisNumber = null;
        modelEditText = null;
    }
}
