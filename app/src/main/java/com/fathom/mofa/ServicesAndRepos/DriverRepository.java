package com.fathom.mofa.ServicesAndRepos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fathom.mofa.DataModels.DriverDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    // To get the driver Photos
    private StorageReference frontLicenseReference;
    private StorageReference backLicenseReference;
    private Bitmap frontLicense = null;
    private Bitmap backLicense = null;
    private int i = 0;

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



        i = 0;
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
                                     getFrontLicense(driver, i);

                                }
                                i++;
                            }
                        } else {
                            Log.d(DRIVER_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(DRIVER_TAG," Loading the data is DONE");
        Log.d(DRIVER_TAG, mDrivers.size()+"");
    }

//    getting front license for certain driver
    public Bitmap getFrontLicense(final DriverDataModel driver,final int position) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        frontLicense = null;
        frontLicenseReference = storageRef.child(driver.getDriverID() + "front");
        frontLicenseReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                frontLicense = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(DRIVER_TAG, " Loading the Image is DONE");
//                set the bitmap for front license
                driver.setLicenseFront(frontLicense);
                mDrivers.get(position).setLicenseFront(frontLicense);
//                call to get the back license
                getBackLicense(driver, position);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(DRIVER_TAG, " Loading the Image Failed" + exception.getMessage());
//                call to get the back license if it fails
                getBackLicense(driver, position);

            }
        });


        return frontLicense;
    }

//    getting back license for certain driver
    public Bitmap getBackLicense(final DriverDataModel driver , final int position) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        backLicense = null;

        backLicenseReference = storageRef.child(driver.getDriverID()  + "back");
        backLicenseReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                backLicense = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(DRIVER_TAG, " Loading the Image is DONE");
//                set the bitmap for back license
                driver.setLicenseBack(backLicense);
                mDrivers.get(position).setLicenseBack(backLicense);
//                add the driver to the array



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(DRIVER_TAG, " Loading the Image Failed" + exception.getMessage());


            }
        });

        return backLicense;
    }

//    setting front license for certain driver
    public Bitmap setFrontLicense(String driverId) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        frontLicense = null;

        return frontLicense;
    }

    //    setting back license for certain driver
    public Bitmap setBackLicense(String driverId) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        backLicense = null;

        return backLicense;
    }





}
