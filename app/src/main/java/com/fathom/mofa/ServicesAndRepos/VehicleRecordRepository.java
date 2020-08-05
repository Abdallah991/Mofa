package com.fathom.mofa.ServicesAndRepos;


import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

public class VehicleRecordRepository {

    private static VehicleRecordRepository instance;
    public static String VEHICLE_RECORD_TAG = "VEHICLE RECORD REPO";
    private ArrayList<VehicleRecordDataModel> mVehicleRecords = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference frontImageRef;
    private StorageReference backImageRef;
    private StorageReference leftImageRef;
    private StorageReference rightImageRef;

    public static VehicleRecordRepository getInstance() {
        if (instance == null) {

            Log.d(VEHICLE_RECORD_TAG ," getting static instance of the vehicle records repo.");
            instance = new VehicleRecordRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the vehicle records repo.");

        return instance;
    }
    public MutableLiveData<List<VehicleRecordDataModel>> getVehicleRecords() {

        // calling the webservice task of function
        setVehicleRecordItems();
        MutableLiveData<List<VehicleRecordDataModel>> data = new MutableLiveData<>();
        data.setValue(mVehicleRecords);

        return data;
    }

    private  void setVehicleRecordItems () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(VEHICLE_RECORD_TAG," Loading the data is going to start");


        db.collection("VehicleRecords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(VEHICLE_RECORD_TAG, document.getId() + " => " + document.getData());
                                if (mVehicleRecords.size() <= task.getResult().size()) {
                                    VehicleRecordDataModel vehicleRecord = document.toObject(VehicleRecordDataModel.class);
                                    mVehicleRecords.add(vehicleRecord);

                                }
                            }
                        } else {
                            Log.d(VEHICLE_RECORD_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(VEHICLE_RECORD_TAG," Loading the data is DONE");
        Log.d(VEHICLE_RECORD_TAG, mVehicleRecords.size()+"");
    }



    // Getting Vehicle Images from the backend
    public CarPhotosDataModel getImage (String vehicleImages) {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        final CarPhotosDataModel mCarPhotos = new CarPhotosDataModel();

//        for (int position = 0; position < 4; position++) {
//
//            switch (position) {
//                case 0:
//                    rightImageRef = storageRef.child(vehiclePlateNumber + "right");
//                    rightImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            mCarPhotos.setPhotoRightSide(bmp);
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image is DONE");
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image Failed" + exception.getMessage());
//                            // Handle any errors
//                        }
//                    });
//                    break;
//
//                case 1:
//                    leftImageRef = storageRef.child(vehiclePlateNumber + "left");
//                    leftImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            mCarPhotos.setPhotoLeftSide(bmp);
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image is DONE");
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image Failed" + exception.getMessage());
//                            // Handle any errors
//                        }
//                    });
//                    break;
//
//                case 2:
//                    frontImageRef = storageRef.child(vehiclePlateNumber + "front");
//                    frontImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            mCarPhotos.setPhotoFrontSide(bmp);
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image is DONE");
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image Failed" + exception.getMessage());
//                            // Handle any errors
//                        }
//                    });
//                    break;
//
//                case 3:
//                    backImageRef = storageRef.child(vehiclePlateNumber + "back");
//                    backImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            mCarPhotos.setPhotoBackSide(bmp);
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image is DONE");
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            Log.d(VEHICLE_RECORD_TAG, " Loading the Image Failed" + exception.getMessage());
//                            // Handle any errors
//                        }
//                    });
//                    break;
//            }
//        }

        return mCarPhotos;
    }
}
