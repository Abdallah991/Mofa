package com.fathom.mofa.ServicesAndRepos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.VehicleRegistration.carPhotos;
import static com.fathom.mofa.VehicleSetUp.vehicle;

public class VehicleRepository {

    private static VehicleRepository instance;
    public static String VEHICLE_TAG = "VEHICLE REPO";
    private ArrayList<VehicleDataModel> mVehicles = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference frontImageRef;
    private StorageReference backImageRef;
    private StorageReference leftImageRef;
    private StorageReference rightImageRef;

    public static VehicleRepository getInstance() {
        if (instance == null) {

            Log.d(VEHICLE_TAG ," getting static instance of the vehicle repo.");
            instance = new VehicleRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the vehicle repo.");

        return instance;
    }
    public MutableLiveData<List<VehicleDataModel>> getVehicles() {

        // calling the webservice task of function
        setVehicleItems();
        MutableLiveData<List<VehicleDataModel>> data = new MutableLiveData<>();
        data.setValue(mVehicles);

        return data;
    }

    private  void setVehicleItems () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(VEHICLE_TAG," Loading the data is going to start");


        db.collection("Vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(VEHICLE_TAG, document.getId() + " => " + document.getData());
                                if (mVehicles.size() <= task.getResult().size()) {
                                    VehicleDataModel vehicle = document.toObject(VehicleDataModel.class);
                                    mVehicles.add(vehicle);

                                }
                            }
                        } else {
                            Log.d(VEHICLE_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(VEHICLE_TAG," Loading the data is DONE");
        Log.d(VEHICLE_TAG, mVehicles.size()+"");
    }



    // Getting Vehicle Images from the backend
    public CarPhotosDataModel getImage (String vehiclePlateNumber) {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        final CarPhotosDataModel mCarPhotos = new CarPhotosDataModel();

        for (int position = 0; position < 4; position++) {

            switch (position) {
                case 0:
                    rightImageRef = storageRef.child(vehiclePlateNumber + "right");
                    rightImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            mCarPhotos.setPhotoRightSide(bmp);
                            Log.d(VEHICLE_TAG, " Loading the Image is DONE");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d(VEHICLE_TAG, " Loading the Image Failed" + exception.getMessage());
                            // Handle any errors
                        }
                    });
                    break;

                case 1:
                    leftImageRef = storageRef.child(vehiclePlateNumber + "left");
                    leftImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            mCarPhotos.setPhotoLeftSide(bmp);
                            Log.d(VEHICLE_TAG, " Loading the Image is DONE");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d(VEHICLE_TAG, " Loading the Image Failed" + exception.getMessage());
                            // Handle any errors
                        }
                    });
                    break;

                case 2:
                    frontImageRef = storageRef.child(vehiclePlateNumber + "front");
                    frontImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            mCarPhotos.setPhotoFrontSide(bmp);
                            Log.d(VEHICLE_TAG, " Loading the Image is DONE");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d(VEHICLE_TAG, " Loading the Image Failed" + exception.getMessage());
                            // Handle any errors
                        }
                    });
                    break;

                case 3:
                    backImageRef = storageRef.child(vehiclePlateNumber + "back");
                    backImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            mCarPhotos.setPhotoBackSide(bmp);
                            Log.d(VEHICLE_TAG, " Loading the Image is DONE");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d(VEHICLE_TAG, " Loading the Image Failed" + exception.getMessage());
                            // Handle any errors
                        }
                    });
                    break;
            }
        }

        return mCarPhotos;
    }




    // Uploading Vehicle Data Model and Images
    public void uploadVehicle(VehicleDataModel vehicle) {
        db.collection("Vehicles")
                .document(vehicle.getPlateNumber()).set(vehicle);
    }

    public void uploadVehicleLeftSide(String vehicleLeftSideName, Bitmap leftSide) {

    }
    public void uploadVehicleRightSide(String vehicleRightSideName, Bitmap rightSide) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        rightImageRef = storageRef.child(vehicle.getPhotoRightSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoRightSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = rightImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(VEHICLE_TAG, "User right image failed to upload.");
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(VEHICLE_TAG, "User right image uploaded.");
            }
        });

    }
    public void uploadVehicleFrontSide(String vehicleFrontSideName, Bitmap frontSide) {

    }
    public void uploadVehicleBackSide(String vehicleBackSideName, Bitmap backSide) {

    }
}
