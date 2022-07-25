package com.fathom.mofa.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRecordRepository;

import java.util.ArrayList;
import java.util.List;
import static com.fathom.mofa.ServicesAndRepos.VehicleRecordRepository.VEHICLE_RECORD_TAG;

public class VehicleRecordViewModel extends ViewModel {

    private MutableLiveData<List<VehicleRecordDataModel>> mVehicleRecords;
    private VehicleRecordRepository mRepository;
    private int positionOfItems;

//    select data in model
    public void selectVehicleRecord (List Items, int position) {

        mVehicleRecords.setValue(Items);
        positionOfItems = position;
    }

//    get position on vehicle record
    public int getPositionOfVehicleRecord() {
        return positionOfItems;
    }

//    initialise vehicle record
    public void initVehicleRecords(){

        Log.d(VEHICLE_RECORD_TAG," init in ViewModel called.");
        if (mVehicleRecords != null){
            Log.d(VEHICLE_RECORD_TAG,"Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d(VEHICLE_RECORD_TAG," Mutable data is empty and going to be loaded");

        mRepository = VehicleRecordRepository.getInstance();
        mVehicleRecords = mRepository.getVehicleRecords();
    }

//    get vehicle records
    public LiveData<List<VehicleRecordDataModel>> getVehicleRecords() {
        return mVehicleRecords;
    }


//    add vehicle record
    public void addVehicleRecord(VehicleRecordDataModel  vehicleRecord) {
        ArrayList<VehicleRecordDataModel> vehicleRecords = new ArrayList<>();

        vehicleRecords.addAll(mVehicleRecords.getValue());
        vehicleRecords.add(vehicleRecord);

        mVehicleRecords.setValue(vehicleRecords);

    }
}
