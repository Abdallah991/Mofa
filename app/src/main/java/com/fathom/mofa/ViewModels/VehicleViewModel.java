package com.fathom.mofa.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.ServicesAndRepos.VehicleRepository.VEHICLE_TAG;

public class VehicleViewModel extends ViewModel {

    private MutableLiveData<List<VehicleDataModel>> mVehicles;
    private VehicleRepository mRepository;
    private int positionOfItems;

//    select vehicle data
    public void selectVehicle (List Items, int position) {

        mVehicles.setValue(Items);
        positionOfItems = position;
    }

//    get position of vehicle
    public int getPositionOfVehicle() {
        return positionOfItems;
    }

//    initialise vehicle values
    public void initVehicles(){

        Log.d(VEHICLE_TAG," init in ViewModel called.");
        if (mVehicles != null){
            Log.d(VEHICLE_TAG,"Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d(VEHICLE_TAG," Mutable data is empty and going to be loaded");

        mRepository = VehicleRepository.getInstance();
        mVehicles = mRepository.getVehicles();
    }

//    get vehicle data
    public LiveData<List<VehicleDataModel>> getVehicles() {
        return mVehicles;
    }

//    add vehicle to the model
    public void addVehicle(VehicleDataModel  vehicle) {
        ArrayList<VehicleDataModel> vehicles = new ArrayList<>();

        vehicles.addAll(mVehicles.getValue());
        vehicles.add(vehicle);

        mVehicles.setValue(vehicles);

    }

//    update vehicle status
    public void updateVehicleStatus(VehicleDataModel  vehicle) {
        ArrayList<VehicleDataModel> vehicles = new ArrayList<>();

        vehicles.addAll(mVehicles.getValue());
        for (VehicleDataModel oneVehicle: vehicles) {
            if (oneVehicle.getPlateNumber().equals(vehicle.getPlateNumber())) {
                oneVehicle.setStatus(vehicle.getStatus());
            }
        }
//        vehicles.add(vehicle);

        mVehicles.setValue(vehicles);

    }


}

