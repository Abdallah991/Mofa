package com.fathom.mofa.ViewModels;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.R;
import com.fathom.mofa.ServicesAndRepos.DriverRepository;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.ServicesAndRepos.DriverRepository.DRIVER_TAG;


public class DriverViewModel extends ViewModel {

    private MutableLiveData<List<DriverDataModel>> mDrivers;
    private DriverRepository mRepository;
    private int positionOfItems;
    public DriverDataModel driver = new DriverDataModel();
    private Bitmap frontLicense = null;
    private Bitmap backLicense = null;



//    select driver implementation
    public void selectDriver(List Items, int position) {

        mDrivers.setValue(Items);
        positionOfItems = position;
    }

//    get te position of item
    public int getPositionOfDriver() {
        return positionOfItems;
    }

//    initialise the model
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

//    get drivers
    public LiveData<List<DriverDataModel>> getDrivers() {
        return mDrivers;
    }


//    add a driver to the model
    public void addDriver(DriverDataModel  driver) {
        ArrayList<DriverDataModel> drivers = new ArrayList<>();

        drivers.addAll(mDrivers.getValue());
        drivers.add(driver);

        mDrivers.setValue(drivers);


    }

//    edit a driver
    public void modifyDriver(DriverDataModel  driverOld, DriverDataModel driverNew) {
        ArrayList<DriverDataModel> drivers = new ArrayList<>();

        drivers.addAll(mDrivers.getValue());
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverID() == driverOld.getDriverID()) {
                drivers.remove(driverOld);
            }
        }
        drivers.add(driverNew);

        mDrivers.setValue(drivers);


    }

//    selecting a driver
    public DriverDataModel selectDriver(int i) {
        driver = new DriverDataModel();

        driver = mDrivers.getValue().get(i);


        return driver;
    }

//      Delete driver from View Model
    public void deleteDriver(DriverDataModel  driverOld) {
        ArrayList<DriverDataModel> drivers = new ArrayList<>();
        drivers = (ArrayList<DriverDataModel>) mDrivers.getValue();

        drivers.remove(driverOld);
        mDrivers.setValue(drivers);

    }
  

}
