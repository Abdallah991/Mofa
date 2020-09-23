package com.fathom.mofa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fathom.mofa.DataModels.NotificationDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.ServicesAndRepos.VehicleRepository;
import com.fathom.mofa.ViewModels.NotificationViewModel;
import com.fathom.mofa.ViewModels.VehicleViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import static com.fathom.mofa.LoginActivity.USER;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRegistration.carPhotos;
import static com.fathom.mofa.VehicleSetUp.vehicle;
import static com.fathom.mofa.VehicleSetUpDamageReport.damageReport;
import static com.fathom.mofa.VehicleSetUpRentalInfo.rentalInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleSetUpSignature extends Fragment {

    private NavController mNavController;
    private Button rentalSignature;
    private Button mofaSignature;
    private Button saveSignature;
    private Button cancelSignature;
    private Button clearSignature;
    private LinearLayout signatureLayout;
    private int signatureSelector;
    private Button done;
    private VehicleViewModel model;
    private NotificationViewModel mNotificationViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference frontImageRef;
    private StorageReference backImageRef;
    private StorageReference leftImageRef;
    private StorageReference rightImageRef;
    private final String TAG = "VEHICLE SET UP";
    private ProgressDialog progressDialog;
    private VehicleRepository mVehicleRepository;
    public static boolean doneUploading = false;

    public VehicleSetUpSignature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_set_up_signature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rentalSignature = view.findViewById(R.id.rentalSignature);
        mofaSignature = view.findViewById(R.id.mofaSignature);
        done = view.findViewById(R.id.doneVehicleSetUp);
        // Dialog components
        final Dialog dialog = new Dialog(getContext());
        final CaptureSignatureView mSig = new CaptureSignatureView(getContext(), null);
        dialog.setContentView(R.layout.signature);
        dialog.setTitle("Signature");
        saveSignature = dialog.findViewById(R.id.save);
        cancelSignature = dialog.findViewById(R.id.cancel);
        clearSignature = dialog.findViewById(R.id.clear);
        signatureLayout = dialog.findViewById(R.id.signature2);

        // setting up Progress Dialog and Storage
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        // adding the signature functionality to the signatureLayout
        signatureLayout.addView(mSig, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        model = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        model.initVehicles();
        model.getVehicles().observe(getViewLifecycleOwner(), new Observer<List<VehicleDataModel>>() {
            @Override
            public void onChanged(List<VehicleDataModel> vehicleDataModels) {

            }
        });

        mNotificationViewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);
        mNotificationViewModel.initNotifications();
        mNotificationViewModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<List<NotificationDataModel>>() {
            @Override
            public void onChanged(List<NotificationDataModel> notificationDataModels) {

            }
        });
        // filling up some data for Vehicle Object
        vehicle.setRentalInfo(vehicle.getPlateNumber());
        vehicle.setCarName(vehicle.getManufacturer()+ " "+vehicle.getModel()+ " "+vehicle.getPlateNumber());
        vehicle.setPhotoLeftSide(vehicle.getPlateNumber()+"left");
        vehicle.setPhotoRightSide(vehicle.getPlateNumber()+"right");
        vehicle.setPhotoFrontSide(vehicle.getPlateNumber()+"front");
        vehicle.setPhotoBackSide(vehicle.getPlateNumber()+"back");
        vehicle.setStatus("Returned");

        rentalSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureSelector = 0;
                dialog.show();

            }
        });

        mofaSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureSelector = 1;
                dialog.show();

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadVehicleInfo();
                uploadDamageReportOfVehicle();
                uploadRentalInfoOfVehicle();
                uploadNotification();
                uploadVehicleLeftSide();
                uploadVehicleRightSide();
                uploadVehicleFrontSide();
                uploadVehicleBackSide();
                uploadVehicleFrontInterior();
                uploadVehicleBackInterior();
                uploadVehicleTrunk();
                updateVehicleToViewModel();



                mNavController.navigate(R.id.home);

            }
        });

        cancelSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        clearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSig.ClearCanvas();
            }
        });

        saveSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signatureSelector == 0) {
                    Bitmap signature = mSig.getBitmap();
                    Drawable d = new BitmapDrawable(getResources(), signature);
                    rentalSignature.setBackground(d);
                    mSig.ClearCanvas();
                    dialog.dismiss();
                }
                if (signatureSelector == 1) {

                    Bitmap signature = mSig.getBitmap();
                    Drawable d = new BitmapDrawable(getResources(), signature);
                    mofaSignature.setBackground(d);
                    mSig.ClearCanvas();
                    dialog.dismiss();
                }

            }
        });
    }

    private void uploadNotification() {
         SharedPreferences pref = getActivity().getSharedPreferences(USER, 0); // 0 - for private mode
        String name = pref.getString("userName", "");
        String added = getResources().getString(R.string.been_added);
        NotificationDataModel notification = new NotificationDataModel();
        notification.setNotificationContent(vehicle.getManufacturer()+" "+ vehicle.getModel()+" "+vehicle.getMake()+" "+ added+" "+name);
        Date date = new Date();
        notification.setNotificationDate(date);
        notification.setNotificationType("Set Up");
        db.collection("Notifications")
                .document().set(notification);
        addNotificationToDataModel(notification);

    }

    private void uploadVehicleInfo() {
        progressDialog.show();
        db.collection("Vehicles")
                .document(vehicle.getPlateNumber()).set(vehicle);
//        mVehicleRepository.uploadVehicle(vehicle);
    }

    private void uploadRentalInfoOfVehicle() {
        db.collection("Rental Information")
                .document(rentalInfo.getCarId()).set(rentalInfo);

    }

    private void uploadDamageReportOfVehicle() {
        damageReport.setCarType(vehicle.getCarType());
        // Should set damageReportName
        db.collection("Damage Reports")
                .document().set(damageReport);

    }

    private void uploadVehicleRightSide() {
//        mVehicleRepository.uploadVehicleRightSide(vehicle.getPhotoRightSide(), carPhotos.getPhotoRightSide());
        rightImageRef = storageRef.child(vehicle.getPhotoRightSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoRightSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = rightImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User right image failed to upload.");
//                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User right image uploaded.");
//                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleLeftSide() {
        leftImageRef = storageRef.child(vehicle.getPhotoLeftSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoLeftSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = leftImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User left image failed to upload.");
//                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User left image uploaded.");
//                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleFrontSide() {
        frontImageRef = storageRef.child(vehicle.getPhotoFrontSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoFrontSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = frontImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User front image failed to upload.");
//                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User front image uploaded.");
//                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleBackSide() {
        backImageRef = storageRef.child(vehicle.getPhotoBackSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoBackSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = backImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User back image failed to upload.");
//                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User back image uploaded.");
//                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleFrontInterior() {
        backImageRef = storageRef.child(vehicle.getVehicleFrontInterior());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getVehicleFrontInterior();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = backImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User back image failed to upload.");
//                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User back image uploaded.");
//                progressDialog.dismiss();
            }
        });

    }
    private void uploadVehicleBackInterior() {
        backImageRef = storageRef.child(vehicle.getVehicleBackInterior());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getVehicleBackInterior();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = backImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User back image failed to upload.");
//                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User back image uploaded.");
//                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleTrunk() {
        backImageRef = storageRef.child(vehicle.getVehicleTrunk());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getVehicleTrunk();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = backImageRef.putBytes(data);
//        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User back image failed to upload.");
                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User back image uploaded.");
                progressDialog.dismiss();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpSignature";
    }

    private void updateVehicleToViewModel() {

        model.addVehicle(vehicle);
    }

    private void addNotificationToDataModel(NotificationDataModel notification) {
        mNotificationViewModel.addNotification(notification);

    }




}
