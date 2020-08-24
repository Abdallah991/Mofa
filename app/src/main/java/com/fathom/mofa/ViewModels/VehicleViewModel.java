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

    public void selectVehicle (List Items, int position) {

        mVehicles.setValue(Items);
        positionOfItems = position;
    }

    public int getPositionOfVehicle() {
        return positionOfItems;
    }

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

    public LiveData<List<VehicleDataModel>> getVehicles() {
        return mVehicles;
    }

    public void addVehicle(VehicleDataModel  vehicle) {
        ArrayList<VehicleDataModel> vehicles = new ArrayList<>();

        vehicles.addAll(mVehicles.getValue());
        vehicles.add(vehicle);

        mVehicles.setValue(vehicles);

    }

}

