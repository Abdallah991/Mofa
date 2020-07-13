package com.fathom.mofa;

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
import android.widget.Toast;

import com.fathom.mofa.DataModels.VehicleDataModel;
import com.google.android.material.textfield.TextInputEditText;

import static com.fathom.mofa.MainActivity.FRAGMENT;



public class VehicleSetUp extends Fragment {
    private NavController mNavController;
    public static VehicleDataModel vehicle = new VehicleDataModel();
    private AutoCompleteTextView manufactureTextView;
    private AutoCompleteTextView colorTextView;
    private AutoCompleteTextView typeTextView;
    private AutoCompleteTextView yearTextView;
    private TextInputEditText plateNumberEditText;
    private TextInputEditText modelEditText;
    private Button next;
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
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, TYPES);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, YEARS);
        manufactureTextView = view.findViewById(R.id.manufacturer);
        colorTextView = view.findViewById(R.id.color);
        typeTextView = view.findViewById(R.id.carType);
        yearTextView = view.findViewById(R.id.make);
        plateNumberEditText = view.findViewById(R.id.plateNumber);
        modelEditText = view.findViewById(R.id.model);
        next = view.findViewById(R.id.nextVehicleInformation);

        manufactureTextView.setAdapter(manufactureAdapter);
        colorTextView.setAdapter(colorAdapter);
        typeTextView.setAdapter(typeAdapter);
        yearTextView.setAdapter(yearAdapter);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (getCarInfo()) {
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
        String model = modelEditText.getText().toString();
        String manufacturer = manufactureTextView.getText().toString();
        String color = colorTextView.getText().toString();
        String type = typeTextView.getText().toString();
        String year = yearTextView.getText().toString();

//        if ((!plateNumber.isEmpty())&& (!model.isEmpty())&&
//                (!manufacturer.isEmpty())&& (!color.isEmpty())&&
//                (!type.isEmpty())&& (!year.isEmpty()))  {

            vehicle.setPlateNumber(plateNumber);
            vehicle.setModel(model);
            vehicle.setManufacturer(manufacturer);
            vehicle.setColorOfCar(color);
            vehicle.setCarType(type);
            vehicle.setMake(year);
            return true;
//        }
//        else {
//            Toast.makeText(getContext(), "Please fill the missing fields" , Toast.LENGTH_SHORT).show();
//            return false;
//
//        }

    }



}
