package com.fathom.mofa.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.R;
import com.fathom.mofa.ServicesAndRepos.DriverRepository;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.ServicesAndRepos.DriverRepository.DRIVER_TAG;


public class DriverViewModel extends ViewModel {

    private MutableLiveData<List<DriverDataModel>> mDrivers;
    private DriverRepository mRepository;
    private int positionOfItems;

    public void selectDriver(List Items, int position) {

        mDrivers.setValue(Items);
        positionOfItems = position;
    }

    public int getPositionOfDriver() {
        return positionOfItems;
    }

    public void initDrivers() {

        Log.d(DRIVER_TAG, " init in ViewModel called.");
        if (mDrivers != null) {
            Log.d(DRIVER_TAG, "Mutable data is already loaded from the Repo.");
             boolean setVisible = true;
            return;
        }

        Log.d(DRIVER_TAG, " Mutable data is empty and going to be loaded");

        mRepository = DriverRepository.getInstance();
        mDrivers = mRepository.getDrivers();

    }

    public LiveData<List<DriverDataModel>> getDrivers() {
        return mDrivers;
    }


    public void addDriver(DriverDataModel  driver) {
        ArrayList<DriverDataModel> drivers = new ArrayList<>();

        drivers.addAll(mDrivers.getValue());
        drivers.add(driver);

        mDrivers.setValue(drivers);


    }
  

}
