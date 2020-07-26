package com.fathom.mofa.ServicesAndRepos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private static DriverRepository instance;
    public static String DRIVER_TAG = "DRIVER REPO";
    private ArrayList<DriverDataModel> mDrivers = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference frontImageRef;
    private StorageReference backImageRef;

    public static DriverRepository getInstance() {
        if (instance == null) {

            Log.d(DRIVER_TAG ," getting static instance of the driver repo.");
            instance = new DriverRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the driver repo.");

        return instance;
    }

    public MutableLiveData<List<DriverDataModel>> getDrivers() {

        // calling the webservice task of function
        setDriverItems();
        MutableLiveData<List<DriverDataModel>> data = new MutableLiveData<>();
        data.setValue(mDrivers);

        return data;
    }

    private  void setDriverItems () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(DRIVER_TAG," Loading the data is going to start");


        db.collection("Drivers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(DRIVER_TAG, document.getId() + " => " + document.getData());
                                if (mDrivers.size() <= task.getResult().size()) {
                                    DriverDataModel driver = document.toObject(DriverDataModel.class);
                                    mDrivers.add(driver);

                                }
                            }
                        } else {
                            Log.d(DRIVER_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(DRIVER_TAG," Loading the data is DONE");
        Log.d(DRIVER_TAG, mDrivers.size()+"");
    }


}
